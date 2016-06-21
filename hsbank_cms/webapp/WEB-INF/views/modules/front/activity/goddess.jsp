<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/views/include/head_for_front.jsp"%>
<link href="${ctxStatic}/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<script type="text/javascript">
	function closePopup() {
		$("#popup").css("display", "none");
	}
	function openPopup() {
		$("#popup").css("display", "block");
	}
</script>
<title>女神升值季，超值礼包等你拿！</title>
<style type="text/css">
body{ background-color:#fff;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.line-top{ width:100%; height:3px; background-color:#927100;}
.line-bottom{ width:100%; height:3px; background-color:#bebebe;}
.mc-one{ height:580px; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-01.jpg) no-repeat center top;}
.mc-two{ height:322px; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-02.jpg) no-repeat center top;}
.mc-three{ height:348px; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-03.jpg) no-repeat center top;}
.mc-four{ height:449px; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-04.jpg) no-repeat center top;}
.btn-red{ position:absolute; top:170px; right:50px; width:259px; height:53px; line-height:53px; text-align:center; color:#fff; font-size:24px; font-weight:bold; display:block; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-05.png) no-repeat;}
.btn-red:hover, .btn-red:focus, .btn-red:active { color:#fff;}
.btn-gray{ position:absolute; top:170px; right:50px; width:259px; height:53px; line-height:53px; text-align:center; color:#fff; font-size:24px; font-weight:bold; display:block; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-05-2.png) no-repeat;}
.btn-gray:hover, .btn-gray:focus, .btn-gray:active { color:#fff;}
.four-text{ padding:40px 0 230px 14px; font-size:16px; color:#858585;}
.four-text a{ color:#ff3b63; text-decoration:underline;}
.btn-group{ margin-right:-60px;}
.btn-group a{ float:left; margin:0 60px 0 5px; width:178px; height:42px; display:block; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-06.png) no-repeat;}
.mc-five{ padding:10px 0 30px; font-size:18px; color:#7e7e7e; line-height:30px;}
.popup{ position:fixed; top:0; left:0; z-index:9999; width:100%; height:100%; background-color:rgba(0, 0, 0, 0.6);}
.popup .btn-close{ position:absolute; top:5px; left:420px; margin-left:-12px; width:35px; height:35px; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-08.png) no-repeat;}
.pupup-bg{ position:absolute; top:50%; left:50%; margin:-115px 0 0 -230px; padding:4px; width:440px; height:210px; background:#ff3b63;}
.pupup-bg .pupop-border{ width:438px; height:208px; border:1px solid #f6eecf;}
.pupup-bg .pupop-border p{ margin:50px auto 20px; width:330px; text-align:center; line-height:40px; font-size:20px; color:#f6eecf; display:block;}
.pupup-bg .pupop-border .btn-gold{ margin:0 auto; width:130px; height:34px; line-height:34px; text-align:center; font-size:16px; font-weight:bold; color:#ff3b63; display:block; background:url(${ctxStatic }/modules/front/images/activity/goddess/goddess-07.png) no-repeat;}
</style>

<script type="text/javascript">

function openPrompt(title, btnTitle,btnUrl){
	btnTitle = (btnTitle==null?"关闭":btnTitle);
	btnUrl = (btnUrl==null?"javascript:closePopup();":btnUrl);
	$("#promptTitle").text(title);
    $("#promptBtn").text(btnTitle);
    $("#promptBtn").attr("href", btnUrl);
    openPopup();
}

$(document).ready(function() {
	$("#getGiftBag").bind('click', function() {
		 
		  $.ajax({
			   type: "POST",
			   url: "${ctxFront}/activity/getGiftBag",
			   dataType : 'json',
			   success: function(msg){
			     //alert(JSON.stringify(msg)  );
			     if(msg.code == 0){
			    	 var resultCode = msg.data.resultCode;
			    	 if(resultCode==-1){
			    		 openPrompt(msg.data.result, "去登录","${ctxFront}/login");
			    	 }
			    	 else if(resultCode==3){
			    		 openPrompt(msg.data.result, "验明真身","${ctxFront}/customer/thirdAccount/open");
			    	 }else  if(resultCode==1){
			    		 openPrompt(msg.data.result, "邀请女神","${ctxFront}/activity/marketing");
			    	 }else  if(resultCode==2 || resultCode==0){
			    		 openPrompt(msg.data.result, "查看礼包","${ctxFront}/customer/ticket/my");
			    	 }
			     }
			     else{
				     openPrompt("操作失败！");
			     }
			     
			   }
			});
	});

});
</script>
</head>
<body>
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
<!--popup-->
<div id="popup" class="popup" style="display:none;">
    <div class="pupup-bg">
    	<a href="javascript:;" onclick="closePopup()" class="btn-close"></a>
    	<div class="pupop-border">
        	<p id="promptTitle">女神的奶酪碰不得，快叫上你的女神来参与吧~</p>
            <a href="javascript:;"  class="btn-gold" id="promptBtn">查看礼包</a>
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
        		<c:if test="${received}">
        			<a href="javascript:return;" class="btn-gray" id="getGiftBag">已领取</a>
        		</c:if>
        		<c:if test="${!received}">
        			<a href="javascript:return;" class="btn-red" id="getGiftBag">立即领取</a>
        		</c:if>
            	
            </div>
        </div>
        <div class="mc-three">
        	<div class="display-area">
            	<a href="${ctxFront}/wytz" class="btn-red" style="top:210px;">我要投资</a>
            </div>
        </div>
        <div class="mc-four">
        	<div class="display-area">
            	<p class="four-text">玩转花生豆，尽在花生乐园，<a href="${ctxFront }/integralMall/index" target="_blank">去逛逛</a></p>
            	<div class="btn-group">
                	<a href="${ctxFront }/integralMall/commodityDetails?productId=36" target="_blank" class="btn-yellow" style="margin-right:80px;"></a>
                	<a href="${ctxFront }/integralMall/commodityDetails?productId=26" target="_blank" class="btn-yellow" style="margin-right:68px;"></a>
                	<a href="${ctxFront }/integralMall/commodityDetails?productId=30" target="_blank" class="btn-yellow" style="margin-right:67px;"></a>
                	<a href="${ctxFront }/integralMall/commodityDetails?productId=28" target="_blank" class="btn-yellow"></a>
                </div>
            </div>
        </div> 
        <div class="mc-five">
        	<div class="display-area">
                <p>1、 活动时间：2016.3.8-3.20</p>
                <p>2、 活动对象：仅限女性用户参与</p>
                <p>3、 奖励范围：除“活花生”外所有投资项目</p>
                <p>4、 奖励说明：①领取礼包后可在个人账户中查看 ②投资获得的花生豆奖励请至   个人账户-我的花生豆   进行查看</p>
                <p>5、 花生金服保留本次活动的最终解释权</p>
            </div>
        </div>       
    	<div class="line-bottom"></div>
    </div>
</div>
<!-- copyright -->
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