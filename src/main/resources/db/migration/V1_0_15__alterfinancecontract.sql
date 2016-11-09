ALTER TABLE t_finance_order_contract ADD `finance_type` varchar(20) NOT NULL COMMENT '申请类型';
ALTER TABLE t_finance_order_contract ADD `finance_source_id` varchar(100) NOT NULL COMMENT '金融单流水号，业务编号';
ALTER TABLE t_finance_order_contract ADD `apply_user_id` int(11) NOT NULL COMMENT '申请人userId';
ALTER TABLE t_finance_order_contract ADD `apply_user_name` varchar(50) DEFAULT NULL COMMENT '申请人姓名';
ALTER TABLE t_finance_order_contract ADD `apply_user_phone` varchar(50) NOT NULL COMMENT '申请人手机号';
ALTER TABLE t_finance_order_contract ADD `apply_company_id` BIGINT(20) DEFAULT NULL COMMENT '申请人公司id';
ALTER TABLE t_finance_order_contract ADD `apply_company_name` varchar(60) NOT NULL COMMENT '申请人公司名称';




