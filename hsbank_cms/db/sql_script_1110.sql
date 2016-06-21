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
   introduce            varchar(2048) comment '说明',
   duration             varchar(20) comment '期限',
   investment_scope     text comment '投标范围',
   buy_rule             text comment '购买规则',
   interest_calculate_rule text comment '收益计算规则',
   safe_description     text comment '保障方式',
   fee_description      text comment '费用说明',
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

delete from current_account_summary;
insert into current_account_summary
(account_id,total_investment_money,total_get_interest,total_redeem_principal,total_redeem_interest,current_principal,create_dt)
select account_id,0 as 'total_investment_money',0 as 'total_get_interest',0 as 'total_redeem_principal',0 as 'total_redeem_interest',0 as 'current_principal',now() as 'create_dt'
from customer_account where status_code = '0';

drop table if exists project_operation_summary;

/*==============================================================*/
/* Table: project_operation_summary                             */
/*==============================================================*/
create table project_operation_summary
(
   id                   bigint not null auto_increment comment '记录编号',
   finance_amount       decimal(13,2) comment '累计募资额',
   repay_principal      decimal(13,2) comment '累计偿还本金',
   repay_interest       decimal(13,2) comment '累计偿还利息',
   xin_finance_amount   decimal(13,2) comment '新花生累计募资额',
   xin_repay_principal  decimal(13,2) comment '新花生累计偿还本金',
   xin_repay_interest   decimal(13,2) comment '新花生累计偿还利息',
   yue_finance_amount   decimal(13,2) comment '月花生累计募资额',
   yue_repay_principal  decimal(13,2) comment '月花生累计偿还本金',
   yue_repay_interest   decimal(13,2) comment '月花生累计偿还利息',
   shuangyue_finance_amount decimal(13,2) comment '双月花生累计募资额',
   shuangyue_repay_principal decimal(13,2) comment '双月花生累计偿还本金',
   shuangyue_repay_interest decimal(13,2) comment '双月花生累计偿还利息',
   ji_finance_amount    decimal(13,2) comment '季花生累计募资额',
   ji_repay_principal   decimal(13,2) comment '季花生累计偿还本金',
   ji_repay_interest    decimal(13,2) comment '季花生累计偿还利息',
   shuangji_finance_amount decimal(13,2) comment '双季花生累计募资额',
   shuangji_repay_principal decimal(13,2) comment '双季花生累计偿还本金',
   shuangeji_repay_interest decimal(13,2) comment '双季花生累计偿还利息',
   nian_finance_amount  decimal(13,2) comment '年花生累计募资额',
   nian_repay_principal decimal(13,2) comment '年花生累计偿还本金',
   nian_repay_interest  decimal(13,2) comment '年花生累计偿还利息',
   date                 date comment '日期',
   primary key (id)
);

alter table project_operation_summary comment '项目运营数据汇总';

delete from sys_dict where id = '0d941d84fdb04089949de7b352a25417';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0d941d84fdb04089949de7b352a25417', '4', '一月活动投资排行', 'investment_rank_type_dict', '投资排行类型', '80', '0', '1', '2015-12-28 13:00:02', '1', '2015-12-28 13:00:30', '', '0');

/*==============================================================*/
/* 增加: 项目运营数据汇总菜单											*/
/*==============================================================*/
 
delete from sys_menu where id='9c6011a70ad34927840a089d5b5f4a76';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9c6011a70ad34927840a089d5b5f4a76', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '运营数据汇总', '120', '/operation/projectOperationSummary', '', 'icon-folder-open', '1', 'operation:projectOperationSummary:view', '1', '2015-12-25 15:50:35', '1', '2015-12-25 16:01:20', '', '0');
delete from sys_role_menu where menu_id='9c6011a70ad34927840a089d5b5f4a76';
INSERT INTO `sys_role_menu` VALUES ('1', '9c6011a70ad34927840a089d5b5f4a76');
/*==============================================================*/
/* 增加: 项目运营数据汇总菜单更新											*/
/*==============================================================*/
delete from sys_menu where id='0092882d07794049904893aee078ec36';
delete from sys_menu where id='526880b97e1c4c61ba1df684b014e1d5';
delete from sys_menu where id='c4645e3af7e748e2b976665e4c220692';
delete from sys_menu where id='f7fc5b48354d498e953700bf55e7ae62';
delete from sys_menu where id='71c71f827a7b44198bc49d459e00e9a5';
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0092882d07794049904893aee078ec36', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '运营数据查询', '30', '/operation/operationData/list', '', 'icon-bullhorn', '1', 'operation:operationData:view', '1', '2015-12-01 14:57:21', '1', '2015-12-09 14:00:18', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('526880b97e1c4c61ba1df684b014e1d5', '1', '0,1,', '运营数据', '5060', '', '', '', '1', '', '1', '2015-12-01 13:43:55', '1', '2015-12-01 13:46:29', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c4645e3af7e748e2b976665e4c220692', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '项目运营数据', '60', '/operation/projectOperation/list', '', 'icon-pencil', '1', 'operation:projectOperation:view,project:projectRepaymentPlan:edit', '1', '2015-12-01 15:58:18', '1', '2015-12-25 15:49:36', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f7fc5b48354d498e953700bf55e7ae62', '526880b97e1c4c61ba1df684b014e1d5', '0,1,526880b97e1c4c61ba1df684b014e1d5,', '运营数据', '30', '', '', '', '1', '', '1', '2015-12-01 13:45:43', '1', '2015-12-01 14:54:35', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('71c71f827a7b44198bc49d459e00e9a5', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '项目还款计划', '90', '/operation/projectOperation/projectRepayPlanList', '', 'icon-align-right', '1', '', '1', '2015-12-01 15:59:18', '1', '2015-12-25 15:50:09', '', '0');

/*==============================================================*/
/*	增加：运营数据菜单及其子菜单项权限  更新                                                                                                                    	    */
/*==============================================================*/
delete  FROM sys_role_menu where menu_id in ('0092882d07794049904893aee078ec36','526880b97e1c4c61ba1df684b014e1d5','c4645e3af7e748e2b976665e4c220692','f7fc5b48354d498e953700bf55e7ae62','71c71f827a7b44198bc49d459e00e9a5');
INSERT INTO `sys_role_menu` VALUES ('1', '0092882d07794049904893aee078ec36');
INSERT INTO `sys_role_menu` VALUES ('1', '526880b97e1c4c61ba1df684b014e1d5');
INSERT INTO `sys_role_menu` VALUES ('1', 'c4645e3af7e748e2b976665e4c220692');
INSERT INTO `sys_role_menu` VALUES ('1', 'f7fc5b48354d498e953700bf55e7ae62');
INSERT INTO `sys_role_menu` VALUES ('1', '71c71f827a7b44198bc49d459e00e9a5');

ALTER TABLE `customer_bank_card_his`
ADD COLUMN `change_dt`  datetime NULL COMMENT '变更时间' AFTER `op_term_code`;

ALTER TABLE `customer_bank_card`
ADD COLUMN `unbind_request_no`  varchar(100) NULL COMMENT '解绑请求流水号' AFTER `request_no`;

