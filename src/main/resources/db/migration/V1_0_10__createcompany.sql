CREATE TABLE `t_finance_company` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL COMMENT '公司名称',
  `status` VARCHAR(30) NOT NULL COMMENT '状态',
  `status_id` INT(3) NOT NULL COMMENT '状态id',
  `create_man_id` varchar(255) DEFAULT NULL COMMENT '创建人userId',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) DEFAULT NULL COMMENT '最后一次更新人userId',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-公司表';


CREATE TABLE `t_finance_company_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `number` INT(10) NOT NULL COMMENT '编号',
  `role` VARCHAR(50) NOT NULL COMMENT '角色',
  `create_man_id` varchar(255) DEFAULT NULL COMMENT '创建人userId',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) DEFAULT NULL COMMENT '最后一次更新人userId',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-公司角色表';


CREATE TABLE `t_finance_company_role_relationship` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `company_id` BIGINT(20) NOT NULL COMMENT '公司id',
  `role_number` BIGINT(20) NOT NULL COMMENT '角色id',
  `role` VARCHAR(50) NOT NULL COMMENT '角色',
  `create_man_id` varchar(255) DEFAULT NULL COMMENT '创建人userId',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) DEFAULT NULL COMMENT '最后一次更新人userId',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-公司角色-关系表';

INSERT INTO t_finance_company_role(`number`, `role`, `create_man_id`, `create_time`, `last_update_man_id`, `last_update_time`) VALUES(1, 'RiskManager_Organization', '0', now(), '0', now());