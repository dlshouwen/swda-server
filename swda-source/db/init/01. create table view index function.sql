drop function if exists f_get_first_spell;

drop function if exists f_get_full_spell;

drop table if exists bms_advance_query;

drop table if exists bms_advance_query_condition;

drop table if exists bms_advance_query_sort;

drop table if exists bms_attr;

drop table if exists bms_data_log;

drop table if exists bms_dict;

drop table if exists bms_dict_category;

drop table if exists bms_login_log;

drop table if exists bms_operation_log;

drop table if exists bms_organ;

drop table if exists bms_permission;

drop table if exists bms_pinyin;

drop table if exists bms_region;

drop table if exists bms_role;

drop table if exists bms_role_permission;

drop table if exists bms_system;

drop table if exists bms_task;

drop table if exists bms_task_role;

drop table if exists bms_task_user;

drop table if exists bms_town;

drop table if exists bms_user;

drop table if exists bms_user_role;

drop table if exists bms_user_shortcut;

drop table if exists bms_user_system;

/*==============================================================*/
/* Table: bms_advance_query                                     */
/*==============================================================*/
create table bms_advance_query
(
   advance_query_id     bigint not null comment '查询方案编号',
   advance_query_name   varchar(400) not null comment '查询方案名称',
   function_code        varchar(400) not null comment '功能编号',
   remark               varchar(200) comment '备注',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
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
/* Table: bms_attr                                              */
/*==============================================================*/
create table bms_attr
(
   attr_id              varchar(200) not null comment '参数编号',
   attr_type            varchar(2) not null default '1' comment '参数类型',
   attr_name            varchar(200) not null comment '参数名称',
   data_type            varchar(2) not null default '1' comment '数据类型',
   data_format          varchar(80) comment '数据格式',
   options              text comment '选项',
   valid                varchar(80) not null comment '验证脚本',
   content              text comment '参数内容',
   is_special           varchar(2) not null default '0' comment '是否特殊',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   primary key (attr_id)
);

alter table bms_attr comment '参数表';

/*==============================================================*/
/* Table: bms_data_log                                          */
/*==============================================================*/
create table bms_data_log
(
   log_id               bigint not null auto_increment comment '日志编号',
   call_type            varchar(2) not null comment '调用方式',
   call_source          varchar(200) not null comment '调用来源',
   line_no              int not null comment '行号',
   operation_type       varchar(2) not null comment '操作类型',
   operation_sql        text not null comment '操作SQL',
   params               text comment '参数',
   call_result          varchar(2) not null comment '执行结果',
   error_reason         text comment '错误原因',
   execute_type         varchar(20) not null comment '执行类别',
   execute_result       text comment '执行结果',
   result_type          varchar(200) not null comment '结果类别',
   start_time           datetime not null comment '执行开始时间',
   end_time             datetime not null comment '执行结束时间',
   cost                 int not null comment '耗时',
   user_id              bigint comment '用户编号',
   user_name            varchar(400) comment '用户名称',
   organ_id             bigint comment '机构编号',
   organ_name           varchar(400) comment '机构名称',
   ip                   varchar(40) not null comment 'IP地址',
   primary key (log_id)
);

alter table bms_data_log comment '数据日志表';

/*==============================================================*/
/* Table: bms_dict                                              */
/*==============================================================*/
create table bms_dict
(
   dict_category_id     varchar(200) not null comment '字典类型编号',
   dict_id              varchar(200) not null comment '字典编号',
   dict_key             varchar(200) not null comment '字典键',
   dict_value           varchar(400) not null comment '字典值',
   sort                 int not null default 0 comment '排序号',
   primary key (dict_category_id, dict_id)
);

alter table bms_dict comment '字典表';

/*==============================================================*/
/* Table: bms_dict_category                                     */
/*==============================================================*/
create table bms_dict_category
(
   dict_category_id     varchar(200) not null comment '字典类型编号',
   dict_category_name   varchar(400) not null comment '字典类型名称',
   sort                 int not null default 0 comment '排序号',
   primary key (dict_category_id)
);

alter table bms_dict_category comment '字典类型表';

/*==============================================================*/
/* Table: bms_login_log                                         */
/*==============================================================*/
create table bms_login_log
(
   log_id               bigint not null auto_increment comment '日志内码',
   token                varchar(400) not null comment 'TOKEN',
   user_id              bigint not null comment '用户编号',
   user_name            varchar(400) not null comment '用户名称',
   organ_id             bigint not null comment '机构编号',
   organ_name           varchar(400) not null comment '机构名称',
   login_time           datetime not null comment '登录时间',
   ip                   varchar(40) not null comment '登录IP',
   login_status         varchar(2) not null comment '登录状态',
   is_logout            varchar(2) not null default '0' comment '是否登出',
   logout_type          varchar(2) comment '登出状态',
   logout_time          datetime comment '登出时间',
   primary key (log_id)
);

alter table bms_login_log comment '登录日志表';

/*==============================================================*/
/* Table: bms_operation_log                                     */
/*==============================================================*/
create table bms_operation_log
(
   log_id               bigint not null auto_increment comment '日志编号',
   call_source          varchar(200) not null comment '调用来源',
   operation_url        text not null comment '操作地址',
   params               text comment '参数',
   operation_type       varchar(2) not null default '0' comment '操作类型',
   operation_result     varchar(20) not null comment '操作结果',
   error_reason         text comment '错误原因',
   operation_detail     text not null comment '操作说明',
   response_start       datetime not null comment '响应开始时间',
   response_end         datetime not null comment '响应结束时间',
   cost                 int not null comment '耗时',
   user_id              bigint comment '用户编号',
   user_name            varchar(400) comment '用户名称',
   organ_id             bigint comment '机构编号',
   organ_name           varchar(400) comment '机构名称',
   ip                   varchar(40) not null comment 'IP地址',
   primary key (log_id)
);

alter table bms_operation_log comment '操作日志表';

/*==============================================================*/
/* Table: bms_organ                                             */
/*==============================================================*/
create table bms_organ
(
   organ_id             bigint not null comment '机构编号',
   pre_organ_id         bigint comment '上级机构编号',
   organ_code           varchar(200) not null comment '机构编码',
   organ_name           varchar(400) not null comment '机构名称',
   contact              varchar(20) not null comment '联系人',
   contact_phone        varchar(20) not null comment '联系电话',
   longitude            decimal(20, 16) not null default -1 comment '经度',
   latitude             decimal(20, 16) not null default -1 comment '纬度',
   province_id          bigint not null comment '所属省',
   city_id              bigint not null comment '所属市',
   county_id            bigint not null comment '所属区',
   address              varchar(600) not null comment '详细地址',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
   is_delete            varchar(2) not null default '0' comment '是否删除',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (organ_id)
);

alter table bms_organ comment '机构表';

/*==============================================================*/
/* Table: bms_permission                                        */
/*==============================================================*/
create table bms_permission
(
   permission_id        bigint not null comment '权限编号',
   pre_permission_id    bigint not null comment '上级权限编号',
   system_id            bigint not null comment '系统编号',
   permission_code      varchar(200) not null comment '权限编码',
   permission_name      varchar(400) not null comment '权限名称',
   permission_type      varchar(2) not null default '1' comment '权限类型',
   permission_value     varchar(200) not null comment '权限值',
   path                 varchar(800) comment '路径',
   component            varchar(800) comment '组件',
   icon                 varchar(40) not null comment '图标',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null default 0 comment '排序码',
   remark               varchar(200) comment '备注',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
   is_delete            varchar(2) not null default '0' comment '是否删除',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (permission_id)
);

alter table bms_permission comment '权限表';

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
   primary key (region_id)
);

alter table bms_region comment '区域表';

/*==============================================================*/
/* Table: bms_role                                              */
/*==============================================================*/
create table bms_role
(
   role_id              bigint not null comment '角色编号',
   system_id            bigint not null comment '系统编号',
   role_code            varchar(200) not null comment '角色编码',
   role_name            varchar(400) not null comment '角色名称',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null comment '排序码',
   remark               varchar(200) comment '备注',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
   is_delete            varchar(2) not null default '0' comment '是否删除',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (role_id)
);

alter table bms_role comment '角色表';

/*==============================================================*/
/* Table: bms_role_permission                                   */
/*==============================================================*/
create table bms_role_permission
(
   system_id            bigint not null comment '系统编号',
   role_id              bigint not null comment '角色编号',
   permission_id        bigint not null comment '权限编号',
   primary key (system_id, role_id, permission_id)
);

alter table bms_role_permission comment '角色权限关系表';

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
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
   is_delete            varchar(2) not null default '0' comment '是否删除',
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
   task_status          varchar(2) not null default '0' comment '任务状态',
   is_never_overdue     varchar(2) not null default '0' comment '是否永不过期',
   overdue_time         datetime comment '过期时间',
   is_all_user          varchar(2) not null default '0' comment '是否所有用户启用',
   iterator_time        int not null comment '迭代时间',
   detonate_sql         varchar(400) not null comment '触发SQL',
   alert_message        varchar(400) not null comment '提示信息',
   function_id          bigint not null comment '响应功能',
   assist_search        varchar(400) comment '辅助查询',
   sort                 int not null comment '排序码',
   remark               varchar(200) comment '备注',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
   is_delete            varchar(2) not null default '0' comment '是否删除',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (task_id)
);

alter table bms_task comment '任务表';

/*==============================================================*/
/* Table: bms_task_role                                         */
/*==============================================================*/
create table bms_task_role
(
   task_id              bigint not null comment '任务编号',
   role_id              bigint not null comment '角色编号',
   primary key (role_id, task_id)
);

alter table bms_task_role comment '任务角色关系表';

/*==============================================================*/
/* Table: bms_task_user                                         */
/*==============================================================*/
create table bms_task_user
(
   task_id              bigint not null comment '任务编号',
   user_id              bigint not null comment '用户编号',
   primary key (user_id, task_id)
);

alter table bms_task_user comment '任务用户关系表';

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
   sort                 int not null default 1 comment '排序码',
   primary key (town_id)
);

alter table bms_town comment '街道表';

/*==============================================================*/
/* Table: bms_user                                              */
/*==============================================================*/
create table bms_user
(
   user_id              bigint not null comment '用户编号',
   organ_id             bigint not null comment '机构编号',
   user_code            varchar(200) not null comment '用户编码',
   user_name            varchar(400) not null comment '用户名称',
   password             varchar(80) not null default '123456' comment '密码',
   password_expire_time datetime not null comment '密码过期时间',
   is_enable            varchar(2) not null comment '是否启用',
   sex                  varchar(2) not null default '9' comment '性别',
   card_id              varchar(80) comment '证件号',
   phone                varchar(20) comment '联系电话',
   email                varchar(200) comment '电子邮箱',
   address              varchar(600) comment '联系地址',
   assist_search        varchar(400) comment '辅助查询',
   remark               varchar(200) comment '备注',
   creator              bigint not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               bigint not null comment '编辑人',
   edit_time            datetime not null comment '编辑时间',
   is_delete            national varchar(2) not null default '0' comment '是否删除',
   deleter              bigint comment '删除人',
   delete_time          datetime comment '删除时间',
   primary key (user_id)
);

alter table bms_user comment '用户表';

/*==============================================================*/
/* Table: bms_user_role                                         */
/*==============================================================*/
create table bms_user_role
(
   user_id              bigint not null comment '用户编号',
   role_id              bigint not null comment '角色编号',
   primary key (user_id, role_id)
);

alter table bms_user_role comment '用户角色关系表';

/*==============================================================*/
/* Table: bms_user_shortcut                                     */
/*==============================================================*/
create table bms_user_shortcut
(
   user_id              bigint not null comment '用户编号',
   function_id          bigint not null comment '功能编号',
   primary key (user_id, function_id)
);

alter table bms_user_shortcut comment '用户快捷方式表';

/*==============================================================*/
/* Table: bms_user_system                                       */
/*==============================================================*/
create table bms_user_system
(
   user_id              bigint not null comment '用户编号',
   system_id            bigint not null comment '系统编号',
   primary key (user_id, system_id)
);

alter table bms_user_system comment '用户系统关系表';


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
