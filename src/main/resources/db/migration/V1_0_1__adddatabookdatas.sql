CREATE TABLE `t_finance_databook` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `sequence` int(3) DEFAULT NULL COMMENT '顺序，例如 1 代表 网站，2 代表 安卓， 3 代表 IOS等',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'transportmode', 1, '汽运' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'transportmode' AND sequence=1 AND name='汽运'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'transportmode', 2, '海运' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'transportmode' AND sequence=2 AND name='海运'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'transportmode', 3, '火运' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'transportmode' AND sequence=3 AND name='火运'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'transportmode', 4, '其它' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'transportmode' AND sequence=4 AND name='其它'
) LIMIT 1;