CREATE TABLE `warehouse_company` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL COMMENT '公司名称',
  `status` VARCHAR(30) NOT NULL COMMENT '状态',
  `status_id` INT(3) NOT NULL COMMENT '状态id',
  `remarks` VARCHAR(520) DEFAULT NULL COMMENT '备注',
  `create_man_id` varchar(255) NOT NULL COMMENT '创建人userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) NOT NULL COMMENT '最后一次更新人userId',
  `last_update_time` TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-公司表';


CREATE TABLE `warehouse_company_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `number` int(10) NOT NULL COMMENT '编号',
  `role` varchar(50) NOT NULL COMMENT '角色',
  `create_man_id` varchar(255) DEFAULT NULL COMMENT '创建人userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_man_id` VARCHAR(255) NOT NULL COMMENT '最后一次更新人userId',
  `last_update_time` TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-公司角色表';


CREATE TABLE `warehouse_company_role_relationship` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  `role_number` bigint(20) NOT NULL COMMENT '角色number',
  `create_man_id` varchar(255) NOT NULL COMMENT '创建人userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) NOT NULL COMMENT '最后一次更新人userId',
  `last_update_time` TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-公司角色-关系表';


CREATE TABLE `warehouse_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(60) NOT NULL COMMENT '账号',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `name` VARCHAR(60) DEFAULT NULL COMMENT '姓名',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(120) DEFAULT NULL COMMENT '邮箱',
  `company_id` BIGINT(20) NOT NULL COMMENT '公司id',
  `company_name` VARCHAR(120) NOT NULL COMMENT '公司name',
  `role_number` INT(3) NOT NULL COMMENT '角色number',
  `role_name` VARCHAR(30) NOT NULL COMMENT '角色名称',
  `status_id` INT(3) NOT NULL COMMENT '状态id',
  `status` VARCHAR(30) NOT NULL COMMENT '状态',
  `create_man_id` varchar(255) NOT NULL COMMENT '创建人userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) NOT NULL COMMENT '最后一次更新人userId',
  `last_update_time` TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-用户表';


CREATE TABLE `warehouse_user_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `create_time` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-用户登陆日志表';


CREATE TABLE `warehouse_user_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `number` int(10) NOT NULL COMMENT '编号',
  `role` varchar(50) NOT NULL COMMENT '角色',
  `create_man_id` varchar(255) NOT NULL COMMENT '创建人userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) NOT NULL COMMENT '最后一次更新人userId',
  `last_update_time` TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-用户角色表';
