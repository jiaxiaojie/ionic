/*==============================================================*/
/* 活期项目信息增加赎回规则字段                                 */
/*==============================================================*/
ALTER TABLE `current_project_info`
ADD COLUMN `redeem_rule`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '赎回规则' AFTER `buy_rule`;

/*==============================================================*/
/* 借贷合同明细增加放款时间字段                                 */
/*==============================================================*/
ALTER TABLE `project_base`
ADD COLUMN `loan_dt`  datetime NULL DEFAULT NULL COMMENT '放款时间' AFTER `is_loan`;

/*==============================================================*/
/* 更新赎回规则                                                  */
/*==============================================================*/
update current_project_info set redeem_rule='1、活花生支持随时发起赎回，每日不限赎回次数。
2、赎回金额必须为1元的整数倍，每人每天赎回限额5万元，赎回的资金当日没有利息。
3、工作日15:00前申请赎回，当日到账；工作日15:00后申请赎回，预计次日（双休日或节假日顺延）到账。
4、若遇巨额赎回情况，花生金服有权拒绝用户当日的赎回申请，用户可以次日继续申请赎回，具体以公司通知为准。';

drop table if exists mobile_award_record;

/*==============================================================*/
/* Table: mobile_award_record                                   */
/*==============================================================*/
create table mobile_award_record
(
   id                   bigint not null auto_increment comment '编号',
   activity_id          bigint comment '活动编号',
   mobile               varchar(20) comment '手机号',
   prize_instance_id    bigint comment '奖品实例编号',
   op_dt                datetime comment '获奖时间',
   op_term              varchar(2) comment '获奖终端',
   status               varchar(20) comment '状态',
   account_id           bigint comment '用户编号',
   award_dt             datetime comment '赠送时间',
   primary key (id)
);

alter table mobile_award_record comment '手机号中奖记录';



