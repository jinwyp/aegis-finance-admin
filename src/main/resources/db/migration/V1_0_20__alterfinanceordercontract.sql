
ALTER TABLE `t_finance_order_contract` ADD COLUMN quantity_acceptance_basis DECIMAL(12, 2) DEFAULT NULL COMMENT '数量验收依据' AFTER quantity_acceptance_criteria;
ALTER TABLE `t_finance_order_contract` ADD COLUMN coal_supplier VARCHAR(120) DEFAULT NULL COMMENT '供货商';
ALTER TABLE `t_finance_order_contract` ADD COLUMN upstream_contract_no VARCHAR(10) DEFAULT NULL COMMENT '上有合同编号' AFTER contract_no;