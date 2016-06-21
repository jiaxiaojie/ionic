drop table if exists integral_mall_product_type;

/*==============================================================*/
/* Table: integral_mall_product_type                            */
/*==============================================================*/
create table integral_mall_product_type
(
   type_id              bigint not null auto_increment comment 'type_id',
   type_name            varchar(200) comment 'type_name',
   parent_type_id       bigint comment 'parent_type_id',
   type_log             varchar(50) comment 'type_log',
   primary key (type_id)
);

alter table integral_mall_product_type comment '上架产品类别';


drop table if exists integral_mall_product;

/*==============================================================*/
/* Table: integral_mall_product                                 */
/*==============================================================*/
create table integral_mall_product
(
   product_id           bigint not null auto_increment comment '产品编号',
   product_name         varchar(300) comment '产品名称',
   product_type_id      bigint comment '产品类别',
   product_logo_min     varchar(300) comment '产品logo1',
   product_logo_normal  varchar(300) comment '产品logo2',
   product_logo_max     varchar(300) comment '产品logo3',
   product_introduction text comment '产品简介',
   is_recommend         varchar(2) comment '是否重点推荐',
   price                int comment '兑换价格',
   up_dt                datetime comment '上架时间',
   dow_dt               datetime comment '下架时间',
   product_count        int comment '上架数量',
   product_surplus      int comment '剩余数量',
   create_dt            datetime comment '创建时间',
   create_user_id       bigint comment '创建人',
   review_dt            datetime comment '审批时间',
   review_user_id       bigint comment '审批人',
   review_remark        varchar(200) comment '审批意见',
   status               varchar(2) comment '产品状态',
   primary key (product_id)
);

alter table integral_mall_product comment '上架产品';

drop table if exists integral_mall_product_hot;

/*==============================================================*/
/* Table: integral_mall_product_hot                             */
/*==============================================================*/
create table integral_mall_product_hot
(
   id                   bigint not null auto_increment comment 'id',
   product_id           bigint comment '产品编号',
   hot_type             varchar(2) comment '标签类型',
   hot_value            varchar(200) comment '标签值',
   primary key (id)
);

alter table integral_mall_product_hot comment '产品活动标签';

drop table if exists integral_mall_product_sps;

/*==============================================================*/
/* Table: integral_mall_product_sps                             */
/*==============================================================*/
create table integral_mall_product_sps
(
   para_id              bigint not null auto_increment comment '参数编号',
   product_id           bigint comment '产品编号',
   para_name            varchar(40) comment '参数名称',
   para_val             varchar(200) comment '参数值',
   para_seq             int comment '参数顺序',
   status               int comment '状态',
   primary key (para_id)
);

alter table integral_mall_product_sps comment '产品规格参数系列';

drop table if exists integral_mall_product_price;

/*==============================================================*/
/* Table: integral_mall_product_price                           */
/*==============================================================*/
create table integral_mall_product_price
(
   id                   bigint not null auto_increment comment 'id',
   product_id           bigint comment '产品编号',
   price_type           varchar(2) comment '产品价格类型',
   market_new_price     float(10,2) comment '活动价格',
   market_discount      float(3,2) comment '活动折扣',
   begin_dt             datetime comment '开始时间',
   end_dt               datetime comment '结束时间',
   status               varchar(2) comment '状态',
   create_user_id       bigint comment '创建人',
   create_dt            datetime comment '创建时间',
   primary key (id)
);

alter table integral_mall_product_price comment '产品价格策略';

drop table if exists customer_address;

/*==============================================================*/
/* Table: customer_address                                      */
/*==============================================================*/
create table customer_address
(
   id                   bigint not null auto_increment comment '地址编号',
   account_id           bigint comment '用户编号',
   show_name            varchar(100) comment '收件人名称',
   mobile               varchar(20) comment '收件人手机号',
   address              varchar(500) comment '收件人地址',
   post_code            varchar(6) comment '收件人邮编',
   is_default           varchar(1) comment '是否缺省收件地址',
   create_dt            datetime comment '创建时间',
   status               varchar(2) comment '状态',
   primary key (id)
);

alter table customer_address comment '积分商城用户地址';
drop table if exists integral_mall_product_order;

/*==============================================================*/
/* Table: integral_mall_product_order                           */
/*==============================================================*/
create table integral_mall_product_order
(
   id                   bigint not null auto_increment comment '订单编号',
   product_type_id      int comment '产品类型',
   product_id           bigint comment '产品编号',
   product_count        int comment '产品数量',
   customer_account     bigint comment '用户编号',
   address_id           bigint comment '用户住址编号',
   order_status         varchar(2) comment '订单状态',
   flow_user_id         bigint comment '跟踪人员',
   create_dt            datetime comment '创建时间',
   create_channel_id    varchar(2) comment '创建渠道',
   primary key (id)
);

alter table integral_mall_product_order comment '积分商城订单';

/*==============================================================*/
/*	增加:积分公告栏目                                                                                                                         */
/*==============================================================*/
delete  from cms_category  where id='7aca1e25667841cfa4e13d12f689e9e7';
INSERT INTO `cms_category` VALUES ('7aca1e25667841cfa4e13d12f689e9e7', '03d180e235284784acab64febd300a7d', '0,1,03d180e235284784acab64febd300a7d,', '1', '1', 'article', '积分公告', '', '', '', '', '', 80, '0', '0', '0', '0', '0', '', '', '', '1', '2015-9-21 10:27:18', '1', '2015-9-21 13:27:49', NULL, '0');

/*==============================================================*/
/*	增加:积分商城菜单                                                                                                                                 	    */
/*==============================================================*/
delete from sys_menu where id in ('15ceb9510c9d4b6dbabd037bd6625897','561f5393aec3499095d3764d1a30a54d','568cad2d11194f0b9c833b6a10b6c668','598b7e749a4c409bb9fe6eb8217f8cd2','83d6f5e906b04762af1beb734792d161','970627ce8c44445bbe2b21f50c42bdbe','98857a2c6c7d4f608e15d897d7e05492','a306801977664300931bc49183c04992',
'a6311bf6649e4c609a9a198c4bd451c3','b33d0322081e4e7ab1fdb819c69f528e','be74c518fa3e4994adbcd683e2e0c24f','c674c71f611c4948aecfa2421ed341fa','c84e96893f344a958779f5e7170d0ccd','ce9f282f175b4831bebc7736f01f613a','d5656230c8674f24baec0db909b85cf9','e41da7755b5c45408b5ad07f627274ba','fd72ff64f8434a1598a18d79b486c37a',
'21d32a3408444b618397f23c61bc72b6','146c3f3623f64537be99d867874bc29a','613e9e87046a46348f7b66c321c9014d','6da0e1bc12874a3382bca31e3ce14d94');

INSERT INTO `sys_menu` VALUES ('146c3f3623f64537be99d867874bc29a', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品类别-查看', 360, '', '', '', '0', 'integral:integralMallProductType:view', '1', '2015-9-23 15:52:45', '1', '2015-9-23 15:52:45', '', '0');
INSERT INTO `sys_menu` VALUES ('15ceb9510c9d4b6dbabd037bd6625897', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '编辑', 60, '', '', '', '0', 'integral:integralMallProduct:edit', '1', '2015-9-21 17:47:44', '1', '2015-9-21 17:47:44', '', '0');
INSERT INTO `sys_menu` VALUES ('561f5393aec3499095d3764d1a30a54d', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品规格-编辑', 210, '', '', '', '0', 'integral:integralMallProductSps:edit', '1', '2015-9-22 16:20:27', '1', '2015-9-22 16:20:27', '', '0');
INSERT INTO `sys_menu` VALUES ('568cad2d11194f0b9c833b6a10b6c668', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '审批产品', 150, '/integral/integralMallProduct/reviewList', '', '', '1', '', '1', '2015-9-21 17:59:28', '1', '2015-9-22 18:40:03', '', '0');
INSERT INTO `sys_menu` VALUES ('598b7e749a4c409bb9fe6eb8217f8cd2', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品活动-查看', 300, '', '', '', '0', 'integral:integralMallProductHot:view', '1', '2015-9-22 16:23:04', '1', '2015-9-22 16:23:19', '', '0');
INSERT INTO `sys_menu` VALUES ('613e9e87046a46348f7b66c321c9014d', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品类别-编辑', 390, '', '', '', '0', 'integral:integralMallProductType:edit', '1', '2015-9-23 15:53:13', '1', '2015-9-23 15:53:13', '', '0');
INSERT INTO `sys_menu` VALUES ('6da0e1bc12874a3382bca31e3ce14d94', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品类别', 110, '/integral/integralMallProductType/list', '', '', '1', '', '1', '2015-9-23 15:51:30', '1', '2015-9-23 15:51:30', '', '0');
INSERT INTO `sys_menu` VALUES ('83d6f5e906b04762af1beb734792d161', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '创建产品', 120, '/integral/integralMallProduct/list', '', '', '1', '', '1', '2015-9-21 17:51:14', '1', '2015-9-21 17:51:14', '', '0');
INSERT INTO `sys_menu` VALUES ('970627ce8c44445bbe2b21f50c42bdbe', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '查看', 30, '', '', '', '0', 'integral:integralMallProduct:view', '1', '2015-9-21 17:47:01', '1', '2015-9-21 17:47:01', '', '0');
INSERT INTO `sys_menu` VALUES ('98857a2c6c7d4f608e15d897d7e05492', 'a6311bf6649e4c609a9a198c4bd451c3', '0,1,21d32a3408444b618397f23c61bc72b6,a6311bf6649e4c609a9a198c4bd451c3,', '查看', 30, '', '', '', '0', 'integral:integralMallProductOrder:view', '1', '2015-9-23 11:43:53', '1', '2015-9-23 11:43:53', '', '0');
INSERT INTO `sys_menu` VALUES ('a306801977664300931bc49183c04992', '21d32a3408444b618397f23c61bc72b6', '0,1,21d32a3408444b618397f23c61bc72b6,', '产品管理', 30, '', '', '', '1', '', '1', '2015-9-21 17:42:46', '1', '2015-9-21 17:42:46', '', '0');
INSERT INTO `sys_menu` VALUES ('a6311bf6649e4c609a9a198c4bd451c3', '21d32a3408444b618397f23c61bc72b6', '0,1,21d32a3408444b618397f23c61bc72b6,', '订单管理', 60, '', '', '', '1', '', '1', '2015-9-23 11:41:58', '1', '2015-9-23 11:41:58', '', '0');
INSERT INTO `sys_menu` VALUES ('b33d0322081e4e7ab1fdb819c69f528e', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品规格-查看', 180, '', '', '', '0', 'integral:integralMallProductSps:view', '1', '2015-9-22 16:19:38', '1', '2015-9-22 16:19:38', '', '0');
INSERT INTO `sys_menu` VALUES ('be74c518fa3e4994adbcd683e2e0c24f', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品价格-查看', 240, '', '', '', '0', 'integral:integralMallProductPrice:view', '1', '2015-9-22 16:21:37', '1', '2015-9-22 16:21:37', '', '0');
INSERT INTO `sys_menu` VALUES ('c674c71f611c4948aecfa2421ed341fa', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '查看产品', 160, '/integral/integralMallProduct/querylist', '', '', '1', '', '1', '2015-9-23 09:34:24', '1', '2015-9-23 09:35:54', '', '0');
INSERT INTO `sys_menu` VALUES ('c84e96893f344a958779f5e7170d0ccd', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品活动-编辑', 330, '', '', '', '0', 'integral:integralMallProductHot:edit', '1', '2015-9-22 16:23:50', '1', '2015-9-22 16:23:50', '', '0');
INSERT INTO `sys_menu` VALUES ('ce9f282f175b4831bebc7736f01f613a', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '审批', 90, '', '', '', '0', 'integral:integralMallProduct:review', '1', '2015-9-21 17:48:58', '1', '2015-9-21 17:48:58', '', '0');
INSERT INTO `sys_menu` VALUES ('d5656230c8674f24baec0db909b85cf9', 'a306801977664300931bc49183c04992', '0,1,21d32a3408444b618397f23c61bc72b6,a306801977664300931bc49183c04992,', '产品价格-编辑', 270, '', '', '', '0', 'integral:integralMallProductPrice:edit', '1', '2015-9-22 16:22:10', '1', '2015-9-22 16:22:10', '', '0');
INSERT INTO `sys_menu` VALUES ('e41da7755b5c45408b5ad07f627274ba', 'a6311bf6649e4c609a9a198c4bd451c3', '0,1,21d32a3408444b618397f23c61bc72b6,a6311bf6649e4c609a9a198c4bd451c3,', '编辑', 60, '', '', '', '0', 'integral:integralMallProductOrder:edit', '1', '2015-9-23 11:44:19', '1', '2015-9-23 11:44:19', '', '0');
INSERT INTO `sys_menu` VALUES ('fd72ff64f8434a1598a18d79b486c37a', 'a6311bf6649e4c609a9a198c4bd451c3', '0,1,21d32a3408444b618397f23c61bc72b6,a6311bf6649e4c609a9a198c4bd451c3,', '订单管理', 90, '/integral/integralMallProductOrder/list', '', '', '1', '', '1', '2015-9-23 11:46:09', '1', '2015-9-23 12:23:25', '', '0');
INSERT INTO `sys_menu` VALUES ('21d32a3408444b618397f23c61bc72b6', '1', '0,1,', '积分商城', 1200, '', '', '', '1', '', '1', '2015-9-21 16:36:25', '1', '2015-9-21 16:36:25', '', '0');

/*==============================================================*/
/*	增加:积分商城菜单角色授权                                                                                                                          	*/
/*==============================================================*/
delete  from sys_role_menu where menu_id in  ('15ceb9510c9d4b6dbabd037bd6625897','561f5393aec3499095d3764d1a30a54d','568cad2d11194f0b9c833b6a10b6c668','598b7e749a4c409bb9fe6eb8217f8cd2','83d6f5e906b04762af1beb734792d161','970627ce8c44445bbe2b21f50c42bdbe','98857a2c6c7d4f608e15d897d7e05492','a306801977664300931bc49183c04992',
'a6311bf6649e4c609a9a198c4bd451c3','b33d0322081e4e7ab1fdb819c69f528e','be74c518fa3e4994adbcd683e2e0c24f','c674c71f611c4948aecfa2421ed341fa','c84e96893f344a958779f5e7170d0ccd','ce9f282f175b4831bebc7736f01f613a','d5656230c8674f24baec0db909b85cf9','e41da7755b5c45408b5ad07f627274ba','fd72ff64f8434a1598a18d79b486c37a',
'21d32a3408444b618397f23c61bc72b6','146c3f3623f64537be99d867874bc29a','613e9e87046a46348f7b66c321c9014d','6da0e1bc12874a3382bca31e3ce14d94');

INSERT INTO `sys_role_menu` VALUES ('1', '146c3f3623f64537be99d867874bc29a');
INSERT INTO `sys_role_menu` VALUES ('1', '15ceb9510c9d4b6dbabd037bd6625897');
INSERT INTO `sys_role_menu` VALUES ('1', '21d32a3408444b618397f23c61bc72b6');
INSERT INTO `sys_role_menu` VALUES ('1', '561f5393aec3499095d3764d1a30a54d');
INSERT INTO `sys_role_menu` VALUES ('1', '568cad2d11194f0b9c833b6a10b6c668');
INSERT INTO `sys_role_menu` VALUES ('1', '598b7e749a4c409bb9fe6eb8217f8cd2');
INSERT INTO `sys_role_menu` VALUES ('1', '613e9e87046a46348f7b66c321c9014d');
INSERT INTO `sys_role_menu` VALUES ('1', '6da0e1bc12874a3382bca31e3ce14d94');
INSERT INTO `sys_role_menu` VALUES ('1', '83d6f5e906b04762af1beb734792d161');
INSERT INTO `sys_role_menu` VALUES ('1', '970627ce8c44445bbe2b21f50c42bdbe');
INSERT INTO `sys_role_menu` VALUES ('1', '98857a2c6c7d4f608e15d897d7e05492');
INSERT INTO `sys_role_menu` VALUES ('1', 'a306801977664300931bc49183c04992');
INSERT INTO `sys_role_menu` VALUES ('1', 'a6311bf6649e4c609a9a198c4bd451c3');
INSERT INTO `sys_role_menu` VALUES ('1', 'b33d0322081e4e7ab1fdb819c69f528e');
INSERT INTO `sys_role_menu` VALUES ('1', 'be74c518fa3e4994adbcd683e2e0c24f');
INSERT INTO `sys_role_menu` VALUES ('1', 'c674c71f611c4948aecfa2421ed341fa');
INSERT INTO `sys_role_menu` VALUES ('1', 'c84e96893f344a958779f5e7170d0ccd');
INSERT INTO `sys_role_menu` VALUES ('1', 'ce9f282f175b4831bebc7736f01f613a');
INSERT INTO `sys_role_menu` VALUES ('1', 'd5656230c8674f24baec0db909b85cf9');
INSERT INTO `sys_role_menu` VALUES ('1', 'e41da7755b5c45408b5ad07f627274ba');
INSERT INTO `sys_role_menu` VALUES ('1', 'fd72ff64f8434a1598a18d79b486c37a');


/*==============================================================*/
/*	增加:产品状态数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type ='integral_project_status_dict';
INSERT INTO `sys_dict` VALUES ('0116aecdac35417db9d2f90652c9521b', '0', '初始化创建', 'integral_project_status_dict', '积分商城产品状态', 20, '0', '1', '2015-9-21 15:44:29', '1', '2015-9-21 15:44:29', '', '0');
INSERT INTO `sys_dict` VALUES ('63685bd27193438d9e0bbb3e1194f852', '1', '审批通过', 'integral_project_status_dict', '积分商城产品状态', 30, '0', '1', '2015-9-21 15:45:57', '1', '2015-9-21 15:45:57', '', '0');
INSERT INTO `sys_dict` VALUES ('a5d30849b3cb4536a7b87cc22d3c0cbb', '-1', '取消', 'integral_project_status_dict', '积分商城产品状态', 10, '0', '1', '2015-9-21 15:43:27', '1', '2015-9-21 15:43:27', '', '0');

/*==============================================================*/
/*	增加:有效状态数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type ='integral_mall_status_dict';
INSERT INTO `sys_dict` VALUES ('4519b95195704ce0b362238da644735e', '0', '无效', 'integral_mall_status_dict', '有效状态', 20, '0', '1', '2015-9-21 15:49:17', '1', '2015-9-21 15:49:17', '', '0');
INSERT INTO `sys_dict` VALUES ('9f68a4313d0e4645acdf0ab3287964db', '1', '正常', 'integral_mall_status_dict', '有效状态', 10, '0', '1', '2015-9-21 15:47:58', '1', '2015-9-21 15:47:58', '', '0');

/*==============================================================*/
/*	增加:订单状态数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type ='integral_mall_order_status_dict';
INSERT INTO `sys_dict` VALUES ('51d0c3c6bbff4780bdb9a5351b2796cb', '1', '确认', 'integral_mall_order_status_dict', '积分商城订单状态', 30, '0', '1', '2015-9-21 15:53:30', '1', '2015-9-23 15:38:59', '', '0');
INSERT INTO `sys_dict` VALUES ('6db68d00be6d40ad8143cea126db727f', '5', '删除', 'integral_mall_order_status_dict', '积分商城订单状态', 110, '0', '1', '2015-9-23 15:41:33', '1', '2015-9-23 15:41:33', '', '0');
INSERT INTO `sys_dict` VALUES ('74250f5f98d14a32ad89f1d3446f0980', '3', '已收货', 'integral_mall_order_status_dict', '积分商城订单状态', 70, '0', '1', '2015-9-23 15:40:26', '1', '2015-9-23 15:40:26', '', '0');
INSERT INTO `sys_dict` VALUES ('9714551771a44dbcb06a3cc8279abe4b', '0', '下单', 'integral_mall_order_status_dict', '积分商城订单状态', 20, '0', '1', '2015-9-21 15:52:22', '1', '2015-9-23 15:38:47', '', '0');
INSERT INTO `sys_dict` VALUES ('a593dfa781f34548a43455412ee92ce0', '4', '订单完成', 'integral_mall_order_status_dict', '积分商城订单状态', 90, '0', '1', '2015-9-23 15:41:02', '1', '2015-9-23 15:41:02', '', '0');
INSERT INTO `sys_dict` VALUES ('ac259a61bbaa45379516bcfb24b9fdf7', '2', '已发货', 'integral_mall_order_status_dict', '积分商城订单状态', 50, '0', '1', '2015-9-23 15:39:49', '1', '2015-9-23 15:39:49', '', '0');
INSERT INTO `sys_dict` VALUES ('b28ba42c096a4088afd2cdd14bd28180', '-1', '取消', 'integral_mall_order_status_dict', '积分商城订单状态', 10, '0', '1', '2015-9-21 15:50:28', '1', '2015-9-21 15:50:28', '', '0');

/*==============================================================*/
/*	增加:产品价格类型数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type ='integral_project_price_type_dict';
INSERT INTO `sys_dict` VALUES ('1d59a499e0f4474f958dae672081ad70', '1', '标新价', 'integral_project_price_type_dict', '产品价格类型', 10, '0', '1', '2015-9-23 15:32:33', '1', '2015-9-23 15:32:33', '', '0');
INSERT INTO `sys_dict` VALUES ('3bcad8975a6a49f18683a908218b52c6', '2', '折扣', 'integral_project_price_type_dict', '产品价格类型', 10, '0', '1', '2015-9-23 15:32:56', '1', '2015-9-23 15:32:56', '', '0');
/*==============================================================*/
/*	增加:产品标签类型数据字典                                                                                                                         		*/
/*==============================================================*/
delete from sys_dict where type ='integral_project_hot_type_dict';
INSERT INTO `sys_dict` VALUES ('0338677864e94b1599b4eb01686a73ed', '2', '左上角', 'integral_project_hot_type_dict', '产品标签类型', 30, '0', '1', '2015-9-23 16:28:42', '1', '2015-9-23 16:28:42', '', '0');
INSERT INTO `sys_dict` VALUES ('783fe41cbff745abb85d378be50d9b16', '1', '右上角', 'integral_project_hot_type_dict', '产品标签类型', 10, '0', '1', '2015-9-23 16:28:19', '1', '2015-9-23 16:28:19', '', '0');

/*==============================================================*/
/*	长期营销活动
 *  投资送积分活动                                                                                                                         		*/
/*==============================================================*/
delete from marketing_activity_info where acticity_id=15;
INSERT INTO `marketing_activity_info` VALUES (15, '投资送积分', '2015-9-21', '2099-12-31', '00:00:00', '23:59:59', 'marketActivity1003Handler', '规则1：投资送积分，每1元一个积分，不足一元不参与计算。', 1, '2015-9-25 14:29:16', 1, '2015-9-25 14:30:09', '1');
delete from marketing_activity_channel_limit where activity_id=15;
INSERT INTO `marketing_activity_channel_limit` VALUES (113, 15, 0);
INSERT INTO `marketing_activity_channel_limit` VALUES (114, 15, 1);
INSERT INTO `marketing_activity_channel_limit` VALUES (115, 15, 2);
INSERT INTO `marketing_activity_channel_limit` VALUES (112, 15, 3);
delete from marketing_activity_user_behavior_limit where activity_code=15 ;
INSERT INTO `marketing_activity_user_behavior_limit` VALUES (82, 15, '1130');
delete from sys_dict where id='e327d062ec274b7ea730aee46486fc1f';
INSERT INTO `sys_dict` VALUES ('e327d062ec274b7ea730aee46486fc1f', '3', '投资赠送', 'customer_integral_change_reason_dict', '会员积分变更原因', 40, '0', '1', '2015-09-25 13:37:25', '1', '2015-09-25 13:37:25', '', '0');

ALTER TABLE `integral_mall_product_order`
ADD COLUMN `order_no`  varchar(50) NULL COMMENT '订单号' AFTER `create_channel_id`,
ADD COLUMN `show_name`  varchar(100) NULL COMMENT '收件人姓名' AFTER `order_no`,
ADD COLUMN `mobile`  varchar(20) NULL COMMENT '收件人手机号' AFTER `show_name`,
ADD COLUMN `address_show`  varchar(500) NULL COMMENT '收件人地址' AFTER `mobile`,
ADD COLUMN `post_code`  varchar(6) NULL COMMENT '收件人邮编' AFTER `address_show`;

ALTER TABLE `integral_mall_product_order`
ADD COLUMN `product_price`  int NULL COMMENT '产品单价' AFTER `product_count`,
ADD COLUMN `price`  int NULL COMMENT '总价' AFTER `product_price`;

ALTER TABLE `integral_mall_product`
ADD COLUMN `rel_ticket_id`  bigint NULL COMMENT '关联券编号' AFTER `status`;

/*==============================================================*/
/* 添加会员积分变更原因字典数据                                  */
/*==============================================================*/
delete from sys_dict where type='customer_integral_change_reason_dict';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('7fe4dbabaaa54833b75460353922f6e4', '1', '消费', 'customer_integral_change_reason_dict', '会员积分变更原因', '20', '0', '1', '2015-07-23 21:14:20', '1', '2015-08-17 11:11:45', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('d7afb553e6d04177b32b06f221933f82', '4', '兑换商品', 'customer_integral_change_reason_dict', '会员积分变更原因', '50', '0', '1', '2015-09-28 19:49:00', '1', '2015-09-28 19:49:00', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e086d777107a47ada5dfdb323323d9a8', '0', '签到', 'customer_integral_change_reason_dict', '会员积分变更原因', '10', '0', '1', '2015-07-23 21:14:04', '1', '2015-08-17 11:11:30', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e1370c48de43440fba4962624dff5ecd', '2', '推荐好友赠送', 'customer_integral_change_reason_dict', '会员积分变更原因', '30', '0', '1', '2015-08-25 10:07:14', '1', '2015-08-25 10:07:14', '', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('e327d062ec274b7ea730aee46486fc1f', '3', '投资赠送', 'customer_integral_change_reason_dict', '会员积分变更原因', '40', '0', '1', '2015-09-25 13:37:25', '1', '2015-09-25 13:37:25', '', '0');