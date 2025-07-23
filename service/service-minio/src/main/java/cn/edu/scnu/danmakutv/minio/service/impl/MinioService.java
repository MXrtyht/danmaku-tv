package cn.edu.scnu.danmakutv.minio.service.impl;

import cn.edu.scnu.common.utils.HttpUtil;
import io.minio.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MinioService {

    @Resource
    private MinioClient minioClient;

    @Resource
    @Value("${minio.endpoint}")
    private String minioUrl;

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

        // minioClient.close();
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

        // minioClient.close();
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

        // minioClient.close();
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

    @Deprecated
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

    /**
     * 分片视频流处理
     *
     * @param request    请求
     * @param response   响应
     * @param bucketName 视频所在桶的位置
     * @param objectName 视频的文件名
     * @throws Exception 所有异常都抛出
     */
    public void videoSlice (HttpServletRequest request, HttpServletResponse response,
                            String bucketName, String objectName) throws Exception {

        // 获取视频文件的元信息（包括 Content-Type、文件大小等）
        StatObjectResponse metaInfo = minioClient.statObject(
                StatObjectArgs.builder()
                              .bucket(bucketName)
                              .object(objectName)
                              .build()
        );

        // System.out.println("test: " + metaInfo.contentType() + " " + metaInfo.size());

        // 获取文件类型和大小
        String contentType = metaInfo.contentType();
        Long fileSize = metaInfo.size();

        // 收集所有请求头信息
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.put(header, request.getHeader(header));
        }

        // 获取客户端请求的 Range 字段
        String rangeStr = request.getHeader("Range");
        String[] range;

        // 如果没有指定 Range，则默认从头到尾
        if (rangeStr == null || StringUtils.isBlank(rangeStr)) {
            rangeStr = "bytes=0-" + (fileSize - 1);
        }

        // 解析 Range
        range = rangeStr.split("bytes=|-");

        Long beginPos = 0L;
        Long endPos = fileSize - 1;

        // 如果有起始位置
        if (range.length >= 2) {
            beginPos = Long.parseLong(range[1]);
        }

        // 如果有结束位置
        if (range.length >= 3) {
            endPos = Long.parseLong(range[2]);
        }

        // 计算实际请求的分片长度
        Long sliceLength = (endPos - beginPos) + 1;

        // 设置响应头
        String contentRange = "bytes " + beginPos + "-" + endPos + "/" + fileSize;
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Range", contentRange);
        response.setHeader("Content-Type", contentType);
        response.setContentLength(sliceLength.intValue());
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206

        // MinIO 文件 URL
        // String encodedObjectName = URLEncoder.encode(objectName, StandardCharsets.UTF_8);
        // String url = minioUrl + "/" + bucketName + "/" + objectName;
        String url = String.format("%s/%s/%s", minioUrl, bucketName, objectName);

        // 发起请求并将数据写入响应流
        HttpUtil.get(url, headers, response);
    }
}
