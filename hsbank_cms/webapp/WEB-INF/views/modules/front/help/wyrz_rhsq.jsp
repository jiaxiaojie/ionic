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
   	
       <div class="right_title"><span>如何申请</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>借款申请条件有哪些？是否需要担保和抵押？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>申请人提交必要的审核条件后，即可发起借款申请，通过花生金服网站申请借款不需要抵押和担保。<a href="" class="btn_text_gold">查看详情>></a></p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>借款申请步骤有哪些？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>1.我要融资款</p>
                   <p>2.填写借款金额，立即申请</p>
                   <p>3.填写个人信息</p>
                   <p>4.等待审核</p>
                   <p>5.通过审核</p>
                   <p>6.筹集借款</p>
                   <p>7.获得借款</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>我能获得多少借款金额<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>您可申请的借款额度范围是10,000-1000,000（请您按照实际需求填写）。</p>
                   <p>您可获得的借款额度将在信审部门审核完成后，由您的整体资质决定（可能与您申请的借款金额不一致）。</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>我能选择哪些借款期限和还款方式<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse4" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>目前您可选择的借款期限有3个月、6个月、12个月、18个月、24个月。</p>
                   <p>花生金服网站的还款方式为等额本息、等额本金和到期还本付息。</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>如何填写借款申请表<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse5" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>登陆花生金服网站，打开【我要融资】--点击【立即申请借款】，您可根据提示进行贷款申请</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse6" class="collapsed"><em class="icon_arrow_right"></em>如何修改或取消借款申请？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse6" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>在您发布借款申请后，无法自行修改或取消申请，请您在发布时注意认真填写各项信息。如遇特殊情况需修改或取消申请时，您可以联系客服寻求帮助（400-969-6599）。</p>
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
