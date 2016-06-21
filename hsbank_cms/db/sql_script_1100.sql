drop table if exists current_project_notice;

/*==============================================================*/
/* Table: current_project_notice                                */
/*==============================================================*/
create table current_project_notice
(
   id                   bigint not null auto_increment comment '流水号',
   project_id           bigint comment '项目编号',
   title                varchar(50) comment '标题',
   content              text comment '内容',
   publish_dt           datetime comment '发布时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table current_project_notice comment '活期产品公告';
drop table if exists current_account_summary;

/*==============================================================*/
/* Table: current_account_summary                               */
/*==============================================================*/
create table current_account_summary
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账户编号',
   total_investment_money decimal(13,2) comment '累计投资金额',
   total_get_interest   decimal(13,4) comment '累计获取利息',
   total_redeem_principal decimal(13,2) comment '累计赎回本金',
   total_redeem_interest decimal(13,2) comment '累计赎回利息',
   current_principal    decimal(13,2) comment '当前持有本金',
   create_dt            datetime comment '创建时间',
   modify_dt            datetime comment '修改时间',
   primary key (id)
);

alter table current_account_summary comment '活期账户总览';
drop table if exists current_project_hold_info;

/*==============================================================*/
/* Table: current_project_hold_info                             */
/*==============================================================*/
create table current_project_hold_info
(
   id                   bigint not null auto_increment comment '流水号',
   project_id           bigint comment '项目流水号',
   account_id           bigint comment '投资账户编号',
   principal            decimal(13,2) comment '持有本金',
   apply_redeem_principal decimal(13,2) comment '申请赎回本金',
   interest             decimal(13,4) comment '持有利息',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table current_project_hold_info comment '活期产品持有信息';
drop table if exists current_project_redemption_apply;

/*==============================================================*/
/* Table: current_project_redemption_apply                      */
/*==============================================================*/
create table current_project_redemption_apply
(
   id                   bigint not null auto_increment comment '流水号',
   hold_id              bigint comment '活期产品持有流水号',
   redeem_principal     decimal(13,2) comment '赎回本金',
   redeem_interest      decimal(13,2) comment '赎回利息',
   apply_term           varchar(2) comment '申请终端',
   apply_dt             datetime comment '申请时间',
   status                varchar(2) comment '状态',
   review_dt            datetime comment '审批时间',
   review_user_id       bigint comment '审批人',
   finish_dt            datetime comment '完成时间',
   primary key (id)
);

alter table current_project_redemption_apply comment '活期产品赎回申请';
drop table if exists current_account_interest_change_his;

/*==============================================================*/
/* Table: current_account_interest_change_his                          */
/*==============================================================*/
create table current_account_interest_change_his
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账户编号',
   project_id           bigint comment '项目流水号',
   change_value         decimal(13,4) comment '变更值',
   change_type          varchar(2) comment '变更类型',
   change_reason        varchar(50) comment '变更原因',
   op_term              varchar(2) comment '操作终端',
   op_dt                datetime comment '操作时间',
   third_account_request_no varchar(50) comment '第三方日志流水号',
   primary key (id)
);

alter table current_account_interest_change_his comment '活期账户利息变更历史';
drop table if exists current_account_principal_change_his;

/*==============================================================*/
/* Table: current_account_principal_change_his                         */
/*==============================================================*/
create table current_account_principal_change_his
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账户编号',
   project_id           bigint comment '项目流水号',
   change_value         decimal(13,2) comment '变更值',
   change_type          varchar(2) comment '变更类型',
   change_reason        varchar(50) comment '变更原因',
   status               varchar(2) comment '状态',
   op_term              varchar(2) comment '操作终端',
   op_dt                datetime comment '操作时间',
   third_account_request_no varchar(50) comment '第三方日志流水号',
   primary key (id)
);

alter table current_account_principal_change_his comment '活期账户本金变更历史';
drop table if exists current_project_info;

/*==============================================================*/
/* Table: current_project_info                                  */
/*==============================================================*/
create table current_project_info
(
   id                   bigint not null auto_increment comment '项目流水号',
   code                 varchar(500) comment '项目编号',
   name                 varchar(200) comment '项目名称',
   rate                 decimal(5,3) comment '年化利率',
   finance_money        decimal(13,2) comment '融资金额',
   net_worth            decimal(7,4) comment '单位净值',
   starting_amount      decimal(13,2) comment '起投金额',
   max_amount           decimal(13,2) comment '最大投资额度',
   publish_dt           datetime comment '发布日期',
   end_investment_dt    datetime comment '投标截止时间',
   borrower_account_id  bigint comment '融资人账户编号',
   risk_info            text comment '风控信息',
   about_files          varchar(200) comment '相关文件',
   is_auto_repay        varchar(2) comment '自动还款授权',
   introduce            varchar(2048) comment '项目概述',
   status               varchar(2) comment '项目状态',
   create_dt            datetime comment '创建时间',
   create_user_id       bigint comment '创建人',
   review_dt            datetime comment '审核时间',
   review_remark        varchar(500) comment '审核意见',
   review_user_id       bigint comment '审核人',
   winding_up_status    varchar(2) comment '清盘状态',
   winding_up_apply_dt  datetime comment '清盘申请时间',
   winding_up_apply_reason varchar(2048) comment '清盘申请原因',
   winding_up_apply_user_id bigint comment '清盘申请人',
   winding_up_review_dt datetime comment '清盘审核时间',
   winding_up_review_remark varchar(2048) comment '清盘审核意见',
   winding_up_review_user_id bigint comment '清盘审核人',
   finish_dt            datetime comment '结束时间',
   primary key (id)
);

alter table current_project_info comment '活期项目信息';
drop table if exists current_project_repay_record;

/*==============================================================*/
/* Table: current_project_repay_record                          */
/*==============================================================*/
create table current_project_repay_record
(
   id                   bigint not null auto_increment comment '流水号',
   project_id           bigint comment '项目流水号',
   principal            decimal(13,2) comment '本金',
   interest             decimal(13,4) comment '利息',
   repay_dt             datetime comment '付款日期',
   primary key (id)
);

alter table current_project_repay_record comment '活期产品付款记录';
drop table if exists current_project_parameter;

/*==============================================================*/
/* Table: current_project_parameter                             */
/*==============================================================*/
create table current_project_parameter
(
   id                   bigint not null auto_increment comment '流水号',
   max_investment_money decimal(13,2) comment '最大持有金额',
   investment_fee_value decimal(6,4) comment '购买费用',
   investment_fee_mode  varchar(2) comment '购买计费方式',
   one_day_max_redeem_money decimal(13,2) comment '单日最大赎回金额',
   redeem_fee_value     decimal(6,4) comment '赎回费用',
   redeem_fee_mode      varchar(2) comment '赎回计费方式',
   redeem_separate_time time comment '赎回到账时间分割点',
   create_dt            datetime comment '创建时间',
   create_user_id       bigint comment '创建人',
   modify_dt            datetime comment '修改时间',
   modify_user_id       bigint comment '修改人',
   primary key (id)
);

alter table current_project_parameter comment '活期产品参数';
drop table if exists current_project_review_his;

/*==============================================================*/
/* Table: current_project_review_his                            */
/*==============================================================*/
create table current_project_review_his
(
   id                   bigint not null auto_increment comment '流水号',
   project_id           bigint comment '项目流水号',
   review_dt            datetime comment '审核时间',
   review_remark        varchar(500) comment '审核意见',
   review_user_id       bigint comment '审核人',
   review_result        varchar(2) comment '审核结果',
   primary key (id)
);

alter table current_project_review_his comment '活期产品审批历史';
drop table if exists current_project_winding_up_review_his;

/*==============================================================*/
/* Table: current_project_winding_up_review_his                 */
/*==============================================================*/
create table current_project_winding_up_review_his
(
   id                   bigint not null auto_increment comment '流水号',
   project_id           bigint comment '项目流水号',
   review_dt            datetime comment '审核时间',
   review_remark        varchar(500) comment '审核意见',
   review_user_id       bigint comment '审核人',
   review_result        varchar(2) comment '审核结果',
   primary key (id)
);

alter table current_project_winding_up_review_his comment '活期产品清盘审批历史';
drop table if exists current_project_date_node;

/*==============================================================*/
/* Table: current_project_date_node                             */
/*==============================================================*/
create table current_project_date_node
(
   project_id           bigint comment '项目流水号',
   on_line_dt           datetime comment '上线时间',
   start_funding_dt     datetime comment '开始募资时间',
   end_funding_dt       datetime comment '募资结束时间',
   finish_repay_dt      datetime comment '还款结束时间',
   primary key (project_id)
);

alter table current_project_date_node comment '活期产品时间节点信息';
drop table if exists current_project_execute_snapshot;

/*==============================================================*/
/* Table: current_project_execute_snapshot                      */
/*==============================================================*/
create table current_project_execute_snapshot
(
   id                   bigint not null auto_increment comment '流水号',
   project_id           bigint comment '项目流水号',
   has_financed_money   decimal(13,2) comment '已融资金额',
   real_principal       decimal(13,2) comment '当前实际本金',
   has_repaid_money     decimal(13,4) comment '已产生利息',
   has_redeem_interest  decimal(13,2) comment '已提取利息',
   primary key (id)
);

alter table current_project_execute_snapshot comment '活期项目执行快照';
drop table if exists date_info;

/*==============================================================*/
/* Table: date_info                                             */
/*==============================================================*/
create table date_info
(
   id                   bigint not null auto_increment comment '流水号',
   date                 date comment '日期',
   is_workday           varchar(2) comment '是否工作日',
   primary key (id)
);

alter table date_info comment '日期信息';


delete from sys_dict where id in ('d6d333f7283f40b3846881fd6cb3c37b','fc647b1f7ee146379adbdf47ad64d688','1dade802f31247f8a61fa857e080c143','2a8be77c95e3454a98cac6ae5409cc38','50f39bbb52a34ee4a261b92ce38f4b1b','8f373a9cc42642a7936fcfa1b86782ba','68fd28472f404d45b8ac7502421ad81f','880a800b311e4c428ed12ceea13eea88','716080875ecb4ff79758b548bb36f308','71ee2a2a13fc48aebe516ded7f828931','76c597aee7c3439683f7db58521aafe3','584d5cdbb3924b3a903859c3d6a6495c','f48df3543c954b488dd99276f1378d90','e9ef5b9c7bc14bd98293f138c0701b91','11ba957a51d64c27b7a7e8449530306b','24814e8d87c2468d95b52c2f43d8859e','2ea29ea4838942efb88326853e4e9aa6','83b6365db7b24004b9055040ae6e51aa','baa19bdd3ecd406ea127f3e4c93d773e','deb1033c1d7f4e5083733935fafd0099','280b0c675263482fa93c8cf2e4578cc1','9aea4755b09f408f9919e745fd8284c8','d0d4e65020274c62a02ab746fd69c925','ffac06787dd44336bbc1865b5c823090','0278b91f109e4b00930b397351fcefa8','3d87a764256d49a2abce2806bab3bdb6','b4d148f27ed74ac1bd19063e5c3f7a87','c8ac6abb61b1464d8f521b8b044d5461','4d454566023649119cc4638547c72007','9620ad6f73ff4783ba39caf7a408edbd','c684a31c01754750af46821b5e7f77d8');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d6d333f7283f40b3846881fd6cb3c37b', '0', '正常', 'current_project_hold_status', '活期产品持有状态', '10', '0', '1', '2015-12-09 10:48:18', '1', '2015-12-09 10:48:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('fc647b1f7ee146379adbdf47ad64d688', '1', '已清盘', 'current_project_hold_status', '活期产品持有状态', '30', '0', '1', '2015-12-09 10:49:29', '1', '2015-12-09 10:49:29', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1dade802f31247f8a61fa857e080c143', '0', '申请中', 'current_project_redemption_apply_status', '活期产品赎回申请状态', '10', '0', '1', '2015-12-09 10:55:08', '1', '2015-12-09 10:55:08', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2a8be77c95e3454a98cac6ae5409cc38', '-1', '申请失败', 'current_project_redemption_apply_status', '活期产品赎回申请状态', '5', '0', '1', '2015-12-09 10:56:42', '1', '2015-12-09 10:56:42', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('50f39bbb52a34ee4a261b92ce38f4b1b', '1', '申请通过', 'current_project_redemption_apply_status', '活期产品赎回申请状态', '20', '0', '1', '2015-12-09 10:56:18', '1', '2015-12-09 10:56:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8f373a9cc42642a7936fcfa1b86782ba', '2', '已赎回', 'current_project_redemption_apply_status', '活期产品赎回申请状态', '30', '0', '1', '2015-12-09 10:56:30', '1', '2015-12-09 10:56:30', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('68fd28472f404d45b8ac7502421ad81f', '0', '收息', 'current_account_interest_change_type', '活期账户利息变更类型', '10', '0', '1', '2015-12-09 11:08:03', '1', '2015-12-09 11:08:03', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('880a800b311e4c428ed12ceea13eea88', '1', '赎回', 'current_account_interest_change_type', '活期账户利息变更类型', '20', '0', '1', '2015-12-09 11:08:26', '1', '2015-12-09 11:08:26', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('716080875ecb4ff79758b548bb36f308', '0', '投资', 'current_account_principal_change_type', '活期账户本金变更类型', '10', '0', '1', '2015-12-09 11:23:03', '1', '2015-12-09 11:23:03', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('71ee2a2a13fc48aebe516ded7f828931', '1', '赎回', 'current_account_principal_change_type', '活期账户本金变更类型', '20', '0', '1', '2015-12-09 11:23:18', '1', '2015-12-09 11:23:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('76c597aee7c3439683f7db58521aafe3', '2', '清盘', 'current_account_principal_change_type', '活期账户本金变更类型', '30', '0', '1', '2015-12-09 11:23:35', '1', '2015-12-09 11:23:35', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('584d5cdbb3924b3a903859c3d6a6495c', '0', '不启用', 'current_project_notice_status', '活期产品公告状态', '10', '0', '1', '2015-12-09 11:24:45', '1', '2015-12-09 11:24:45', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f48df3543c954b488dd99276f1378d90', '1', '启用', 'current_project_notice_status', '活期产品公告状态', '20', '0', '1', '2015-12-09 11:24:54', '1', '2015-12-09 11:24:54', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e9ef5b9c7bc14bd98293f138c0701b91', '-1', '取消', 'current_project_status', '活期项目状态', '5', '0', '1', '2015-12-09 11:47:14', '1', '2015-12-09 11:47:14', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('11ba957a51d64c27b7a7e8449530306b', '0', '创建', 'current_project_status', '活期项目状态', '10', '0', '1', '2015-12-09 11:26:41', '1', '2015-12-09 11:26:41', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('24814e8d87c2468d95b52c2f43d8859e', '3', '投标中', 'current_project_status', '活期项目状态', '40', '0', '1', '2015-12-09 11:29:51', '1', '2015-12-09 11:29:51', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2ea29ea4838942efb88326853e4e9aa6', '1', '提交审批', 'current_project_status', '活期项目状态', '20', '0', '1', '2015-12-09 11:27:13', '1', '2015-12-09 11:27:13', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('83b6365db7b24004b9055040ae6e51aa', '4', '投标结束', 'current_project_status', '活期项目状态', '50', '0', '1', '2015-12-09 11:30:12', '1', '2015-12-09 11:30:12', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('baa19bdd3ecd406ea127f3e4c93d773e', '5', '已清盘', 'current_project_status', '活期项目状态', '60', '0', '1', '2015-12-09 11:30:46', '1', '2015-12-09 11:30:46', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('deb1033c1d7f4e5083733935fafd0099', '2', '审批通过', 'current_project_status', '活期项目状态', '30', '0', '1', '2015-12-09 11:27:46', '1', '2015-12-09 11:27:46', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('280b0c675263482fa93c8cf2e4578cc1', '2', '审批通过', 'current_project_winding_up_status', '活期项目清盘状态', '30', '0', '1', '2015-12-09 11:35:41', '1', '2015-12-09 11:35:41', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9aea4755b09f408f9919e745fd8284c8', '0', '未申请', 'current_project_winding_up_status', '活期项目清盘状态', '10', '0', '1', '2015-12-09 11:34:56', '1', '2015-12-09 11:34:56', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d0d4e65020274c62a02ab746fd69c925', '3', '完成清盘', 'current_project_winding_up_status', '活期项目清盘状态', '40', '0', '1', '2015-12-09 11:36:03', '1', '2015-12-09 11:36:03', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ffac06787dd44336bbc1865b5c823090', '1', '提交审批', 'current_project_winding_up_status', '活期项目清盘状态', '20', '0', '1', '2015-12-09 11:35:31', '1', '2015-12-09 11:35:48', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0278b91f109e4b00930b397351fcefa8', '1', '通过', 'current_project_review_result', '活期产品审批结果', '20', '0', '1', '2015-12-09 11:39:11', '1', '2015-12-09 11:39:11', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3d87a764256d49a2abce2806bab3bdb6', '0', '不通过', 'current_project_review_result', '活期产品审批结果', '10', '0', '1', '2015-12-09 11:39:05', '1', '2015-12-09 11:39:05', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b4d148f27ed74ac1bd19063e5c3f7a87', '0', '否', 'is_workday', '是否是工作日', '10', '0', '1', '2015-12-09 11:40:09', '1', '2015-12-09 11:40:09', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c8ac6abb61b1464d8f521b8b044d5461', '1', '是', 'is_workday', '是否是工作日', '20', '0', '1', '2015-12-09 11:40:15', '1', '2015-12-09 11:40:15', '', '0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4d454566023649119cc4638547c72007', '-1', '已撤销', 'current_account_principal_change_status', '活期账户本金变更状态', '5', '0', '1', '2015-12-09 15:31:13', '1', '2015-12-09 15:31:13', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9620ad6f73ff4783ba39caf7a408edbd', '0', '正常', 'current_account_principal_change_status', '活期账户本金变更状态', '10', '0', '1', '2015-12-09 15:30:30', '1', '2015-12-09 15:30:30', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c684a31c01754750af46821b5e7f77d8', '1', '冻结中', 'current_account_principal_change_status', '活期账户本金变更状态', '20', '0', '1', '2015-12-09 15:30:56', '1', '2015-12-09 15:30:56', '', '0');

delete from sys_dict where id in ('47eafb19b33c4339bbcef7fd48e1f922','b21111220fd04dd38f66fcc379211a62','2c1b992415d94233bfe53d9250fa2755');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('47eafb19b33c4339bbcef7fd48e1f922', '10', '提取活期产品利息', 'customer_balance_change_type_dict', '变更类型', '160', '0', '1', '2015-12-14 20:11:40', '1', '2015-12-14 20:11:40', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b21111220fd04dd38f66fcc379211a62', '9', '赎回活期产品本金', 'customer_balance_change_type_dict', '变更类型', '150', '0', '1', '2015-12-14 20:11:30', '1', '2015-12-14 20:11:30', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2c1b992415d94233bfe53d9250fa2755', '11', '活期产品清盘', 'customer_balance_change_type_dict', '变更类型', '170', '0', '1', '2015-12-23 15:02:01', '1', '2015-12-23 15:02:01', '', '0');

delete from sys_dict where id = '26a9106fd51044e4bf7085059c773c37';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('26a9106fd51044e4bf7085059c773c37', '2', '清盘', 'current_account_interest_change_type', '活期账户利息变更类型', '30', '0', '1', '2015-12-16 10:46:02', '1', '2015-12-16 10:46:02', '', '0');



/*==============================================================*/
/* 增加: 活期产品管理菜单项                                                                                                      */
/*==============================================================*/
delete from sys_menu where id in (
'063aa90faef945a99194a10776b6eab0',
'1343daccff5046708ff6fedcac20a576',
'1bbbc13018bd488ea6d0a338892ef1b5',
'2105258d1ca84ae09c8d130e87fe31ae',
'2f1ada72b2fe4d9188f78b4060579f9d',
'34086864cae547598aa7b9261c99b1ab',
'50f18c724ad34e9ab427c10d67ad2eef',
'53d8536b74804c8581fd69d0f9e17995',
'5f424fe3291843fba3e9b8d374ec1b84',
'6a8e4ce32f1449ab9b5198b83fe58b23',
'ae8b07f00c4348c98d4922f0dd08d223',
'b9ec9632968c42e19ff5e92cafe13788',
'd40cbfd7afab4c18b796dae84ed8ea38',
'd46f7e293fd948cb8279a9bb8e6d4a1d',
'e50cc2f9b2b14da5a3f3ef38f9b6e431',
'e9557ccaefec4b8a990346775f343556',
'f9a3e1b95a9e44dfab9f8ac0c51e50d6'
);
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('063aa90faef945a99194a10776b6eab0', 'b9ec9632968c42e19ff5e92cafe13788', '0,1,b9ec9632968c42e19ff5e92cafe13788,', '活期产品管理', '30', '', '', '', '1', '', '1', '2015-12-09 16:49:55', '1', '2015-12-09 16:49:55', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1343daccff5046708ff6fedcac20a576', '063aa90faef945a99194a10776b6eab0', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,', '产品清盘', '90', '/current/currentProjectInfo/clearList', '', 'icon-coffee', '1', 'current:currentProjectInfo:clear', '1', '2015-12-11 10:06:56', '1', '2015-12-11 11:31:08', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1bbbc13018bd488ea6d0a338892ef1b5', '063aa90faef945a99194a10776b6eab0', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,', '产品列表', '20', '/current/currentProjectInfo/list', '', 'icon-eye-open', '1', 'current:currentProjectInfo:view', '1', '2015-12-11 13:33:30', '1', '2015-12-11 13:33:30', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2105258d1ca84ae09c8d130e87fe31ae', 'e50cc2f9b2b14da5a3f3ef38f9b6e431', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,e50cc2f9b2b14da5a3f3ef38f9b6e431,', '查看利息流水', '90', '', '', '', '0', 'current:currentAccountInterestChangeHis:view', '1', '2015-12-14 17:19:19', '1', '2015-12-14 17:19:33', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('2f1ada72b2fe4d9188f78b4060579f9d', 'e50cc2f9b2b14da5a3f3ef38f9b6e431', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,e50cc2f9b2b14da5a3f3ef38f9b6e431,', '查看赎回流水', '120', '', '', '', '0', 'current:currentProjectRedemptionApply:view', '1', '2015-12-16 13:07:22', '1', '2015-12-16 13:07:22', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('34086864cae547598aa7b9261c99b1ab', '1bbbc13018bd488ea6d0a338892ef1b5', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,', '产品-还款授权', '60', '', '', 'icon-certificate', '0', 'current:currentProjectInfo:authorize', '1', '2015-12-15 11:40:46', '1', '2015-12-15 11:40:46', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('50f18c724ad34e9ab427c10d67ad2eef', '063aa90faef945a99194a10776b6eab0', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,', '产品审批', '60', '/current/currentProjectInfo/reviewList', '', 'icon-circle', '1', 'current:currentProjectInfo:review', '1', '2015-12-10 15:48:44', '1', '2015-12-11 13:19:30', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('53d8536b74804c8581fd69d0f9e17995', 'e50cc2f9b2b14da5a3f3ef38f9b6e431', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,e50cc2f9b2b14da5a3f3ef38f9b6e431,', '查看投资流水', '60', '', '', '', '0', 'current:currentAccountPrincipalChangeHis:view', '1', '2015-12-14 11:34:25', '1', '2015-12-14 11:34:25', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5f424fe3291843fba3e9b8d374ec1b84', 'e50cc2f9b2b14da5a3f3ef38f9b6e431', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,e50cc2f9b2b14da5a3f3ef38f9b6e431,', '查看持有明细', '150', '', '', '', '1', 'current:currentProjectHoldInfo:view', '1', '2015-12-16 13:15:10', '1', '2015-12-16 13:15:10', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('6a8e4ce32f1449ab9b5198b83fe58b23', 'f9a3e1b95a9e44dfab9f8ac0c51e50d6', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,f9a3e1b95a9e44dfab9f8ac0c51e50d6,', '查看', '30', '', '', '', '0', 'current:currentProjectNotice:view', '1', '2015-12-14 17:37:36', '1', '2015-12-14 17:37:50', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ae8b07f00c4348c98d4922f0dd08d223', 'f9a3e1b95a9e44dfab9f8ac0c51e50d6', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,f9a3e1b95a9e44dfab9f8ac0c51e50d6,', '修改', '60', '', '', '', '0', 'current:currentProjectNotice:edit', '1', '2015-12-14 17:38:11', '1', '2015-12-14 17:38:11', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b9ec9632968c42e19ff5e92cafe13788', '1', '0,1,', '活期产品管理', '5090', '', '', '', '1', '', '1', '2015-12-09 16:49:38', '1', '2015-12-09 16:49:38', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d40cbfd7afab4c18b796dae84ed8ea38', '063aa90faef945a99194a10776b6eab0', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,', '清盘审批', '120', '/current/currentProjectInfo/clearReviewList', '', 'icon-credit-card', '1', 'current:currentProjectInfo:clearReview', '1', '2015-12-11 11:30:14', '1', '2015-12-11 11:36:15', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d46f7e293fd948cb8279a9bb8e6d4a1d', '063aa90faef945a99194a10776b6eab0', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,', '产品创建', '30', '/current/currentProjectInfo/createList', '', 'icon-credit-card', '1', 'current:currentProjectInfo:create', '1', '2015-12-09 16:50:23', '1', '2015-12-11 13:23:30', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e50cc2f9b2b14da5a3f3ef38f9b6e431', '1bbbc13018bd488ea6d0a338892ef1b5', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,', '项目详情', '30', '', '', '', '0', '', '1', '2015-12-14 10:32:36', '1', '2015-12-14 10:33:02', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e9557ccaefec4b8a990346775f343556', 'e50cc2f9b2b14da5a3f3ef38f9b6e431', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,e50cc2f9b2b14da5a3f3ef38f9b6e431,', '查看投资快照', '30', '', '', '', '0', 'current:currentProjectExecuteSnapshot:view', '1', '2015-12-14 10:33:39', '1', '2015-12-14 10:33:39', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f9a3e1b95a9e44dfab9f8ac0c51e50d6', '063aa90faef945a99194a10776b6eab0', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,', '产品公告', '150', '/current/currentProjectNotice/list', '', 'icon-reorder', '1', '', '1', '2015-12-14 17:37:00', '1', '2015-12-14 17:42:01', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '063aa90faef945a99194a10776b6eab0');
INSERT INTO `sys_role_menu` VALUES ('1', '1343daccff5046708ff6fedcac20a576');
INSERT INTO `sys_role_menu` VALUES ('1', '1bbbc13018bd488ea6d0a338892ef1b5');
INSERT INTO `sys_role_menu` VALUES ('1', '2105258d1ca84ae09c8d130e87fe31ae');
INSERT INTO `sys_role_menu` VALUES ('1', '2f1ada72b2fe4d9188f78b4060579f9d');
INSERT INTO `sys_role_menu` VALUES ('1', '34086864cae547598aa7b9261c99b1ab');
INSERT INTO `sys_role_menu` VALUES ('1', '50f18c724ad34e9ab427c10d67ad2eef');
INSERT INTO `sys_role_menu` VALUES ('1', '53d8536b74804c8581fd69d0f9e17995');
INSERT INTO `sys_role_menu` VALUES ('1', '5f424fe3291843fba3e9b8d374ec1b84');
INSERT INTO `sys_role_menu` VALUES ('1', '6a8e4ce32f1449ab9b5198b83fe58b23');
INSERT INTO `sys_role_menu` VALUES ('1', 'ae8b07f00c4348c98d4922f0dd08d223');
INSERT INTO `sys_role_menu` VALUES ('1', 'b9ec9632968c42e19ff5e92cafe13788');
INSERT INTO `sys_role_menu` VALUES ('1', 'd40cbfd7afab4c18b796dae84ed8ea38');
INSERT INTO `sys_role_menu` VALUES ('1', 'd46f7e293fd948cb8279a9bb8e6d4a1d');
INSERT INTO `sys_role_menu` VALUES ('1', 'e50cc2f9b2b14da5a3f3ef38f9b6e431');
INSERT INTO `sys_role_menu` VALUES ('1', 'e9557ccaefec4b8a990346775f343556');
INSERT INTO `sys_role_menu` VALUES ('1', 'f9a3e1b95a9e44dfab9f8ac0c51e50d6');


/*==============================================================*/
/* 增加: 产品-还款授权隐藏菜单                                                                                                     */
/*==============================================================*/
delete from sys_menu where id ='34086864cae547598aa7b9261c99b1ab';
INSERT INTO `sys_menu` VALUES ('34086864cae547598aa7b9261c99b1ab', '1bbbc13018bd488ea6d0a338892ef1b5', '0,1,b9ec9632968c42e19ff5e92cafe13788,063aa90faef945a99194a10776b6eab0,1bbbc13018bd488ea6d0a338892ef1b5,', '产品-还款授权', 60, '', '', 'icon-certificate', '0', 'current:currentProjectInfo:authorize', '1', '2015-12-15 11:40:46', '1', '2015-12-15 11:40:46', '', '0');
delete from sys_role_menu where menu_id ='34086864cae547598aa7b9261c99b1ab';
INSERT INTO `sys_role_menu` VALUES ('1', '34086864cae547598aa7b9261c99b1ab');

/*==============================================================*/
/* 修改: 积分商城-产品规格参数 ，参数值字段长度                                                                                             */
/*==============================================================*/
ALTER TABLE `integral_mall_product_sps`
MODIFY COLUMN `para_val`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数值' AFTER `para_name`;

/*==============================================================*/
/* 修改: 移动端版本参数 ，增加是否强制更新字段                                                                                            */
/*==============================================================*/
ALTER TABLE `sys_mobile_version_para`
ADD COLUMN `is_forced_update`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否强制更新' AFTER `channel`;

/*==============================================================*/
/* 增加: 移动端活动数据                                                                                           					*/
/*==============================================================*/
delete from cms_activity where id in ('24','25','27');
INSERT INTO `cms_activity` VALUES (24, '安全保障活动(移动版)', 'http://m.hsbank360.com/#/more/safe/?_hideNavbar=1&amp;_hideNavbarBottom=1', '/fd/userfiles/1/images/content/activity/2015/12/banner_2_2.png', '1', '0', '2015-9-1 10:07:18', '2020-12-31 10:07:28', '1', '2015-12-16 10:07:00', 1, '2015-12-16 10:07:21', '安全保障', 230, ',1,2,3,');
INSERT INTO `cms_activity` VALUES (25, '投资排行活动(移动版)', 'http://fakeapi.fdjf.net/#/event/top?_hideNavbar=1&amp;_hideNavbarBottom=1&amp;t1=t1', '/fd/userfiles/1/images/content/activity/2015/12/app-banner.png', '1', '0', '2015-12-1 10:11:04', '2015-12-31 10:11:07', '1', '2015-12-16 10:10:45', 1, '2015-12-16 10:13:42', '投资排行', 270, ',1,2,3,');
INSERT INTO `cms_activity` VALUES (27, '邀请好友(移动版)', 'http://m.hsbank360.com/#/more/safe/?_hideNavbar=1&amp;_hideNavbarBottom=1', '/fuding_p2p/userfiles/1/images/content/activity/2015/12/banner_2.png', '1', '0', '2015-11-1 13:36:08', '2020-12-31 13:36:11', '1', '2015-12-22 13:36:58', 1, '2015-12-22 16:04:27', '邀请好友', 250, ',1,2,3,');


/*==============================================================*/
/* 增加: 会员总览权限                                                                                                                     */
/*==============================================================*/
delete from sys_menu where id in (
'3f89c42b98a649e482317110aa00a9d5',
'4faf42601f2b47bb8c18f831ed84417f',
'70906a531b6e41daa688bc2251852627',
'8222f0104e8f4a62a24207e8f34cfc50',
'd389cc8d7cbe455c811f461a67fe0d5e',
'e0c05c5bcba3431cb7087181f0b2c19f'
);
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3f89c42b98a649e482317110aa00a9d5', 'd389cc8d7cbe455c811f461a67fe0d5e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d389cc8d7cbe455c811f461a67fe0d5e,', '查看活期账户总览', '30', '', '', '', '0', 'current:currentAccountSummary:view', '1', '2015-12-16 09:51:06', '1', '2015-12-16 16:10:00', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4faf42601f2b47bb8c18f831ed84417f', 'd389cc8d7cbe455c811f461a67fe0d5e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d389cc8d7cbe455c811f461a67fe0d5e,', '查看赎回流水', '150', '', '', '', '0', 'current:currentProjectRedemptionApply:view', '1', '2015-12-16 15:45:51', '1', '2015-12-16 16:10:58', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('70906a531b6e41daa688bc2251852627', 'd389cc8d7cbe455c811f461a67fe0d5e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d389cc8d7cbe455c811f461a67fe0d5e,', '查看利息流水', '120', '', '', '', '0', 'current:currentAccountInterestChangeHis:view', '1', '2015-12-16 15:44:58', '1', '2015-12-16 16:10:43', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8222f0104e8f4a62a24207e8f34cfc50', 'd389cc8d7cbe455c811f461a67fe0d5e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d389cc8d7cbe455c811f461a67fe0d5e,', '查看投资流水', '90', '', '', '', '0', 'current:currentAccountPrincipalChangeHis:view', '1', '2015-12-16 15:43:58', '1', '2015-12-16 16:10:31', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d389cc8d7cbe455c811f461a67fe0d5e', 'a8c72721812b476ca0e5ff78e924d1a2', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,', '会员总览', '180', '', '', '', '0', '', '1', '2015-12-16 16:09:08', '1', '2015-12-16 16:09:08', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e0c05c5bcba3431cb7087181f0b2c19f', 'd389cc8d7cbe455c811f461a67fe0d5e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,a8c72721812b476ca0e5ff78e924d1a2,d389cc8d7cbe455c811f461a67fe0d5e,', '查看持有明细', '60', '', '', '', '0', 'current:currentProjectHoldInfo:view', '1', '2015-12-16 15:43:02', '1', '2015-12-16 16:10:17', '', '0');
INSERT INTO `sys_role_menu` VALUES ('1', '3f89c42b98a649e482317110aa00a9d5');
INSERT INTO `sys_role_menu` VALUES ('1', '4faf42601f2b47bb8c18f831ed84417f');
INSERT INTO `sys_role_menu` VALUES ('1', '70906a531b6e41daa688bc2251852627');
INSERT INTO `sys_role_menu` VALUES ('1', '8222f0104e8f4a62a24207e8f34cfc50');
INSERT INTO `sys_role_menu` VALUES ('1', 'd389cc8d7cbe455c811f461a67fe0d5e');
INSERT INTO `sys_role_menu` VALUES ('1', 'e0c05c5bcba3431cb7087181f0b2c19f');



/*==============================================================*/
/* 增加: 赎回审批管理菜单项                                                                                     */
/*==============================================================*/
delete from sys_menu where id='fdf2af9432344e85911a99a54d4a5202';
delete from sys_menu where id='49e4c4522ffb42be911fd342a9dc30a7';
delete from sys_menu where id='0e041e74d84a4291821bee9a27d06258';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('49e4c4522ffb42be911fd342a9dc30a7', 'b9ec9632968c42e19ff5e92cafe13788', '0,1,b9ec9632968c42e19ff5e92cafe13788,', '赎回审批管理', '90', '', '', '', '1', '', '1', '2015-12-11 15:58:26', '1', '2015-12-11 15:58:26', '', '1');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('fdf2af9432344e85911a99a54d4a5202', 'b9ec9632968c42e19ff5e92cafe13788', '0,1,b9ec9632968c42e19ff5e92cafe13788,', '赎回审批管理', '60', '', '', '', '1', '', '1', '2015-12-11 14:57:43', '1', '2015-12-15 17:27:56', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0e041e74d84a4291821bee9a27d06258', 'fdf2af9432344e85911a99a54d4a5202', '0,1,b9ec9632968c42e19ff5e92cafe13788,fdf2af9432344e85911a99a54d4a5202,', '赎回审批', '90', '/current/currentProjectRedemptionApply/reviewList', '', '', '1', 'current:currentProjectRedemptionApply:review', '1', '2015-12-11 15:15:50', '1', '2015-12-16 15:13:25', '', '0');
delete from sys_role_menu where menu_id='49e4c4522ffb42be911fd342a9dc30a7';
delete from sys_role_menu where menu_id='fdf2af9432344e85911a99a54d4a5202';
delete from sys_role_menu where menu_id='0e041e74d84a4291821bee9a27d06258';
INSERT INTO `sys_role_menu` VALUES ('1', '49e4c4522ffb42be911fd342a9dc30a7');
INSERT INTO `sys_role_menu` VALUES ('1', 'fdf2af9432344e85911a99a54d4a5202');
INSERT INTO `sys_role_menu` VALUES ('1', '0e041e74d84a4291821bee9a27d06258');

/*==============================================================*/
/* 增加: 工作日管理菜单项                                                                                     */
/*==============================================================*/
delete from sys_menu where id='8774597685004ee1ba8c59c7232730ca';
delete from sys_menu where id='d1edd01e90bb4a3daf66d461330373bb';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('8774597685004ee1ba8c59c7232730ca', 'd1edd01e90bb4a3daf66d461330373bb', '0,1,31,d1edd01e90bb4a3daf66d461330373bb,', '工作日管理', '30', '/current/dateInfo', '', '', '1', 'current:dateInfo:view,current:dateInfo:edit', '1', '2015-12-09 16:44:39', '1', '2015-12-11 11:23:18', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d1edd01e90bb4a3daf66d461330373bb', '31', '0,1,31,', '工作日管理', '1050', '', '', '', '1', '', '1', '2015-12-09 16:42:56', '1', '2015-12-09 16:42:56', '', '0');
delete from sys_role_menu where menu_id='8774597685004ee1ba8c59c7232730ca';
delete from sys_role_menu where menu_id='d1edd01e90bb4a3daf66d461330373bb';
INSERT INTO `sys_role_menu` VALUES ('1', '8774597685004ee1ba8c59c7232730ca');
INSERT INTO `sys_role_menu` VALUES ('1', 'd1edd01e90bb4a3daf66d461330373bb');

/*==============================================================*/
/* 增加:在系统设置中增加工作日管理菜单项                                                                                     */
/*==============================================================*/
delete from sys_menu where id='484918f43a1b493e835b5f31b1f208c5';
delete from sys_menu where id='ab12965c18b34a7b85189461a7b3d4c5';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('484918f43a1b493e835b5f31b1f208c5', '3', '0,1,2,3,', '工作日管理', '240', '/current/dateInfo', '', 'icon-time', '1', '', '1', '2015-12-17 15:27:14', '1', '2015-12-17 15:31:49', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ab12965c18b34a7b85189461a7b3d4c5', '484918f43a1b493e835b5f31b1f208c5', '0,1,2,3,484918f43a1b493e835b5f31b1f208c5,', '查看', '30', '', '', '', '0', 'current:dateInfo:view,current:dateInfo:edit', '1', '2015-12-17 15:31:30', '1', '2015-12-17 15:32:05', '', '0');
delete from sys_role_menu where menu_id='484918f43a1b493e835b5f31b1f208c5';
delete from sys_role_menu where menu_id='ab12965c18b34a7b85189461a7b3d4c5';
INSERT INTO `sys_role_menu` VALUES ('1', '484918f43a1b493e835b5f31b1f208c5');
INSERT INTO `sys_role_menu` VALUES ('1', 'ab12965c18b34a7b85189461a7b3d4c5');

/*==============================================================*/
/* 增加:在活期产品管理中增加赎回信息菜单项                                                                                 */
/*==============================================================*/
delete from sys_menu where id='dcb55cd6ac2246419deecffcf2e6a470';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('dcb55cd6ac2246419deecffcf2e6a470', 'fdf2af9432344e85911a99a54d4a5202', '0,1,b9ec9632968c42e19ff5e92cafe13788,fdf2af9432344e85911a99a54d4a5202,', '赎回信息', '120', '/current/redemptionInfo/redemptionInfoList', '', 'icon-file-alt', '1', 'current:redemptionInfo:view', '1', '2015-12-17 17:15:11', '1', '2015-12-22 18:35:44', '', '0');
delete from sys_role_menu where menu_id='dcb55cd6ac2246419deecffcf2e6a470';
INSERT INTO `sys_role_menu` VALUES ('1', 'dcb55cd6ac2246419deecffcf2e6a470');


-- ----------------------------
-- Records of date_info(初始化日期数据)
-- ----------------------------
delete from date_info;
INSERT INTO `date_info` VALUES ('7997', '2015-12-01', '1');
INSERT INTO `date_info` VALUES ('7998', '2015-12-02', '1');
INSERT INTO `date_info` VALUES ('7999', '2015-12-03', '1');
INSERT INTO `date_info` VALUES ('8000', '2015-12-04', '1');
INSERT INTO `date_info` VALUES ('8001', '2015-12-05', '0');
INSERT INTO `date_info` VALUES ('8002', '2015-12-06', '0');
INSERT INTO `date_info` VALUES ('8003', '2015-12-07', '1');
INSERT INTO `date_info` VALUES ('8004', '2015-12-08', '1');
INSERT INTO `date_info` VALUES ('8005', '2015-12-09', '1');
INSERT INTO `date_info` VALUES ('8006', '2015-12-10', '1');
INSERT INTO `date_info` VALUES ('8007', '2015-12-11', '1');
INSERT INTO `date_info` VALUES ('8008', '2015-12-12', '0');
INSERT INTO `date_info` VALUES ('8009', '2015-12-13', '0');
INSERT INTO `date_info` VALUES ('8010', '2015-12-14', '1');
INSERT INTO `date_info` VALUES ('8011', '2015-12-15', '1');
INSERT INTO `date_info` VALUES ('8012', '2015-12-16', '1');
INSERT INTO `date_info` VALUES ('8013', '2015-12-17', '1');
INSERT INTO `date_info` VALUES ('8014', '2015-12-18', '1');
INSERT INTO `date_info` VALUES ('8015', '2015-12-19', '0');
INSERT INTO `date_info` VALUES ('8016', '2015-12-20', '0');
INSERT INTO `date_info` VALUES ('8017', '2015-12-21', '1');
INSERT INTO `date_info` VALUES ('8018', '2015-12-22', '1');
INSERT INTO `date_info` VALUES ('8019', '2015-12-23', '1');
INSERT INTO `date_info` VALUES ('8020', '2015-12-24', '1');
INSERT INTO `date_info` VALUES ('8021', '2015-12-25', '1');
INSERT INTO `date_info` VALUES ('8022', '2015-12-26', '0');
INSERT INTO `date_info` VALUES ('8023', '2015-12-27', '0');
INSERT INTO `date_info` VALUES ('8024', '2015-12-28', '1');
INSERT INTO `date_info` VALUES ('8025', '2015-12-29', '1');
INSERT INTO `date_info` VALUES ('8026', '2015-12-30', '1');
INSERT INTO `date_info` VALUES ('8027', '2015-12-31', '1');
INSERT INTO `date_info` VALUES ('8028', '2016-01-01', '1');
INSERT INTO `date_info` VALUES ('8029', '2016-01-02', '0');
INSERT INTO `date_info` VALUES ('8030', '2016-01-03', '0');
INSERT INTO `date_info` VALUES ('8031', '2016-01-04', '1');
INSERT INTO `date_info` VALUES ('8032', '2016-01-05', '1');
INSERT INTO `date_info` VALUES ('8033', '2016-01-06', '1');
INSERT INTO `date_info` VALUES ('8034', '2016-01-07', '1');
INSERT INTO `date_info` VALUES ('8035', '2016-01-08', '1');
INSERT INTO `date_info` VALUES ('8036', '2016-01-09', '0');
INSERT INTO `date_info` VALUES ('8037', '2016-01-10', '0');
INSERT INTO `date_info` VALUES ('8038', '2016-01-11', '1');
INSERT INTO `date_info` VALUES ('8039', '2016-01-12', '1');
INSERT INTO `date_info` VALUES ('8040', '2016-01-13', '1');
INSERT INTO `date_info` VALUES ('8041', '2016-01-14', '1');
INSERT INTO `date_info` VALUES ('8042', '2016-01-15', '1');
INSERT INTO `date_info` VALUES ('8043', '2016-01-16', '0');
INSERT INTO `date_info` VALUES ('8044', '2016-01-17', '0');
INSERT INTO `date_info` VALUES ('8045', '2016-01-18', '1');
INSERT INTO `date_info` VALUES ('8046', '2016-01-19', '1');
INSERT INTO `date_info` VALUES ('8047', '2016-01-20', '1');
INSERT INTO `date_info` VALUES ('8048', '2016-01-21', '1');
INSERT INTO `date_info` VALUES ('8049', '2016-01-22', '1');
INSERT INTO `date_info` VALUES ('8050', '2016-01-23', '0');
INSERT INTO `date_info` VALUES ('8051', '2016-01-24', '0');
INSERT INTO `date_info` VALUES ('8052', '2016-01-25', '1');
INSERT INTO `date_info` VALUES ('8053', '2016-01-26', '1');
INSERT INTO `date_info` VALUES ('8054', '2016-01-27', '1');
INSERT INTO `date_info` VALUES ('8055', '2016-01-28', '1');
INSERT INTO `date_info` VALUES ('8056', '2016-01-29', '1');
INSERT INTO `date_info` VALUES ('8057', '2016-01-30', '0');
INSERT INTO `date_info` VALUES ('8058', '2016-01-31', '0');
INSERT INTO `date_info` VALUES ('8059', '2016-02-01', '1');
INSERT INTO `date_info` VALUES ('8060', '2016-02-02', '1');
INSERT INTO `date_info` VALUES ('8061', '2016-02-03', '1');
INSERT INTO `date_info` VALUES ('8062', '2016-02-04', '1');
INSERT INTO `date_info` VALUES ('8063', '2016-02-05', '1');
INSERT INTO `date_info` VALUES ('8064', '2016-02-06', '0');
INSERT INTO `date_info` VALUES ('8065', '2016-02-07', '0');
INSERT INTO `date_info` VALUES ('8066', '2016-02-08', '1');
INSERT INTO `date_info` VALUES ('8067', '2016-02-09', '1');
INSERT INTO `date_info` VALUES ('8068', '2016-02-10', '1');
INSERT INTO `date_info` VALUES ('8069', '2016-02-11', '1');
INSERT INTO `date_info` VALUES ('8070', '2016-02-12', '1');
INSERT INTO `date_info` VALUES ('8071', '2016-02-13', '0');
INSERT INTO `date_info` VALUES ('8072', '2016-02-14', '0');
INSERT INTO `date_info` VALUES ('8073', '2016-02-15', '1');
INSERT INTO `date_info` VALUES ('8074', '2016-02-16', '1');
INSERT INTO `date_info` VALUES ('8075', '2016-02-17', '1');
INSERT INTO `date_info` VALUES ('8076', '2016-02-18', '1');
INSERT INTO `date_info` VALUES ('8077', '2016-02-19', '1');
INSERT INTO `date_info` VALUES ('8078', '2016-02-20', '0');
INSERT INTO `date_info` VALUES ('8079', '2016-02-21', '0');
INSERT INTO `date_info` VALUES ('8080', '2016-02-22', '1');
INSERT INTO `date_info` VALUES ('8081', '2016-02-23', '1');
INSERT INTO `date_info` VALUES ('8082', '2016-02-24', '1');
INSERT INTO `date_info` VALUES ('8083', '2016-02-25', '1');
INSERT INTO `date_info` VALUES ('8084', '2016-02-26', '1');
INSERT INTO `date_info` VALUES ('8085', '2016-02-27', '0');
INSERT INTO `date_info` VALUES ('8086', '2016-02-28', '0');
INSERT INTO `date_info` VALUES ('8087', '2016-02-29', '1');
INSERT INTO `date_info` VALUES ('8088', '2016-03-01', '1');
INSERT INTO `date_info` VALUES ('8089', '2016-03-02', '1');
INSERT INTO `date_info` VALUES ('8090', '2016-03-03', '1');
INSERT INTO `date_info` VALUES ('8091', '2016-03-04', '1');
INSERT INTO `date_info` VALUES ('8092', '2016-03-05', '0');
INSERT INTO `date_info` VALUES ('8093', '2016-03-06', '0');
INSERT INTO `date_info` VALUES ('8094', '2016-03-07', '1');
INSERT INTO `date_info` VALUES ('8095', '2016-03-08', '1');
INSERT INTO `date_info` VALUES ('8096', '2016-03-09', '1');
INSERT INTO `date_info` VALUES ('8097', '2016-03-10', '1');
INSERT INTO `date_info` VALUES ('8098', '2016-03-11', '1');
INSERT INTO `date_info` VALUES ('8099', '2016-03-12', '0');
INSERT INTO `date_info` VALUES ('8100', '2016-03-13', '0');
INSERT INTO `date_info` VALUES ('8101', '2016-03-14', '1');
INSERT INTO `date_info` VALUES ('8102', '2016-03-15', '1');
INSERT INTO `date_info` VALUES ('8103', '2016-03-16', '1');
INSERT INTO `date_info` VALUES ('8104', '2016-03-17', '1');
INSERT INTO `date_info` VALUES ('8105', '2016-03-18', '1');
INSERT INTO `date_info` VALUES ('8106', '2016-03-19', '0');
INSERT INTO `date_info` VALUES ('8107', '2016-03-20', '0');
INSERT INTO `date_info` VALUES ('8108', '2016-03-21', '1');
INSERT INTO `date_info` VALUES ('8109', '2016-03-22', '1');
INSERT INTO `date_info` VALUES ('8110', '2016-03-23', '1');
INSERT INTO `date_info` VALUES ('8111', '2016-03-24', '1');
INSERT INTO `date_info` VALUES ('8112', '2016-03-25', '1');
INSERT INTO `date_info` VALUES ('8113', '2016-03-26', '0');
INSERT INTO `date_info` VALUES ('8114', '2016-03-27', '0');
INSERT INTO `date_info` VALUES ('8115', '2016-03-28', '1');
INSERT INTO `date_info` VALUES ('8116', '2016-03-29', '1');
INSERT INTO `date_info` VALUES ('8117', '2016-03-30', '1');
INSERT INTO `date_info` VALUES ('8118', '2016-03-31', '1');
INSERT INTO `date_info` VALUES ('8119', '2016-04-01', '1');
INSERT INTO `date_info` VALUES ('8120', '2016-04-02', '0');
INSERT INTO `date_info` VALUES ('8121', '2016-04-03', '0');
INSERT INTO `date_info` VALUES ('8122', '2016-04-04', '1');
INSERT INTO `date_info` VALUES ('8123', '2016-04-05', '1');
INSERT INTO `date_info` VALUES ('8124', '2016-04-06', '1');
INSERT INTO `date_info` VALUES ('8125', '2016-04-07', '1');
INSERT INTO `date_info` VALUES ('8126', '2016-04-08', '1');
INSERT INTO `date_info` VALUES ('8127', '2016-04-09', '0');
INSERT INTO `date_info` VALUES ('8128', '2016-04-10', '0');
INSERT INTO `date_info` VALUES ('8129', '2016-04-11', '1');
INSERT INTO `date_info` VALUES ('8130', '2016-04-12', '1');
INSERT INTO `date_info` VALUES ('8131', '2016-04-13', '1');
INSERT INTO `date_info` VALUES ('8132', '2016-04-14', '1');
INSERT INTO `date_info` VALUES ('8133', '2016-04-15', '1');
INSERT INTO `date_info` VALUES ('8134', '2016-04-16', '0');
INSERT INTO `date_info` VALUES ('8135', '2016-04-17', '0');
INSERT INTO `date_info` VALUES ('8136', '2016-04-18', '1');
INSERT INTO `date_info` VALUES ('8137', '2016-04-19', '1');
INSERT INTO `date_info` VALUES ('8138', '2016-04-20', '1');
INSERT INTO `date_info` VALUES ('8139', '2016-04-21', '1');
INSERT INTO `date_info` VALUES ('8140', '2016-04-22', '1');
INSERT INTO `date_info` VALUES ('8141', '2016-04-23', '0');
INSERT INTO `date_info` VALUES ('8142', '2016-04-24', '0');
INSERT INTO `date_info` VALUES ('8143', '2016-04-25', '1');
INSERT INTO `date_info` VALUES ('8144', '2016-04-26', '1');
INSERT INTO `date_info` VALUES ('8145', '2016-04-27', '1');
INSERT INTO `date_info` VALUES ('8146', '2016-04-28', '1');
INSERT INTO `date_info` VALUES ('8147', '2016-04-29', '1');
INSERT INTO `date_info` VALUES ('8148', '2016-04-30', '0');
INSERT INTO `date_info` VALUES ('8149', '2016-05-01', '0');
INSERT INTO `date_info` VALUES ('8150', '2016-05-02', '1');
INSERT INTO `date_info` VALUES ('8151', '2016-05-03', '1');
INSERT INTO `date_info` VALUES ('8152', '2016-05-04', '1');
INSERT INTO `date_info` VALUES ('8153', '2016-05-05', '1');
INSERT INTO `date_info` VALUES ('8154', '2016-05-06', '1');
INSERT INTO `date_info` VALUES ('8155', '2016-05-07', '0');
INSERT INTO `date_info` VALUES ('8156', '2016-05-08', '0');
INSERT INTO `date_info` VALUES ('8157', '2016-05-09', '1');
INSERT INTO `date_info` VALUES ('8158', '2016-05-10', '1');
INSERT INTO `date_info` VALUES ('8159', '2016-05-11', '1');
INSERT INTO `date_info` VALUES ('8160', '2016-05-12', '1');
INSERT INTO `date_info` VALUES ('8161', '2016-05-13', '1');
INSERT INTO `date_info` VALUES ('8162', '2016-05-14', '0');
INSERT INTO `date_info` VALUES ('8163', '2016-05-15', '0');
INSERT INTO `date_info` VALUES ('8164', '2016-05-16', '1');
INSERT INTO `date_info` VALUES ('8165', '2016-05-17', '1');
INSERT INTO `date_info` VALUES ('8166', '2016-05-18', '1');
INSERT INTO `date_info` VALUES ('8167', '2016-05-19', '1');
INSERT INTO `date_info` VALUES ('8168', '2016-05-20', '1');
INSERT INTO `date_info` VALUES ('8169', '2016-05-21', '0');
INSERT INTO `date_info` VALUES ('8170', '2016-05-22', '0');
INSERT INTO `date_info` VALUES ('8171', '2016-05-23', '1');
INSERT INTO `date_info` VALUES ('8172', '2016-05-24', '1');
INSERT INTO `date_info` VALUES ('8173', '2016-05-25', '1');
INSERT INTO `date_info` VALUES ('8174', '2016-05-26', '1');
INSERT INTO `date_info` VALUES ('8175', '2016-05-27', '1');
INSERT INTO `date_info` VALUES ('8176', '2016-05-28', '0');
INSERT INTO `date_info` VALUES ('8177', '2016-05-29', '0');
INSERT INTO `date_info` VALUES ('8178', '2016-05-30', '1');
INSERT INTO `date_info` VALUES ('8179', '2016-05-31', '1');
INSERT INTO `date_info` VALUES ('8180', '2016-06-01', '1');
INSERT INTO `date_info` VALUES ('8181', '2016-06-02', '1');
INSERT INTO `date_info` VALUES ('8182', '2016-06-03', '1');
INSERT INTO `date_info` VALUES ('8183', '2016-06-04', '0');
INSERT INTO `date_info` VALUES ('8184', '2016-06-05', '0');
INSERT INTO `date_info` VALUES ('8185', '2016-06-06', '1');
INSERT INTO `date_info` VALUES ('8186', '2016-06-07', '1');
INSERT INTO `date_info` VALUES ('8187', '2016-06-08', '1');
INSERT INTO `date_info` VALUES ('8188', '2016-06-09', '1');
INSERT INTO `date_info` VALUES ('8189', '2016-06-10', '1');
INSERT INTO `date_info` VALUES ('8190', '2016-06-11', '0');
INSERT INTO `date_info` VALUES ('8191', '2016-06-12', '0');
INSERT INTO `date_info` VALUES ('8192', '2016-06-13', '1');
INSERT INTO `date_info` VALUES ('8193', '2016-06-14', '1');
INSERT INTO `date_info` VALUES ('8194', '2016-06-15', '1');
INSERT INTO `date_info` VALUES ('8195', '2016-06-16', '1');
INSERT INTO `date_info` VALUES ('8196', '2016-06-17', '1');
INSERT INTO `date_info` VALUES ('8197', '2016-06-18', '0');
INSERT INTO `date_info` VALUES ('8198', '2016-06-19', '0');
INSERT INTO `date_info` VALUES ('8199', '2016-06-20', '1');
INSERT INTO `date_info` VALUES ('8200', '2016-06-21', '1');
INSERT INTO `date_info` VALUES ('8201', '2016-06-22', '1');
INSERT INTO `date_info` VALUES ('8202', '2016-06-23', '1');
INSERT INTO `date_info` VALUES ('8203', '2016-06-24', '1');
INSERT INTO `date_info` VALUES ('8204', '2016-06-25', '0');
INSERT INTO `date_info` VALUES ('8205', '2016-06-26', '0');
INSERT INTO `date_info` VALUES ('8206', '2016-06-27', '1');
INSERT INTO `date_info` VALUES ('8207', '2016-06-28', '1');
INSERT INTO `date_info` VALUES ('8208', '2016-06-29', '1');
INSERT INTO `date_info` VALUES ('8209', '2016-06-30', '1');
INSERT INTO `date_info` VALUES ('8210', '2016-07-01', '1');
INSERT INTO `date_info` VALUES ('8211', '2016-07-02', '0');
INSERT INTO `date_info` VALUES ('8212', '2016-07-03', '0');
INSERT INTO `date_info` VALUES ('8213', '2016-07-04', '1');
INSERT INTO `date_info` VALUES ('8214', '2016-07-05', '1');
INSERT INTO `date_info` VALUES ('8215', '2016-07-06', '1');
INSERT INTO `date_info` VALUES ('8216', '2016-07-07', '1');
INSERT INTO `date_info` VALUES ('8217', '2016-07-08', '1');
INSERT INTO `date_info` VALUES ('8218', '2016-07-09', '0');
INSERT INTO `date_info` VALUES ('8219', '2016-07-10', '0');
INSERT INTO `date_info` VALUES ('8220', '2016-07-11', '1');
INSERT INTO `date_info` VALUES ('8221', '2016-07-12', '1');
INSERT INTO `date_info` VALUES ('8222', '2016-07-13', '1');
INSERT INTO `date_info` VALUES ('8223', '2016-07-14', '1');
INSERT INTO `date_info` VALUES ('8224', '2016-07-15', '1');
INSERT INTO `date_info` VALUES ('8225', '2016-07-16', '0');
INSERT INTO `date_info` VALUES ('8226', '2016-07-17', '0');
INSERT INTO `date_info` VALUES ('8227', '2016-07-18', '1');
INSERT INTO `date_info` VALUES ('8228', '2016-07-19', '1');
INSERT INTO `date_info` VALUES ('8229', '2016-07-20', '1');
INSERT INTO `date_info` VALUES ('8230', '2016-07-21', '1');
INSERT INTO `date_info` VALUES ('8231', '2016-07-22', '1');
INSERT INTO `date_info` VALUES ('8232', '2016-07-23', '0');
INSERT INTO `date_info` VALUES ('8233', '2016-07-24', '0');
INSERT INTO `date_info` VALUES ('8234', '2016-07-25', '1');
INSERT INTO `date_info` VALUES ('8235', '2016-07-26', '1');
INSERT INTO `date_info` VALUES ('8236', '2016-07-27', '1');
INSERT INTO `date_info` VALUES ('8237', '2016-07-28', '1');
INSERT INTO `date_info` VALUES ('8238', '2016-07-29', '1');
INSERT INTO `date_info` VALUES ('8239', '2016-07-30', '0');
INSERT INTO `date_info` VALUES ('8240', '2016-07-31', '0');
INSERT INTO `date_info` VALUES ('8241', '2016-08-01', '1');
INSERT INTO `date_info` VALUES ('8242', '2016-08-02', '1');
INSERT INTO `date_info` VALUES ('8243', '2016-08-03', '1');
INSERT INTO `date_info` VALUES ('8244', '2016-08-04', '1');
INSERT INTO `date_info` VALUES ('8245', '2016-08-05', '1');
INSERT INTO `date_info` VALUES ('8246', '2016-08-06', '0');
INSERT INTO `date_info` VALUES ('8247', '2016-08-07', '0');
INSERT INTO `date_info` VALUES ('8248', '2016-08-08', '1');
INSERT INTO `date_info` VALUES ('8249', '2016-08-09', '1');
INSERT INTO `date_info` VALUES ('8250', '2016-08-10', '1');
INSERT INTO `date_info` VALUES ('8251', '2016-08-11', '1');
INSERT INTO `date_info` VALUES ('8252', '2016-08-12', '1');
INSERT INTO `date_info` VALUES ('8253', '2016-08-13', '0');
INSERT INTO `date_info` VALUES ('8254', '2016-08-14', '0');
INSERT INTO `date_info` VALUES ('8255', '2016-08-15', '1');
INSERT INTO `date_info` VALUES ('8256', '2016-08-16', '1');
INSERT INTO `date_info` VALUES ('8257', '2016-08-17', '1');
INSERT INTO `date_info` VALUES ('8258', '2016-08-18', '1');
INSERT INTO `date_info` VALUES ('8259', '2016-08-19', '1');
INSERT INTO `date_info` VALUES ('8260', '2016-08-20', '0');
INSERT INTO `date_info` VALUES ('8261', '2016-08-21', '0');
INSERT INTO `date_info` VALUES ('8262', '2016-08-22', '1');
INSERT INTO `date_info` VALUES ('8263', '2016-08-23', '1');
INSERT INTO `date_info` VALUES ('8264', '2016-08-24', '1');
INSERT INTO `date_info` VALUES ('8265', '2016-08-25', '1');
INSERT INTO `date_info` VALUES ('8266', '2016-08-26', '1');
INSERT INTO `date_info` VALUES ('8267', '2016-08-27', '0');
INSERT INTO `date_info` VALUES ('8268', '2016-08-28', '0');
INSERT INTO `date_info` VALUES ('8269', '2016-08-29', '1');
INSERT INTO `date_info` VALUES ('8270', '2016-08-30', '1');
INSERT INTO `date_info` VALUES ('8271', '2016-08-31', '1');
INSERT INTO `date_info` VALUES ('8272', '2016-09-01', '1');
INSERT INTO `date_info` VALUES ('8273', '2016-09-02', '1');
INSERT INTO `date_info` VALUES ('8274', '2016-09-03', '0');
INSERT INTO `date_info` VALUES ('8275', '2016-09-04', '0');
INSERT INTO `date_info` VALUES ('8276', '2016-09-05', '1');
INSERT INTO `date_info` VALUES ('8277', '2016-09-06', '1');
INSERT INTO `date_info` VALUES ('8278', '2016-09-07', '1');
INSERT INTO `date_info` VALUES ('8279', '2016-09-08', '1');
INSERT INTO `date_info` VALUES ('8280', '2016-09-09', '1');
INSERT INTO `date_info` VALUES ('8281', '2016-09-10', '0');
INSERT INTO `date_info` VALUES ('8282', '2016-09-11', '0');
INSERT INTO `date_info` VALUES ('8283', '2016-09-12', '1');
INSERT INTO `date_info` VALUES ('8284', '2016-09-13', '1');
INSERT INTO `date_info` VALUES ('8285', '2016-09-14', '1');
INSERT INTO `date_info` VALUES ('8286', '2016-09-15', '1');
INSERT INTO `date_info` VALUES ('8287', '2016-09-16', '1');
INSERT INTO `date_info` VALUES ('8288', '2016-09-17', '0');
INSERT INTO `date_info` VALUES ('8289', '2016-09-18', '0');
INSERT INTO `date_info` VALUES ('8290', '2016-09-19', '1');
INSERT INTO `date_info` VALUES ('8291', '2016-09-20', '1');
INSERT INTO `date_info` VALUES ('8292', '2016-09-21', '1');
INSERT INTO `date_info` VALUES ('8293', '2016-09-22', '1');
INSERT INTO `date_info` VALUES ('8294', '2016-09-23', '1');
INSERT INTO `date_info` VALUES ('8295', '2016-09-24', '0');
INSERT INTO `date_info` VALUES ('8296', '2016-09-25', '0');
INSERT INTO `date_info` VALUES ('8297', '2016-09-26', '1');
INSERT INTO `date_info` VALUES ('8298', '2016-09-27', '1');
INSERT INTO `date_info` VALUES ('8299', '2016-09-28', '1');
INSERT INTO `date_info` VALUES ('8300', '2016-09-29', '1');
INSERT INTO `date_info` VALUES ('8301', '2016-09-30', '1');
INSERT INTO `date_info` VALUES ('8302', '2016-10-01', '0');
INSERT INTO `date_info` VALUES ('8303', '2016-10-02', '0');
INSERT INTO `date_info` VALUES ('8304', '2016-10-03', '1');
INSERT INTO `date_info` VALUES ('8305', '2016-10-04', '1');
INSERT INTO `date_info` VALUES ('8306', '2016-10-05', '1');
INSERT INTO `date_info` VALUES ('8307', '2016-10-06', '1');
INSERT INTO `date_info` VALUES ('8308', '2016-10-07', '1');
INSERT INTO `date_info` VALUES ('8309', '2016-10-08', '0');
INSERT INTO `date_info` VALUES ('8310', '2016-10-09', '0');
INSERT INTO `date_info` VALUES ('8311', '2016-10-10', '1');
INSERT INTO `date_info` VALUES ('8312', '2016-10-11', '1');
INSERT INTO `date_info` VALUES ('8313', '2016-10-12', '1');
INSERT INTO `date_info` VALUES ('8314', '2016-10-13', '1');
INSERT INTO `date_info` VALUES ('8315', '2016-10-14', '1');
INSERT INTO `date_info` VALUES ('8316', '2016-10-15', '0');
INSERT INTO `date_info` VALUES ('8317', '2016-10-16', '0');
INSERT INTO `date_info` VALUES ('8318', '2016-10-17', '1');
INSERT INTO `date_info` VALUES ('8319', '2016-10-18', '1');
INSERT INTO `date_info` VALUES ('8320', '2016-10-19', '1');
INSERT INTO `date_info` VALUES ('8321', '2016-10-20', '1');
INSERT INTO `date_info` VALUES ('8322', '2016-10-21', '1');
INSERT INTO `date_info` VALUES ('8323', '2016-10-22', '0');
INSERT INTO `date_info` VALUES ('8324', '2016-10-23', '0');
INSERT INTO `date_info` VALUES ('8325', '2016-10-24', '1');
INSERT INTO `date_info` VALUES ('8326', '2016-10-25', '1');
INSERT INTO `date_info` VALUES ('8327', '2016-10-26', '1');
INSERT INTO `date_info` VALUES ('8328', '2016-10-27', '1');
INSERT INTO `date_info` VALUES ('8329', '2016-10-28', '1');
INSERT INTO `date_info` VALUES ('8330', '2016-10-29', '0');
INSERT INTO `date_info` VALUES ('8331', '2016-10-30', '0');
INSERT INTO `date_info` VALUES ('8332', '2016-10-31', '1');
INSERT INTO `date_info` VALUES ('8333', '2016-11-01', '1');
INSERT INTO `date_info` VALUES ('8334', '2016-11-02', '1');
INSERT INTO `date_info` VALUES ('8335', '2016-11-03', '1');
INSERT INTO `date_info` VALUES ('8336', '2016-11-04', '1');
INSERT INTO `date_info` VALUES ('8337', '2016-11-05', '0');
INSERT INTO `date_info` VALUES ('8338', '2016-11-06', '0');
INSERT INTO `date_info` VALUES ('8339', '2016-11-07', '1');
INSERT INTO `date_info` VALUES ('8340', '2016-11-08', '1');
INSERT INTO `date_info` VALUES ('8341', '2016-11-09', '1');
INSERT INTO `date_info` VALUES ('8342', '2016-11-10', '1');
INSERT INTO `date_info` VALUES ('8343', '2016-11-11', '1');
INSERT INTO `date_info` VALUES ('8344', '2016-11-12', '0');
INSERT INTO `date_info` VALUES ('8345', '2016-11-13', '0');
INSERT INTO `date_info` VALUES ('8346', '2016-11-14', '1');
INSERT INTO `date_info` VALUES ('8347', '2016-11-15', '1');
INSERT INTO `date_info` VALUES ('8348', '2016-11-16', '1');
INSERT INTO `date_info` VALUES ('8349', '2016-11-17', '1');
INSERT INTO `date_info` VALUES ('8350', '2016-11-18', '1');
INSERT INTO `date_info` VALUES ('8351', '2016-11-19', '0');
INSERT INTO `date_info` VALUES ('8352', '2016-11-20', '0');
INSERT INTO `date_info` VALUES ('8353', '2016-11-21', '1');
INSERT INTO `date_info` VALUES ('8354', '2016-11-22', '1');
INSERT INTO `date_info` VALUES ('8355', '2016-11-23', '1');
INSERT INTO `date_info` VALUES ('8356', '2016-11-24', '1');
INSERT INTO `date_info` VALUES ('8357', '2016-11-25', '1');
INSERT INTO `date_info` VALUES ('8358', '2016-11-26', '0');
INSERT INTO `date_info` VALUES ('8359', '2016-11-27', '0');
INSERT INTO `date_info` VALUES ('8360', '2016-11-28', '1');
INSERT INTO `date_info` VALUES ('8361', '2016-11-29', '1');
INSERT INTO `date_info` VALUES ('8362', '2016-11-30', '1');
INSERT INTO `date_info` VALUES ('8363', '2016-12-01', '1');
INSERT INTO `date_info` VALUES ('8364', '2016-12-02', '1');
INSERT INTO `date_info` VALUES ('8365', '2016-12-03', '0');
INSERT INTO `date_info` VALUES ('8366', '2016-12-04', '0');
INSERT INTO `date_info` VALUES ('8367', '2016-12-05', '1');
INSERT INTO `date_info` VALUES ('8368', '2016-12-06', '1');
INSERT INTO `date_info` VALUES ('8369', '2016-12-07', '1');
INSERT INTO `date_info` VALUES ('8370', '2016-12-08', '1');
INSERT INTO `date_info` VALUES ('8371', '2016-12-09', '1');
INSERT INTO `date_info` VALUES ('8372', '2016-12-10', '0');
INSERT INTO `date_info` VALUES ('8373', '2016-12-11', '0');
INSERT INTO `date_info` VALUES ('8374', '2016-12-12', '1');
INSERT INTO `date_info` VALUES ('8375', '2016-12-13', '1');
INSERT INTO `date_info` VALUES ('8376', '2016-12-14', '1');
INSERT INTO `date_info` VALUES ('8377', '2016-12-15', '1');
INSERT INTO `date_info` VALUES ('8378', '2016-12-16', '1');
INSERT INTO `date_info` VALUES ('8379', '2016-12-17', '0');
INSERT INTO `date_info` VALUES ('8380', '2016-12-18', '0');
INSERT INTO `date_info` VALUES ('8381', '2016-12-19', '1');
INSERT INTO `date_info` VALUES ('8382', '2016-12-20', '1');
INSERT INTO `date_info` VALUES ('8383', '2016-12-21', '1');
INSERT INTO `date_info` VALUES ('8384', '2016-12-22', '1');
INSERT INTO `date_info` VALUES ('8385', '2016-12-23', '1');
INSERT INTO `date_info` VALUES ('8386', '2016-12-24', '0');
INSERT INTO `date_info` VALUES ('8387', '2016-12-25', '0');
INSERT INTO `date_info` VALUES ('8388', '2016-12-26', '1');
INSERT INTO `date_info` VALUES ('8389', '2016-12-27', '1');
INSERT INTO `date_info` VALUES ('8390', '2016-12-28', '1');
INSERT INTO `date_info` VALUES ('8391', '2016-12-29', '1');
INSERT INTO `date_info` VALUES ('8392', '2016-12-30', '1');
INSERT INTO `date_info` VALUES ('8393', '2016-12-31', '0');
INSERT INTO `date_info` VALUES ('8394', '2017-01-01', '0');
INSERT INTO `date_info` VALUES ('8395', '2017-01-02', '1');
INSERT INTO `date_info` VALUES ('8396', '2017-01-03', '1');
INSERT INTO `date_info` VALUES ('8397', '2017-01-04', '1');
INSERT INTO `date_info` VALUES ('8398', '2017-01-05', '1');
INSERT INTO `date_info` VALUES ('8399', '2017-01-06', '1');
INSERT INTO `date_info` VALUES ('8400', '2017-01-07', '0');
INSERT INTO `date_info` VALUES ('8401', '2017-01-08', '0');
INSERT INTO `date_info` VALUES ('8402', '2017-01-09', '1');
INSERT INTO `date_info` VALUES ('8403', '2017-01-10', '1');
INSERT INTO `date_info` VALUES ('8404', '2017-01-11', '1');
INSERT INTO `date_info` VALUES ('8405', '2017-01-12', '1');
INSERT INTO `date_info` VALUES ('8406', '2017-01-13', '1');
INSERT INTO `date_info` VALUES ('8407', '2017-01-14', '0');
INSERT INTO `date_info` VALUES ('8408', '2017-01-15', '0');
INSERT INTO `date_info` VALUES ('8409', '2017-01-16', '1');
INSERT INTO `date_info` VALUES ('8410', '2017-01-17', '1');
INSERT INTO `date_info` VALUES ('8411', '2017-01-18', '1');
INSERT INTO `date_info` VALUES ('8412', '2017-01-19', '1');
INSERT INTO `date_info` VALUES ('8413', '2017-01-20', '1');
INSERT INTO `date_info` VALUES ('8414', '2017-01-21', '0');
INSERT INTO `date_info` VALUES ('8415', '2017-01-22', '0');
INSERT INTO `date_info` VALUES ('8416', '2017-01-23', '1');
INSERT INTO `date_info` VALUES ('8417', '2017-01-24', '1');
INSERT INTO `date_info` VALUES ('8418', '2017-01-25', '1');
INSERT INTO `date_info` VALUES ('8419', '2017-01-26', '1');
INSERT INTO `date_info` VALUES ('8420', '2017-01-27', '1');
INSERT INTO `date_info` VALUES ('8421', '2017-01-28', '0');
INSERT INTO `date_info` VALUES ('8422', '2017-01-29', '0');
INSERT INTO `date_info` VALUES ('8423', '2017-01-30', '1');
INSERT INTO `date_info` VALUES ('8424', '2017-01-31', '1');
INSERT INTO `date_info` VALUES ('8425', '2017-02-01', '1');
INSERT INTO `date_info` VALUES ('8426', '2017-02-02', '1');
INSERT INTO `date_info` VALUES ('8427', '2017-02-03', '1');
INSERT INTO `date_info` VALUES ('8428', '2017-02-04', '0');
INSERT INTO `date_info` VALUES ('8429', '2017-02-05', '0');
INSERT INTO `date_info` VALUES ('8430', '2017-02-06', '1');
INSERT INTO `date_info` VALUES ('8431', '2017-02-07', '1');
INSERT INTO `date_info` VALUES ('8432', '2017-02-08', '1');
INSERT INTO `date_info` VALUES ('8433', '2017-02-09', '1');
INSERT INTO `date_info` VALUES ('8434', '2017-02-10', '1');
INSERT INTO `date_info` VALUES ('8435', '2017-02-11', '0');
INSERT INTO `date_info` VALUES ('8436', '2017-02-12', '0');
INSERT INTO `date_info` VALUES ('8437', '2017-02-13', '1');
INSERT INTO `date_info` VALUES ('8438', '2017-02-14', '1');
INSERT INTO `date_info` VALUES ('8439', '2017-02-15', '1');
INSERT INTO `date_info` VALUES ('8440', '2017-02-16', '1');
INSERT INTO `date_info` VALUES ('8441', '2017-02-17', '1');
INSERT INTO `date_info` VALUES ('8442', '2017-02-18', '0');
INSERT INTO `date_info` VALUES ('8443', '2017-02-19', '0');
INSERT INTO `date_info` VALUES ('8444', '2017-02-20', '1');
INSERT INTO `date_info` VALUES ('8445', '2017-02-21', '1');
INSERT INTO `date_info` VALUES ('8446', '2017-02-22', '1');
INSERT INTO `date_info` VALUES ('8447', '2017-02-23', '1');
INSERT INTO `date_info` VALUES ('8448', '2017-02-24', '1');
INSERT INTO `date_info` VALUES ('8449', '2017-02-25', '0');
INSERT INTO `date_info` VALUES ('8450', '2017-02-26', '0');
INSERT INTO `date_info` VALUES ('8451', '2017-02-27', '1');
INSERT INTO `date_info` VALUES ('8452', '2017-02-28', '1');
INSERT INTO `date_info` VALUES ('8453', '2017-03-01', '1');
INSERT INTO `date_info` VALUES ('8454', '2017-03-02', '1');
INSERT INTO `date_info` VALUES ('8455', '2017-03-03', '1');
INSERT INTO `date_info` VALUES ('8456', '2017-03-04', '0');
INSERT INTO `date_info` VALUES ('8457', '2017-03-05', '0');
INSERT INTO `date_info` VALUES ('8458', '2017-03-06', '1');
INSERT INTO `date_info` VALUES ('8459', '2017-03-07', '1');
INSERT INTO `date_info` VALUES ('8460', '2017-03-08', '1');
INSERT INTO `date_info` VALUES ('8461', '2017-03-09', '1');
INSERT INTO `date_info` VALUES ('8462', '2017-03-10', '1');
INSERT INTO `date_info` VALUES ('8463', '2017-03-11', '0');
INSERT INTO `date_info` VALUES ('8464', '2017-03-12', '0');
INSERT INTO `date_info` VALUES ('8465', '2017-03-13', '1');
INSERT INTO `date_info` VALUES ('8466', '2017-03-14', '1');
INSERT INTO `date_info` VALUES ('8467', '2017-03-15', '1');
INSERT INTO `date_info` VALUES ('8468', '2017-03-16', '1');
INSERT INTO `date_info` VALUES ('8469', '2017-03-17', '1');
INSERT INTO `date_info` VALUES ('8470', '2017-03-18', '0');
INSERT INTO `date_info` VALUES ('8471', '2017-03-19', '0');
INSERT INTO `date_info` VALUES ('8472', '2017-03-20', '1');
INSERT INTO `date_info` VALUES ('8473', '2017-03-21', '1');
INSERT INTO `date_info` VALUES ('8474', '2017-03-22', '1');
INSERT INTO `date_info` VALUES ('8475', '2017-03-23', '1');
INSERT INTO `date_info` VALUES ('8476', '2017-03-24', '1');
INSERT INTO `date_info` VALUES ('8477', '2017-03-25', '0');
INSERT INTO `date_info` VALUES ('8478', '2017-03-26', '0');
INSERT INTO `date_info` VALUES ('8479', '2017-03-27', '1');
INSERT INTO `date_info` VALUES ('8480', '2017-03-28', '1');
INSERT INTO `date_info` VALUES ('8481', '2017-03-29', '1');
INSERT INTO `date_info` VALUES ('8482', '2017-03-30', '1');
INSERT INTO `date_info` VALUES ('8483', '2017-03-31', '1');
INSERT INTO `date_info` VALUES ('8484', '2017-04-01', '0');
INSERT INTO `date_info` VALUES ('8485', '2017-04-02', '0');
INSERT INTO `date_info` VALUES ('8486', '2017-04-03', '1');
INSERT INTO `date_info` VALUES ('8487', '2017-04-04', '1');
INSERT INTO `date_info` VALUES ('8488', '2017-04-05', '1');
INSERT INTO `date_info` VALUES ('8489', '2017-04-06', '1');
INSERT INTO `date_info` VALUES ('8490', '2017-04-07', '1');
INSERT INTO `date_info` VALUES ('8491', '2017-04-08', '0');
INSERT INTO `date_info` VALUES ('8492', '2017-04-09', '0');
INSERT INTO `date_info` VALUES ('8493', '2017-04-10', '1');
INSERT INTO `date_info` VALUES ('8494', '2017-04-11', '1');
INSERT INTO `date_info` VALUES ('8495', '2017-04-12', '1');
INSERT INTO `date_info` VALUES ('8496', '2017-04-13', '1');
INSERT INTO `date_info` VALUES ('8497', '2017-04-14', '1');
INSERT INTO `date_info` VALUES ('8498', '2017-04-15', '0');
INSERT INTO `date_info` VALUES ('8499', '2017-04-16', '0');
INSERT INTO `date_info` VALUES ('8500', '2017-04-17', '1');
INSERT INTO `date_info` VALUES ('8501', '2017-04-18', '1');
INSERT INTO `date_info` VALUES ('8502', '2017-04-19', '1');
INSERT INTO `date_info` VALUES ('8503', '2017-04-20', '1');
INSERT INTO `date_info` VALUES ('8504', '2017-04-21', '1');
INSERT INTO `date_info` VALUES ('8505', '2017-04-22', '0');
INSERT INTO `date_info` VALUES ('8506', '2017-04-23', '0');
INSERT INTO `date_info` VALUES ('8507', '2017-04-24', '1');
INSERT INTO `date_info` VALUES ('8508', '2017-04-25', '1');
INSERT INTO `date_info` VALUES ('8509', '2017-04-26', '1');
INSERT INTO `date_info` VALUES ('8510', '2017-04-27', '1');
INSERT INTO `date_info` VALUES ('8511', '2017-04-28', '1');
INSERT INTO `date_info` VALUES ('8512', '2017-04-29', '0');
INSERT INTO `date_info` VALUES ('8513', '2017-04-30', '0');
INSERT INTO `date_info` VALUES ('8514', '2017-05-01', '1');
INSERT INTO `date_info` VALUES ('8515', '2017-05-02', '1');
INSERT INTO `date_info` VALUES ('8516', '2017-05-03', '1');
INSERT INTO `date_info` VALUES ('8517', '2017-05-04', '1');
INSERT INTO `date_info` VALUES ('8518', '2017-05-05', '1');
INSERT INTO `date_info` VALUES ('8519', '2017-05-06', '0');
INSERT INTO `date_info` VALUES ('8520', '2017-05-07', '0');
INSERT INTO `date_info` VALUES ('8521', '2017-05-08', '1');
INSERT INTO `date_info` VALUES ('8522', '2017-05-09', '1');
INSERT INTO `date_info` VALUES ('8523', '2017-05-10', '1');
INSERT INTO `date_info` VALUES ('8524', '2017-05-11', '1');
INSERT INTO `date_info` VALUES ('8525', '2017-05-12', '1');
INSERT INTO `date_info` VALUES ('8526', '2017-05-13', '0');
INSERT INTO `date_info` VALUES ('8527', '2017-05-14', '0');
INSERT INTO `date_info` VALUES ('8528', '2017-05-15', '1');
INSERT INTO `date_info` VALUES ('8529', '2017-05-16', '1');
INSERT INTO `date_info` VALUES ('8530', '2017-05-17', '1');
INSERT INTO `date_info` VALUES ('8531', '2017-05-18', '1');
INSERT INTO `date_info` VALUES ('8532', '2017-05-19', '1');
INSERT INTO `date_info` VALUES ('8533', '2017-05-20', '0');
INSERT INTO `date_info` VALUES ('8534', '2017-05-21', '0');
INSERT INTO `date_info` VALUES ('8535', '2017-05-22', '1');
INSERT INTO `date_info` VALUES ('8536', '2017-05-23', '1');
INSERT INTO `date_info` VALUES ('8537', '2017-05-24', '1');
INSERT INTO `date_info` VALUES ('8538', '2017-05-25', '1');
INSERT INTO `date_info` VALUES ('8539', '2017-05-26', '1');
INSERT INTO `date_info` VALUES ('8540', '2017-05-27', '0');
INSERT INTO `date_info` VALUES ('8541', '2017-05-28', '0');
INSERT INTO `date_info` VALUES ('8542', '2017-05-29', '1');
INSERT INTO `date_info` VALUES ('8543', '2017-05-30', '1');
INSERT INTO `date_info` VALUES ('8544', '2017-05-31', '1');
INSERT INTO `date_info` VALUES ('8545', '2017-06-01', '1');
INSERT INTO `date_info` VALUES ('8546', '2017-06-02', '1');
INSERT INTO `date_info` VALUES ('8547', '2017-06-03', '0');
INSERT INTO `date_info` VALUES ('8548', '2017-06-04', '0');
INSERT INTO `date_info` VALUES ('8549', '2017-06-05', '1');
INSERT INTO `date_info` VALUES ('8550', '2017-06-06', '1');
INSERT INTO `date_info` VALUES ('8551', '2017-06-07', '1');
INSERT INTO `date_info` VALUES ('8552', '2017-06-08', '1');
INSERT INTO `date_info` VALUES ('8553', '2017-06-09', '1');
INSERT INTO `date_info` VALUES ('8554', '2017-06-10', '0');
INSERT INTO `date_info` VALUES ('8555', '2017-06-11', '0');
INSERT INTO `date_info` VALUES ('8556', '2017-06-12', '1');
INSERT INTO `date_info` VALUES ('8557', '2017-06-13', '1');
INSERT INTO `date_info` VALUES ('8558', '2017-06-14', '1');
INSERT INTO `date_info` VALUES ('8559', '2017-06-15', '1');
INSERT INTO `date_info` VALUES ('8560', '2017-06-16', '1');
INSERT INTO `date_info` VALUES ('8561', '2017-06-17', '0');
INSERT INTO `date_info` VALUES ('8562', '2017-06-18', '0');
INSERT INTO `date_info` VALUES ('8563', '2017-06-19', '1');
INSERT INTO `date_info` VALUES ('8564', '2017-06-20', '1');
INSERT INTO `date_info` VALUES ('8565', '2017-06-21', '1');
INSERT INTO `date_info` VALUES ('8566', '2017-06-22', '1');
INSERT INTO `date_info` VALUES ('8567', '2017-06-23', '1');
INSERT INTO `date_info` VALUES ('8568', '2017-06-24', '0');
INSERT INTO `date_info` VALUES ('8569', '2017-06-25', '0');
INSERT INTO `date_info` VALUES ('8570', '2017-06-26', '1');
INSERT INTO `date_info` VALUES ('8571', '2017-06-27', '1');
INSERT INTO `date_info` VALUES ('8572', '2017-06-28', '1');
INSERT INTO `date_info` VALUES ('8573', '2017-06-29', '1');
INSERT INTO `date_info` VALUES ('8574', '2017-06-30', '1');
INSERT INTO `date_info` VALUES ('8575', '2017-07-01', '0');
INSERT INTO `date_info` VALUES ('8576', '2017-07-02', '0');
INSERT INTO `date_info` VALUES ('8577', '2017-07-03', '1');
INSERT INTO `date_info` VALUES ('8578', '2017-07-04', '1');
INSERT INTO `date_info` VALUES ('8579', '2017-07-05', '1');
INSERT INTO `date_info` VALUES ('8580', '2017-07-06', '1');
INSERT INTO `date_info` VALUES ('8581', '2017-07-07', '1');
INSERT INTO `date_info` VALUES ('8582', '2017-07-08', '0');
INSERT INTO `date_info` VALUES ('8583', '2017-07-09', '0');
INSERT INTO `date_info` VALUES ('8584', '2017-07-10', '1');
INSERT INTO `date_info` VALUES ('8585', '2017-07-11', '1');
INSERT INTO `date_info` VALUES ('8586', '2017-07-12', '1');
INSERT INTO `date_info` VALUES ('8587', '2017-07-13', '1');
INSERT INTO `date_info` VALUES ('8588', '2017-07-14', '1');
INSERT INTO `date_info` VALUES ('8589', '2017-07-15', '0');
INSERT INTO `date_info` VALUES ('8590', '2017-07-16', '0');
INSERT INTO `date_info` VALUES ('8591', '2017-07-17', '1');
INSERT INTO `date_info` VALUES ('8592', '2017-07-18', '1');
INSERT INTO `date_info` VALUES ('8593', '2017-07-19', '1');
INSERT INTO `date_info` VALUES ('8594', '2017-07-20', '1');
INSERT INTO `date_info` VALUES ('8595', '2017-07-21', '1');
INSERT INTO `date_info` VALUES ('8596', '2017-07-22', '0');
INSERT INTO `date_info` VALUES ('8597', '2017-07-23', '0');
INSERT INTO `date_info` VALUES ('8598', '2017-07-24', '1');
INSERT INTO `date_info` VALUES ('8599', '2017-07-25', '1');
INSERT INTO `date_info` VALUES ('8600', '2017-07-26', '1');
INSERT INTO `date_info` VALUES ('8601', '2017-07-27', '1');
INSERT INTO `date_info` VALUES ('8602', '2017-07-28', '1');
INSERT INTO `date_info` VALUES ('8603', '2017-07-29', '0');
INSERT INTO `date_info` VALUES ('8604', '2017-07-30', '0');
INSERT INTO `date_info` VALUES ('8605', '2017-07-31', '1');
INSERT INTO `date_info` VALUES ('8606', '2017-08-01', '1');
INSERT INTO `date_info` VALUES ('8607', '2017-08-02', '1');
INSERT INTO `date_info` VALUES ('8608', '2017-08-03', '1');
INSERT INTO `date_info` VALUES ('8609', '2017-08-04', '1');
INSERT INTO `date_info` VALUES ('8610', '2017-08-05', '0');
INSERT INTO `date_info` VALUES ('8611', '2017-08-06', '0');
INSERT INTO `date_info` VALUES ('8612', '2017-08-07', '1');
INSERT INTO `date_info` VALUES ('8613', '2017-08-08', '1');
INSERT INTO `date_info` VALUES ('8614', '2017-08-09', '1');
INSERT INTO `date_info` VALUES ('8615', '2017-08-10', '1');
INSERT INTO `date_info` VALUES ('8616', '2017-08-11', '1');
INSERT INTO `date_info` VALUES ('8617', '2017-08-12', '0');
INSERT INTO `date_info` VALUES ('8618', '2017-08-13', '0');
INSERT INTO `date_info` VALUES ('8619', '2017-08-14', '1');
INSERT INTO `date_info` VALUES ('8620', '2017-08-15', '1');
INSERT INTO `date_info` VALUES ('8621', '2017-08-16', '1');
INSERT INTO `date_info` VALUES ('8622', '2017-08-17', '1');
INSERT INTO `date_info` VALUES ('8623', '2017-08-18', '1');
INSERT INTO `date_info` VALUES ('8624', '2017-08-19', '0');
INSERT INTO `date_info` VALUES ('8625', '2017-08-20', '0');
INSERT INTO `date_info` VALUES ('8626', '2017-08-21', '1');
INSERT INTO `date_info` VALUES ('8627', '2017-08-22', '1');
INSERT INTO `date_info` VALUES ('8628', '2017-08-23', '1');
INSERT INTO `date_info` VALUES ('8629', '2017-08-24', '1');
INSERT INTO `date_info` VALUES ('8630', '2017-08-25', '1');
INSERT INTO `date_info` VALUES ('8631', '2017-08-26', '0');
INSERT INTO `date_info` VALUES ('8632', '2017-08-27', '0');
INSERT INTO `date_info` VALUES ('8633', '2017-08-28', '1');
INSERT INTO `date_info` VALUES ('8634', '2017-08-29', '1');
INSERT INTO `date_info` VALUES ('8635', '2017-08-30', '1');
INSERT INTO `date_info` VALUES ('8636', '2017-08-31', '1');
INSERT INTO `date_info` VALUES ('8637', '2017-09-01', '1');
INSERT INTO `date_info` VALUES ('8638', '2017-09-02', '0');
INSERT INTO `date_info` VALUES ('8639', '2017-09-03', '0');
INSERT INTO `date_info` VALUES ('8640', '2017-09-04', '1');
INSERT INTO `date_info` VALUES ('8641', '2017-09-05', '1');
INSERT INTO `date_info` VALUES ('8642', '2017-09-06', '1');
INSERT INTO `date_info` VALUES ('8643', '2017-09-07', '1');
INSERT INTO `date_info` VALUES ('8644', '2017-09-08', '1');
INSERT INTO `date_info` VALUES ('8645', '2017-09-09', '0');
INSERT INTO `date_info` VALUES ('8646', '2017-09-10', '0');
INSERT INTO `date_info` VALUES ('8647', '2017-09-11', '1');
INSERT INTO `date_info` VALUES ('8648', '2017-09-12', '1');
INSERT INTO `date_info` VALUES ('8649', '2017-09-13', '1');
INSERT INTO `date_info` VALUES ('8650', '2017-09-14', '1');
INSERT INTO `date_info` VALUES ('8651', '2017-09-15', '1');
INSERT INTO `date_info` VALUES ('8652', '2017-09-16', '0');
INSERT INTO `date_info` VALUES ('8653', '2017-09-17', '0');
INSERT INTO `date_info` VALUES ('8654', '2017-09-18', '1');
INSERT INTO `date_info` VALUES ('8655', '2017-09-19', '1');
INSERT INTO `date_info` VALUES ('8656', '2017-09-20', '1');
INSERT INTO `date_info` VALUES ('8657', '2017-09-21', '1');
INSERT INTO `date_info` VALUES ('8658', '2017-09-22', '1');
INSERT INTO `date_info` VALUES ('8659', '2017-09-23', '0');
INSERT INTO `date_info` VALUES ('8660', '2017-09-24', '0');
INSERT INTO `date_info` VALUES ('8661', '2017-09-25', '1');
INSERT INTO `date_info` VALUES ('8662', '2017-09-26', '1');
INSERT INTO `date_info` VALUES ('8663', '2017-09-27', '1');
INSERT INTO `date_info` VALUES ('8664', '2017-09-28', '1');
INSERT INTO `date_info` VALUES ('8665', '2017-09-29', '1');
INSERT INTO `date_info` VALUES ('8666', '2017-09-30', '0');
INSERT INTO `date_info` VALUES ('8667', '2017-10-01', '0');
INSERT INTO `date_info` VALUES ('8668', '2017-10-02', '1');
INSERT INTO `date_info` VALUES ('8669', '2017-10-03', '1');
INSERT INTO `date_info` VALUES ('8670', '2017-10-04', '1');
INSERT INTO `date_info` VALUES ('8671', '2017-10-05', '1');
INSERT INTO `date_info` VALUES ('8672', '2017-10-06', '1');
INSERT INTO `date_info` VALUES ('8673', '2017-10-07', '0');
INSERT INTO `date_info` VALUES ('8674', '2017-10-08', '0');
INSERT INTO `date_info` VALUES ('8675', '2017-10-09', '1');
INSERT INTO `date_info` VALUES ('8676', '2017-10-10', '1');
INSERT INTO `date_info` VALUES ('8677', '2017-10-11', '1');
INSERT INTO `date_info` VALUES ('8678', '2017-10-12', '1');
INSERT INTO `date_info` VALUES ('8679', '2017-10-13', '1');
INSERT INTO `date_info` VALUES ('8680', '2017-10-14', '0');
INSERT INTO `date_info` VALUES ('8681', '2017-10-15', '0');
INSERT INTO `date_info` VALUES ('8682', '2017-10-16', '1');
INSERT INTO `date_info` VALUES ('8683', '2017-10-17', '1');
INSERT INTO `date_info` VALUES ('8684', '2017-10-18', '1');
INSERT INTO `date_info` VALUES ('8685', '2017-10-19', '1');
INSERT INTO `date_info` VALUES ('8686', '2017-10-20', '1');
INSERT INTO `date_info` VALUES ('8687', '2017-10-21', '0');
INSERT INTO `date_info` VALUES ('8688', '2017-10-22', '0');
INSERT INTO `date_info` VALUES ('8689', '2017-10-23', '1');
INSERT INTO `date_info` VALUES ('8690', '2017-10-24', '1');
INSERT INTO `date_info` VALUES ('8691', '2017-10-25', '1');
INSERT INTO `date_info` VALUES ('8692', '2017-10-26', '1');
INSERT INTO `date_info` VALUES ('8693', '2017-10-27', '1');
INSERT INTO `date_info` VALUES ('8694', '2017-10-28', '0');
INSERT INTO `date_info` VALUES ('8695', '2017-10-29', '0');
INSERT INTO `date_info` VALUES ('8696', '2017-10-30', '1');
INSERT INTO `date_info` VALUES ('8697', '2017-10-31', '1');
INSERT INTO `date_info` VALUES ('8698', '2017-11-01', '1');
INSERT INTO `date_info` VALUES ('8699', '2017-11-02', '1');
INSERT INTO `date_info` VALUES ('8700', '2017-11-03', '1');
INSERT INTO `date_info` VALUES ('8701', '2017-11-04', '0');
INSERT INTO `date_info` VALUES ('8702', '2017-11-05', '0');
INSERT INTO `date_info` VALUES ('8703', '2017-11-06', '1');
INSERT INTO `date_info` VALUES ('8704', '2017-11-07', '1');
INSERT INTO `date_info` VALUES ('8705', '2017-11-08', '1');
INSERT INTO `date_info` VALUES ('8706', '2017-11-09', '1');
INSERT INTO `date_info` VALUES ('8707', '2017-11-10', '1');
INSERT INTO `date_info` VALUES ('8708', '2017-11-11', '0');
INSERT INTO `date_info` VALUES ('8709', '2017-11-12', '0');
INSERT INTO `date_info` VALUES ('8710', '2017-11-13', '1');
INSERT INTO `date_info` VALUES ('8711', '2017-11-14', '1');
INSERT INTO `date_info` VALUES ('8712', '2017-11-15', '1');
INSERT INTO `date_info` VALUES ('8713', '2017-11-16', '1');
INSERT INTO `date_info` VALUES ('8714', '2017-11-17', '1');
INSERT INTO `date_info` VALUES ('8715', '2017-11-18', '0');
INSERT INTO `date_info` VALUES ('8716', '2017-11-19', '0');
INSERT INTO `date_info` VALUES ('8717', '2017-11-20', '1');
INSERT INTO `date_info` VALUES ('8718', '2017-11-21', '1');
INSERT INTO `date_info` VALUES ('8719', '2017-11-22', '1');
INSERT INTO `date_info` VALUES ('8720', '2017-11-23', '1');
INSERT INTO `date_info` VALUES ('8721', '2017-11-24', '1');
INSERT INTO `date_info` VALUES ('8722', '2017-11-25', '0');
INSERT INTO `date_info` VALUES ('8723', '2017-11-26', '0');
INSERT INTO `date_info` VALUES ('8724', '2017-11-27', '1');
INSERT INTO `date_info` VALUES ('8725', '2017-11-28', '1');
INSERT INTO `date_info` VALUES ('8726', '2017-11-29', '1');
INSERT INTO `date_info` VALUES ('8727', '2017-11-30', '1');
INSERT INTO `date_info` VALUES ('8728', '2017-12-01', '1');
INSERT INTO `date_info` VALUES ('8729', '2017-12-02', '0');
INSERT INTO `date_info` VALUES ('8730', '2017-12-03', '0');
INSERT INTO `date_info` VALUES ('8731', '2017-12-04', '1');
INSERT INTO `date_info` VALUES ('8732', '2017-12-05', '1');
INSERT INTO `date_info` VALUES ('8733', '2017-12-06', '1');
INSERT INTO `date_info` VALUES ('8734', '2017-12-07', '1');
INSERT INTO `date_info` VALUES ('8735', '2017-12-08', '1');
INSERT INTO `date_info` VALUES ('8736', '2017-12-09', '0');
INSERT INTO `date_info` VALUES ('8737', '2017-12-10', '0');
INSERT INTO `date_info` VALUES ('8738', '2017-12-11', '1');
INSERT INTO `date_info` VALUES ('8739', '2017-12-12', '1');
INSERT INTO `date_info` VALUES ('8740', '2017-12-13', '1');
INSERT INTO `date_info` VALUES ('8741', '2017-12-14', '1');
INSERT INTO `date_info` VALUES ('8742', '2017-12-15', '1');
INSERT INTO `date_info` VALUES ('8743', '2017-12-16', '0');
INSERT INTO `date_info` VALUES ('8744', '2017-12-17', '0');
INSERT INTO `date_info` VALUES ('8745', '2017-12-18', '1');
INSERT INTO `date_info` VALUES ('8746', '2017-12-19', '1');
INSERT INTO `date_info` VALUES ('8747', '2017-12-20', '1');
INSERT INTO `date_info` VALUES ('8748', '2017-12-21', '1');
INSERT INTO `date_info` VALUES ('8749', '2017-12-22', '1');
INSERT INTO `date_info` VALUES ('8750', '2017-12-23', '0');
INSERT INTO `date_info` VALUES ('8751', '2017-12-24', '0');
INSERT INTO `date_info` VALUES ('8752', '2017-12-25', '1');
INSERT INTO `date_info` VALUES ('8753', '2017-12-26', '1');
INSERT INTO `date_info` VALUES ('8754', '2017-12-27', '1');
INSERT INTO `date_info` VALUES ('8755', '2017-12-28', '1');
INSERT INTO `date_info` VALUES ('8756', '2017-12-29', '1');
INSERT INTO `date_info` VALUES ('8757', '2017-12-30', '0');
INSERT INTO `date_info` VALUES ('8758', '2017-12-31', '0');
INSERT INTO `date_info` VALUES ('8759', '2018-01-01', '1');
INSERT INTO `date_info` VALUES ('8760', '2018-01-02', '1');
INSERT INTO `date_info` VALUES ('8761', '2018-01-03', '1');
INSERT INTO `date_info` VALUES ('8762', '2018-01-04', '1');
INSERT INTO `date_info` VALUES ('8763', '2018-01-05', '1');
INSERT INTO `date_info` VALUES ('8764', '2018-01-06', '0');
INSERT INTO `date_info` VALUES ('8765', '2018-01-07', '0');
INSERT INTO `date_info` VALUES ('8766', '2018-01-08', '1');
INSERT INTO `date_info` VALUES ('8767', '2018-01-09', '1');
INSERT INTO `date_info` VALUES ('8768', '2018-01-10', '1');
INSERT INTO `date_info` VALUES ('8769', '2018-01-11', '1');
INSERT INTO `date_info` VALUES ('8770', '2018-01-12', '1');
INSERT INTO `date_info` VALUES ('8771', '2018-01-13', '0');
INSERT INTO `date_info` VALUES ('8772', '2018-01-14', '0');
INSERT INTO `date_info` VALUES ('8773', '2018-01-15', '1');
INSERT INTO `date_info` VALUES ('8774', '2018-01-16', '1');
INSERT INTO `date_info` VALUES ('8775', '2018-01-17', '1');
INSERT INTO `date_info` VALUES ('8776', '2018-01-18', '1');
INSERT INTO `date_info` VALUES ('8777', '2018-01-19', '1');
INSERT INTO `date_info` VALUES ('8778', '2018-01-20', '0');
INSERT INTO `date_info` VALUES ('8779', '2018-01-21', '0');
INSERT INTO `date_info` VALUES ('8780', '2018-01-22', '1');
INSERT INTO `date_info` VALUES ('8781', '2018-01-23', '1');
INSERT INTO `date_info` VALUES ('8782', '2018-01-24', '1');
INSERT INTO `date_info` VALUES ('8783', '2018-01-25', '1');
INSERT INTO `date_info` VALUES ('8784', '2018-01-26', '1');
INSERT INTO `date_info` VALUES ('8785', '2018-01-27', '0');
INSERT INTO `date_info` VALUES ('8786', '2018-01-28', '0');
INSERT INTO `date_info` VALUES ('8787', '2018-01-29', '1');
INSERT INTO `date_info` VALUES ('8788', '2018-01-30', '1');
INSERT INTO `date_info` VALUES ('8789', '2018-01-31', '1');
INSERT INTO `date_info` VALUES ('8790', '2018-02-01', '1');
INSERT INTO `date_info` VALUES ('8791', '2018-02-02', '1');
INSERT INTO `date_info` VALUES ('8792', '2018-02-03', '0');
INSERT INTO `date_info` VALUES ('8793', '2018-02-04', '0');
INSERT INTO `date_info` VALUES ('8794', '2018-02-05', '1');
INSERT INTO `date_info` VALUES ('8795', '2018-02-06', '1');
INSERT INTO `date_info` VALUES ('8796', '2018-02-07', '1');
INSERT INTO `date_info` VALUES ('8797', '2018-02-08', '1');
INSERT INTO `date_info` VALUES ('8798', '2018-02-09', '1');
INSERT INTO `date_info` VALUES ('8799', '2018-02-10', '0');
INSERT INTO `date_info` VALUES ('8800', '2018-02-11', '0');
INSERT INTO `date_info` VALUES ('8801', '2018-02-12', '1');
INSERT INTO `date_info` VALUES ('8802', '2018-02-13', '1');
INSERT INTO `date_info` VALUES ('8803', '2018-02-14', '1');
INSERT INTO `date_info` VALUES ('8804', '2018-02-15', '1');
INSERT INTO `date_info` VALUES ('8805', '2018-02-16', '1');
INSERT INTO `date_info` VALUES ('8806', '2018-02-17', '0');
INSERT INTO `date_info` VALUES ('8807', '2018-02-18', '0');
INSERT INTO `date_info` VALUES ('8808', '2018-02-19', '1');
INSERT INTO `date_info` VALUES ('8809', '2018-02-20', '1');
INSERT INTO `date_info` VALUES ('8810', '2018-02-21', '1');
INSERT INTO `date_info` VALUES ('8811', '2018-02-22', '1');
INSERT INTO `date_info` VALUES ('8812', '2018-02-23', '1');
INSERT INTO `date_info` VALUES ('8813', '2018-02-24', '0');
INSERT INTO `date_info` VALUES ('8814', '2018-02-25', '0');
INSERT INTO `date_info` VALUES ('8815', '2018-02-26', '1');
INSERT INTO `date_info` VALUES ('8816', '2018-02-27', '1');
INSERT INTO `date_info` VALUES ('8817', '2018-02-28', '1');
INSERT INTO `date_info` VALUES ('8818', '2018-03-01', '1');
INSERT INTO `date_info` VALUES ('8819', '2018-03-02', '1');
INSERT INTO `date_info` VALUES ('8820', '2018-03-03', '0');
INSERT INTO `date_info` VALUES ('8821', '2018-03-04', '0');
INSERT INTO `date_info` VALUES ('8822', '2018-03-05', '1');
INSERT INTO `date_info` VALUES ('8823', '2018-03-06', '1');
INSERT INTO `date_info` VALUES ('8824', '2018-03-07', '1');
INSERT INTO `date_info` VALUES ('8825', '2018-03-08', '1');
INSERT INTO `date_info` VALUES ('8826', '2018-03-09', '1');
INSERT INTO `date_info` VALUES ('8827', '2018-03-10', '0');
INSERT INTO `date_info` VALUES ('8828', '2018-03-11', '0');
INSERT INTO `date_info` VALUES ('8829', '2018-03-12', '1');
INSERT INTO `date_info` VALUES ('8830', '2018-03-13', '1');
INSERT INTO `date_info` VALUES ('8831', '2018-03-14', '1');
INSERT INTO `date_info` VALUES ('8832', '2018-03-15', '1');
INSERT INTO `date_info` VALUES ('8833', '2018-03-16', '1');
INSERT INTO `date_info` VALUES ('8834', '2018-03-17', '0');
INSERT INTO `date_info` VALUES ('8835', '2018-03-18', '0');
INSERT INTO `date_info` VALUES ('8836', '2018-03-19', '1');
INSERT INTO `date_info` VALUES ('8837', '2018-03-20', '1');
INSERT INTO `date_info` VALUES ('8838', '2018-03-21', '1');
INSERT INTO `date_info` VALUES ('8839', '2018-03-22', '1');
INSERT INTO `date_info` VALUES ('8840', '2018-03-23', '1');
INSERT INTO `date_info` VALUES ('8841', '2018-03-24', '0');
INSERT INTO `date_info` VALUES ('8842', '2018-03-25', '0');
INSERT INTO `date_info` VALUES ('8843', '2018-03-26', '1');
INSERT INTO `date_info` VALUES ('8844', '2018-03-27', '1');
INSERT INTO `date_info` VALUES ('8845', '2018-03-28', '1');
INSERT INTO `date_info` VALUES ('8846', '2018-03-29', '1');
INSERT INTO `date_info` VALUES ('8847', '2018-03-30', '1');
INSERT INTO `date_info` VALUES ('8848', '2018-03-31', '0');
INSERT INTO `date_info` VALUES ('8849', '2018-04-01', '0');
INSERT INTO `date_info` VALUES ('8850', '2018-04-02', '1');
INSERT INTO `date_info` VALUES ('8851', '2018-04-03', '1');
INSERT INTO `date_info` VALUES ('8852', '2018-04-04', '1');
INSERT INTO `date_info` VALUES ('8853', '2018-04-05', '1');
INSERT INTO `date_info` VALUES ('8854', '2018-04-06', '1');
INSERT INTO `date_info` VALUES ('8855', '2018-04-07', '0');
INSERT INTO `date_info` VALUES ('8856', '2018-04-08', '0');
INSERT INTO `date_info` VALUES ('8857', '2018-04-09', '1');
INSERT INTO `date_info` VALUES ('8858', '2018-04-10', '1');
INSERT INTO `date_info` VALUES ('8859', '2018-04-11', '1');
INSERT INTO `date_info` VALUES ('8860', '2018-04-12', '1');
INSERT INTO `date_info` VALUES ('8861', '2018-04-13', '1');
INSERT INTO `date_info` VALUES ('8862', '2018-04-14', '0');
INSERT INTO `date_info` VALUES ('8863', '2018-04-15', '0');
INSERT INTO `date_info` VALUES ('8864', '2018-04-16', '1');
INSERT INTO `date_info` VALUES ('8865', '2018-04-17', '1');
INSERT INTO `date_info` VALUES ('8866', '2018-04-18', '1');
INSERT INTO `date_info` VALUES ('8867', '2018-04-19', '1');
INSERT INTO `date_info` VALUES ('8868', '2018-04-20', '1');
INSERT INTO `date_info` VALUES ('8869', '2018-04-21', '0');
INSERT INTO `date_info` VALUES ('8870', '2018-04-22', '0');
INSERT INTO `date_info` VALUES ('8871', '2018-04-23', '1');
INSERT INTO `date_info` VALUES ('8872', '2018-04-24', '1');
INSERT INTO `date_info` VALUES ('8873', '2018-04-25', '1');
INSERT INTO `date_info` VALUES ('8874', '2018-04-26', '1');
INSERT INTO `date_info` VALUES ('8875', '2018-04-27', '1');
INSERT INTO `date_info` VALUES ('8876', '2018-04-28', '0');
INSERT INTO `date_info` VALUES ('8877', '2018-04-29', '0');
INSERT INTO `date_info` VALUES ('8878', '2018-04-30', '1');
INSERT INTO `date_info` VALUES ('8879', '2018-05-01', '1');
INSERT INTO `date_info` VALUES ('8880', '2018-05-02', '1');
INSERT INTO `date_info` VALUES ('8881', '2018-05-03', '1');
INSERT INTO `date_info` VALUES ('8882', '2018-05-04', '1');
INSERT INTO `date_info` VALUES ('8883', '2018-05-05', '0');
INSERT INTO `date_info` VALUES ('8884', '2018-05-06', '0');
INSERT INTO `date_info` VALUES ('8885', '2018-05-07', '1');
INSERT INTO `date_info` VALUES ('8886', '2018-05-08', '1');
INSERT INTO `date_info` VALUES ('8887', '2018-05-09', '1');
INSERT INTO `date_info` VALUES ('8888', '2018-05-10', '1');
INSERT INTO `date_info` VALUES ('8889', '2018-05-11', '1');
INSERT INTO `date_info` VALUES ('8890', '2018-05-12', '0');
INSERT INTO `date_info` VALUES ('8891', '2018-05-13', '0');
INSERT INTO `date_info` VALUES ('8892', '2018-05-14', '1');
INSERT INTO `date_info` VALUES ('8893', '2018-05-15', '1');
INSERT INTO `date_info` VALUES ('8894', '2018-05-16', '1');
INSERT INTO `date_info` VALUES ('8895', '2018-05-17', '1');
INSERT INTO `date_info` VALUES ('8896', '2018-05-18', '1');
INSERT INTO `date_info` VALUES ('8897', '2018-05-19', '0');
INSERT INTO `date_info` VALUES ('8898', '2018-05-20', '0');
INSERT INTO `date_info` VALUES ('8899', '2018-05-21', '1');
INSERT INTO `date_info` VALUES ('8900', '2018-05-22', '1');
INSERT INTO `date_info` VALUES ('8901', '2018-05-23', '1');
INSERT INTO `date_info` VALUES ('8902', '2018-05-24', '1');
INSERT INTO `date_info` VALUES ('8903', '2018-05-25', '1');
INSERT INTO `date_info` VALUES ('8904', '2018-05-26', '0');
INSERT INTO `date_info` VALUES ('8905', '2018-05-27', '0');
INSERT INTO `date_info` VALUES ('8906', '2018-05-28', '1');
INSERT INTO `date_info` VALUES ('8907', '2018-05-29', '1');
INSERT INTO `date_info` VALUES ('8908', '2018-05-30', '1');
INSERT INTO `date_info` VALUES ('8909', '2018-05-31', '1');
INSERT INTO `date_info` VALUES ('8910', '2018-06-01', '1');
INSERT INTO `date_info` VALUES ('8911', '2018-06-02', '0');
INSERT INTO `date_info` VALUES ('8912', '2018-06-03', '0');
INSERT INTO `date_info` VALUES ('8913', '2018-06-04', '1');
INSERT INTO `date_info` VALUES ('8914', '2018-06-05', '1');
INSERT INTO `date_info` VALUES ('8915', '2018-06-06', '1');
INSERT INTO `date_info` VALUES ('8916', '2018-06-07', '1');
INSERT INTO `date_info` VALUES ('8917', '2018-06-08', '1');
INSERT INTO `date_info` VALUES ('8918', '2018-06-09', '0');
INSERT INTO `date_info` VALUES ('8919', '2018-06-10', '0');
INSERT INTO `date_info` VALUES ('8920', '2018-06-11', '1');
INSERT INTO `date_info` VALUES ('8921', '2018-06-12', '1');
INSERT INTO `date_info` VALUES ('8922', '2018-06-13', '1');
INSERT INTO `date_info` VALUES ('8923', '2018-06-14', '1');
INSERT INTO `date_info` VALUES ('8924', '2018-06-15', '1');
INSERT INTO `date_info` VALUES ('8925', '2018-06-16', '0');
INSERT INTO `date_info` VALUES ('8926', '2018-06-17', '0');
INSERT INTO `date_info` VALUES ('8927', '2018-06-18', '1');
INSERT INTO `date_info` VALUES ('8928', '2018-06-19', '1');
INSERT INTO `date_info` VALUES ('8929', '2018-06-20', '1');
INSERT INTO `date_info` VALUES ('8930', '2018-06-21', '1');
INSERT INTO `date_info` VALUES ('8931', '2018-06-22', '1');
INSERT INTO `date_info` VALUES ('8932', '2018-06-23', '0');
INSERT INTO `date_info` VALUES ('8933', '2018-06-24', '0');
INSERT INTO `date_info` VALUES ('8934', '2018-06-25', '1');
INSERT INTO `date_info` VALUES ('8935', '2018-06-26', '1');
INSERT INTO `date_info` VALUES ('8936', '2018-06-27', '1');
INSERT INTO `date_info` VALUES ('8937', '2018-06-28', '1');
INSERT INTO `date_info` VALUES ('8938', '2018-06-29', '1');
INSERT INTO `date_info` VALUES ('8939', '2018-06-30', '0');
INSERT INTO `date_info` VALUES ('8940', '2018-07-01', '0');
INSERT INTO `date_info` VALUES ('8941', '2018-07-02', '1');
INSERT INTO `date_info` VALUES ('8942', '2018-07-03', '1');
INSERT INTO `date_info` VALUES ('8943', '2018-07-04', '1');
INSERT INTO `date_info` VALUES ('8944', '2018-07-05', '1');
INSERT INTO `date_info` VALUES ('8945', '2018-07-06', '1');
INSERT INTO `date_info` VALUES ('8946', '2018-07-07', '0');
INSERT INTO `date_info` VALUES ('8947', '2018-07-08', '0');
INSERT INTO `date_info` VALUES ('8948', '2018-07-09', '1');
INSERT INTO `date_info` VALUES ('8949', '2018-07-10', '1');
INSERT INTO `date_info` VALUES ('8950', '2018-07-11', '1');
INSERT INTO `date_info` VALUES ('8951', '2018-07-12', '1');
INSERT INTO `date_info` VALUES ('8952', '2018-07-13', '1');
INSERT INTO `date_info` VALUES ('8953', '2018-07-14', '0');
INSERT INTO `date_info` VALUES ('8954', '2018-07-15', '0');
INSERT INTO `date_info` VALUES ('8955', '2018-07-16', '1');
INSERT INTO `date_info` VALUES ('8956', '2018-07-17', '1');
INSERT INTO `date_info` VALUES ('8957', '2018-07-18', '1');
INSERT INTO `date_info` VALUES ('8958', '2018-07-19', '1');
INSERT INTO `date_info` VALUES ('8959', '2018-07-20', '1');
INSERT INTO `date_info` VALUES ('8960', '2018-07-21', '0');
INSERT INTO `date_info` VALUES ('8961', '2018-07-22', '0');
INSERT INTO `date_info` VALUES ('8962', '2018-07-23', '1');
INSERT INTO `date_info` VALUES ('8963', '2018-07-24', '1');
INSERT INTO `date_info` VALUES ('8964', '2018-07-25', '1');
INSERT INTO `date_info` VALUES ('8965', '2018-07-26', '1');
INSERT INTO `date_info` VALUES ('8966', '2018-07-27', '1');
INSERT INTO `date_info` VALUES ('8967', '2018-07-28', '0');
INSERT INTO `date_info` VALUES ('8968', '2018-07-29', '0');
INSERT INTO `date_info` VALUES ('8969', '2018-07-30', '1');
INSERT INTO `date_info` VALUES ('8970', '2018-07-31', '1');
INSERT INTO `date_info` VALUES ('8971', '2018-08-01', '1');
INSERT INTO `date_info` VALUES ('8972', '2018-08-02', '1');
INSERT INTO `date_info` VALUES ('8973', '2018-08-03', '1');
INSERT INTO `date_info` VALUES ('8974', '2018-08-04', '0');
INSERT INTO `date_info` VALUES ('8975', '2018-08-05', '0');
INSERT INTO `date_info` VALUES ('8976', '2018-08-06', '1');
INSERT INTO `date_info` VALUES ('8977', '2018-08-07', '1');
INSERT INTO `date_info` VALUES ('8978', '2018-08-08', '1');
INSERT INTO `date_info` VALUES ('8979', '2018-08-09', '1');
INSERT INTO `date_info` VALUES ('8980', '2018-08-10', '1');
INSERT INTO `date_info` VALUES ('8981', '2018-08-11', '0');
INSERT INTO `date_info` VALUES ('8982', '2018-08-12', '0');
INSERT INTO `date_info` VALUES ('8983', '2018-08-13', '1');
INSERT INTO `date_info` VALUES ('8984', '2018-08-14', '1');
INSERT INTO `date_info` VALUES ('8985', '2018-08-15', '1');
INSERT INTO `date_info` VALUES ('8986', '2018-08-16', '1');
INSERT INTO `date_info` VALUES ('8987', '2018-08-17', '1');
INSERT INTO `date_info` VALUES ('8988', '2018-08-18', '0');
INSERT INTO `date_info` VALUES ('8989', '2018-08-19', '0');
INSERT INTO `date_info` VALUES ('8990', '2018-08-20', '1');
INSERT INTO `date_info` VALUES ('8991', '2018-08-21', '1');
INSERT INTO `date_info` VALUES ('8992', '2018-08-22', '1');
INSERT INTO `date_info` VALUES ('8993', '2018-08-23', '1');
INSERT INTO `date_info` VALUES ('8994', '2018-08-24', '1');
INSERT INTO `date_info` VALUES ('8995', '2018-08-25', '0');
INSERT INTO `date_info` VALUES ('8996', '2018-08-26', '0');
INSERT INTO `date_info` VALUES ('8997', '2018-08-27', '1');
INSERT INTO `date_info` VALUES ('8998', '2018-08-28', '1');
INSERT INTO `date_info` VALUES ('8999', '2018-08-29', '1');
INSERT INTO `date_info` VALUES ('9000', '2018-08-30', '1');
INSERT INTO `date_info` VALUES ('9001', '2018-08-31', '1');
INSERT INTO `date_info` VALUES ('9002', '2018-09-01', '0');
INSERT INTO `date_info` VALUES ('9003', '2018-09-02', '0');
INSERT INTO `date_info` VALUES ('9004', '2018-09-03', '1');
INSERT INTO `date_info` VALUES ('9005', '2018-09-04', '1');
INSERT INTO `date_info` VALUES ('9006', '2018-09-05', '1');
INSERT INTO `date_info` VALUES ('9007', '2018-09-06', '1');
INSERT INTO `date_info` VALUES ('9008', '2018-09-07', '1');
INSERT INTO `date_info` VALUES ('9009', '2018-09-08', '0');
INSERT INTO `date_info` VALUES ('9010', '2018-09-09', '0');
INSERT INTO `date_info` VALUES ('9011', '2018-09-10', '1');
INSERT INTO `date_info` VALUES ('9012', '2018-09-11', '1');
INSERT INTO `date_info` VALUES ('9013', '2018-09-12', '1');
INSERT INTO `date_info` VALUES ('9014', '2018-09-13', '1');
INSERT INTO `date_info` VALUES ('9015', '2018-09-14', '1');
INSERT INTO `date_info` VALUES ('9016', '2018-09-15', '0');
INSERT INTO `date_info` VALUES ('9017', '2018-09-16', '0');
INSERT INTO `date_info` VALUES ('9018', '2018-09-17', '1');
INSERT INTO `date_info` VALUES ('9019', '2018-09-18', '1');
INSERT INTO `date_info` VALUES ('9020', '2018-09-19', '1');
INSERT INTO `date_info` VALUES ('9021', '2018-09-20', '1');
INSERT INTO `date_info` VALUES ('9022', '2018-09-21', '1');
INSERT INTO `date_info` VALUES ('9023', '2018-09-22', '0');
INSERT INTO `date_info` VALUES ('9024', '2018-09-23', '0');
INSERT INTO `date_info` VALUES ('9025', '2018-09-24', '1');
INSERT INTO `date_info` VALUES ('9026', '2018-09-25', '1');
INSERT INTO `date_info` VALUES ('9027', '2018-09-26', '1');
INSERT INTO `date_info` VALUES ('9028', '2018-09-27', '1');
INSERT INTO `date_info` VALUES ('9029', '2018-09-28', '1');
INSERT INTO `date_info` VALUES ('9030', '2018-09-29', '0');
INSERT INTO `date_info` VALUES ('9031', '2018-09-30', '0');
INSERT INTO `date_info` VALUES ('9032', '2018-10-01', '1');
INSERT INTO `date_info` VALUES ('9033', '2018-10-02', '1');
INSERT INTO `date_info` VALUES ('9034', '2018-10-03', '1');
INSERT INTO `date_info` VALUES ('9035', '2018-10-04', '1');
INSERT INTO `date_info` VALUES ('9036', '2018-10-05', '1');
INSERT INTO `date_info` VALUES ('9037', '2018-10-06', '0');
INSERT INTO `date_info` VALUES ('9038', '2018-10-07', '0');
INSERT INTO `date_info` VALUES ('9039', '2018-10-08', '1');
INSERT INTO `date_info` VALUES ('9040', '2018-10-09', '1');
INSERT INTO `date_info` VALUES ('9041', '2018-10-10', '1');
INSERT INTO `date_info` VALUES ('9042', '2018-10-11', '1');
INSERT INTO `date_info` VALUES ('9043', '2018-10-12', '1');
INSERT INTO `date_info` VALUES ('9044', '2018-10-13', '0');
INSERT INTO `date_info` VALUES ('9045', '2018-10-14', '0');
INSERT INTO `date_info` VALUES ('9046', '2018-10-15', '1');
INSERT INTO `date_info` VALUES ('9047', '2018-10-16', '1');
INSERT INTO `date_info` VALUES ('9048', '2018-10-17', '1');
INSERT INTO `date_info` VALUES ('9049', '2018-10-18', '1');
INSERT INTO `date_info` VALUES ('9050', '2018-10-19', '1');
INSERT INTO `date_info` VALUES ('9051', '2018-10-20', '0');
INSERT INTO `date_info` VALUES ('9052', '2018-10-21', '0');
INSERT INTO `date_info` VALUES ('9053', '2018-10-22', '1');
INSERT INTO `date_info` VALUES ('9054', '2018-10-23', '1');
INSERT INTO `date_info` VALUES ('9055', '2018-10-24', '1');
INSERT INTO `date_info` VALUES ('9056', '2018-10-25', '1');
INSERT INTO `date_info` VALUES ('9057', '2018-10-26', '1');
INSERT INTO `date_info` VALUES ('9058', '2018-10-27', '0');
INSERT INTO `date_info` VALUES ('9059', '2018-10-28', '0');
INSERT INTO `date_info` VALUES ('9060', '2018-10-29', '1');
INSERT INTO `date_info` VALUES ('9061', '2018-10-30', '1');
INSERT INTO `date_info` VALUES ('9062', '2018-10-31', '1');
INSERT INTO `date_info` VALUES ('9063', '2018-11-01', '1');
INSERT INTO `date_info` VALUES ('9064', '2018-11-02', '1');
INSERT INTO `date_info` VALUES ('9065', '2018-11-03', '0');
INSERT INTO `date_info` VALUES ('9066', '2018-11-04', '0');
INSERT INTO `date_info` VALUES ('9067', '2018-11-05', '1');
INSERT INTO `date_info` VALUES ('9068', '2018-11-06', '1');
INSERT INTO `date_info` VALUES ('9069', '2018-11-07', '1');
INSERT INTO `date_info` VALUES ('9070', '2018-11-08', '1');
INSERT INTO `date_info` VALUES ('9071', '2018-11-09', '1');
INSERT INTO `date_info` VALUES ('9072', '2018-11-10', '0');
INSERT INTO `date_info` VALUES ('9073', '2018-11-11', '0');
INSERT INTO `date_info` VALUES ('9074', '2018-11-12', '1');
INSERT INTO `date_info` VALUES ('9075', '2018-11-13', '1');
INSERT INTO `date_info` VALUES ('9076', '2018-11-14', '1');






