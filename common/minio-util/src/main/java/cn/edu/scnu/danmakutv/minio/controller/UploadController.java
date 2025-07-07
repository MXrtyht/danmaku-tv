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


    /**
     * 单点上传
     * @param file 上传的文件 前端: type = file 即可
     * @param bucketName 桶名称
     * @return 上传后的文件名
     * @throws Exception 所有异常都抛出
     */
    @PostMapping("/single")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("bucketName") String bucketName) throws Exception {
        return minioService.uploadFile(file, bucketName);
    }
}
