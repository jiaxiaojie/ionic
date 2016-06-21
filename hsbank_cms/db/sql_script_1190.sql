drop table if exists customer_withdraw_his;

/*==============================================================*/
/* Table: customer_withdraw_his                                 */
/*==============================================================*/
create table customer_withdraw_his
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   amount               decimal(13,2) comment '提现金额',
   fee_mode             varchar(20) comment '费率模式',
   withdraw_type        varchar(20) comment '提现模式',
   bank_code            varchar(20) comment '所属银行',
   bank_card_no         varchar(50) comment '提现银行卡号',
   remark               varchar(200) comment '提现备注',
   third_party_req      varchar(50) comment '提现流水号',
   op_dt                datetime comment '操作时间',
   op_term_type         varchar(2) comment '操作终端',
   primary key (id)
);

alter table customer_withdraw_his comment '会员提现记录表';

drop table if exists message_alert_setting;

/*==============================================================*/
/* Table: message_alert_setting            消息提醒设置                     */
/*==============================================================*/
create table message_alert_setting
(
   id                   bigint not null auto_increment,
   type                 varchar(2),
   title                varchar(50),
   content              varchar(200),
   mobile               varchar(2000),
   create_dt            datetime,
   primary key (id)
);

alter table message_alert_setting comment '消息提醒设置';

/*==============================================================*/
/* 添加字典 	message_alert_purpose                               */
/*==============================================================*/
delete from sys_dict where id = '32142226acb348d8bf6495edfe359955';
delete from sys_dict where id = '819fb750941645eb8782975458cdccd0';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('32142226acb348d8bf6495edfe359955', '1', '账户余额提醒', 'message_alert_purpose', '发送消息用途', '10', '0', '1', '2016-05-09 15:56:41', '1', '2016-05-09 15:58:21', '账户余额提醒', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('819fb750941645eb8782975458cdccd0', '2', '项目满标和结束提醒', 'message_alert_purpose', '发送消息用途', '20', '0', '1', '2016-05-09 15:57:38', '1', '2016-05-09 15:59:00', '项目满标和结束提醒', '0');

/*==============================================================*/
/* 添加消息提醒设置菜单及权限   ;                               */
/*==============================================================*/
delete from sys_menu where id='19d063f6afcc45d1add7456ee2ca9521';
delete from sys_menu where id='6e73294a10164588a3eb41607ebeca14';
delete from sys_menu where id='f4ea83ed05b74f1cb24d1c6de5ab9ec7';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6e73294a10164588a3eb41607ebeca14', '3', '0,1,2,3,', '消息提醒设置', '330', '/message/messageAlertSetting', '', 'icon-bell-alt', '1', '', '1', '2016-05-09 16:12:22', '1', '2016-05-09 16:12:22', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f4ea83ed05b74f1cb24d1c6de5ab9ec7', '6e73294a10164588a3eb41607ebeca14', '0,1,2,3,6e73294a10164588a3eb41607ebeca14,', '消息提醒设置查看', '30', '', '', '', '0', 'message:messageAlertSetting:view', '1', '2016-05-09 16:13:38', '1', '2016-05-09 16:14:32', '消息提醒设置查看', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('19d063f6afcc45d1add7456ee2ca9521', '6e73294a10164588a3eb41607ebeca14', '0,1,2,3,6e73294a10164588a3eb41607ebeca14,', '消息提醒设置添加', '60', '', '', '', '0', 'message:messageAlertSetting:edit', '1', '2016-05-09 16:14:15', '1', '2016-05-09 16:14:15', '', '0');
delete from sys_role_menu where menu_id='19d063f6afcc45d1add7456ee2ca9521';
delete from sys_role_menu where menu_id='6e73294a10164588a3eb41607ebeca14';
delete from sys_role_menu where menu_id='f4ea83ed05b74f1cb24d1c6de5ab9ec7';
INSERT INTO `sys_role_menu` VALUES ('1', '19d063f6afcc45d1add7456ee2ca9521');
INSERT INTO `sys_role_menu` VALUES ('1', '6e73294a10164588a3eb41607ebeca14');
INSERT INTO `sys_role_menu` VALUES ('1', 'f4ea83ed05b74f1cb24d1c6de5ab9ec7');

drop table if exists ad_position_info;

/*==============================================================*/
/* Table: ad_position_info                                      */
/*==============================================================*/
create table ad_position_info
(
   id                   bigint not null auto_increment comment '编号',
   code                 varchar(20) comment '代码',
   name                 varchar(30) comment '名称',
   image                varchar(500) comment '图片',
   can_click            varchar(2) comment '是否可点击',
   type                 varchar(2) comment '类型',
   target               varchar(300) comment '目标参数',
   description          varchar(500) comment '描述',
   start_dt             datetime comment '开始时间',
   end_dt               datetime comment '结束时间',
   version              varchar(20) comment '版本',
   status               varchar(2) comment '状态',
   create_user_id       bigint comment '创建人',
   create_dt            datetime comment '创建时间',
   review_user_id       bigint comment '审批人',
   review_dt            datetime comment '审批时间',
   primary key (id)
);

alter table ad_position_info comment '广告位信息';


drop table if exists ad_position_show_term;

/*==============================================================*/
/* Table: ad_position_show_term                                 */
/*==============================================================*/
create table ad_position_show_term
(
   id                   bigint not null auto_increment comment '编号',
   ad_position_id       bigint comment '广告位编号',
   term_code            varchar(2) comment '终端',
   primary key (id)
);

alter table ad_position_show_term comment '广告位显示终端';

/*==============================================================*/
/* 项目还款计划增加剩余本息字段                                 */
/*==============================================================*/
ALTER TABLE `project_repayment_plan`
ADD COLUMN `remaining_principal_interest`  decimal(12,2) NULL DEFAULT NULL COMMENT '剩余本息' AFTER `remaining_principal`;

/*==============================================================*/
/* 项目基本信息增加项目提示字段                                 */
/*==============================================================*/
ALTER TABLE `project_base`
ADD COLUMN `project_tips`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目提示' AFTER `project_name`;

/*==============================================================*/
/* 增加广告位后台 菜单 及权限                            */
/*==============================================================*/
delete from sys_menu where id='2496bc6090134912b7a48120734b8362';
delete from sys_menu where id='69fa14d0fd6d42389b2e9496dfdcb31f';
delete from sys_menu where id='adfbae8d6d494706a0d1be90e61ac2ce';
delete from sys_menu where id='2e04d7ad1f3d4ec8a9c3b57302c6bf48';
delete from sys_menu where id='0b8f5b4748c1401aa612ae4a0f671238';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2496bc6090134912b7a48120734b8362', 'adfbae8d6d494706a0d1be90e61ac2ce', '0,1,31,40,adfbae8d6d494706a0d1be90e61ac2ce,', '广告位创建', '30', '', '', '', '0', 'carousel:adPositionInfo:edit', '1', '2016-05-17 14:55:19', '1', '2016-05-18 15:17:31', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('69fa14d0fd6d42389b2e9496dfdcb31f', 'adfbae8d6d494706a0d1be90e61ac2ce', '0,1,31,40,adfbae8d6d494706a0d1be90e61ac2ce,', '广告位查看', '60', '', '', '', '0', 'carousel:adPositionInfo:view', '1', '2016-05-17 14:55:52', '1', '2016-05-18 15:18:21', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('adfbae8d6d494706a0d1be90e61ac2ce', '40', '0,1,31,40,', '广告位信息', '140', '/carousel/adPositionInfo/list', '', 'icon-laptop', '1', '', '1', '2016-05-17 14:54:59', '1', '2016-05-17 15:01:22', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2e04d7ad1f3d4ec8a9c3b57302c6bf48', 'adfbae8d6d494706a0d1be90e61ac2ce', '0,1,31,40,adfbae8d6d494706a0d1be90e61ac2ce,', '管理', '90', '/carousel/adPositionInfo/list', '', 'icon-asterisk', '1', 'carousel:adPositionInfo:view', '1', '2016-05-17 14:58:19', '1', '2016-05-17 15:06:46', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0b8f5b4748c1401aa612ae4a0f671238', 'adfbae8d6d494706a0d1be90e61ac2ce', '0,1,31,40,adfbae8d6d494706a0d1be90e61ac2ce,', '审核', '120', '/carousel/adPositionInfo/reviewList', '', 'icon-ok', '1', 'carousel:adPositionInfo:view', '1', '2016-05-17 14:59:56', '1', '2016-05-17 17:06:12', '', '0');
delete from sys_role_menu where menu_id='2496bc6090134912b7a48120734b8362';
delete from sys_role_menu where menu_id='69fa14d0fd6d42389b2e9496dfdcb31f';
delete from sys_role_menu where menu_id='adfbae8d6d494706a0d1be90e61ac2ce';
delete from sys_role_menu where menu_id='2e04d7ad1f3d4ec8a9c3b57302c6bf48';
delete from sys_role_menu where menu_id='0b8f5b4748c1401aa612ae4a0f671238';
INSERT INTO `sys_role_menu` VALUES ('1', '2496bc6090134912b7a48120734b8362');
INSERT INTO `sys_role_menu` VALUES ('1', '69fa14d0fd6d42389b2e9496dfdcb31f');
INSERT INTO `sys_role_menu` VALUES ('1', 'adfbae8d6d494706a0d1be90e61ac2ce');
INSERT INTO `sys_role_menu` VALUES ('1', '2e04d7ad1f3d4ec8a9c3b57302c6bf48');
INSERT INTO `sys_role_menu` VALUES ('1', '0b8f5b4748c1401aa612ae4a0f671238');

/*==============================================================*/
/* 增加字典表 ad_code(广告位代码)                      */
/*==============================================================*/
delete from sys_dict where id='50a95a82821a4873841b32bf9675ba9d';
delete from sys_dict where id='86cbd11ed9e64607b2b13b4025ff0e90';
delete from sys_dict where id='87d592cecdea4adf830cc3e301422cb8';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('50a95a82821a4873841b32bf9675ba9d', '1003', '投资成功', 'ad_code', '广告位代码', '30', '0', '1', '2016-05-16 18:12:45', '1', '2016-05-16 18:12:45', '投资成功', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('86cbd11ed9e64607b2b13b4025ff0e90', '1002', '注册', 'ad_code', '广告位代码', '20', '0', '1', '2016-05-16 18:12:29', '1', '2016-05-16 18:12:29', '注册', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('87d592cecdea4adf830cc3e301422cb8', '1001', '登陆', 'ad_code', '广告位代码', '10', '0', '1', '2016-05-16 18:11:59', '1', '2016-05-16 18:11:59', '登陆', '0');
