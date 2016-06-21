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
   	
       <div class="right_title"><span>充值</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
                                 
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>充值介绍<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>花生金服用户可以通过与花生金服合作的第三方支付平台充值，成功后可以进行理财。</p>
                   <p>如果通过网上银行支付，需要先开通网上银行功能。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>如何申请开通网上银行？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>目前所有商业银行都支持个人网银业务，您只需要携带有效身份证件，到当地您所持银行卡的发卡行任意营业网点，即可申请开通网上银行业务。您还可以到商业银行官网查看个人网上银行详细信息。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>如何给账户充值？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>1.登录花生金服，打开【我的花生金服】页面，点击【充值】；</p>
                   <p>2.跳转至充值页面，选择充值银行，输入充值金额，点击【充值】；</p>
                   <p>3.跳转至银行或者第三方支付页面，按照页面的提示输入银行账户和密码等信息即可完成充值。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>充值限额是多少？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse4" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>充值限额是由银行支付限额、第三方支付平台支付限额和用户自己设定的支付限额三者共同决定，取三者最小值。<a href="" class="btn_text_gold">查看详情>></a></p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>充值会不会扣手续费？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse5" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>第三方支付平台将收取您充值金额0.18%的费用。目前由花生金服补贴。</p>
                    <p><span class="label label-important">注：</span>花生金服不会收取您任何费用哦。</p>
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
