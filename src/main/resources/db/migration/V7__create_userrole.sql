CREATE TABLE `T_finance_user_role` (
  `id` int(11) NOT NULL,
  `T_finance_users_id` int(11) DEFAULT NULL,
  `T_finance_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '用户角色中间表';