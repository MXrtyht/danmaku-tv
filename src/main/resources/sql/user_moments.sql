CREATE TABLE `t_user_moments` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
`userId` bigint DEFAULT NULL COMMENT '用户id',
`description` varchar(16) DEFAULT NULL COMMENT '动态描述',
`type` varchar(5) DEFAULT NULL COMMENT '动态类型：0视频 1直播 2动态专栏',
`contentId` bigint DEFAULT NULL COMMENT '内容详情id',
`createTime` datetime DEFAULT NULL COMMENT '创建时间',
`updateTime` datetime DEFAULT NULL COMMENT '更新时间',
PRIMARY KEY(`id`),
FOREIGN KEY(`contentId`) REFERENCES t_video(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户动态表';