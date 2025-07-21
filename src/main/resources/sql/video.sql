USE `danmaku-tv`;

DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '上传用户 ID',
    `video_url`   VARCHAR(255)    NOT NULL COMMENT '视频文件链接',
    `cover_url`   VARCHAR(255)    NOT NULL COMMENT '视频封面链接',
    `title`       VARCHAR(100)    NOT NULL COMMENT '视频标题',
    `type`        TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '视频类型 (0: 自制, 1: 转载)',
    `duration`    INT UNSIGNED    NOT NULL COMMENT '视频时长 (秒)',
    `area`        TINYINT(1)      NOT NULL COMMENT '视频分区',
    `description` TEXT                     DEFAULT NULL COMMENT '视频简介',
    `create_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '视频创建时间',
    `update_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '视频更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频表';

DROP TABLE IF EXISTS `r_video_tag`;
CREATE TABLE `r_video_tag`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `video_id` BIGINT UNSIGNED NOT NULL COMMENT '视频 ID',
    `tag_id`   BIGINT UNSIGNED NOT NULL COMMENT '标签 ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频标签关联表';

-- 视频标签表
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`
(
    `id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(50)     NOT NULL COMMENT '标签名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频标签表';

-- 视频观看表
DROP TABLE IF EXISTS `t_video_view`;
CREATE TABLE `t_video_view`
(
    `id`    bigint unsigned not null auto_increment comment '主键id',
    `video_id` BIGINT UNSIGNED NOT NULL COMMENT '视频 ID',
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '用户 ID',
    `client_id` varchar(500) character set utf8 collate utf8_general_ci default null comment '客户端id',
    `ip` varchar(50) character set utf8 collate utf8_general_ci default null comment 'ip',
    `create_at` datetime    default null comment '创建时间',
    primary key (`id`)
) ENGINE = InnoDB auto_increment=3 default charset =utf8mb4 comment ='视频观看表';

-- 视频分区表
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area`
(
    `id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(50)     NOT NULL COMMENT '分区名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频分区表';
