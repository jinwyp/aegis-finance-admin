ALTER TABLE t_finance_order_investigator_info modify `business_risk_point` TEXT DEFAULT NULL COMMENT '业务风险点';

ALTER TABLE t_finance_order_investigator_info modify `performance_credit_ability_eval` TEXT DEFAULT NULL COMMENT '履约信用及能力评估';

ALTER TABLE t_finance_order_investigator_info modify `final_conclusion` TEXT DEFAULT NULL COMMENT '综合意见,最终结论';

ALTER TABLE t_finance_order_investigator_info modify `supply_material_introduce` TEXT DEFAULT NULL COMMENT '补充材料说明';
