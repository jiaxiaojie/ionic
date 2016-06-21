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
    	
        <div class="right_title"><span>借款审核与风控</span></div>
    
    	<div class="content">
            <div class="panel-group" id="accordion">
            	
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>严格的贷前审核<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>在客户提出借款申请后，花生金服会对客户的基本资料进行分析。通过网络、电话及其他可以掌握的有效渠道进行详实、仔细的调查。避免不良客户的欺诈风险。在资料信息核实完成后，根据个人信用风险分析系统进行评估，由经验丰富的借款审核人员进行双重审核确认后最终决定批核结果。</p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>完善的贷后管理<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>如果用户逾期未归还借款，贷后管理部门将第一时间通过短信、电话等方式提醒用户进行还款。如果用户在5天内还未归还当期借款，花生金服将会联系该用户的紧急联系人、直系亲属、单位等督促用户尽快还款。如果用户仍未还款，交由专业的高级催收团队与第三方专业机构合作进行包括上门等一系列的催收工作，启动法律手段，直至催款成功。</p>
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
