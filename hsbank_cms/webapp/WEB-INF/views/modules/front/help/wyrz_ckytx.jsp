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
   	
       <div class="right_title"><span>筹款与提现</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
                                 
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>审核成功后如何进行筹款？筹款时间多久？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>审核成功后，您可得到相应的信用等级和信用额度，同时客户经理将致电您沟通借款金额、期限和利率，确认后帮助您进入融资进程。</p>
                   <p>您的借款金额将于1-3天内为您融资完成。</p>
                 </div>
               </div>
             </div>
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>筹款成功后如何进行提现？到账时间多久？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse2" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p><a href="" class="btn_text_gold">查看提现流程>></a></p>
                    <p>资金将在申请提现后1-2个工作日内到达您指定的银行账户。</p>
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
