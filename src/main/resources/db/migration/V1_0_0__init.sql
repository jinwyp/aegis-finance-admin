CREATE TABLE `T_finance_apply_info` (
  `id` int(11) NOT NULL COMMENT '主键',
  `userId` int(11) DEFAULT NULL COMMENT '用户id ',
  `applyType` varchar(45) DEFAULT NULL COMMENT '申请类型：\n煤易融：MYR\n煤易贷:   MYD\n煤易购:   MYG',
  `financingAmount` decimal(10,2) DEFAULT NULL COMMENT '拟融资金额（单位：万元）',
  `expectedDate` int(11) DEFAULT NULL COMMENT '拟使用资金时间（单位：天）',
  `businessAmount` decimal(15,2) DEFAULT NULL COMMENT '预期此笔业务量（单位：万吨）',
  `transportMode` varchar(45) DEFAULT NULL COMMENT '运输方式：\n海运\n汽运\n火运\n其他',
  `procurementPrice` decimal(10,2) DEFAULT NULL COMMENT '单吨采购价 (元/吨)',
  `upstreamResource` varchar(100) DEFAULT NULL COMMENT '上游资源方全称',
  `transferPort` varchar(100) DEFAULT NULL COMMENT '中转港口/地全称',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `contractors` varchar(100) DEFAULT NULL COMMENT '签约单位全称',
  `downstreamContractors` varchar(100) DEFAULT NULL COMMENT '下游签约单位全称',
  `terminalServer` varchar(100) DEFAULT NULL COMMENT '用煤终端',
  `sellingPrice` decimal(10,2) DEFAULT NULL COMMENT '预计单吨销售价 (元/吨)',
  `storageLocation` varchar(100) DEFAULT NULL COMMENT '煤炭仓储地',
  `coalSources` varchar(100) DEFAULT NULL COMMENT '煤炭来源',
  `marketPrice` decimal(10,2) DEFAULT NULL COMMENT '单吨市场报价（元／吨）',
  `approveState` varchar(30) DEFAULT NULL COMMENT '审批状态',
  `approveComments` varchar(200) DEFAULT NULL COMMENT '审批说明',
  `souceId` varchar(50) DEFAULT NULL COMMENT '流水号，编号',
  `applyDateTime` datetime DEFAULT NULL COMMENT '申请时间',
  `applyUserName` varchar(45) DEFAULT NULL COMMENT '申请人姓名',
  `applyCompanyName` varchar(45) DEFAULT NULL COMMENT '申请公司名称',
  `workFlowInstanceId` varchar(45) DEFAULT NULL COMMENT '工作流实例id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '申请信息表';

CREATE TABLE `T_finance_apply_info_bills` (
  `id` int(11) NOT NULL,
  `T_finance_apply_info_id` int(11) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '申请信息账单表';

CREATE TABLE `T_finance_role` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '角色名',
  `comment` varchar(45) DEFAULT NULL COMMENT '备份描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '角色表';

CREATE TABLE `T_finance_users` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(15) DEFAULT NULL COMMENT '密码',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  `department` varchar(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '用户表';

CREATE TABLE `T_financing_review_result` (
  `id` int(11) NOT NULL,
  `workFlowInstanceId` varchar(45) DEFAULT NULL COMMENT '工作流实例id',
  `reviewResult` varchar(2000) DEFAULT NULL COMMENT '结果json内容',
  `isPassed` tinyint(1) DEFAULT NULL COMMENT '是否通过',
  `nodeName` varchar(50) DEFAULT NULL COMMENT '节点名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '节点审核结果表';

CREATE TABLE `T_finance_user_role` (
  `id` int(11) NOT NULL,
  `T_finance_users_id` int(11) DEFAULT NULL,
  `T_finance_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '用户角色中间表';