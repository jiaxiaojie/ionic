drop table if exists european_cup_top_scorer;

/*==============================================================*/
/* Table: european_cup_top_scorer                               */
/*==============================================================*/
create table european_cup_top_scorer
(
   id                   bigint not null auto_increment comment '编号',
   account_id           bigint comment '用户编号',
   activity_id          bigint comment '活动编号',
   total_goals          int comment '累计进球总数',
   used_scratch_times   int comment '已刮奖次数',
   op_dt                datetime comment '操作时间',
   primary key (id)
);

alter table european_cup_top_scorer comment '欧洲杯射手榜';


drop table if exists shoot_record;

/*==============================================================*/
/* Table: shoot_record                                          */
/*==============================================================*/
create table shoot_record
(
   id                   bigint not null auto_increment comment '编号',
   account_id           bigint comment '用户',
   activity_id          bigint comment '活动编号',
   op_dt                datetime comment '射门时间',
   op_term              varchar(2) comment '射门终端',
   is_goal              varchar(2) comment '是否进球(0:否，1:是)',
   primary key (id)
);

alter table shoot_record comment '射门记录';


/* 修改变更类型customer_balance_change_type_dict中的活期提取利息为活期提取收益 */
update sys_dict set label='提取活期产品收益' where id='47eafb19b33c4339bbcef7fd48e1f922';
update current_account_interest_change_his set change_reason='提取活期产品收益' where change_reason='提取活期产品利息';
UPDATE customer_balance_his SET change_reason = REPLACE(change_reason, '利息', '收益') where change_type='10';



/*shoot_record增加状态字段*/
ALTER TABLE `shoot_record`
ADD COLUMN `status`  varchar(20)  DEFAULT NULL COMMENT '状态';

/*活动管理增加type数据为1（活动）*/
UPDATE cms_activity SET type='1';

/*订单表integral_mall_product_order增加district_id（区域）字段*/
ALTER TABLE integral_mall_product_order ADD COLUMN district_id  varchar(64)  DEFAULT NULL COMMENT '区域' AFTER post_code;
