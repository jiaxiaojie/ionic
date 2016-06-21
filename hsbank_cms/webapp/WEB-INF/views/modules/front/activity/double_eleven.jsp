<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />
<title>双十一理财黄金周</title>
<style type="text/css">
.main_content{ padding-bottom:20px; margin:0 auto; width:980px; border:1px solid #c9b093; background:#fff;}
.pic1, .pic2, .pic3, .pic4, .pic5{ position:relative; margin:0 auto; text-align:center;}
.pic1{ margin-top:20px;}
.main_content a:hover{ color:#dea731 !important;}
.btn-mt{ position:absolute; z-index:10; width:269px; height:53px; text-align:center; line-height:53px; font-size:24px; color:#fff; display:block; background:url(${ctxStatic }/modules/front/images/activity/marketing/btn_orange.png) no-repeat; text-decoration:none;}
.btn-zc{ right:348px; bottom:30px;}
.btn-sy{ width: 269px; height: 53px; text-align: center; line-height: 53px; font-size: 24px; color: #fff; display: inline-block; background: url(${ctxStatic }/modules/front/images/activity/marketing/btn_orange.png) no-repeat; text-decoration: none;}
.enter_home{ margin: 50px auto; text-align:center;}
</style>
</head>

<body>
<div class="div_bg_001">
	<!--主要内容-->
    <div class="juanzhou_middle pb30">
        <div class="main_content">
        	<div class="pic1">
            	<img src="${ctxStatic }/modules/front/images/activity/double_eleven/duble-eleven-01.jpg">
            </div>
        	<div class="pic2">
                <a href="${ctxFront }/wytz" class="btn-mt btn-zc">立享双倍收益</a>
            	<img src="${ctxStatic }/modules/front/images/activity/double_eleven/duble-eleven-02.jpg">
            </div>
        	<div class="pic3">
           	  <img src="${ctxStatic }/modules/front/images/activity/double_eleven/duble-eleven-03.jpg">
            </div>
            <div class="enter_home"><a href="${ctxFront }/index" class="btn-sy">回到首页</a></div>
        </div>            
    </div>
</div>

</body>
</html>