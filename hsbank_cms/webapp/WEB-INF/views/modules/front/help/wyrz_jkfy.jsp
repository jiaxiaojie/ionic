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
   	
       <div class="right_title"><span>借款费用</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>借款服务费<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                 <dl>
                  <dt>融资服务费：</dt>
                  <dd>花生金服向融资人收取其借款本金的3%作为借款服务费。</dd>
                  <dd><span class="label label-important">注：</span> 花生金服收取的融资服务费将全部用于风险备用金账户提高花生金服的本金保障计划。</dd>
                 </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>月综合费率<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>融资人的月还款金额=借款本金/期数+借款本金*月综合费率。其中，包含偿还给投资人的本金、偿还给投资人的利息、借款管理费。</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>逾期罚息<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse4" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>自逾期开始之后，正常利息停止计算。按照下面公式计算逾期违约金：</p>
                   <p><strong>逾期违约金=剩余金额*罚息利率*逾期天数，罚息利率为0.05%。</strong></p>
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
