CREATE TABLE `t_finance_company` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL COMMENT '公司名称',
  `status` VARCHAR(30) NOT NULL COMMENT '状态',
  `status_id` INT(3) NOT NULL COMMENT '状态id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-公司表';


CREATE TABLE `t_finance_company_fb_relationship` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `business_company_id` BIGINT(20) NOT NULL COMMENT '业务组织id',
  `fund_company_id` BIGINT(20) NOT NULL COMMENT '资金公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-资金公司-业务组织-关系表';


CREATE TABLE `t_finance_company_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `number` INT(10) NOT NULL COMMENT '编号',
  `role` VARCHAR(50) NOT NULL COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-公司角色表';


CREATE TABLE `t_finance_company_role_relationship` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `company_id` BIGINT(20) NOT NULL COMMENT '公司id',
  `role_number` BIGINT(20) NOT NULL COMMENT '角色id',
  `role` VARCHAR(50) NOT NULL COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融-公司角色-关系表';

INSERT INTO t_finance_company_role(`number`, `role`) VALUES(1, 'Business_Organization');
INSERT INTO t_finance_company_role(`number`, `role`) VALUES(2, 'Fund_Provider');