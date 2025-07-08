package cn.edu.scnu.danmakutv.minio.service.impl;

import io.minio.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
}
