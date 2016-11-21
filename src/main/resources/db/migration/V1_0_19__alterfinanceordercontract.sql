
ALTER TABLE `t_finance_order_contract` ADD COLUMN seller_receipt_money_capital VARCHAR(120) DEFAULT NULL COMMENT '卖家开票金额大写' AFTER seller_receipt_money;
ALTER TABLE `t_finance_order_contract` ADD COLUMN buyer_settlement_money_capital VARCHAR(120) DEFAULT NULL COMMENT '买家结算金额大写' AFTER buyer_settlement_money;
ALTER TABLE `t_finance_order_contract` ADD COLUMN cash_deposit_capital VARCHAR(120) DEFAULT NULL COMMENT '保证金金额大写' AFTER cash_deposit;