drop table if exists project_trans_error_record;

/*==============================================================*/
/* Table: project_trans_error_record                            */
/*==============================================================*/
create table project_trans_error_record
(
   id                   bigint not null auto_increment comment 'id',
   account_id           bigint comment '账号编号',
   biz_type             varchar(20) comment '业务类型',
   third_party_seq      varchar(100) comment '第三方交易号',
   third_party_result   varchar(1500) comment '返回报文',
   status               varchar(2) comment '状态',
   create_dt            datetime comment '创建时间',
   modify_dt            datetime comment '修改时间',
   primary key (id)
);

alter table project_trans_error_record comment '异常交易记录';

/*==============================================================*/
/*	添加:异常交易菜单及权限                                                                                                                       		*/
/*==============================================================*/
delete from sys_menu where id in ('8378cdffadd14209adc88d4d98df0d8b','bed025d82dfa4f258d78df718851ccff','ec3fe04a62474ce9adc38630eaf5c045');
INSERT INTO `sys_menu` VALUES ('8378cdffadd14209adc88d4d98df0d8b', 'ec3fe04a62474ce9adc38630eaf5c045', '0,1,c4391fe4778142a7a5938fcf30c01fbd,a290e331b0b047d5b1af471f66d4244b,ec3fe04a62474ce9adc38630eaf5c045,', '查看', 60, '', '', '', '0', 'project:projectTransErrorRecord:view', '1', '2016-3-3 11:30:33', '1', '2016-3-3 11:30:33', '查看', '0');
INSERT INTO `sys_menu` VALUES ('bed025d82dfa4f258d78df718851ccff', 'ec3fe04a62474ce9adc38630eaf5c045', '0,1,c4391fe4778142a7a5938fcf30c01fbd,a290e331b0b047d5b1af471f66d4244b,ec3fe04a62474ce9adc38630eaf5c045,', '编辑', 30, '', '', '', '0', 'project:projectTransErrorRecord:edit', '1', '2016-3-3 11:29:57', '1', '2016-3-3 11:29:57', '编辑', '0');
INSERT INTO `sys_menu` VALUES ('ec3fe04a62474ce9adc38630eaf5c045', 'a290e331b0b047d5b1af471f66d4244b', '0,1,c4391fe4778142a7a5938fcf30c01fbd,a290e331b0b047d5b1af471f66d4244b,', '异常交易', 60, '/project/projectTransErrorRecord/list', '', 'icon-bolt', '1', '', '1', '2016-3-3 11:29:07', '1', '2016-3-3 11:29:07', '', '0');
delete from sys_role_menu where menu_id in ('8378cdffadd14209adc88d4d98df0d8b','bed025d82dfa4f258d78df718851ccff','ec3fe04a62474ce9adc38630eaf5c045');
INSERT INTO `sys_role_menu` VALUES ('1', '8378cdffadd14209adc88d4d98df0d8b');
INSERT INTO `sys_role_menu` VALUES ('1', 'bed025d82dfa4f258d78df718851ccff');
INSERT INTO `sys_role_menu` VALUES ('1', 'ec3fe04a62474ce9adc38630eaf5c045');

/*==============================================================*/
/*	修改:更改发送渠道 (不可重复执行)                                                                                                                      		*/
/*==============================================================*/
update customer_push_switch set push_channel='3' where push_channel='2';
update customer_push_switch set push_channel='2' where push_channel='1';

/*==============================================================*/
/*	添加会员投资额度分布菜单及权限                                                                                                                         */
/*==============================================================*/
delete from sys_menu where id in ('379c96eca788469bb93b646b9de40813');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('379c96eca788469bb93b646b9de40813', '73ece50b959f41c59a90a1d3951c24e8', '0,1,c4391fe4778142a7a5938fcf30c01fbd,73ece50b959f41c59a90a1d3951c24e8,', '会员投资额度分布', '5150', '/operation/operationData/customerInvestmentAmountDistributionList', '', 'icon-asterisk', '1', 'operation:operationData:customerInvestmentAmountDistribution', '1', '2016-02-25 14:28:22', '1', '2016-02-25 14:34:30', '', '0');
delete from sys_role_menu where menu_id in ('379c96eca788469bb93b646b9de40813');
INSERT INTO `sys_role_menu` VALUES ('1', '379c96eca788469bb93b646b9de40813');



/*==============================================================*/
/*	integral_mall_product添加产品面值列                                                                                                                     */
/*==============================================================*/
ALTER TABLE integral_mall_product ADD product_par_value int(11) DEFAULT NULL COMMENT '产品面值';

/*==============================================================*/
/*	删除类型为integral_mall_order_status_dict，lable为  删除 的字典项                                                                                                                  */
/*==============================================================*/
delete FROM sys_dict where type='integral_mall_order_status_dict' and label='删除';


/*==============================================================*/
/*	在数据库表operation_data中增加   首次投资次数investment_fist_number、首次投资额度investment_fist_total_amount、
          复投人数repeated_investment_number、复投额度repeated_investment_total_amount                                                                                                             */
/*==============================================================*/
ALTER TABLE `operation_data`
ADD COLUMN `investment_fist_number`  int(11) NULL DEFAULT 0 COMMENT '首次投资次数' AFTER `bind_bank_card_count`;
ALTER TABLE `operation_data`
ADD COLUMN `investment_fist_total_amount`  decimal(11,2) NULL DEFAULT 0 COMMENT '首次投资额度' AFTER `investment_fist_number`;
ALTER TABLE `operation_data`
ADD COLUMN `repeated_investment_number`  varchar(11) NULL DEFAULT 0 COMMENT '复投次数' AFTER `investment_fist_total_amount`;
ALTER TABLE `operation_data`
ADD COLUMN `repeated_investment_total_amount`  decimal(11,2) NULL DEFAULT 0 COMMENT '复投额度' AFTER `repeated_investment_number`;


/*==============================================================*/
/*	marketing_award_type_dict   投资券 改为             现金券                                                                                                  */
/*==============================================================*/
update  sys_dict set label='现金券' where label='投资券' and type="marketing_award_type_dict"


/*==============================================================*/
/*	新建marketing_product_type_dict字典                                                                                                 */
/*==============================================================*/
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0104b020021b4c5799a880013401c0dc', '4', '免费提现次数', 'marketing_product_type_dict', '花生乐园产品类型', '30', '0', '1', '2016-03-04 14:14:15', '1', '2016-03-04 14:33:50', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('a2f271da982446f7a048a75af3e6d788', '3', '现金', 'marketing_product_type_dict', '花生乐园产品类型', '25', '0', '1', '2016-03-04 14:13:54', '1', '2016-03-04 14:34:08', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d93dd80f66a94e35a39728ca61866aae', '2', '现金券', 'marketing_product_type_dict', '花生乐园产品类型', '20', '0', '1', '2016-03-04 14:13:24', '1', '2016-03-04 14:13:24', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f3778695bdf246bfa14985dbeb41e25c', '1', '实物', 'marketing_product_type_dict', '花生乐园产品类型', '10', '0', '1', '2016-03-04 14:12:55', '1', '2016-03-04 14:12:55', '', '0');


/*======================分割线================*/
/*==============================================================*/
/*	添加 customer_free_withdraw_count_change_type_dict字典项                                                                                         */
/*==============================================================*/
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5bfbb8a3246840c793236a34606cd230', '2', '花生豆兑换', 'customer_free_withdraw_count_change_type_dict', '会员可免费提现次数变更类型', '30', '0', '1', '2016-03-09 13:59:57', '1', '2016-03-09 13:59:57', '', '0');

update message_instance set status = '-2'
where id in
(
	select id
	from (
		select a.id
		from message_instance a
		left join message b on b.id = a.message_id
			where a.status = '0' and a.message_channel = '2' and b.account_id not in (
			select account_id from customer_push_switch where message_channel = '2'
		)
		union
		select a.id
		from message_instance a
		left join message b on b.id = a.message_id
			where a.status = '0' and a.message_channel = '3' and b.account_id not in (
			select account_id from customer_push_switch where message_channel = '3'
		)
	) a
);

/*==============================================================*/
/*	添加 增加额度统计菜单项                                                                                         */
/*==============================================================*/

delete from sys_menu where id in ('5288d89cc77344de8874ec8b3068099d');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5288d89cc77344de8874ec8b3068099d', 'f7fc5b48354d498e953700bf55e7ae62', '0,1,526880b97e1c4c61ba1df684b014e1d5,f7fc5b48354d498e953700bf55e7ae62,', '日投资额统计', '150', '/operation/operationData/InvestmentStatistics', '', 'icon-th-large', '1', 'operation:operationData:InvestmentStatistics', '1', '2016-03-10 10:25:57', '1', '2016-03-10 11:20:53', '', '0');
delete from sys_role_menu where menu_id in ('5288d89cc77344de8874ec8b3068099d');
INSERT INTO `sys_role_menu` VALUES ('1', '5288d89cc77344de8874ec8b3068099d');

delete from sys_menu where id in ('0281d934e5894c77ac22e5527887a616','390bccf674d546c8a8868bc77b5933da','4a59e3fe7ddb4ba4832ec855a29af03e','54108649b7e4425d8240449133e7e356',
	'7dc65b37d3e94b2d959e08ed785ae568','9c48e7ebb72943c79057aa38c981de67','b32d22e0a78b4e98b59ebbff7494ec4f','e60ad82979ab4b9e816acd4f536c3ee2','bcca0ff1b7ea440d809eb6d63e92b931',
	'cc0e2515c6c44ae4ae1c6400bbb2ed2f','12fcde85af2647deadeefb93cb1bd2b0','78d00c36ff1245b193dfec844cb43e51','3bab59a5f32b405083fb4e772509244a');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0281d934e5894c77ac22e5527887a616', '78d00c36ff1245b193dfec844cb43e51', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,78d00c36ff1245b193dfec844cb43e51,', '总览', '30', '/loanAccount/summary', '', 'icon-beaker', '1', '', '1', '2016-03-14 15:28:50', '1', '2016-03-14 15:28:50', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('390bccf674d546c8a8868bc77b5933da', '12fcde85af2647deadeefb93cb1bd2b0', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,12fcde85af2647deadeefb93cb1bd2b0,', '项目列表', '60', '/loanAccount/current/project', '', 'icon-heart', '1', '', '1', '2016-03-14 15:37:32', '1', '2016-03-14 15:37:32', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('4a59e3fe7ddb4ba4832ec855a29af03e', '78d00c36ff1245b193dfec844cb43e51', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,78d00c36ff1245b193dfec844cb43e51,', '余额流水', '120', '/loanAccount/balance/history', '', 'icon-filter', '1', '', '1', '2016-03-14 15:32:32', '1', '2016-03-14 15:32:32', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('54108649b7e4425d8240449133e7e356', 'cc0e2515c6c44ae4ae1c6400bbb2ed2f', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,cc0e2515c6c44ae4ae1c6400bbb2ed2f,', '总览', '30', '/loanAccount/fix/summary', '', 'icon-asterisk', '1', '', '1', '2016-03-14 15:24:59', '1', '2016-03-14 15:43:20', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7dc65b37d3e94b2d959e08ed785ae568', '78d00c36ff1245b193dfec844cb43e51', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,78d00c36ff1245b193dfec844cb43e51,', '提现', '90', '/loanAccount/withdraw', '', 'icon-dashboard', '1', '', '1', '2016-03-14 15:30:13', '1', '2016-03-14 15:30:13', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9c48e7ebb72943c79057aa38c981de67', '78d00c36ff1245b193dfec844cb43e51', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,78d00c36ff1245b193dfec844cb43e51,', '充值', '60', '/loanAccount/recharge', '', 'icon-briefcase', '1', '', '1', '2016-03-14 15:29:37', '1', '2016-03-14 15:29:37', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b32d22e0a78b4e98b59ebbff7494ec4f', '12fcde85af2647deadeefb93cb1bd2b0', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,12fcde85af2647deadeefb93cb1bd2b0,', '总览', '30', '/loanAccount/current/summary', '', 'icon-edit', '1', '', '1', '2016-03-14 15:36:57', '1', '2016-03-14 15:36:57', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e60ad82979ab4b9e816acd4f536c3ee2', 'cc0e2515c6c44ae4ae1c6400bbb2ed2f', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,cc0e2515c6c44ae4ae1c6400bbb2ed2f,', '项目列表', '60', '/loanAccount/fix/project', '', 'icon-bell-alt', '1', '', '1', '2016-03-14 15:27:41', '1', '2016-03-14 15:44:04', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('bcca0ff1b7ea440d809eb6d63e92b931', '1', '0,1,', '融资账户', '5150', '', '', '', '1', '', '1', '2016-03-14 15:21:53', '1', '2016-03-14 15:21:53', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('cc0e2515c6c44ae4ae1c6400bbb2ed2f', 'bcca0ff1b7ea440d809eb6d63e92b931', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,', '固收项目', '60', '', '', '', '1', '', '1', '2016-03-14 15:23:52', '1', '2016-03-14 15:36:03', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('12fcde85af2647deadeefb93cb1bd2b0', 'bcca0ff1b7ea440d809eb6d63e92b931', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,', '活期项目', '90', '', '', '', '1', '', '1', '2016-03-14 15:36:16', '1', '2016-03-14 15:36:16', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('78d00c36ff1245b193dfec844cb43e51', 'bcca0ff1b7ea440d809eb6d63e92b931', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,', '账户管理', '30', '', '', '', '1', '', '1', '2016-03-14 15:23:02', '1', '2016-03-14 15:23:02', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3bab59a5f32b405083fb4e772509244a', '78d00c36ff1245b193dfec844cb43e51', '0,1,bcca0ff1b7ea440d809eb6d63e92b931,78d00c36ff1245b193dfec844cb43e51,', '我的银行卡', '150', '/loanAccount/bankCard', '', 'icon-credit-card', '1', '', '1', '2016-03-14 16:19:15', '1', '2016-03-14 16:19:15', '', '0');

