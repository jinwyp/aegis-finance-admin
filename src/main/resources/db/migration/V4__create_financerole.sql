CREATE TABLE `T_finance_role` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '角色名',
  `comment` varchar(45) DEFAULT NULL COMMENT '备份描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '角色表';