
ALTER TABLE `t_finance_order_riskmanager_info` ADD COLUMN upstream_contract_status INT(1) DEFAULT '0' COMMENT '上游合同状态, 0:未填写,  2:已经提交';
ALTER TABLE `t_finance_order_riskmanager_info` ADD COLUMN downstream_contract_status INT(1) DEFAULT '0' COMMENT '下游合同状态, 0:未填写,  2:已经提交';