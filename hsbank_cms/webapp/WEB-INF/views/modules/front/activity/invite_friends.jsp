<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic }/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic }/modules/front/css/util/util.css" rel="stylesheet" />
<title>好友征集令，奖励无上限！</title>
<style type="text/css">
body{ background-color:#8ed7fd;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.mc-one{ height:537px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/invite-friends-01.jpg) no-repeat center top;}
.mc-two{ height:415px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/invite-friends-02.jpg) no-repeat center top;}
.QR-code{ margin:113px 20px 0 170px; width:130px; height:130px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/QR-code.jpg) no-repeat;}
.reward{ margin:190px 20px 0 170px; width:376px;}
.reward span{ display:inline-block; font-size:24px; color:#f66266; text-align:center;}
.reward .cash{ margin:0 20px 0 36px; width:130px;}
.reward .cash-coupons{ width:138px;}
.reward .btn-flash{ margin:54px 0 0 80px; width:212px; height:58px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/invite-friends-flash.gif) no-repeat;}
.mc-three{ height:583px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/invite-friends-03.jpg) no-repeat center top;}
.mc-four{ height:569px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/invite-friends-04.jpg) no-repeat center top;}
.mc-five{ margin-bottom:50px; height:414px; background:url(${ctxStatic }/modules/front/images/activity/invite_friends/invite-friends-05.jpg) no-repeat center top;}
.mc-five .explanation-area{ padding:110px 50px 0 50px;}
.mc-five .explanation-area p{ font-size:16px; line-height:34px; color:#fff;}
</style>
</head>
<body>
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront}/index">
				<img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png">
        	</a>
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront}/index">进入首页</a>
        </div>
    </div>
</div>
<!--main-->
<div class="main">
    <!--main content-->
    <div class="main-content">
        <div class="mc-one"></div>
        <div class="mc-two">
        	<div class="display-area clearfix">
            	<div class="fl QR-code"></div>
            	<div class="fl reward">
                	<div class="reward-num">
                        <span class="cash">${cashMoney }</span>
                        <span class="cash-coupons">${ticketMoney }</span>
                    </div>
                    <div class="btn-flash"></div>
                </div>
            </div>
        </div>
        <div class="mc-three"></div>
        <div class="mc-four"></div>
        <div class="mc-five">
        	<div class="display-area">
            	<div class="explanation-area">
                    <p>1、活动对象：花生金服注册用户</p>
                    <p>2、“活花生”不参与本次活动</p>
                    <p>3、奖励发放：①现金奖励将在好友投资后5个工作日内发放至邀请人账户；②25元现金券奖励将在好友开通第三方托管账户后即时到账推荐人账户，200元现金券奖励将在好友投资后5个工作日内发放至邀请人账户</p>
                    <p>4、奖励查看：①APP、微信端，用户可在“我的”--“可用余额”查看现金奖励，在“卡券”查看现金券奖励；②电脑端，用户可在“我的账户”--“可用余额”查看现金奖励，在“我的现金券”中查看现金券奖励</p>
                    <p>5、对于违规操作和作弊行为，花生金服有权取消其获奖资格</p>
                    <p>6、花生金服保留此次活动的最终解释权。如有疑问请拨打客服热线：400-969-6599</p>
            	</div>
            </div>
        </div>        
    </div>
</div>
<!-- copyright -->
<div id="footer_area" class="footer_area">
	<div class="footer_menu">
       	<span class=""><a href="${ctxFront}/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/index#cooperationAgency">友情链接</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">网站地图</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">法律声明</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">黑名单</a></span>
    </div>
	<div class="div_height_20"></div>
	<div class="footer_text">
    	<span>客服邮箱：service@fdjf.net</span>&nbsp;&nbsp;&nbsp;&nbsp;
        <span>服务热线：<b class="font_size_18">400-969-6599</b>&nbsp;(工作日9:00~18:00)</span>
    </div>
    <div class="div_height_10"></div>
	<div class="footer_text">©2015上海富定金融信息服务股份有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>
</body>
</html>