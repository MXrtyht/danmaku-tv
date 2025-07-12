USE `danmaku-tv`;

DROP TABLE IF EXISTS `t_video_like`;
CREATE TABLE `t_video_like`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `video_id`  BIGINT UNSIGNED NOT NULL COMMENT '视频ID',
    `create_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频点赞表';

DROP TABLE IF EXISTS `t_danmaku`;
CREATE TABLE `t_danmaku`
(
    `id`        BIGINT UNSIGNED                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED                                       NOT NULL COMMENT '用户ID',
    `video_id`  BIGINT UNSIGNED                                       NOT NULL COMMENT '视频ID',
    `content`   TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '弹幕内容',
    `create_at` DATETIME                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  DEFAULT CHARSET = utf8mb4 COMMENT ='弹幕表';