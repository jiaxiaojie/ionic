<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic }/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic }/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<script src="${ctxStatic }/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic }/modules/front/js/snow.js" type="text/javascript"></script>
<script>
//百度站长统计代码
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?07b2a85308e4705c86371a9310089957";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

</script>
<script type="text/javascript">
//雪花图片路径和数量代码
createSnow('${ctxStatic }/modules/front/images/activity/recommend/', 60);
</script>

<title>新年红包发不停，好人缘就是大本钱！</title>

<style type="text/css">
body{ background-color:#db403b;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.main-banner{ height:662px; background: url(${ctxStatic }/modules/front/images/activity/recommend/recommend-banner.png) no-repeat center 0;}
.main-banner-time{ padding-top:339px; text-align:center; font-size:20px; color:#fff;}
/*QR-code*/
.mb-qr-code{}
.mb-qr-code .qr-code{ width:144px; height:144px; padding:95px 20px 0 427px;}
.mb-qr-code .tip-img{ position:relative; margin-top:138px; width:119px; height:62px; background: url(${ctxStatic }/modules/front/images/activity/recommend/recommend-tip.png) no-repeat; animation:tip-img 2s infinite;-o-animation:tip-img 2s infinite; -moz-animation:tip-img 2s infinite; -webkit-animation:tip-img 2s infinite;}
@-webkit-keyframes tip-img {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( -10px, 0, 0);
transform:translate3d( -10px, 0, 0)
}}
@-moz-keyframes tip-img {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( -10px, 0, 0);
transform:translate3d( -10px, 0, 0)
}}
@keyframes tip-img {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( -10px, 0, 0);
transform:translate3d( -10px, 0, 0)
}}
/*button*/
.recommend-button{ position:relative; margin:146px auto 0 auto; width:242px; height:70px; line-height:70px; display:block; font-size:22px; color:#5c457a; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-button.png) no-repeat; overflow:hidden;}
.recommend-button > span { float:left;display: inline-block;opacity: 0;color: #db403b; padding:0;-webkit-transform: translate3d(0, -10px, 0);transform: translate3d(0, -10px, 0);-webkit-transition: -webkit-transform 0.3s, opacity 0.3s;transition: transform 0.3s, opacity 0.3s;-webkit-transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);}
.recommend-button .word-1{ margin-left:34px;}
.recommend-button::before {content: attr(data-text);position: absolute;top: 0;left: 0;width: 100%;height: 100%;-webkit-transition: -webkit-transform 0.3s, opacity 0.3s;transition: transform 0.3s, opacity 0.3s;-webkit-transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);}
.recommend-button:hover {}
.recommend-button:hover::before {opacity: 0;-webkit-transform: translate3d(0, 100%, 0);transform: translate3d(0, 100%, 0);}
.recommend-button:hover > span {opacity: 1;-webkit-transform: translate3d(0, 0, 0);transform: translate3d(0, 0, 0);}
.recommend-button:hover > span.word-1 {-webkit-transition-delay: 0.045s;transition-delay: 0.045s;}
.recommend-button:hover > span.word-2 {-webkit-transition-delay: 0.09s;transition-delay: 0.09s;}
.recommend-button:hover > span.word-3 {-webkit-transition-delay: 0.135s;transition-delay: 0.135s;}
.recommend-button:hover > span.word-4 {-webkit-transition-delay: 0.18s;transition-delay: 0.18s;}
.recommend-button:hover > span.word-5 {-webkit-transition-delay: 0.225s;transition-delay: 0.225s;}
.recommend-button:hover > span.word-6 {-webkit-transition-delay: 0.27s;transition-delay: 0.27s;}
.recommend-button:hover > span.word-7 {-webkit-transition-delay: 0.315s;transition-delay: 0.315s;}
.recommend-button:hover > span.word-8 {-webkit-transition-delay: 0.36s;transition-delay: 0.36s;}
.recommend-button:hover > span.word-9 {-webkit-transition-delay: 0.405s;transition-delay: 0.405s;}
.mc-one{ margin:0 auto; width:1000px;}
.mc-one .mc-one-title{ margin:74px auto 0; width:205px; height:50px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-title-1.png) no-repeat;}
.mc-one .mc-one-text{ margin-top:13px; font-size:20px; color:#fff; text-align:center;}
.mc-one .mc-one-list{ margin-top:35px; margin-right:-32px; height:253px; overflow:hidden; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-bg-01.png) no-repeat;}
.mc-one .mc-one-list p{ margin:145px 32px 0px 3px;width:168px; height:108px; line-height:34px; text-align:center; display:inline-block; font-size:20px; color:#5c457a;}
.mc-two{ margin:100px auto 0 auto; width:1000px;}
.mc-two .mc-two-title{ height:121px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-title-2.png) no-repeat;}
.mc-two-content{ background:#3a334f;}
.mc-two-title-2{ padding-top:13px; font-size:20px; color:#fff; text-align:center;}
.mc-two-tip{ padding-top:13px; font-size:16px; color:#ec2626; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-bg-03.png) no-repeat center 21px;}
.mc-two-list{ padding:20px 0;}
.mc-two-list li{ position:relative; width:227px; height:239px; float:left; margin:0 20px 30px 69px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-1.png) no-repeat center 22px;}
.mc-two-list li .re-card{ position:absolute; top:50px; left:15px; width:194px; height:148px; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-2.png) no-repeat; transition: all ease-out .3s; -o-transition: all ease-out .3s; -ms-transition: all ease-out .3s; -moz-transition: all ease-out .3s; -webkit-transition: all ease-out .3s;}
.mc-two-list li:hover .re-card{ position:absolute; top:0px; left:15px;}
.mc-two-list li .re-card span{ margin-top:20px; display:inline-block;}
.mc-two-list li .re-card p{ padding-top:24px; font-size:14px; color:#e52a29;}
.mc-two-list li .re-text{ position:absolute; top:73px; left:0; z-index:5; width:227px; height:166px; text-align:center; font-size:16px; color:#fff; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-3.png) no-repeat;}
.mc-two-list li .re-text p{ padding-top:85px; line-height:28px;}
.mc-two-list li.re-6 .re-text{ position:absolute; top:73px; left:0; z-index:5; width:227px; height:166px; text-align:center; font-size:16px; color:#fff; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-5.png) no-repeat;}
.mc-two-list li.re-6 .re-text p{ padding-top:85px; line-height:20px;}
.mc-two-list li .re-text span{ color:#ffc600;}
.mc-two-list li .re-num{ position:absolute; top:116px; left:100px; z-index:6; width:27px; height:27px; line-height:27px; text-align:center; color:#fff; font-size:16px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-4.png) no-repeat;}
.mc-two-bottom{ height:69px; line-height:69px; text-align:center; border-top:1px solid #49425f; background:#3a334f; border-radius:0px 0px 10px 10px; -o-border-radius:0px 0px 10px 10px; -moz-border-radius:0px 0px 10px 10px; -webkit-border-radius:0px 0px 10px 10px;}
.mc-two-bottom a{ color:#ffc600; font-size:16px;}
.mc-two-bottom span{ margin:0 20px; width:54px; height:14px; display:inline-block; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-star.png) no-repeat;}
.mc-three{ margin:0 auto; width:1000px;}
.mc-three .mc-three-title{ margin:74px auto 0; width:205px; height:50px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-title-3.png) no-repeat;}
.mc-three .mc-three-text{ margin-top:30px; margin-bottom:90px; font-size:19px; color:#fff;}
.mc-three .mc-three-text p{ margin-bottom:20px; line-height:28px;}
.activity-code{ position:fixed; top:0px; right:20px; width:130px; height:589px; z-index:5; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-code.png) no-repeat;}
.re-bottom-tip{ padding:10px 0; text-align:center; color:#fff; font-size:16px;}
</style>

</head>

<body>
<!--二维码-->
<!--<div class="activity-code"></div>-->
<!--头部-->
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront }/index">
				<img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png">
        	</a>
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront }/index">进入首页</a>
        </div>
    </div>
</div>
<!--主要的-->
<div class="activity-main">
	<!--banner-->
    <div class="main-banner">
    	<div class="display-area">
        	<div class="main-banner-time">活动时间：2015.12.23-2016.1.25</div>
            <!--<a href="#" class="recommend-button" data-text="下载APP立即参与">
            	<span class="word-1">下</span>
                <span class="word-2">载</span>
                <span class="word-3">A</span>
                <span class="word-4">P</span>
                <span class="word-5">P</span>
                <span class="word-6">立</span>
                <span class="word-7">即</span>
                <span class="word-8">参</span>
                <span class="word-9">与</span>
            </a>-->
            <div class="mb-qr-code clearfix">
            	<div class="pull-left qr-code"><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-qr-code.png"></div>
            	<div class="pull-left tip-img"></div>
            </div>
        </div>
    </div>
    <!--主要内容-->
    <div class="main-content">
    	<div class="mc-one">
        	<div class="mc-one-title"></div>
        	<div class="mc-one-text">简单5步，搞定好友，红包到手！</div>
        	<div class="mc-one-list">
            	<p>点击<br/>邀请好友</p>
            	<p>分享<br/>邀请链接</p>
            	<p>邀请<br/>好友注册</p>
            	<p>好友<br/>完成投资</p>
            	<p>坐享<br/>现金奖励</p>
            </div>            
        </div>
        
    	<div class="mc-two">
        	<div class="mc-two-title"></div>  
        	<div class="mc-two-content">
            	<div class="mc-two-title-2">邀请投资的好友越多，现金奖励越丰厚！</div>
            	<div class="mc-two-tip">活动期间，邀请好友注册，且每位好友累计投资额（活期产品除外）≥1000元，可获得以下奖励</div>
            	<div class="mc-two-list">
                	<ul class="clearfix">
                    	<li class="re-1">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-1.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第1位好友<br>奖励<span>10</span>元现金</p>
                            </div>
                        	<div class="re-num">1</div>
                        </li>
                    	<li class="re-2">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-2.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第2位好友<br>奖励<span>20</span>元现金</p>
                            </div>
                        	<div class="re-num">2</div>
                        </li>
                    	<li class="re-3">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-3.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第3位好友<br>奖励<span>30</span>元现金</p>
                            </div>
                        	<div class="re-num">3</div>
                        </li>
                    	<li class="re-4">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-4.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第4位好友<br>奖励<span>40</span>元现金</p>
                            </div>
                        	<div class="re-num">4</div>
                        </li>
                    	<li class="re-5">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-5.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第5位好友<br>奖励<span>50</span>元现金</p>
                            </div>
                        	<div class="re-num">5</div>
                        </li>
                    	<li class="re-6">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-1.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第6位及以上好友<br>每多邀请1位<br>奖励<span>10</span>元现金</p>
                            </div>
                        	<div class="re-num">6</div>
                        </li>
                    </ul>
                    <div class="re-bottom-tip">举例：邀请7位好友且符合活动规则，可获得10+20+30+40+50+10+10=170元 现金奖励</div>
                </div>
            </div>
        	<div class="mc-two-bottom">
            	<span class=""></span>
                <a href="${ctxFront }/activity/marketing.html#guize" name="#guize">本活动与推荐有奖叠加 点此查看>></a>
            	<span class=""></span>
            </div>
        </div>
        
    	<div class="mc-three">
        	<div class="mc-three-title"></div>
        	<div class="mc-three-text">
            	<p>1、活动时间：2015.12.23-2016.1.25</p>
                <p>2、参与用户：花生金服平台所有注册用户</p>
                <p>3、奖励条件：每位被邀请的好友在活动期间注册且累计投资额（活期产品除外）≥1000元</p>
                <p>4、奖励发放：现金奖励将在好友投资成功后5个工作日内发放至邀请人账户，可在“我的账户”-“可用余额”中查看</p>
                <p>5、本活动可与推荐有奖活动叠加，活动解释权归花生金服所有</p>
            </div>
        </div>
        
    </div>
</div>

<!-- 版权申明区域 -->
<div id="footer_area" class="footer_area">
	<div class="footer_menu">
       	<span class=""><a href="${ctxFront }/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/index#cooperationAgency">友情链接</a></span>
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