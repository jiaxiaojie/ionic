<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="decorator" content="help_center"/>
	<link href="${ctxStatic}/modules/front/css/help_center.css?${version }" rel="stylesheet"/>
	<title></title>
</head>
<body>
<div id="content_bottom" class="help_content">
        	
<div class="help_main">
   	
       <div class="right_title"><span>安全认证</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
                                 
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>为什么要进行安全认证？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                 	<p>为了保障用户资金的安全性和合同的有效性，花生金服要求所有投资人及融资人必须通过身份证绑定、手机号绑定以及交易密码设置。安全认证的过程简单便捷，花生金服对于所有个人资料均作严格保密。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>如何进行实名认证？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>1.用户可以在【注册成功】页面进行实名认证；</p>
                   <p>2.用户可以打开【我的花生金服】--【账户管理】--【安全信息】页面，在实名认证信息栏，点击【设置】，输入姓名和身份证号，进行实名认证。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>身份信息正确，实名认证未通过怎么办？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>可能是由于第三方认证系统没有及时更新您的身份信息导致的。您可以联系客服寻求帮助（400-969-6599）。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>进行实名认证后，能解绑或重新认证吗？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse4" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>1.目前网站暂不提供解绑操作；</p>
                   <p>2.如果账户资金余额为0且没有借款和理财记录的用户，您可以联系客服解绑（400-969-6599）。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>一个手机号能绑定几个花生金服账户？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse5" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>一个。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse6" class="collapsed"><em class="icon_arrow_right"></em>如何修改绑定手机号？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse6" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>登录花生金服，打开【我的花生金服】--【账户管理】--【安全信息】页面，用户可以选择通过原手机号或者身份证号进行修改。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
 
            </div>
        </div>
        
    </div>
    
</div>
<script type="text/javascript">
	function collapseClick(element) {
		if($(element).hasClass("collapsed")) {
			$(element).find("i").removeClass("icon_plus").addClass("icon_minus");
		}else {
			$(element).find("i").removeClass("icon_minus").addClass("icon_plus");
		}
	}
</script>
</body>
</html>
