
drop table if exists customer_double_eleven_activity;

/*==============================================================*/
/* Table: customer_double_eleven_activity                       */
/*==============================================================*/
create table customer_double_eleven_activity
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   customer_name        varchar(20) comment '姓名',
   amount               float(13,2) comment '金额',
   change_reason        varchar(300) comment '变更原因',
   status               varchar(2) comment '状态',
   op_dt                datetime comment '操作时间',
   primary key (id)
);

alter table customer_double_eleven_activity comment '双11活动';
drop table if exists customer_blance_alignment;

/*==============================================================*/
/* Table: customer_blance_alignment                             */
/*==============================================================*/
create table customer_blance_alignment
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   customer_name        varchar(20) comment '姓名',
   task_gold_balance    float(13,2) comment 'task_平台账户余额',
   task_yeepay_balance  float(13,2) comment 'task_易宝账户余额',
   new_gold_balance     float(13,2) comment 'new_平台账户余额',
   new_yeepay_balance   float(13,2) comment 'new_易宝账户余额',
   status               varchar(2) comment '状态',
   create_dt            datetime comment '创建时间',
   modify_dt            datetime comment '修改时间',
   primary key (id)
);

alter table customer_blance_alignment comment '会员账户余额对齐';


delete from sys_menu where id in ('0f4fb297a29e46139c5b477f9cc45b11',
'6afe2463d2e94065baae45475ad91ed9',
'0efe549fbcd844819b4d94cf277b5490',
'28aa95cc1ad9464c8652408b09749a14',
'4ec8875786d8441786ba5a7a5b9eda54',
'c5349739b2de448a8556cff52de8c630',
'55a2440bbde048cfbfbbdee329a27450',
'7544d98c726c4452942e7d3cbe6af54a');

INSERT INTO `sys_menu` VALUES ('0f4fb297a29e46139c5b477f9cc45b11', '28aa95cc1ad9464c8652408b09749a14', '0,1,c4391fe4778142a7a5938fcf30c01fbd,28aa95cc1ad9464c8652408b09749a14,', '账户余额对齐', 30, '/customer/customerBlanceAlignment/list', '', 'icon-retweet', '1', '', '1', '2015-11-3 12:37:13', '1', '2015-11-3 12:37:13', '', '0');
INSERT INTO `sys_menu` VALUES ('6afe2463d2e94065baae45475ad91ed9', '0efe549fbcd844819b4d94cf277b5490', '0,1,c4391fe4778142a7a5938fcf30c01fbd,0efe549fbcd844819b4d94cf277b5490,', '双11活动', 30, '/customer/customerDoubleElevenActivity/list', '', 'icon-flag', '1', '', '1', '2015-11-3 12:34:01', '1', '2015-11-3 12:34:01', '', '0');
INSERT INTO `sys_menu` VALUES ('0efe549fbcd844819b4d94cf277b5490', 'c4391fe4778142a7a5938fcf30c01fbd', '0,1,c4391fe4778142a7a5938fcf30c01fbd,', '赠送现金', 70, '', '', '', '1', '', '1', '2015-11-3 12:31:54', '1', '2015-11-3 12:31:54', '', '0');
INSERT INTO `sys_menu` VALUES ('28aa95cc1ad9464c8652408b09749a14', 'c4391fe4778142a7a5938fcf30c01fbd', '0,1,c4391fe4778142a7a5938fcf30c01fbd,', '数据对齐', 100, '', '', '', '1', '', '1', '2015-11-3 12:34:56', '1', '2015-11-3 12:34:56', '', '0');
INSERT INTO `sys_menu` VALUES ('4ec8875786d8441786ba5a7a5b9eda54', '0f4fb297a29e46139c5b477f9cc45b11', '0,1,c4391fe4778142a7a5938fcf30c01fbd,28aa95cc1ad9464c8652408b09749a14,0f4fb297a29e46139c5b477f9cc45b11,', '修改', 60, '', '', '', '0', 'customer:customerBlanceAlignment:edit', '1', '2015-11-3 12:41:33', '1', '2015-11-3 12:41:33', '', '0');
INSERT INTO `sys_menu` VALUES ('c5349739b2de448a8556cff52de8c630', '0f4fb297a29e46139c5b477f9cc45b11', '0,1,c4391fe4778142a7a5938fcf30c01fbd,28aa95cc1ad9464c8652408b09749a14,0f4fb297a29e46139c5b477f9cc45b11,', '查看', 30, '', '', '', '0', 'customer:customerBlanceAlignment:view', '1', '2015-11-3 12:41:11', '1', '2015-11-3 12:41:11', '', '0');
INSERT INTO `sys_menu` VALUES ('55a2440bbde048cfbfbbdee329a27450', '6afe2463d2e94065baae45475ad91ed9', '0,1,c4391fe4778142a7a5938fcf30c01fbd,0efe549fbcd844819b4d94cf277b5490,6afe2463d2e94065baae45475ad91ed9,', '修改', 60, '', '', '', '0', 'customer:customerDoubleElevenActivity:edit', '1', '2015-11-3 12:40:45', '1', '2015-11-3 12:40:45', '', '0');
INSERT INTO `sys_menu` VALUES ('7544d98c726c4452942e7d3cbe6af54a', '6afe2463d2e94065baae45475ad91ed9', '0,1,c4391fe4778142a7a5938fcf30c01fbd,0efe549fbcd844819b4d94cf277b5490,6afe2463d2e94065baae45475ad91ed9,', '查看', 30, '', '', '', '0', 'customer:customerDoubleElevenActivity:view', '1', '2015-11-3 12:40:21', '1', '2015-11-3 12:40:21', '', '0');


delete from sys_role_menu where menu_id in ('0f4fb297a29e46139c5b477f9cc45b11',
'6afe2463d2e94065baae45475ad91ed9',
'0efe549fbcd844819b4d94cf277b5490',
'28aa95cc1ad9464c8652408b09749a14',
'4ec8875786d8441786ba5a7a5b9eda54',
'c5349739b2de448a8556cff52de8c630',
'55a2440bbde048cfbfbbdee329a27450',
'7544d98c726c4452942e7d3cbe6af54a');

INSERT INTO `sys_role_menu` VALUES ('1', '0efe549fbcd844819b4d94cf277b5490');
INSERT INTO `sys_role_menu` VALUES ('1', '0f4fb297a29e46139c5b477f9cc45b11');
INSERT INTO `sys_role_menu` VALUES ('1', '28aa95cc1ad9464c8652408b09749a14');
INSERT INTO `sys_role_menu` VALUES ('1', '6afe2463d2e94065baae45475ad91ed9');

INSERT INTO `sys_role_menu` VALUES ('1', '4ec8875786d8441786ba5a7a5b9eda54');
INSERT INTO `sys_role_menu` VALUES ('1', '55a2440bbde048cfbfbbdee329a27450');
INSERT INTO `sys_role_menu` VALUES ('1', '7544d98c726c4452942e7d3cbe6af54a');
INSERT INTO `sys_role_menu` VALUES ('1', 'c5349739b2de448a8556cff52de8c630');

ALTER TABLE `project_base`
ADD COLUMN `is_loan`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否放款（0：否；1：是）' AFTER `increase_interest_rate`;

delete from sys_menu where id in ('0dbbd7f690494e559a7c6a18aa0736dd');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('0dbbd7f690494e559a7c6a18aa0736dd', '0efe549fbcd844819b4d94cf277b5490', '0,1,c4391fe4778142a7a5938fcf30c01fbd,0efe549fbcd844819b4d94cf277b5490,', '补送双十一奖励', '60', '/customer/customerDoubleElevenActivity/patchAward', '', 'icon-briefcase', '1', '', '1', '2015-11-06 14:44:55', '1', '2015-11-06 14:44:55', '', '0');
delete from sys_role_menu where menu_id = '0dbbd7f690494e559a7c6a18aa0736dd';
INSERT INTO `sys_role_menu` VALUES ('1', '0dbbd7f690494e559a7c6a18aa0736dd');


drop table if exists carousel_info;

/*==============================================================*/
/* Table: carousel_info                                         */
/*==============================================================*/
create table carousel_info
(
   id                   bigint not null auto_increment comment '轮播图编号',
   title                varchar(100) comment '标题',
   picture_big          varchar(500) comment '大图',
   picture_medium       varchar(500) comment '中图',
   picture_small        varchar(500) comment '小图',
   type_code            varchar(2) comment '类型',
   target               varbinary(500) comment '目标',
   sort                 int comment '排序',
   start_dt             datetime comment '开始时间',
   end_dt               datetime comment '结束时间',
   status               varchar(2) comment '状态',
   create_user_id       bigint comment '创建人',
   create_dt            datetime comment '创建时间',
   review_user_id       bigint comment '审批人',
   review_dt            datetime comment '审批时间',
   primary key (id)
);

alter table carousel_info comment '轮播图';


drop table if exists carousel_show_term;

/*==============================================================*/
/* Table: carousel_show_term                                    */
/*==============================================================*/
create table carousel_show_term
(
   id                   bigint not null auto_increment comment '编号',
   carousel_id          bigint comment '轮播图编号',
   term_code            varchar(2) comment '终端',
   primary key (id)
);

alter table carousel_show_term comment '轮播图显示终端';
