drop table if exists project_investment_rank;

/*==============================================================*/
/* Table: project_investment_rank                               */
/*==============================================================*/
create table project_investment_rank
(
   id                   bigint not null auto_increment COMMENT '编号',
   account_id           bigint COMMENT '投资人',
   amount               float(13,2) COMMENT '投资金额',
   rank                 int COMMENT '名次',
   op_term              int COMMENT '终端',
   type                 varchar(2) COMMENT '类型',
   data_dt              datetime COMMENT '数据时间',
   create_dt            datetime COMMENT '创建时间',
   remark               varchar(500) COMMENT '备注',
   primary key (id)
);

alter table project_investment_rank comment '投资排行';

drop table if exists project_investment_rank_calendar;

/*==============================================================*/
/* Table: project_investment_rank_calendar                      */
/*==============================================================*/
create table project_investment_rank_calendar
(
   id                   bigint not null auto_increment COMMENT '编号',
   year                 varchar(6) COMMENT '年份',
   month                 varchar(2) COMMENT '月份',
   week                 int COMMENT '周数',
   begin_dt             datetime COMMENT '开始时间',
   end_dt               datetime COMMENT '结束时间',
   status               varchar(2) COMMENT '状态',
   primary key (id)
);

alter table project_investment_rank_calendar comment '投资排行日历';

/*==============================================================*/
/*	增加:投资排行类型数据字典                                                                                                                         	*/
/*==============================================================*/
delete from sys_dict where type='investment_rank_type_dict';
INSERT INTO `sys_dict` VALUES ('21e9a122395b49aa92a9cfb9b14662a8', '3', '年', 'investment_rank_type_dict', '投资排行类型', 70, '0', '1', '2015-11-23 16:55:23', '1', '2015-11-23 16:55:23', '', '0');
INSERT INTO `sys_dict` VALUES ('6387ef5eaf724655ac12a5354b0aea1d', '1', '周', 'investment_rank_type_dict', '投资排行类型', 30, '0', '1', '2015-11-23 16:53:50', '1', '2015-11-23 16:53:50', '', '0');
INSERT INTO `sys_dict` VALUES ('755e998975aa4c01b309117e746f3092', '0', '日', 'investment_rank_type_dict', '投资排行类型', 10, '0', '1', '2015-11-23 16:53:17', '1', '2015-11-23 16:53:17', '', '0');
INSERT INTO `sys_dict` VALUES ('b05b3156394a44b2a4bd4eafd85beefd', '2', '月', 'investment_rank_type_dict', '投资排行类型', 50, '0', '1', '2015-11-23 16:54:28', '1', '2015-11-23 16:54:28', '', '0');

/*==============================================================*/
/*	增加：投资排行菜单                                                                                                                         	    */
/*==============================================================*/
delete FROM sys_menu where id in ('1655165dcc54407983e470cd8bf165a8','2a698f6e9fad49b6a14570e213e05b8c','a290e331b0b047d5b1af471f66d4244b','a752681d972e4e9f83b1e64d464cda82');
INSERT INTO `sys_menu` VALUES ('1655165dcc54407983e470cd8bf165a8', 'a290e331b0b047d5b1af471f66d4244b', '0,1,c4391fe4778142a7a5938fcf30c01fbd,a290e331b0b047d5b1af471f66d4244b,', '投资排行', 30, '/project/projectInvestmentRank/list', '', 'icon-tasks', '1', '', '1', '2015-11-24 19:21:16', '1', '2015-11-24 19:21:16', '', '0');
INSERT INTO `sys_menu` VALUES ('2a698f6e9fad49b6a14570e213e05b8c', '1655165dcc54407983e470cd8bf165a8', '0,1,c4391fe4778142a7a5938fcf30c01fbd,a290e331b0b047d5b1af471f66d4244b,1655165dcc54407983e470cd8bf165a8,', '编辑', 30, '', '', '', '0', 'project:projectInvestmentRank:edit', '1', '2015-11-24 19:21:53', '1', '2015-11-24 19:21:53', '', '0');
INSERT INTO `sys_menu` VALUES ('a290e331b0b047d5b1af471f66d4244b', 'c4391fe4778142a7a5938fcf30c01fbd', '0,1,c4391fe4778142a7a5938fcf30c01fbd,', '投资统计', 160, '', '', '', '1', '', '1', '2015-11-24 19:17:49', '1', '2015-11-24 19:17:49', '', '0');
INSERT INTO `sys_menu` VALUES ('a752681d972e4e9f83b1e64d464cda82', '1655165dcc54407983e470cd8bf165a8', '0,1,c4391fe4778142a7a5938fcf30c01fbd,a290e331b0b047d5b1af471f66d4244b,1655165dcc54407983e470cd8bf165a8,', '查看', 60, '', '', '', '0', 'project:projectInvestmentRank:view', '1', '2015-11-24 19:22:25', '1', '2015-11-24 19:22:25', '', '0');

/*==============================================================*/
/*	增加：投资排行菜单权限                                                                                                                         	    */
/*==============================================================*/
delete  FROM sys_role_menu where menu_id in ('1655165dcc54407983e470cd8bf165a8','2a698f6e9fad49b6a14570e213e05b8c','a290e331b0b047d5b1af471f66d4244b','a752681d972e4e9f83b1e64d464cda82');
INSERT INTO `sys_role_menu` VALUES ('1', '1655165dcc54407983e470cd8bf165a8');
INSERT INTO `sys_role_menu` VALUES ('1', '2a698f6e9fad49b6a14570e213e05b8c');
INSERT INTO `sys_role_menu` VALUES ('1', 'a290e331b0b047d5b1af471f66d4244b');
INSERT INTO `sys_role_menu` VALUES ('1', 'a752681d972e4e9f83b1e64d464cda82');

drop table if exists marketing_wheel_prize_info;

/*==============================================================*/
/* Table: marketing_wheel_prize_info                            */
/*==============================================================*/
create table marketing_wheel_prize_info
(
   id                   bigint not null auto_increment comment '编号',
   name                 varchar(20) comment '奖品名称',
   label                varchar(20) comment '奖品标签',
   type                 varbinary(2) comment '奖品类型',
   value                varbinary(20) comment '奖品值',
   description          varchar(100) comment '奖品描述',
   logo                 varchar(100) comment '奖品logo',
   number               int comment '奖品数量',
   rotate               float(6,3) comment '中奖角度',
   primary key (id)
);

alter table marketing_wheel_prize_info comment '大转盘奖品信息';

drop table if exists marketing_wheel_prize_instance;

/*==============================================================*/
/* Table: marketing_wheel_prize_instance                          */
/*==============================================================*/
create table marketing_wheel_prize_instance
(
   id                   bigint not null auto_increment comment '编号',
   prize_id             bigint comment '奖品编号',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table marketing_wheel_prize_instance comment '大转盘奖品实例';

drop table if exists marketing_wheel_get_prize_record;

/*==============================================================*/
/* Table: marketing_wheel_get_prize_record                      */
/*==============================================================*/
create table marketing_wheel_get_prize_record
(
   id                   bigint not null auto_increment comment '编号',
   prize_instance_id      bigint comment '奖品详情编号',
   status               varchar(2) comment '状态',
   token                varchar(50) comment '中奖token',
   get_dt               datetime comment '中奖时间',
   op_term              varchar(2) comment '操作终端',
   invalid_dt           datetime comment '失效时间',
   account_id           bigint comment '中奖用户编号',
   give_dt              datetime comment '奖品赠送时间',
   primary key (id)
);

alter table marketing_wheel_get_prize_record comment '大转盘中奖记录';

delete from sys_dict where id in ('1d783ab2cc924bc4b9b4b574a55e1cba','276047a83ddf495684c3c9f9e3806cc4','5f2a1b6a3740418491dda862f60f513e');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1d783ab2cc924bc4b9b4b574a55e1cba', '2', '已被抽中', 'marketing_wheel_prize_instance_status_dict', '大转盘奖品实例状态', '30', '0', '1', '2015-11-24 13:46:14', '1', '2015-11-24 13:46:14', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('276047a83ddf495684c3c9f9e3806cc4', '1', '锁定', 'marketing_wheel_prize_instance_status_dict', '大转盘奖品实例状态', '20', '0', '1', '2015-11-24 13:44:31', '1', '2015-11-24 13:44:31', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5f2a1b6a3740418491dda862f60f513e', '0', '正常', 'marketing_wheel_prize_instance_status_dict', '大转盘奖品实例状态', '10', '0', '1', '2015-11-24 13:44:22', '1', '2015-11-24 13:44:22', '', '0');

delete from sys_dict where id in ('3a99532657e943da9c78c306f741a2d0','64cc108d8ad942e9a0718663c761036f','cd2a887b5a1d47418c69611998ba8cab','fc1f998432b74ce48dc895c53482f24f');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3a99532657e943da9c78c306f741a2d0', '0', '中奖', 'marketing_wheel_get_prize_record_status_dict', '大转盘中奖记录状态', '10', '0', '1', '2015-11-24 13:48:51', '1', '2015-11-24 13:48:51', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('64cc108d8ad942e9a0718663c761036f', '1', '待赠送', 'marketing_wheel_get_prize_record_status_dict', '大转盘中奖记录状态', '20', '0', '1', '2015-11-24 13:50:27', '1', '2015-11-24 13:50:27', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cd2a887b5a1d47418c69611998ba8cab', '2', '已赠送', 'marketing_wheel_get_prize_record_status_dict', '大转盘中奖记录状态', '30', '0', '1', '2015-11-24 13:50:45', '1', '2015-11-24 13:50:45', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('fc1f998432b74ce48dc895c53482f24f', '-1', '失效', 'marketing_wheel_get_prize_record_status_dict', '大转盘中奖记录状态', '40', '0', '1', '2015-11-24 13:50:54', '1', '2015-11-24 13:50:54', '', '0');

delete from sys_dict where id in ('6ef21bc40e7d404e89192e08f9effb1a','c332e76ab8ec43ed8bc591ae73d2f98c','cb119cc7d82e40e99455e979683453ad');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6ef21bc40e7d404e89192e08f9effb1a', '2', '现金', 'marketing_wheel_prize_type_dict', '大转盘奖品类型', '20', '0', '1', '2015-11-24 13:53:51', '1', '2015-11-24 13:53:51', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c332e76ab8ec43ed8bc591ae73d2f98c', '5', '实物', 'marketing_wheel_prize_type_dict', '大转盘奖品类型', '30', '0', '1', '2015-11-24 13:54:03', '1', '2015-11-24 13:54:03', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cb119cc7d82e40e99455e979683453ad', '1', '投资券', 'marketing_wheel_prize_type_dict', '大转盘奖品类型', '10', '0', '1', '2015-11-24 13:53:41', '1', '2015-11-24 13:53:41', '', '0');

delete from sys_menu where id in ('2dabd363c50944f0820fdb853b215cee','51f20dc719e146969233647608977329','739a02c608904a40b1712f77d694ef0a','e878508b86914a3ea0ad6afa63956550');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2dabd363c50944f0820fdb853b215cee', '739a02c608904a40b1712f77d694ef0a', '0,1,6fe1901412d042bcb2010240ea57f1e0,51f20dc719e146969233647608977329,', '查看-奖品信息', '60', '', '', '', '0', 'marketing:marketingWheelPrizeInfo:view', '1', '2015-11-24 15:16:36', '1', '2015-11-24 15:16:36', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('51f20dc719e146969233647608977329', '6fe1901412d042bcb2010240ea57f1e0', '0,1,6fe1901412d042bcb2010240ea57f1e0,', '大转盘管理', '120', '', '', '', '1', '', '1', '2015-11-24 15:13:48', '1', '2015-11-24 15:13:48', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('739a02c608904a40b1712f77d694ef0a', '51f20dc719e146969233647608977329', '0,1,6fe1901412d042bcb2010240ea57f1e0,51f20dc719e146969233647608977329,', '奖品管理', '30', '/marketing/marketingWheelPrizeInfo/list', '', '', '1', '', '1', '2015-11-24 15:15:15', '1', '2015-11-24 15:15:15', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e878508b86914a3ea0ad6afa63956550', '739a02c608904a40b1712f77d694ef0a', '0,1,6fe1901412d042bcb2010240ea57f1e0,51f20dc719e146969233647608977329,', '修改-奖品信息', '90', '', '', '', '0', 'marketing:marketingWheelPrizeInfo:edit', '1', '2015-11-24 15:17:06', '1', '2015-11-24 15:17:06', '', '0');
delete from sys_role_menu where menu_id in ('2dabd363c50944f0820fdb853b215cee','51f20dc719e146969233647608977329','739a02c608904a40b1712f77d694ef0a','e878508b86914a3ea0ad6afa63956550');
INSERT INTO `sys_role_menu` VALUES ('1', '2dabd363c50944f0820fdb853b215cee');
INSERT INTO `sys_role_menu` VALUES ('1', '51f20dc719e146969233647608977329');
INSERT INTO `sys_role_menu` VALUES ('1', '739a02c608904a40b1712f77d694ef0a');
INSERT INTO `sys_role_menu` VALUES ('1', 'e878508b86914a3ea0ad6afa63956550');

ALTER TABLE `marketing_wheel_prize_info`
ADD COLUMN `get_tips`  varchar(50) NULL COMMENT '获奖提示' AFTER `value`;


/*==============================================================*/
/* 增加: 活动管理及其子菜单项                    */
/*==============================================================*/
delete from sys_menu where id = '0083d759b3c04f9aac52772d4b389775';
delete from sys_menu where id = '059661f5e087414aa4e4e0915b27195f';
delete from sys_menu where id = '44dad5b9c204445fb750e54211ac5412';
delete from sys_menu where id = '64b6462cfd594eed9b9ac6fb04e9b20f';
delete from sys_menu where id = '8a1334377fb545f1bc5100ee06a23bf2';
delete from sys_menu where id = 'd9d4665aad99449a9e30fa4aa92f2c3f';
delete from sys_menu where id = 'f6e49517df4a4dd9afadd54e3887dbcf';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0083d759b3c04f9aac52772d4b389775', '64b6462cfd594eed9b9ac6fb04e9b20f', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,64b6462cfd594eed9b9ac6fb04e9b20f,', '查看', '30', '', '', '', '0', 'content:activity:view', '1', '2015-11-24 11:29:34', '1', '2015-11-24 11:29:34', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('059661f5e087414aa4e4e0915b27195f', '44dad5b9c204445fb750e54211ac5412', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,44dad5b9c204445fb750e54211ac5412,', '查看', '30', '', '', '', '0', 'content:activity:review:view', '1', '2015-11-24 13:57:42', '1', '2015-11-24 13:57:42', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('44dad5b9c204445fb750e54211ac5412', '8a1334377fb545f1bc5100ee06a23bf2', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,', '审批', '60', '/content/activity/reviewList', '', 'icon-briefcase', '1', '', '1', '2015-11-24 13:37:04', '1', '2015-11-24 14:05:03', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('64b6462cfd594eed9b9ac6fb04e9b20f', '8a1334377fb545f1bc5100ee06a23bf2', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,', '管理', '30', '/content/activity', '', 'icon-briefcase', '1', '', '1', '2015-11-24 11:29:15', '1', '2015-11-24 11:29:15', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8a1334377fb545f1bc5100ee06a23bf2', '31', '0,1,31,', '活动管理', '1020', '', '', '', '1', '', '1', '2015-11-24 11:27:36', '1', '2015-11-24 11:27:36', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d9d4665aad99449a9e30fa4aa92f2c3f', '64b6462cfd594eed9b9ac6fb04e9b20f', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,64b6462cfd594eed9b9ac6fb04e9b20f,', '编辑', '60', '', '', '', '0', 'content:activity:edit', '1', '2015-11-24 11:29:53', '1', '2015-11-24 11:29:53', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f6e49517df4a4dd9afadd54e3887dbcf', '44dad5b9c204445fb750e54211ac5412', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,44dad5b9c204445fb750e54211ac5412,', '编辑', '60', '', '', '', '0', 'content:activity:review:edit', '1', '2015-11-24 13:58:08', '1', '2015-11-24 13:58:08', '', '0');
delete from sys_role_menu where menu_id = '0083d759b3c04f9aac52772d4b389775';
delete from sys_role_menu where menu_id = '059661f5e087414aa4e4e0915b27195f';
delete from sys_role_menu where menu_id = '44dad5b9c204445fb750e54211ac5412';
delete from sys_role_menu where menu_id = '64b6462cfd594eed9b9ac6fb04e9b20f';
delete from sys_role_menu where menu_id = '8a1334377fb545f1bc5100ee06a23bf2';
delete from sys_role_menu where menu_id = 'd9d4665aad99449a9e30fa4aa92f2c3f';
delete from sys_role_menu where menu_id = 'f6e49517df4a4dd9afadd54e3887dbcf';
INSERT INTO `sys_role_menu` VALUES ('1', '0083d759b3c04f9aac52772d4b389775');
INSERT INTO `sys_role_menu` VALUES ('1', '059661f5e087414aa4e4e0915b27195f');
INSERT INTO `sys_role_menu` VALUES ('1', '44dad5b9c204445fb750e54211ac5412');
INSERT INTO `sys_role_menu` VALUES ('1', '64b6462cfd594eed9b9ac6fb04e9b20f');
INSERT INTO `sys_role_menu` VALUES ('1', '8a1334377fb545f1bc5100ee06a23bf2');
INSERT INTO `sys_role_menu` VALUES ('1', 'd9d4665aad99449a9e30fa4aa92f2c3f');
INSERT INTO `sys_role_menu` VALUES ('1', 'f6e49517df4a4dd9afadd54e3887dbcf');

delete from sys_menu where id = 'a9c0471224114ac5b7580b7ccdb02eaa';
delete from sys_menu where id = 'cd60aaaf94a74ca48cce4549bc9b2a18';
delete from sys_menu where id = 'f4a3224ef48a4376ae7d5278f42750c4';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a9c0471224114ac5b7580b7ccdb02eaa', 'cd60aaaf94a74ca48cce4549bc9b2a18', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,cd60aaaf94a74ca48cce4549bc9b2a18,', '编辑', '120', '', '', '', '0', 'content:activity:edit', '1', '2015-11-25 14:13:17', '1', '2015-11-25 14:13:17', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cd60aaaf94a74ca48cce4549bc9b2a18', '8a1334377fb545f1bc5100ee06a23bf2', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,', '活动排序', '90', '/content/activity/sortList', '', 'icon-book', '1', '', '1', '2015-11-25 14:11:03', '1', '2015-11-25 14:14:18', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f4a3224ef48a4376ae7d5278f42750c4', 'cd60aaaf94a74ca48cce4549bc9b2a18', '0,1,31,8a1334377fb545f1bc5100ee06a23bf2,cd60aaaf94a74ca48cce4549bc9b2a18,', '查看', '90', '', '', '', '0', 'content:activity:view', '1', '2015-11-25 14:12:17', '1', '2015-11-25 14:12:41', '', '0');

delete from sys_role_menu where menu_id = 'a9c0471224114ac5b7580b7ccdb02eaa';
delete from sys_role_menu where menu_id = 'cd60aaaf94a74ca48cce4549bc9b2a18';
delete from sys_role_menu where menu_id = 'f4a3224ef48a4376ae7d5278f42750c4';
INSERT INTO `sys_role_menu` VALUES ('1', 'a9c0471224114ac5b7580b7ccdb02eaa');
INSERT INTO `sys_role_menu` VALUES ('1', 'cd60aaaf94a74ca48cce4549bc9b2a18');
INSERT INTO `sys_role_menu` VALUES ('1', 'f4a3224ef48a4376ae7d5278f42750c4');


/*==============================================================*/
/* 增加: 活动表                    */
/*==============================================================*/
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_activity
-- ----------------------------
DROP TABLE IF EXISTS `cms_activity`;
CREATE TABLE `cms_activity` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `activity_description` varchar(255) DEFAULT NULL COMMENT '活动描述',
  `activity_join` varchar(255) DEFAULT NULL COMMENT '活动链接',
  `activity_cover` varchar(255) DEFAULT NULL COMMENT '活动封面',
  `activity_status` varchar(2) DEFAULT NULL COMMENT '活动状态',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `start_dt` datetime DEFAULT NULL COMMENT '开始时间',
  `end_dt` datetime DEFAULT NULL COMMENT '结束时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_dt` datetime DEFAULT NULL COMMENT '创建时间',
  `review_user_id` bigint(20) DEFAULT NULL COMMENT '审批人',
  `review_dt` datetime DEFAULT NULL COMMENT '审批时间',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
  `termCodes` varchar(255) DEFAULT NULL COMMENT '渠道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

delete from sys_menu where id in('0a7359fbc4404afbae8394c4c1fdc23c','2ffcf1fa92a04dd5b8dbdd57c85436ad','d9de97473f77452f94d64052408ba99a');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0a7359fbc4404afbae8394c4c1fdc23c', '2ffcf1fa92a04dd5b8dbdd57c85436ad', '0,1,6fe1901412d042bcb2010240ea57f1e0,51f20dc719e146969233647608977329,2ffcf1fa92a04dd5b8dbdd57c85436ad,', '修改-中奖记录', '60', '', '', '', '0', 'marketing:marketingWheelGetPrizeRecord:edit', '1', '2015-11-25 16:08:28', '1', '2015-11-25 16:08:28', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2ffcf1fa92a04dd5b8dbdd57c85436ad', '51f20dc719e146969233647608977329', '0,1,6fe1901412d042bcb2010240ea57f1e0,51f20dc719e146969233647608977329,', '中奖记录', '120', '/marketing/marketingWheelGetPrizeRecord/list', '', '', '1', '', '1', '2015-11-25 16:07:24', '1', '2015-11-25 16:07:24', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d9de97473f77452f94d64052408ba99a', '2ffcf1fa92a04dd5b8dbdd57c85436ad', '0,1,6fe1901412d042bcb2010240ea57f1e0,51f20dc719e146969233647608977329,2ffcf1fa92a04dd5b8dbdd57c85436ad,', '查看-中奖记录', '30', '', '', '', '0', 'marketing:marketingWheelGetPrizeRecord:view', '1', '2015-11-25 16:08:00', '1', '2015-11-25 16:08:00', '', '0');
delete from sys_role_menu where menu_id in('0a7359fbc4404afbae8394c4c1fdc23c','2ffcf1fa92a04dd5b8dbdd57c85436ad','d9de97473f77452f94d64052408ba99a');
INSERT INTO `sys_role_menu` VALUES ('1', '0a7359fbc4404afbae8394c4c1fdc23c');
INSERT INTO `sys_role_menu` VALUES ('1', '2ffcf1fa92a04dd5b8dbdd57c85436ad');
INSERT INTO `sys_role_menu` VALUES ('1', 'd9de97473f77452f94d64052408ba99a');

delete from sys_dict where id = '849f71f266f84269accf1bff0a9f4bc6';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('849f71f266f84269accf1bff0a9f4bc6', '5', '实物', 'marketing_award_type_dict', '营销活动奖励产品类型', '50', '0', '1', '2015-11-26 10:17:07', '1', '2015-11-26 10:17:07', '', '0');

/*==============================================================*/
/* 增加: 查看还款明细菜单项                    */
/*==============================================================*/
delete from sys_menu where id = '49953ed40d7e4de593766aa11b4aeaf5';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('49953ed40d7e4de593766aa11b4aeaf5', '8254b5883c894a1287c696f41d6586ba', '0,1,44aee8dec38849159118f41a4767ece2,b3b6ba3bba644220b3eb9528bebe2f58,8254b5883c894a1287c696f41d6586ba,', '查看还款明细', '60', '', '', '', '0', 'project:projectRepaymentSplitRecord:view', '1', '2015-11-26 11:38:25', '1', '2015-11-26 11:38:25', '', '0');
delete from sys_role_menu where menu_id = '49953ed40d7e4de593766aa11b4aeaf5';
INSERT INTO `sys_role_menu` VALUES ('1', '49953ed40d7e4de593766aa11b4aeaf5');

/*==============================================================*/
/* Table: third_party_yeepay_para(增加列：
             yeepay_tenderorderno_prefix(varchar(500) '测试环境传给易宝trenderNo前缀')
             yeepay_gate_way_callback_url_prefix(varchar(500) '易宝前端浏览器callback地址前缀')
             yeepay_gate_way_notify_prefix(varchar(500) '易宝前端浏览器notify地址前缀')
             yeepay_direct_notify_url_prefix(varchar(500) '易宝直连notify地址前缀')
             yeepay_gate_way_wireless_callback_url_prefix(varcahr(500) '易宝前端浏览器移动端callback地址前缀') 
)          */
/*==============================================================*/
ALTER TABLE `third_party_yeepay_para`
ADD COLUMN `yeepay_tenderorderno_prefix`  varchar(500) NULL DEFAULT 0 COMMENT '测试环境传给易宝trenderNo前缀' AFTER `yeepay_notify_url_prefix`;
ALTER TABLE `third_party_yeepay_para`
ADD COLUMN `yeepay_gate_way_callback_url_prefix`  varchar(500) NULL DEFAULT 0 COMMENT '易宝前端浏览器callback地址前缀' AFTER `yeepay_tenderorderno_prefix`;
ALTER TABLE `third_party_yeepay_para`
ADD COLUMN `yeepay_gate_way_notify_prefix`  varchar(500) NULL DEFAULT 0 COMMENT '易宝前端浏览器notify地址前缀' AFTER `yeepay_gate_way_callback_url_prefix`;
ALTER TABLE `third_party_yeepay_para`
ADD COLUMN `yeepay_direct_notify_url_prefix`  varchar(500) NULL DEFAULT 0 COMMENT '易宝直连notify地址前缀' AFTER `yeepay_gate_way_notify_prefix`;
ALTER TABLE `third_party_yeepay_para`
ADD COLUMN `yeepay_gate_way_wireless_callback_url_prefix`  varchar(500) NULL DEFAULT 0 COMMENT '易宝前端浏览器移动端callback地址前缀' AFTER `yeepay_direct_notify_url_prefix`;

/*==============================================================*/
/* Table: sys_biz_para(增加列：
            project_max_amount_default(varchar(500) '最大投资金额')
            oneday_max_withdraw_count(varchar(500)  '一天最多提现次数')
  )          */
/*==============================================================*/
ALTER TABLE `sys_biz_para`
ADD COLUMN `project_max_amount_default`  varchar(500) NULL DEFAULT 0 COMMENT '最大投资金额' AFTER `project_transfer_max_assignment_hours`;
ALTER TABLE `sys_biz_para`
ADD COLUMN `oneday_max_withdraw_count`  varchar(500) NULL DEFAULT 0 COMMENT '一天最多提现次数' AFTER `project_max_amount_default`;



/*==============================================================*/
/* 增加: 最新活动数据                    */
/*==============================================================*/
delete FROM cms_activity where id = '1';
delete FROM cms_activity where id = '2';
delete FROM cms_activity where id = '3';
delete FROM cms_activity where id = '4';
INSERT INTO `cms_activity` (`id`, `activity_description`, `activity_join`, `activity_cover`, `activity_status`, `del_flag`, `start_dt`, `end_dt`, `create_user_id`, `create_dt`, `review_user_id`, `review_dt`, `title`, `sort`, `termCodes`) VALUES ('1', '每邀请一位好友注册并开通第三方托管账户，邀请人和被邀请人可获得丰厚奖励，邀请越多奖励多多...', '/activity/marketing', '/userfiles/1/images/content/activity/2015/11/latest_activity_s06.jpg', '1', '0', '2015-11-30 14:45:39', '2019-11-30 14:45:39', '1', '2015-11-30 14:45:29', '1', '2015-11-30 14:49:28', '你邀请我送钱', '0', ',0,');
INSERT INTO `cms_activity` (`id`, `activity_description`, `activity_join`, `activity_cover`, `activity_status`, `del_flag`, `start_dt`, `end_dt`, `create_user_id`, `create_dt`, `review_user_id`, `review_dt`, `title`, `sort`, `termCodes`) VALUES ('2', '开通P2P账户即送20元现金红包，用户10天内首次交易成功，还有200元现金红包赠送哦！一路豪礼，更多活动，更多精彩好礼等你来！', '/xszq', '/userfiles/1/images/content/activity/2015/11/activity-img-01.jpg', '1', '0', '2015-11-30 14:46:38', '2015-11-30 14:46:47', '1', '2015-11-30 14:46:58', '1', '2015-11-30 14:49:31', '注册马上送豪礼', '200', ',0,');
INSERT INTO `cms_activity` (`id`, `activity_description`, `activity_join`, `activity_cover`, `activity_status`, `del_flag`, `start_dt`, `end_dt`, `create_user_id`, `create_dt`, `review_user_id`, `review_dt`, `title`, `sort`, `termCodes`) VALUES ('3', '扫微信，或者注册花生金服线上平台，即可参与&ldquo;你敢摇，我就敢送&rdquo;新手礼遇，5-100元不等的现金红包，更多iPhone6、Ipad迷你等你带走！您会是那个Lucky Girl/Boy me ?!', '/activity/shake', '/userfiles/1/images/content/activity/2015/11/activity-img-02.jpg', '1', '0', '2015-11-30 14:48:08', '2015-11-30 14:48:10', '1', '2015-11-30 14:48:13', '1', '2015-11-30 14:49:33', '你敢摇我就敢送', '210', ',0,');
INSERT INTO `cms_activity` (`id`, `activity_description`, `activity_join`, `activity_cover`, `activity_status`, `del_flag`, `start_dt`, `end_dt`, `create_user_id`, `create_dt`, `review_user_id`, `review_dt`, `title`, `sort`, `termCodes`) VALUES ('4', '用户在浏览平台过程中，发现有任何程序bug、文字问题或者合理的用户体验问题，欢迎及时反馈给我们。审核合理，即可获得 100 元现金投资券。', '/activity/bug', '/userfiles/1/images/content/activity/2015/11/activity-img-03.jpg', '1', '0', '2015-11-30 14:48:53', '2015-11-30 14:48:55', '1', '2015-11-30 14:49:19', '1', '2015-11-30 14:49:36', '少年醒醒快来找茬', '220', ',0,');


/*==============================================================*/
/* 增加: 首页轮播图数据                    */
/*==============================================================*/
delete FROM carousel_info where carousel_id = '146';
delete FROM carousel_info where carousel_id = '147';
INSERT INTO `carousel_info` (`carousel_id`, `title`, `picture_big`, `type_code`, `target`, `sort`, `start_dt`, `end_dt`, `create_user_id`, `create_dt`, `review_user_id`, `review_dt`, `status`) VALUES ('146', '你邀请我送钱', '/userfiles/1/images/carousel/carouselInfo/2015/11/banner-11.jpg', '1', '/activity/marketing', '3', '2015-11-30 15:12:04', '2019-11-30 15:12:10', '1', '2015-11-30 15:16:17', '1', '2015-11-30 15:16:43', '1');
INSERT INTO `carousel_info` (`carousel_id`, `title`, `picture_big`, `type_code`, `target`, `sort`, `start_dt`, `end_dt`, `create_user_id`, `create_dt`, `review_user_id`, `review_dt`, `status`) VALUES ('147', '投资保障信息安全', '/userfiles/1/images/carousel/carouselInfo/2015/11/banner-10.jpg', '1', '/gywm/aqbz', '2', '2015-11-30 15:14:32', '2019-11-30 15:14:36', '1', '2015-11-30 15:14:44', '1', '2015-11-30 15:14:55', '1');

delete FROM carousel_show_term where id = '418';
delete FROM carousel_show_term where id = '419';
INSERT INTO `carousel_show_term` (`id`, `carousel_id`, `term_code`) VALUES ('418', '147', '0');
INSERT INTO `carousel_show_term` (`id`, `carousel_id`, `term_code`) VALUES ('419', '146', '0');

