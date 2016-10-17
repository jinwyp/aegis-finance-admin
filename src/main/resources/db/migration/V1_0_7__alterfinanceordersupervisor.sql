ALTER TABLE t_finance_order_riskmanager_info modify `distribution_ability_eval` TEXT DEFAULT NULL COMMENT '分销能力评估';

ALTER TABLE t_finance_order_riskmanager_info modify `risk_control_scheme` TEXT DEFAULT NULL COMMENT '业务风险点';

ALTER TABLE t_finance_order_riskmanager_info modify `final_conclusion` TEXT DEFAULT NULL COMMENT '风控结论,最终结论,综合意见';

ALTER TABLE t_finance_order_riskmanager_info modify `supply_material_introduce` TEXT DEFAULT NULL COMMENT '补充材料说明';

ALTER TABLE t_finance_order_riskmanager_info modify `business_risk_point` TEXT DEFAULT NULL COMMENT '业务风险点';
