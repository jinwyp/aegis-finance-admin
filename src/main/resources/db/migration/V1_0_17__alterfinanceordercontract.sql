
ALTER TABLE `t_finance_order_contract` ADD COLUMN coal_index_ncv INT(5) unsigned DEFAULT NULL COMMENT '低位热值';
ALTER TABLE `t_finance_order_contract` ADD COLUMN coal_index_rs DECIMAL(4,2) NOT NULL COMMENT '收到基硫分';