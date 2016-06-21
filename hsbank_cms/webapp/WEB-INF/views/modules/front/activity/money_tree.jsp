<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic }/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic }/modules/front/css/util/util.css" rel="stylesheet" />
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
<title>花生金服欢乐多，天天摇好运，每摇必中！</title>
<style type="text/css">
body{ background-color:#fff;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.line-top{ width:100%; height:3px; background-color:#927100;}
.line-bottom{ width:100%; height:3px; background-color:#bebebe;}
.mc-one{ height:457px; background:url(${ctxStatic }/modules/front/images/activity/money_tree/money-tree-1.jpg) no-repeat center top;}
.mc-two{ height:455px; background:url(${ctxStatic }/modules/front/images/activity/money_tree/money-tree-2.jpg) no-repeat center top;}
.QR-code{ position:absolute; top:27px; left:-64px; width:220px; height:280px;}
.QR-code img{ margin:0 0 38px 17px}
.QR-code p{ text-align:center; font-size:18px; color:#5c5c5c;}
.rule{ position:absolute; top:150px; left:436px; width:400px; height:180px; font-size:16px; color:#595548; line-height:26px;}
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
    	<div class="line-top"></div>
        <div class="mc-one"></div>
        <div class="mc-two">
        	<div class="display-area">
                <div class="QR-code">
                	<img src="${ctxStatic }/modules/front/images/activity/money_tree/money-tree-3.jpg">
                    <p>扫一扫，马上摇起来</p>
                </div>
              <div class="rule">
              	摇前必看：<br>
                1、每天免费获得2次摇奖机会<br>
                2、每天分享活动，增加1次摇奖机会<br>
                3、单笔投资每满1000元增加1次摇奖机会，<br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;例：投资5000元，获得5次摇奖机会<br>
                4、活动日期：2016.3.1-2016.3.31

              </div>
            </div>
        </div>
    	<div class="line-bottom"></div>
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
	<div class="footer_text">©2015上海蓄积金融信息服务有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>
</body>
</html>