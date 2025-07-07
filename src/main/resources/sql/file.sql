USE `danmaku-tv`;

DROP TABLE IF EXISTS `t_upload_task`;
CREATE TABLE `t_upload_task`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uuid`        VARCHAR(128)    NOT NULL UNIQUE COMMENT 'uuid',
    `md5`         VARCHAR(128)    NOT NULL UNIQUE COMMENT '文件的md5码',
    `upload_id`   VARCHAR(128)    NOT NULL COMMENT 'minio上传任务的id',
    `total_chunk` INT UNSIGNED    NOT NULL COMMENT '分片数量',
    `name`        VARCHAR(255)    NOT NULL COMMENT '文件名',
    `size`        BIGINT          NOT NULL COMMENT '文件大小',
    `is_uploaded` TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '0 未完成, 1 完成',
    `create_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建上传任务时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='分片上传任务表';