/*==============================================================*/
/*	修改:项目类型数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type='project_type_dict';
INSERT INTO `sys_dict` VALUES ('6683c6777af941939680fb7496dad0fe', '1', '抵押', 'project_type_dict', '项目类型', 10, '0', '1', '2015-6-23 13:58:30', '1', '2015-7-10 17:53:27', '', '0');
INSERT INTO `sys_dict` VALUES ('f132c1dc876747da9c15837c851501f8', '2', '个人信用贷', 'project_type_dict', '项目类型', 20, '0', '1', '2015-6-23 13:59:07', '1', '2015-7-10 17:53:37', '', '0');
INSERT INTO `sys_dict` VALUES ('86d85bbe951a4ec49b4fd2319b831732', '3', '商圈贷', 'project_type_dict', '项目类型', 30, '0', '1', '2015-6-23 13:57:52', '1', '2015-7-10 17:53:19', '', '0');
INSERT INTO `sys_dict` VALUES ('abdf29cc86724f0499f7bea5c7cc85ee', '4', '质押', 'project_type_dict', '项目类型', 40, '0', '1', '2015-6-23 13:59:50', '1', '2015-9-8 14:30:58', '', '0');
INSERT INTO `sys_dict` VALUES ('ad1ab136c72e4700bcccc4dccdc123a4', '5', '融资租赁', 'project_type_dict', '项目类型', 50, '0', '1', '2015-6-23 14:00:16', '1', '2015-7-9 17:04:29', '', '0');
INSERT INTO `sys_dict` VALUES ('12b114911c2844378c5c2520703eb9d5', '6', '资管计划', 'project_type_dict', '项目类型', 60, '0', '1', '2015-10-13 10:04:36', '1', '2015-10-13 10:04:36', '', '0');

/*==============================================================*/
/*	更新项目数据:把项目的原类型 "房产质押" 改为  "质押"                                                                                                                       		*/
/*==============================================================*/
update project_base t set t.project_type_code='4' where t.project_type_code='5';


drop table if exists customer_client_token;

/*==============================================================*/
/* Table: customer_client_token                            */
/*==============================================================*/
create table customer_client_token
(
   customer_id          bigint not null COMMENT '会员编号',
   token                varchar(50) COMMENT '令牌',
   last_dt              datetime COMMENT '最后一次更改时间',
   primary key (customer_id)
);

alter table customer_client_token comment '客户端缓存信息';