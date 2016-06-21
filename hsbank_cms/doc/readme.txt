1）原型项目路径 https://github.com/thinkgem/jeesite 
对应自检系统路径 svn://211.167.112.236/java-workspace/jeesite-master_hibernate,用户名：thinkgem/admin
2）另一选择项目模板对应路径    svn://211.167.112.236/java-workspace/jeefw  用户名：3228979148@qq.com/123456
3）技术选型依据 spring mvc +hibernate +bootstrap ，有较现成的rbac实现，支持spring 安全或者apache shiro更好
4)bbs选型结果  svn://211.167.112.236/java-workspace/jeebbs-src-v4.0
5)hibernate 生成参考文档地址http://blog.sina.com.cn/s/blog_451f596201016m5q.html
6）

首页入口：
http://localhost:9001/fuding_p2p/f/index

http://demo.fdjf.net/p2p/f/index

前端页面：
http://tracker.wmios.com:6080/pages/viewpage.action?pageId=1476244


会员Customer（1 --> N）用户Account

${frontPath}/activity/{jspName}.html 这个地址指向modules/front/activity/{jspName}.jsp这个jsp，其参数会被遍历放到model中

modules描述:
api：对外接口
carousel:轮播图
cms:内容管理
credit：债权管理
current：活期产品
customer：会员管理
entity：实体类
extend.web：外部扩展的api（目前是本米在调用）
feedback：意见反馈
front:前端页面
fund：基金
gen：代码生成
integral：积分商城
loan：借款
log：日志查询
marketing：营销活动
message：消息管理
oa：办公系统
operation:运营数据
project:借贷产品管理
rpt：报表
sms：短信
sys：系统管理
test：测试
yeepay：易宝支付