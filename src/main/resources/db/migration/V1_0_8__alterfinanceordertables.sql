ALTER TABLE t_finance_order modify `coal_quantity_index` TEXT DEFAULT NULL COMMENT '主要煤质指标';

ALTER TABLE t_finance_order_investigator_info modify `historical_cooperation_detail` TEXT DEFAULT NULL COMMENT '历史合作情况';

ALTER TABLE t_finance_order_investigator_info modify `main_business_information` TEXT DEFAULT NULL COMMENT '业务主要信息';

ALTER TABLE t_finance_order_investigator_info modify `business_transfer_info` TEXT DEFAULT NULL COMMENT '业务流转信息';

ALTER TABLE t_finance_order_riskmanager_info modify `payment_situation_eval` TEXT DEFAULT NULL COMMENT '预计回款情况';

ALTER TABLE t_finance_order_supervisor_info modify `historical_cooperation_detail` TEXT DEFAULT NULL COMMENT '历史合作情况';

