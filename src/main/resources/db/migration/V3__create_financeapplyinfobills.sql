CREATE TABLE `T_finance_apply_info_bills` (
  `id` int(11) NOT NULL,
  `T_finance_apply_info_id` int(11) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '申请信息账单表';