修改:project_execute_snapshot表end_finance_money字段类型为decimal(13,2)									--ydt
增加:移动端版本mark数据字典 和移动端版本菜单项																	--wanduanrui
project_base表添加列：sort_in_list(int,'在列表中的排序'),sort_in_index(int,'在首页中的排序')					--ydt
增加:项目前端排序管理菜单项及分配此菜单权限给管理员																--ydt
增加:营销活动奖励记录表																					--ydt
增加:营销活动行为数据字典																					--ydt
增加:营销活动奖励产品类型数据字典																			--ydt
增加:活动奖励管理、查看奖励、补送奖励菜单 并将菜单权限分配给管理员													--ydt
增加:投资券类型状态数据字典																				--ydt
增加:项目显示终端表																						--ydt
增加:会员信息查询菜单项 ,银行信息管理菜单项 																	--wanduanrui
增加:轮播图信息表                                                                                                                                                                                                                                                                 --ydt
增加:bank_info表及其初始化数据                                                                                                                                                                                                                               --wanduanrui                                                                                                                                                                                      --wanduanrui
增加：推广渠道信息表(ad_channel_info)																	--ydt
customer_account表添加列:ad_channel_id(varchar(50),'推广渠道来源')										--ydt
增加：customer_balance表添加列:will_principal(float(13,2),'待收本金')										--lizibo
增加：推广渠道信息菜单项     																				--wanduanrui
marketing_activity_award_record表添加列:cause_type(varchar(2),'奖励来源类型'),cause_id(bigint,'来源编号')	--ydt
增加：活动奖励来源类型字典																					--ydt
customer_integral_his表添加列:change_type(varchar(2),'变更类型')										--ydt
删除：会员花生豆变更原因字典																				--ydt
增加：会员花生豆变更类型字典																				--ydt
增加:注册渠道统计菜单项          																				--wanduanrui
增加:渠道管理菜单项                  																				--wanduanrui
设置项目的默认sort_in_list,sort_in_index值																--ydt
设置营销活动1002,1004为不启用状态																			--ydt
增加：积分商城用户地址状态数据字典																		    --lizibo
增加：移动端版本参数表																					--lizibo
增加：新手任务表																						--lizibo
增加：表customer_client_token增加列：term_type(varchar(2),'终端类型')									--lizibo
修改：customer_client_token(修改主键 customer_id 为 customer_id + term_type)							--lizibo
数据维护：更新原来token数据，终端类型统一为微信端 																--lizibo
数据维护：更新账户待收本金、更新账户累计收益																	--lizibo
将customer_free_withdraw_count_his change_type_code 为'2'的更新为'0'									--ydt
增加: 首页轮播图菜单项    新手任务管理菜单项 ；审核 ,轮播图类型字典                                                                                                                                                                                                             --hyc
数据初始化：移动端版本参数、新手任务、轮播图 																	--lizibo