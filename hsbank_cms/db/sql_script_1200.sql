-- 客户留言表增加字段 personal_id
ALTER TABLE customer_leave_message ADD `personal_id` bigint(20) DEFAULT NULL COMMENT '私人订制id';

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

-- 增加字典项

delete from sys_dict where id in
('074644c6e6c9459e8a28175b71d2bd1c','24683ef0dca84fa3837c9efa35ee68b0','71943076e51047d890a4015315ae1300',
'ad85af524e6e4e8287cb5bf1a3cc377b','af74f4e25a6b46ff93e1a2b6a9b85078','c40c0e1fcc6c450d98bd0bec8cc8cd3a');
-- 增加权限
delete from sys_menu where id in(
'af283a94869d4f76af0cbde0d0a2a1bb',
'ba533ed3aab64f909b89e9a5c4c589eb',
'1ae6004f079c4e388991f5381a82737f',
'3583881d3b7d40a28498d148785be5ad',
'dc1aed419c46486b9e32e2cc44b26cfd',
'703f9027701c4a34bde5f3fb54481ab3',
'8c76417bc7004ef99b940094a480f891',
'b02d9a8864e242fcabf1a0e5d79325da',
'eb6764e64bfa46afae0c032364ca18d5',
'00f2ffec7a544cd4b730a7339fa98d0a',
'c3291fff87e44119b1ca42a7c10c6053'
);
INSERT INTO sys_menu VALUES ('af283a94869d4f76af0cbde0d0a2a1bb', '1', '0,1,', '私人订制管理', '5180', '', '', '', '1', '', '1', '2016-05-20 13:09:46', '1', '2016-05-20 13:09:46', '', '0');
INSERT INTO sys_menu VALUES ('ba533ed3aab64f909b89e9a5c4c589eb', 'af283a94869d4f76af0cbde0d0a2a1bb', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,', '私人订制管理', '50', '', '', '', '1', '', '1', '2016-05-18 14:22:19', '1', '2016-05-20 13:12:17', '', '0');
INSERT INTO sys_menu VALUES ('1ae6004f079c4e388991f5381a82737f', 'ba533ed3aab64f909b89e9a5c4c589eb', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,', '私人订制审批', '60', '/personal/personalApproved/list', '', '', '1', '', '1', '2016-05-18 15:33:48', '1', '2016-05-18 15:33:48', '', '0');
INSERT INTO sys_menu VALUES ('3583881d3b7d40a28498d148785be5ad', 'ba533ed3aab64f909b89e9a5c4c589eb', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,', '私人订制项目', '30', '/personal/personalTailor/list', '', '', '1', '', '1', '2016-05-18 14:26:40', '1', '2016-05-18 14:26:40', '', '0');
INSERT INTO sys_menu VALUES ('dc1aed419c46486b9e32e2cc44b26cfd', 'ba533ed3aab64f909b89e9a5c4c589eb', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,', '私人订制预约', '120', '/personal/personalTailorSubs/list', '', '', '1', '', '1', '2016-05-19 11:16:48', '1', '2016-05-19 11:16:48', '', '0');
INSERT INTO sys_menu VALUES ('703f9027701c4a34bde5f3fb54481ab3', '1ae6004f079c4e388991f5381a82737f', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,1ae6004f079c4e388991f5381a82737f,', '查看', '30', '', '', '', '0', 'personal:personalApproved:view', '1', '2016-05-18 15:34:19', '1', '2016-05-18 15:34:19', '', '0');
INSERT INTO sys_menu VALUES ('8c76417bc7004ef99b940094a480f891', '1ae6004f079c4e388991f5381a82737f', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,1ae6004f079c4e388991f5381a82737f,', '编辑', '60', '', '', '', '0', 'personal:personalApproved:edit', '1', '2016-05-18 15:34:44', '1', '2016-05-18 15:34:44', '', '0');
INSERT INTO sys_menu VALUES ('b02d9a8864e242fcabf1a0e5d79325da', '3583881d3b7d40a28498d148785be5ad', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,3583881d3b7d40a28498d148785be5ad,', '查看', '30', '', '', '', '0', 'personal:personalTailor:view', '1', '2016-05-18 14:27:06', '1', '2016-05-18 14:27:06', '', '0');
INSERT INTO sys_menu VALUES ('eb6764e64bfa46afae0c032364ca18d5', '3583881d3b7d40a28498d148785be5ad', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,3583881d3b7d40a28498d148785be5ad,', '编辑', '60', '', '', '', '0', 'personal:personalTailor:edit', '1', '2016-05-18 14:39:58', '1', '2016-05-18 14:40:20', '', '0');
INSERT INTO sys_menu VALUES ('00f2ffec7a544cd4b730a7339fa98d0a', 'dc1aed419c46486b9e32e2cc44b26cfd', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,dc1aed419c46486b9e32e2cc44b26cfd,', '编辑', '60', '', '', '', '0', 'personal:personalTailorSubs:edit', '1', '2016-05-19 11:17:59', '1', '2016-05-19 11:17:59', '', '0');
INSERT INTO sys_menu VALUES ('c3291fff87e44119b1ca42a7c10c6053', 'dc1aed419c46486b9e32e2cc44b26cfd', '0,1,af283a94869d4f76af0cbde0d0a2a1bb,ba533ed3aab64f909b89e9a5c4c589eb,dc1aed419c46486b9e32e2cc44b26cfd,', '查看', '30', '', '', '', '0', 'personal:personalTailorSubs:view', '1', '2016-05-19 11:17:19', '1', '2016-05-19 11:17:19', '', '0');
delete from sys_role_menu where  menu_id in(
'af283a94869d4f76af0cbde0d0a2a1bb',
'ba533ed3aab64f909b89e9a5c4c589eb',
'1ae6004f079c4e388991f5381a82737f',
'3583881d3b7d40a28498d148785be5ad',
'dc1aed419c46486b9e32e2cc44b26cfd',
'703f9027701c4a34bde5f3fb54481ab3',
'8c76417bc7004ef99b940094a480f891',
'b02d9a8864e242fcabf1a0e5d79325da',
'eb6764e64bfa46afae0c032364ca18d5',
'00f2ffec7a544cd4b730a7339fa98d0a',
'c3291fff87e44119b1ca42a7c10c6053'
);
INSERT INTO sys_role_menu VALUES ('1', 'af283a94869d4f76af0cbde0d0a2a1bb');
INSERT INTO sys_role_menu VALUES ('1', 'ba533ed3aab64f909b89e9a5c4c589eb');
INSERT INTO sys_role_menu VALUES ('1', '1ae6004f079c4e388991f5381a82737f');
INSERT INTO sys_role_menu VALUES ('1', '3583881d3b7d40a28498d148785be5ad');
INSERT INTO sys_role_menu VALUES ('1', 'dc1aed419c46486b9e32e2cc44b26cfd');
INSERT INTO sys_role_menu VALUES ('1', '703f9027701c4a34bde5f3fb54481ab3');
INSERT INTO sys_role_menu VALUES ('1', '8c76417bc7004ef99b940094a480f891');
INSERT INTO sys_role_menu VALUES ('1', 'b02d9a8864e242fcabf1a0e5d79325da');
INSERT INTO sys_role_menu VALUES ('1', 'eb6764e64bfa46afae0c032364ca18d5');
INSERT INTO sys_role_menu VALUES ('1', '00f2ffec7a544cd4b730a7339fa98d0a');
INSERT INTO sys_role_menu VALUES ('1', 'c3291fff87e44119b1ca42a7c10c6053');


-- 修改project base info 区域字段类型
alter table project_base modify column area_id VARCHAR(64);
delete from sys_dict where id = '9a0fcc40ecd84d3d9ce6a5356b244143';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('9a0fcc40ecd84d3d9ce6a5356b244143', '7', '供应链', 'project_type_dict', '项目类型', '70', '0', '1', '2016-05-23 13:23:44', '1', '2016-05-23 13:27:19', '', '0');

/*==============================================================*/
/* 会员积分汇总表增加连续签到天数                                 */
/*==============================================================*/
ALTER TABLE `customer_integral_snapshot`
ADD COLUMN `consecutive_days`  int(11) NULL DEFAULT NULL COMMENT '连续签到天数' AFTER `outtime_val`;
/*==============================================================*/
/* 初始化值                                 */
/*==============================================================*/
update customer_integral_snapshot set consecutive_days = 0;

/*==============================================================*/
/* 首页轮播增加是否新网站使用标示                                 */
/*==============================================================*/
ALTER TABLE `carousel_info`
ADD COLUMN `is_new_website`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否新网站使用' AFTER `type_code`;


drop table if exists sachet_record;

/*==============================================================*/
/* Table: sachet_record                                         */
/*==============================================================*/
create table sachet_record
(
   id                   bigint not null auto_increment comment '编号',
   activity_id          bigint comment '活动编号',
   account_id           bigint comment '用户编号',
   change_value         int comment '变化数量',
   change_reason        varchar(20) comment '变化原因',
   op_term              varchar(2) comment '操作终端',
   op_dt                datetime comment '操作时间',
   prize                varchar(50) comment '奖品',
   primary key (id)
);

alter table sachet_record comment '香囊记录';


/**
收货地址增加区县ID列
 */
ALTER TABLE `customer_address`
ADD COLUMN `district_id`  varchar(64)  DEFAULT NULL COMMENT '区、县Id';


/** 更新 借贷合同表的字段area_id，上海(80 -> 799) */
UPDATE `project_base` SET area_id=799 WHERE area_id=80;
/** 更新 机构表的字段area_id，上海(80 -> 799)  */
UPDATE `sys_office` SET area_id=799 WHERE area_id=80;

/*==============================================================*/
/* 还原等额本息删除标示                      */
/*==============================================================*/
update sys_dict set del_flag='0' where id='dfca33b2749342279558e495b15b9128';