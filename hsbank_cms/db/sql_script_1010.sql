ALTER TABLE `project_base`
ADD COLUMN `is_increase_interest`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否加息' AFTER `apply_src_id`,
ADD COLUMN `increase_interest_rate`  float(5,3) NULL DEFAULT NULL COMMENT '加息比例' AFTER `is_increase_interest`;


drop table if exists marketing_behavior_type; 
drop table if exists marketing_activity_user_behavior_limit; 
drop table if exists marketing_activity_attachment; 
drop table if exists marketing_activity_channel_limit; 
drop table if exists marketing_activity_op_his; 
drop table if exists marketing_activity_performance; 
drop table if exists marketing_activity_day_report; 
drop table if exists marketing_activity_info; 


/*==============================================================*/
/* Table: marketing_behavior_type                               */
/*==============================================================*/
create table marketing_behavior_type
(
   id                   int not null auto_increment comment '流水号',
   behavior_code        varchar(6) comment '行为编号',
   behavior_name        varchar(200) comment '行为名称',
   primary key (id)
);

alter table marketing_behavior_type comment '会员行为编码';



/*==============================================================*/
/* Table: marketing_activity_attachment                         */
/*==============================================================*/
create table marketing_activity_attachment
(
   id                   bigint not null auto_increment comment 'id',
   activity_code        bigint comment '活动编号',
   atta_name            varchar(200) comment '附件名称',
   atta_size            int comment '附件大小',
   atts_size            varchar(200) comment '附件路径',
   create_time          datetime comment '上传时间',
   primary key (id)
);

alter table marketing_activity_attachment comment '活动关联附件';



/*==============================================================*/
/* Table: marketing_activity_user_behavior_limit                */
/*==============================================================*/
create table marketing_activity_user_behavior_limit
(
   id                   int not null auto_increment comment 'id',
   activity_code        bigint comment '活动编号',
   action_type          varchar(20) comment '行为类',
   primary key (id)
);

alter table marketing_activity_user_behavior_limit comment '会员行为限制';


/*==============================================================*/
/* Table: marketing_activity_channel_limit                      */
/*==============================================================*/
create table marketing_activity_channel_limit
(
   id                   bigint not null auto_increment,
   activity_id          bigint,
   channel_id           bigint,
   primary key (id)
);

alter table marketing_activity_channel_limit comment '活动渠道限制';



/*==============================================================*/
/* Table: marketing_activity_info                               */
/*==============================================================*/
create table marketing_activity_info
(
   acticity_id          bigint not null auto_increment comment '活动编号',
   name                 varchar(200) comment '活动名称',
   begin_date           date comment '活动开始时间',
   end_date             date comment '活动结束时间',
   begin_time           time comment '活动有效开始时间段',
   end_time             time comment '活动有效结束时间段',
   biz_class_name       varchar(200) comment '关联实现类名',
   introduction         varchar(1000) comment '活动说明',
   create_user_id       bigint comment '活动创建人',
   create_dt            datetime comment '创建时间',
   review_user_id       bigint comment '审批人',
   review_dt            datetime comment '审批时间',
   status               varchar(2) comment '状态',
   primary key (acticity_id)
);

alter table marketing_activity_info comment '活动明细';

alter table marketing_activity_info comment '活动明细';



/*==============================================================*/
/* Table: marketing_activity_op_his                             */
/*==============================================================*/
create table marketing_activity_op_his
(
   id                   bigint not null auto_increment comment 'id',
   acticity_id          bigint comment '活动编号',
   behavior_code        varchar(6) comment '行为编号',
   account_id           bigint comment '用户账号',
   channel_id           int comment '渠道编号',
   op_dt                datetime comment '操作时间',
   in_para              varchar(500) comment '入参',
   out_para             varchar(500) comment '出参',
   result_code          varchar(2) comment '操作结果',
   primary key (id)
);

alter table marketing_activity_op_his comment '营销活动操作流水';




/*==============================================================*/
/* Table: marketing_activity_performance                        */
/*==============================================================*/
create table marketing_activity_performance
(
   id                   bigint not null auto_increment comment 'id',
   activity_code        bigint comment '活动编号',
   performance_id       varchar(200) comment '活动指标',
   target_rel           varchar(2) comment '活动目标关系',
   target_val           float(8,2) comment '活动目标数值',
   primary key (id)
);

alter table marketing_activity_performance comment '活动跟踪指标数据';



/*==============================================================*/
/* Table: marketing_activity_day_report                         */
/*==============================================================*/
create table marketing_activity_day_report
(
   id                   bigint not null auto_increment comment 'id',
   activity_code        bigint comment '活动编号',
   performance_id       varchar(200) comment '活动指标',
   report_date          date comment '日期',
   performance_val      float(8,2) comment '数值',
   primary key (id)
);

alter table marketing_activity_day_report comment '活动指标日跟踪报表';

/*==============================================================*/
/* Table: user_feedback_info                         			*/
/*==============================================================*/
drop table if exists user_feedback_info; 
CREATE TABLE user_feedback_info (
  id 					bigint(20) 	NOT NULL AUTO_INCREMENT COMMENT 'id',
  channel_id 			varchar(2) DEFAULT NULL COMMENT '渠道',
  content 				varchar(1000) DEFAULT NULL COMMENT '反馈内容',
  create_dt datetime 	DEFAULT NULL COMMENT '反馈时间',
  account_id 			bigint(20) DEFAULT NULL COMMENT '用户编号',
  return_content 		varchar(500) DEFAULT NULL COMMENT '处理意见',
  result 				varchar(500) DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (id)
);

alter table user_feedback_info comment '用户意见反馈表';

delete from marketing_behavior_type;
INSERT INTO `marketing_behavior_type` VALUES (1, '1010', '登录');
INSERT INTO `marketing_behavior_type` VALUES (2, '1020', '注册');
INSERT INTO `marketing_behavior_type` VALUES (3, '1030', '签到');
INSERT INTO `marketing_behavior_type` VALUES (4, '1040', '邀请');
INSERT INTO `marketing_behavior_type` VALUES (5, '1050', '充值');
INSERT INTO `marketing_behavior_type` VALUES (6, '1060', '开通第三方账号');
INSERT INTO `marketing_behavior_type` VALUES (7, '1070', '绑卡');
INSERT INTO `marketing_behavior_type` VALUES (8, '1080', '提现');
INSERT INTO `marketing_behavior_type` VALUES (9, '1090', '投资--投标');
INSERT INTO `marketing_behavior_type` VALUES (10, '1100', '债权转让');
INSERT INTO `marketing_behavior_type` VALUES (11, '1110', '融资申请');
INSERT INTO `marketing_behavior_type` VALUES (12, '1120', '抽奖');

/*==============================================================*/
/*	增加:服务费收取策略数据字典                                                                                                                             */
/*==============================================================*/
delete from sys_dict  where type='project_service_charge_type_dict';
INSERT INTO `sys_dict`  VALUES ('ae0e5bc1f68441d7a4a56359a496af2b', '0', '不收取', 'project_service_charge_type_dict', '投资服务费收取策略', 10, '0', '1', '2015-9-6 09:49:45', '1', '2015-9-6 09:49:45', '', '0');
INSERT INTO `sys_dict`  VALUES ('7684e92802b64aa581859ded001e33a5', '1', '优先收取', 'project_service_charge_type_dict', '投资服务费收取策略', 20, '0', '1', '2015-7-28 17:28:47', '1', '2015-9-6 09:48:35', '', '0');
INSERT INTO `sys_dict`  VALUES ('a01ca245c3c64f38b07a5952870f090b', '2', '按比例收取', 'project_service_charge_type_dict', '投资服务费收取策略', 30, '0', '1', '2015-7-28 17:29:01', '1', '2015-9-6 09:47:56', '', '0');

/*==============================================================*/
/*	增加:是否加息数据字典                                                                                                                             	*/
/*==============================================================*/
delete from sys_dict  where type='is_increase_interest_dict';
INSERT INTO `sys_dict` VALUES ('75d65212e1f84ffab3b3defda1984c32', '1', '是', 'is_increase_interest_dict', '是否加息', 20, '0', '1', '2015-9-6 09:51:41', '1', '2015-9-6 09:51:41', '0：否，1：是', '0');
INSERT INTO `sys_dict` VALUES ('cc877ee444ba4948a21ecaaceddfbb92', '0', '否', 'is_increase_interest_dict', '是否加息', 10, '0', '1', '2015-9-6 09:43:07', '1', '2015-9-6 09:51:12', '0：否，1：是', '0');

/*==============================================================*/
/*	增加:意见反馈菜单数据                                                                                                                           		*/
/*==============================================================*/
delete from sys_menu  where id in ('231181915c544905864e78ae8138510e','ccc31fa4a14e4bd284ad69dc9da25bec','ef4433e6d83d4830a3294074d7a4e8af');
INSERT INTO `sys_menu` VALUES ('231181915c544905864e78ae8138510e', 'ccc31fa4a14e4bd284ad69dc9da25bec', '0,1,31,40,ccc31fa4a14e4bd284ad69dc9da25bec,', '审核', 60, '', '', '', '0', 'feedback:userFeedbackInfo:edit', '1', '2015-9-7 09:14:01', '1', '2015-9-7 09:14:01', '', '0');
INSERT INTO `sys_menu` VALUES ('ccc31fa4a14e4bd284ad69dc9da25bec', '40', '0,1,31,40,', '意见反馈', 90, '/feedback/userFeedbackInfo/list', '', 'icon-comment', '1', '', '1', '2015-9-7 09:09:49', '1', '2015-9-7 09:09:49', '意见反馈', '0');
INSERT INTO `sys_menu` VALUES ('ef4433e6d83d4830a3294074d7a4e8af', 'ccc31fa4a14e4bd284ad69dc9da25bec', '0,1,31,40,ccc31fa4a14e4bd284ad69dc9da25bec,', '查看', 30, '', '', '', '0', 'feedback:userFeedbackInfo:view', '1', '2015-9-7 09:13:19', '1', '2015-9-7 09:13:19', '', '0');

/*==============================================================*/
/*	增加:意见反馈菜单角色授权                                                                                                                          		*/
/*==============================================================*/
delete from sys_role_menu where menu_id in ('231181915c544905864e78ae8138510e','ccc31fa4a14e4bd284ad69dc9da25bec','ef4433e6d83d4830a3294074d7a4e8af');
INSERT INTO `sys_role_menu` VALUES ('1', '231181915c544905864e78ae8138510e');
INSERT INTO `sys_role_menu` VALUES ('1', 'ccc31fa4a14e4bd284ad69dc9da25bec');
INSERT INTO `sys_role_menu` VALUES ('1', 'ef4433e6d83d4830a3294074d7a4e8af');

/*==============================================================*/
/*	增加:会员投资券管理菜单数据                                                                                                                           		*/
/*==============================================================*/
delete from sys_menu  where id in ('b4646508f6ad4f74a36ead1216b9d82a','e4291ae170da4ab581cb6b8d567b2987','67b56373e2994f328eb6c9162513f988');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('67b56373e2994f328eb6c9162513f988', 'b4646508f6ad4f74a36ead1216b9d82a', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,b4646508f6ad4f74a36ead1216b9d82a,', '修改_会员投资券', '60', '', '', '', '0', 'customer:customerInvestmentTicket:edit', '1', '2015-09-09 10:43:54', '1', '2015-09-09 10:43:54', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('b4646508f6ad4f74a36ead1216b9d82a', '302f3b2d42764abcbefd99046b39ac8b', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,', '会员投资券管理', '190', '/customer/customerInvestmentTicket/list', '', 'icon-briefcase', '0', '', '1', '2015-09-09 10:42:37', '1', '2015-09-09 10:42:37', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e4291ae170da4ab581cb6b8d567b2987', 'b4646508f6ad4f74a36ead1216b9d82a', '0,1,c4391fe4778142a7a5938fcf30c01fbd,302f3b2d42764abcbefd99046b39ac8b,b4646508f6ad4f74a36ead1216b9d82a,', '查看_会员投资券', '30', '', '', '', '0', 'customer:customerInvestmentTicket:view', '1', '2015-09-09 10:43:17', '1', '2015-09-09 10:45:37', '', '0');

/*==============================================================*/
/*	增加:会员投资券管理角色授权                                                                                                                          		*/
/*==============================================================*/
delete from sys_role_menu where menu_id in ('b4646508f6ad4f74a36ead1216b9d82a','e4291ae170da4ab581cb6b8d567b2987','67b56373e2994f328eb6c9162513f988');
INSERT INTO `sys_role_menu` VALUES ('1', 'b4646508f6ad4f74a36ead1216b9d82a');
INSERT INTO `sys_role_menu` VALUES ('1', 'e4291ae170da4ab581cb6b8d567b2987');
INSERT INTO `sys_role_menu` VALUES ('1', '67b56373e2994f328eb6c9162513f988');

/*==============================================================*/
/*	增加:赠送投资券菜单数据                                                                                                                           		*/
/*==============================================================*/
delete from sys_menu  where id in ('3dc8edb532564f87a08ff571726cf9d1','312c8dae320744c68c0160b6d1090912');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('312c8dae320744c68c0160b6d1090912', '3dc8edb532564f87a08ff571726cf9d1', '0,1,c4391fe4778142a7a5938fcf30c01fbd,3dc8edb532564f87a08ff571726cf9d1,', '首次投资', '30', '/customer/customerInvestmentTicket/firstInvestmentGiveTicket', '', 'icon-thumbs-up', '1', '', '1', '2015-09-09 09:56:37', '1', '2015-09-09 09:56:37', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3dc8edb532564f87a08ff571726cf9d1', 'c4391fe4778142a7a5938fcf30c01fbd', '0,1,c4391fe4778142a7a5938fcf30c01fbd,', '赠送投资券', '40', '', '', '', '1', '', '1', '2015-09-09 09:51:24', '1', '2015-09-09 09:51:24', '', '0');

/*==============================================================*/
/*	增加:赠送投资券菜单角色授权                                                                                                                          		*/
/*==============================================================*/
delete from sys_role_menu where menu_id in ('3dc8edb532564f87a08ff571726cf9d1','312c8dae320744c68c0160b6d1090912');
INSERT INTO `sys_role_menu` VALUES ('1', '3dc8edb532564f87a08ff571726cf9d1');
INSERT INTO `sys_role_menu` VALUES ('1', '312c8dae320744c68c0160b6d1090912');

drop table if exists customer_shake_activity;
/*==============================================================*/
/* Table: customer_shake_activity                               */
/*==============================================================*/
create table customer_shake_activity
(
   id                   int not null auto_increment comment 'Id',
   shake_id             varchar(20) comment '摇奖编号',
   mobile               varchar(20) comment '手机号',
   customer_name        varchar(20) comment '姓名',
   denomination         int comment '面额',
   has_gived             varchar(2) comment '是否已赠送',
   ip                   varchar(20) comment 'ip',
   shake_date           datetime comment '摇奖时间',
   update_by            varchar(64) comment '更新者',
   update_date          datetime comment '更新时间',
   primary key (id)
);
alter table customer_shake_activity comment '摇一摇活动';

/*==============================================================*/
/*	增加:投资券是否已赠送数据字典                                                                                                                             	*/
/*==============================================================*/
delete from sys_dict  where type='has_gived_dict';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('1ce32c6913b0465ca63fc7496f98fb04', '0', '未赠送', 'has_gived_dict', '投资券是否已赠送', '10', '0', '1', '2015-09-10 10:34:15', '1', '2015-09-10 10:34:15', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('3882730239b94501a445884fa2ca33b1', '1', '已赠送', 'has_gived_dict', '投资券是否已赠送', '20', '0', '1', '2015-09-10 10:34:28', '1', '2015-09-10 10:34:28', '', '0');


/*==============================================================*/
/*	增加:摇一摇活动菜单数据                                                                                                                           		*/
/*==============================================================*/
delete from sys_menu  where id in ('ddcf01073eb44bf2afb44537ed08fc7e','5cf6e31f123c4108baf9b360d8212438','caa2f06c45044fb3bc6dd59689901692');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('5cf6e31f123c4108baf9b360d8212438', 'ddcf01073eb44bf2afb44537ed08fc7e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,3dc8edb532564f87a08ff571726cf9d1,ddcf01073eb44bf2afb44537ed08fc7e,', '查看_摇一摇活动', '30', '', '', '', '0', 'customer:customerShakeActivity:view', '1', '2015-09-10 11:12:13', '1', '2015-09-10 11:13:13', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('caa2f06c45044fb3bc6dd59689901692', 'ddcf01073eb44bf2afb44537ed08fc7e', '0,1,c4391fe4778142a7a5938fcf30c01fbd,3dc8edb532564f87a08ff571726cf9d1,ddcf01073eb44bf2afb44537ed08fc7e,', '修改_摇一摇活动', '60', '', '', '', '0', 'customer:customerShakeActivity:edit', '1', '2015-09-10 11:12:58', '1', '2015-09-10 11:12:58', '', '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('ddcf01073eb44bf2afb44537ed08fc7e', '3dc8edb532564f87a08ff571726cf9d1', '0,1,c4391fe4778142a7a5938fcf30c01fbd,3dc8edb532564f87a08ff571726cf9d1,', '摇一摇活动', '60', '/customer/customerShakeActivity/list', '', 'icon-camera', '1', '', '1', '2015-09-10 11:11:30', '1', '2015-09-10 11:11:30', '', '0');

/*==============================================================*/
/*	增加:赠送摇一摇活动菜单角色授权                                                                                                                          		*/
/*==============================================================*/
delete from sys_role_menu where menu_id in ('ddcf01073eb44bf2afb44537ed08fc7e','5cf6e31f123c4108baf9b360d8212438','caa2f06c45044fb3bc6dd59689901692');
INSERT INTO `sys_role_menu` VALUES ('1', 'ddcf01073eb44bf2afb44537ed08fc7e');
INSERT INTO `sys_role_menu` VALUES ('1', '5cf6e31f123c4108baf9b360d8212438');
INSERT INTO `sys_role_menu` VALUES ('1', 'caa2f06c45044fb3bc6dd59689901692');

/*==============================================================*/
/*	增加:活动状态数据字典                                                                                                                    		*/
/*==============================================================*/
delete from sys_dict  where type='marketing_status_dict';
INSERT INTO `sys_dict` VALUES ('0f2a930b19c446a6b8f82cd10e317d1c', '2', '执行结束', 'marketing_status_dict', '活动状态', 30, '0', '1', '2015-6-23 18:15:51', '1', '2015-6-23 18:16:27', '', '0');
INSERT INTO `sys_dict` VALUES ('36e64f94edea4304914d957de7bbd9ae', '-1', '被拒绝', 'marketing_status_dict', '活动状态', 0, '0', '1', '2015-9-10 18:01:31', '1', '2015-9-10 18:01:31', '', '0');
INSERT INTO `sys_dict` VALUES ('38d65c8354ad4860860e4aacaef6e260', '1', '审批通过', 'marketing_status_dict', '活动状态', 20, '0', '1', '2015-6-23 18:15:08', '1', '2015-6-23 18:15:08', '', '0');
INSERT INTO `sys_dict` VALUES ('90c02bfc5293457198f57ad6b9437945', '3', '归档', 'marketing_status_dict', '活动状态', 40, '0', '1', '2015-6-23 18:16:18', '1', '2015-6-23 18:16:36', '', '0');
INSERT INTO `sys_dict` VALUES ('f93fceca1eb343f7a1257e031f96a9a9', '0', '创建', 'marketing_status_dict', '活动状态', 10, '0', '1', '2015-6-23 18:14:46', '1', '2015-6-23 18:14:46', '', '0');

/*==============================================================*/
/*	增加:营销活动菜单数据                                                                                                                   		*/
/*==============================================================*/
delete from sys_menu  where id  = '6fe1901412d042bcb2010240ea57f1e0' or parent_id in ('41839ac24261461b90791b7ada338a1e','6fe1901412d042bcb2010240ea57f1e0');
INSERT INTO `sys_menu` VALUES ('41839ac24261461b90791b7ada338a1e', '6fe1901412d042bcb2010240ea57f1e0', '0,1,6fe1901412d042bcb2010240ea57f1e0,', '营销活动', 30, '', '', '', '1', '', '1', '2015-6-23 18:21:55', '1', '2015-6-23 18:21:55', '', '0');
INSERT INTO `sys_menu` VALUES ('4a3a770f20f14628b0717483f4ab5e7f', '41839ac24261461b90791b7ada338a1e', '0,1,6fe1901412d042bcb2010240ea57f1e0,41839ac24261461b90791b7ada338a1e,', '审批', 60, '', '', '', '0', 'marketing:marketingActivityInfo:review', '1', '2015-6-23 18:22:43', '1', '2015-6-23 18:22:43', '', '0');
INSERT INTO `sys_menu` VALUES ('5216ab37540749b98b1804089263da66', '41839ac24261461b90791b7ada338a1e', '0,1,6fe1901412d042bcb2010240ea57f1e0,41839ac24261461b90791b7ada338a1e,', '管理', 120, '/marketing/marketingActivityInfo/list', '', 'icon-briefcase', '1', 'marketing:marketingActivityInfo:view', '1', '2015-6-23 18:25:14', '1', '2015-6-23 18:27:13', '', '0');
INSERT INTO `sys_menu` VALUES ('7dd1e2fe502d4cbca43067c3789f22f0', '41839ac24261461b90791b7ada338a1e', '0,1,6fe1901412d042bcb2010240ea57f1e0,41839ac24261461b90791b7ada338a1e,', '审批', 150, '/marketing/marketingActivityInfo/reviewList', '', 'icon-ok', '1', 'marketing:marketingActivityInfo:view', '1', '2015-9-10 16:15:39', '1', '2015-9-10 16:21:16', '', '0');
INSERT INTO `sys_menu` VALUES ('8729d5d7ca9d49a1ae24646633017039', '41839ac24261461b90791b7ada338a1e', '0,1,6fe1901412d042bcb2010240ea57f1e0,41839ac24261461b90791b7ada338a1e,', '查看流水', 180, '', '', '', '0', 'marketing:marketingActivityOpHis:view', '1', '2015-9-10 17:27:08', '1', '2015-9-10 17:27:08', '', '0');
INSERT INTO `sys_menu` VALUES ('87c6c82f852b4d97bf4ccd60671b0849', '41839ac24261461b90791b7ada338a1e', '0,1,6fe1901412d042bcb2010240ea57f1e0,41839ac24261461b90791b7ada338a1e,', '创建', 30, '', '', '', '0', 'marketing:marketingActivityInfo:edit', '1', '2015-6-23 18:22:22', '1', '2015-6-23 18:22:53', '', '0');
INSERT INTO `sys_menu` VALUES ('a89467f90cc6408ca8130dd34909b3ba', '41839ac24261461b90791b7ada338a1e', '0,1,6fe1901412d042bcb2010240ea57f1e0,41839ac24261461b90791b7ada338a1e,', '修改', 90, '', '', '', '0', 'marketing:marketingActivityInfo:edit', '1', '2015-6-23 18:23:16', '1', '2015-6-23 18:23:16', '', '0');
INSERT INTO `sys_menu` VALUES ('6fe1901412d042bcb2010240ea57f1e0', '1', '0,1,', '营销活动管理', 1000, '', '', '', '1', '', '1', '2015-6-23 13:37:51', '1', '2015-9-9 11:39:41', '', '0');

/*==============================================================*/
/*	增加:营销活动菜单角色授权                                                                                                                   		*/
/*==============================================================*/
delete FROM sys_role_menu where menu_id in ('7dd1e2fe502d4cbca43067c3789f22f0','8729d5d7ca9d49a1ae24646633017039');
INSERT INTO `sys_role_menu` VALUES ('1', '7dd1e2fe502d4cbca43067c3789f22f0');
INSERT INTO `sys_role_menu` VALUES ('1', '8729d5d7ca9d49a1ae24646633017039');


