package cn.edu.scnu.danmakutv.minio.service;

import cn.edu.scnu.danmakutv.minio.entity.domain.UploadTask;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UploadService extends IService<UploadTask> {
    // 根据文件md5查找上传任务
    UploadTask getUploadTaskByMd5(String md5);
}
