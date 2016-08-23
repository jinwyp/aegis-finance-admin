INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'financedepartment', 1, '线上交易部' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'financedepartment' AND sequence=1 AND name='线上交易部'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'financedepartment', 2, '业务部' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'financedepartment' AND sequence=2 AND name='业务部'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'financedepartment', 3, '监管部' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'financedepartment' AND sequence=3 AND name='监管部'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'financedepartment', 4, '尽调部' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'financedepartment' AND sequence=4 AND name='尽调部'
) LIMIT 1;

INSERT INTO t_finance_databook (type, sequence, name)
SELECT 'financedepartment', 5, '风控部' FROM dual
WHERE NOT EXISTS (
    SELECT type, sequence, name FROM t_finance_databook WHERE type = 'financedepartment' AND sequence=5 AND name='风控部'
) LIMIT 1;