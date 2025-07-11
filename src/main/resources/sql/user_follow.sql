USE `danmaku-tv`;

DROP TABLE IF EXISTS `t_user_follow`;
CREATE TABLE `t_user_follow`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED NOT NULL COMMENT '用户 ID',
    `follow_id` BIGINT UNSIGNED NOT NULL COMMENT '被关注用户 ID',
    `group_id`  BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '分组 ID',
    `create_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户关注表';
# group_id 设置为1时, 即为默认分组

DROP TABLE IF EXISTS `t_follow_group`;
CREATE TABLE `t_follow_group`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`   BIGINT UNSIGNED COMMENT '创建的用户 ID',
    `name`      VARCHAR(50)     NOT NULL COMMENT '分组名称',
    `create_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户关注分组表';
INSERT INTO `t_follow_group` (`id`, `name`) VALUE
    (1, '默认分组');