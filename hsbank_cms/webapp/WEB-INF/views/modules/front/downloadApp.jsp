<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic}/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic}/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic}/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<title>年终狂欢，投资飓风呼啸来袭</title>
<script>
//百度站长统计代码
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?07b2a85308e4705c86371a9310089957";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

$(function () 
{ $("[data-toggle='popover']").popover();
	  });
</script>
<style type="text/css">
body{ background-color:#fff;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.main-banner{ min-width:1000px; height:630px; background: url(${ctxStatic}/modules/front/images/downloadApp/bg_banner.jpg) no-repeat center 0;}
.banner-content{ position:relative; width:1000px; height:630px; margin:0 auto;}
.banner-content .banner-lt{ margin:104px 0 0 30px; width:376px; height:470px; background: url(${ctxStatic}/modules/front/images/downloadApp/img_phone.png) no-repeat center 0;}
.banner-content .banner-rt .banner-slogan{ margin:80px 38px 0 0; width:591px; height:224px; background: url(${ctxStatic}/modules/front/images/downloadApp/img_slogan.png) no-repeat center 0;}
.banner-content .banner-rt .banner-download{ position:absolute; right:0px; top:370px; width:490px;}
.banner-download .banner-tip{ margin:0px 0px 0px 0px; width:261px; height:50px; background: url(${ctxStatic}/modules/front/images/downloadApp/img_tip.png) no-repeat center 0;}
.banner-download .banner-tdc{ text-align:center;}
.banner-download .banner-tdc span{ margin-top:13px; color:#37225a; font-size:15px; display:block;}
.banner-download .banner-btn{ margin-top:32px;}
.banner-btn a{ margin-bottom:10px; width:186px; height:40px; display:block; border:2px solid #e60000; background:#e60000; border-radius:5px; -o-border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px; transition: all ease-out .2s; -o-transition: all ease-out .2s; -ms-transition: all ease-out .2s; -moz-transition: all ease-out .2s; -webkit-transition: all ease-out .2s;}
.banner-btn a.btn-ios-gray{ border-color:#bbb; background: url(${ctxStatic}/modules/front/images/downloadApp/btn_ios_gray.png) no-repeat -2px -2px;}
.banner-btn a.btn-ios{ background:#e60000 url(${ctxStatic}/modules/front/images/downloadApp/btn_ios.png) no-repeat 0px -46px;}
.banner-btn a.btn-ios:hover{ background:#b70000 url(${ctxStatic}/modules/front/images/downloadApp/btn_ios.png) no-repeat 0px -2px;}
.banner-btn a.btn-ios:active{ background:#e60000 url(${ctxStatic}/modules/front/images/downloadApp/btn_ios.png) no-repeat 0px -2px;}
.banner-btn a.btn-android{ background:#e60000 url(${ctxStatic}/modules/front/images/downloadApp/btn_android.png) no-repeat 0px -46px;}
.banner-btn a.btn-android:hover{ background:#b70000 url(${ctxStatic}/modules/front/images/downloadApp/btn_android.png) no-repeat 0px -2px;}
.banner-btn a.btn-android:active{ background:#e60000 url(${ctxStatic}/modules/front/images/downloadApp/btn_android.png) no-repeat 0px -2px;}
.main-content{ min-width:1000px;}
.main-content .bg-gray{ background-color:#f5f5f5;}
.teletext li{ height:510px;}
.teletext li .text-golden{ color:#d2a91a;}
.teletext li .title{ margin-bottom:26px; height:50px; line-height:50px; font-size:42px; color:#000;}
.teletext li .text p{ font-size:20px; color:#333; line-height:36px;}
.teletext li .bg-img{ width:420px; height:510px;}
.teletext li .bg-01{ background:url(${ctxStatic}/modules/front/images/downloadApp/bg_01.png) no-repeat 0 center;}
.teletext li .bg-02{ background:url(${ctxStatic}/modules/front/images/downloadApp/bg_02.png) no-repeat 0 center;}
.teletext li .bg-03{ background:url(${ctxStatic}/modules/front/images/downloadApp/bg_03.png) no-repeat 0 center;}
.teletext li .bg-04{ background:url(${ctxStatic}/modules/front/images/downloadApp/bg_04.png) no-repeat 0 center;}
.teletext li .bg-05{ background:url(${ctxStatic}/modules/front/images/downloadApp/bg_05.png) no-repeat 0 center;}
</style>
</head>
<body>
<!--头部-->
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront }/index"><img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png"></a>
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
    	<div class="banner-content">

        	<div class="banner-rt">
            	<div class="banner-download clearfix">
                	<div class="pull-left">
                    	<div class="banner-tip"></div>
                    	<div class="banner-btn">
                        	<!-- <a class="btn-ios-gray"></a> -->
                        	<a href="https://itunes.apple.com/cn/app/id1060369794" target="_blank" class="btn-ios"></a>
                        	<a href="https://www.hsbank360.com/userfiles/release/hsjf_android_release.apk" target="_blank" class="btn-android"></a>
                        </div>
                    </div>
                	<div class="pull-right banner-tdc">
                    	<p><img src="${ctxStatic}/modules/front/images/downloadApp/img_tdc.jpg"></p>
                        <span>扫二维码下载花生金服APP</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--主要内容-->
    <div class="main-content">
    	<ul class="teletext">
        	<li class="teletext-item">
            	<div class="display-area clearfix">
                    <div class="pull-left" style="margin-top:130px;">
                        <div class="title">安全稳健，<span class="text-golden">有保障</span></div>
                        <div class="text">
                            <p><span class="text-golden">第三方资金托管</span>避免资金池风险</p>
                            <p>与<span class="text-golden">平安银行</span>签署风险备用金专户协议</p>
                            <p><span class="text-golden">阳光财险全程</span>承保账户资金安全</p>
                            <p><span class="text-golden">国内顶尖律所</span>全程法律保障</p>
                            <p><span class="text-golden">AAA</span>级信用企业</p>
                        </div>
                    </div>
                    <div class="pull-right">
                    	<p class="bg-img bg-01"></p>
                    </div>
                </div>
            </li>
        	<li class="teletext-item bg-gray">
            	<div class="display-area clearfix">
                    <div class="pull-left">
                    	<p class="bg-img bg-02"></p>
                    </div>
                    <div class="pull-right" style="margin-top:150px;">
                        <div class="title">低门槛，<span class="text-golden">高收益</span></div>
                        <div class="text">
                            <p>100元起投，零钱也能理财</p>
                            <p>精选<span class="text-golden">8%-16%</span>优质项目，风控护航</p>
                            <p>1-12个月灵活期限</p>
                      </div>
                    </div>
                </div>
            </li>
        	<li class="teletext-item">
            	<div class="display-area clearfix">
                    <div class="pull-left" style="margin-top:170px;">
                        <div class="title">新手专享，<span class="text-golden">福利多</span></div>
                        <div class="text">
                            <p>首次登陆送现金红包</p>
                            <p>新手专享高收益项目</p>
                        </div>
                    </div>
                    <div class="pull-right">
                    	<p class="bg-img bg-03"></p>
                    </div>
                </div>
            </li>        
        	<li class="teletext-item bg-gray">
            	<div class="display-area clearfix">
                    <div class="pull-left">
                    	<p class="bg-img bg-04"></p>
                    </div>
                    <div class="pull-right" style="margin-top:170px;">
                        <div class="title">掌上理财，<span class="text-golden">很便捷</span></div>
                        <div class="text">
                            <p>认证、充值、快捷安全</p>
                            <p>随时随地，想投就投</p>
                        </div>
                    </div>
                </div>
            </li>
        	<li class="teletext-item">
            	<div class="display-area clearfix">
                    <div class="pull-left" style="margin-top:200px;">
                        <div class="title">账户详情，<span class="text-golden">一目了然</span></div>
                        <div class="text">
                            <p>账户资产、待收收益、还款日历，随时查看</p>
                        </div>
                    </div>
                    <div class="pull-right">
                    	<p class="bg-img bg-05"></p>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<!-- 版权申明区域 -->
<div id="footer_area" class="footer_area">
	<div class="footer_menu">
       	<span class=""><a href="/f/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="/f/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="/f/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="/f/index#cooperationAgency">友情链接</a></span>
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
    	<!--<a href="javascript:void(0);" class="logo_kx"></a>-->
    	<!--<a href="javascript:void(0);" class="logo_aq"></a>-->
    	<!--<a href="javascript:void(0);" class="logo_x"></a>-->
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>

</body>
</html>