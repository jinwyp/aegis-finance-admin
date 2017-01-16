INSERT INTO warehouse_company_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(0, 'Admin', '0', now(), '0', now());
INSERT INTO warehouse_company_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(1, 'Port', '0', now(), '0', now());
INSERT INTO warehouse_company_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(2, 'Supervise', '0', now(), '0', now());
INSERT INTO warehouse_company_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(3, 'Trafficker', '0', now(), '0', now());
INSERT INTO warehouse_company_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(4, 'FundProvider', '0', now(), '0', now());

INSERT INTO warehouse_company(name, status, status_id, role_number, role_name, remarks, create_man_id, create_time, last_update_man_id, last_update_time)
values('易煤网', 'Normal', 1, 0, '平台管理', '易煤网管理平台', '0', now(), '0', now());

INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(0, 'Admin', '0', now(), '0', now());
INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(1, 'Port', '0', now(), '0', now());
INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(2, 'Supervise', '0', now(), '0', now());
INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(3, 'Trafficker', '0', now(), '0', now());
INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(4, 'Trafficker_Finance', '0', now(), '0', now());
INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(5, 'FundProvider', '0', now(), '0', now());
INSERT INTO warehouse_user_role(number, role, create_man_id, create_time, last_update_man_id, last_update_time) values(6, 'FundProvider_Finance', '0', now(), '0', now());

INSERT INTO warehouse_user(username, password, name, email, role_number, role_name, company_id, company_name, company_role_name, status_id, status, create_man_id, create_time, last_update_man_id, last_update_time)
SELECT 'admin' as username, '3de13d80edbfb2f2523605252803deef' as password, '系统管理员' as name, 'liuxinjie@yimei180.com' as email,
0 as role_number, '平台管理员' as role_name,
id as company_id, '易煤网' as company_name, '平台管理' as company_role_name,
1 as status_id, 'Normal' as status,
'0' as create_man_id, now() as create_time, '0' as last_update_man_id, now() as last_update_time
FROM warehouse_company WHERE name='易煤网' and role_name='平台管理';

