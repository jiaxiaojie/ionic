/*==============================================================*/
/*	customer_account表添加列：register_source(varchar(500),'注册来源')*/
/*	此sql不可重复执行                                                                                                                   */
/*==============================================================*/
ALTER TABLE `customer_account`
ADD COLUMN `register_source`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册来源' AFTER `outer_id`;

drop table if exists message_create_rule_channel;

/*==============================================================*/
/* Table: message_create_rule_channel                           */
/*==============================================================*/
create table message_create_rule_channel
(
   id                   bigint not null auto_increment comment '编号',
   rule_id              bigint comment '消息产生规则编号',
   message_channel      varchar(2) comment '渠道',
   primary key (id)
);

alter table message_create_rule_channel comment '消息产生规则发送渠道';
drop table if exists message_behavior_type;

/*==============================================================*/
/* Table: message_behavior_type                                 */
/*==============================================================*/
create table message_behavior_type
(
   id                   int not null auto_increment comment '流水号',
   behavior_code        varchar(6) comment '行为编号',
   behavior_name        varchar(200) comment '行为名称',
   primary key (id)
);

alter table message_behavior_type comment '行为编码';
drop table if exists message_create_rule;

/*==============================================================*/
/* Table: message_create_rule                                   */
/*==============================================================*/
create table message_create_rule
(
   id                   bigint not null auto_increment comment '编号',
   name                 varchar(30) comment '名称',
   behavior_code        varchar(5) comment '行为编号',
   message_title        varchar(20) comment '消息标题',
   message_content      varchar(280) comment '消息内容',
   message_type         varchar(2) comment '消息类型',
   label               varchar(30) comment '标签',
   status               varchar(2) comment '状态',
   class_name           varchar(40) comment '实现类名',
   start_date           date comment '开始日期',
   end_date             date comment '结束日期',
   start_time           time comment '开始时间段',
   end_time             time comment '结束时间段',
   create_dt            datetime comment '创建时间',
   creator              bigint comment '创建人',
   review_dt            datetime comment '审批时间',
   reviewer             bigint comment '审批人',
   primary key (id)
);

alter table message_create_rule comment '消息产生规则';

drop table if exists custom_message_send_channel;

/*==============================================================*/
/* Table: custom_message_send_channel                           */
/*==============================================================*/
create table custom_message_send_channel
(
   id                   bigint not null auto_increment comment '编号',
   customer_message_id  bigint comment '自定义消息编号',
   message_channel      varchar(2) comment '渠道',
   primary key (id)
);

alter table custom_message_send_channel comment '自定义消息发送渠道';
drop table if exists custom_message;

/*==============================================================*/
/* Table: custom_message                                        */
/*==============================================================*/
create table custom_message
(
   id                   bigint not null auto_increment comment '编号',
   title                varchar(20) comment '标题',
   content              varchar(280) comment '内容',
   receiver_type        varchar(2) comment '接收用户类型',
   receiver_data        varchar(200) comment '接收用户',
   type                 varchar(2) comment '消息类型',
   label                varchar(30) comment '标签',
   send_dt              datetime comment '发送时间',
   status               varchar(2) comment '状态',
   create_dt            datetime comment '创建时间',
   creator              bigint comment '创建人',
   review_dt            datetime comment '审批时间',
   review_remark        varchar(100) comment '审批意见',
   reviewer             bigint comment '审批人',
   primary key (id)
);

alter table custom_message comment '自定义消息';
drop table if exists account_select_sql;

/*==============================================================*/
/* Table: account_select_sql                                    */
/*==============================================================*/
create table account_select_sql
(
   id                   bigint not null auto_increment comment '编号',
   name                 varchar(30) comment '名称',
   sql_content          text comment 'sql_content',
   primary key (id)
);

alter table account_select_sql comment '账户查询sql';
drop table if exists receive_message_switch;

/*==============================================================*/
/* Table: receive_message_switch                                */
/*==============================================================*/
create table receive_message_switch
(
   id                   bigint not null auto_increment comment '编号',
   message_type         varchar(2) comment '消息类型',
   message_channel      varchar(2) comment '渠道',
   account_id           bigint comment '账户编号',
   is_receive           varchar(2) comment '是否接收',
   primary key (id)
);

alter table receive_message_switch comment '用户接收消息开关';
drop table if exists message;

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   id                   bigint not null auto_increment comment '编号',
   account_id           bigint comment '账户编号',
   title                varchar(20) comment '标题',
   content              varchar(280) comment '内容',
   send_dt              datetime comment '预计发送时间',
   type                 varchar(2) comment '类型',
   label                varchar(30) comment '标签',
   from_type            varchar(2) comment '来源类型',
   from_id              bigint comment '来源编号',
   primary key (id)
);

alter table message comment '消息';
drop table if exists receive_sms_message_time;

/*==============================================================*/
/* Table: receive_sms_message_time                              */
/*==============================================================*/
create table receive_sms_message_time
(
   id                   bigint not null auto_increment comment '编号',
   account_id           bigint comment '账户编号',
   start_time           time comment '开始时间段',
   end_time             time comment '结束时间段',
   primary key (id)
);

alter table receive_sms_message_time comment '用户接收短信时间段';
drop table if exists message_instance;

/*==============================================================*/
/* Table: message_instance                                      */
/*==============================================================*/
create table message_instance
(
   id                   bigint not null auto_increment comment '编号',
   message_id           bigint comment '消息编号',
   message_channel      varchar(2) comment '渠道',
   create_dt            datetime comment '创建时间',
   send_dt              datetime comment '发送时间',
   read_dt              datetime comment '阅读时间',
   delete_dt            datetime comment '删除时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table message_instance comment '消息实例';

drop table if exists message_create_rule_term;

/*==============================================================*/
/* Table: message_create_rule_term                              */
/*==============================================================*/
create table message_create_rule_term
(
   id                   bigint not null auto_increment comment '编号',
   rule_id              bigint comment '消息产生规则编号',
   op_term              varchar(2) comment '终端',
   primary key (id)
);

alter table message_create_rule_term comment '消息产生规则适用终端';


delete from sys_dict where id in('012134e6a625408bba7185c5cd9dbece','050e1316ad004bbab4c3f71ed2fb23a9','124e5b7ba1cc4384b7fd58cd404d595c','15fa7114743d4cd98445b4c7cf378ed8','224636a669aa4e0599152fd447b2bcea','26f8387e2724471d86295cb4a2d03e24','30c687aef2bf415a9ce83b4fad32587a','41afc2351bf74d7aba16347d1b8439cb','426b7001abcc4fa5a59cf2695c1f030a',
	'4615c3b234584bd7ac858d2428139495','4b2711b8cff144868e9a515e93bc3ed5','4d15cf2c475b4aaf916560b683beb5d2','52ee33fbac5c4d8991f07b213ddaffc6','5a7dc2459b1c483f869c3dbfef295d9e','5aaf7cac6d214e2fa6d950dbfee40dea','5b426a3c97964f91a308dc109a767bcc','7a1537e1f3414899923cde0a1265674a','8b7a2645b8554add855ea225a95884ef','93bb515ca57643e0bf2d13ac21afa6ec',
	'983eea8677db43d087ae1a49cdad33d0','a61ca28219e8472fa085bdcda81d2be9','a88edbe22a9f40dfa08413b7ea0f7345','ad7d3ddf48eb4218bd5348cf6a1ece94','bc2ba1f0e0f8430d8bf9b6d8b5b0bea1','d0c9049efdca49d69f44882fb64b88bd','d1d156821c1843fdbc895e481e871b90','d5cc063b7c034a66bfd73c65dc748542','d81d02355230497d8acae0519275897d','ef7540755a504309a2f9081f563e8f7d',
	'faec8a92eaba4b94ba826d14dfb847c1','fd94ce2129384fc98b1be6060361b61d','cfaaed39bcd04051af5fd1c7bff1a884');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('012134e6a625408bba7185c5cd9dbece', '1', '特定用户', 'custom_message_receiver_type', '自定义消息接收用户类型', '20', '0', '1', '2016-01-11 16:39:22', '1', '2016-01-11 16:42:31', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('124e5b7ba1cc4384b7fd58cd404d595c', '0', '接收', 'is_receive_message', '是否接收消息', '10', '0', '1', '2016-01-11 14:56:50', '1', '2016-01-11 14:59:05', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('224636a669aa4e0599152fd447b2bcea', '-1', '审批不通过', 'message_rule_status', '消息产生规则状态', '5', '0', '1', '2016-01-11 14:42:52', '1', '2016-01-11 14:43:59', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('26f8387e2724471d86295cb4a2d03e24', '0', '上传文件', 'custom_message_receiver_type', '自定义消息接收用户类型', '10', '0', '1', '2016-01-11 16:39:35', '1', '2016-01-11 16:39:35', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('30c687aef2bf415a9ce83b4fad32587a', '-2', '失效', 'message_status', '消息状态', '2', '0', '1', '2016-01-11 15:02:29', '1', '2016-01-11 15:02:29', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('426b7001abcc4fa5a59cf2695c1f030a', '1', '已发送', 'message_status', '消息状态', '20', '0', '1', '2016-01-11 15:00:46', '1', '2016-01-11 15:01:28', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4615c3b234584bd7ac858d2428139495', '2', '已读', 'message_status', '消息状态', '30', '0', '1', '2016-01-11 15:00:59', '1', '2016-01-11 15:00:59', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('52ee33fbac5c4d8991f07b213ddaffc6', '5', '邮件', 'message_channel', '消息发送渠道', '60', '0', '1', '2016-01-13 15:22:31', '1', '2016-01-13 15:22:31', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5a7dc2459b1c483f869c3dbfef295d9e', '0', '规则产生', 'message_from_type', '消息来源类型', '10', '0', '1', '2016-01-11 15:03:29', '1', '2016-01-11 15:03:29', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5aaf7cac6d214e2fa6d950dbfee40dea', '2', 'android', 'message_channel', '消息发送渠道', '30', '0', '1', '2016-01-13 15:22:05', '1', '2016-01-13 15:22:05', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5b426a3c97964f91a308dc109a767bcc', '1', '自定义产生', 'message_from_type', '消息来源类型', '20', '0', '1', '2016-01-11 15:03:39', '1', '2016-01-11 15:03:39', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7a1537e1f3414899923cde0a1265674a', '2', '执行结束', 'message_rule_status', '消息产生规则状态', '30', '0', '1', '2016-01-11 14:43:21', '1', '2016-01-11 14:44:09', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8b7a2645b8554add855ea225a95884ef', '4', '短信', 'message_channel', '消息发送渠道', '50', '0', '1', '2016-01-13 15:22:18', '1', '2016-01-13 15:22:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('983eea8677db43d087ae1a49cdad33d0', '-1', '已删除', 'message_status', '消息状态', '5', '0', '1', '2016-01-11 15:01:14', '1', '2016-01-11 15:01:14', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a61ca28219e8472fa085bdcda81d2be9', '0', '创建', 'message_rule_status', '消息产生规则状态', '10', '0', '1', '2016-01-11 14:41:58', '1', '2016-01-11 14:41:58', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a88edbe22a9f40dfa08413b7ea0f7345', '1', '微信', 'message_channel', '消息发送渠道', '20', '0', '1', '2016-01-13 15:21:56', '1', '2016-01-13 15:21:56', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bc2ba1f0e0f8430d8bf9b6d8b5b0bea1', '3', 'ios', 'message_channel', '消息发送渠道', '40', '0', '1', '2016-01-13 15:22:11', '1', '2016-01-13 15:22:11', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d1d156821c1843fdbc895e481e871b90', '1', '用户消息', 'message_type', '消息类型', '20', '0', '1', '2016-01-11 14:47:07', '1', '2016-01-11 14:47:07', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d5cc063b7c034a66bfd73c65dc748542', '1', '审批通过', 'message_rule_status', '消息产生规则状态', '20', '0', '1', '2016-01-11 14:42:15', '1', '2016-01-11 14:44:05', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d81d02355230497d8acae0519275897d', '0', '系统消息', 'message_type', '消息类型', '10', '0', '1', '2016-01-11 14:46:52', '1', '2016-01-11 14:46:52', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ef7540755a504309a2f9081f563e8f7d', '0', 'web', 'message_channel', '消息发送渠道', '10', '0', '1', '2016-01-13 15:21:49', '1', '2016-01-13 15:21:49', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('faec8a92eaba4b94ba826d14dfb847c1', '0', '创建', 'message_status', '消息状态', '10', '0', '1', '2016-01-11 15:00:28', '1', '2016-01-11 15:00:28', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('fd94ce2129384fc98b1be6060361b61d', '1', '不接收', 'is_receive_message', '是否接收消息', '20', '0', '1', '2016-01-11 14:57:01', '1', '2016-01-11 14:59:19', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('050e1316ad004bbab4c3f71ed2fb23a9', '0', '创建', 'custom_message_stauts', '自定义消息状态', '10', '0', '1', '2016-01-11 14:45:22', '1', '2016-01-11 14:45:22', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('15fa7114743d4cd98445b4c7cf378ed8', '-1', '审批不通过', 'custom_message_stauts', '自定义消息状态', '5', '0', '1', '2016-01-11 14:45:39', '1', '2016-01-11 14:45:39', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('93bb515ca57643e0bf2d13ac21afa6ec', '3', '执行结束', 'custom_message_stauts', '自定义消息状态', '30', '0', '1', '2016-01-11 14:45:58', '1', '2016-01-19 16:08:50', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ad7d3ddf48eb4218bd5348cf6a1ece94', '2', '审批通过', 'custom_message_stauts', '自定义消息状态', '20', '0', '1', '2016-01-11 14:45:50', '1', '2016-01-19 16:08:38', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cfaaed39bcd04051af5fd1c7bff1a884', '1', '提交审批', 'custom_message_stauts', '自定义消息状态', '15', '0', '1', '2016-01-19 16:08:17', '1', '2016-01-19 16:09:22', '', '0');




/*==============================================================*/
/* 增加用户状态字典								*/
/*==============================================================*/
delete from sys_dict where id = '3686aa98dd4f44ab9d8ef34f89036b79';
delete from sys_dict where id = '4c0940303b6d4f5690ddaafef634218f';
delete from sys_dict where id = '4d753a072599411eb3af8ff5a95d7fb6';
delete from sys_dict where id = 'e0914f64bd8c47cf954092914f65fcfc';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3686aa98dd4f44ab9d8ef34f89036b79', '3', '已投资', 'user_status', '客户状态', '40', '0', '1', '2016-01-11 18:59:20', '1', '2016-01-11 18:59:20', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4c0940303b6d4f5690ddaafef634218f', '2', '已绑卡', 'user_status', '客户状态', '30', '0', '1', '2016-01-11 18:59:06', '1', '2016-01-11 18:59:06', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4d753a072599411eb3af8ff5a95d7fb6', '1', '已开通第三方账号', 'user_status', '客户状态', '20', '0', '1', '2016-01-11 18:58:36', '1', '2016-01-11 18:58:36', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e0914f64bd8c47cf954092914f65fcfc', '0', '已注册', 'user_status', '客户状态', '10', '0', '1', '2016-01-11 18:57:40', '1', '2016-01-11 18:58:01', '', '0');

/*==============================================================*/
/* 增加客服查询系统菜单项							*/
/*==============================================================*/
delete from sys_menu where id='7ca8547d194e441cb9b9764d79acb7ac';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7ca8547d194e441cb9b9764d79acb7ac', '302f3b2d42764abcbefd99046b39ac8b', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,', '客服查询', '220', '/customer/customerServiceInquiry/list', '', 'icon-phone-sign', '1', 'customer:customerServiceInquiry:view', '1', '2016-01-11 11:12:19', '1', '2016-01-12 16:38:15', '', '0');
delete from sys_role_menu where menu_id='7ca8547d194e441cb9b9764d79acb7ac';
INSERT INTO `sys_role_menu` VALUES ('1', '7ca8547d194e441cb9b9764d79acb7ac');


drop table if exists marketing_money_award_record;

/*==============================================================*/
/* Table: marketing_money_award_record                          */
/*==============================================================*/
create table marketing_money_award_record
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '用户编号',
   amount               decimal(13,2) comment '金额',
   parameters           text comment '参数',
   status               varchar(2) comment '状态',
   create_dt            datetime comment '创建时间',
   finish_dt            datetime comment '完成时间',
   primary key (id)
);

alter table marketing_money_award_record comment '活动现金奖励记录';

delete from sys_dict where id in ('139fafee992040af9048d326f5a3f6f4','b2d09b0a491742348e8abfa98d011e14','e85b214a4b6f4e17a2058854cbe05b54');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('139fafee992040af9048d326f5a3f6f4', '1', '已赠送', 'marketing_money_award_status', '活动现金奖励记录状态', '20', '0', '1', '2016-01-18 13:13:28', '1', '2016-01-18 13:13:28', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b2d09b0a491742348e8abfa98d011e14', '-1', '取消', 'marketing_money_award_status', '活动现金奖励记录状态', '5', '0', '1', '2016-01-18 13:13:55', '1', '2016-01-18 13:13:55', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e85b214a4b6f4e17a2058854cbe05b54', '0', '待赠送', 'marketing_money_award_status', '活动现金奖励记录状态', '10', '0', '1', '2016-01-18 13:13:07', '1', '2016-01-18 13:13:44', '', '0');

/*==============================================================*/
/* 增加消息管理菜单项	及权限				*/
/*==============================================================*/
delete from sys_menu where id='12cd17ac4f434301b5f4861dce620f56';
delete from sys_menu where id='b55313139685487a84fb1ab6f539182d';
delete from sys_menu where id='99fa71156b7845fd82d1e7570f1c1091';
delete from sys_menu where id='7750a9fb4f5d48b6a0a3a6d362895ae7';
delete from sys_menu where id='bbfd91d5402e4f31a9532d4da3c50b7a';
delete from sys_menu where id='f6d3155217fe4983b783cece53871e48';
delete from sys_menu where id='40f6a1a26f11438385fc39eb94f09177';
delete from sys_menu where id='747596340176481ab009340dbcfe3d03';
delete from sys_menu where id='816e14ad64da48b29357d6fb273e303a';
delete from sys_menu where id='02aa82f8f45e41dea50eb6c2ba467a77';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('12cd17ac4f434301b5f4861dce620f56', 'b55313139685487a84fb1ab6f539182d', '0,1,b55313139685487a84fb1ab6f539182d,', '消息管理', '30', '', '', '', '1', '', '1', '2016-01-14 15:50:56', '1', '2016-01-14 17:58:15', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b55313139685487a84fb1ab6f539182d', '1', '0,1,', '消息管理', '5120', '', '', '', '1', '', '1', '2016-01-14 15:50:37', '1', '2016-01-14 15:51:04', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('99fa71156b7845fd82d1e7570f1c1091', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息-创建', '60', '', '', '', '0', 'message:customMessage:create', '1', '2016-01-15 14:32:54', '1', '2016-01-19 14:59:41', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7750a9fb4f5d48b6a0a3a6d362895ae7', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息-查看', '90', '', '', '', '0', 'message:customMessage:view', '1', '2016-01-15 14:43:13', '1', '2016-01-19 15:00:04', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bbfd91d5402e4f31a9532d4da3c50b7a', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息-审批', '120', '', '', '', '0', 'message:customMessage:review', '1', '2016-01-15 14:43:54', '1', '2016-01-19 15:00:15', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f6d3155217fe4983b783cece53871e48', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息-编辑', '140', '', '', '', '0', 'message:customMessage:edit', '1', '2016-01-15 14:45:17', '1', '2016-01-19 15:00:25', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('40f6a1a26f11438385fc39eb94f09177', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息列表', '150', '/message/customMessage/list', '', 'icon-cogs', '1', 'message:customMessage:view', '1', '2016-01-14 19:32:57', '1', '2016-01-19 14:52:33', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('747596340176481ab009340dbcfe3d03', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '创建消息', '180', '/message/customMessage/createList', '', 'icon-sign-blank', '1', 'message:customMessage:create', '1', '2016-01-14 19:36:22', '1', '2016-01-14 19:39:58', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('816e14ad64da48b29357d6fb273e303a', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息审批', '210', '/message/customMessage/reviewList', '', 'icon-ok', '1', 'message:customMessage:review', '1', '2016-01-15 11:00:24', '1', '2016-01-15 11:03:26', '', '0');
/*INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('02aa82f8f45e41dea50eb6c2ba467a77', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息查询', '240', '/message/messageInstance/searchPageList', '', 'icon-globe', '1', 'message:messageInstance:view', '1', '2016-01-18 09:57:26', '1', '2016-01-18 10:08:42', '', '0');*/
delete from sys_role_menu where menu_id='12cd17ac4f434301b5f4861dce620f56';
delete from sys_role_menu where menu_id='b55313139685487a84fb1ab6f539182d';
delete from sys_role_menu where menu_id='99fa71156b7845fd82d1e7570f1c1091';
delete from sys_role_menu where menu_id='7750a9fb4f5d48b6a0a3a6d362895ae7';
delete from sys_role_menu where menu_id='bbfd91d5402e4f31a9532d4da3c50b7a';
delete from sys_role_menu where menu_id='f6d3155217fe4983b783cece53871e48';
delete from sys_role_menu where menu_id='40f6a1a26f11438385fc39eb94f09177';
delete from sys_role_menu where menu_id='747596340176481ab009340dbcfe3d03';
delete from sys_role_menu where menu_id='816e14ad64da48b29357d6fb273e303a';
delete from sys_role_menu where menu_id='02aa82f8f45e41dea50eb6c2ba467a77';
INSERT INTO `sys_role_menu` VALUES ('1', '99fa71156b7845fd82d1e7570f1c1091');
INSERT INTO `sys_role_menu` VALUES ('1', '12cd17ac4f434301b5f4861dce620f56');
INSERT INTO `sys_role_menu` VALUES ('1', 'b55313139685487a84fb1ab6f539182d');
INSERT INTO `sys_role_menu` VALUES ('1', '7750a9fb4f5d48b6a0a3a6d362895ae7');
INSERT INTO `sys_role_menu` VALUES ('1', 'bbfd91d5402e4f31a9532d4da3c50b7a');
INSERT INTO `sys_role_menu` VALUES ('1', 'f6d3155217fe4983b783cece53871e48');
INSERT INTO `sys_role_menu` VALUES ('1', '40f6a1a26f11438385fc39eb94f09177');
INSERT INTO `sys_role_menu` VALUES ('1', '747596340176481ab009340dbcfe3d03');
INSERT INTO `sys_role_menu` VALUES ('1', '816e14ad64da48b29357d6fb273e303a');
INSERT INTO `sys_role_menu` VALUES ('1', '02aa82f8f45e41dea50eb6c2ba467a77');

/*==============================================================*/
/* 增加消息产生规则菜单项	及权限				*/
/*==============================================================*/
delete from sys_menu where id in ('beaeeb1ae7fa4bddbb39245ae2b4a625','e6d66b45daed4129a8824c50b12e1bf3','acc8f2b854554e93ab7ade8b6be6b2d3','49d1a863a7b64726a1d7b9efc8d3ba1d','bbdee647efdb49819c9973ccb5db4264');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('49d1a863a7b64726a1d7b9efc8d3ba1d', 'beaeeb1ae7fa4bddbb39245ae2b4a625', '0,1,b55313139685487a84fb1ab6f539182d,beaeeb1ae7fa4bddbb39245ae2b4a625,', '规则审批', '60', '/message/messageCreateRule/reviewList', '', 'icon-circle-blank', '1', '', '1', '2016-01-19 11:02:32', '1', '2016-01-19 11:04:16', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('acc8f2b854554e93ab7ade8b6be6b2d3', 'beaeeb1ae7fa4bddbb39245ae2b4a625', '0,1,b55313139685487a84fb1ab6f539182d,beaeeb1ae7fa4bddbb39245ae2b4a625,', '查看_消息产生规则', '90', '', '', '', '0', 'message:messageCreateRule:view', '1', '2016-01-19 11:01:02', '1', '2016-01-19 11:03:41', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bbdee647efdb49819c9973ccb5db4264', 'beaeeb1ae7fa4bddbb39245ae2b4a625', '0,1,b55313139685487a84fb1ab6f539182d,beaeeb1ae7fa4bddbb39245ae2b4a625,', '规则管理', '30', '/message/messageCreateRule/list', '', 'icon-asterisk', '1', '', '1', '2016-01-19 11:00:05', '1', '2016-01-19 11:00:05', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('beaeeb1ae7fa4bddbb39245ae2b4a625', 'b55313139685487a84fb1ab6f539182d', '0,1,b55313139685487a84fb1ab6f539182d,', '消息产生规则', '15', '', '', '', '1', '', '1', '2016-01-19 10:57:46', '1', '2016-01-19 10:58:00', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e6d66b45daed4129a8824c50b12e1bf3', 'beaeeb1ae7fa4bddbb39245ae2b4a625', '0,1,b55313139685487a84fb1ab6f539182d,beaeeb1ae7fa4bddbb39245ae2b4a625,', '编辑_消息产生规则', '120', '', '', '', '0', 'message:messageCreateRule:edit', '1', '2016-01-19 11:01:35', '1', '2016-01-19 11:04:02', '', '0');
delete from sys_role_menu where menu_id in ('beaeeb1ae7fa4bddbb39245ae2b4a625','e6d66b45daed4129a8824c50b12e1bf3','acc8f2b854554e93ab7ade8b6be6b2d3','49d1a863a7b64726a1d7b9efc8d3ba1d','bbdee647efdb49819c9973ccb5db4264');
INSERT INTO `sys_role_menu` VALUES ('1', 'beaeeb1ae7fa4bddbb39245ae2b4a625');
INSERT INTO `sys_role_menu` VALUES ('1', 'e6d66b45daed4129a8824c50b12e1bf3');
INSERT INTO `sys_role_menu` VALUES ('1', 'acc8f2b854554e93ab7ade8b6be6b2d3');
INSERT INTO `sys_role_menu` VALUES ('1', '49d1a863a7b64726a1d7b9efc8d3ba1d');
INSERT INTO `sys_role_menu` VALUES ('1', 'bbdee647efdb49819c9973ccb5db4264');

delete from message_behavior_type;
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('1', '1010', '注册');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('2', '1020', '登录');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('3', '1030', '签到');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('4', '1040', '开通第三方账号');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('5', '1050', '绑卡');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('6', '1060', '重置密码');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('7', '1070', '修改密码');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('8', '1080', '充值');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('9', '1090', '提现');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('10', '1100', '投标');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('11', '1110', '投标结束');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('12', '1120', '还款');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('13', '1130', '投资债权');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('14', '1140', '投资活期');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('15', '1150', '申请赎回本金');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('16', '1160', '赎回本金审批通过');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('17', '1170', '赎回本金申请失败');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('18', '1180', '赎回本金完成');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('19', '1190', '活期收息');
INSERT INTO `message_behavior_type` (`id`, `behavior_code`, `behavior_name`) VALUES ('20', '1200', '活期提取利息');

/*==============================================================*/
/* 增加发送统计菜单	及权限				*/
/*==============================================================*/
/*delete from sys_menu where id='a141a5fc002044a583f595ff79058390';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a141a5fc002044a583f595ff79058390', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '发送统计', '270', '/message/messageInstance/messageStatistics', '', 'icon-table', '1', 'message:messageInstance:messageStatistics', '1', '2016-01-18 15:00:00', '1', '2016-01-20 15:05:59', '', '0');
delete from sys_role_menu where menu_id='a141a5fc002044a583f595ff79058390';
INSERT INTO `sys_role_menu` VALUES ('1', 'a141a5fc002044a583f595ff79058390');*/







/*==============================================================*/
/* 增加消息设置菜单	及权限				*/
/*==============================================================*/
delete from sys_menu where id='3819182868f54dc0ab6691150f52f0ec';
delete from sys_menu where id='d9a224f498b14eeebf35d66616d66e6d';
delete from sys_menu where id='e2947b484a1847e1883c1dd5b382caca';
/*INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3819182868f54dc0ab6691150f52f0ec', '12cd17ac4f434301b5f4861dce620f56', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,', '消息设置', '300', '/message/receiveMessageSwitch/customerSwitchSettingList?pageType=customerMessageMenu&amp;pageStyle=mysimple', '', 'icon-cog', '1', '', '1', '2016-01-19 17:04:42', '1', '2016-01-22 12:25:49', '', '0');*/
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d9a224f498b14eeebf35d66616d66e6d', '3819182868f54dc0ab6691150f52f0ec', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,3819182868f54dc0ab6691150f52f0ec,', '消息开关', '30', '', '', '', '0', 'message:receiveMessageSwitch:edit,message:receiveMessageSwitch:view', '1', '2016-01-21 13:17:34', '1', '2016-01-21 13:17:34', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e2947b484a1847e1883c1dd5b382caca', '3819182868f54dc0ab6691150f52f0ec', '0,1,b55313139685487a84fb1ab6f539182d,12cd17ac4f434301b5f4861dce620f56,3819182868f54dc0ab6691150f52f0ec,', '消息接收时间段', '60', '', '', '', '0', 'message:receiveSmsMessageTime:view,message:receiveSmsMessageTime:edit', '1', '2016-01-21 13:34:13', '1', '2016-01-22 18:14:40', '', '0');
delete from sys_role_menu where menu_id='3819182868f54dc0ab6691150f52f0ec';
delete from sys_role_menu where menu_id='d9a224f498b14eeebf35d66616d66e6d';
delete from sys_role_menu where menu_id='e2947b484a1847e1883c1dd5b382caca';
INSERT INTO `sys_role_menu` VALUES ('1', '3819182868f54dc0ab6691150f52f0ec');
INSERT INTO `sys_role_menu` VALUES ('1', 'd9a224f498b14eeebf35d66616d66e6d');
INSERT INTO `sys_role_menu` VALUES ('1', 'e2947b484a1847e1883c1dd5b382caca');











/*==============================================================*/
/* 增加消息查看	及权限				*/
/*==============================================================*/
delete from sys_menu where id='63269bfe332e4fcbaf1476679ac34f92';
delete from sys_menu where id='af1d515f1f0240f2b07f0629608c28b1';
delete from sys_menu where id='d804000c99d44189b19b1180406687d6';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('63269bfe332e4fcbaf1476679ac34f92', 'd804000c99d44189b19b1180406687d6', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d804000c99d44189b19b1180406687d6,', '消息时间段设置', '60', '', '', '', '0', 'message:receiveSmsMessageTime:view', '1', '2016-01-18 10:22:46', '1', '2016-01-20 15:53:02', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('af1d515f1f0240f2b07f0629608c28b1', 'd804000c99d44189b19b1180406687d6', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d804000c99d44189b19b1180406687d6,', '消息开关', '30', '', '', '', '1', 'message:receiveMessageSwitch:view', '1', '2016-01-15 13:52:19', '1', '2016-01-20 15:52:40', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d804000c99d44189b19b1180406687d6', 'a8c72721812b476ca0e5ff78e924d1a2', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,', '消息', '210', '', '', '', '0', '', '1', '2016-01-15 10:48:57', '1', '2016-01-20 15:53:47', '', '0');
delete from sys_role_menu where menu_id='63269bfe332e4fcbaf1476679ac34f92';
delete from sys_role_menu where menu_id='af1d515f1f0240f2b07f0629608c28b1';
delete from sys_role_menu where menu_id='d804000c99d44189b19b1180406687d6';
INSERT INTO `sys_role_menu` VALUES ('1', '63269bfe332e4fcbaf1476679ac34f92');
INSERT INTO `sys_role_menu` VALUES ('1', 'af1d515f1f0240f2b07f0629608c28b1');
INSERT INTO `sys_role_menu` VALUES ('1', 'd804000c99d44189b19b1180406687d6');

drop table if exists marketing_wheel_lottery_times;

/*==============================================================*/
/* Table: marketing_wheel_lottery_times                         */
/*==============================================================*/
create table marketing_wheel_lottery_times
(
   id                   bigint not null auto_increment comment '编号',
   account_id           bigint comment '用户编号',
   total_times          int comment '总次数',
   used_times           int comment '已使用次数',
   primary key (id)
);

alter table marketing_wheel_lottery_times comment '大转盘抽奖次数';


/*==============================================================*/
/* 增加消息开关初始化数据                                                                                                            */
/*==============================================================*/
delete from receive_message_switch where id='1';
delete from receive_message_switch where id='2';
delete from receive_message_switch where id='3';
delete from receive_message_switch where id='4';
delete from receive_message_switch where id='5';
delete from receive_message_switch where id='6';
delete from receive_message_switch where id='7';
delete from receive_message_switch where id='8';
delete from receive_message_switch where id='9';
delete from receive_message_switch where id='10';
delete from receive_message_switch where id='11';
delete from receive_message_switch where id='12';
delete from receive_message_switch where account_id='0';
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('1', '0', '0', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('2', '0', '1', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('3', '0', '2', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('4', '0', '3', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('5', '0', '4', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('6', '0', '5', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('7', '1', '0', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('8', '1', '1', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('9', '1', '2', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('10', '1', '3', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('11', '1', '4', '0', '0');
INSERT INTO `receive_message_switch` (`id`, `message_type`, `message_channel`, `account_id`, `is_receive`) VALUES ('12', '1', '5', '0', '0');


/*==============================================================*/
/* 将消息管理--消息管理下的消息查询菜单移动到 消息管理---消息查询下                                                                                                         */
/*==============================================================*/
update  sys_menu set name='自定义消息' where id='12cd17ac4f434301b5f4861dce620f56';
delete from sys_menu where id='3f71eb37794c4036b2c38d8eb512a145';
delete from sys_menu where id='70237c5470714d29b365678b2961d7fb';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3f71eb37794c4036b2c38d8eb512a145', '70237c5470714d29b365678b2961d7fb', '0,1,b55313139685487a84fb1ab6f539182d,70237c5470714d29b365678b2961d7fb,', '消息查询', '60', '/message/messageInstance/searchPageList', '', 'icon-comment-alt', '1', 'message:messageInstance:view', '1', '2016-01-27 11:14:23', '1', '2016-01-27 14:17:49', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('70237c5470714d29b365678b2961d7fb', 'b55313139685487a84fb1ab6f539182d', '0,1,b55313139685487a84fb1ab6f539182d,', '消息查询', '60', '', '', 'icon-paper-clip', '1', '', '1', '2016-01-27 11:10:05', '1', '2016-01-27 11:14:57', '', '0');
delete from sys_role_menu where menu_id='3f71eb37794c4036b2c38d8eb512a145';
delete from sys_role_menu where menu_id='70237c5470714d29b365678b2961d7fb';
INSERT INTO `sys_role_menu` VALUES ('1', '3f71eb37794c4036b2c38d8eb512a145');
INSERT INTO `sys_role_menu` VALUES ('1', '70237c5470714d29b365678b2961d7fb');
/*==============================================================*/
/*更改消息管理--自定义消息--消息设置到消息管理--自定义消息--消息查询下                                                                                                     */
/*==============================================================*/
delete from sys_menu where id='fe6cca3528634484aedeca9ab6233c97';
delete from sys_menu where id='67e2b8047dea40eb8824ff1234f307d6';
delete from sys_menu where id='03fe908b08d3443fa03752179dd2b979';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('fe6cca3528634484aedeca9ab6233c97', '70237c5470714d29b365678b2961d7fb', '0,1,b55313139685487a84fb1ab6f539182d,70237c5470714d29b365678b2961d7fb,', '消息设置', '90', '/message/receiveMessageSwitch/customerSwitchSettingList?pageType=customerMessageMenu&amp;pageStyle=mysimple', '', 'icon-cogs', '1', '', '1', '2016-01-27 14:15:12', '1', '2016-01-28 10:15:12', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('67e2b8047dea40eb8824ff1234f307d6', 'fe6cca3528634484aedeca9ab6233c97', '0,1,b55313139685487a84fb1ab6f539182d,70237c5470714d29b365678b2961d7fb,fe6cca3528634484aedeca9ab6233c97,', '消息开关', '30', '', '', '', '0', 'message:receiveMessageSwitch:edit,message:receiveMessageSwitch:view', '1', '2016-01-27 14:16:38', '1', '2016-01-27 14:23:14', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('03fe908b08d3443fa03752179dd2b979', 'fe6cca3528634484aedeca9ab6233c97', '0,1,b55313139685487a84fb1ab6f539182d,70237c5470714d29b365678b2961d7fb,fe6cca3528634484aedeca9ab6233c97,', '消息接收时间段', '60', '', '', '', '0', 'message:receiveSmsMessageTime:view,message:receiveSmsMessageTime:edit', '1', '2016-01-27 14:16:56', '1', '2016-01-28 10:16:00', '', '0');
delete from sys_role_menu where menu_id='03fe908b08d3443fa03752179dd2b979';
delete from sys_role_menu where menu_id='67e2b8047dea40eb8824ff1234f307d6';
delete from sys_role_menu where menu_id='fe6cca3528634484aedeca9ab6233c97';
INSERT INTO `sys_role_menu` VALUES ('1', '03fe908b08d3443fa03752179dd2b979');
INSERT INTO `sys_role_menu` VALUES ('1', '67e2b8047dea40eb8824ff1234f307d6');
INSERT INTO `sys_role_menu` VALUES ('1', 'fe6cca3528634484aedeca9ab6233c97');

