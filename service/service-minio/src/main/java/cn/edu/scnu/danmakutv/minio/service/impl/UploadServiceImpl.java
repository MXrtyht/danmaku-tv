package cn.edu.scnu.danmakutv.minio.service.impl;

import cn.edu.scnu.danmakutv.minio.entity.domain.UploadTask;
import cn.edu.scnu.danmakutv.minio.mapper.UploadTaskMapper;
import cn.edu.scnu.danmakutv.minio.service.UploadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl extends ServiceImpl<UploadTaskMapper, UploadTask> implements UploadService {

    /**
     * 根据MD5获取上传任务
     *
     * @param md5 上传文件的MD5值
     * @return 返回对应的上传任务，如果不存在则返回null
     */
    @Override
    public UploadTask getUploadTaskByMd5 (String md5) {
        return baseMapper.selectOne(
                new QueryWrapper<UploadTask>().eq("md5", md5)
        );
    }
}
