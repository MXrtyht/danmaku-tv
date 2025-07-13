package cn.edu.scnu.danmakutv.minio.controller;

import cn.edu.scnu.danmakutv.minio.service.impl.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件处理相关接口")
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
    @ApiOperation(
            value = "单点上传",
            notes = "上传单个文件到指定的桶中"
    )
    @PostMapping("/single")
    public String uploadFile (@RequestParam("file") MultipartFile file,
                              @RequestParam("bucketName") String bucketName) throws Exception {
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
                           @PathVariable(value = "objectName") String objectName) throws Exception {
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
    @ApiOperation(
            value = "分片视频流处理"
    )
    @GetMapping("/video-slice/{bucketName}/{objectName}")
    public void videoSlice (HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("bucketName") String bucketName,
                            @PathVariable("objectName") String objectName) throws Exception {
        minioService.videoSlice(request, response, bucketName, objectName);
    }
}
