DROP DATABASE IF EXISTS `danmaku-tv`;
CREATE DATABASE IF NOT EXISTS `danmaku-tv`
    CHARACTER SET utf8mb4;
USE `danmaku-tv`;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `phone`         CHAR(11)        NOT NULL COMMENT '手机号',
    `email`         VARCHAR(100)             DEFAULT NULL COMMENT '邮箱',
    `password`      VARCHAR(100)    NOT NULL COMMENT '密码',
    `salt`          VARCHAR(100)    NOT NULL COMMENT '加密盐',
    `is_banned`     TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '状态(0:正常, 1:封禁)',
    `last_login_at` DATETIME                 DEFAULT NULL COMMENT '最后登录时间',
    `create_at`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

DROP TABLE IF EXISTS `t_user_profiles`;
CREATE TABLE IF NOT EXISTS `t_user_profiles`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`          BIGINT UNSIGNED NOT NULL UNIQUE COMMENT '关联用户表 uid',
    `nickname`     VARCHAR(20)     NOT NULL COMMENT '昵称',
    `gender`       TINYINT(1)      NOT NULL DEFAULT 1 COMMENT '性别 (0:女, 1:男, 2:未知)',
    `birthday`     DATE            NOT NULL DEFAULT '1970-01-01' COMMENT '出生日期',
    `sign`         VARCHAR(100)             DEFAULT NULL COMMENT '个性签名',
    `announcement` TEXT                     DEFAULT NULL COMMENT '主页公告',
    `avatar`       VARCHAR(255)             DEFAULT NULL COMMENT '头像 URL',
    `coin`         INT UNSIGNED    NOT NULL DEFAULT 10 COMMENT '硬币数',
    `created_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';