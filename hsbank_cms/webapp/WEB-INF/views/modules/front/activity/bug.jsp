<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />

<title></title>
<style type="text/css">
.main_content{ padding-bottom:120px; margin:0 auto; width:980px; border:1px solid #c9b093; background:#76d8f3 url(${ctxStatic }/modules/front/images/activity/bug/bug_quan.png) no-repeat center 1100px;}
.bug_img{ width:980px; min-height:700px; background:url(${ctxStatic }/modules/front/images/activity/bug/bug_img.png) no-repeat; background-size:100% auto;}
.bug_text{ width:810px; line-height:44px; margin:30px auto 0; color:#6e5a1a; font-size:20px;}
.bug_text span{ height:23px; line-height:24px; padding:0 2px; margin:0 2px; display:inline-block; background-color:#b70000; color:#fef007; border-radius:2px; -o-border-radius:2px; -ms-border-radius:2px; -moz-border-radius:2px; -webkit-border-radius:2px;}
.line_white{ margin:50px auto 0; width:30px; height:3px; background-color:#fff;}
.bug_title{ margin:20px auto 30px auto; font-size:20px; color:#6e5a1a; text-align:center;}
.mialto{ margin:0 auto 60px auto; width:176px; height:46px; line-height:46px; display:block; text-align:center; font-size:20px; color:#6e5a1a; border:1px solid #6e5a1a; border-radius:4px; -o-border-radius:4px; -ms-border-radius:4px; -moz-border-radius:4px; -webkit-border-radius:4px;}
.bug_tdc{ margin:0 auto; width:120px; height:120px;}
</style>
</head>

<body>
<div class="div_bg_001">
	<!--主要内容-->
    <div class="juanzhou_middle pb30">
        <div class="main_content">
            <div class="bug_img"></div>
            <div class="bug_text">用户在浏览平台过程中，发现有任何程序bug、文字问题或者合理的用户体验问题，欢迎及时通过花生金服官方邮箱或花生金服微信平台反馈给我们。审核合理，即可获得<span>100</span>元现金券。</div>
            <div class="line_white"></div>
            <div class="bug_title">花生金服官方邮箱</div>
          <a class="mialto" href="mailto:hsjf@fdjf.net">hsjf@fdjf.net</a>
            <div class="line_white"></div>
            <div class="bug_title">花生金服微信平台</div>
            <div class="bug_tdc"><img src="${ctxStatic }/modules/front/images/activity/bug/bug_tdc.png" /></div>
        </div>
    </div>
</div>
</body>
</html>
