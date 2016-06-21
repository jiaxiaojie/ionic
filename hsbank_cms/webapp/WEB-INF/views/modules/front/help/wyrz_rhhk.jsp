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
   	
       <div class="right_title"><span>如何还款</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
                                 
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>如何进行还款？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>1.设定自动还款；</p>
                   <p>2.保证账户余额；</p>
                   <p>3.当账户余额不足支付当期还款时，您可先通过易宝支付为账户充值；</p>
                   <p>4.充值完成后，您可以自行点击还款；</p>
                   <p>5.充值完成后，您也可以等待系统自动扣款，无需自行点击还款。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>是否可以提前还款？如何操作？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>您可以随时进行提前还款。</p>
                   <p>登陆花生金服网站，打开【我的账户】--【融资管理】--【我的借款】--【还款中】页面，点击【提前还款】进行提前还款操作（与还款操作类似）。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>是否可以申请延期还款？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse3" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>您不可以申请延期还款。您需每月按时还款，以免影响您的信用记录。</p>
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
