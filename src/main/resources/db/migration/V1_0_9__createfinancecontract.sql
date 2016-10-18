
CREATE TABLE `t_finance_order_contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_man_id` varchar(255) DEFAULT NULL COMMENT '创建人userId',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_man_id` varchar(255) DEFAULT NULL COMMENT '最后一次更新人userId',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `attachment_names` varchar(520) DEFAULT NULL COMMENT '附件名称',
  `attachment_number` int(11) DEFAULT NULL COMMENT '附件个数',
  `buyer_bank_account` varchar(120) DEFAULT NULL COMMENT '买家银行账号',
  `buyer_bank_name` varchar(120) DEFAULT NULL COMMENT '买家开户行名称',
  `buyer_company_address` varchar(220) DEFAULT NULL COMMENT '买家公司地址',
  `buyer_company_name` varchar(120) DEFAULT NULL COMMENT '买家公司名称',
  `buyer_legal_person` varchar(60) DEFAULT NULL COMMENT '买家公司法人',
  `buyer_linkman_email` varchar(120) DEFAULT NULL COMMENT '买家联系人邮箱',
  `buyer_linkman_name` varchar(60) DEFAULT NULL COMMENT '买家联系人姓名',
  `buyer_linkman_phone` varchar(50) DEFAULT NULL COMMENT '买家联系人手机号',
  `buyer_settlement_money` decimal(20,2) DEFAULT NULL COMMENT '买家结算金额',
  `cash_deposit` decimal(20,2) DEFAULT NULL COMMENT '保证金',
  `coal_amount` decimal(12,2) DEFAULT NULL COMMENT '煤炭数量  ',
  `coal_index` text DEFAULT NULL COMMENT '煤炭指标  ',
  `coal_ton` decimal(12,2) DEFAULT NULL COMMENT '煤炭吨数',
  `coal_type` varchar(120) DEFAULT NULL COMMENT '煤炭品种,种类',
  `contract_no` varchar(100) NOT NULL COMMENT '合同编号',
  `delivery_place` varchar(220) DEFAULT NULL COMMENT '交货地点  ',
  `finance_id` bigint(20) NOT NULL COMMENT '金融申请单id',
  `payment_period` int(11) DEFAULT NULL COMMENT '付款提货期限',
  `purchase_place` varchar(220) DEFAULT NULL COMMENT '购货地点  ',
  `quality_acceptance_criteria` text DEFAULT NULL COMMENT '质量验收标准  ',
  `quality_remark` text DEFAULT NULL COMMENT '质量备注/质量说明  ',
  `quantity_acceptance_criteria` text DEFAULT NULL COMMENT '数量验收标准',
  `quantity_remark` text DEFAULT NULL COMMENT '数量备注/数量说明',
  `seller_bank_account` varchar(120) DEFAULT NULL COMMENT '卖家银行账号',
  `seller_bank_name` varchar(120) DEFAULT NULL COMMENT '卖家开户行名称',
  `seller_company_address` varchar(220) DEFAULT NULL COMMENT '卖家公司地址',
  `seller_company_name` varchar(120) DEFAULT NULL COMMENT '卖家公司名称',
  `seller_legal_person` varchar(60) DEFAULT NULL COMMENT '卖家公司法人',
  `seller_linkman_email` varchar(120) DEFAULT NULL COMMENT '卖家联系人邮箱',
  `seller_linkman_name` varchar(60) DEFAULT NULL COMMENT '卖家联系人姓名',
  `seller_linkman_phone` varchar(50) DEFAULT NULL COMMENT '卖家联系人手机号',
  `seller_receipt_amount` decimal(12,2) DEFAULT NULL COMMENT '卖家开票吨数',
  `seller_receipt_money` decimal(20,2) DEFAULT NULL COMMENT '卖家开票金额  ',
  `seller_receipt_price` decimal(10,2) DEFAULT NULL COMMENT '卖家开票价格',
  `settlement_amount` decimal(12,2) DEFAULT NULL COMMENT '结算吨数',
  `settlement_price` decimal(10,2) DEFAULT NULL COMMENT '结算价格',
  `ship_name` varchar(120) DEFAULT NULL COMMENT '船名',
  `ship_no` varchar(120) DEFAULT NULL COMMENT '船次',
  `sign_date` date DEFAULT NULL COMMENT '签约日期',
  `sign_place` varchar(220) DEFAULT NULL COMMENT '签约地点',
  `special_remark` text DEFAULT NULL COMMENT '特别约定,特殊说明  ',
  `type` int(11) NOT NULL COMMENT '合同类型',
  `type_name` VARCHAR(100) NOT NULL COMMENT '合同类型Name',
  `unload_place` varchar(220) DEFAULT NULL COMMENT '卸货地点',
  `unload_place_short` varchar(220) DEFAULT NULL COMMENT '卸货地点简称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `contractnumber` (`contract_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='金融申-合同表';

