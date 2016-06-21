drop table if exists app_message_push_record;

/*==============================================================*/
/* Table: app_message_push_record                               */
/*==============================================================*/
create table app_message_push_record
(
   id                   bigint not null auto_increment comment '编号',
   message_instance_id  bigint comment '消息实例编号',
   push_dt              datetime comment '推送时间',
   result_data          text comment '推送结果',
   primary key (id)
);

alter table app_message_push_record comment '客户端消息推送记录';

drop table if exists customer_leave_message;

/*==============================================================*/
/* Table: customer_leave_message                                */
/*==============================================================*/
create table customer_leave_message
(
   id                   bigint not null auto_increment comment '流水号',
   name                 varchar(20) comment '姓名',
   mobile               varchar(20) comment '手机号',
   email                varchar(50) comment '邮箱',
   content              varchar(500) comment '内容',
   address              varchar(200) comment '地址',
   type                 varchar(2) comment '留言类型',
   status               varchar(2) comment '状态',
   account_id           bigint comment '账户编号',
   op_dt                datetime comment '操作时间',
   ip                   varchar(50) comment 'ip地址',
   primary key (id)
);

alter table customer_leave_message comment '客户留言';



delete from sys_dict where id in ('7254a48a74da43d497d80a39b16b27a0','b4eff66f5fb343c3a70df5396cad136d');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7254a48a74da43d497d80a39b16b27a0', '1', '亨沃美国房地产投资', 'customer_leave_message_type', '客户留言类型', '10', '0', '1', '2016-02-22 13:50:19', '1', '2016-02-22 13:50:19', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b4eff66f5fb343c3a70df5396cad136d', '0', '提交', 'customer_leave_message_status', '客户留言状态', '10', '0', '1', '2016-02-22 13:54:43', '1', '2016-02-22 13:54:43', '', '0');

ALTER TABLE `marketing_wheel_prize_info`
ADD COLUMN `activity_id`  bigint NULL COMMENT '活动编号' AFTER `rotate`,
ADD COLUMN `is_default`  varchar(2) NULL COMMENT '是否默认' AFTER `activity_id`;

ALTER TABLE `marketing_wheel_lottery_times`
ADD COLUMN `activity_id`  bigint NULL COMMENT '活动编号' AFTER `used_times`;


drop table if exists customer_push_switch;

/*==============================================================*/
/* Table: customer_push_switch                                  */
/*==============================================================*/
create table customer_push_switch
(
   id                   bigint not null auto_increment comment '编号',
   push_channel         varchar(2) comment '渠道',
   account_id           bigint comment '账户编号',
   is_receive           varchar(2) comment '是否接收',
   primary key (id)
);

alter table customer_push_switch comment '用户接收push消息开关';

drop table if exists marketing_share_record;

/*==============================================================*/
/* Table: marketing_share_record                                */
/*==============================================================*/
create table marketing_share_record
(
   id                   bigint not null auto_increment comment '记录编号',
   activity_id          bigint comment '活动编号',
   channel_id           varchar(10) comment '渠道编号',
   account_id           bigint comment '分享用户',
   share_dt             datetime comment '分享时间',
   share_reason         varchar(100) comment '分享说明',
   acticity_code        varchar(50) comment '活动代码',
   primary key (id)
);

alter table marketing_share_record comment '营销活动分享记录';


/*==============================================================*/
/*	修改：我的现金券label描述("过期" 改为 "已过期")                                                                                                                       */
/*==============================================================*/
update sys_dict set label='已过期' where type='customer_investment_ticket_dict' and  id='31496da5a818499496d3fc7341ad0ed4';

delete from sys_dict where type = 'marketing_wheel_prize_type_dict';

/*==============================================================*/
/*	修改：新手任务("投资券" 改为  "现金券")                                                                                                                         */
/*==============================================================*/
delete from customer_new_task;
INSERT INTO `customer_new_task` VALUES (1, 'step1', '获得5元现金券', 10, '1', '2015-11-13 11:12:41', '2015-11-19 10:39:49');
INSERT INTO `customer_new_task` VALUES (2, 'step2', '获得10元现金券', 30, '1', '2015-11-13 11:13:18', NULL);
INSERT INTO `customer_new_task` VALUES (3, 'step3', '又获得10元现金券', 40, '1', '2015-11-13 11:13:54', NULL);
INSERT INTO `customer_new_task` VALUES (4, 'step4', '超级大礼包已发放', 60, '1', '2015-11-13 11:14:21', NULL);

/*==============================================================*/
/*	修改：现金券使用说明                                                                                                                      	    */
/*==============================================================*/
update investment_ticket_type set use_info='满1000抵5元' where id=1;
update investment_ticket_type set use_info='满2000抵10元' where id=2;
update investment_ticket_type set use_info='满4000抵20元' where id=3;
update investment_ticket_type set use_info='满10000抵50元' where id=4;
update investment_ticket_type set use_info='满20000抵100元' where id=5;

/*==============================================================*/
/*	添加亨沃房地产投资留言菜单及权限                                                                                                                         */
/*==============================================================*/
delete from sys_menu where id in ('1c90aeabd75148169b29ca56682b63a2','c6fd216a16c2472f81ee96bb6f850ec5');
delete from sys_role_menu where menu_id in ('1c90aeabd75148169b29ca56682b63a2','c6fd216a16c2472f81ee96bb6f850ec5');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1c90aeabd75148169b29ca56682b63a2', 'c6fd216a16c2472f81ee96bb6f850ec5', '0,1,526880b97e1c4c61ba1df684b014e1d5,c6fd216a16c2472f81ee96bb6f850ec5,', '美国房地产留言', '30', '/feedback/customerLeaveMessage/hanworldList', '', 'icon-globe', '1', 'feedback:customerLeaveMessage:view', '1', '2016-02-29 13:49:45', '1', '2016-02-29 14:44:51', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c6fd216a16c2472f81ee96bb6f850ec5', '526880b97e1c4c61ba1df684b014e1d5', '0,1,526880b97e1c4c61ba1df684b014e1d5,', '亨沃家族办公室', '60', '', '', '', '1', '', '1', '2016-02-29 13:41:43', '1', '2016-02-29 13:42:10', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '1c90aeabd75148169b29ca56682b63a2');
INSERT INTO `sys_role_menu` VALUES ('1', 'c6fd216a16c2472f81ee96bb6f850ec5');























