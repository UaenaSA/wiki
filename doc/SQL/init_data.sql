
-- 初始数据
DELETE FROM sys_menu;
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('2', '1', '管理员列表', 'modules/sys/user.html', NULL, '1', 'fa fa-user', '1');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('3', '1', '角色管理', 'modules/sys/role.html', NULL, '1', 'fa fa-user-secret', '2');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', NULL, '1', 'fa fa-th-list', '3');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('15', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('16', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('17', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('18', '2', '删除', NULL, 'sys:user:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('19', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('20', '3', '新增', NULL, 'sys:role:save,sys:menu:list', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('21', '3', '修改', NULL, 'sys:role:update,sys:menu:list', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('22', '3', '删除', NULL, 'sys:role:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('23', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('24', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('25', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('26', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');

DELETE FROM sys_user;
INSERT INTO sys_user VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'admin@microcore.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
INSERT INTO sys_user VALUES (2, '用户1', '0bd40f1647ce8bdb6764553b81aa3f0826172e3fc8205c15454cc0055b5d5dd7', 'cUMo3s59uwdaBLAoWcOV', '1@microcore.tean', NULL, 1, 1, '2018-6-2 11:12:36');
INSERT INTO sys_user VALUES (3, '用户2', 'cd5ffb087f4ad33563827f555ccb5e2278c41d45da833f899b0e611a8eacfea9', 'UquJ9haiOWYqH3ClKvv3', '1@microcore.tean', NULL, 1, 1, '2018-6-2 11:13:22');
INSERT INTO sys_user VALUES (4, '用户3', '0e9ec3fd4e163847a85b3a6384acbb5fcb1d221876983ee165a5d70d49cb9bf2', 'J7COV30AZqUc23bQVqb4', '1@microcore.tean', NULL, 1, 1, '2018-6-2 11:13:52');
INSERT INTO sys_user VALUES (5, '操作员1', 'e560e01ac018d1665d856650d8edfab28bc0b7b87562f48295ec07fcf4e68287', '5JrsAvLKxw3CYmK0MFEY', '1@microcore.tean', NULL, 1, 1, '2018-6-2 11:15:08');
INSERT INTO sys_user VALUES (6, '操作员2', '4ad37623d48fb33cf4c68cd8db9501415788bb60c6f30bcb8799dd1ff94063fd', 'T7HkZXfGmyhk2rJzRNa1', '1@microcore.tech', NULL, 1, 1, '2018-6-5 17:18:13');
INSERT INTO sys_user VALUES (7, '管理员', 'cffe7c53ebedea25865c2fe1c5febd614d091c7b88cf772e8009e95fe813f37c', 'y2uy5AmFjx4YUMBwnmkP', '1@qq.com', NULL, 1, 1, '2018-6-5 09:21:35');

DELETE FROM sys_role;
INSERT INTO sys_role VALUES (1, '管理员', NULL, 1, '2018-5-29 09:33:12');
INSERT INTO sys_role VALUES (2, '操作员', NULL, 1, '2018-5-29 09:33:04');
INSERT INTO sys_role VALUES (3, '用户', NULL, 1, '2018-5-29 09:32:57');

DELETE FROM sys_user_role;
INSERT INTO sys_user_role VALUES (1, 2, 3);
INSERT INTO sys_user_role VALUES (2, 3, 3);
INSERT INTO sys_user_role VALUES (3, 4, 3);
INSERT INTO sys_user_role VALUES (4, 5, 2);
INSERT INTO sys_user_role VALUES (5, 6, 2);
INSERT INTO sys_user_role VALUES (6, 7, 1);

DELETE FROM sys_department;
INSERT INTO sys_department VALUES (1, '软件系统事业部');

DELETE FROM sys_user_department;
INSERT INTO sys_user_department VALUES (1, 2, 1);
INSERT INTO sys_user_department VALUES (2, 3, 1);
INSERT INTO sys_user_department VALUES (3, 4, 1);
INSERT INTO sys_user_department VALUES (4, 5, 1);
INSERT INTO sys_user_department VALUES (5, 6, 1);



