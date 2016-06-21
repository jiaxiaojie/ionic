/*在carousel_info中增加字段activity_time*/
ALTER TABLE `carousel_info`
ADD COLUMN `activity_time`  bigint  DEFAULT NULL COMMENT '活动开始时间' AFTER `type_code`;

ALTER TABLE `project_base`
ADD COLUMN `can_use_rate_ticket`  int NULL COMMENT '能否使用加息券' AFTER `is_recommend`,
ADD COLUMN `duration_type`  varchar(2) NULL COMMENT '项目期限类型' AFTER `area_id`;

ALTER TABLE `project_investment_record`
ADD COLUMN `rate_ticket_ids`  varchar(1000) NULL COMMENT '加息券ID' AFTER `ticket_ids`;

ALTER TABLE `project_repayment_record`
ADD COLUMN `rate_ticket_interest`  decimal(13,2) NULL COMMENT '加息' AFTER `interest`;

ALTER TABLE `project_repayment_plan`
ADD COLUMN `rate_ticket_interest`  decimal(13,2) NULL COMMENT '加息' AFTER `interest`;

ALTER TABLE `project_repayment_split_record`
ADD COLUMN `rate_ticket_interest`  decimal(13,2) NULL COMMENT '加息' AFTER `interest`;

drop table if exists rate_ticket_type;

/*==============================================================*/
/* Table: rate_ticket_type                                      */
/*==============================================================*/
create table rate_ticket_type
(
   id                   bigint not null auto_increment comment '编号',
   ticket_type_name     varchar(20) comment '加息券名称',
   rate                 decimal(5,3) comment '加息',
   rate_duration        int comment '加息期限（天）',
   max_amount           decimal(13,2) comment '额度上限（元）',
   term_of_validity     int comment '有效期限制（天）',
   status               varchar(2) comment '状态',
   use_description      varchar(1000) comment '使用说明',
   create_user          bigint comment '创建人',
   create_dt            datetime comment '创建时间',
   last_modify_user     bigint comment '最后一次修改人',
   last_modify_dt       datetime comment '修改时间',
   primary key (id)
);

alter table rate_ticket_type comment '加息券类型';


drop table if exists customer_rate_ticket;

/*==============================================================*/
/* Table: customer_rate_ticket                                  */
/*==============================================================*/
create table customer_rate_ticket
(
   id                   bigint not null auto_increment comment 'id',
   account_id           bigint comment '账号编号',
   ticket_type_id       bigint comment '加息券类型编号',
   get_dt               datetime comment '获得时间',
   invalid_dt           datetime comment '失效时间',
   get_remark           varchar(200) comment '获得备注',
   use_dt               datetime comment '使用时间',
   use_remark           varchar(200) comment '使用备注',
   use_project_id       bigint comment '使用项目',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table customer_rate_ticket comment '会员加息券清单';


/*==============================================================*/
/*	增加 项目期限类型数据字典                                                                            */
/*==============================================================*/
delete from sys_dict where id in ('021feb6379d34f3496bc7aa7f55ad2ea','cf7010c118d34ee18e825d5facaff633');
INSERT INTO `sys_dict` VALUES ('021feb6379d34f3496bc7aa7f55ad2ea', '2', '按月', 'project_duration_type_dict', '项目期限类型', 30, '0', '1', '2016-3-31 13:18:06', '1', '2016-3-31 13:18:20', '', '0');
INSERT INTO `sys_dict` VALUES ('cf7010c118d34ee18e825d5facaff633', '1', '按日', 'project_duration_type_dict', '项目期限类型', 10, '0', '1', '2016-3-31 13:17:44', '1', '2016-3-31 13:17:44', '', '0');

/*==============================================================*/
/*	更改原有项目期限类型为按月、假删项目还款方式数据字典（等额本金、等额本息）                               */
/*==============================================================*/
update project_base set duration_type = '2' where duration_type is null;
update sys_dict set del_flag='1' where id in('c901d9693bb04312a3c6d84d12f2f554','dfca33b2749342279558e495b15b9128') and type='project_repayment_mode_dict';

delete from sys_menu where id in ('9357225a85a44d21b5078c819accfb6e','d7f20bea93614d88b786f8400c3777ee','dd90b739210b453da5902a4e92abaa97');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9357225a85a44d21b5078c819accfb6e', '2c25c21db34b4af7b5866598b362b261', '0,1,2,2c25c21db34b4af7b5866598b362b261,', '加息券类型管理', '60', '/sys/rateTicketType/list', '', 'icon-circle', '1', '', '1', '2016-04-05 14:14:07', '1', '2016-04-05 14:14:37', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d7f20bea93614d88b786f8400c3777ee', '9357225a85a44d21b5078c819accfb6e', '0,1,2,2c25c21db34b4af7b5866598b362b261,9357225a85a44d21b5078c819accfb6e,', '查看', '30', '', '', '', '0', 'sys:rateTicketType:view', '1', '2016-04-05 14:15:38', '1', '2016-04-05 14:15:38', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('dd90b739210b453da5902a4e92abaa97', '9357225a85a44d21b5078c819accfb6e', '0,1,2,2c25c21db34b4af7b5866598b362b261,9357225a85a44d21b5078c819accfb6e,', '编辑', '60', '', '', '', '0', 'sys:rateTicketType:edit', '1', '2016-04-05 14:16:14', '1', '2016-04-05 14:16:14', '', '0');
delete from sys_role_menu where menu_id in ('9357225a85a44d21b5078c819accfb6e','d7f20bea93614d88b786f8400c3777ee','dd90b739210b453da5902a4e92abaa97');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', '9357225a85a44d21b5078c819accfb6e');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', 'd7f20bea93614d88b786f8400c3777ee');
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES ('1', 'dd90b739210b453da5902a4e92abaa97');

/*==============================================================*/
/*	增加 债权管理相关表结构                                                                            */
/*==============================================================*/

drop table if exists credit_base_info;

drop table if exists credit_invest_user_info;

drop table if exists credit_machine_account;

/*==============================================================*/
/* Table: credit_base_info                                      */
/*==============================================================*/
create table credit_base_info
(
   id                   bigint not null auto_increment comment 'ID',
   credit_name          varchar(200) comment '债权名称',
   credit_project_type  bigint comment '债权项目类型',
   credit_original_money float(13,2) comment '债权原始金额',
   credit_financing_money float(13,2) comment '债权融资金额',
   credit_real_deadline int comment '债权实际期限',
   credit_financing_deadline int comment '债权融资期限',
   credit_interest      float(5,2) comment '债权利率',
   financier_name       varchar(50) comment '融资人名称',
   real_borrower_name   varchar(50) comment '实际借款人名称',
   receivables_analogue_name varchar(50) comment '应收账款对手方名称',
   is_assigned          varchar(2) comment '是否已做转让',
   raised_money_on_line float(13,2) comment '线上已经募集金额',
   raised_money_below_line float(13,2) comment '线下已经募集金额',
   raised_money         float(13,2) comment '已经募集金额',
   raising_money        float(13,2) comment '募集中金额',
   to_raise_money       float(13,2) comment '待募集金额',
   credit_status        varchar(2) comment '债权状态',
   relevant_document_original varchar(2000) comment '相关文件原件',
   public_document      varchar(2000) comment '公开文件',
   raise_begin_date     date comment '募集起始时间',
   raise_end_date       date comment '募集结束时间',
   primary key (id)
);

alter table credit_base_info comment '债权基本信息';

/*==============================================================*/
/* Table: credit_invest_user_info                               */
/*==============================================================*/
create table credit_invest_user_info
(
   id                   bigint not null auto_increment comment 'ID',
   investor_name        varchar(100) comment '投资人姓名',
   Investor_gender      varchar(2) comment '投资人性别',
   Investor_birthday    date comment '投资人生日',
   investor_contact_info varchar(50) comment '投资人联系方式(手机号)',
   id_number            varchar(100) comment '证件号',
   Id_address           varchar(100) comment '证件地址',
   id_type              varchar(2) comment '证件类型',
   bank_card_no         varchar(50) comment '银行卡号',
   opening_bank         varchar(2) comment '开户行',
   primary key (id)
);

alter table credit_invest_user_info comment '债权投资用户信息';

/*==============================================================*/
/* Table: credit_machine_account                                */
/*==============================================================*/
create table credit_machine_account
(
   id                   bigint not null auto_increment comment 'ID',
   contract_no          varchar(500) comment '合同编号',
   invest_money         float(13,2) comment '投资金额',
   interest_rate        float(5,2) comment '利率',
   interest_calculation varchar(2) comment '计息方式',
   value_date           date comment '起息日',
   investment_horizon   int comment '投资期限（月）',
   expiring_date        date comment '到期日',
   financial_manager    varchar(50) comment '理财经理',
   business_manager     varchar(50) comment '营业部经理',
   payment_method       varchar(2) comment '付款方式',
   commission_charge    float(13,2) comment '手续费',
   contract_address     varchar(100) comment '合同地址',
   relevant_document    varchar(2000) comment '相关文件',
   procedure_documents  varchar(20) comment '手续文件',
   gift_recipients      varchar(500) comment '礼品领用',
   ahead_redemptive     varchar(500) comment '提前赎回',
   remark               varchar(500) comment '备注',
   credit_invest_user_id bigint comment '债权投资用户ID',
   credit_id            bigint(20) comment '所属债权',
   primary key (id)
);

alter table credit_machine_account comment '债权台帐';



/*==============================================================*/
/*	增加 债权管理菜单                                                                            */
/*==============================================================*/
delete from sys_menu where id in ('35ec077879154b4ca7db9055a8019470','51bf4d436e81488992406b0d74969dde','52a40cbc3e844f2f99b18c62c5acf30d'
,'5eb3c64deb5241518a483f3049347b49','63e7c30ec5094898b64c70ba16e7cca8','6cf8f196ec78440f966029788e46f6fa',
'88a74efe10994d9c91c78c932217f790','9883e80a748245d2ae4b3ef3d1628129','bb17588121d747d79a0c8e43465a8553',
'bdeddc7f6b0042b0825aee6b9444eb02','f8724bb39ab743029754c233d40e9e31');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('35ec077879154b4ca7db9055a8019470', 'bb17588121d747d79a0c8e43465a8553', '0,1,bb17588121d747d79a0c8e43465a8553,', '债权台帐管理', '30', '', '', '', '1', '', '1', '2016-03-29 14:08:43', '1', '2016-03-29 14:08:43', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('51bf4d436e81488992406b0d74969dde', '63e7c30ec5094898b64c70ba16e7cca8', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,63e7c30ec5094898b64c70ba16e7cca8,', '债权投资用户查看', '30', '', '', '', '0', 'credit:creditInvestUserInfo:view', '1', '2016-04-07 14:17:25', '1', '2016-04-07 14:17:25', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('52a40cbc3e844f2f99b18c62c5acf30d', '6cf8f196ec78440f966029788e46f6fa', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,6cf8f196ec78440f966029788e46f6fa,', '债券基本信息编辑', '60', '', '', '', '0', 'credit:creditBaseInfo:edit', '1', '2016-03-29 14:15:05', '1', '2016-03-29 14:15:05', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5eb3c64deb5241518a483f3049347b49', '35ec077879154b4ca7db9055a8019470', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,', '债权台帐', '60', '/credit/creditMachineAccount/list', '', 'icon-desktop', '1', 'credit:creditMachineAccount:view,credit:creditMachineAccount:edit', '1', '2016-03-30 13:36:49', '1', '2016-03-30 14:04:50', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('63e7c30ec5094898b64c70ba16e7cca8', '35ec077879154b4ca7db9055a8019470', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,', '债权投资用户管理', '90', '/credit/creditInvestUserInfo/list', '', 'icon-credit-card', '1', 'credit:creditInvestUserInfo:view,credit:creditInvestUserInfo:edit', '1', '2016-03-30 14:06:31', '1', '2016-04-07 14:17:01', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6cf8f196ec78440f966029788e46f6fa', '35ec077879154b4ca7db9055a8019470', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,', '债权列表', '30', '/credit/creditBaseInfo/list', '', 'icon-coffee', '1', '', '1', '2016-03-29 14:10:06', '1', '2016-03-29 14:14:04', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('88a74efe10994d9c91c78c932217f790', '6cf8f196ec78440f966029788e46f6fa', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,6cf8f196ec78440f966029788e46f6fa,', '债券基本信息查看', '30', '', '', '', '0', 'credit:creditBaseInfo:view', '1', '2016-03-29 14:14:33', '1', '2016-03-29 14:14:39', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9883e80a748245d2ae4b3ef3d1628129', '63e7c30ec5094898b64c70ba16e7cca8', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,63e7c30ec5094898b64c70ba16e7cca8,', '债权投资用户编辑', '60', '', '', '', '0', 'credit:creditInvestUserInfo:edit', '1', '2016-04-07 14:17:50', '1', '2016-04-07 14:17:50', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bb17588121d747d79a0c8e43465a8553', '1', '0,1,', '债权管理', '120', '', '', '', '1', '', '1', '2016-03-29 14:08:30', '1', '2016-03-29 14:12:01', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bdeddc7f6b0042b0825aee6b9444eb02', '5eb3c64deb5241518a483f3049347b49', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,5eb3c64deb5241518a483f3049347b49,', '债权台帐查看', '30', '', '', '', '0', 'credit:creditMachineAccount:view', '1', '2016-04-07 14:16:08', '1', '2016-04-07 14:16:08', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f8724bb39ab743029754c233d40e9e31', '5eb3c64deb5241518a483f3049347b49', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,5eb3c64deb5241518a483f3049347b49,', '债权台帐编辑', '60', '', '', '', '0', 'credit:creditMachineAccount:edit', '1', '2016-04-07 14:16:35', '1', '2016-04-07 14:16:35', '', '0');

delete  FROM sys_role_menu where menu_id in ('35ec077879154b4ca7db9055a8019470','51bf4d436e81488992406b0d74969dde','52a40cbc3e844f2f99b18c62c5acf30d'
,'5eb3c64deb5241518a483f3049347b49','63e7c30ec5094898b64c70ba16e7cca8','6cf8f196ec78440f966029788e46f6fa',
'88a74efe10994d9c91c78c932217f790','9883e80a748245d2ae4b3ef3d1628129','bb17588121d747d79a0c8e43465a8553',
'bdeddc7f6b0042b0825aee6b9444eb02','f8724bb39ab743029754c233d40e9e31');

INSERT INTO sys_role_menu VALUES ('1', '35ec077879154b4ca7db9055a8019470');
INSERT INTO sys_role_menu VALUES ('1', '51bf4d436e81488992406b0d74969dde');
INSERT INTO sys_role_menu VALUES ('1', '52a40cbc3e844f2f99b18c62c5acf30d');
INSERT INTO sys_role_menu VALUES ('1', '5eb3c64deb5241518a483f3049347b49');
INSERT INTO sys_role_menu VALUES ('1', '63e7c30ec5094898b64c70ba16e7cca8');
INSERT INTO sys_role_menu VALUES ('1', '6cf8f196ec78440f966029788e46f6fa');
INSERT INTO sys_role_menu VALUES ('1', '88a74efe10994d9c91c78c932217f790');
INSERT INTO sys_role_menu VALUES ('1', '9883e80a748245d2ae4b3ef3d1628129');
INSERT INTO sys_role_menu VALUES ('1', 'bb17588121d747d79a0c8e43465a8553');
INSERT INTO sys_role_menu VALUES ('1', 'bdeddc7f6b0042b0825aee6b9444eb02');
INSERT INTO sys_role_menu VALUES ('1', 'f8724bb39ab743029754c233d40e9e31');

/*==============================================================*/
/*	增加 债相关字典项                                                                            */
/*==============================================================*/
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1406bc5bb684470bbc78ce1769c6daeb', '0', '募集中', 'credit_status', '债权状态', '10', '0', '1', '2016-03-29 13:39:07', '1', '2016-03-29 13:39:07', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('14479ecd3dee47019a0c5646cf6d24a9', '2', '停止', 'credit_status', '债权状态', '30', '0', '1', '2016-03-29 13:39:25', '1', '2016-03-29 13:39:25', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1afa71a1cf0e490483e78d6cc98286d5', '3', '到期转存', 'payment_method', '付款方式', '40', '0', '1', '2016-03-30 11:41:40', '1', '2016-03-30 11:41:40', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3d17fcfd16c04f3aa53207c5ba0b982e', '3', '其它', 'id_type', '证件类型', '40', '0', '1', '2016-03-30 13:52:49', '1', '2016-03-30 13:52:49', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4976d3eda97840c4920dd433aeff59b4', '0', '按月付息', 'interest_calculation', '计息方式', '10', '0', '1', '2016-03-30 11:39:09', '1', '2016-03-30 11:39:09', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('569d0e4ea1df4eb6b604927a80140fb3', '1', '动产质押', 'credit_project_type', '债权项目类型', '20', '0', '1', '2016-03-29 13:25:22', '1', '2016-03-29 13:25:22', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('642a62fd905a4066b54d4eefd362fd08', '2', '车辆抵押', 'credit_project_type', '债权项目类型', '30', '0', '1', '2016-03-29 13:25:30', '1', '2016-03-29 13:25:30', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6ab247151f21434e8b0e5de8f85c740a', '2', '担保函', 'procedure_documents', '手续文件', '30', '0', '1', '2016-03-30 11:44:16', '1', '2016-03-30 11:44:16', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7221eb4b01f240798837a21147c922bc', '1', '盛付通POS机', 'payment_method', '付款方式', '20', '0', '1', '2016-03-30 11:41:10', '1', '2016-03-30 11:41:10', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('774f389ae8504e9a9ffdb2fb5431243f', '4', '资产管理计划', 'credit_project_type', '债权项目类型', '50', '0', '1', '2016-03-29 13:25:53', '1', '2016-03-29 13:25:53', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9024455b7d98495194ec95601f88a599', '1', '到期还本付息', 'interest_calculation', '计息方式', '20', '0', '1', '2016-03-30 11:39:21', '1', '2016-03-30 11:39:21', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a5ace1a5b3b24b189048572f21afc43a', '2', '转账', 'payment_method', '付款方式', '30', '0', '1', '2016-03-30 11:41:20', '1', '2016-03-30 11:41:20', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bd8f0a8bbe23437a856f4af2f6edf8c9', '0', '身份证', 'id_type', '证件类型', '10', '0', '1', '2016-03-30 13:52:24', '1', '2016-03-30 13:52:24', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c4d6f97bc6854636b85c9d27fb5b9f29', '2', '台胞证', 'id_type', '证件类型', '30', '0', '1', '2016-03-30 13:52:41', '1', '2016-03-30 13:52:41', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c84cd7c6fba54b9bab247e4d0dbc6833', '1', '护照', 'id_type', '证件类型', '20', '0', '1', '2016-03-30 13:52:32', '1', '2016-03-30 13:52:32', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c8cd57060eb64bab84f566c6598c014c', '3', '供应链金融', 'credit_project_type', '债权项目类型', '40', '0', '1', '2016-03-29 13:25:40', '1', '2016-03-29 13:25:40', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c95c787bad3c49739f28c570a3858104', '0', '通联POS机', 'payment_method', '付款方式', '10', '0', '1', '2016-03-30 11:40:54', '1', '2016-03-30 11:40:54', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d758fca9077f48ea882dcf8cfc9da6b1', '3', '短信确认', 'procedure_documents', '手续文件', '40', '0', '1', '2016-03-30 11:44:27', '1', '2016-03-30 11:44:27', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('dd59a988c1114b32bd650f9a8069267c', '0', '债权确认函', 'procedure_documents', '手续文件', '10', '0', '1', '2016-03-30 11:43:52', '1', '2016-03-30 11:43:52', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('dfa95ae2c72c4b28960dc12541fc630b', '1', '募集结束', 'credit_status', '债权状态', '20', '0', '1', '2016-03-29 13:39:18', '1', '2016-03-29 13:39:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e6b7c75f3e714f798413294f0b41aa49', '0', '房产抵押', 'credit_project_type', '债权项目类型', '10', '0', '1', '2016-03-29 13:25:14', '1', '2016-03-29 13:25:14', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f749a5a04a5748749e0ee2a9ce233408', '1', '回购函', 'procedure_documents', '手续文件', '20', '0', '1', '2016-03-30 11:44:03', '1', '2016-03-30 11:44:03', '', '0');

/*==============================================================*/
/* project_base中增加 债权ID                                                                        */
/*==============================================================*/
ALTER TABLE `project_base`
ADD COLUMN `credit_id`  bigint  DEFAULT NULL COMMENT '债权ID';



/*==============================================================*/
/* Table: demand_redemption_information_record                                */
/*==============================================================*/
CREATE TABLE demand_redemption_information_record (
  id bigint(20) NOT NULL  auto_increment  COMMENT '记录编号',
  redemption_id bigint(20) DEFAULT NULL COMMENT '赎回申请编号',
  reedmption_date datetime DEFAULT NULL COMMENT '申请时间',
  info_reason varchar(50) DEFAULT NULL COMMENT '记录信息原因',
  account_amount decimal(13,2) DEFAULT NULL COMMENT '融资人账户余额',
  redeem_principal decimal(13,2) DEFAULT NULL COMMENT '赎回本金',
  status varchar(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (id)
);

alter table demand_redemption_information_record comment '活期赎回信息记录';


/*==============================================================*/
/* 增加    赎回状态字典                          */
/*==============================================================*/
delete from sys_menu where id in ('5c36f16fff8e49fc9c1d3a53143541c5','b1b1f189678d4cb1b6c634df34baa169');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5c36f16fff8e49fc9c1d3a53143541c5', '1', '成功', 'redemption_execution_status', '赎回申请执行状态', '20', '0', '1', '2016-04-11 16:01:49', '1', '2016-04-11 16:01:49', '成功', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b1b1f189678d4cb1b6c634df34baa169', '0', '失败', 'redemption_execution_status', '赎回申请执行状态', '10', '0', '1', '2016-04-11 16:01:18', '1', '2016-04-11 16:01:18', '失败', '0');
/*==============================================================*/
/* 增加活期产品赎回记录菜单                */
/*==============================================================*/
delete from sys_menu where id in('5d37735bd430469e9961a0df88db0a56','7c667362a3b348a5a2d3cc13910d703d','f969ce79ef2941319b050e0ac0aa3a7b');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5d37735bd430469e9961a0df88db0a56', '7c667362a3b348a5a2d3cc13910d703d', '0,1,b9ec9632968c42e19ff5e92cafe13788,7c667362a3b348a5a2d3cc13910d703d,', '活期产品赎回记录', '30', '/current/demandRedemptionInformationRecord', '', 'icon-cloud', '1', '', '1', '2016-04-11 17:03:21', '1', '2016-04-13 14:12:59', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7c667362a3b348a5a2d3cc13910d703d', 'b9ec9632968c42e19ff5e92cafe13788', '0,1,b9ec9632968c42e19ff5e92cafe13788,', '活期产品赎回记录', '5180', '', '', '', '1', '', '1', '2016-04-11 17:01:12', '1', '2016-04-13 14:22:25', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f969ce79ef2941319b050e0ac0aa3a7b', '5d37735bd430469e9961a0df88db0a56', '0,1,b9ec9632968c42e19ff5e92cafe13788,7c667362a3b348a5a2d3cc13910d703d,5d37735bd430469e9961a0df88db0a56,', '活期产品赎回记录查看', '30', '', '', '', '0', 'current:demandRedemptionInformationRecord:view', '1', '2016-04-13 14:14:19', '1', '2016-04-13 14:21:04', '', '0');
delete  FROM sys_role_menu where menu_id in ('5d37735bd430469e9961a0df88db0a56','7c667362a3b348a5a2d3cc13910d703d','f969ce79ef2941319b050e0ac0aa3a7b');
INSERT INTO sys_role_menu VALUES ('1', '5d37735bd430469e9961a0df88db0a56');
INSERT INTO sys_role_menu VALUES ('1', '7c667362a3b348a5a2d3cc13910d703d');
INSERT INTO sys_role_menu VALUES ('1', 'f969ce79ef2941319b050e0ac0aa3a7b');



/*==============================================================*/
/* credit_base_info，credit_machine_account，credit_invest_user_info中增加 create_date列                                                                        */
/*==============================================================*/
ALTER TABLE `credit_base_info`
ADD COLUMN `create_date`  datetime  DEFAULT NULL COMMENT '创建日期';

ALTER TABLE `credit_machine_account`
ADD COLUMN `create_date`  datetime  DEFAULT NULL COMMENT '创建日期';

ALTER TABLE `credit_invest_user_info`
ADD COLUMN `create_date`  datetime  DEFAULT NULL COMMENT '创建日期';

/*==============================================================*/
/* 修改credit_base_info中credit_project_type列 为varchar(2)类型                                                                       */
/*==============================================================*/
Alter table credit_base_info modify `credit_project_type` varchar(2) COMMENT '债权项目类型';


/*==============================================================*/
/* credit_base_info中增加
[营业执照  
贸易合同 
借款合同  
央行登记信息
实地考察照片
发票（10个工作日内上传）  
物流签收单据（十个工作日内上传）]
这几个上传附件项                                                                   */
/*==============================================================*/
ALTER TABLE `credit_base_info`
ADD COLUMN `business_license`  varchar(2000)  DEFAULT NULL COMMENT '营业执照';

ALTER TABLE `credit_base_info`
ADD COLUMN `trading_contract`  varchar(2000)  DEFAULT NULL COMMENT '贸易合同';

ALTER TABLE `credit_base_info`
ADD COLUMN `loan_contract`  varchar(2000)  DEFAULT NULL COMMENT '借款合同';

ALTER TABLE `credit_base_info`
ADD COLUMN `central_bank_registration_information`  varchar(2000)  DEFAULT NULL COMMENT '央行登记信息';

ALTER TABLE `credit_base_info`
ADD COLUMN `field_trip_photos`  varchar(2000)  DEFAULT NULL COMMENT '实地考察照片';

ALTER TABLE `credit_base_info`
ADD COLUMN `invoice`  varchar(2000)  DEFAULT NULL COMMENT '发票';

ALTER TABLE `credit_base_info`
ADD COLUMN `logistics_sign_receipts`  varchar(2000)  DEFAULT NULL COMMENT '物流签收单据';


drop table if exists customer_balance_alert;

/*==============================================================*/
/* Table: customer_balance_alert                                */
/*==============================================================*/
create table customer_balance_alert
(
   id                   bigint not null auto_increment comment '编号',
   platform_user_no     varchar(50) comment '平台编号',
   title                varchar(50) comment '标题',
   content              varchar(200) comment '内容',
   mobile               varchar(1000) comment '手机号',
   amount               decimal(13,2) comment '警戒额度',
   create_dt            datetime comment '创建时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table customer_balance_alert comment '账户余额告警表';

/*==============================================================*/
/*添加 账户余额提醒菜单                                */
/*==============================================================*/
delete from sys_menu where id in('3bf0f8fabc214313a15eee3b5ad169d8','ae4f76aa580a40518ac936278724c71a','d38a930ceff34033bb8fa468524b2a92');
INSERT INTO sys_menu (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3bf0f8fabc214313a15eee3b5ad169d8', 'ae4f76aa580a40518ac936278724c71a', '0,1,2,3,ae4f76aa580a40518ac936278724c71a,', '账户余额警戒设置更改', '60', '', '', '', '0', 'customer:customerBalanceAlert:edit', '1', '2016-04-14 15:43:02', '1', '2016-04-15 11:38:41', '', '0');
INSERT INTO sys_menu (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ae4f76aa580a40518ac936278724c71a', '3', '0,1,2,3,', '账户余额警戒设置', '270', '/customer/customerBalanceAlert', '', 'icon-bell', '1', '', '1', '2016-04-14 15:24:31', '1', '2016-04-14 15:41:24', '', '0');
INSERT INTO sys_menu (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d38a930ceff34033bb8fa468524b2a92', 'ae4f76aa580a40518ac936278724c71a', '0,1,2,3,ae4f76aa580a40518ac936278724c71a,', '账户余额警戒设置查看', '30', '', '', '', '0', 'customer:customerBalanceAlert:view', '1', '2016-04-14 15:26:19', '1', '2016-04-15 11:38:00', '', '0');
delete  FROM sys_role_menu where menu_id in ('3bf0f8fabc214313a15eee3b5ad169d8','ae4f76aa580a40518ac936278724c71a','d38a930ceff34033bb8fa468524b2a92');
INSERT INTO sys_role_menu VALUES ('1', '3bf0f8fabc214313a15eee3b5ad169d8');
INSERT INTO sys_role_menu VALUES ('1', 'ae4f76aa580a40518ac936278724c71a');
INSERT INTO sys_role_menu VALUES ('1', 'd38a930ceff34033bb8fa468524b2a92');




/*==============================================================*/
/*将债权实际期限和债权融资期限改为区间，添加和删除credit_base_info的部分字段                                */
/*==============================================================*/
ALTER TABLE `credit_base_info`
ADD COLUMN `credit_real_begin_date`  date  DEFAULT NULL COMMENT '债权实际起始时间';


ALTER TABLE `credit_base_info`
ADD COLUMN `credit_real_end_date`  date  DEFAULT NULL COMMENT '债权实际结束时间';

ALTER TABLE `credit_base_info`
ADD COLUMN `credit_financing_begin_date`  date  DEFAULT NULL COMMENT '债权融资起始时间';

ALTER TABLE `credit_base_info`
ADD COLUMN `credit_financing_end_date`  date  DEFAULT NULL COMMENT '债权融资结束时间';

alter table `credit_base_info` drop column credit_real_deadline;
alter table `credit_base_info` drop column credit_financing_deadline;

/*更新项目排序*/
update project_base set sort_in_list = 9999999-project_id*3,sort_in_index = 9999999-project_id*3 where project_status > '3';




/*==============================================================*/
/*credit_base_info增加是否为草稿列                                */
/*==============================================================*/
ALTER TABLE `credit_base_info`
ADD COLUMN `is_draft`  varchar(2)  DEFAULT NULL COMMENT '是否为草稿';

/*==============================================================*/
/*sys_menu 增加提醒菜单项                                */
/*==============================================================*/
delete from sys_menu where id in('ea607d789e2a4d87b3319edb082f12f6');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ea607d789e2a4d87b3319edb082f12f6', '35ec077879154b4ca7db9055a8019470', '0,1,bb17588121d747d79a0c8e43465a8553,35ec077879154b4ca7db9055a8019470,', '提醒', '120', '/credit/creditInvestUserInfo/birthdayRemindList?pageType=creditRemindMenu', '', 'icon-asterisk', '1', 'credit:creditInvestUserInfo:view,credit:creditInvestUserInfo:edit', '1', '2016-04-14 16:27:15', '1', '2016-04-18 18:49:10', '', '0');
delete  FROM sys_role_menu where menu_id in ('ea607d789e2a4d87b3319edb082f12f6');
INSERT INTO sys_role_menu VALUES ('1', 'ea607d789e2a4d87b3319edb082f12f6');

/*==============================================================*/
/*修改credit_base_info中金额相关字段类型为 decimal                               */
/*==============================================================*/
Alter table credit_base_info modify `credit_original_money` decimal(13,2) COMMENT '债权原始金额';
Alter table credit_base_info modify `credit_financing_money` decimal(13,2) COMMENT '债权融资金额';
Alter table credit_base_info modify `raised_money_on_line` decimal(13,2) COMMENT '线上已经募集金额';
Alter table credit_base_info modify `raised_money_below_line` decimal(13,2) COMMENT '线下已经募集金额';
Alter table credit_base_info modify `raising_money` decimal(13,2) COMMENT '募集中金额';
Alter table credit_base_info modify `to_raise_money` decimal(13,2) COMMENT '待募集金额';
Alter table credit_machine_account modify `invest_money` decimal(13,2) COMMENT '投资金额';
