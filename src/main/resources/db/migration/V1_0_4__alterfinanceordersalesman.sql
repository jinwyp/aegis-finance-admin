ALTER TABLE t_finance_order_salesman_info modify `contract_companies_info_supply` TEXT DEFAULT NULL COMMENT '上下游签约单位信息补充';

ALTER TABLE t_finance_order_salesman_info modify `business_model_introduce` TEXT DEFAULT NULL COMMENT '业务操作模式介绍';

ALTER TABLE t_finance_order_salesman_info modify `logistics_storage_info_supply` TEXT DEFAULT NULL COMMENT '物流仓储信息补充';

ALTER TABLE t_finance_order_salesman_info modify `other_info_supply` TEXT DEFAULT NULL COMMENT '是否需要补充材料';

ALTER TABLE t_finance_order_salesman_info modify `supply_material_introduce` TEXT DEFAULT NULL COMMENT '补充材料说明';