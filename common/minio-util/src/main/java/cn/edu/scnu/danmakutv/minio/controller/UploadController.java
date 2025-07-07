package cn.edu.scnu.danmakutv.minio.controller;

import cn.edu.scnu.danmakutv.minio.service.impl.MinioService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private MinioService minioService;

    // 单点上传
    @PostMapping("/single")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("bucketName") String bucketName) throws Exception {
        return minioService.uploadFile(file, bucketName);
    }
}
