
/*==============================================================*/
/* 更新到期还本付息项目还款计划剩余本息                      */
/*==============================================================*/
update project_repayment_plan a, project_base b
   set a.remaining_principal_interest = 0
 where a.project_id = b.project_id
   and a.remaining_principal_interest is null
   and b.repayment_mode = '1';

-- 文章数据表增加 来源logo(图片路径)
ALTER TABLE `cms_article_data` ADD COLUMN `copy_from_logo` varchar(1024);

-- 增加公司动态分类数据
INSERT INTO `cms_category` (`id`, `parent_id`, `parent_ids`, `site_id`, `office_id`, `module`, `name`, `image`, `href`, `target`, `description`, `keywords`, `sort`, `in_menu`, `in_list`, `show_modes`, `allow_comment`, `is_audit`, `custom_list_view`, `custom_content_view`, `view_config`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('13ee4e7f1dc34b04b65ee815a84dcc1b', '03d180e235284784acab64febd300a7d', '0,1,03d180e235284784acab64febd300a7d,', '1', '1', 'article', '公司动态', '', '', '', '', '', '75', '0', '1', '0', '0', '0', '', '', '', '1', '2016-05-31 14:17:38', '1', '2016-05-31 14:17:38', NULL, '0');

-- 添加消息行为编码
delete from message_behavior_type where behavior_code = '1210';
INSERT INTO `message_behavior_type` (`behavior_code`, `behavior_name`) VALUES ('1210', '抽奖');

--广告位位置字典增加键值1000（欢迎页）
delete from sys_dict where id='e64d17e40cc540818541ab11abf3037c';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e64d17e40cc540818541ab11abf3037c', '1000', '欢迎页', 'ad_code', '广告位代码', '0', '0', '1', '2016-06-01 15:32:12', '1', '2016-06-01 15:32:12', '欢迎页', '0');


/*=====================================================================2016-06-02 start========================================================================================================*/

/*==============================================================*/
/* 活期项目增加项目提示字段                                 */
/*==============================================================*/
ALTER TABLE `current_project_info`
ADD COLUMN `tips`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目提示' AFTER `name`;


-- 增加私人订制表和私人订制日志表
drop table if exists project_personal_tailor;
/*==============================================================*/
/* Table: project_personal_tailor                               */
/*==============================================================*/
create table project_personal_tailor
(
   id                   bigint not null auto_increment comment '项目流水线',
   name                 varchar(128) comment '项目名称',
   code                 varchar(32) comment '项目编号',
   type                 char(1) comment '项目类型',
   amount               varchar(32) comment '项目金额',
   duration             varchar(32) comment '项目期限',
   desc_pic             text comment '项目描述图片',
   state                char(1) comment '状态( 0 草稿 1 审核 2 通过 3 不通过 )',
   rate                 varchar(32) comment '最低年化利率',
   deadline             datetime comment '投标截止日期',
   publish_time         datetime comment '发布时间',
   starting_amount      varchar(32) comment '起投金额',
   create_by            bigint comment '创建者',
   create_date          datetime comment '创建时间',
   update_by            bigint comment '更新者',
   update_date          datetime comment '更新时间',
   remarks              varchar(255) comment '备注信息',
   primary key (id)
);

alter table project_personal_tailor comment '私人订制项目';

update customer_account set avatar_image='/static/modules/front/images/util/sex/man.png' where avatar_image='/static/modules/front/images/generate/sex/man.png';


/*=====================================================================2016-06-06 start========================================================================================================*/

/**
更改还款方式字典	project_repayment_mode_dict中的一次性还款付息 为到期还本付息
 */
update sys_dict set label='到期还本付息' where id='d33db02ce39c4ba59337943f831f75d2';

/*=====================================================================2016-06-12 start========================================================================================================*/

/*==============================================================*/
/* 修改项目还款计划表字段类型                                 */
/*==============================================================*/
ALTER TABLE `project_repayment_plan`
MODIFY COLUMN `plan_money`  decimal(13,2) NULL DEFAULT NULL COMMENT '还款金额' AFTER `plan_date`,
MODIFY COLUMN `principal`  decimal(13,2) NULL DEFAULT NULL COMMENT '本金' AFTER `plan_money`,
MODIFY COLUMN `interest`  decimal(13,2) NULL DEFAULT NULL COMMENT '利息' AFTER `principal`,
MODIFY COLUMN `remaining_principal`  decimal(13,2) NULL DEFAULT NULL COMMENT '剩余本金' AFTER `rate_ticket_interest`,
MODIFY COLUMN `remaining_principal_interest`  decimal(13,2) NULL DEFAULT NULL COMMENT '剩余本息' AFTER `remaining_principal`;



/*=====================================================================2016-06-13 start========================================================================================================*/
/**
在表cms_activity中增加字段type,target
 */
ALTER TABLE `cms_activity`
ADD COLUMN `type`  varchar(2)  NULL  DEFAULT NULL COMMENT '类型' AFTER `activity_join`,
ADD COLUMN `target`  varchar(300)  NULL  DEFAULT NULL COMMENT '类型' AFTER `type`;

;
