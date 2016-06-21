/*==============================================================*/
/*	添加 项目下线菜单项                                                                                         */
/*==============================================================*/
delete from sys_menu where id in ('5a914b6b754c4bc1977b36aa9e0d0999');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5a914b6b754c4bc1977b36aa9e0d0999', 'b3b6ba3bba644220b3eb9528bebe2f58', '0,1,44aee8dec38849159118f41a4767ece2,b3b6ba3bba644220b3eb9528bebe2f58,', '项目下线', '570', '/project/projectBaseInfo/downlineList', '', 'icon-book', '1', 'project:projectBaseInfo:downline', '1', '2016-03-18 13:53:28', '1', '2016-03-18 13:53:28', '', '0');

/*==============================================================*/
/*	增加 银行卡状态字段                                                                               */
/*==============================================================*/
ALTER TABLE `bank_info`
ADD COLUMN `status`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态' AFTER `logo`;

/*==============================================================*/
/*	初始化银行卡状态                                                                               */
/*==============================================================*/
update bank_info set `status`='1';

/*==============================================================*/
/*	增加 银行卡状态数据字典                                                                            */
/*==============================================================*/
DELETE FROM  sys_dict where id in ('c26c4bdda3a94e29ab33ac351b5d4ca2','dce65ab9d8c941858808cda827cb6a4c');
INSERT INTO `sys_dict` VALUES ('c26c4bdda3a94e29ab33ac351b5d4ca2', '0', '无效', 'bank_info_status_dict', '银行卡状态', 30, '0', '1', '2016-3-21 13:45:42', '1', '2016-3-21 13:45:42', '无效', '0');
INSERT INTO `sys_dict` VALUES ('dce65ab9d8c941858808cda827cb6a4c', '1', '正常', 'bank_info_status_dict', '银行卡状态', 10, '0', '1', '2016-3-21 13:46:20', '1', '2016-3-21 13:46:20', '正常', '0');

/*创建数据自定义查询相关表、菜单、权限*/
drop table if exists data_query;

/*==============================================================*/
/* Table: data_query                                            */
/*==============================================================*/
create table data_query
(
   id                   bigint not null auto_increment comment '编号',
   name                 varchar(50) comment '名称',
   from_content         text comment 'fromContent',
   filter_content       text comment 'filterContent',
   group_by_content     varchar(500) comment 'groupByContent',
   having_content       varchar(500) comment 'havingContent',
   order_by_content     varchar(500) comment 'orderByContent',
   limit_content        varchar(500) comment 'limitContent',
   description          varchar(500) comment '说明',
   primary key (id)
);

alter table data_query comment '数据查询';

drop table if exists data_query_row;

/*==============================================================*/
/* Table: data_query_row                                        */
/*==============================================================*/
create table data_query_row
(
   id                   bigint not null auto_increment comment '编号',
   query_id             bigint comment '查询编号',
   row_name             varchar(100) comment '列名',
   show_row_name        varchar(100) comment '列显示名',
   sort                 int comment '排序',
   sortable             varchar(10) comment '是否可排序',
   dict_type            varchar(100) comment '数据字典',
   date_format          varchar(50) comment '日期格式化',
   primary key (id)
);

alter table data_query_row comment '查询数据列';

drop table if exists data_query_form;

/*==============================================================*/
/* Table: data_query_form                                       */
/*==============================================================*/
create table data_query_form
(
   id                   bigint not null auto_increment comment '编号',
   query_id             bigint comment '查询编号',
   label                varchar(50) comment '标签',
   parameter            varchar(50) comment '变量名',
   show_type            varchar(20) comment '表单显示类型',
   date_format          varchar(50) comment '日期格式化',
   dict_type            varchar(100) comment '数据字典',
   expression           text comment '表达式',
   nullable             varchar(10) comment '是否可空',
   sort                 int comment '排序',
   primary key (id)
);

alter table data_query_form comment '数据查询表单';

drop table if exists data_query_menu;

/*==============================================================*/
/* Table: data_query_menu                                       */
/*==============================================================*/
create table data_query_menu
(
   id                   bigint not null auto_increment comment '编号',
   query_id             bigint comment '查询编号',
   menu_id              varchar(64) comment '菜单编号',
   title                varchar(50) comment '标题',
   primary key (id)
);

alter table data_query_menu comment '数据查询关联菜单';

delete from sys_menu where id in ('13d980293e094814a28d461f958dd3e8','81f376ffc2fc4dceacbe3ce90651bc0c','38ba174bf86b4d1bb2c88d0ca46e32c6','8a77fe445a3a45618a6b0bc7c65bcd5d');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('13d980293e094814a28d461f958dd3e8', '2', '0,1,2,', '自定义数据查询', '2040', '', '', '', '1', '', '1', '2016-03-17 14:59:40', '1', '2016-03-17 15:00:01', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('38ba174bf86b4d1bb2c88d0ca46e32c6', '81f376ffc2fc4dceacbe3ce90651bc0c', '0,1,2,13d980293e094814a28d461f958dd3e8,81f376ffc2fc4dceacbe3ce90651bc0c,', '查看-数据查询', '30', '', '', '', '0', 'operation:dataQuery:view', '1', '2016-03-17 15:02:10', '1', '2016-03-17 15:02:10', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('81f376ffc2fc4dceacbe3ce90651bc0c', '13d980293e094814a28d461f958dd3e8', '0,1,2,13d980293e094814a28d461f958dd3e8,', '数据查询配置', '30', '/operation/dataQuery', '', 'icon-circle-blank', '1', '', '1', '2016-03-17 15:01:21', '1', '2016-03-17 15:01:21', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8a77fe445a3a45618a6b0bc7c65bcd5d', '81f376ffc2fc4dceacbe3ce90651bc0c', '0,1,2,13d980293e094814a28d461f958dd3e8,81f376ffc2fc4dceacbe3ce90651bc0c,', '编辑-数据查询', '60', '', '', '', '0', 'operation:dataQuery:edit', '1', '2016-03-17 15:02:35', '1', '2016-03-17 15:02:35', '', '0');
delete from sys_role_menu where menu_id in ('13d980293e094814a28d461f958dd3e8','81f376ffc2fc4dceacbe3ce90651bc0c','38ba174bf86b4d1bb2c88d0ca46e32c6','8a77fe445a3a45618a6b0bc7c65bcd5d');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', '13d980293e094814a28d461f958dd3e8');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', '38ba174bf86b4d1bb2c88d0ca46e32c6');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', '81f376ffc2fc4dceacbe3ce90651bc0c');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', '8a77fe445a3a45618a6b0bc7c65bcd5d');

ALTER TABLE `data_query_menu`
ADD COLUMN `exportable`  varchar(10) NULL COMMENT '能否导出数据' AFTER `title`;


