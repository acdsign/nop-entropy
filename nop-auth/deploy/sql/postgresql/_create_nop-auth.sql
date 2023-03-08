
CREATE TABLE nop_auth_ext_login(
  USER_ID VARCHAR(50) NOT NULL ,
  LOGIN_TYPE INT4 NOT NULL ,
  LOGIN_VALUE VARCHAR(50) NOT NULL ,
  DEL_FLAG INT4 NOT NULL ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_ext_login primary key (USER_ID,LOGIN_TYPE)
);

CREATE TABLE nop_auth_user_role(
  USER_ID VARCHAR(32) NOT NULL ,
  ROLE_ID VARCHAR(50) NOT NULL ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_user_role primary key (USER_ID,ROLE_ID)
);

CREATE TABLE nop_auth_user_substitution(
  SID VARCHAR(32) NOT NULL ,
  USER_ID VARCHAR(32) NOT NULL ,
  SUBSTITUTED_USER_ID VARCHAR(32) NOT NULL ,
  START_TIME TIMESTAMP  ,
  END_TIME TIMESTAMP  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_user_substitution primary key (SID)
);

CREATE TABLE nop_auth_role_resource(
  SID VARCHAR(32) NOT NULL ,
  ROLE_ID VARCHAR(50) NOT NULL ,
  RESOURCE_ID VARCHAR(100)  ,
  DEL_FLAG INT4  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_role_resource primary key (SID)
);

CREATE TABLE nop_auth_op_log(
  LOG_ID VARCHAR(32) NOT NULL ,
  USER_NAME VARCHAR(32) NOT NULL ,
  SESSION_ID VARCHAR(100)  ,
  TITLE VARCHAR(200)  ,
  BIZ_OBJ_NAME VARCHAR(100)  ,
  BIZ_ACTION_NAME VARCHAR(100)  ,
  OP_REQUEST TEXT  ,
  OP_RESPONSE VARCHAR(4000)  ,
  RESULT_STATUS INT4  ,
  ERROR_CODE VARCHAR(200)  ,
  USED_TIME INT8  ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  constraint PK_nop_auth_op_log primary key (LOG_ID)
);

CREATE TABLE nop_auth_tenant(
  TENANT_ID VARCHAR(32) NOT NULL ,
  NAME VARCHAR(100) NOT NULL ,
  BEGIN_TIME TIMESTAMP  ,
  END_TIME TIMESTAMP  ,
  STATUS INT4 NOT NULL ,
  DEL_FLAG INT4  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_tenant primary key (TENANT_ID)
);

CREATE TABLE nop_auth_user(
  USER_ID VARCHAR(50) NOT NULL ,
  USER_NAME VARCHAR(50) NOT NULL ,
  PASSWORD VARCHAR(80) NOT NULL ,
  SALT VARCHAR(32)  ,
  NICK_NAME VARCHAR(50) NOT NULL ,
  DEPT_ID VARCHAR(50)  ,
  OPEN_ID VARCHAR(32) NOT NULL ,
  GENDER INT4 NOT NULL ,
  AVATAR VARCHAR(100)  ,
  EMAIL VARCHAR(100)  ,
  EMAIL_VERIFIED INT4  ,
  PHONE VARCHAR(50)  ,
  PHONE_VERIFIED INT4 NOT NULL ,
  BIRTHDAY DATE  ,
  USER_TYPE INT4 NOT NULL ,
  STATUS INT4 NOT NULL ,
  ID_TYPE VARCHAR(10)  ,
  ID_NBR VARCHAR(100)  ,
  EXPIRE_AT TIMESTAMP  ,
  PWD_UPDATE_TIME TIMESTAMP  ,
  CHANGE_PWD_AT_LOGIN INT4  ,
  REAL_NAME VARCHAR(50)  ,
  WORK_NO VARCHAR(100)  ,
  POSITION_ID VARCHAR(32)  ,
  TELEPHONE VARCHAR(50)  ,
  CLIENT_ID VARCHAR(100)  ,
  DEL_FLAG INT4 NOT NULL ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_user primary key (USER_ID)
);

CREATE TABLE nop_auth_role(
  ROLE_ID VARCHAR(50) NOT NULL ,
  ROLE_NAME VARCHAR(50) NOT NULL ,
  CHILD_ROLE_IDS VARCHAR(500)  ,
  IS_PRIMARY INT4  ,
  DEL_FLAG INT4 NOT NULL ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_role primary key (ROLE_ID)
);

CREATE TABLE nop_auth_resource(
  RESOURCE_ID VARCHAR(100) NOT NULL ,
  SITE_ID VARCHAR(100) NOT NULL ,
  DISPLAY_NAME VARCHAR(100) NOT NULL ,
  ORDER_NO INT4 NOT NULL ,
  RESOURCE_TYPE VARCHAR(4) NOT NULL ,
  PARENT_ID VARCHAR(100)  ,
  ICON VARCHAR(150)  ,
  ROUTE_PATH VARCHAR(200)  ,
  URL VARCHAR(1000)  ,
  COMPONENT VARCHAR(200)  ,
  TARGET VARCHAR(50)  ,
  HIDDEN INT4 NOT NULL ,
  KEEP_ALIVE INT4 NOT NULL ,
  PERMISSIONS VARCHAR(200)  ,
  NO_AUTH INT4 NOT NULL ,
  DEPENDS VARCHAR(1000)  ,
  IS_LEAF INT4 NOT NULL ,
  STATUS INT4 NOT NULL ,
  Meta_CONFIG VARCHAR(1000)  ,
  PROPS_CONFIG VARCHAR(1000)  ,
  DEL_FLAG INT4 NOT NULL ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_resource primary key (RESOURCE_ID)
);

CREATE TABLE nop_auth_session(
  SESSION_ID VARCHAR(100) NOT NULL ,
  USER_ID VARCHAR(32) NOT NULL ,
  USER_NAME VARCHAR(100) NOT NULL ,
  LOGIN_ADDR VARCHAR(100)  ,
  LOGIN_DEVICE VARCHAR(100)  ,
  LOGIN_APP VARCHAR(100)  ,
  LOGIN_OS VARCHAR(100)  ,
  LOGIN_TIME TIMESTAMP NOT NULL ,
  LOGIN_TYPE INT4 NOT NULL ,
  LOGOUT_TIME TIMESTAMP  ,
  LOGOUT_TYPE INT4 NOT NULL ,
  LOGOUT_BY VARCHAR(100)  ,
  LAST_ACCESS_TIME TIMESTAMP  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_session primary key (SESSION_ID)
);

CREATE TABLE nop_auth_dept(
  DEPT_ID VARCHAR(50) NOT NULL ,
  DEPT_NAME VARCHAR(100) NOT NULL ,
  PARENT_ID VARCHAR(50)  ,
  ORDER_NUM INT4  ,
  DEPT_TYPE VARCHAR(10)  ,
  EMAIL VARCHAR(100)  ,
  PHONE VARCHAR(50)  ,
  DEL_FLAG INT4  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_dept primary key (DEPT_ID)
);

CREATE TABLE nop_auth_position(
  POSITION_ID VARCHAR(50) NOT NULL ,
  NAME VARCHAR(100) NOT NULL ,
  DEL_FLAG INT4  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_position primary key (POSITION_ID)
);

CREATE TABLE nop_auth_site(
  SITE_ID VARCHAR(100) NOT NULL ,
  DISPLAY_NAME VARCHAR(100) NOT NULL ,
  ORDER_NO INT4 NOT NULL ,
  URL VARCHAR(200)  ,
  STATUS INT4 NOT NULL ,
  EXT_CONFIG VARCHAR(1000)  ,
  CONFIG_VERSION VARCHAR(20)  ,
  VERSION INT4 NOT NULL ,
  CREATED_BY VARCHAR(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR(200)  ,
  constraint PK_nop_auth_site primary key (SITE_ID)
);


      COMMENT ON TABLE nop_auth_ext_login IS '扩展登录方式';
                
      COMMENT ON COLUMN nop_auth_ext_login.USER_ID IS '用户ID';
                    
      COMMENT ON COLUMN nop_auth_ext_login.LOGIN_TYPE IS '登录类型';
                    
      COMMENT ON COLUMN nop_auth_ext_login.LOGIN_VALUE IS '登录标识';
                    
      COMMENT ON COLUMN nop_auth_ext_login.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_ext_login.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_ext_login.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_ext_login.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_ext_login.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_ext_login.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_ext_login.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_user_role IS '用户角色';
                
      COMMENT ON COLUMN nop_auth_user_role.USER_ID IS '用户ID';
                    
      COMMENT ON COLUMN nop_auth_user_role.ROLE_ID IS '角色ID';
                    
      COMMENT ON COLUMN nop_auth_user_role.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_user_role.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_user_role.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_user_role.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_user_role.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_user_role.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_user_substitution IS '用户代理';
                
      COMMENT ON COLUMN nop_auth_user_substitution.SID IS '主键';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.USER_ID IS '用户ID';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.SUBSTITUTED_USER_ID IS '被代理的用户ID';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.START_TIME IS '开始时间';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.END_TIME IS '结束时间';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_user_substitution.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_role_resource IS '角色可访问资源';
                
      COMMENT ON COLUMN nop_auth_role_resource.SID IS '主键';
                    
      COMMENT ON COLUMN nop_auth_role_resource.ROLE_ID IS '角色ID';
                    
      COMMENT ON COLUMN nop_auth_role_resource.RESOURCE_ID IS '资源ID';
                    
      COMMENT ON COLUMN nop_auth_role_resource.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_role_resource.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_role_resource.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_role_resource.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_role_resource.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_role_resource.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_role_resource.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_op_log IS '操作日志';
                
      COMMENT ON COLUMN nop_auth_op_log.LOG_ID IS '主键';
                    
      COMMENT ON COLUMN nop_auth_op_log.USER_NAME IS '用户名';
                    
      COMMENT ON COLUMN nop_auth_op_log.SESSION_ID IS '会话ID';
                    
      COMMENT ON COLUMN nop_auth_op_log.TITLE IS '标题';
                    
      COMMENT ON COLUMN nop_auth_op_log.BIZ_OBJ_NAME IS '业务对象';
                    
      COMMENT ON COLUMN nop_auth_op_log.BIZ_ACTION_NAME IS '业务操作';
                    
      COMMENT ON COLUMN nop_auth_op_log.OP_REQUEST IS '请求参数';
                    
      COMMENT ON COLUMN nop_auth_op_log.OP_RESPONSE IS '响应数据';
                    
      COMMENT ON COLUMN nop_auth_op_log.RESULT_STATUS IS '操作状态';
                    
      COMMENT ON COLUMN nop_auth_op_log.ERROR_CODE IS '错误码';
                    
      COMMENT ON COLUMN nop_auth_op_log.USED_TIME IS '操作时长';
                    
      COMMENT ON COLUMN nop_auth_op_log.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_op_log.CREATE_TIME IS '创建时间';
                    
      COMMENT ON TABLE nop_auth_tenant IS '租户';
                
      COMMENT ON COLUMN nop_auth_tenant.TENANT_ID IS '主键';
                    
      COMMENT ON COLUMN nop_auth_tenant.NAME IS '名称';
                    
      COMMENT ON COLUMN nop_auth_tenant.BEGIN_TIME IS '开始时间';
                    
      COMMENT ON COLUMN nop_auth_tenant.END_TIME IS '结束时间';
                    
      COMMENT ON COLUMN nop_auth_tenant.STATUS IS '租户状态';
                    
      COMMENT ON COLUMN nop_auth_tenant.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_tenant.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_tenant.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_tenant.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_tenant.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_tenant.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_tenant.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_user IS '用户';
                
      COMMENT ON COLUMN nop_auth_user.USER_ID IS '用户ID';
                    
      COMMENT ON COLUMN nop_auth_user.USER_NAME IS '用户名';
                    
      COMMENT ON COLUMN nop_auth_user.PASSWORD IS '密码';
                    
      COMMENT ON COLUMN nop_auth_user.SALT IS '密码加盐';
                    
      COMMENT ON COLUMN nop_auth_user.NICK_NAME IS '昵称';
                    
      COMMENT ON COLUMN nop_auth_user.DEPT_ID IS '部门';
                    
      COMMENT ON COLUMN nop_auth_user.OPEN_ID IS '用户外部标识';
                    
      COMMENT ON COLUMN nop_auth_user.GENDER IS '性别';
                    
      COMMENT ON COLUMN nop_auth_user.AVATAR IS '头像';
                    
      COMMENT ON COLUMN nop_auth_user.EMAIL IS '邮件';
                    
      COMMENT ON COLUMN nop_auth_user.EMAIL_VERIFIED IS '电话已验证';
                    
      COMMENT ON COLUMN nop_auth_user.PHONE IS '电话';
                    
      COMMENT ON COLUMN nop_auth_user.PHONE_VERIFIED IS '电话已验证';
                    
      COMMENT ON COLUMN nop_auth_user.BIRTHDAY IS '生日';
                    
      COMMENT ON COLUMN nop_auth_user.USER_TYPE IS '用户类型';
                    
      COMMENT ON COLUMN nop_auth_user.STATUS IS '用户状态';
                    
      COMMENT ON COLUMN nop_auth_user.ID_TYPE IS '证件类型';
                    
      COMMENT ON COLUMN nop_auth_user.ID_NBR IS '证件号';
                    
      COMMENT ON COLUMN nop_auth_user.EXPIRE_AT IS '用户过期时间';
                    
      COMMENT ON COLUMN nop_auth_user.PWD_UPDATE_TIME IS '上次密码更新时间';
                    
      COMMENT ON COLUMN nop_auth_user.CHANGE_PWD_AT_LOGIN IS '登陆后立刻修改密码';
                    
      COMMENT ON COLUMN nop_auth_user.REAL_NAME IS '真实姓名';
                    
      COMMENT ON COLUMN nop_auth_user.WORK_NO IS '工号';
                    
      COMMENT ON COLUMN nop_auth_user.POSITION_ID IS '职务';
                    
      COMMENT ON COLUMN nop_auth_user.TELEPHONE IS '座机';
                    
      COMMENT ON COLUMN nop_auth_user.CLIENT_ID IS '设备ID';
                    
      COMMENT ON COLUMN nop_auth_user.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_user.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_user.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_user.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_user.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_user.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_user.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_role IS '角色';
                
      COMMENT ON COLUMN nop_auth_role.ROLE_ID IS '角色ID';
                    
      COMMENT ON COLUMN nop_auth_role.ROLE_NAME IS '角色名';
                    
      COMMENT ON COLUMN nop_auth_role.CHILD_ROLE_IDS IS '子角色';
                    
      COMMENT ON COLUMN nop_auth_role.IS_PRIMARY IS '是否主角色';
                    
      COMMENT ON COLUMN nop_auth_role.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_role.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_role.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_role.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_role.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_role.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_role.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_resource IS '菜单资源';
                
      COMMENT ON COLUMN nop_auth_resource.RESOURCE_ID IS '资源ID';
                    
      COMMENT ON COLUMN nop_auth_resource.SITE_ID IS '站点ID';
                    
      COMMENT ON COLUMN nop_auth_resource.DISPLAY_NAME IS '显示名称';
                    
      COMMENT ON COLUMN nop_auth_resource.ORDER_NO IS '排序';
                    
      COMMENT ON COLUMN nop_auth_resource.RESOURCE_TYPE IS '资源类型';
                    
      COMMENT ON COLUMN nop_auth_resource.PARENT_ID IS '父资源ID';
                    
      COMMENT ON COLUMN nop_auth_resource.ICON IS '图标';
                    
      COMMENT ON COLUMN nop_auth_resource.ROUTE_PATH IS '前端路由';
                    
      COMMENT ON COLUMN nop_auth_resource.URL IS '链接';
                    
      COMMENT ON COLUMN nop_auth_resource.COMPONENT IS '组件名';
                    
      COMMENT ON COLUMN nop_auth_resource.TARGET IS '链接目标';
                    
      COMMENT ON COLUMN nop_auth_resource.HIDDEN IS '是否隐藏';
                    
      COMMENT ON COLUMN nop_auth_resource.KEEP_ALIVE IS '隐藏时保持状态';
                    
      COMMENT ON COLUMN nop_auth_resource.PERMISSIONS IS '权限标识';
                    
      COMMENT ON COLUMN nop_auth_resource.NO_AUTH IS '不检查权限';
                    
      COMMENT ON COLUMN nop_auth_resource.DEPENDS IS '依赖资源';
                    
      COMMENT ON COLUMN nop_auth_resource.IS_LEAF IS '是否叶子节点';
                    
      COMMENT ON COLUMN nop_auth_resource.STATUS IS '状态';
                    
      COMMENT ON COLUMN nop_auth_resource.Meta_CONFIG IS '扩展配置';
                    
      COMMENT ON COLUMN nop_auth_resource.PROPS_CONFIG IS '组件属性';
                    
      COMMENT ON COLUMN nop_auth_resource.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_resource.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_resource.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_resource.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_resource.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_resource.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_resource.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_session IS '会话日志';
                
      COMMENT ON COLUMN nop_auth_session.SESSION_ID IS '会话ID';
                    
      COMMENT ON COLUMN nop_auth_session.USER_ID IS '用户ID';
                    
      COMMENT ON COLUMN nop_auth_session.USER_NAME IS '用户名';
                    
      COMMENT ON COLUMN nop_auth_session.LOGIN_ADDR IS '登录地址';
                    
      COMMENT ON COLUMN nop_auth_session.LOGIN_DEVICE IS '登录设备';
                    
      COMMENT ON COLUMN nop_auth_session.LOGIN_APP IS '应用程序';
                    
      COMMENT ON COLUMN nop_auth_session.LOGIN_OS IS '操作系统';
                    
      COMMENT ON COLUMN nop_auth_session.LOGIN_TIME IS '登录时间';
                    
      COMMENT ON COLUMN nop_auth_session.LOGIN_TYPE IS '登录方式';
                    
      COMMENT ON COLUMN nop_auth_session.LOGOUT_TIME IS '退出时间';
                    
      COMMENT ON COLUMN nop_auth_session.LOGOUT_TYPE IS '退出方式';
                    
      COMMENT ON COLUMN nop_auth_session.LOGOUT_BY IS '退出操作人';
                    
      COMMENT ON COLUMN nop_auth_session.LAST_ACCESS_TIME IS '最后访问时间';
                    
      COMMENT ON COLUMN nop_auth_session.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_session.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_session.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_session.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_dept IS '部门';
                
      COMMENT ON COLUMN nop_auth_dept.DEPT_ID IS '主键';
                    
      COMMENT ON COLUMN nop_auth_dept.DEPT_NAME IS '名称';
                    
      COMMENT ON COLUMN nop_auth_dept.PARENT_ID IS '父ID';
                    
      COMMENT ON COLUMN nop_auth_dept.ORDER_NUM IS '排序';
                    
      COMMENT ON COLUMN nop_auth_dept.DEPT_TYPE IS '类型';
                    
      COMMENT ON COLUMN nop_auth_dept.EMAIL IS '邮件';
                    
      COMMENT ON COLUMN nop_auth_dept.PHONE IS '电话';
                    
      COMMENT ON COLUMN nop_auth_dept.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_dept.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_dept.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_dept.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_dept.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_dept.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_dept.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_position IS '岗位';
                
      COMMENT ON COLUMN nop_auth_position.POSITION_ID IS '主键';
                    
      COMMENT ON COLUMN nop_auth_position.NAME IS '名称';
                    
      COMMENT ON COLUMN nop_auth_position.DEL_FLAG IS '删除标识';
                    
      COMMENT ON COLUMN nop_auth_position.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_position.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_position.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_position.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_position.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_position.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_auth_site IS '子站点';
                
      COMMENT ON COLUMN nop_auth_site.SITE_ID IS '站点ID';
                    
      COMMENT ON COLUMN nop_auth_site.DISPLAY_NAME IS '显示名称';
                    
      COMMENT ON COLUMN nop_auth_site.ORDER_NO IS '排序';
                    
      COMMENT ON COLUMN nop_auth_site.URL IS '链接';
                    
      COMMENT ON COLUMN nop_auth_site.STATUS IS '状态';
                    
      COMMENT ON COLUMN nop_auth_site.EXT_CONFIG IS '扩展配置';
                    
      COMMENT ON COLUMN nop_auth_site.CONFIG_VERSION IS '配置版本';
                    
      COMMENT ON COLUMN nop_auth_site.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_auth_site.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_auth_site.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_auth_site.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_auth_site.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_auth_site.REMARK IS '备注';
                    