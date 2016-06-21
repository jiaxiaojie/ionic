<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic }/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic }/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<title>投资风暴进行中, iPhone手机周周送，更有MacBook等你拿</title>
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
body{ background-color:#ed3044;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.main-banner{ height:604px; background:url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-01.jpg) no-repeat  center 0px;}
.activity-tdc{ position:fixed; top:0px; right:20px; width:130px; height:401px; z-index:5;}
.main-content { min-height:800px; background:#ed3044 url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-02.png) no-repeat center 0;}
.main-one{ margin:0 auto; width:1000px;}
.main-one .mo-title{ width:1000px; height:151px; background:url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-03.png) no-repeat center bottom;}
.main-one .mo-yellow{ margin:0 auto; padding-bottom:20px; width:864px; background-color:#ffd51d; border-radius:0 0 20px 20px;}
.main-one .mo-beige{ margin:0 auto; padding-bottom:20px; width:828px; min-height:530px; background-color:#fcf3cc; border-radius:0 0 20px 20px; box-shadow:0px 5px 0px #ce8f51;}
.main-one .mo-info{ padding-left:30px; padding-top:14px; color:#bd8432;}
.mo-info .pull-left{ width:460px;}
.mo-info .pull-right{ width:230px; text-align:center;}
.mo-info .mo-name{ margin-bottom:24px; margin-top:9px; font-size:26px;}
.mo-info .mo-date{ font-size:17px;}
.mo-info .mo-date span{ color:#ed3044;}
.mo-info .pull-right{ padding-right:30px;}
.mo-info .mo-tip{ margin-bottom:20px; font-size:17px;}
.mo-info .mo-btn{ margin:0 auto; width:156px; height:48px; display:inline-block; background:url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-04.png) no-repeat;}
.mo-tab{ position:relative; z-index:2; margin-top:30px; padding:0 34px; width:760px; height:69px; background:url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-05.png) no-repeat center 52px;}
.mo-tab .pull-left a{ margin-right:5px; width:134px; height:52px; line-height:52px; display:inline-block; text-align:center; font-size:26px; color:#fffae0; border-radius:10px 10px 0 0;}
.mo-tab .tab-red{ background-color:#f0493c;}
.mo-tab .tab-gray{ background-color:#b5b5b5;}
.mo-tab .pull-right{ line-height:52px; font-size:18px; color:#c93f35;}
.mo-tab .pull-right span{ float:left; margin-left:10px;}
.mo-tab .pull-right a{ float:left; margin-left:10px; margin-top:16px; width:22px; height:22px; line-height:22px; text-align:center; color:#fff; border-radius:50px; font-weight:bold; font-family: sans-serif;}
.mo-tab .pull-right .mo-prev{ background-color:#e94c40;}
.mo-tab .pull-right .mo-next{ background-color:#e94c40;}
.mo-tab .pull-right .mo-disabled{ background-color:#b5b5b5; cursor:default;}
.mo-first{ position:relative; z-index:1; top:-1px; margin:0 auto; width:764px; height:57px; line-height:56px; text-align:center; font-size:16px; font-weight:bold; color:#000; background:url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-07.png) no-repeat;}
.mo-first span{ margin:0 3px; color:#f0493c;}
.mo-list{ margin:20px auto 0 auto; width:760px;}
.mo-list thead{ padding-bottom:10px;}
.mo-list thead th{ font-size:24px; color:#333; text-align:center; font-weight:normal;}
.mo-list tbody .list-color{ background-color:#fae9c0; border-radius:10px;}
.mo-list tbody td{ line-height:28px; text-align:center; font-size:18px; color:#666; border:none;}
.mo-list .mo-rirst{ width:24px; height:24px; line-height:24px; text-align:center; background-color:#ffc000; color:#fff; display:inline-block; border-radius:50px;}
.mo-list .mo-second{ width:24px; height:24px; line-height:24px; text-align:center; background-color:#b7b5b5; color:#fff; display:inline-block; border-radius:50px;}
.mo-list .mo-third{ width:24px; height:24px; line-height:24px; text-align:center; background-color:#e99931; color:#fff; display:inline-block; border-radius:50px;}
.main-two{ margin:0 auto; padding:50px 0; width:1000px; height:398px; background:url(${ctxStatic }/modules/front/images/activity/leaderboard/20151130-06.png) no-repeat center 50px;}
.main-two dl{ margin:50px auto 0 auto; width:828px; color:#bd8432;}
.main-two dl dt{ margin-bottom:20px; font-weight:bold; font-size:24px;}
.main-two dl dd{ margin-bottom:10px; font-size:20px; line-height:30px;}
</style>
</head>
<body>
<!--二维码-->
<div class="activity-tdc">
	<div class=""><img src="${ctxStatic }/modules/front/images/activity/leaderboard/20151130-tdc.png"></div>
</div>
<!--头部-->
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png">
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront }/index">进入首页</a>
        </div>
    </div>
</div>

<!--banner-->
<div class="main-banner"></div>
<!--主要内容-->
<div class="main-content">
	<div class="main-one">
    	<div class="mo-title"></div>
        <div class="mo-yellow">
        	<div class="mo-beige">
            	<div class="mo-info clearfix">
                	<div class="pull-left">
                    	<p class="mo-name">${p2p:vagueMobile(customerBase.mobile) }，您好：</p>
                        <p class="mo-date">您本月的投资额：<span><fmt:formatNumber value="${investmentRank.amount}" pattern="#0.00" /></span>元，您的月排名：<span>${investmentRank.rank }</span></p>
                    </div>
                	<div class="pull-right">
                    	<p class="mo-tip">
                    	   <c:choose>
			            	  <c:when test="${investmentRank.rank eq '1'}">
			            	             坐稳第一名，大奖等你哦
			            	  </c:when>
			            	  <c:otherwise>
			            	            快来投资提升排名拿奖品吧
			            	  </c:otherwise>
			                </c:choose>
                    	</p>
                        <a href="${ctxFront }/wytz" class="mo-btn"></a>
                    </div>
                </div>
                <div class="mo-tab">
                	<div class="pull-left">
                    	<!--排行标签切换时，对换class就可以-->
                    	<a href="#" class="tab-gray" onClick="selectItem(1)">周排行</a>
                    	<a href="#" class="tab-red" onClick="selectItem(2)">月排行</a>
                    </div>
                </div>
                <!--周排行-->
                <div class="mo-list">
                	<table class="table table-hover">
                        <thead>
                            <tr>
                                <th width="18%">排名</th>
                                <th width="47%">手机号码</th>
                                <th width="35%">投资额(元)</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${list}" var="investmentRank">
				                <tr class="list-color">
				                    <c:choose>
					            	  <c:when test="${investmentRank.rank eq '1'}">
					            	      <td><span class="mo-rirst">${investmentRank.rank }</span></td>
					            	  </c:when>
					            	  <c:when test="${investmentRank.rank eq '2'}">
					            	      <td><span class="mo-second">${investmentRank.rank }</span></td>
					            	  </c:when>
					            	  <c:when test="${investmentRank.rank eq '3'}">
					            	      <td><span class="mo-third">${investmentRank.rank }</span></td>
					            	  </c:when>
					            	  <c:otherwise>
					            	     <td>${investmentRank.rank }</td>
					            	  </c:otherwise>
					                </c:choose>
				                    <td>${p2p:vagueMobile(investmentRank.mobile) }</td>
				                    <td><fmt:formatNumber value="${investmentRank.amount}" pattern="#0.00" /></td>
				                </tr>
			                </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
	<div class="main-two">
    	<dl>
        	<dt>活动规则：</dt>
            <dd>1、2015年12月1日—12月31日期间通过花生金服网站、微信、APP进行交易的用户，均可参与。</dd>
            <dd>2、周投资额度最高者可获得iPhone 6s 16G一部，共计四部（最后一周不满7天不计算在周排行榜）。</dd>
            <dd>3、月投资额度最高者可获得Apple MacBook一台。</dd>
            <dd>4、每周一正式发布榜单，客服会在7个工作日内联系获奖用户。</dd>
            <dd>5、本活动最终解释权归花生金服所有。</dd>
        </dl>
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
	<div class="footer_text">©2015上海蓄积金融信息服务有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
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