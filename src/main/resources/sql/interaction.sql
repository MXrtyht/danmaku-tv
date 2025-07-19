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

DROP TABLE IF EXISTS `t_video_collection`;
CREATE TABLE `t_video_collection`
(
    `id`         bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `videoId`    bigint   DEFAULT NULL COMMENT '视频投稿id',
    `userId`     bigint   DEFAULT NULL COMMENT '用户id',
    `groupId`    bigint   DEFAULT NULL COMMENT '收藏分组id',
    `createTime` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT=5
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='视频收藏';

DROP TABLE IF EXISTS `t_video_coin`;
CREATE TABLE `t_video_coin`
(
    `id`         bigint NOT NULL AUTO_INCREMENT COMMENT '视频投稿id',
    `userId`     bigint   DEFAULT NULL COMMENT '用户id',
    `videoId`    bigint   DEFAULT NULL COMMENT '视频投稿id',
    `amount`     int DEFAULT NULL COMMENT '投币数',
    `createTime` datetime DEFAULT NULL COMMENT '创建时间',
    `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT=3
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='视频投币';

DROP TABLE IF EXISTS `t_user_coin`;
CREATE TABLE `t_user_coin`
(
    `id`         bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `userId`     bigint   DEFAULT NULL COMMENT '用户id',
    `amount`     int DEFAULT NULL COMMENT '硬币总数',
    `createTime` datetime DEFAULT NULL COMMENT '创建时间',
    `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT=3
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户硬币数';

DROP TABLE IF EXISTS `t_video_comment`;
CREATE TABLE `t_video_comment`
(
    `id`         bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `videoId`    bigint NOT NULL COMMENT '视频id',
    `userId`     bigint NOT NULL COMMENT '用户id',
    `comment`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论',
    `replyUserId` bigint DEFAULT NULL COMMENT '回复用户id',
    `rootId`     bigint DEFAULT NULL COMMENT '根节点评论id',
    `createTime` datetime DEFAULT NULL COMMENT '创建时间',
    `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT=7
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频评论表';