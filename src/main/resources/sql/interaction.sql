USE `danmaku-tv`;

DROP TABLE IF EXISTS `t_video_like`;
CREATE TABLE `t_video_like`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `video_id`    BIGINT UNSIGNED NOT NULL COMMENT '视频ID',
    `create_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频点赞表';