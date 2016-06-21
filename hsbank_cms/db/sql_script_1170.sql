drop table if exists sys_exchange_rate_para;

/*==============================================================*/
/* Table: sys_exchange_rate_para                                */
/*==============================================================*/
create table sys_exchange_rate_para
(
   id                   int not null auto_increment comment 'id',
   rate_type            varchar(2) comment '汇率类型',
   rate_name            varchar(50) comment '汇率名称',
   annualized_rate      decimal(6,4) comment '年化利率',
   create_dt            datetime comment '创建时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table sys_exchange_rate_para comment '汇率参数表';

/*==============================================================*/
/* 自定义消息表增加字段                                                                                                                                               */
/*==============================================================*/
ALTER TABLE `custom_message`
ADD COLUMN `is_urgent`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否紧急' AFTER `label`,
ADD COLUMN `target_type`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标类型' AFTER `is_urgent`,
ADD COLUMN `target`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标参数' AFTER `target_type`,
ADD COLUMN `is_click`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可点击' AFTER `target`;

/*==============================================================*/
/* 增加汇率类型数据字典                                                                                                                                              */
/*==============================================================*/
delete from sys_dict where id in ('305c9a818507455799ad1a77799b33a0','db09391d4c4b40edb92cd3ecccf64b8a');
INSERT INTO `sys_dict` VALUES ('305c9a818507455799ad1a77799b33a0', '1', '货币基金', 'exchange_rate_type_dict', '货币基金', 10, '0', '1', '2016-4-20 15:36:13', '1', '2016-4-20 15:36:13', '货币基金', '0');
INSERT INTO `sys_dict` VALUES ('db09391d4c4b40edb92cd3ecccf64b8a', '2', '银行活期', 'exchange_rate_type_dict', '银行活期', 30, '0', '1', '2016-4-20 15:36:37', '1', '2016-4-20 15:36:37', '银行活期', '0');

/*==============================================================*/
/* 增加汇率参数菜单及分配权限                                                                                                                                              */
/*==============================================================*/
delete from sys_menu where id in ('3ab545c26819431cb6be2ecaa5e7abbc','d570ef6d8ac6426198a4ad329fd44e57','e2480b99964643bfa50f114c1903032f');
INSERT INTO `sys_menu` VALUES ('3ab545c26819431cb6be2ecaa5e7abbc', 'e2480b99964643bfa50f114c1903032f', '0,1,2,3,e2480b99964643bfa50f114c1903032f,', '查看', 60, '', '', '', '0', 'sys:sysExchangeRatePara:view', '1', '2016-4-20 15:56:15', '1', '2016-4-20 15:56:15', '', '0');
INSERT INTO `sys_menu` VALUES ('d570ef6d8ac6426198a4ad329fd44e57', 'e2480b99964643bfa50f114c1903032f', '0,1,2,3,e2480b99964643bfa50f114c1903032f,', '编辑', 30, '', '', '', '0', 'sys:sysExchangeRatePara:edit', '1', '2016-4-20 15:55:36', '1', '2016-4-20 15:55:36', '', '0');
INSERT INTO `sys_menu` VALUES ('e2480b99964643bfa50f114c1903032f', '3', '0,1,2,3,', '汇率参数管理', 300, '/sys/sysExchangeRatePara/list', '', 'icon-sitemap', '1', '', '1', '2016-4-20 15:54:50', '1', '2016-4-20 15:54:50', '', '0');

delete from sys_role_menu where menu_id in ('3ab545c26819431cb6be2ecaa5e7abbc','d570ef6d8ac6426198a4ad329fd44e57','e2480b99964643bfa50f114c1903032f');
INSERT INTO `sys_role_menu` VALUES ('1', '3ab545c26819431cb6be2ecaa5e7abbc');
INSERT INTO `sys_role_menu` VALUES ('1', 'd570ef6d8ac6426198a4ad329fd44e57');
INSERT INTO `sys_role_menu` VALUES ('1', 'e2480b99964643bfa50f114c1903032f');

/*==============================================================*/
/* 初始化汇率参数表                                                                                                                                          */
/*==============================================================*/
delete from sys_exchange_rate_para;
INSERT INTO `sys_exchange_rate_para` VALUES (1, '1', '货币基金', 0.0300, '2016-4-20 17:41:45', '0');
INSERT INTO `sys_exchange_rate_para` VALUES (2, '2', '银行活期', 0.0035, '2016-4-20 17:42:14', '0');


drop table if exists gamble;

/*==============================================================*/
/* Table: gamble                                                */
/*==============================================================*/
create table gamble
(
   id                   bigint not null auto_increment comment '编号',
   account_id           bigint comment '用户',
   activity_id          bigint comment '活动编号',
   choice_side          varchar(20) comment '选择队伍',
   op_dt                datetime comment '竞猜时间',
   op_term              varchar(2) comment '竞猜终端',
   right_side           varchar(20) comment '比赛结果',
   award_dt             datetime comment '领奖时间',
   primary key (id)
);

alter table gamble comment '竞猜';

drop table if exists join_match_record;

/*==============================================================*/
/* Table: join_match_record                                     */
/*==============================================================*/
create table join_match_record
(
   id                   bigint not null auto_increment comment '编号',
   side                 varchar(20) comment '队伍',
   account_id           bigint comment '用户',
   activity_id          bigint comment '活动编号',
   op_dt                datetime comment '参赛时间',
   op_term              varchar(2) comment '参赛终端',
   primary key (id)
);

alter table join_match_record comment '参赛记录';


/*==============================================================*/
/* 消息产生规则增加字段                                                                                                                                               */
/*==============================================================*/
ALTER TABLE `message_create_rule`
ADD COLUMN `is_urgent`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否紧急' AFTER `label`,
ADD COLUMN `target_type`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标类型' AFTER `is_urgent`,
ADD COLUMN `target`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标参数' AFTER `target_type`,
ADD COLUMN `is_click`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可点击' AFTER `target`;


/*==============================================================*/
/* 为photo_type增加键值   其他（0）                                                                                                                                          */
/*==============================================================*/
delete from sys_dict where id in ('a06df210bd4145dbb8e440b9d6b870c6');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a06df210bd4145dbb8e440b9d6b870c6', '0', '其他', 'photo_type', '轮播图类型', '30', '0', '1', '2016-04-26 16:08:58', '1', '2016-04-26 16:08:58', '其他', '0');




