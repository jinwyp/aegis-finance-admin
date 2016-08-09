CREATE TABLE `T_finance_users` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(15) DEFAULT NULL COMMENT '密码',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  `department` varchar(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '用户表';