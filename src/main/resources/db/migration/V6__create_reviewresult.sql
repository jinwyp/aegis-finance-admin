CREATE TABLE `T_financing_review_result` (
  `id` int(11) NOT NULL,
  `workFlowInstanceId` varchar(45) DEFAULT NULL COMMENT '工作流实例id',
  `reviewResult` varchar(2000) DEFAULT NULL COMMENT '结果json内容',
  `isPassed` tinyint(1) DEFAULT NULL COMMENT '是否通过',
  `nodeName` varchar(50) DEFAULT NULL COMMENT '节点名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '节点审核结果表';