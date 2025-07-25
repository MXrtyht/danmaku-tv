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

DROP TABLE IF EXISTS `t_video_collection`;
CREATE TABLE `t_video_collection`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `video_id`  BIGINT UNSIGNED NOT NULL COMMENT '视频ID',
    `group_id`  BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '分组 ID',
    `create_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频收藏表';

DROP TABLE IF EXISTS `t_collection_group`;
CREATE TABLE `t_collection_group`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED COMMENT '用户ID',
    `name`      VARCHAR(50)     NOT NULL COMMENT '分组名称',
    `create_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频收藏分组表';

DROP TABLE IF EXISTS `t_video_comment`;
CREATE TABLE `t_video_comment`
(
    `id`        BIGINT UNSIGNED                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED                                       NOT NULL COMMENT '用户ID',
    `video_id`  BIGINT UNSIGNED                                       NOT NULL COMMENT '视频ID',
    `content`   TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
    `create_at` DATETIME                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频评论表';

DROP TABLE IF EXISTS `t_danmaku`;
CREATE TABLE `t_danmaku`
(
    `id`        BIGINT UNSIGNED                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED                                       NOT NULL COMMENT '用户ID',
    `video_id`  BIGINT UNSIGNED                                       NOT NULL COMMENT '视频ID',
    `content`   TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '弹幕内容',
    `video_time` BIGINT UNSIGNED            NOT NULL COMMENT '视频时间点 (秒)',
    `create_at` DATETIME                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  DEFAULT CHARSET = utf8mb4 COMMENT ='弹幕表';

