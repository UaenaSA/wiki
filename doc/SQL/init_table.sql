# mysql -uroot -p
# 密码 123456

DROP TABLE IF EXISTS sys_department;

DROP TABLE IF EXISTS sys_user_department;

DROP TABLE IF EXISTS sys_log;

DROP TABLE IF EXISTS sys_menu;

DROP TABLE IF EXISTS sys_user;

DROP TABLE IF EXISTS sys_user_token;

DROP TABLE IF EXISTS sys_role;

DROP TABLE IF EXISTS sys_user_role;

DROP TABLE IF EXISTS sys_role_menu;

DROP TABLE IF EXISTS sys_config;

-- 菜单
CREATE TABLE sys_menu (
  menu_id   BIGINT NOT NULL AUTO_INCREMENT,
  parent_id BIGINT COMMENT '父菜单ID，一级菜单为0',
  name      VARCHAR(50) COMMENT '菜单名称',
  url       VARCHAR(200) COMMENT '菜单URL',
  perms     VARCHAR(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  type      INT COMMENT '类型   0：目录   1：菜单   2：按钮',
  icon      VARCHAR(50) COMMENT '菜单图标',
  order_num INT COMMENT '排序',
  PRIMARY KEY (menu_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '菜单管理';

-- 系统用户
CREATE TABLE sys_user (
  user_id        BIGINT      NOT NULL AUTO_INCREMENT,
  username       VARCHAR(50) NOT NULL
  COMMENT '用户名',
  password       VARCHAR(100) COMMENT '密码',
  salt           VARCHAR(20) COMMENT '盐',
  email          VARCHAR(100) COMMENT '邮箱',
  mobile         VARCHAR(100) COMMENT '手机号',
  status         TINYINT COMMENT '状态  0：禁用   1：正常',
  create_user_id BIGINT(20) COMMENT '创建者ID',
  create_time    DATETIME COMMENT '创建时间',
  PRIMARY KEY (user_id),
  UNIQUE INDEX (username)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '系统用户';

-- 系统用户Token
CREATE TABLE sys_user_token (
  user_id     BIGINT(20)   NOT NULL,
  token       VARCHAR(100) NOT NULL
  COMMENT 'token',
  expire_time DATETIME DEFAULT NULL
  COMMENT '过期时间',
  update_time DATETIME DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (user_id),
  UNIQUE KEY token (token)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '系统用户Token';

-- 角色
CREATE TABLE sys_role (
  role_id        BIGINT NOT NULL AUTO_INCREMENT,
  role_name      VARCHAR(100) COMMENT '角色名称',
  remark         VARCHAR(100) COMMENT '备注',
  create_user_id BIGINT(20) COMMENT '创建者ID',
  create_time    DATETIME COMMENT '创建时间',
  PRIMARY KEY (role_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '角色';

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id      BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT COMMENT '用户ID',
  role_id BIGINT COMMENT '角色ID',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户与角色对应关系';

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id      BIGINT NOT NULL AUTO_INCREMENT,
  role_id BIGINT COMMENT '角色ID',
  menu_id BIGINT COMMENT '菜单ID',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '角色与菜单对应关系';

-- 系统配置信息
CREATE TABLE sys_config (
  id       BIGINT NOT NULL AUTO_INCREMENT,
  `key`    VARCHAR(50) COMMENT 'key',
  `value`  VARCHAR(2000) COMMENT 'value',
  `status` TINYINT         DEFAULT 1
  COMMENT '状态   0：隐藏   1：显示',
  remark   VARCHAR(500) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE INDEX (`key`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8
  COMMENT = '系统配置信息表';

-- 系统日志
CREATE TABLE sys_log (
  id          BIGINT(20) NOT NULL AUTO_INCREMENT,
  username    VARCHAR(50) COMMENT '用户名',
  operation   VARCHAR(50) COMMENT '用户操作',
  method      VARCHAR(200) COMMENT '请求方法',
  params      VARCHAR(5000) COMMENT '请求参数',
  time        BIGINT     NOT NULL
  COMMENT '执行时长(毫秒)',
  ip          VARCHAR(64) COMMENT 'IP地址',
  create_date DATETIME COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8
  COMMENT = '系统日志';

/*==============================================================*/
/* Table: sys_user_department                             */
/*==============================================================*/
CREATE TABLE sys_user_department
(
  user_department_id BIGINT NOT NULL
  COMMENT '用户部门关联id',
  user_id            BIGINT NOT NULL
  COMMENT '用户id',
  department_id      BIGINT NOT NULL
  COMMENT '部门id',
  PRIMARY KEY (user_department_id)
);

/*==============================================================*/
/* Table: sys_department                             */
/*==============================================================*/
CREATE TABLE sys_department
(
  department_id BIGINT NOT NULL
  COMMENT '部门标示',
  department    VARCHAR(40) COMMENT '部门',
  PRIMARY KEY (department_id)
);
