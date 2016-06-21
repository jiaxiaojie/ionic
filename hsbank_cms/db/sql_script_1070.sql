drop table if exists marketing_activity_award_record;
/*==============================================================*/
/* Table: marketing_activity_award_record                       */
/*==============================================================*/
create table marketing_activity_award_record
(
   id                   bigint not null auto_increment comment '记录编号',
   activity_id          bigint comment '活动编号',
   behavior_code        varchar(6) comment '行为编号',
   channel_id           varchar(10) comment '渠道编号',
   account_id           bigint comment '奖励用户',
   award_type           varchar(10) comment '奖励产品类型',
   award_dt             datetime comment '获得奖励时间',
   award_value          varchar(50) comment '奖励值',
   award_reason         varchar(100) comment '奖励说明',
   user_id              int comment '操作人员',
   primary key (id)
);
alter table marketing_activity_award_record comment '营销活动奖励记录';
ALTER TABLE `marketing_activity_award_record`
ADD COLUMN `cause_type`  varchar(2) NULL COMMENT '奖励来源类型' AFTER `award_reason`,
ADD COLUMN `cause_id`  bigint NULL COMMENT '来源编号' AFTER `cause_type`;

/*==============================================================*/
/*	project_base表添加列：sort_in_list(int,'在列表中的排序'),sort_in_index(int,'在首页中的排序')    	*/
/*	此sql不可重复执行                                                                                                                   */
/*==============================================================*/
ALTER TABLE `project_base`
ADD COLUMN `sort_in_list`  int NULL COMMENT '在列表中的排序' AFTER `is_loan`,
ADD COLUMN `sort_in_index`  int NULL COMMENT '在首页中的排序' AFTER `sort_in_list`;

/*==============================================================*/
/*	customer_account表添加列：ad_channel_id(varchar(50),'推广渠道来源')*/
/*	此sql不可重复执行                                                                                                                   */
/*==============================================================*/
ALTER TABLE `customer_account`
ADD COLUMN `ad_channel_id`  varchar(50) NULL COMMENT '推广渠道来源' AFTER `recommender_type`;

/*修改project_execute_snapshot表end_finance_money字段类型为decimal(13,2)*/
alter table project_execute_snapshot modify `end_finance_money` decimal(13,2);

/*==============================================================*/
/*	增加:积分商城用户地址状态数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type='customer_address_status_dict';
INSERT INTO `sys_dict` VALUES ('72ba1b0a59044dc4b1cd39c19e1e72c2', '-1', '已删除', 'customer_address_status_dict', '积分商城用户地址状态', 30, '0', '1', '2015-11-9 19:24:13', '1', '2015-11-9 19:24:13', '', '0');
INSERT INTO `sys_dict` VALUES ('f0774e25d8134b8ea2b69ce48a61eea3', '0', '正常', 'customer_address_status_dict', '积分商城用户地址状态', 10, '0', '1', '2015-11-9 19:23:30', '1', '2015-11-9 19:23:30', '', '0');

drop table if exists sys_mobile_version_para;

/*==============================================================*/
/* Table: sys_mobile_version_para                               */
/*==============================================================*/
create table sys_mobile_version_para
(
   id                   int not null auto_increment,
   version              varchar(20)  COMMENT '版本',
   version_info         varchar(500) COMMENT '版本说明',
   url                  varchar(200) COMMENT 'apk文件URL',
   channel              varchar(20)  COMMENT '渠道',
   type                 varchar(20)  COMMENT '移动端类型',
   mark                 varchar(2)   COMMENT '启用标示(0:停用；1：启用)',
   primary key (id)
);

alter table sys_mobile_version_para comment '移动端版本参数';

/*==============================================================*/
/*	增加:移动端版本mark数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type = 'mobile_version_mark';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2d9a86c26ef9435fa349ee0883ff06f5', '1', '启用', 'mobile_version_mark', '移动版本启用标示', '20', '0', '1', '2015-11-11 14:00:35', '1', '2015-11-11 14:00:35', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4277b6e4f09a487b9b9cc4022c2f7225', '0', '停用', 'mobile_version_mark', '移动版本启用标示', '10', '0', '1', '2015-11-11 14:00:13', '1', '2015-11-11 14:00:13', '', '0');

/*==============================================================*/
/*	增加:移动端版本菜单项                                                                                                               		*/
/*==============================================================*/
delete from sys_menu where id = 'cb713cd476bf43979de23fc8e6f95686';
delete from sys_menu where id = '2c79d16d602a42909762e7eb29883cd1';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cb713cd476bf43979de23fc8e6f95686', '3', '0,1,2,3,', '移动端版本', '180', '/sys/sysMobileVersionPara', '', '', '1', 'sys:sysMobileVersionPara:view', '1', '2015-11-11 13:03:49', '1', '2015-11-11 13:39:45', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2c79d16d602a42909762e7eb29883cd1', 'cb713cd476bf43979de23fc8e6f95686', '0,1,2,3,cb713cd476bf43979de23fc8e6f95686,', '编辑', '30', '', '', '', '0', 'sys:sysMobileVersionPara:edit', '1', '2015-11-11 13:40:05', '1', '2015-11-11 13:40:05', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', 'cb713cd476bf43979de23fc8e6f95686');
INSERT INTO `sys_role_menu` VALUES ('1', '2c79d16d602a42909762e7eb29883cd1');

/*==============================================================*/
/*	增加:项目前端排序管理菜单项及分配此菜单权限给管理员                                                                                                               		*/
/*==============================================================*/
delete from sys_menu where id = 'cb8c825a4c6e4f97ad8151da70cfdd46';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cb8c825a4c6e4f97ad8151da70cfdd46', 'b3b6ba3bba644220b3eb9528bebe2f58', '0,1,44aee8dec38849159118f41a4767ece2,b3b6ba3bba644220b3eb9528bebe2f58,', '项目前端排序管理', '510', '/project/projectBaseInfo/frontSortManager', '', '', '1', '', '1', '2015-11-12 10:56:37', '1', '2015-11-12 10:56:37', '', '0');
delete from sys_role_menu where menu_id = 'cb8c825a4c6e4f97ad8151da70cfdd46';
INSERT INTO `sys_role_menu` VALUES ('1', 'cb8c825a4c6e4f97ad8151da70cfdd46');

delete from sys_dict where id in ('0dfb683026c64b5898c4606dc09cded9','3fa8b7f53b4948f1a7dbad1b39597453','41ea4f82f2514f1893558396015e756b','423719d4330242bda25ee8b7837e3e8a','4e692ac2014b473e8ababbdb9e15ab60',
	'5853304e883b4d20a0673c40a5eb4a9c','63a51a9c31154d87951c0ef9a3ed0caf','86d871d3a27e4a03b898fcdb9e43a7e8','877e0dd205ef4d89bedfc92bd8fc4b18','8a609e63981a4ff6b524e038a3778c4d','b069a3bb932f40eba79c84aeea306b3a','c8e6a35c5592480793c6cbf160b0276d','f1e808ed78a5478fb8ce029c85848be9');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0dfb683026c64b5898c4606dc09cded9', '1090', '投资--投标', 'marketing_behavior_dict', '营销活动行为', '90', '0', '1', '2015-11-12 15:21:15', '1', '2015-11-12 15:21:15', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3fa8b7f53b4948f1a7dbad1b39597453', '1060', '开通第三方账号', 'marketing_behavior_dict', '营销活动行为', '60', '0', '1', '2015-11-12 15:20:14', '1', '2015-11-12 15:20:14', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('41ea4f82f2514f1893558396015e756b', '1070', '绑卡', 'marketing_behavior_dict', '营销活动行为', '70', '0', '1', '2015-11-12 15:20:27', '1', '2015-11-12 15:20:27', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('423719d4330242bda25ee8b7837e3e8a', '1100', '投资--债权转让', 'marketing_behavior_dict', '营销活动行为', '100', '0', '1', '2015-11-12 15:21:32', '1', '2015-11-12 15:21:32', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4e692ac2014b473e8ababbdb9e15ab60', '1130', '投标--完成', 'marketing_behavior_dict', '营销活动行为', '130', '0', '1', '2015-11-12 15:22:18', '1', '2015-11-12 15:22:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5853304e883b4d20a0673c40a5eb4a9c', '1110', '融资申请', 'marketing_behavior_dict', '营销活动行为', '110', '0', '1', '2015-11-12 15:21:50', '1', '2015-11-12 15:21:50', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('63a51a9c31154d87951c0ef9a3ed0caf', '1080', '提现', 'marketing_behavior_dict', '营销活动行为', '80', '0', '1', '2015-11-12 15:20:40', '1', '2015-11-12 15:20:40', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('86d871d3a27e4a03b898fcdb9e43a7e8', '1030', '签到', 'marketing_behavior_dict', '营销活动行为', '30', '0', '1', '2015-11-12 15:19:26', '1', '2015-11-12 15:19:26', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('877e0dd205ef4d89bedfc92bd8fc4b18', '1050', '充值', 'marketing_behavior_dict', '营销活动行为', '50', '0', '1', '2015-11-12 15:19:53', '1', '2015-11-12 15:19:53', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8a609e63981a4ff6b524e038a3778c4d', '1120', '抽奖', 'marketing_behavior_dict', '营销活动行为', '120', '0', '1', '2015-11-12 15:22:04', '1', '2015-11-12 15:22:04', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b069a3bb932f40eba79c84aeea306b3a', '1040', '邀请', 'marketing_behavior_dict', '营销活动行为', '40', '0', '1', '2015-11-12 15:19:41', '1', '2015-11-12 15:19:41', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c8e6a35c5592480793c6cbf160b0276d', '1020', '注册', 'marketing_behavior_dict', '营销活动行为', '20', '0', '1', '2015-11-12 15:18:52', '1', '2015-11-12 15:18:52', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f1e808ed78a5478fb8ce029c85848be9', '1010', '登录', 'marketing_behavior_dict', '营销活动行为', '10', '0', '1', '2015-11-12 15:18:33', '1', '2015-11-12 15:18:33', '', '0');

delete from sys_dict where id in ('177a6b72a28c4d75aa5482f48a5fbf71','423341ced07a4bbf9cca812cecd80521','8701370cde8f4bf4a95d7a4fbff65d38','c55f5ee0f83d4d5db1f963452b675eb6');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('177a6b72a28c4d75aa5482f48a5fbf71', '3', '花生豆', 'marketing_award_type_dict', '营销活动奖励产品类型', '30', '0', '1', '2015-11-12 15:35:53', '1', '2015-11-12 15:35:53', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('423341ced07a4bbf9cca812cecd80521', '4', '免费提现次数', 'marketing_award_type_dict', '营销活动奖励产品类型', '40', '0', '1', '2015-11-12 15:36:10', '1', '2015-11-12 15:36:10', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8701370cde8f4bf4a95d7a4fbff65d38', '2', '现金', 'marketing_award_type_dict', '营销活动奖励产品类型', '20', '0', '1', '2015-11-12 15:35:41', '1', '2015-11-12 15:35:41', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c55f5ee0f83d4d5db1f963452b675eb6', '1', '投资券', 'marketing_award_type_dict', '营销活动奖励产品类型', '10', '0', '1', '2015-11-12 15:35:11', '1', '2015-11-12 15:35:11', '', '0');

delete from sys_menu where id in ('367873d12dbf453c8caf27e26f9e2183','7edfa3c4c4d042ca987c95658029db6d');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('367873d12dbf453c8caf27e26f9e2183', '6fe1901412d042bcb2010240ea57f1e0', '0,1,6fe1901412d042bcb2010240ea57f1e0,', '活动奖励管理', '60', '', '', '', '1', '', '1', '2015-11-12 15:49:16', '1', '2015-11-12 15:49:16', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7edfa3c4c4d042ca987c95658029db6d', '367873d12dbf453c8caf27e26f9e2183', '0,1,6fe1901412d042bcb2010240ea57f1e0,367873d12dbf453c8caf27e26f9e2183,', '查看奖励', '30', '/marketing/marketingActivityAwardRecord/list', '', 'icon-briefcase', '1', 'marketing:marketingActivityAwardRecord:view', '1', '2015-11-12 15:51:29', '1', '2015-11-12 15:52:09', '', '0');
delete from sys_role_menu where menu_id in ('367873d12dbf453c8caf27e26f9e2183','7edfa3c4c4d042ca987c95658029db6d');
INSERT INTO `sys_role_menu` VALUES ('1', '367873d12dbf453c8caf27e26f9e2183');
INSERT INTO `sys_role_menu` VALUES ('1', '7edfa3c4c4d042ca987c95658029db6d');

delete from sys_menu where id = 'f769d024116b4bc6a1f62951b0247924';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f769d024116b4bc6a1f62951b0247924', '367873d12dbf453c8caf27e26f9e2183', '0,1,6fe1901412d042bcb2010240ea57f1e0,367873d12dbf453c8caf27e26f9e2183,', '补送奖励', '60', '/marketing/marketingActivityAwardRecord/patchAward', '', 'icon-briefcase', '1', 'marketing:marketingActivityAwardRecord:edit', '1', '2015-11-13 10:29:18', '1', '2015-11-13 10:29:18', '', '0');
delete from sys_role_menu where menu_id = 'f769d024116b4bc6a1f62951b0247924';
INSERT INTO `sys_role_menu` VALUES ('1', 'f769d024116b4bc6a1f62951b0247924');

delete from sys_dict where type = 'investment_ticket_type_status_dict';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9c866723642644aa8cd8541269db28c5', '-1', '不可用', 'investment_ticket_type_status_dict', '投资券类型的状态', '20', '0', '1', '2015-11-13 10:53:03', '1', '2015-11-13 10:53:03', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a6f49d5895ec4275b93543bb8eee2f65', '0', '正常', 'investment_ticket_type_status_dict', '投资券类型的状态', '10', '0', '1', '2015-11-13 10:52:39', '1', '2015-11-13 10:52:39', '', '0');

/*==============================================================*/
/* Table: customer_new_task                                     */
/*==============================================================*/
drop table if exists customer_new_task;
create table customer_new_task
(
   id                   int not null auto_increment COMMENT 'id编号',
   step                 varchar(50) COMMENT '步骤',
   des                  varchar(500) COMMENT '描述',
   sort                 decimal(10,0) COMMENT '排序',
   status               varchar(2) COMMENT '状态（1：正常；0：停用）',
   create_dt            datetime COMMENT '创建时间',
   modify_dt            datetime COMMENT '修改时间',
   primary key (id)
);
alter table customer_new_task comment '新手任务';

drop table if exists project_show_term;
/*==============================================================*/
/* Table: project_show_term                                     */
/*==============================================================*/
create table project_show_term
(
   id                   bigint not null auto_increment comment '编号',
   project_id      bigint comment '项目编号',
   term_code            varchar(2) comment '终端',
   primary key (id)
);
alter table project_show_term comment '项目显示终端';

/*=======================删除我的合同菜单===========================*/
delete from sys_menu where id = '839b81c7ab90494fa12ed6629bc39f11';
delete from sys_role_menu where menu_id = '839b81c7ab90494fa12ed6629bc39f11';

/*==============================================================*/
/*	增加:会员信息查询菜单项 ,银行信息管理菜单项                                                                                               		*/
/*==============================================================*/
delete from sys_menu where id = 'a8c72721812b476ca0e5ff78e924d1a2';
delete from sys_menu where id = 'be348c5723a442799b2f4087f99b5617';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a8c72721812b476ca0e5ff78e924d1a2', '302f3b2d42764abcbefd99046b39ac8b', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,', '会员信息查询', '1', '/customer/customerAccountInfo/customerAccountInfoList', '', 'icon-list', '1', '', '1', '2015-11-12 10:36:21', '1', '2015-11-16 15:35:54', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('be348c5723a442799b2f4087f99b5617', '3', '0,1,2,3,', '银行信息管理', '210', '/sys/bankInfo', '', 'icon-book', '1', '', '1', '2015-11-17 10:14:57', '1', '2015-11-17 10:14:57', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', 'a8c72721812b476ca0e5ff78e924d1a2');
INSERT INTO `sys_role_menu` VALUES ('1', 'be348c5723a442799b2f4087f99b5617');

delete from sys_menu where id = '25a29137025546d28a42f66edcd079f9';
delete from sys_menu where id = '61f2d01772b14385bb6d66f4b8661a7e';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('25a29137025546d28a42f66edcd079f9', 'be348c5723a442799b2f4087f99b5617', '0,1,2,3,be348c5723a442799b2f4087f99b5617,', '修改', '60', '', '', '', '0', 'sys:bankInfo:edit', '1', '2015-11-17 10:16:16', '1', '2015-11-17 10:16:16', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('61f2d01772b14385bb6d66f4b8661a7e', 'be348c5723a442799b2f4087f99b5617', '0,1,2,3,be348c5723a442799b2f4087f99b5617,', '查看', '30', '', '', '', '0', 'sys:bankInfo:view', '1', '2015-11-17 10:15:52', '1', '2015-11-17 10:15:52', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '25a29137025546d28a42f66edcd079f9');
INSERT INTO `sys_role_menu` VALUES ('1', '61f2d01772b14385bb6d66f4b8661a7e');

/*==============================================================*/
/*	增加:bank_info表及其初始化数据                                                                                            		*/
/*==============================================================*/
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for bank_info
-- ----------------------------
DROP TABLE IF EXISTS `bank_info`;
CREATE TABLE `bank_info` (
  `id` varchar(60) NOT NULL,
  `bank_code` varchar(15) NOT NULL,
  `bank_name` varchar(30) NOT NULL,
  `shortcut_individual` int(100) NOT NULL,
  `shortcut_single_day` int(100) NOT NULL,
  `shortcut_single_month` int(100) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_info
-- ----------------------------
INSERT INTO `bank_info` VALUES ('05cdcd13a0cc4c3eb24206859fca7699', 'ECITIC', '中信银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/ECITIC.gif');
INSERT INTO `bank_info` VALUES ('0e0c92868ba043c49ce8cd4f2dfb0311', 'CCB', '建设银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/CCB.gif');
INSERT INTO `bank_info` VALUES ('1f6402e75a914bf9a20c6ac1cf570e8f', 'CIB', '兴业银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/CIB.gif');
INSERT INTO `bank_info` VALUES ('3c10a4604e614fdd8d96c937ae7fe6e9', 'GDB', '广发银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/GDB.gif');
INSERT INTO `bank_info` VALUES ('55dfdc9a256a478e9408e5f5a874c8e7', 'ABC', '农业银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/ABC.gif');
INSERT INTO `bank_info` VALUES ('68bd0690860a4388a983495284359deb', 'BOC', '中国银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/BOC.gif');
INSERT INTO `bank_info` VALUES ('802d74cc7ec04516be931e2770d59dc9', 'ICBC', '工商银行', '10000', '20000', '20000', '/fuding_p2p/userfiles/1/images/bank/2015/11/ICBC.gif');
INSERT INTO `bank_info` VALUES ('90f275de0359417e9c2643845f269a8b', 'CMBC', '民生银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/CMBC.gif');
INSERT INTO `bank_info` VALUES ('928581907a5a412ea85b7c6c7242b14a', 'CMBCHINA', '招商银行', '50000', '50000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/CMBCHINA.gif');
INSERT INTO `bank_info` VALUES ('af7c95642df34fee8fe85464bcb6e8d8', 'CEB', '光大银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/CEB.gif');
INSERT INTO `bank_info` VALUES ('b79f9e6147d042f4b5414b22a1f082a0', 'BOCO', '交通银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/BOCO.gif');
INSERT INTO `bank_info` VALUES ('c0c34e0c8ceb4a8aa441a7ca2b40f0ce', 'SPDB', '浦发银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/SPDB.gif');
INSERT INTO `bank_info` VALUES ('e771ad98951f43279f6ae80b90988468', 'HX', '华夏银行', '50000', '100000', '100000', '/fuding_p2p/userfiles/1/images/bank/2015/11/HX.gif');


drop table if exists ad_channel_info;
/*==============================================================*/
/* Table: ad_channel_info                                       */
/*==============================================================*/
create table ad_channel_info
(
   id                   bigint not null auto_increment comment 'id',
   channel           varchar(50) comment '渠道',
   channel_name         varchar(100) comment '渠道名称',
   start_dt             datetime comment '开始时间',
   end_dt               datetime comment '结束时间',
   status               varchar(2) comment '状态',
   primary key (id)
);
alter table ad_channel_info comment '推广渠道信息';

/*==============================================================*/
/* Table: customer_balance(增加列：待收本金 will_principal)          */
/*==============================================================*/
ALTER TABLE `customer_balance`
ADD COLUMN `will_principal`  float(13,2) NULL DEFAULT 0 COMMENT '待收本金' AFTER `will_profit`;


/*==============================================================*/
/*	增加:渠道管理菜单项                                                                                      

  		*/
/*==============================================================*/
delete from sys_menu where id = '73ece50b959f41c59a90a1d3951c24e8';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('73ece50b959f41c59a90a1d3951c24e8', 'c4391fe4778142a7a5938fcf30c01fbd', '0,1,c4391fe4778142a7a5938fcf30c01fbd,', '渠道管理', '130', '', '', '', '1', '', '1', '2015-11-19 10:14:53', '1', '2015-11-19 10:14:53', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '73ece50b959f41c59a90a1d3951c24e8');


/*==============================================================*/
/*	增加:推广渠道信息菜单项                                                                                        		*/
/*==============================================================*/
delete from sys_menu where id = 'c51a06f338df424ea19b0785ce8dda0e';
delete from sys_menu where id = '2487b5d8a9fd45428f74a895979b7289';
delete from sys_menu where id = 'e006bafee52847a186bab3baaf7be270';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2487b5d8a9fd45428f74a895979b7289', 'c51a06f338df424ea19b0785ce8dda0e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,73ece50b959f41c59a90a1d3951c24e8,c51a06f338df424ea19b0785ce8dda0e,', '编辑', '60', '', '', '', '0', 'customer:adChannelInfo:edit', '1', '2015-11-17 14:39:48', '1', '2015-11-19 14:01:12', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c51a06f338df424ea19b0785ce8dda0e', '73ece50b959f41c59a90a1d3951c24e8', '0,1,c4391fe4778142a7a5938fcf30c01fbd,73ece50b959f41c59a90a1d3951c24e8,', '推广渠道信息', '270', '/customer/adChannelInfo', '', 'icon-bell', '1', '', '1', '2015-11-17 14:38:38', '1', '2015-11-19 14:01:45', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e006bafee52847a186bab3baaf7be270', 'c51a06f338df424ea19b0785ce8dda0e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,73ece50b959f41c59a90a1d3951c24e8,c51a06f338df424ea19b0785ce8dda0e,', '查看', '30', '', '', '', '0', 'customer:adChannelInfo:view', '1', '2015-11-17 14:39:11', '1', '2015-11-19 14:00:46', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', 'c51a06f338df424ea19b0785ce8dda0e');
INSERT INTO `sys_role_menu` VALUES ('1', '2487b5d8a9fd45428f74a895979b7289');
INSERT INTO `sys_role_menu` VALUES ('1', 'e006bafee52847a186bab3baaf7be270');

delete from sys_dict where id in ('cedbaade1ab74f6eae411bda17e72cbb','f2c4c4f2ee9c4df3a193cde35c12d6f1','9a82daeaf7294e5d97a98e6ac20f5ba5');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9a82daeaf7294e5d97a98e6ac20f5ba5', '2', '好友其它行为', 'market_award_cause_type_dict', '活动奖励来源类型', '30', '0', '1', '2015-11-18 16:19:09', '1', '2015-11-18 16:19:09', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cedbaade1ab74f6eae411bda17e72cbb', '0', '自身', 'market_award_cause_type_dict', '活动奖励来源类型', '10', '0', '1', '2015-11-17 19:54:15', '1', '2015-11-18 16:18:24', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f2c4c4f2ee9c4df3a193cde35c12d6f1', '1', '好友投资', 'market_award_cause_type_dict', '活动奖励来源类型', '20', '0', '1', '2015-11-17 19:54:25', '1', '2015-11-18 16:18:59', '', '0');


ALTER TABLE `customer_integral_his`
ADD COLUMN `change_type`  varchar(2) NULL COMMENT '变更类型' AFTER `change_val`;

delete from sys_dict where type in ('customer_integral_change_type_dict','customer_integral_change_reason_dict');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('61243d95b30b4537b77a9d5e6b0882d9', '3', '取消消费返还', 'customer_integral_change_type_dict', '会员积分变更原因', '40', '0', '1', '2015-11-18 11:27:15', '1', '2015-11-18 11:27:15', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7fe4dbabaaa54833b75460353922f6e4', '1', '消费', 'customer_integral_change_type_dict', '会员积分变更原因', '20', '0', '1', '2015-07-23 21:14:20', '1', '2015-08-17 11:11:45', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e086d777107a47ada5dfdb323323d9a8', '0', '签到', 'customer_integral_change_type_dict', '会员积分变更原因', '10', '0', '1', '2015-07-23 21:14:04', '1', '2015-08-17 11:11:30', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e1370c48de43440fba4962624dff5ecd', '2', '活动奖励', 'customer_integral_change_type_dict', '会员积分变更原因', '30', '0', '1', '2015-08-25 10:07:14', '1', '2015-11-18 11:19:26', '', '0');


/*==============================================================*/
/*	增加:注册渠道统计菜单项                                                                                      

  		*/
/*==============================================================*/
delete from sys_menu where id = 'f98c6930431444c6a6efceb794cff63f';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f98c6930431444c6a6efceb794cff63f', '73ece50b959f41c59a90a1d3951c24e8', '0,1,c4391fe4778142a7a5938fcf30c01fbd,73ece50b959f41c59a90a1d3951c24e8,', '注册渠道统计', '300', '/customer/adChannelInfo/registPopChannelsStatisticsList', '', 'icon-comment', '1', 'customer:adChannelInfo:view', '1', '2015-11-18 09:52:54', '1', '2015-11-19 14:01:29', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', 'f98c6930431444c6a6efceb794cff63f');

update project_base set sort_in_list = project_id * 10, sort_in_index = project_id * 10;
update marketing_activity_info set end_date = '2015-11-08', status = '0' where acticity_id in (14,16);
update customer_free_withdraw_count_his set change_type_code = '0' where change_type_code = '2';

/*==============================================================*/
/* Table: customer_client_token(增加列  终端类型 term_type)          */
/*==============================================================*/
ALTER TABLE `customer_client_token`
ADD COLUMN `term_type`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '终端类型' AFTER `token`;

/*==============================================================*/
/* Table: customer_client_token(修改主键 customer_id 为 customer_id + term_type)*/
/*==============================================================*/
ALTER TABLE `customer_client_token`
DROP PRIMARY KEY,
ADD PRIMARY KEY (`customer_id`, `term_type`);

/*==============================================================*/
/* 数据维护：更新原来token数据，终端类型统一为微信端          						*/
/*==============================================================*/
update customer_client_token t set t.term_type='3' where (t.term_type ='' or term_type is null);

/*==============================================================*/
/* 数据维护: 更新账户待收本金                                                                                                                                */
/*==============================================================*/
update  customer_balance t,(SELECT sum(a.principal) as principal,a.payee_user_id
  from project_repayment_split_record a
  left join project_base b
    on a.project_id = b.project_id
 where a. status = '0'
   and b.project_status in ('5','6')
group by a.payee_user_id) d set t.will_principal=d.principal where t.account_id=d.payee_user_id;


/*==============================================================*/
/* 数据维护: 更新账户累计收益                                                                                                                                */
/*==============================================================*/
update  customer_balance t,(SELECT sum(a.interest) as interest,a.payee_user_id
  from project_repayment_split_record a
  left join project_base b
    on a.project_id = b.project_id
 where a. status = '1'
   and b.project_status in ('5','6','7')
group by a.payee_user_id) d set t.sum_profit=d.interest where t.account_id=d.payee_user_id;  



/*==============================================================*/
/* 增加: 首页轮播图菜单项                                                                                                                             */
/*==============================================================*/
delete from sys_menu where id = '2412d86cfaa649558bf45648411c70db';
delete from sys_menu where id = '2faf7900e1c645f9af237bb60c4a0510';
delete from sys_menu where id = '4c2348ee4f5c4f6db325b36bcce704b9';
delete from sys_menu where id = '585044065a6d433b9b581927beb6cd03';
delete from sys_menu where id = 'a8de2ba338474f1fa63e077d41079472';
delete from sys_menu where id = 'bc620a99d5af4eba88daaa09cd9d1959';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2412d86cfaa649558bf45648411c70db', '2faf7900e1c645f9af237bb60c4a0510', '0,1,31,40,2faf7900e1c645f9af237bb60c4a0510,', '管理', '180', '/carousel/carouselInfo/list', '', 'icon-th-large', '1', 'carousel:carouselInfo:view', '1', '2015-11-18 11:27:53', '1', '2015-11-18 12:15:40', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2faf7900e1c645f9af237bb60c4a0510', '40', '0,1,31,40,', '首页轮播图', '110', '/carousel/carouselInfo/form', '', 'icon-book', '1', 'carousel:carouselInfo:view', '1', '2015-07-03 11:27:58', '1', '2015-11-19 09:54:12', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4c2348ee4f5c4f6db325b36bcce704b9', '2faf7900e1c645f9af237bb60c4a0510', '0,1,31,40,2faf7900e1c645f9af237bb60c4a0510,', '审批', '90', '', '', '', '0', 'carousel:carouselInfo:review', '1', '2015-11-18 11:12:17', '1', '2015-11-18 12:22:42', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('585044065a6d433b9b581927beb6cd03', '2faf7900e1c645f9af237bb60c4a0510', '0,1,31,40,2faf7900e1c645f9af237bb60c4a0510,', '修改', '120', '/carousel/carouselInfo/form', '', '', '0', 'carousel:carouselInfo:edit', '1', '2015-11-18 11:16:27', '1', '2015-11-18 11:29:47', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a8de2ba338474f1fa63e077d41079472', '2faf7900e1c645f9af237bb60c4a0510', '0,1,31,40,2faf7900e1c645f9af237bb60c4a0510,', '创建', '30', '', '', '', '0', 'carousel:carouselInfo:edit', '1', '2015-11-18 11:11:18', '1', '2015-11-18 11:15:26', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bc620a99d5af4eba88daaa09cd9d1959', '2faf7900e1c645f9af237bb60c4a0510', '0,1,31,40,2faf7900e1c645f9af237bb60c4a0510,', '审核', '210', '/carousel/carouselInfo/reviewList', '', 'icon-ok', '1', 'carousel:carouselInfo:view', '1', '2015-11-18 11:41:04', '1', '2015-11-18 12:14:54', '', '0');
delete from sys_role_menu where menu_id in ('2412d86cfaa649558bf45648411c70db','2faf7900e1c645f9af237bb60c4a0510','4c2348ee4f5c4f6db325b36bcce704b9','585044065a6d433b9b581927beb6cd03','a8de2ba338474f1fa63e077d41079472','bc620a99d5af4eba88daaa09cd9d1959');
INSERT INTO `sys_role_menu` VALUES ('1', '2412d86cfaa649558bf45648411c70db');
INSERT INTO `sys_role_menu` VALUES ('1', '2faf7900e1c645f9af237bb60c4a0510');
INSERT INTO `sys_role_menu` VALUES ('1', '4c2348ee4f5c4f6db325b36bcce704b9');
INSERT INTO `sys_role_menu` VALUES ('1', '585044065a6d433b9b581927beb6cd03');
INSERT INTO `sys_role_menu` VALUES ('1', 'a8de2ba338474f1fa63e077d41079472');
INSERT INTO `sys_role_menu` VALUES ('1', 'bc620a99d5af4eba88daaa09cd9d1959');

/*==============================================================*/
/* 增加: 新手任务管理菜单项                                                                                                                             */
/*==============================================================*/
delete from sys_menu where id = '5b25dca2d69d48c5a137c8876c124c87';
delete from sys_menu where id = '6b80989f12224b3a9519d663a5d36018';
delete from sys_menu where id = 'b12feeda976947dd9846d42c3aac293e';
delete from sys_menu where id = 'b16ce52a8cae4e8584fd1c064a169ac0';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5b25dca2d69d48c5a137c8876c124c87', '6b80989f12224b3a9519d663a5d36018', '0,1,6fe1901412d042bcb2010240ea57f1e0,b12feeda976947dd9846d42c3aac293e,6b80989f12224b3a9519d663a5d36018,', '删除', '60', '', '', '', '0', 'customer:customerNewTask:edit', '1', '2015-11-19 19:41:07', '1', '2015-11-19 19:42:32', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6b80989f12224b3a9519d663a5d36018', 'b12feeda976947dd9846d42c3aac293e', '0,1,6fe1901412d042bcb2010240ea57f1e0,b12feeda976947dd9846d42c3aac293e,', '管理', '30', '/customer/customerNewTask/list', '', 'icon-lock', '1', 'customer:customerNewTask:view', '1', '2015-11-19 10:36:47', '1', '2015-11-19 10:38:52', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b12feeda976947dd9846d42c3aac293e', '6fe1901412d042bcb2010240ea57f1e0', '0,1,6fe1901412d042bcb2010240ea57f1e0,', '新手任务管理', '90', '', '', '', '1', '', '1', '2015-11-19 10:16:30', '1', '2015-11-19 10:37:27', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b16ce52a8cae4e8584fd1c064a169ac0', '6b80989f12224b3a9519d663a5d36018', '0,1,6fe1901412d042bcb2010240ea57f1e0,b12feeda976947dd9846d42c3aac293e,6b80989f12224b3a9519d663a5d36018,', '修改', '30', '', '', '', '0', 'customer:customerNewTask:edit', '1', '2015-11-19 19:40:45', '1', '2015-11-19 19:43:08', '', '0');

INSERT INTO `sys_role_menu` VALUES ('1', '5b25dca2d69d48c5a137c8876c124c87');
INSERT INTO `sys_role_menu` VALUES ('1', '6b80989f12224b3a9519d663a5d36018');
INSERT INTO `sys_role_menu` VALUES ('1', 'b12feeda976947dd9846d42c3aac293e');
INSERT INTO `sys_role_menu` VALUES ('1', 'b16ce52a8cae4e8584fd1c064a169ac0');

/*==============================================================*/
/* 增加: 审核 ,轮播图类型字典                                                                                                              */
/*==============================================================*/
delete from sys_dict where id = '2caa00a6270b40b69d2e04b9f67d9659';
delete from sys_dict where id = 'b2edc8b06c034a14ab8103c1ba39b1d3';
delete from sys_dict where id = 'd4d6b2f8d7884113b5445096ca2416eb';
delete from sys_dict where id = '3bcba5bb3f2e48d7978d11b7a94aaf25';
delete from sys_dict where id = '5c7e72e4ca914c82b03c42cb3e2f0aa3';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2caa00a6270b40b69d2e04b9f67d9659', '1', '审核完毕', 'shenghe', '审核', '20', '0', '1', '2015-11-09 14:23:45', '1', '2015-11-09 14:23:45', '审核完毕', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b2edc8b06c034a14ab8103c1ba39b1d3', '0', '创建', 'shenghe', '创建', '20', '0', '1', '2015-11-09 17:17:13', '1', '2015-11-09 17:17:13', '创建', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d4d6b2f8d7884113b5445096ca2416eb', '-1', '拒绝', 'shenghe', '审核', '10', '0', '1', '2015-11-09 14:15:35', '1', '2015-11-13 10:11:38', '轮播图审核', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3bcba5bb3f2e48d7978d11b7a94aaf25', '2', '项目', 'photo_type', '轮播图类型', '20', '0', '1', '2015-11-17 14:16:29', '1', '2015-11-17 19:24:49', '轮播图类型为地址', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5c7e72e4ca914c82b03c42cb3e2f0aa3', '1', '活动', 'photo_type', '轮播图类型', '10', '0', '1', '2015-11-17 14:15:46', '1', '2015-11-17 14:21:20', '轮播图类型为活动', '0');

/*==============================================================*/
/* 数据初始化: 移动端版本参数                                                                                                                               */
/*==============================================================*/
delete from sys_mobile_version_para;
INSERT INTO `sys_mobile_version_para` VALUES (1, '1.0.0', 'ANDROID版本', 'http://www.fdjf.net/client/fdjf_hsbank-1.0.1-fdjf-FDJF-release-aligned-com.fdjf.hsbank.apk', 'FDJF', '1', '1');
INSERT INTO `sys_mobile_version_para` VALUES (6, '1.0.0', 'iOS版本', '', '', '2', '1');

/*==============================================================*/
/* 数据初始化: 新手任务                                                                                                                               */
/*==============================================================*/
delete from customer_new_task;
INSERT INTO `customer_new_task` VALUES (1, 'step1', '获得5元投资劵', 10, '1', '2015-11-13 11:12:41', '2015-11-19 10:39:49');
INSERT INTO `customer_new_task` VALUES (2, 'step2', '获得10元投资劵', 30, '1', '2015-11-13 11:13:18', NULL);
INSERT INTO `customer_new_task` VALUES (3, 'step3', '又获得10元投资劵', 40, '1', '2015-11-13 11:13:54', NULL);
INSERT INTO `customer_new_task` VALUES (4, 'step4', '超级大礼包已发放', 60, '1', '2015-11-13 11:14:21', NULL);

/*==============================================================*/
/* 数据初始化: 轮播图                                                                                                                               */
/*==============================================================*/
delete from carousel_info;
INSERT INTO `carousel_info` VALUES (104, '邀请好友活动', '/userfiles/1/files/carousel/carouselInfo/2015/11/banner_2_0.png', '1', 'http://m.hsbank360.com/activity/maintain/index.html?inApp=true', 40, '2015-11-1 00:00:00', '2020-12-31 23:59:59', '1', '2015-11-17 16:41:46', 1, '2015-11-17 17:02:13', '1');
INSERT INTO `carousel_info` VALUES (105, '双11活动', '/userfiles/1/files/carousel/carouselInfo/2015/11/banner_2_1.png', '1', 'http://m.hsbank360.com/activity/11/?inApp=true', 30, '2015-11-1 00:00:00', '2019-12-31 23:59:59', '1', '2015-11-17 16:49:53', 1, '2015-11-17 19:50:54', '1');
INSERT INTO `carousel_info` VALUES (106, '安全保障', '/userfiles/1/files/carousel/carouselInfo/2015/11/banner_2_2.png', '1', 'http://m.hsbank360.com/#/more/safe/?_hideNavbar=1&_hideNavbarBottom=1', 10, '2015-11-1 16:51:35', '2020-12-31 16:51:43', '1', '2015-11-17 16:50:44', 1, '2015-11-17 17:02:29', '1');


/*==============================================================*/
/*定时任务菜单项                                                                                                                            */
/*==============================================================*/
delete from sys_menu where id='10610747c4d54a828f2b4c75a9674893';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('10610747c4d54a828f2b4c75a9674893', '67', '0,1,2,67,', '定时任务', '100', '/log/logTimerTask', '', 'icon-book', '1', 'log:logTimerTask:view', '1', '2015-12-03 09:58:03', '1', '2015-12-03 10:02:33', '', '0');
delete from sys_role_menu where menu_id='10610747c4d54a828f2b4c75a9674893';
INSERT INTO `sys_role_menu` VALUES ('1', '10610747c4d54a828f2b4c75a9674893');
/*==============================================================*/
/*定时任务字典                                                                                                                */
/*==============================================================*/
delete from sys_dict where id = '03240a6a098f417d83c220679798076b';
delete from sys_dict where id = '50bf681d91024dfe91a20a4133c5a87e';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('03240a6a098f417d83c220679798076b', '0', '失败', 'timer_task_result', '定时任务结果', '10', '0', '1', '2015-12-03 11:46:55', '1', '2015-12-03 11:46:55', '&quot;   0   &quot; 成功    &rdquo;1&ldquo;  失败', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('50bf681d91024dfe91a20a4133c5a87e', '1', '成功', 'timer_task_result', '定时任务结果', '20', '0', '1', '2015-12-03 11:47:09', '1', '2015-12-03 11:47:19', '&quot;   0   &quot; 成功    &rdquo;1&ldquo;  失败', '0');







