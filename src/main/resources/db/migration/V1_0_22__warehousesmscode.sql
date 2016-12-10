CREATE TABLE `warehouse_sms_code` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(30) NOT NULL COMMENT '用户id',
  `phone` VARCHAR(255) NOT NULL COMMENT '手机号/邮箱',
  `code` VARCHAR(20) NOT NULL COMMENT '验证码',
  `type` INT(3) NOT NULL COMMENT '类型id',
  `status` INT(3) NOT NULL COMMENT '状态id',
  `expiration_time` datetime NOT NULL COMMENT '过期(失效)时间',
  `ip` VARCHAR(255) NOT NULL COMMENT 'ip地址',
  `create_man_id` varchar(255) NOT NULL COMMENT '创建人userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) NOT NULL COMMENT '最后一次更新人userId',
  `last_update_time` TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓押-短信验证码表';


