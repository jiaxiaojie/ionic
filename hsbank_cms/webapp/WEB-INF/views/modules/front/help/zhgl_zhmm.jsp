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
   	
       <div class="right_title"><span>账户密码</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
                                 
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>如何修改登录密码？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                 	<p>登录花生金服，打开【我的账户】--【账户管理】--【安全信息】页面，找到【登录密码】信息栏，点击【修改】，重置登录密码。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>如何找回登录密码？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>打开登录页面，点击【忘记密码】；跳转至【找回密码】页面，可以选择通过绑定邮箱或者绑定手机号找回登录密码。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>通过邮箱找回登录密码时，收不到邮件，怎么办？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>请您在垃圾邮件中查找，如果没有，则可能是您邮箱服务器存在问题，你可以选择<a href="" class="btn_text_gold">更换绑定邮箱</a>。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>如何修改交易密码？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse4" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>两种方式：</p>
                    <p>1.您可登录易宝支付进行修改</p>
                    <p>2.在花生金服充值操作时，跳转到易宝支付页面，您可点击忘记密码重新设置支付密码。<a href="" class="btn_text_gold">查看操作流程>></a></p>
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
