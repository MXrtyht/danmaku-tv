package cn.edu.scnu.danmakutv.minio.controller;

import cn.edu.scnu.danmakutv.minio.service.impl.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件处理相关接口")
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private MinioService minioService;


    /**
     * 单点上传
     *
     * @param file       上传的文件 前端: type = file 即可
     * @param bucketName 桶名称
     * @return 上传后的文件名
     * @throws Exception 所有异常都抛出
     */
    @Operation(
            summary = "单点上传",
            description = "上传单个文件到指定的桶中"
    )
    @PostMapping("/single")
    public String uploadFile (
            @Parameter(description = "要上传的文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "指定的桶的名称") @RequestParam("bucketName") String bucketName
    ) throws Exception {
        return minioService.uploadFile(file, bucketName);
    }

    /**
     * 分段读取视频流(弃用)
     *
     * @param request    请求对象
     * @param response   响应对象
     * @param bucketName 视频所在桶的位置
     * @param objectName 视频的文件名
     * @throws Exception 所有异常都抛出
     */
    @GetMapping(value = "/play/{bucketName}/{objectName}")
    public void videoPlay (HttpServletRequest request, HttpServletResponse response,
                           @PathVariable(value = "bucketName") String bucketName,
                           @PathVariable(value = "objectName") String objectName
    ) throws Exception {
        minioService.videoPlay(request, response, bucketName, objectName);
    }

    /**
     * 分片视频流处理(对上面videoPlay的封装)
     *
     * @param request    请求对象
     * @param response   响应对象
     * @param bucketName 视频所在桶的位置
     * @param objectName 视频的文件名
     * @throws Exception 所有异常都抛出
     */
    @Operation(
            summary = "分片视频流处理"
    )
    @GetMapping("/video-slice/{bucketName}/{objectName}")
    public void videoSlice (
            @Parameter(description = "请求") HttpServletRequest request,
            @Parameter(description = "响应") HttpServletResponse response,
            @Parameter(description = "桶名称") @PathVariable("bucketName") String bucketName,
            @Parameter(description = "视频名称") @PathVariable("objectName") String objectName
    ) throws Exception {
        minioService.videoSlice(request, response, bucketName, objectName);
    }
}
