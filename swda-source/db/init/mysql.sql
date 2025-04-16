drop function if exists f_get_first_spell;

drop function if exists f_get_full_spell;

drop table if exists bms_advance_query;

drop table if exists bms_advance_query_condition;

drop table if exists bms_advance_query_sort;

drop table if exists bms_attachment;

drop table if exists bms_attr;

drop table if exists bms_auth;

drop table if exists bms_auth_platform;

drop table if exists bms_data_log;

drop table if exists bms_dict;

drop table if exists bms_dict_type;

drop table if exists bms_email_log;

drop table if exists bms_login_log;

drop table if exists bms_mail_platform;

drop table if exists bms_menu;

drop table if exists bms_operation_log;

drop table if exists bms_organ;

drop table if exists bms_pinyin;

drop table if exists bms_post;

drop table if exists bms_region;

drop table if exists bms_role;

drop table if exists bms_role_menu;

drop table if exists bms_role_organ;

drop table if exists bms_sms_log;

drop table if exists bms_sms_platform;

drop table if exists bms_system;

drop table if exists bms_task;

drop table if exists bms_town;

drop table if exists bms_user;

drop table if exists bms_user_post;

drop table if exists bms_user_role;

drop table if exists bms_user_system;

drop table if exists bms_user_token;

/*==============================================================*/
/* Table: bms_advance_query                                     */
/*==============================================================*/
create table bms_advance_query
(
   advance_query_id     bigint not null comment '查询方案编号',
   advance_query_name   varchar(400) not null comment '查询方案名称',
   function_code        varchar(400) not null comment '功能编号',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (advance_query_id)
);

alter table bms_advance_query comment '查询方案表';

/*==============================================================*/
/* Table: bms_advance_query_condition                           */
/*==============================================================*/
create table bms_advance_query_condition
(
   condition_id         bigint not null comment '条件编号',
   advance_query_id     bigint not null comment '查询方案编号',
   left_parentheses     varchar(10) comment '左括号',
   condition_field      varchar(20) comment '字段',
   condition_type       varchar(4) not null default '0' comment '条件',
   condition_value      varchar(400) comment '值',
   right_parentheses    varchar(10) comment '右括号',
   condition_logic      varchar(2) comment '逻辑',
   sort                 int not null default 0 comment '排序码',
   primary key (condition_id)
);

alter table bms_advance_query_condition comment '查询方案条件表';

/*==============================================================*/
/* Table: bms_advance_query_sort                                */
/*==============================================================*/
create table bms_advance_query_sort
(
   sort_id              bigint not null comment '排序编号',
   advance_query_id     bigint not null comment '查询方案编号',
   sort_field           varchar(20) not null comment '字段',
   sort_logic           varchar(2) not null default '1' comment '排序逻辑',
   sort                 int not null default 0 comment '排序码',
   primary key (sort_id)
);

alter table bms_advance_query_sort comment '查询方案排序表';

/*==============================================================*/
/* Table: bms_attachment                                        */
/*==============================================================*/
create table bms_attachment
(
   attachment_id        bigint not null auto_increment comment '附件编号',
   file_name            varchar(200) not null comment '文件名称',
   file_url             varchar(200) not null comment '文件地址',
   file_size            varchar(400) not null comment '文件大小',
   storage_platform     varchar(200) comment '存储平台',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (attachment_id)
);

alter table bms_attachment comment '附件表';

/*==============================================================*/
/* Table: bms_attr                                              */
/*==============================================================*/
create table bms_attr
(
   attr_id              varchar(200) not null comment '参数编号',
   attr_name            varchar(200) not null comment '参数名称',
   attr_type            tinyint not null default 1 comment '参数类型',
   data_type            tinyint not null default 1 comment '数据类型',
   data_format          varchar(80) comment '数据格式',
   options              text comment '选项',
   valid                varchar(80) not null comment '验证脚本',
   content              text comment '参数内容',
   is_special           varchar(2) not null default '0' comment '是否特殊',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (attr_id)
);

alter table bms_attr comment '参数表';

/*==============================================================*/
/* Table: bms_auth                                              */
/*==============================================================*/
create table bms_auth
(
   auth_id              bigint not null auto_increment comment '授权编号',
   open_type            tinyint not null comment '平台类型',
   open_id              varchar(400) not null comment '平台唯一标识',
   username             varchar(400) comment '用户名称',
   user_id              bigint comment '用户编号',
   tenant_id            bigint comment '租户编号',
   version              int not null comment '版本号',
   auth_time            datetime not null comment '授权时间',
   status               tinyint not null default 0 comment '状态',
   primary key (auth_id)
);

alter table bms_auth comment '授权表';

/*==============================================================*/
/* Table: bms_auth_platform                                     */
/*==============================================================*/
create table bms_auth_platform
(
   auth_platform_id     bigint not null auto_increment comment '授权平台编号',
   open_type            tinyint not null comment '平台类型',
   client_id            varchar(400) comment '客户端编号',
   client_secret        varchar(400) comment '客户端密钥',
   redirect_uri         varchar(400) comment '重定向地址',
   agent_id             varchar(400) comment '代理编号',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (auth_platform_id)
);

alter table bms_auth_platform comment '授权平台表';

/*==============================================================*/
/* Table: bms_data_log                                          */
/*==============================================================*/
create table bms_data_log
(
   data_log_id          bigint not null auto_increment comment '数据日志编号',
   execute_type         tinyint not null comment '执行类型',
   execute_sql          text not null comment '执行SQL',
   execute_method       varchar(200) not null comment '执行方法',
   execute_params       text comment '执行参数',
   execute_result_class varchar(200) not null comment '执行结果类型',
   execute_result       text comment '执行结果',
   call_type            tinyint not null comment '调用方式',
   call_source          varchar(200) not null comment '调用来源',
   line_no              int not null comment '行号',
   call_result          tinyint not null comment '调用结果',
   error_reason         text comment '错误原因',
   start_time           datetime not null comment '开始时间',
   end_time             datetime not null comment '结束时间',
   cost                 int not null comment '耗时',
   tenant_id            bigint not null default 0 comment '租户编号',
   user_id              bigint comment '用户编号',
   real_name            varchar(400) comment '真实名称',
   organ_id             bigint comment '机构编号',
   organ_name           varchar(400) comment '机构名称',
   ip                   varchar(40) not null comment 'IP地址',
   primary key (data_log_id)
);

alter table bms_data_log comment '数据日志表';

/*==============================================================*/
/* Table: bms_dict                                              */
/*==============================================================*/
create table bms_dict
(
   dict_id              bigint not null auto_increment comment '字典编号',
   dict_name            varchar(400) not null comment '字典名称',
   dict_type            varchar(200) not null comment '字典类型',
   dict_key             varchar(200) not null comment '字典键',
   dict_value           varchar(400) not null comment '字典值',
   dict_class           varchar(200) comment '字典样式',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (dict_id)
);

alter table bms_dict comment '字典表';

/*==============================================================*/
/* Table: bms_dict_type                                         */
/*==============================================================*/
create table bms_dict_type
(
   dict_type_id         bigint not null auto_increment comment '字典类型编号',
   dict_type_name       varchar(400) not null comment '字典类型名称',
   dict_type            varchar(200) not null comment '字典类型',
   source_type          tinyint not null default 0 comment '来源类型',
   source_sql           varchar(800) comment '来源SQL',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (dict_type_id)
);

alter table bms_dict_type comment '字典类型表';

/*==============================================================*/
/* Table: bms_email_log                                         */
/*==============================================================*/
create table bms_email_log
(
   email_log_id         bigint not null auto_increment comment '邮件日志编号',
   email_platform_id    varchar(200) not null comment '邮件平台编号',
   email_platform_name  varchar(400) not null comment '邮件平台名称',
   email_platform_type  tinyint not null comment '邮件平台类型',
   mail_from            varchar(200) not null comment '发件人地址',
   mail_tos             varchar(800) comment '收件人地址',
   subject              varchar(800) comment '主题',
   content              text comment '内容',
   call_result          tinyint comment '调用结果',
   error_reason         text comment '错误原因',
   send_time            datetime comment '发送时间',
   primary key (email_log_id)
);

alter table bms_email_log comment '邮件日志表';

/*==============================================================*/
/* Table: bms_login_log                                         */
/*==============================================================*/
create table bms_login_log
(
   login_log_id         bigint not null auto_increment comment '登录日志编号',
   user_agent           varchar(800) comment 'User Agent',
   ip                   varchar(40) not null comment '登录IP',
   login_type           tinyint not null comment '登录类型',
   login_status         tinyint not null comment '登录状态',
   login_time           datetime not null comment '登录时间',
   login_info           varchar(200) not null comment '登录信息',
   login_message        text comment '登录消息',
   tenant_id            bigint default 0 comment '租户编号',
   user_id              bigint comment '用户编号',
   real_name            varchar(400) comment '真实名称',
   organ_id             bigint comment '机构编号',
   organ_name           varchar(400) comment '机构名称',
   is_logout            varchar(2) not null default '0' comment '是否登出',
   logout_type          varchar(2) comment '登出状态',
   logout_time          datetime comment '登出时间',
   primary key (login_log_id)
);

alter table bms_login_log comment '登录日志表';

/*==============================================================*/
/* Table: bms_mail_platform                                     */
/*==============================================================*/
create table bms_mail_platform
(
   email_platform_id    bigint not null auto_increment comment '邮件平台编号',
   email_platform_code  varchar(200) not null comment '邮件平台编码',
   email_platform_name  varchar(400) not null comment '邮件平台名称',
   email_platform_type  tinyint not null comment '邮件平台类型',
   status               tinyint not null comment '状态',
   group_name           varchar(200) comment '分组名称',
   mail_host            varchar(200) comment '邮件HOST',
   mail_port            int comment '邮件端口',
   mail_from            varchar(200) comment '邮件来源',
   mail_pass            varchar(200) comment '邮件密码',
   region_id            varchar(200) comment '区域编号',
   end_point            varchar(200) comment '端点',
   access_key           varchar(200) comment 'Access Key',
   secret_key           varchar(200) comment 'Secret Key',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (email_platform_id)
);

alter table bms_mail_platform comment '邮件平台表';

/*==============================================================*/
/* Table: bms_menu                                              */
/*==============================================================*/
create table bms_menu
(
   menu_id              bigint not null comment '菜单编号',
   pre_menu_id          bigint not null comment '上级菜单编号',
   system_id            bigint not null comment '系统编号',
   menu_code            varchar(200) not null comment '菜单编码',
   menu_name            varchar(400) not null comment '菜单名称',
   menu_type            varchar(2) not null default '1' comment '菜单类型',
   authority            varchar(200) not null comment '权限值',
   path                 varchar(800) comment '路径',
   component            varchar(800) comment '组件',
   icon                 varchar(40) not null comment '图标',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (menu_id)
);

alter table bms_menu comment '菜单表';

/*==============================================================*/
/* Table: bms_operation_log                                     */
/*==============================================================*/
create table bms_operation_log
(
   operation_log_id     bigint not null auto_increment comment '操作日志编号',
   user_agent           varchar(800) comment 'User Agent',
   operation_module     varchar(200) not null comment '操作模块',
   operation_name       varchar(200) comment '操作名称',
   operation_type       tinyint not null default 0 comment '操作类型',
   request_uri          varchar(800) comment '请求地址',
   request_method       varchar(20) comment '请求方法',
   request_params       text comment '请求参数',
   response_result      varchar(20) not null comment '响应结果',
   call_source          varchar(200) not null comment '调用来源',
   line_no              int not null comment '行号',
   call_result          tinyint not null comment '调用结果',
   error_reason         text comment '错误原因',
   start_time           datetime not null comment '开始时间',
   end_time             datetime not null comment '结束时间',
   cost                 int not null comment '耗时',
   tenant_id            bigint not null default 0 comment '租户编号',
   user_id              bigint comment '用户编号',
   real_name            varchar(400) comment '真实名称',
   organ_id             bigint comment '机构编号',
   organ_name           varchar(400) comment '机构名称',
   ip                   varchar(40) not null comment 'IP地址',
   primary key (operation_log_id)
);

alter table bms_operation_log comment '操作日志表';

/*==============================================================*/
/* Table: bms_organ                                             */
/*==============================================================*/
create table bms_organ
(
   organ_id             bigint not null auto_increment comment '机构编号',
   pre_organ_id         bigint comment '上级机构编号',
   organ_code           varchar(200) not null comment '机构编码',
   organ_name           varchar(400) not null comment '机构名称',
   contact              varchar(20) not null comment '联系人',
   contact_phone        varchar(20) not null comment '联系电话',
   longitude            decimal(20, 16) not null default -1 comment '经度',
   latitude             decimal(20, 16) not null default -1 comment '纬度',
   province_id          int not null comment '所属省',
   city_id              int not null comment '所属市',
   county_id            int not null comment '所属区',
   address              varchar(600) not null comment '详细地址',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (organ_id)
);

alter table bms_organ comment '机构表';

/*==============================================================*/
/* Table: bms_pinyin                                            */
/*==============================================================*/
create table bms_pinyin
(
   pinyin               varchar(20) not null comment '拼音',
   code                 int not null default 1 comment '编码',
   primary key (pinyin)
);

alter table bms_pinyin comment '拼音表';

/*==============================================================*/
/* Table: bms_post                                              */
/*==============================================================*/
create table bms_post
(
   post_id              bigint not null auto_increment comment '岗位编号',
   post_code            varchar(200) not null comment '岗位编码',
   post_name            varchar(400) not null comment '岗位名称',
   status               tinyint not null comment '状态',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (post_id)
);

alter table bms_post comment '岗位表';

/*==============================================================*/
/* Table: bms_region                                            */
/*==============================================================*/
create table bms_region
(
   region_id            int not null comment '区域编号',
   pre_region_id        int not null comment '上级区域编号',
   region_type          varchar(2) not null comment '区域类型',
   region_name          varchar(200) not null comment '区域名称',
   region_full_name     varchar(400) not null comment '区域全名',
   region_name_pinyin   varchar(200) not null comment '区域名称拼音',
   region_name_full_pinyin varchar(400) not null comment '区域名称全拼',
   postal_code          varchar(6) not null comment '邮政编码',
   is_open              varchar(2) not null default '1' comment '是否启用',
   is_hot               varchar(2) not null default '0' comment '是否热门',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (region_id)
);

alter table bms_region comment '区域表';

/*==============================================================*/
/* Table: bms_role                                              */
/*==============================================================*/
create table bms_role
(
   role_id              bigint not null auto_increment comment '角色编号',
   system_id            bigint not null comment '系统编号',
   organ_id             bigint not null comment '机构编号',
   role_code            varchar(200) not null comment '角色编码',
   role_name            varchar(400) not null comment '角色名称',
   data_scope           tinyint comment '数据范围',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (role_id)
);

alter table bms_role comment '角色表';

/*==============================================================*/
/* Table: bms_role_menu                                         */
/*==============================================================*/
create table bms_role_menu
(
   relation_id          bigint not null auto_increment comment '关系编号',
   system_id            bigint not null comment '系统编号',
   role_id              bigint not null comment '角色编号',
   menu_id              bigint not null comment '菜单编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (relation_id)
);

alter table bms_role_menu comment '角色菜单关系表';

/*==============================================================*/
/* Table: bms_role_organ                                        */
/*==============================================================*/
create table bms_role_organ
(
   relation_id          bigint not null auto_increment comment '关系编号',
   system_id            bigint not null comment '系统编号',
   role_id              bigint not null comment '角色编号',
   organ_id             bigint not null comment '机构编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (relation_id)
);

alter table bms_role_organ comment '角色机构关系表';

/*==============================================================*/
/* Table: bms_sms_log                                           */
/*==============================================================*/
create table bms_sms_log
(
   sms_log_id           bigint not null auto_increment comment '短信日志编号',
   sms_platform_id      varchar(200) not null comment '短信平台编号',
   sms_platform_name    varchar(400) not null comment '短信平台名称',
   sms_platform_type    tinyint not null comment '短信平台类型',
   mobile               varchar(20) not null comment '手机号',
   params               text comment '参数',
   call_result          tinyint comment '调用结果',
   error_reason         text comment '错误原因',
   send_time            datetime comment '发送时间',
   primary key (sms_log_id)
);

alter table bms_sms_log comment '短信日志表';

/*==============================================================*/
/* Table: bms_sms_platform                                      */
/*==============================================================*/
create table bms_sms_platform
(
   sms_platform_id      bigint not null auto_increment comment '短信平台编号',
   sms_platform_code    varchar(200) not null comment '短信平台编码',
   sms_platform_name    varchar(400) not null comment '短信平台名称',
   sms_platform_type    tinyint not null comment '短信平台类型',
   status               tinyint not null comment '状态',
   sign_name            varchar(200) comment '签名名称',
   template_id          varchar(200) comment '模板编号',
   app_id               varchar(200) comment '应用编号',
   sender_id            varchar(200) comment '发送人编号',
   url                  varchar(400) comment '接入地址',
   access_key           varchar(200) comment 'Access Key',
   secret_key           varchar(200) comment 'Secret Key',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (sms_platform_id)
);

alter table bms_sms_platform comment '短信平台表';

/*==============================================================*/
/* Table: bms_system                                            */
/*==============================================================*/
create table bms_system
(
   system_id            bigint not null comment '系统编号',
   system_code          varchar(200) not null comment '系统编码',
   system_name          varchar(400) not null comment '系统名称',
   icon                 varchar(40) not null comment '图标',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (system_id)
);

alter table bms_system comment '系统表';

/*==============================================================*/
/* Table: bms_task                                              */
/*==============================================================*/
create table bms_task
(
   task_id              bigint not null comment '任务编号',
   task_code            varchar(200) not null comment '任务编码',
   task_name            varchar(400) not null comment '任务名称',
   task_group           varchar(20) not null default '0' comment '任务分组',
   task_status          varchar(2) not null default '0' comment '任务状态',
   allow_concurrent     varchar(2) not null comment '允许并发',
   bean_name            varchar(200) not null default '0' comment 'Bean名称',
   method_name          varchar(200) not null comment '方法名称',
   params               text comment '参数',
   core_expression      varchar(40) not null comment 'Core表达式',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (task_id)
);

alter table bms_task comment '任务表';

/*==============================================================*/
/* Table: bms_town                                              */
/*==============================================================*/
create table bms_town
(
   town_id              int not null comment '街道编号',
   province_id          int not null comment '所属省',
   city_id              int not null comment '所属市',
   county_id            int not null comment '所属区',
   town_name            varchar(200) not null comment '街道名称',
   town_full_name       varchar(400) not null comment '街道全名',
   town_name_pinyin     varchar(200) not null comment '街道名称拼音',
   town_name_full_pinyin varchar(400) not null comment '街道名称全拼',
   postal_code          varchar(6) not null comment '邮政编码',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (town_id)
);

alter table bms_town comment '街道表';

/*==============================================================*/
/* Table: bms_user                                              */
/*==============================================================*/
create table bms_user
(
   user_id              bigint not null auto_increment comment '用户编号',
   organ_id             bigint not null comment '机构编号',
   username             varchar(200) not null comment '用户名称',
   real_name            varchar(400) comment '真实名称',
   avatar               varchar(400) comment '头像',
   password             varchar(80) not null default '123456' comment '密码',
   password_expire_time datetime not null comment '密码过期时间',
   super_admin          tinyint not null comment '超级管理员',
   status               tinyint not null comment '状态',
   gender               varchar(2) not null default '9' comment '性别',
   card_id              varchar(80) comment '证件号',
   mobile               varchar(20) comment '手机号码',
   email                varchar(200) comment '电子邮箱',
   address              varchar(600) comment '联系地址',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   tenant_id            bigint not null default 0 comment '租户编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (user_id)
);

alter table bms_user comment '用户表';

/*==============================================================*/
/* Table: bms_user_post                                         */
/*==============================================================*/
create table bms_user_post
(
   relation_id          bigint not null auto_increment comment '关系编号',
   user_id              bigint not null comment '用户编号',
   post_id              bigint not null comment '岗位编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (relation_id)
);

alter table bms_user_post comment '用户岗位关系表';

/*==============================================================*/
/* Table: bms_user_role                                         */
/*==============================================================*/
create table bms_user_role
(
   relation_id          bigint not null auto_increment comment '关系编号',
   user_id              bigint not null comment '用户编号',
   role_id              bigint not null comment '角色编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (relation_id)
);

alter table bms_user_role comment '用户角色关系表';

/*==============================================================*/
/* Table: bms_user_system                                       */
/*==============================================================*/
create table bms_user_system
(
   relation_id          bigint not null auto_increment comment '关系编号',
   user_id              bigint not null comment '用户编号',
   system_id            bigint not null comment '系统编号',
   version              int not null comment '版本号',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   updater              bigint not null comment '更新人',
   update_time          datetime not null comment '更新时间',
   deleted              varchar(2) not null default '0' comment '删除标识',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (relation_id)
);

alter table bms_user_system comment '用户系统关系表';

/*==============================================================*/
/* Table: bms_user_token                                        */
/*==============================================================*/
create table bms_user_token
(
   relation_id          bigint not null auto_increment comment '关系编号',
   user_id              bigint not null comment '用户编号',
   access_token         varchar(32) not null comment 'access token',
   access_token_expire  datetime comment 'access token 过期时间',
   refresh_token        varchar(32) comment 'refresh token',
   refresh_token_expire datetime comment 'refresh token 过期时间',
   tenant_id            bigint comment '租户编号',
   create_time          datetime not null comment '创建时间',
   primary key (relation_id)
);

alter table bms_user_token comment '用户TOKEN表';


create function f_get_first_spell(words varchar(2000) charset gbk) 
    returns varchar(2000) charset gbk
begin

    declare mycode int;
    declare tmp_lcode varchar(2) charset gbk;
    declare lcode int;
    declare tmp_rcode varchar(2) charset gbk;
    declare rcode int;
    
    declare mypy varchar(2000) charset gbk default '';
    declare lp int;
    
    set mycode = 0;
    set lp = 1;
    
    set words = hex(words);
    
    while lp < length(words) do
        set tmp_lcode = substring(words, lp, 2);
        set lcode = cast(ascii(unhex(tmp_lcode)) as unsigned);
        set tmp_rcode = substring(words, lp + 2, 2);
        set rcode = cast(ascii(unhex(tmp_rcode)) as unsigned);
        if lcode > 128 then
            set mycode =65536 - lcode * 256 - rcode ;
            select concat(mypy,substring(pinyin, 1, 1)) into mypy from bms_pinyin where code >= abs(mycode) order by code asc limit 1;
            set lp = lp + 4;
        else
            set mypy = concat(mypy,substring(char(cast(ascii(unhex(substring(words, lp, 2))) as unsigned)), 1, 1));
            set lp = lp + 2;
        end if;
    end while;
    
    return lower(mypy);
    
end;


create function f_get_full_spell(words varchar(2000) charset gbk) 
    returns varchar(2000) charset gbk
begin

    declare mycode int;
    declare tmp_lcode varchar(2) charset gbk;
    declare lcode int;
    declare tmp_rcode varchar(2) charset gbk;
    declare rcode int;
    
    declare mypy varchar(2000) charset gbk default '';
    declare lp int;
    
    set mycode = 0;
    set lp = 1;
    
    set words = hex(words);
    
    while lp < length(words) do
        set tmp_lcode = substring(words, lp, 2);
        set lcode = cast(ascii(unhex(tmp_lcode)) as unsigned);
        set tmp_rcode = substring(words, lp + 2, 2);
        set rcode = cast(ascii(unhex(tmp_rcode)) as unsigned);
        if lcode > 128 then
            set mycode =65536 - lcode * 256 - rcode ;
            select concat(mypy,pinyin) into mypy from bms_pinyin where code >= abs(mycode) order by code asc limit 1;
            set mypy = concat(mypy, ' ');
            set lp = lp + 4;
        else
            set mypy = concat(mypy,char(cast(ascii(unhex(substring(words, lp, 2))) as unsigned)));
            set mypy = concat(mypy, ' ');
            set lp = lp + 2;
        end if;
    end while;
    
    return lower(mypy);
    
end;
