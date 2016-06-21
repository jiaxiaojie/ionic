创建表：project_investment_rank（投资排行）											    --lizibo
增加：investment_rank_type_dict（投资排行类型 ）字典数据					                    --lizibo
增加：维护投资排行菜单									                                    --lizibo
增加：维护投资排行菜单权限分配给管理员							                                --lizibo
创建表：marketing_wheel_prize_info（大转盘奖品信息）											--ydt
创建表：marketing_wheel_prize_instance（大转盘奖品实例）										--ydt
创建表：marketing_wheel_get_prize_record（大转盘中奖记录）										--ydt
增加：marketing_wheel_prize_instance_status_dict（大转盘奖品实例状态）字典数据					--ydt
增加：marketing_wheel_get_prize_record_status_dict（大转盘中奖记录状态）字典数据					--ydt
增加：marketing_wheel_prize_type_dict（大转盘奖品类型）字典数据									--ydt
增加：'大转盘管理','奖品管理','查看-奖品信息','修改-奖品信息'菜单									--ydt
将菜单'大转盘管理','奖品管理','查看-奖品信息','修改-奖品信息'权限分配给管理员							--ydt
marketing_wheel_prize_info增加列：marketing_wheel_prize_info（varchar(20) '获奖提示'）		--ydt
增加: 活动数据表，活动管理及其子菜单项                                                                                                                                                                                    --wanduanrui
增加：'中奖记录','查看-中奖记录','修改-中奖记录'菜单												--ydt
将菜单'中奖记录','查看-中奖记录','修改-中奖记录'权限分配给管理员										--ydt
增加：营销活动奖励产品类型 数据字典：实物															--ydt
增加: 查看还款明细菜单项                                                                                                                                                                                                   --wanduanrui
“投资券”、“优惠券”修改为“现金券”
功能：后端 项目前端首页排序																		--ydt
功能：转盘活动管理及api功能																	--ydt
功能：前端 邀请好友-->我的收益 查询逻辑修改														--ydt
功能：添加活动首次登录客户端送20元现金券（marketActivity1006Handler）								--ydt
third_party_yeepay_para增加列：
             yeepay_tenderorderno_prefix(varchar(500) '测试环境传给易宝trenderNo前缀')
             yeepay_gate_way_callback_url_prefix(varchar(500) '易宝前端浏览器callback地址前缀')
             yeepay_gate_way_notify_prefix(varchar(500) '易宝前端浏览器notify地址前缀')
             yeepay_direct_notify_url_prefix(varchar(500) '易宝直连notify地址前缀')
             yeepay_gate_way_wireless_callback_url_prefix(varcahr(500) '易宝前端浏览器移动端callback地址前缀') 
sys_biz_para 增加列：
             project_max_amount_default(varchar(500) ''最大投资金额)
             oneday_max_withdraw_count(varchar(500) '一天最多提现次数')                     --hyc
增加: 最新活动数据,首页轮播图数据                                                                                                                                                                                          --wanduanrui