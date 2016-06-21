delete from marketing_behavior_type where id='13';
INSERT INTO `marketing_behavior_type` VALUES (13, '1130', '投标--完成');

drop table if exists customer_employee;

/*==============================================================*/
/* Table: customer_employee                                     */
/*==============================================================*/
create table customer_employee
(
   employee_id          bigint not null auto_increment comment '员工id',
   name                 varchar(20) comment '姓名',
   mobile               varchar(20) comment '手机号',
   position             varchar(50) comment '职位',
   remark               varchar(500) comment '备注',
   primary key (employee_id)
);

alter table customer_employee comment '员工信息';
ALTER TABLE `customer_account`
ADD COLUMN `recommender_mobile`  varchar(20) NULL COMMENT '推荐人手机号' AFTER `request_no`,
ADD COLUMN `recommender_type`  varchar(2) NULL COMMENT '推荐人类型' AFTER `recommender_mobile`;

/*==============================================================*/
/* 添加变更原因类型字典数据                                  */
/*==============================================================*/
delete from sys_dict  where type='customer_balance_change_type_dict';
INSERT INTO `sys_dict` VALUES ('1faf1cd8fd6744ccaa19fe8840f44f03', 'NET_B2B', 'B2B 网银充值', 'customer_balance_change_type_dict', '变更类型', 10, '0', '1', '2015-6-23 17:51:30', '1', '2015-10-1 12:53:39', '', '0');
INSERT INTO `sys_dict` VALUES ('4eb2f9e7e30a4fdabed7a7582caadb72', 'NET_B2C', 'B2C 网银充值', 'customer_balance_change_type_dict', '变更类型', 20, '0', '1', '2015-6-23 17:51:42', '1', '2015-10-1 12:53:46', '', '0');
INSERT INTO `sys_dict` VALUES ('11357c242ce14d23a83fdbc1b74128e9', 'A_PAY', '一键支付', 'customer_balance_change_type_dict', '变更类型', 30, '0', '1', '2015-6-23 17:51:52', '1', '2015-8-4 21:04:05', '', '0');
INSERT INTO `sys_dict` VALUES ('7a9e4206a00c4966a4dc2f59aa8751e0', 'WH_NO_CARD', '代充值', 'customer_balance_change_type_dict', '变更类型', 34, '0', '1', '2015-8-4 21:05:21', '1', '2015-8-4 21:05:58', '', '0');
INSERT INTO `sys_dict` VALUES ('75715356074f4db3bd1249ea14c1060a', 'SWIFT', '快捷充值', 'customer_balance_change_type_dict', '变更类型', 36, '0', '1', '2015-8-4 21:05:47', '1', '2015-8-4 21:05:47', '', '0');
INSERT INTO `sys_dict` VALUES ('7e688c83a6ab43f7af2e2087978ca34a', 'NORMAL', '正常提现，T+1 天到账', 'customer_balance_change_type_dict', '变更类型', 40, '0', '1', '2015-6-23 17:52:03', '1', '2015-8-4 21:06:33', '', '0');
INSERT INTO `sys_dict` VALUES ('a506b3316b37430ab33c337811c28c3c', 'URGENT', '加急提现，T+0 当日到账', 'customer_balance_change_type_dict', '变更类型', 50, '0', '1', '2015-6-23 17:52:11', '1', '2015-8-4 21:06:55', '', '0');
INSERT INTO `sys_dict` VALUES ('dc2338ea848a4be2996fbca528f75215', '1', '投资冻结', 'customer_balance_change_type_dict', '变更类型', 70, '0', '1', '2015-7-22 19:11:04', '1', '2015-8-20 09:35:11', '', '0');
INSERT INTO `sys_dict` VALUES ('9bf8a2abbb3840ab97e4b511cf81fce6', '2', '投资冻结取消', 'customer_balance_change_type_dict', '变更类型', 80, '0', '1', '2015-7-22 19:11:15', '1', '2015-8-20 09:36:09', '', '0');
INSERT INTO `sys_dict` VALUES ('3d879ac46f1a42d49c6b6ff266ad740b', '3', '投资放款', 'customer_balance_change_type_dict', '变更类型', 90, '0', '1', '2015-8-20 09:35:34', '1', '2015-8-20 09:35:34', '', '0');
INSERT INTO `sys_dict` VALUES ('5add96c701c4467ebe5f7e9354b49907', '4', '还款', 'customer_balance_change_type_dict', '变更类型', 100, '0', '1', '2015-8-20 09:35:50', '1', '2015-8-20 09:35:50', '', '0');
INSERT INTO `sys_dict` VALUES ('bd23d6b830a049b49b943b74ea4de6dd', '5', '充值获取抵用额', 'customer_balance_change_type_dict', '变更类型', 110, '0', '1', '2015-8-20 09:38:01', '1', '2015-8-20 09:38:01', '', '0');
INSERT INTO `sys_dict` VALUES ('c86bd210535842c9b145f23fcc8bcb05', '6', '好友投资返利', 'customer_balance_change_type_dict', '变更类型', 120, '0', '1', '2015-9-16 11:34:44', '1', '2015-9-16 11:34:44', '', '0');
INSERT INTO `sys_dict` VALUES ('2494c04a7e414f529cec7858243ee59d', '7', '首次充值送现金', 'customer_balance_change_type_dict', '变更类型', 130, '0', '1', '2015-9-16 14:04:16', '1', '2015-9-16 14:04:16', '', '0');
INSERT INTO `sys_dict` VALUES ('7895f9efd1d241579b90f066de1eadac', '8', '中秋国庆双节投资返利', 'customer_balance_change_type_dict', '变更类型', 140, '0', '1', '2015-9-16 15:05:09', '1', '2015-9-16 15:05:09', '', '0');
/*==============================================================*/
/* 添加字典类型：recommender_type_dict                                 */
/*==============================================================*/
delete from sys_dict  where type='recommender_type_dict';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7510dce1090849c6921c5a36acfe272e', '2', '普通用户', 'recommender_type_dict', '推荐人类型', '30', '0', '1', '2015-09-15 14:22:15', '1', '2015-09-15 15:42:28', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('c95f49eb2c294d148da5c0c7411aabfe', '3', '员工', 'recommender_type_dict', '推荐人类型', '40', '0', '1', '2015-09-15 14:22:24', '1', '2015-09-15 15:42:52', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ec1fa7957ddc412a8f680ba77aea7e35', '0', '无推荐人', 'recommender_type_dict', '推荐人类型', '10', '0', '1', '2015-09-15 15:43:07', '1', '2015-09-15 16:05:40', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('f274b1b790f440aeb87db91c1c1bcde6', '1', '未注册用户', 'recommender_type_dict', '推荐人类型', '20', '0', '1', '2015-09-15 14:22:06', '1', '2015-09-15 15:42:01', '', '0');

/*==============================================================*/
/*	增加:营销活动操作结果数据字典                                                                                                                         */
/*==============================================================*/
delete from sys_dict  where type='activity_result_code_dict';
INSERT INTO `sys_dict` VALUES ('a7decf5e79ec40068f32af080a2762f8', '0', '失败', 'activity_result_code_dict', '营销活动操作结果', 10, '0', '1', '2015-9-16 13:04:14', '1', '2015-9-16 13:04:14', '', '0');
INSERT INTO `sys_dict` VALUES ('c58e1815febb473ebd3198ab78bfcace', '1', '成功', 'activity_result_code_dict', '营销活动操作结果', 10, '0', '1', '2015-9-16 13:05:28', '1', '2015-9-16 13:05:28', '', '0');


/*==============================================================*/
/*	初始化营销活动相关数据                                                                                                                                       */
/*==============================================================*/
delete from marketing_activity_info;
INSERT INTO `marketing_activity_info` VALUES (12, '花生金服邀请好友规则', '2015-9-14', '2020-12-31', '00:00:00', '23:59:59', 'marketActivity1001Handler', '规则1：每邀请1位好友注册，好友注册并完成实名认证，邀请人可获得2张10元投资券，单笔投资满1000元即可使用5元。\r\n规则2：被邀请的好友在活动期间注册并完成交易，邀请人可以获得被邀请好友首次投资金额的1&permil;的现金奖励。（不包括购买债权转让类产品）\r\n例如：好友投资10万，您可获得：100000X1&permil;=100元(可重点突出）\r\n规则3：用户邀请满5位好友注册并完成交易（购买非债权转让类产品），用户将额外获得奖励200元投资券。（100元一张， 50元一张， 20元两张，10元一张）', 1, '2015-9-16 14:28:56', 1, '2015-9-16 14:29:56', '1');
INSERT INTO `marketing_activity_info` VALUES (13, '新用户注册花生金服送投资券规则', '2015-9-14', '2020-12-31', '00:00:00', '23:59:59', 'marketActivity1000Handler', '规则1：新用户注册并完成实名认证，即可获得1张10元投资券，2张5元投资券，登录网站后，可在我的账户&mdash;我的优惠券中查看。\r\n规则2：单笔投资满1000元即可使用5元投资券，投资满2000元可使用10元投资券。\r\n规则3：首次充值成功送3元现金，可在我的账户中查看。\r\n规则4：首次投资后送200元投资券（1张100元券，1张50元券，2张20元券，1张10元券）。\r\n规则5：投资券有限期3个月，从发放日起开始计算', 1, '2015-9-17 11:49:19', 1, '2015-9-16 14:30:01', '1');
INSERT INTO `marketing_activity_info` VALUES (14, '中秋国庆双节活动规则', '2015-9-21', '2015-10-9', '00:00:00', '23:59:59', 'marketActivity1002Handler', '规则1：单笔投资满2000元即可使用10元投资券，单笔投资满4000元可使用20元投资券。\r\n规则2：所有用户投资交易，即可返现投资额的5&permil;到个人账户。\r\n规则3：投资券有限期3个月，从发放日起开始计算。\r\n规则4：此活动和新用户注册送券可叠加。', 1, '2015-9-16 14:29:16', 1, '2015-9-16 14:30:09', '1');

delete from marketing_activity_channel_limit;
INSERT INTO `marketing_activity_channel_limit` VALUES (116, 12, 0);
INSERT INTO `marketing_activity_channel_limit` VALUES (117, 12, 1);
INSERT INTO `marketing_activity_channel_limit` VALUES (118, 12, 2);
INSERT INTO `marketing_activity_channel_limit` VALUES (119, 12, 3);
INSERT INTO `marketing_activity_channel_limit` VALUES (120, 14, 0);
INSERT INTO `marketing_activity_channel_limit` VALUES (121, 14, 1);
INSERT INTO `marketing_activity_channel_limit` VALUES (122, 14, 2);
INSERT INTO `marketing_activity_channel_limit` VALUES (123, 14, 3);
INSERT INTO `marketing_activity_channel_limit` VALUES (124, 13, 0);
INSERT INTO `marketing_activity_channel_limit` VALUES (125, 13, 1);
INSERT INTO `marketing_activity_channel_limit` VALUES (126, 13, 2);
INSERT INTO `marketing_activity_channel_limit` VALUES (127, 13, 3);

delete from marketing_activity_user_behavior_limit;
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (83, 12, '1060');
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (84, 12, '1130');
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (85, 14, '1090');
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (86, 13, '1050');
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (87, 13, '1060');
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (88, 13, '1090');

ALTER TABLE `customer_balance_his`
ADD COLUMN `ext_1`  varchar(50) NULL COMMENT '扩展字段' AFTER `rel_project`;

/*==============================================================*/
/*	修改信用等级规则并根据新规则更新已有用户的信用额度等信息                                             */
/*==============================================================*/
delete from credit_level_info;
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('1', '-999999', '55', 'E', '10000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('2', '56', '60', 'D', '30000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('3', '61', '65', 'D+', '50000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('4', '66', '70', 'C', '100000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('5', '71', '75', 'C+', '150000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('6', '76', '80', 'B', '200000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('7', '81', '85', 'B+', '300000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('8', '86', '90', 'A', '400000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('9', '91', '95', 'A+', '500000');
INSERT INTO `credit_level_info` (`id`, `min_score`, `max_score`, `credit_level`, `credit_limit`) VALUES ('10', '96', '999999', 'Ex', '1000000');
update customer_credit_auth a set a.credit_level=(select credit_level from credit_level_info where a.credit_score between min_score and max_score), a.credit_limit=(select credit_limit from credit_level_info where a.credit_score between min_score and max_score)

delete from sys_dict  where type='customer_income';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('57c7f987873547d4a5063152781b338f', '0', '50000元以下', 'customer_income', '会员年收入', '10', '0', '1', '2015-06-23 15:10:41', '1', '2015-07-13 14:46:34', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('31534ea460a341bc99c45afa22c23a61', '1', '50000-100000元', 'customer_income', '会员年收入', '20', '0', '1', '2015-06-23 15:10:54', '1', '2015-07-13 14:48:12', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('643d87ca165c46ceb354ef87ea484933', '2', '100000-300000元', 'customer_income', '会员年收入', '30', '0', '1', '2015-07-13 14:47:37', '1', '2015-07-13 14:48:05', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('390e823543b14c42903ac2561698f71a', '3', '300000-500000元', 'customer_income', '会员年收入', '40', '0', '1', '2015-07-13 14:47:57', '1', '2015-07-13 14:47:57', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d3a4f64e3f2f44389f91ee3f57ee2d87', '4', '500000元以上', 'customer_income', '会员年收入', '50', '0', '1', '2015-07-13 14:48:41', '1', '2015-07-13 14:48:41', '', '0');

delete from sys_dict  where type='education_dict';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('14cab765b3ad4856b49c491c5c295e5c', '2', '专科', 'education_dict', '最高学历', '30', '0', '1', '2015-06-23 15:08:22', '1', '2015-06-23 15:08:22', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1c4e66bf68b941acb64a8c8549f06314', '4', '硕士', 'education_dict', '最高学历', '50', '0', '1', '2015-06-23 15:08:44', '1', '2015-09-21 09:51:13', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5ed707757edc4f06b673602745468679', '5', '博士及以上', 'education_dict', '最高学历', '60', '0', '1', '2015-09-21 09:50:10', '1', '2015-09-21 09:50:47', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('733b26c0c83c4698aa59c92c3c2d09d1', '1', '高中', 'education_dict', '最高学历', '20', '0', '1', '2015-06-23 15:06:56', '1', '2015-06-23 15:06:56', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ce8709891ef14e85975442d055feefda', '0', '初中及以下', 'education_dict', '最高学历', '10', '0', '1', '2015-06-23 15:06:47', '1', '2015-08-31 14:20:18', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e3c4ded425234a6fb0b4c703a3146d20', '3', '本科', 'education_dict', '最高学历', '40', '0', '1', '2015-06-23 15:08:32', '1', '2015-06-23 15:08:32', '', '0');
