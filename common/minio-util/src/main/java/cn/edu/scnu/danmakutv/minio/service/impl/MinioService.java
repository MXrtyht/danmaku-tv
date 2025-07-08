package cn.edu.scnu.danmakutv.minio.service.impl;

import io.minio.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MinioService {

    @Resource
    private MinioClient minioClient;

    /**
     * 上传文件到MinIO
     *
     * @param file       上传的文件
     * @param bucketName 桶名称
     * @return 上传后的文件名
     * @throws Exception 所有异常都抛出
     */
    public String uploadFile (MultipartFile file, String bucketName) throws Exception {

        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = generateUniqueFileName(originalFilename);

        // try-with-resources 确保 InputStream 在使用后被关闭
        try (InputStream inputStream = file.getInputStream()) {

            // 如果桶不存在，则创建
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                                                     .bucket(bucketName)
                                                     .build());
            }

            // 上传到指定桶
            minioClient.putObject(
                    PutObjectArgs.builder()
                                 .bucket(bucketName)
                                 .object(uniqueFileName)
                                 .stream(inputStream, file.getSize(), -1)
                                 .contentType(file.getContentType())
                                 .build()
            );
        }

        minioClient.close();
        return uniqueFileName;
    }

    /**
     * 下载文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @return file
     * @throws Exception 所有异常都抛出
     */
    public InputStream downloadFile (String bucketName, String objectName) throws Exception {

        // 下载文件
        InputStream file = minioClient.getObject(
                GetObjectArgs.builder()
                             .bucket(bucketName)
                             .object(objectName)
                             .build()
        );

        minioClient.close();
        return file;
    }

    /**
     * 删除文件
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @return 删除是否成功
     * @throws Exception 所有异常都抛出
     */
    public boolean deleteFile (String bucketName, String objectName) throws Exception {

        // 删除文件
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .build()
        );

        minioClient.close();
        return true;
    }

    // TODO: 分片上传 断点续传 秒传

    /**
     * 生成唯一的文件名
     *
     * @param originalFilename 原始文件名
     * @return 拼接uuid的文件名
     */
    private String generateUniqueFileName (String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    public void videoPlay (HttpServletRequest request, HttpServletResponse response,
                           String bucketName, String objectName) throws Exception {

        // 获取视频元信息
        StatObjectResponse metaInfo = minioClient.statObject(
                StatObjectArgs.builder()
                              .bucket(bucketName)
                              .object(objectName)
                              .build()
        );

        System.out.println("test: " + metaInfo.contentType() + " " + metaInfo.size() + " ");
        String contentType = metaInfo.contentType();

        Long fileSize = metaInfo.size();
        response.setHeader("Accept-Ranges", "bytes");

        Long startPos = 0L;
        Long endPos = fileSize - 1;
        if (contentType.split("/")[1].equals("mp4")) {

            String rangeHeader = request.getHeader("Range");
            if (!ObjectUtils.isEmpty(rangeHeader) && rangeHeader.startsWith("bytes=")) {

                try {
                    // 情景一：RANGE: bytes=2000070- 情景二：RANGE: bytes=2000070-2000970
                    String numRang = request.getHeader("Range").replaceAll("bytes=", "");
                    if (numRang.startsWith("-")) {
                        endPos = fileSize - 1;
                        startPos = endPos - Long.parseLong(new String(numRang.getBytes(StandardCharsets.UTF_8), 1,
                                numRang.length() - 1)) + 1;
                    } else if (numRang.endsWith("-")) {
                        endPos = fileSize - 1;
                        startPos = Long.parseLong(new String(numRang.getBytes(StandardCharsets.UTF_8), 0,
                                numRang.length() - 1));
                    } else {
                        String[] strRange = numRang.split("-");
                        if (strRange.length == 2) {
                            startPos = Long.parseLong(strRange[0].trim());
                            endPos = Long.parseLong(strRange[1].trim());
                        } else {
                            startPos = Long.parseLong(numRang.replaceAll("-", "").trim());
                        }
                    }

                    if (startPos < 0 || endPos < 0 || endPos >= fileSize || startPos > endPos) {
                        // SC 要求的范围不满足
                        response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                        return;
                    }

                    // 断点续传 状态码206
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                } catch (NumberFormatException e) {
                    // log.error(request.getHeader("Range") + " is not Number!");
                    startPos = 0L;
                }
            }
        }

        long rangLength = endPos - startPos + 1;
        response.setHeader("Content-Range", String.format("bytes %d-%d/%d", startPos, endPos, fileSize));
        response.addHeader("Content-Length", String.valueOf(rangLength));
        // response.setHeader("Connection", "keep-alive");
        response.addHeader("Content-Type", "video/mp4");

        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
             BufferedInputStream bis = new BufferedInputStream(
                     minioClient.getObject(GetObjectArgs
                             .builder()
                             .bucket(bucketName)
                             .object(objectName)
                             .offset(startPos)
                             .length(rangLength)
                             .build()))) {
            IOUtils.copy(bis, bos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
