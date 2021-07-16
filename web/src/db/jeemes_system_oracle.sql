/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2020/7/28 10:44:32                           */
/*==============================================================*/


/*==============================================================*/
/* Table: ht_file_info                                        */
/*==============================================================*/
create table ht_file_info
(
   id                 VARCHAR2(64)         not null,
   parent_id          VARCHAR2(64)         default NULL,
   file_name          VARCHAR2(255)        default NULL,
   file_type          CHAR(1)              default NULL,
   file_size          VARCHAR2(64)         default NULL,
   file_id            VARCHAR2(64)         default NULL,
   address            VARCHAR2(255)        default NULL,
   status             CHAR(1)              default NULL,
   biz_key            VARCHAR2(64)         default NULL,
   biz_type           VARCHAR2(64)         default NULL,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_FILE_INFO primary key (id)
);

comment on column ht_file_info.id is
'主键';

comment on column ht_file_info.parent_id is
'上级文件';

comment on column ht_file_info.file_name is
'名称';

comment on column ht_file_info.file_type is
'文件类型（1-文件夹 2-文件）';

comment on column ht_file_info.file_size is
'文件大小';

comment on column ht_file_info.file_id is
'文件uuid';

comment on column ht_file_info.address is
'文件保存地址';

comment on column ht_file_info.status is
'状态（0-正常 2-停用）';

comment on column ht_file_info.biz_key is
'业务主键';

comment on column ht_file_info.biz_type is
'业务类型';

comment on column ht_file_info.remarks is
'备注';

comment on column ht_file_info.create_by is
'上传者';

comment on column ht_file_info.update_by is
'更新者';

/*==============================================================*/
/* Table: ht_file_received                                    */
/*==============================================================*/
create table ht_file_received
(
   id                 VARCHAR2(64)         not null,
   file_info_id       VARCHAR2(64)         default NULL,
   from_user_id       VARCHAR2(64)         default NULL,
   to_user_id         VARCHAR2(2000)       default NULL,
   share_id           VARCHAR2(64)         default NULL,
   status             CHAR(1)              default NULL,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_FILE_RECEIVED primary key (id)
);

comment on column ht_file_received.id is
'主键';

comment on column ht_file_received.file_info_id is
'上级文件';

comment on column ht_file_received.from_user_id is
'分享人';

comment on column ht_file_received.to_user_id is
'接收人';

comment on column ht_file_received.share_id is
'分享表主键';

comment on column ht_file_received.status is
'状态（0-正常 2-停用）';

comment on column ht_file_received.remarks is
'备注';

comment on column ht_file_received.create_by is
'上传者';

comment on column ht_file_received.update_by is
'更新者';

/*==============================================================*/
/* Table: ht_file_shared                                      */
/*==============================================================*/
create table ht_file_shared
(
   id                 VARCHAR2(64)         not null,
   file_info_id       VARCHAR2(64)         default NULL,
   from_user_id       VARCHAR2(64)         default NULL,
   to_user_ids        VARCHAR2(2000)       default NULL,
   to_user_names      VARCHAR2(2000)       default NULL,
   status             CHAR(1)              default NULL,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_FILE_SHARED primary key (id)
);

comment on column ht_file_shared.id is
'主键';

comment on column ht_file_shared.file_info_id is
'上级文件';

comment on column ht_file_shared.from_user_id is
'分享人';

comment on column ht_file_shared.to_user_ids is
'接收人,多个以,隔开';

comment on column ht_file_shared.to_user_names is
'接收人名称,多个以,隔开';

comment on column ht_file_shared.status is
'状态（0-正常 2-停用）';

comment on column ht_file_shared.remarks is
'备注';

comment on column ht_file_shared.create_by is
'上传者';

comment on column ht_file_shared.update_by is
'更新者';

/*==============================================================*/
/* Table: ht_gen_table                                        */
/*==============================================================*/
create table ht_gen_table
(
   id                 VARCHAR2(64)         not null,
   table_name         VARCHAR2(255)        default NULL,
   table_fk_name      VARCHAR2(64)         default NULL,
   parent_table_name  VARCHAR2(64)         default NULL,
   parent_table_fk_name VARCHAR2(64)         default NULL,
   tpl_category       VARCHAR2(200)        default NULL,
   comments           VARCHAR2(255)        default NULL,
   package_name       VARCHAR2(255)        default NULL,
   module_name        VARCHAR2(255)        default NULL,
   function_author    VARCHAR2(255)        default NULL,
   code_base_dir      VARCHAR2(255)        default NULL,
   remarks            VARCHAR2(255)        default NULL,
   status             CHAR(1)              default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_GEN_TABLE primary key (id)
);

comment on column ht_gen_table.id is
'主键';

comment on column ht_gen_table.table_name is
'表名';

comment on column ht_gen_table.table_fk_name is
'本表关联的字段名';

comment on column ht_gen_table.parent_table_name is
'关联父表的表名';

comment on column ht_gen_table.parent_table_fk_name is
'父表关联的字段名';

comment on column ht_gen_table.tpl_category is
'使用的模板';

comment on column ht_gen_table.comments is
'表说明';

comment on column ht_gen_table.package_name is
'生成包路径';

comment on column ht_gen_table.module_name is
'生成模块名';

comment on column ht_gen_table.function_author is
'生成功能作者';

comment on column ht_gen_table.code_base_dir is
'生成基础路径';

comment on column ht_gen_table.remarks is
'备注';

comment on column ht_gen_table.status is
'状态（0-正常 2-停用）';

comment on column ht_gen_table.create_by is
'创建者';

comment on column ht_gen_table.update_by is
'更改者';

/*==============================================================*/
/* Table: ht_gen_table_column                                 */
/*==============================================================*/
create table ht_gen_table_column
(
   id                 VARCHAR2(64)         not null,
   table_name         VARCHAR2(255)        default NULL,
   column_name        VARCHAR2(64)         default NULL,
   column_type        VARCHAR2(100)        default NULL,
   column_label       VARCHAR2(255)        default NULL,
   comments           VARCHAR2(255)        default NULL,
   attr_name          VARCHAR2(255)        default NULL,
   attr_type          VARCHAR2(255)        default NULL,
   is_pk              CHAR(1)              default NULL,
   is_null            CHAR(1)              default NULL,
   is_edit            CHAR(1)              default NULL,
   show_type          VARCHAR2(255)        default NULL,
   options            VARCHAR2(1000)       default NULL,
   constraint PK_HT_GEN_TABLE_COLUMN primary key (id)
);

comment on column ht_gen_table_column.id is
'主键';

comment on column ht_gen_table_column.table_name is
'表名';

comment on column ht_gen_table_column.column_name is
'字段名';

comment on column ht_gen_table_column.column_type is
'字段类型';

comment on column ht_gen_table_column.column_label is
'字段标签名';

comment on column ht_gen_table_column.comments is
'字段备注说明';

comment on column ht_gen_table_column.attr_name is
'类的属性名';

comment on column ht_gen_table_column.attr_type is
'类的属性类型';

comment on column ht_gen_table_column.is_pk is
'是否主键';

comment on column ht_gen_table_column.is_null is
'是否为空';

comment on column ht_gen_table_column.is_edit is
'是否编辑字段';

comment on column ht_gen_table_column.show_type is
'表单类型';

comment on column ht_gen_table_column.options is
'其他生成选项';

/*==============================================================*/
/* Table: ht_message_receive                                  */
/*==============================================================*/
create table ht_message_receive
(
   id                 VARCHAR2(64)         not null,
   send_id            VARCHAR2(64)         default NULL,
   msg_title          VARCHAR2(200)        default NULL,
   msg_content        CLOB,
   receive_user       VARCHAR2(64)         default NULL,
   send_user          VARCHAR2(64)         default NULL,
   receive_date       DATE,
   read_status        CHAR(1)              default NULL,
   read_date          DATE,
   remarks            VARCHAR2(300)        default NULL,
   constraint PK_HT_MESSAGE_RECEIVE primary key (id)
);

comment on column ht_message_receive.id is
'主键';

comment on column ht_message_receive.send_id is
'发送消息主键';

comment on column ht_message_receive.msg_title is
'消息标题';

comment on column ht_message_receive.receive_user is
'接收人';

comment on column ht_message_receive.send_user is
'发送人';

comment on column ht_message_receive.read_status is
'读取状态（1已读 2未读）';

comment on column ht_message_receive.remarks is
'备注';

/*==============================================================*/
/* Table: ht_message_send                                     */
/*==============================================================*/
create table ht_message_send
(
   id                 VARCHAR2(64)         not null,
   msg_type           CHAR(1)              default NULL,
   msg_title          VARCHAR2(200)        default NULL,
   msg_content        CLOB,
   receive_user       VARCHAR2(64)         default NULL,
   send_user          VARCHAR2(64)         default NULL,
   send_date          DATE,
   push_status        CHAR(1)              default NULL,
   push_number        INTEGER              default NULL,
   push_return_code   VARCHAR2(200)        default NULL,
   push_return_content CLOB,
   remarks            VARCHAR2(300)        default NULL,
   constraint PK_HT_MESSAGE_SEND primary key (id)
);

comment on column ht_message_send.id is
'主键';

comment on column ht_message_send.msg_type is
'消息类型（1-系统 2-短信 3-邮件 4-微信）';

comment on column ht_message_send.msg_title is
'消息标题';

comment on column ht_message_send.receive_user is
'接收人';

comment on column ht_message_send.push_status is
'推送状态（0-未推送 1-成功  2失败）';

comment on column ht_message_send.push_number is
'推送次数';

comment on column ht_message_send.push_return_code is
'推送返回结果码';

comment on column ht_message_send.remarks is
'备注';

/*==============================================================*/
/* Table: ht_message_template                                 */
/*==============================================================*/
create table ht_message_template
(
   id                 VARCHAR2(64)         not null,
   tpl_title          VARCHAR2(200)        default NULL,
   tpl_code           VARCHAR2(100)        default NULL,
   tpl_type           CHAR(1)              default NULL,
   tpl_content        CLOB,
   status             CHAR(1)              default NULL,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_MESSAGE_TEMPLATE primary key (id)
);

comment on column ht_message_template.id is
'主键';

comment on column ht_message_template.tpl_title is
'模板消息标题';

comment on column ht_message_template.tpl_code is
'模板编码';

comment on column ht_message_template.tpl_type is
'模板消息类型';

comment on column ht_message_template.status is
'状态';

comment on column ht_message_template.remarks is
'备注信息';

comment on column ht_message_template.create_by is
'创建者主键';

comment on column ht_message_template.update_by is
'修改者主键';

/*==============================================================*/
/* Table: ht_sys_config                                       */
/*==============================================================*/
create table ht_sys_config
(
   id                 VARCHAR2(64)         not null,
   config_name        VARCHAR2(100)        default NULL,
   config_key         VARCHAR2(100)        default NULL,
   config_value       VARCHAR2(1000)       default NULL,
   status             CHAR(1)              default '0',
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_SYS_CONFIG primary key (id)
);

comment on column ht_sys_config.id is
'主键';

comment on column ht_sys_config.config_name is
'参数名称';

comment on column ht_sys_config.config_key is
'参数键';

comment on column ht_sys_config.config_value is
'参数值';

comment on column ht_sys_config.status is
'状态';

comment on column ht_sys_config.remarks is
'备注';

comment on column ht_sys_config.create_by is
'创建人主键';

comment on column ht_sys_config.update_by is
'修改人主键';

/*==============================================================*/
/* Table: ht_sys_data_scope                                   */
/*==============================================================*/
create table ht_sys_data_scope
(
   id                 VARCHAR2(64)         not null,
   role_id            VARCHAR2(64)         default NULL,
   office_id          VARCHAR2(64)         default NULL,
   type               CHAR(1)              default NULL,
   constraint PK_HT_SYS_DATA_SCOPE primary key (id)
);

comment on column ht_sys_data_scope.id is
'主键';

comment on column ht_sys_data_scope.role_id is
'角色主键';

comment on column ht_sys_data_scope.office_id is
'机构主键';

comment on column ht_sys_data_scope.type is
'数据权限类别,0公司,1部门';

/*==============================================================*/
/* Table: ht_sys_dict_data                                    */
/*==============================================================*/
create table ht_sys_dict_data
(
   id                 VARCHAR2(64)         not null,
   dict_label         VARCHAR2(100)        not null,
   dict_value         VARCHAR2(100)        not null,
   dict_type          VARCHAR2(100)        not null,
   status             CHAR(1)              default '0' not null,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE
);

comment on column ht_sys_dict_data.id is
'主键';

comment on column ht_sys_dict_data.dict_label is
'字典标签';

comment on column ht_sys_dict_data.dict_value is
'字典键值';

comment on column ht_sys_dict_data.dict_type is
'字典类型';

comment on column ht_sys_dict_data.status is
'状态（0正常 1停用）';

comment on column ht_sys_dict_data.remarks is
'备注';

comment on column ht_sys_dict_data.create_by is
'创建者主键';

comment on column ht_sys_dict_data.update_by is
'更新者主键';

/*==============================================================*/
/* Table: ht_sys_dict_type                                    */
/*==============================================================*/
create table ht_sys_dict_type
(
   id                 VARCHAR2(64)         not null,
   dict_name          VARCHAR2(100)        default NULL,
   dict_code          VARCHAR2(100)        default NULL,
   status             CHAR(1)              default '0',
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_SYS_DICT_TYPE primary key (id)
);

comment on column ht_sys_dict_type.id is
'主键';

comment on column ht_sys_dict_type.dict_name is
'字典名称';

comment on column ht_sys_dict_type.dict_code is
'字典编号';

comment on column ht_sys_dict_type.status is
'状态';

comment on column ht_sys_dict_type.remarks is
'备注';

comment on column ht_sys_dict_type.create_by is
'创建者主键';

comment on column ht_sys_dict_type.update_by is
'修改者主键';

/*==============================================================*/
/* Table: ht_sys_job                                          */
/*==============================================================*/
create table ht_sys_job
(
   id                 VARCHAR2(64)         not null,
   job_name           VARCHAR2(64)         not null,
   job_group          VARCHAR2(64)         not null,
   description        VARCHAR2(100)        not null,
   invoke_target      VARCHAR2(1000)       not null,
   params             VARCHAR2(1000)       default NULL,
   cron_expression    VARCHAR2(255)        not null,
   misfire_instruction NUMBER(1,0)          default NULL,
   concurrent         CHAR(1)              default NULL,
   status             CHAR(1)              not null,
   create_by          VARCHAR2(64)         not null,
   create_date        DATE,
   update_by          VARCHAR2(64)         not null,
   update_date        DATE,
   remarks            VARCHAR2(500)        default NULL,
   constraint PK_HT_SYS_JOB primary key (id)
);

comment on column ht_sys_job.id is
'主键';

comment on column ht_sys_job.job_name is
'任务名称';

comment on column ht_sys_job.job_group is
'任务组名';

comment on column ht_sys_job.description is
'任务描述';

comment on column ht_sys_job.invoke_target is
'调用目标字符串';

comment on column ht_sys_job.params is
'参数';

comment on column ht_sys_job.cron_expression is
'Cron执行表达式';

comment on column ht_sys_job.misfire_instruction is
'计划执行错误策略';

comment on column ht_sys_job.concurrent is
'是否并发执行';

comment on column ht_sys_job.status is
'状态（0正常 1删除 2暂停）';

comment on column ht_sys_job.create_by is
'创建者';

comment on column ht_sys_job.update_by is
'更新者';

comment on column ht_sys_job.remarks is
'备注信息';

/*==============================================================*/
/* Table: ht_sys_job_log                                      */
/*==============================================================*/
create table ht_sys_job_log
(
   id                 VARCHAR2(64)         not null,
   job_name           VARCHAR2(64)         not null,
   job_group          VARCHAR2(64)         not null,
   job_type           VARCHAR2(50)         default NULL,
   job_event          VARCHAR2(200)        default NULL,
   job_message        VARCHAR2(500)        default NULL,
   is_exception       CHAR(1)              default NULL,
   exception_info     CLOB,
   create_date        DATE,
   constraint PK_HT_SYS_JOB_LOG primary key (id)
);

comment on column ht_sys_job_log.id is
'编号';

comment on column ht_sys_job_log.job_name is
'任务名称';

comment on column ht_sys_job_log.job_group is
'任务组名';

comment on column ht_sys_job_log.job_type is
'日志类型';

comment on column ht_sys_job_log.job_event is
'日志事件';

comment on column ht_sys_job_log.job_message is
'日志信息';

comment on column ht_sys_job_log.is_exception is
'是否异常';

/*==============================================================*/
/* Table: ht_sys_menu                                         */
/*==============================================================*/
create table ht_sys_menu
(
   id                 VARCHAR2(64)         not null,
   menu_code          VARCHAR2(100),
   menu_name          VARCHAR2(200),
   menu_type          CHAR(1),
   parent_id          VARCHAR2(100),
   parent_ids         VARCHAR2(2000),
   tree_level         INTEGER              default NULL,
   tree_leaf          CHAR(1),
   tree_sort          INTEGER              default NULL,
   menu_href          VARCHAR2(1000),
   menu_target        CHAR(1),
   menu_icon          VARCHAR2(100),
   permission         VARCHAR2(1000),
   weight             INTEGER              default NULL,
   is_show            CHAR(1),
   status             CHAR(1),
   remarks            VARCHAR2(500),
   create_by          VARCHAR2(64),
   create_date        DATE,
   update_by          VARCHAR2(64),
   update_date        DATE,
   constraint PK_HT_SYS_MENU primary key (id)
);

comment on column ht_sys_menu.tree_level is
'所在树级别';

comment on column ht_sys_menu.tree_sort is
'本级排序号（升序）';

comment on column ht_sys_menu.weight is
'菜单权重（0普通用户，1 二级管理员，2 系统管理员，3超级管理员）';

/*==============================================================*/
/* Table: ht_sys_office                                       */
/*==============================================================*/
create table ht_sys_office
(
   id                 VARCHAR2(64)         not null,
   office_code        VARCHAR2(100)        not null,
   company_id         VARCHAR2(64)         default NULL,
   parent_id          VARCHAR2(100)        not null,
   parent_ids         VARCHAR2(2000)       not null,
   tree_leaf          CHAR(1),
   tree_level         INTEGER              default NULL,
   tree_sort          INTEGER              not null,
   office_name        VARCHAR2(100)        not null,
   full_name          VARCHAR2(200)        not null,
   office_type        CHAR(1)              not null,
   leader             VARCHAR2(100)        default NULL,
   phone              VARCHAR2(100)        default NULL,
   address            VARCHAR2(255)        default NULL,
   zip_code           VARCHAR2(100)        default NULL,
   email              VARCHAR2(255)        default NULL,
   status             CHAR(1)              not null,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_SYS_OFFICE primary key (id)
);

comment on column ht_sys_office.id is
'主键';

comment on column ht_sys_office.office_code is
'机构编号';

comment on column ht_sys_office.company_id is
'所属公司';

comment on column ht_sys_office.parent_id is
'父级机构(关联id)';

comment on column ht_sys_office.parent_ids is
'所有父级机构，逗号隔开';

comment on column ht_sys_office.tree_level is
'所在树级别';

comment on column ht_sys_office.tree_sort is
'本级排序号（升序）';

comment on column ht_sys_office.office_name is
'机构名称';

comment on column ht_sys_office.full_name is
'机构全称';

comment on column ht_sys_office.office_type is
'机构类型，数据字典设置(0公司，1部门)';

comment on column ht_sys_office.leader is
'负责人';

comment on column ht_sys_office.phone is
'办公电话';

comment on column ht_sys_office.address is
'联系地址';

comment on column ht_sys_office.zip_code is
'邮政编码';

comment on column ht_sys_office.email is
'电子邮箱';

comment on column ht_sys_office.status is
'状态（0正常 1停用）';

comment on column ht_sys_office.remarks is
'备注信息';

comment on column ht_sys_office.create_by is
'创建者';

comment on column ht_sys_office.update_by is
'修改者';

/*==============================================================*/
/* Table: ht_sys_post                                         */
/*==============================================================*/
create table ht_sys_post
(
   id                 VARCHAR2(64)         not null,
   post_code          VARCHAR2(100)        default NULL,
   post_name          VARCHAR2(200)        default NULL,
   post_type          INTEGER              default NULL,
   parent_id          VARCHAR2(100)        default NULL,
   parent_ids         VARCHAR2(2000)       default NULL,
   tree_leaf          CHAR(1),
   tree_level         INTEGER              default NULL,
   tree_sort          INTEGER              default NULL,
   status             CHAR(1)              default NULL,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_SYS_POST primary key (id)
);

comment on column ht_sys_post.id is
'主键';

comment on column ht_sys_post.post_code is
'岗位编码';

comment on column ht_sys_post.post_name is
'岗位名称';

comment on column ht_sys_post.post_type is
'岗位权重，（0普通用户，1 二级管理员，2 系统管理员）';

comment on column ht_sys_post.parent_id is
'父岗位主键';

comment on column ht_sys_post.parent_ids is
'所有父岗位主键,逗号隔开';

comment on column ht_sys_post.tree_level is
'所在树级别';

comment on column ht_sys_post.tree_sort is
'本级排序号（升序）';

comment on column ht_sys_post.status is
'状态';

comment on column ht_sys_post.remarks is
'备注';

comment on column ht_sys_post.create_by is
'创建者主键';

comment on column ht_sys_post.update_by is
'修改者主键';

/*==============================================================*/
/* Table: ht_sys_post_role                                    */
/*==============================================================*/
create table ht_sys_post_role
(
   id                 VARCHAR2(64)         not null,
   post_id            VARCHAR2(64)         default NULL,
   role_id            VARCHAR2(64)         default NULL,
   constraint PK_HT_SYS_POST_ROLE primary key (id)
);

comment on column ht_sys_post_role.id is
'主键';

comment on column ht_sys_post_role.post_id is
'岗位主键';

comment on column ht_sys_post_role.role_id is
'角色主键';

/*==============================================================*/
/* Table: ht_sys_role                                         */
/*==============================================================*/
create table ht_sys_role
(
   id                 VARCHAR2(64)         not null,
   role_code          VARCHAR2(100)        default NULL,
   role_name          VARCHAR2(200)        default NULL,
   role_type          INTEGER              default NULL,
   role_sort          INTEGER              default NULL,
   is_ctrl            CHAR(1)              default NULL,
   status             CHAR(1)              default NULL,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_SYS_ROLE primary key (id)
);

comment on column ht_sys_role.id is
'主键';

comment on column ht_sys_role.role_code is
'角色编号';

comment on column ht_sys_role.role_name is
'角色名称';

comment on column ht_sys_role.role_type is
'角色权重，（0普通用户，1 二级管理员，2 系统管理员）';

comment on column ht_sys_role.role_sort is
'角色排序';

comment on column ht_sys_role.is_ctrl is
'数据权限类别，0 本部门以及子部门，1 自定义，2 本公司以及子公司';

comment on column ht_sys_role.status is
'状态';

comment on column ht_sys_role.remarks is
'备注';

comment on column ht_sys_role.create_by is
'创建者主键';

comment on column ht_sys_role.update_by is
'修改者主键';

/*==============================================================*/
/* Table: ht_sys_role_menu                                    */
/*==============================================================*/
create table ht_sys_role_menu
(
   id                 VARCHAR2(64)         not null,
   role_id            VARCHAR2(64)         default NULL,
   menu_id            VARCHAR2(64)         default NULL,
   constraint PK_HT_SYS_ROLE_MENU primary key (id)
);

comment on column ht_sys_role_menu.id is
'主键';

comment on column ht_sys_role_menu.role_id is
'角色主键';

comment on column ht_sys_role_menu.menu_id is
'菜单主键';

/*==============================================================*/
/* Table: ht_sys_user                                         */
/*==============================================================*/
create table ht_sys_user
(
   id                 VARCHAR2(64)         not null,
   login_code         VARCHAR2(100)        not null,
   company_id         VARCHAR2(64)         default NULL,
   dept_id            VARCHAR2(64)         default NULL,
   user_name          VARCHAR2(100)        not null,
   password           VARCHAR2(100)        not null,
   email              VARCHAR2(300)        default NULL,
   mobile             VARCHAR2(100)        default NULL,
   phone              VARCHAR2(100)        default NULL,
   sex                CHAR(1)              default NULL,
   avatar             VARCHAR2(1000)       default NULL,
   sign               VARCHAR2(200)        default NULL,
   wx_openid          VARCHAR2(100)        default NULL,
   user_type          INTEGER              not null,
   status             CHAR(1)              not null,
   remarks            VARCHAR2(500)        default NULL,
   create_by          VARCHAR2(64)         default NULL,
   create_date        DATE,
   update_by          VARCHAR2(64)         default NULL,
   update_date        DATE,
   constraint PK_HT_SYS_USER primary key (id)
);

comment on column ht_sys_user.id is
'主键';

comment on column ht_sys_user.login_code is
'登录账号(唯一)';

comment on column ht_sys_user.company_id is
'所属公司';

comment on column ht_sys_user.dept_id is
'所属部门';

comment on column ht_sys_user.user_name is
'昵称';

comment on column ht_sys_user.password is
'密码';

comment on column ht_sys_user.email is
'邮箱';

comment on column ht_sys_user.mobile is
'手机号';

comment on column ht_sys_user.phone is
'办公电话';

comment on column ht_sys_user.sex is
'性别(1男2女)，数据字典设置';

comment on column ht_sys_user.avatar is
'头像路径';

comment on column ht_sys_user.sign is
'个性签名';

comment on column ht_sys_user.wx_openid is
'绑定的微信号';

comment on column ht_sys_user.user_type is
'用户类型(数据字典设置，0普通用户，1 二级管理员，2 系统管理员)';

comment on column ht_sys_user.status is
'状态（0正常1停用）';

comment on column ht_sys_user.remarks is
'备注信息';

comment on column ht_sys_user.create_by is
'创建者主键';

comment on column ht_sys_user.update_by is
'修改者主键';

/*==============================================================*/
/* Table: ht_sys_user_post                                    */
/*==============================================================*/
create table ht_sys_user_post
(
   id                 VARCHAR2(64)         not null,
   post_id            VARCHAR2(64)         default NULL,
   user_id            VARCHAR2(64)         default NULL,
   constraint PK_HT_SYS_USER_POST primary key (id)
);

comment on column ht_sys_user_post.id is
'主键';

comment on column ht_sys_user_post.post_id is
'岗位主键';

comment on column ht_sys_user_post.user_id is
'用户主键';

/*==============================================================*/
/* Table: qrtz_blob_triggers                                  */
/*==============================================================*/
create table qrtz_blob_triggers
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   BLOB_DATA            BLOB,
   constraint PK_QRTZ_BLOB_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_calendars                                      */
/*==============================================================*/
create table qrtz_calendars
(
   SCHED_NAME           VARCHAR2(120)        not null,
   CALENDAR_NAME        VARCHAR2(200)        not null,
   CALENDAR             BLOB                 not null,
   constraint PK_QRTZ_CALENDARS primary key (SCHED_NAME, CALENDAR_NAME)
);

/*==============================================================*/
/* Table: qrtz_cron_triggers                                  */
/*==============================================================*/
create table qrtz_cron_triggers
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   CRON_EXPRESSION      VARCHAR2(200)        not null,
   TIME_ZONE_ID         VARCHAR2(80)         default NULL,
   constraint PK_QRTZ_CRON_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_fired_triggers                                 */
/*==============================================================*/
create table qrtz_fired_triggers
(
   SCHED_NAME           VARCHAR2(120)        not null,
   ENTRY_ID             VARCHAR2(95)         not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   INSTANCE_NAME        VARCHAR2(200)        not null,
   FIRED_TIME           INTEGER              not null,
   SCHED_TIME           INTEGER              not null,
   PRIORITY             INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   JOB_NAME             VARCHAR2(200)        default NULL,
   JOB_GROUP            VARCHAR2(200)        default NULL,
   IS_NONCONCURRENT     VARCHAR2(1)          default NULL,
   REQUESTS_RECOVERY    VARCHAR2(1)          default NULL,
   constraint PK_QRTZ_FIRED_TRIGGERS primary key (SCHED_NAME, ENTRY_ID)
);

/*==============================================================*/
/* Table: qrtz_job_details                                    */
/*==============================================================*/
create table qrtz_job_details
(
   SCHED_NAME           VARCHAR2(120)        not null,
   JOB_NAME             VARCHAR2(200)        not null,
   JOB_GROUP            VARCHAR2(200)        not null,
   DESCRIPTION          VARCHAR2(250)        default NULL,
   JOB_CLASS_NAME       VARCHAR2(250)        not null,
   IS_DURABLE           VARCHAR2(1)          not null,
   IS_NONCONCURRENT     VARCHAR2(1)          not null,
   IS_UPDATE_DATA       VARCHAR2(1)          not null,
   REQUESTS_RECOVERY    VARCHAR2(1)          not null,
   JOB_DATA             BLOB,
   constraint PK_QRTZ_JOB_DETAILS primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

/*==============================================================*/
/* Table: qrtz_locks                                          */
/*==============================================================*/
create table qrtz_locks
(
   SCHED_NAME           VARCHAR2(120)        not null,
   LOCK_NAME            VARCHAR2(40)         not null,
   constraint PK_QRTZ_LOCKS primary key (SCHED_NAME, LOCK_NAME)
);

/*==============================================================*/
/* Table: qrtz_paused_trigger_grps                            */
/*==============================================================*/
create table qrtz_paused_trigger_grps
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   constraint PK_QRTZ_PAUSED_TRIGGER_GRPS primary key (SCHED_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_scheduler_state                                */
/*==============================================================*/
create table qrtz_scheduler_state
(
   SCHED_NAME           VARCHAR2(120)        not null,
   INSTANCE_NAME        VARCHAR2(200)        not null,
   LAST_CHECKIN_TIME    INTEGER              not null,
   CHECKIN_INTERVAL     INTEGER              not null,
   constraint PK_QRTZ_SCHEDULER_STATE primary key (SCHED_NAME, INSTANCE_NAME)
);

/*==============================================================*/
/* Table: qrtz_simple_triggers                                */
/*==============================================================*/
create table qrtz_simple_triggers
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   REPEAT_COUNT         INTEGER              not null,
   REPEAT_INTERVAL      INTEGER              not null,
   TIMES_TRIGGERED      INTEGER              not null,
   constraint PK_QRTZ_SIMPLE_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_simprop_triggers                               */
/*==============================================================*/
create table qrtz_simprop_triggers
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   STR_PROP_1           VARCHAR2(512)        default NULL,
   STR_PROP_2           VARCHAR2(512)        default NULL,
   STR_PROP_3           VARCHAR2(512)        default NULL,
   INT_PROP_1           INTEGER              default NULL,
   INT_PROP_2           INTEGER              default NULL,
   LONG_PROP_1          INTEGER              default NULL,
   LONG_PROP_2          INTEGER              default NULL,
   DEC_PROP_1           NUMBER(13,4)         default NULL,
   DEC_PROP_2           NUMBER(13,4)         default NULL,
   BOOL_PROP_1          VARCHAR2(1)          default NULL,
   BOOL_PROP_2          VARCHAR2(1)          default NULL,
   constraint PK_QRTZ_SIMPROP_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_triggers                                       */
/*==============================================================*/
create table qrtz_triggers
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   JOB_NAME             VARCHAR2(200)        not null,
   JOB_GROUP            VARCHAR2(200)        not null,
   DESCRIPTION          VARCHAR2(250)        default NULL,
   NEXT_FIRE_TIME       INTEGER              default NULL,
   PREV_FIRE_TIME       INTEGER              default NULL,
   PRIORITY             INTEGER              default NULL,
   TRIGGER_STATE        VARCHAR2(16)         not null,
   TRIGGER_TYPE         VARCHAR2(8)          not null,
   START_TIME           INTEGER              not null,
   END_TIME             INTEGER              default NULL,
   CALENDAR_NAME        VARCHAR2(200)        default NULL,
   MISFIRE_INSTR        SMALLINT             default NULL,
   JOB_DATA             BLOB,
   constraint PK_QRTZ_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
   constraint SCHED_NAME unique (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

/*==============================================================*/
/* Table: test_data                                           */
/*==============================================================*/
create table test_data
(
   id                 VARCHAR2(64)         not null,
   test_input         VARCHAR2(200)        default NULL,
   test_textarea      VARCHAR2(200)        default NULL,
   test_select        VARCHAR2(10)         default NULL,
   test_select_multiple VARCHAR2(200)        default NULL,
   test_radio         VARCHAR2(10)         default NULL,
   test_checkbox      VARCHAR2(200)        default NULL,
   test_date          DATE,
   test_datetime      DATE,
   test_user_code     VARCHAR2(64)         default NULL,
   test_office_code   VARCHAR2(64)         default NULL,
   test_area_code     VARCHAR2(64)         default NULL,
   test_area_name     VARCHAR2(100)        default NULL,
   status             CHAR(1)              default '0' not null,
   create_by          VARCHAR2(64)         not null,
   create_date        DATE,
   update_by          VARCHAR2(64)         not null,
   update_date        DATE,
   remarks            VARCHAR2(500)        default NULL,
   constraint PK_TEST_DATA primary key (id)
);

comment on column test_data.id is
'编号';

comment on column test_data.test_input is
'单行文本';

comment on column test_data.test_textarea is
'多行文本';

comment on column test_data.test_select is
'下拉框';

comment on column test_data.test_select_multiple is
'下拉多选';

comment on column test_data.test_radio is
'单选框';

comment on column test_data.test_checkbox is
'复选框';

comment on column test_data.test_user_code is
'用户选择';

comment on column test_data.test_office_code is
'机构选择';

comment on column test_data.test_area_code is
'区域选择';

comment on column test_data.test_area_name is
'区域名称';

comment on column test_data.status is
'状态（0正常 1删除 2停用）';

comment on column test_data.create_by is
'创建者';

comment on column test_data.update_by is
'更新者';

comment on column test_data.remarks is
'备注信息';

/*==============================================================*/
/* Table: test_data_child                                     */
/*==============================================================*/
create table test_data_child
(
   id                 VARCHAR2(64)         not null,
   test_sort          INTEGER              default NULL,
   test_data_id       VARCHAR2(64)         default NULL,
   test_input         VARCHAR2(200)        default NULL,
   test_textarea      VARCHAR2(200)        default NULL,
   test_select        VARCHAR2(10)         default NULL,
   test_select_multiple VARCHAR2(200)        default NULL,
   test_radio         VARCHAR2(10)         default NULL,
   test_checkbox      VARCHAR2(200)        default NULL,
   test_date          DATE,
   test_datetime      DATE,
   test_user_code     VARCHAR2(64)         default NULL,
   test_office_code   VARCHAR2(64)         default NULL,
   test_area_code     VARCHAR2(64)         default NULL,
   test_area_name     VARCHAR2(100)        default NULL,
   constraint PK_TEST_DATA_CHILD primary key (id)
);

comment on column test_data_child.id is
'编号';

comment on column test_data_child.test_sort is
'排序号';

comment on column test_data_child.test_data_id is
'父表主键';

comment on column test_data_child.test_input is
'单行文本';

comment on column test_data_child.test_textarea is
'多行文本';

comment on column test_data_child.test_select is
'下拉框';

comment on column test_data_child.test_select_multiple is
'下拉多选';

comment on column test_data_child.test_radio is
'单选框';

comment on column test_data_child.test_checkbox is
'复选框';

comment on column test_data_child.test_user_code is
'用户选择';

comment on column test_data_child.test_office_code is
'机构选择';

comment on column test_data_child.test_area_code is
'区域选择';

comment on column test_data_child.test_area_name is
'区域名称';

alter table qrtz_triggers
add constraint qrtz_triggers_ibfk_1 foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP)
references qrtz_job_details (SCHED_NAME, JOB_NAME, JOB_GROUP);

