drop table if exists operation_data;

/*==============================================================*/
/* Table: operation_data                                        */
/*==============================================================*/
create table operation_data
(
   id                   bigint not null auto_increment comment '编号',
   date                 date comment '日期',
   register_count       int comment '注册人数',
   open_third_account_count int comment '开通第三方账号人数',
   bind_bank_card_count int comment '绑卡用户数',
   recharge_amount      decimal(13,2) comment '充值额度',
   withdraw_amount      decimal(13,2) comment '提现额度',
   investment_times     varchar(50) comment '投资次数',
   investment_amount    decimal(13,2) comment '投资额度',
   primary key (id)
);

alter table operation_data comment '运营数据';


/*==============================================================*/
/*	增加：运营数据菜单及其子菜单项                                                                                                                      	    */
/*==============================================================*/
delete from sys_menu where id='526880b97e1c4c61ba1df684b014e1d5';
delete from sys_menu where id='f7fc5b48354d498e953700bf55e7ae62';
delete from sys_menu where id='0092882d07794049904893aee078ec36';
delete from sys_menu where id='c4645e3af7e748e2b976665e4c220692';
delete from sys_menu where id='71c71f827a7b44198bc49d459e00e9a5';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('526880b97e1c4c61ba1df684b014e1d5', '1', '0,1,', '运营数据', '5060', '', '', '', '1', '', '1', '2015-12-01 13:43:55', '1', '2015-12-01 13:46:29', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f7fc5b48354d498e953700bf55e7ae62', '526880b97e1c4c61ba1df684b014e1d5', '0,1,526880b97e1c4c61ba1df684b014e1d5,', '运营数据', '30', '', '', '', '1', '', '1', '2015-12-01 13:45:43', '1', '2015-12-01 14:54:35', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0092882d07794049904893aee078ec36', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '运营数据查询', '30', '/operation/operationData/list', '', '', '1', 'operation:operationData:view', '1', '2015-12-01 14:57:21', '1', '2015-12-03 09:28:49', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c4645e3af7e748e2b976665e4c220692', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '项目运营数据', '60', '/operation/projectOperation/list', '', 'icon-pencil', '1', 'operation:projectOperation:view,project:projectRepaymentPlan:edit', '1', '2015-12-01 15:58:18', '1', '2015-12-25 15:49:36', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('71c71f827a7b44198bc49d459e00e9a5', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '项目还款计划', '90', '/operation/projectOperation/projectRepayPlanList', '', '', '1', '', '1', '2015-12-01 15:59:18', '1', '2015-12-02 13:50:25', '', '0');
/*==============================================================*/
/*	增加：运营数据菜单及其子菜单项权限                                                                                                                         	    */
/*==============================================================*/
delete  FROM sys_role_menu where menu_id in ('526880b97e1c4c61ba1df684b014e1d5','f7fc5b48354d498e953700bf55e7ae62','0092882d07794049904893aee078ec36','c4645e3af7e748e2b976665e4c220692','71c71f827a7b44198bc49d459e00e9a5');
INSERT INTO `sys_role_menu` VALUES ('1', '526880b97e1c4c61ba1df684b014e1d5');
INSERT INTO `sys_role_menu` VALUES ('1', 'f7fc5b48354d498e953700bf55e7ae62');
INSERT INTO `sys_role_menu` VALUES ('1', '0092882d07794049904893aee078ec36');
INSERT INTO `sys_role_menu` VALUES ('1', 'c4645e3af7e748e2b976665e4c220692');
INSERT INTO `sys_role_menu` VALUES ('1', '71c71f827a7b44198bc49d459e00e9a5');


/*==============================================================*/
/*	sys_mobile_version_para表添加列：version_size(varchar(20),'版本大小')*/
/*	此sql不可重复执行                                                                                                                   */
/*==============================================================*/
ALTER TABLE `sys_mobile_version_para`
ADD COLUMN `version_size`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本大小' AFTER `version`;



/*==============================================================*/
/*	修改cms_category表： 内容发布只保留公告管理，公共文档和新闻管理      */
/*	                                                                                                                   */
/*==============================================================*/
UPDATE `cms_category` SET `del_flag`='1' WHERE `id` not in('d61b13d02c5f498cb283a6486721b85a','b29eca639edf4723b453b074add4907c','0fbea42e4eb5434d9f6a8c868c9fa531');

/*==============================================================*/
/*	增加：活动状态字典数据                                                                                                                      	    */
/*==============================================================*/
delete from sys_dict where id='117778bada854aa88ac427fd1668d839';
delete from sys_dict where id='3744b60d38ca4df5b3b32dfd2cce3e39';
delete from sys_dict where id='8cbdf1f926bb4a8d842900a63aa39705';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('117778bada854aa88ac427fd1668d839', '2', '已过期', 'act_status', '最新活动状态', '30', '0', '1', '2015-12-04 13:40:53', '1', '2015-12-04 14:18:44', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3744b60d38ca4df5b3b32dfd2cce3e39', '1', '已开始', 'act_status', '最新活动状态', '20', '0', '1', '2015-12-04 13:40:40', '1', '2015-12-04 13:40:40', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8cbdf1f926bb4a8d842900a63aa39705', '0', '未开始', 'act_status', '最新活动状态', '10', '0', '1', '2015-12-04 13:40:22', '1', '2015-12-04 13:40:22', '', '0');

/*==============================================================*/
/*	增加：前台连接池监视（带TASK）     前台连接池监视（不带TASK） 菜单项                                                                                                 	    */
/*==============================================================*/
delete from sys_menu where id='6a1a3b54c71f4b61be8b4c75eda0020e';
delete from sys_menu where id='94c23b2cc1e64a96bc915605be1f263f';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6a1a3b54c71f4b61be8b4c75eda0020e', '67', '0,1,2,67,', '前台连接池监视（带TASK）', '160', 'http://112.74.85.169:9506/druid/index.html', '', 'icon-resize-horizontal', '1', '', '1', '2015-12-07 10:58:58', '1', '2015-12-07 10:58:58', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('94c23b2cc1e64a96bc915605be1f263f', '67', '0,1,2,67,', '前台连接池监视（不带TASK）', '130', 'http://112.74.85.169:9507/druid/index.html', '', 'icon-resize-horizontal', '1', '', '1', '2015-12-07 10:58:14', '1', '2015-12-07 10:58:14', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '6a1a3b54c71f4b61be8b4c75eda0020e');
INSERT INTO `sys_role_menu` VALUES ('1', '94c23b2cc1e64a96bc915605be1f263f');

ALTER TABLE `customer_account`
ADD COLUMN `outer_id`  varchar(50) NULL COMMENT '外部编号' AFTER `ad_channel_id`;