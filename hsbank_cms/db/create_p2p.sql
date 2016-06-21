drop table if exists customer_account;

drop table if exists customer_balance;

drop table if exists customer_balance_his;

drop table if exists customer_bank_card;

drop table if exists customer_bank_card_his;

drop table if exists customer_base;

drop table if exists customer_base_his;

drop table if exists customer_contacts;

drop table if exists customer_cosurety;

drop table if exists customer_finance;

drop table if exists customer_gold_coin_his;

drop table if exists customer_gold_coin_snapshot;

drop table if exists customer_housing;

drop table if exists customer_integral_his;

drop table if exists customer_integral_snapshot;

drop table if exists customer_login_log;

drop table if exists customer_rest_pwd_log;

drop table if exists customer_status;

drop table if exists customer_tipoff;

drop table if exists customer_type;

drop table if exists customer_work_unit;

drop table if exists fund_info;

drop table if exists fund_manager_info;

drop table if exists fund_performance_data;

drop table if exists fund_template_a_record;

drop table if exists fund_template_b_record;

drop table if exists fund_template_c_record;

drop table if exists fund_type;

drop table if exists log_access_log;

drop table if exists log_third_party;

drop table if exists log_timer_task;

drop table if exists mainstay_project;

drop table if exists marketing_activity_Type;

drop table if exists marketing_activity_attachment;

drop table if exists marketing_activity_channel_limit;

drop table if exists marketing_activity_day_report;

drop table if exists marketing_activity_info;

drop table if exists marketing_activity_performance;

drop table if exists marketing_activity_project_limit;

drop table if exists marketing_activity_user_limit;

drop table if exists operating_mail;

drop table if exists operating_sms;

drop table if exists p2p_fd_account;

drop table if exists p2p_fd_account_flow;

drop table if exists project_attachment;

drop table if exists project_base;

drop table if exists project_execute_snapshot;

drop table if exists project_factor_car_flow;

drop table if exists project_factor_car_mortgage;

drop table if exists project_factor_enterprise_management;

drop table if exists project_factor_house_mortgage;

drop table if exists project_factor_person_consumption;

drop table if exists project_factor_person_credit;

drop table if exists project_investment_record;

drop table if exists project_repayment_plan;

drop table if exists project_repayment_record;

drop table if exists project_repayment_split_record;

drop table if exists project_review_record;

drop table if exists project_transfer_info;

drop table if exists project_will_loan;

drop table if exists report_new_user_day;

drop table if exists report_standing_book;

drop table if exists report_transaction_day;

drop table if exists report_user_rank;

drop table if exists sys_code_age;

drop table if exists sys_code_area;

drop table if exists sys_code_cert_type;

drop table if exists sys_code_customer_level;

drop table if exists sys_code_education;

drop table if exists sys_code_guarantee;

drop table if exists sys_code_marriage;

drop table if exists sys_code_nationality;

drop table if exists sys_code_person_rel;

drop table if exists sys_code_profit;

drop table if exists sys_code_project_status;

drop table if exists sys_code_project_type;

drop table if exists sys_code_repayment_mode;

drop table if exists sys_code_term;

drop table if exists sys_code_time_limit;

drop table if exists sys_code_transfer;

drop table if exists sys_help_info;

drop table if exists sys_help_info_rel_page;

drop table if exists third_party_mail_para;

drop table if exists third_party_sms_para;

drop table if exists third_party_trusteeship_para;

drop table if exists web_sit_column;

drop table if exists web_sit_notice;

drop table if exists customer_org_extend_info;

drop table if exists customer_org_finance_year_record;

/*==============================================================*/
/* Table: customer_account                                      */
/*==============================================================*/
create table customer_account
(
   account_id           bigint not null auto_increment,
   account_name         varchar(50),
   account_type         varchar(2),
   nickname             varchar(50),
   avatar_image         varchar(200),
   account_pwd          varchar(50),
   recommend_account_id varchar(50),
   register_channel     varchar(2),
   invite_code          varchar(50),
   register_dt          datetime,
   register_ip          varchar(50),
   last_login_dt        datetime,
   last_login_term_code varchar(2),
   last_login_ip        varchar(50),
   status_code          varchar(2) comment '-1:注销 0:正常 1:锁定 2:等待重置',
   reset_pwd_val        varchar(50),
   reset_pwd_type       varchar(2),
   err_count            int,
   err_ip               varchar(50),
   err_term_code        varchar(2),
   err_dt               datetime,
   third_account        varchar(50),
   third_login_pwd      varchar(50),
   third_trade_pwd      varchar(50),
   primary key (account_id)
);

alter table customer_account comment '会员账号信息';

/*==============================================================*/
/* Table: customer_balance                                      */
/*==============================================================*/
create table customer_balance
(
   account_id           bigint not null comment '账号编号',
   gold_balance         float comment '当前账户余额值',
   congeal_val          float comment '冻结余额值',
   recharge_count       int comment '充值次数',
   withdraw_count       int comment '提现次数',
   investment_count     int comment '投资次数',
   cancel_count         int comment '撤消次数',
   transfer_count       int comment '转让次数',
   accept_count         int comment '受转让次数',
   first_get_dt         datetime comment '第一笔充值时间',
   last_change_dt       datetime comment '最后一笔变更时间',
   primary key (account_id)
);

alter table customer_balance comment '会员账户余额汇总';

/*==============================================================*/
/* Table: customer_balance_his                                  */
/*==============================================================*/
create table customer_balance_his
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   change_val           int comment '变更值',
   change_type          varchar(10) comment '11:个人网银充值
            12:企业网银充值
            13:快捷充值
            01:即时取现
            02:快速取现
            03:普通取现',
   change_reason        varchar(3) comment '变更原因',
   rel_project          varchar(200) comment '关联项目',
   op_dt                datetime comment '操作时间',
   op_term_type         varchar(2) comment '操作终端',
   primary key (id)
);

alter table customer_balance_his comment '会员账户余额变更流水';

/*==============================================================*/
/* Table: customer_bank_card                                    */
/*==============================================================*/
create table customer_bank_card
(
   account_id           bigint not null comment '账号编号',
   card_num             varchar(100) comment '当前银行卡号',
   create_dt            datetime comment '银行卡填写时间',
   card_type            varchar(2) comment '银行卡种类',
   bank_info            varchar(2) comment '银行卡归属',
   currency_type        varchar(2) comment '银行卡币种',
   last_modify_dt       datetime comment '最后一次修改时间',
   primary key (account_id)
);

alter table customer_bank_card comment '会员银行卡信息';

/*==============================================================*/
/* Table: customer_bank_card_his                                */
/*==============================================================*/
create table customer_bank_card_his
(
   id               bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   card_num             varchar(100) comment '当前银行卡号',
   create_dt            datetime comment '银行卡填写时间',
   card_type            varchar(2) comment '银行卡种类',
   bank_info            varchar(2) comment '银行卡归属',
   currency_type        varchar(2) comment '银行卡币种',
   last_modify_dt       datetime comment '最后一次修改时间',
   op_term_type         varchar(2) comment '操作终端',
   op_dt                date comment '操作时间',
   primary key (id)
);

alter table customer_bank_card_his comment '会员银行卡历史变更';

/*==============================================================*/
/* Table: customer_base                                         */
/*==============================================================*/
create table customer_base
(
   customer_id          bigint not null auto_increment comment '会员编号',
   account_id           bigint comment '会员账号编号',
   customer_name        varchar(20) comment '会员名称',
   name_auth            bool comment '是否实名认证',
   nationality          varchar(20) comment '国籍',
   cert_type            varchar(4) comment '证件类型',
   cert_num             varchar(20) comment '证件号码',
   age_score            int comment '年龄',
   gender_code          varchar(2) comment '性别',
   marriage_code        varchar(2) comment '婚姻状态',
   education_code       varchar(2) comment '最高学历',
   income_code          varchar(2) comment '月收入',
   contact_number       varchar(20) comment '联系电话',
   mobile_1             varchar(20) comment '手机1',
   is_mobile_validate   bool comment '是否手机认证',
   mobile_2             varchar(20) comment '手机2',
   address              varchar(200) comment '居住地址',
   qq_num               varchar(20) comment 'QQ号',
   email                varchar(100) comment '电子邮箱',
   is_email_validate    bool comment '是否邮箱认证',
   post_code            varchar(10) comment '居住地邮编',
   cert_file_1          varchar(200) comment '证件扫描照1',
   cert_file_2          varchar(200) comment '证件扫描照2',
   cert_file_3          varchar(200) comment '证件扫描照3',
   org_code             varchar(50) comment '组织机构代码',
   org_business_license varchar(50) comment '营业执照编号',
   org_tax_registration varchar(50) comment '税务登记号',
   create_dt            datetime comment '创建时间',
   last_modify_dt       datetime comment '最后一次修改时间',
   primary key (customer_id)
);

alter table customer_base comment '会员基本信息';

/*==============================================================*/
/* Table: customer_base_his                                     */
/*==============================================================*/
create table customer_base_his
(
   id                   bigint not null auto_increment comment '流水号',
   customer_id          bigint not null comment '会员编号',
   account_id           bigint comment '会员账号编号',
   customer_name        varchar(20) comment '会员名称',
   name_auth            bool comment '是否实名认证',
   nationality          varchar(20) comment '国籍',
   cert_type            varchar(4) comment '证件类型',
   cert_num             varchar(20) comment '证件号码',
   age_score            int comment '年龄',
   gender_code          varchar(2) comment '性别',
   marriage_code        varchar(2) comment '婚姻状态',
   education_code       varchar(2) comment '最高学历',
   income_code          varchar(2) comment '月收入',
   contact_number       varchar(20) comment '联系电话',
   mobile_1             varchar(20) comment '手机1',
   is_mobile_validate   bool comment '是否手机认证',
   mobile_2             varchar(20) comment '手机2',
   address              varchar(200) comment '居住地址',
   qq_num               varchar(20) comment 'QQ号',
   email                varchar(100) comment '电子邮箱',
   is_email_validate    bool comment '是否邮箱认证',
   post_code            varchar(10) comment '居住地邮编',
   cert_file_1          varchar(200) comment '证件扫描照1',
   cert_file_2          varchar(200) comment '证件扫描照2',
   cert_file_3          varchar(200) comment '证件扫描照3',
   create_dt            datetime comment '创建时间',
   primary key (id)
);

alter table customer_base_his comment '会员基本信息变更历史';

/*==============================================================*/
/* Table: customer_contacts                                     */
/*==============================================================*/
create table customer_contacts
(
   id                   bigint not null auto_increment comment '流水号',
   customer_id          bigint comment '会员编号',
   contact_name         varchar(50) comment '联系人名称',
   contact_phone        varchar(30) comment '联系人电话',
   contact_role         varchar(30) comment '联系人角色',
   contact_email        varchar(30) comment '联系人邮箱',
   contact_fax          varchar(30) comment '联系人传真',
   status               varchar(1) comment '状态',
   primary key (id)
);

alter table customer_contacts comment '组织会员联系人列表';

/*==============================================================*/
/* Table: customer_cosurety                                     */
/*==============================================================*/
create table customer_cosurety
(
   id                   bigint not null auto_increment comment '流水号',
   customer_id          bigint comment '会员编号',
   cosurety_name        varchar(200) comment '联保人',
   cosurety_mobile      varchar(20) comment '联保人电话',
   cosurety_rel         varchar(2) comment '联保人关系',
   cosurety_cert_num    varchar(20) comment '联保人身份证号',
   primary key (id)
);

alter table customer_cosurety comment '会员联保人';

/*==============================================================*/
/* Table: customer_finance                                      */
/*==============================================================*/
create table customer_finance
(
   customer_id          bigint not null comment '会员编号',
   month_income         int comment '月均收入',
   income_remark        varchar(500) comment '收入构成描述',
   month_spend          int comment '月均支出',
   spend_remark         varchar(500) comment '支出构成描述',
   housing_conditions   varchar(2) comment '住房条件',
   housing_val          int comment '房产价值(单位:万)',
   has_car              bool comment '是否购车',
   car_val              int comment '车辆价值(单位:万)',
   car_remark           varchar(500) comment '车辆信息描述',
   shareholder_ent      varchar(500) comment '参股企业名称',
   shareholder_val      int comment '参股企业资额(单位:万)',
   other_remark         varchar(500) comment '其他资产描述',
   primary key (customer_id)
);

alter table customer_finance comment '会员财务信息';

/*==============================================================*/
/* Table: customer_gold_coin_his                                */
/*==============================================================*/
create table customer_gold_coin_his
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   change_val           int comment '变更币值',
   change_reason        varchar(500) comment '变更原因',
   rel_activity         varchar(200) comment '关联活动',
   exchange_goods       varchar(200) comment '兑换物品',
   op_dt                datetime comment '操作时间',
   op_term_type         varchar(2) comment '操作终端',
   primary key (id)
);

alter table customer_gold_coin_his comment '会员代币变更流水';

/*==============================================================*/
/* Table: customer_gold_coin_snapshot                           */
/*==============================================================*/
create table customer_gold_coin_snapshot
(
   account_id           bigint not null auto_increment comment '账号编号',
   gold_balance         int comment '当前代币值',
   outtime_val          int comment '即将过期代币值',
   first_get_dt         datetime comment '第一笔代币获得时间',
   last_change_dt       datetime comment '最后一笔代币变更时间',
   primary key (account_id)
);

alter table customer_gold_coin_snapshot comment '会员代金币汇总';

/*==============================================================*/
/* Table: customer_housing                                      */
/*==============================================================*/
create table customer_housing
(
   id                   bigint not null auto_increment comment '流水号',
   customer_id          bigint comment '会员编号(单位：平米)',
   housing_address      varchar(100) comment '房产地址',
   area                 float comment '建筑面积',
   construction_year    varchar(4) comment '建筑年份',
   is_over              int comment '供款状况
            0：按揭中
            1：已供完房款',
   owner1               varchar(200) comment '所有人1',
   owner2               varchar(200) comment '所有人2',
   Column_9             int comment '贷款年限(单位：年)',
   Column_10            float comment '每月供款（单位：元）',
   Column_11            float comment '尚欠贷款余额（单位：元）',
   Column_12            varchar(200) comment '按揭银行',
   primary key (id)
);

alter table customer_housing comment '会员房产信息';

/*==============================================================*/
/* Table: customer_integral_his                                 */
/*==============================================================*/
create table customer_integral_his
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   change_val           int comment '变更分值',
   change_reason        varchar(500) comment '变更原因',
   rel_activity         varchar(200) comment '关联活动',
   exchange_goods       varchar(200) comment '兑换物品',
   op_dt                datetime comment '操作时间',
   op_term_type         varchar(2) comment '操作终端',
   primary key (id)
);

alter table customer_integral_his comment '会员积分变更流水';

/*==============================================================*/
/* Table: customer_integral_snapshot                            */
/*==============================================================*/
create table customer_integral_snapshot
(
   account_id           bigint not null comment '账号编号',
   integral_balance     int comment '当前积分值',
   outtime_val          int comment '即将过期积分值',
   first_get_dt         datetime comment '第一笔积分获得时间',
   last_change_dt       datetime comment '最后一笔积分变更时间',
   primary key (account_id)
);

alter table customer_integral_snapshot comment '会员积分汇总';

/*==============================================================*/
/* Table: customer_login_log                                    */
/*==============================================================*/
create table customer_login_log
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   login_dt             datetime comment '登录时间',
   term_code            varchar(2) comment '登录终端类型',
   remark               varchar(500) comment '登录备注信息',
   equipment_specification varchar(500) comment '终端规格',
   primary key (id)
);

alter table customer_login_log comment '会员客户端访问流水';

/*==============================================================*/
/* Table: customer_rest_pwd_log                                 */
/*==============================================================*/
create table customer_rest_pwd_log
(
   id                   bigint not null auto_increment comment '流水号',
   account_id           bigint comment '账号编号',
   reset_dt             datetime comment '重置时间',
   reset_type           varchar(2) comment '重置方式',
   tmp_pwd              varchar(20) comment '临时密码',
   status               varchar(1) comment '状态',
   primary key (id)
);

alter table customer_rest_pwd_log comment '密码重置记录';

/*==============================================================*/
/* Table: customer_status                                       */
/*==============================================================*/
create table customer_status
(
   status_code          int not null comment '状态码',
   status_name          varchar(100) comment '状态说明',
   primary key (status_code)
);

alter table customer_status comment '会员状态';

/*==============================================================*/
/* Table: customer_tipoff                                       */
/*==============================================================*/
create table customer_tipoff
(
   id                   bigint not null auto_increment comment '流水号',
   informants_name      varchar(50) comment '举报人',
   create_dt            datetime comment '举报时间',
   informants_email     varchar(50) comment '举报人邮箱',
   informants_mobile    varchar(20) comment '举报人联系电话',
   content              varchar(500) comment '举报内容',
   attr1                varchar(200) comment '举报附件1',
   attr2                varchar(200) comment '举报附件2',
   attr3                varchar(200) comment '举报附件3',
   verify_name          varchar(20) comment '举报查证人员',
   verify_reply         varchar(500) comment '查证结果回复',
   reply_dt             datetime comment '回复时间',
   status               varchar(2) comment '举报状态',
   primary key (id)
);

alter table customer_tipoff comment '会员举报信息';

/*==============================================================*/
/* Table: customer_type                                         */
/*==============================================================*/
create table customer_type
(
   ID                   bigint not null auto_increment comment 'ID',
   type_code            varchar(20) comment '类型编码',
   type_name            varchar(40) comment '类型名称',
   primary key (ID)
);

alter table customer_type comment '会员类型';

/*==============================================================*/
/* Table: customer_work_unit                                    */
/*==============================================================*/
create table customer_work_unit
(
   id                   bigint not null auto_increment comment '流水号',
   customer_id          bigint comment '会员编号',
   unit_name            varchar(100) comment '工作单位',
   unit_address         varchar(200) comment '单位地址',
   unit_contact_tel     varchar(20) comment '单位联系电话',
   jion_date            date comment '进入单位时间',
   work_years           int comment '工作年限',
   reterence_name       varchar(50) comment '证明人',
   reterence_mobile     varchar(20) comment '证明人手机',
   income               varchar(2) comment '收入概况',
   create_dt            datetime comment '创建时间',
   last_modify_dt       datetime comment '最后一次修改时间',
   primary key (id)
);

alter table customer_work_unit comment '会员单位信息';

/*==============================================================*/
/* Table: fund_info                                             */
/*==============================================================*/
create table fund_info
(
   fund_id              int not null comment '编号',
   fund_code            varchar(20) comment '基金编码',
   fund_name            varchar(100) comment '基金名称',
   introduction         varchar(2000) comment '基金说明',
   fund_type_id         int comment '基金类型',
   fund_manager         int comment '经理人',
   create_date          date comment '创建时间',
   start_apply_buy_dt   datetime comment '开始申购时间',
   end_dt               datetime comment '下架时间',
   status               varchar(2) comment '基金状态',
   detail_url           varchar(200) comment '基金详情跳转URL',
   primary key (fund_id)
);

alter table fund_info comment '基金';

/*==============================================================*/
/* Table: fund_manager_info                                     */
/*==============================================================*/
create table fund_manager_info
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table fund_manager_info comment '基金经理简介';

/*==============================================================*/
/* Table: fund_performance_data                                 */
/*==============================================================*/
create table fund_performance_data
(
   id                   bigint not null auto_increment comment 'id',
   fund_code            int comment '基金名称',
   day                  date comment '日期',
   net_worth            float(10,4) comment '净值',
   primary key (id)
);

alter table fund_performance_data comment '基金业绩数据';

/*==============================================================*/
/* Table: fund_template_a_record                                */
/*==============================================================*/
create table fund_template_a_record
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table fund_template_a_record comment '产品类型A明细';

/*==============================================================*/
/* Table: fund_template_b_record                                */
/*==============================================================*/
create table fund_template_b_record
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table fund_template_b_record comment '产品关联渠道';

/*==============================================================*/
/* Table: fund_template_c_record                                */
/*==============================================================*/
create table fund_template_c_record
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table fund_template_c_record comment '产品关联用户';

/*==============================================================*/
/* Table: fund_type                                             */
/*==============================================================*/
create table fund_type
(
   type_id              int not null auto_increment comment '类型编号',
   type_name            varchar(100) comment '类型名称',
   remark               varchar(200) comment '类型备注',
   parent_type_id       int comment '上级类型名称',
   level                int comment '类型层级',
   sort                 int comment '类型同层次顺序',
   primary key (type_id)
);

alter table fund_type comment '基金类型';

/*==============================================================*/
/* Table: log_access_log                                        */
/*==============================================================*/
create table log_access_log
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table log_access_log comment '访问日志';

/*==============================================================*/
/* Table: log_third_party                                       */
/*==============================================================*/
create table log_third_party
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table log_third_party comment '第三方交互日志';

/*==============================================================*/
/* Table: log_timer_task                                        */
/*==============================================================*/
create table log_timer_task
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table log_timer_task comment '定时任务日志';

/*==============================================================*/
/* Table: mainstay_project                                      */
/*==============================================================*/
create table mainstay_project
(
   id                   bigint not null auto_increment comment 'id',
   project_id           bigint comment '项目编号',
   project_type         bigint comment '项目类型',
   sort                 int comment '项目顺序',
   begin_dt             datetime comment '开始时间',
   end_dt               datetime comment '结束时间',
   create_dt            datetime comment '创建时间',
   create_user_id       bigint comment '创建人',
   review_dt            datetime comment '审批时间',
   review_user_id       bigint comment '审批人',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table mainstay_project comment '重点推产品';

/*==============================================================*/
/* Table: marketing_activity_Type                               */
/*==============================================================*/
create table marketing_activity_Type
(
   code                 int not null auto_increment comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table marketing_activity_Type comment '活动类型';

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
/* Table: marketing_activity_channel_limit                      */
/*==============================================================*/
create table marketing_activity_channel_limit
(
   id                   bigint not null auto_increment comment 'id',
   activity_id          bigint comment '活动编号',
   channel_id           bigint comment '渠道编号',
   primary key (id)
);

alter table marketing_activity_channel_limit comment '活动渠道限制';

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
/* Table: marketing_activity_info                               */
/*==============================================================*/
create table marketing_activity_info
(
   id                   bigint not null auto_increment comment '活动编号',
   name                 varchar(200) comment '活动名称',
   begin_dt             datetime comment '活动开始时间',
   end_dt               datetime comment '活动结束时间',
   introduction         varchar(1000) comment '活动说明',
   user_limit           varchar(1000) comment '活动用户限制说明',
   channel_limit        varchar(1000) comment '活动渠道限制说明',
   project_limit        varchar(1000) comment '活动产品限制说明',
   sum_limit            varchar(1000) comment '活动总量限制说明',
   create_user_id       bigint comment '活动创建人',
   create_dt            datetime comment '创建时间',
   review_user_id       bigint comment '审批人',
   review_dt            datetime comment '审批时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table marketing_activity_info comment '活动明细';

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
/* Table: marketing_activity_project_limit                      */
/*==============================================================*/
create table marketing_activity_project_limit
(
   id                   bigint not null auto_increment comment 'id',
   activity_code        bigint comment '活动编号',
   project_limit_class  varchar(200) comment '校验类',
   project_limit__method varchar(200) comment '校验方法',
   primary key (id)
);

alter table marketing_activity_project_limit comment '活动产品限制';

/*==============================================================*/
/* Table: marketing_activity_user_limit                         */
/*==============================================================*/
create table marketing_activity_user_limit
(
   id                   bigint not null auto_increment comment 'id',
   activity_code        bigint comment '活动编号',
   user_limit_class     varchar(200) comment '校验类',
   user_limit__method   varchar(200) comment '校验方法',
   primary key (id)
);

alter table marketing_activity_user_limit comment '活动会员限制';

/*==============================================================*/
/* Table: operating_mail                                        */
/*==============================================================*/
create table operating_mail
(
   id                   bigint not null auto_increment comment 'id',
   primary key (id)
);

alter table operating_mail comment '营运邮件';

/*==============================================================*/
/* Table: operating_sms                                         */
/*==============================================================*/
create table operating_sms
(
   id                   bigint not null auto_increment comment 'id',
   primary key (id)
);

alter table operating_sms comment '营运短信';

/*==============================================================*/
/* Table: p2p_fd_account                                        */
/*==============================================================*/
create table p2p_fd_account
(
   para_code            int not null comment 'para_code',
   para_val             varchar(200) comment 'para_val',
   primary key (para_code)
);

alter table p2p_fd_account comment '平台账号';

/*==============================================================*/
/* Table: p2p_fd_account_flow                                   */
/*==============================================================*/
create table p2p_fd_account_flow
(
   id                   bigint not null auto_increment comment 'id',
   change_type          int comment '变更类型',
   change_val           float(10,2) comment '变化值',
   change_reason        varchar(10) comment '变化原因',
   detail               varchar(200) comment '详情',
   create_dt            datetime comment '变化时间',
   third_party_seq      varchar(100) comment '第三方交易号',
   third_party_result   varchar(200) comment '交易结果',
   primary key (id)
);

alter table p2p_fd_account_flow comment '平台账号流水';

/*==============================================================*/
/* Table: project_attachment                                    */
/*==============================================================*/
create table project_attachment
(
   id                   bigint not null auto_increment comment '附件流水号',
   attachment_type      int comment '附件类型',
   name                 varchar(200) comment '附件名称',
   path                 varchar(200) comment '附件路径',
   create_dt            datetime comment '附件上传日期',
   create_user_id       bigint comment '上传人员',
   size                 int comment '附件大小',
   status               varchar(2) comment '附件状态',
   primary key (id)
);

alter table project_attachment comment '合同附件';

/*==============================================================*/
/* Table: project_base                                          */
/*==============================================================*/
create table project_base
(
   project_id           int not null auto_increment comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   is_new_user          int comment '是否新手项目',
   is_recommend         int comment '是否重点推荐',
   project_type_code    bigint comment '项目类型',
   project_name         varchar(200) comment '项目名称',
   project_introduce    varchar(2048) comment '项目概述',
   repayment_mode       varchar(2) comment '还款方式',
   borrowers_user       bigint comment '融资人',
   agent_user           bigint comment '融资代理人',
   area_id              bigint comment '项目区域位置',
   annualized_rate      float(4,2) comment '年化利率',
   project_duration     int comment '项目期限',
   use_method           varchar(100) comment '贷款用途',
   finance_money        float(8,2) comment '本期融资金额',
   credit_extension_money float(8,2) comment '本次授信金额',
   planned_repayment_date date comment '计划还款日期',
   actual_repayment_date date comment '实际还款日期',
   bidding_deadline     datetime comment '投标截止时间',
   transfer_code        bigint comment '债权转让限制',
   create_dt            datetime comment '项目创建日期',
   review_remark        varchar(500) comment '审批备注',
   create_user_id       bigint comment '项目创建人',
   review_dt            datetime comment '项目审核日期',
   review_user_id       bigint comment '项目审核人',
   publish_dt           datetime comment '项目发布日期',
   close_dt             datetime comment '项目关闭日期',
   close_user_id        bigint comment '项目关闭确认人',
   project_status       varchar(2) comment '项目状态',
   is_del               bool comment '是否删除',
   primary key (project_id)
);

alter table project_base comment '借贷合同明细';

/*==============================================================*/
/* Table: project_execute_snapshot                              */
/*==============================================================*/
create table project_execute_snapshot
(
   id                   bigint not null comment '执行流水号',
   project_id           bigint comment '项目流水号',
   finance_money        float(8,2) comment '借款金额',
   end_finance_money    float(8,2) comment '已融资金额',
   end_repay_money      float(8,2) comment '已还款金额',
   sum_profit           float(8,2) comment '总收益',
   primary key (id)
);

alter table project_execute_snapshot comment '合同执行快照';

/*==============================================================*/
/* Table: project_factor_car_flow                               */
/*==============================================================*/
create table project_factor_car_flow
(
   id                   bigint not null auto_increment comment 'ID',
   project_id           int comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   car_model            varchar(200) comment '车辆型号',
   degrees_depreciation varchar(20) comment '新旧状况',
   car_price            float(6,2) comment '购买价格',
   primary key (id)
);

alter table project_factor_car_flow comment '车辆周转贷项目要素';

/*==============================================================*/
/* Table: project_factor_car_mortgage                           */
/*==============================================================*/
create table project_factor_car_mortgage
(
   id                   bigint not null auto_increment comment 'ID',
   project_id           int comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   primary key (id)
);

alter table project_factor_car_mortgage comment '车辆抵押贷项目要素';

/*==============================================================*/
/* Table: project_factor_enterprise_management                  */
/*==============================================================*/
create table project_factor_enterprise_management
(
   id                   bigint not null auto_increment comment 'ID',
   project_id           int comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   primary key (id)
);

alter table project_factor_enterprise_management comment '企业经营贷项目要素';

/*==============================================================*/
/* Table: project_factor_house_mortgage                         */
/*==============================================================*/
create table project_factor_house_mortgage
(
   id                   bigint not null auto_increment comment 'ID',
   project_id           int comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   primary key (id)
);

alter table project_factor_house_mortgage comment '房产抵押贷项目要素';

/*==============================================================*/
/* Table: project_factor_person_consumption                     */
/*==============================================================*/
create table project_factor_person_consumption
(
   id                   bigint not null auto_increment comment 'ID',
   project_id           int comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   primary key (id)
);

alter table project_factor_person_consumption comment '个人消费贷项目要素';

/*==============================================================*/
/* Table: project_factor_person_credit                          */
/*==============================================================*/
create table project_factor_person_credit
(
   id                   bigint not null auto_increment comment 'ID',
   project_id           int comment '项目流水号',
   project_code         varchar(50) comment '项目编号',
   primary key (id)
);

alter table project_factor_person_credit comment '个人信用贷项目要素';

/*==============================================================*/
/* Table: project_investment_record                             */
/*==============================================================*/
create table project_investment_record
(
   id                   bigint not null auto_increment comment '投资流水号',
   project_id           bigint comment '项目流水号',
   investment_user_id   bigint comment '投资人',
   money                float(8,2) comment '投资金额',
   investment_type      int comment '投资方式',
   op_term              int comment '操作终端',
   op_dt                datetime comment '投资时间',
   status               int comment '投资状态',
   third_party_order    varchar(200) comment '投资交易第三方流水编号',
   primary key (id)
);

alter table project_investment_record comment '投资记录';

/*==============================================================*/
/* Table: project_repayment_plan                                */
/*==============================================================*/
create table project_repayment_plan
(
   plan_id              bigint not null auto_increment comment '还款计划流水',
   project_id           bigint comment '项目流水号',
   plan_date            date comment '还款日期',
   plan_money           float(8,2) comment '还款金额',
   money_type           int comment '还款资金类型',
   primary key (plan_id)
);

alter table project_repayment_plan comment '还款计划';

/*==============================================================*/
/* Table: project_repayment_record                              */
/*==============================================================*/
create table project_repayment_record
(
   record_id            bigint not null auto_increment comment '还款流水号',
   project_id           bigint comment '项目流水号',
   plan_id              bigint comment '对应计划流水号',
   repayment_date       date comment '还款日期',
   repayment_channel_id bigint comment '还款渠道',
   sum_money            float(8,2) comment '还款总金额',
   status               bigint comment '还款状态',
   split_balance        float(8,2) comment '还款拆分余额',
   primary key (record_id)
);

alter table project_repayment_record comment '还款记录';

/*==============================================================*/
/* Table: project_repayment_split_record                        */
/*==============================================================*/
create table project_repayment_split_record
(
   split_recore_id      bigint not null auto_increment comment '还款分配流水号',
   record_id            bigint comment '还款流水号',
   project_id           bigint comment '项目流水号',
   repayment_user_id    bigint comment '还款人',
   repayment_account    varchar(200) comment '还款人账号',
   payee_user_id        bigint comment '收款人',
   payee_account        varchar(200) comment '收款人账号',
   money                float(8,2) comment '分配金额',
   principal            float(8,2) comment '本金',
   interest             float(8,2) comment '利息',
   remained_principal   float(8,2) comment '剩余本金',
   repayment_dt         datetime comment '还款时间',
   third_party_order    varchar(200) comment '分配第三方编号',
   repay_result         varchar(200) comment '分配返回码',
   status               varchar(2) comment '分配状态',
   create_dt            datetime comment '创建时间',
   modify_dt            datetime comment '修改时间',
   modify_remark        varchar(1000) comment '修改备注',
   primary key (split_recore_id)
);

alter table project_repayment_split_record comment '还款拆分明细';

/*==============================================================*/
/* Table: project_review_record                                 */
/*==============================================================*/
create table project_review_record
(
   id                   bigint not null auto_increment comment '审核流水号',
   project_id           bigint comment '项目流水号',
   review_user_id       bigint comment '审核人员',
   review_result        bigint comment '审核结果',
   review_dt            datetime comment '审核日期',
   primary key (id)
);

alter table project_review_record comment '合同审核记录';

/*==============================================================*/
/* Table: project_transfer_info                                 */
/*==============================================================*/
create table project_transfer_info
(
   transer_project_id   bigint not null auto_increment comment '转让编号',
   project_id           bigint comment '原始项目编号',
   parent_transer_project_id bigint comment '上一转让编号',
   is_recommend         int comment '是否重点推荐',
   investment_record_id bigint comment '原投资记录编号',
   project_end_date     date comment '原始项目还款结束日期',
   next_repayment_date  date comment '下一还款日期',
   transferor           bigint comment '转让人',
   transfer_price       float(8,2) comment '转让价格',
   fair_price           float(8,2) comment '公允价格',
   discount_rate        float(4,2) comment '转让截止日期',
   service_charge_type  int comment '手续费收取',
   create_date          datetime comment '创建时间',
   remainder_creditor   float(8,2) comment '剩余债权',
   status               varchar(2) comment '转让状态',
   primary key (transer_project_id)
);

alter table project_transfer_info comment '债权转让合同';

/*==============================================================*/
/* Table: project_will_loan                                     */
/*==============================================================*/
create table project_will_loan
(
   id                   bigint not null auto_increment comment 'id',
   contact_name         varchar(100) comment '联系人',
   mobile               varchar(20) comment '手机号码',
   money                float(8,2) comment '融资金额',
   area_id              bigint comment '所在区域',
   remark               varchar(1000) comment '备注说明',
   create_dt            datetime comment '创建时间',
   create_ip            varchar(25) comment '创建IP',
   status               varchar(2) comment '确认状态',
   confirm_user_id      bigint comment '确认人员',
   primary key (id)
);

alter table project_will_loan comment '借贷意向';

/*==============================================================*/
/* Table: report_new_user_day                                   */
/*==============================================================*/
create table report_new_user_day
(
   id                   bigint not null auto_increment comment 'id',
   day                  date comment 'day',
   new_user_count       bigint comment '新增用户总数',
   recommend_count      bigint comment '推荐用户数',
   be_recommend_count   bigint comment '被推荐用户数',
   activity_user_count  bigint comment '活动导入用户数',
   pc_register_count    bigint comment 'pc注册用户数',
   mobile_register_count bigint comment '客户端注册用户数',
   primary key (id)
);

alter table report_new_user_day comment '新增用户数日报';

/*==============================================================*/
/* Table: report_standing_book                                  */
/*==============================================================*/
create table report_standing_book
(
   id                   int not null auto_increment comment 'id',
   day                  date comment '日期',
   sum_loan             float(10,4) comment '平台总借款额（万元）',
   sum_trading          float(10,4) comment '平台累计融资额（万元）',
   new_loan             float(10,4) comment '平台新增借款额（万元）',
   new_trading          float(10,4) comment '平台新增融资额（万元）',
   sum_advance_recharge float(10,4) comment '平台垫付充值额（万元）',
   sum_income_transfer  float(10,4) comment '平台收取转让服务费（万元）',
   sum_income_project   float(10,4) comment '平台收取项目融资服务费（万元）',
   sum_income_day       float(10,4) comment '平台日收入（万元）',
   sum_income           float(10,4) comment '平台累计收入（万元）',
   primary key (id)
);

alter table report_standing_book comment '企业经营总账';

/*==============================================================*/
/* Table: report_transaction_day                                */
/*==============================================================*/
create table report_transaction_day
(
   id                   bigint not null auto_increment comment 'id',
   day                  date comment '交易日',
   recharge_user_count  bigint comment '充值人数',
   recharge_money_sum   float(8,2) comment '充值额',
   withdraw_user_count  bigint comment '提现人数',
   withdrew_money_sum   float(8,2) comment '提现额度',
   stock_money          float(8,2) comment '总余额',
   sum_user_count       bigint comment '累计用户数',
   new_user_count       bigint comment '新增用户数',
   day_active_user_count bigint comment '当天活跃用户数',
   ip_count             bigint comment '访问IP数',
   pc_web_channel_uesr_count bigint comment 'web访问人数',
   android_channel_user_count bigint comment '客户端（android）访问人数',
   ios_channel_user_count bigint comment '客户端（ios）访问人数',
   mobile_web_channel_user_count bigint comment '客户端（web）访问人数',
   primary key (id)
);

alter table report_transaction_day comment '交易量日报';

/*==============================================================*/
/* Table: report_user_rank                                      */
/*==============================================================*/
create table report_user_rank
(
   id                   bigint not null auto_increment comment 'id',
   rank_type            int comment '排行榜类型',
   rank_batch           int comment '排行榜批次',
   user_id              bigint comment '用户',
   money                float(8,2) comment '投资额',
   sort                 int comment '排序号',
   modify_day           date comment '更新日期',
   primary key (id)
);

alter table report_user_rank comment '投资用户排行榜日报';

/*==============================================================*/
/* Table: sys_code_age                                          */
/*==============================================================*/
create table sys_code_age
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_age comment '年龄段';

/*==============================================================*/
/* Table: sys_code_area                                         */
/*==============================================================*/
create table sys_code_area
(
   area_id              bigint not null auto_increment comment '区域编号',
   area_name            varchar(200) comment '区域名称',
   parent_area_id       bigint comment '上级区域编号',
   area_level           int comment '区域层级',
   area_sort            int comment '区域排序',
   primary key (area_id)
);

alter table sys_code_area comment '项目区域';

/*==============================================================*/
/* Table: sys_code_cert_type                                    */
/*==============================================================*/
create table sys_code_cert_type
(
   code                 varchar(4) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_cert_type comment '证件类型';

/*==============================================================*/
/* Table: sys_code_customer_level                               */
/*==============================================================*/
create table sys_code_customer_level
(
   code                 int not null auto_increment comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_customer_level comment '会员级别';

/*==============================================================*/
/* Table: sys_code_education                                    */
/*==============================================================*/
create table sys_code_education
(
   code                 int not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_education comment '受教育程度';

/*==============================================================*/
/* Table: sys_code_guarantee                                    */
/*==============================================================*/
create table sys_code_guarantee
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_guarantee comment '担保方式';

/*==============================================================*/
/* Table: sys_code_marriage                                     */
/*==============================================================*/
create table sys_code_marriage
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_marriage comment '婚姻状态';

/*==============================================================*/
/* Table: sys_code_nationality                                  */
/*==============================================================*/
create table sys_code_nationality
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_nationality comment '国籍';

/*==============================================================*/
/* Table: sys_code_person_rel                                   */
/*==============================================================*/
create table sys_code_person_rel
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_person_rel comment '关联关系';

/*==============================================================*/
/* Table: sys_code_profit                                       */
/*==============================================================*/
create table sys_code_profit
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_profit comment '项目收益';

/*==============================================================*/
/* Table: sys_code_project_status                               */
/*==============================================================*/
create table sys_code_project_status
(
   code                 int not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_project_status comment '项目状态';

/*==============================================================*/
/* Table: sys_code_project_type                                 */
/*==============================================================*/
create table sys_code_project_type
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_project_type comment '项目类型';

/*==============================================================*/
/* Table: sys_code_repayment_mode                               */
/*==============================================================*/
create table sys_code_repayment_mode
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name'
);

alter table sys_code_repayment_mode comment '还款方式代码表';

/*==============================================================*/
/* Table: sys_code_term                                         */
/*==============================================================*/
create table sys_code_term
(
   code                 int not null comment 'code',
   name                 varchar(100) comment 'name',
   primary key (code)
);

alter table sys_code_term comment '终端';

/*==============================================================*/
/* Table: sys_code_time_limit                                   */
/*==============================================================*/
create table sys_code_time_limit
(
   code                 varchar(2) not null comment 'code',
   name                 varchar(100) comment 'name',
   limit_days           int comment 'limit_days',
   primary key (code)
);

alter table sys_code_time_limit comment '项目期限';

/*==============================================================*/
/* Table: sys_code_transfer                                     */
/*==============================================================*/
create table sys_code_transfer
(
   code                 int not null auto_increment comment 'code',
   name                 varchar(100) comment 'name',
   limit_day            int comment 'limit_day',
   primary key (code)
);

alter table sys_code_transfer comment '债权转让';

/*==============================================================*/
/* Table: sys_help_info                                         */
/*==============================================================*/
create table sys_help_info
(
   id                   bigint not null auto_increment comment '流水号',
   title_code           varchar(100) comment '标签编号',
   title_info           varchar(200) comment '标签说明',
   title_show_info      varchar(500) comment '标签显示值',
   primary key (id)
);

alter table sys_help_info comment 'test
';

/*==============================================================*/
/* Table: sys_help_info_rel_page                                */
/*==============================================================*/
create table sys_help_info_rel_page
(
   id                   bigint not null auto_increment comment '流水号',
   title_code           varchar(100) comment '标签编号',
   page_url             varchar(500) comment '关联页面地址',
   primary key (id)
);

alter table sys_help_info_rel_page comment '标签关联页面';

/*==============================================================*/
/* Table: third_party_mail_para                                 */
/*==============================================================*/
create table third_party_mail_para
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table third_party_mail_para comment '邮件通道参数';

/*==============================================================*/
/* Table: third_party_sms_para                                  */
/*==============================================================*/
create table third_party_sms_para
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table third_party_sms_para comment '短信通道参数';

/*==============================================================*/
/* Table: third_party_trusteeship_para                          */
/*==============================================================*/
create table third_party_trusteeship_para
(
   id                   int not null auto_increment comment 'id',
   primary key (id)
);

alter table third_party_trusteeship_para comment '托管账号参数';

/*==============================================================*/
/* Table: web_sit_column                                        */
/*==============================================================*/
create table web_sit_column
(
   column_id            int not null auto_increment comment '栏目编号',
   column_name          varchar(100) comment '栏目名称',
   parent_column_id     int comment '上级栏目编号',
   column_remark        varchar(500) comment '栏目说明',
   primary key (column_id)
);

alter table web_sit_column comment '网站栏目';

/*==============================================================*/
/* Table: web_sit_notice                                        */
/*==============================================================*/
create table web_sit_notice
(
   id                   int not null auto_increment comment 'id',
   title                varchar(100) comment '公告标题',
   column_id            int comment '所属栏目',
   sort                 int comment '文章顺序',
   thumbnail_name       varchar(100) comment '缩略图名称',
   thumbnail_path       varchar(200) comment '缩略图路径',
   key_words            varchar(200) comment '文章关键字',
   simple_profile       varchar(500) comment '文章简介',
   content              varchar(2000) comment '文章内容',
   create_dt            datetime comment '创建时间',
   create_user_id       bigint comment '创建人',
   review_dt            datetime comment '审核时间',
   review_user_id       bigint comment '审核人',
   publish_dt           datetime comment '发布时间',
   invalid_dt           datetime comment '失效时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table web_sit_notice comment '栏目公告';




/*==============================================================*/
/* Table: customer_org_finance_year_record                          */
/*==============================================================*/
create table customer_org_finance_year_record
(
   id                   bigint not null auto_increment comment 'id',
   customer_id          bigint comment '会员编号',
   year_id              varchar(4) comment '年度',
   main_revenue         float(8,2) comment '主营收入（万）',
   gross_profit         float(8,2) comment '毛利润（万）',
   net_profit           float(8,2) comment '净利润（万）',
   total_assets         float(8,2) comment '总资产（万）',
   net_assets           float(8,2) comment '净资产（万）',
   primary key (id)
);

alter table customer_org_finance_year_record comment '企业会员财务年表';

/*==============================================================*/
/* Table: customer_org_extend_info                              */
/*==============================================================*/
create table customer_org_extend_info
(
   customer_id          bigint not null auto_increment comment '会员编号',
   org_code             varchar(50) comment '组织机构代码',
   org_business_license varchar(50) comment '营业执照编号',
   org_tax_registration varchar(50) comment '税务登记号',
   registered_ife       int comment '注册年限',
   registered_capital   float(8,2) comment '注册资金（万元）',
   cash_inflows         float(8,2) comment '上年度经营现金流入（万元）',
   industry             varchar(500) comment '行业',
   business_activities  varchar(1000) comment '经营情况',
   litigation_situatio  varchar(1000) comment '涉诉情况',
   credit_record        varchar(1000) comment '征信记录',
   create_user_id       bigint comment '创建人',
   modify_user_id       bigint comment '修改人',
   status               int comment '状态',
   primary key (customer_id)
);

alter table customer_org_extend_info comment '组织会员扩展信息';


drop table if exists p2p_fd_account;

/*==============================================================*/
/* Table: p2p_fd_account                                        */
/*==============================================================*/
create table p2p_fd_account
(
   platform_code        varchar(20) not null comment '平台编号',
   platform_pwd         varchar(200) comment '平台密码',
   signature_certificate_path varchar(100) comment '签名证书地址',
   sign_content         varchar(100) comment '签名内容',
   yeepay_test_url      varchar(100) comment '易宝测试地址',
   yeepay_formal_url    varchar(100) comment '易宝生产环境地址',
   primary key (platform_code)
);

alter table p2p_fd_account comment '平台账号';
drop table if exists p2p_interface_result_code_explain;

/*==============================================================*/
/* Table: p2p_interface_result_code_explain                     */
/*==============================================================*/
create table p2p_interface_result_code_explain
(
   result_code          varchar(20) not null comment '返回码',
   meaning              varchar(100) comment '含义',
   explain_info         varchar(100) comment '客户解释',
   primary key (result_code)
);

alter table p2p_interface_result_code_explain comment '平台返回码释义表';



drop table if exists log_third_party;

/*==============================================================*/
/* Table: log_third_party                                       */
/*==============================================================*/
create table log_third_party
(
   id                   bigint not null auto_increment comment 'id',
   req_no               varchar(50) comment '请求流水号',
   interface_id         varchar(10) comment '关联业务编号',
   input_paras          varchar(1024) comment '入参',
   output_paras         varchar(1024) comment '出参',
   result               varchar(2) comment '结果',
   create_dt            datetime comment '创建时间',
   primary key (id)
);

alter table log_third_party comment '第三方交互日志';

