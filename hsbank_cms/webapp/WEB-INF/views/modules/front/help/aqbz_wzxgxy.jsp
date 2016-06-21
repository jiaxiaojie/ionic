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
    	
        <div class="right_title"><span>网站相关协议</span></div>
    
    	<div class="content">
            <div class="panel-group" id="accordion">
            	
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>电子合同的有效性<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>通过花生金服审核的借款方向投资方借贷，双方通过平台的电子借贷协议，明确双方的债务与债权关系。依据中华人民共和国合同法第十一条规定：“书面形式是指合同书、信件和数据电文（包括电报、电传、传真、电子数据交换和电子邮件）等可以有形地表现所载内容的形式”，花生金服上电子合同与传统合同具有同等的法律效力，花生金服服务仅向符合中华人民共和国有关法律法规及本公司相关规定的合格投资人和融资人提供。</p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>如何查询我的借款协议？<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>1.如果您已在网上成功的获得借款，您可以在网站上打开【我的账户】--【融资管理】--【我的借款】--【借款协议】</p>
                        <p>2.如果您已在网上成功的投标理财，您可以在网站上打开【我的账户】--【投资管理】--【我的债权】--【借款协议】</p>
                        <p>3.您也可以在帮助中查看“网站借款协议范本”</p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>网站借款协议范本<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse3" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>点击查看网站<a href="${ctxStatic}/agreement/loan_agreement.html" target="_blank" class="btn_text_gold">借款协议范本</a></p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>债权转让说明书<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse4" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>点击查看网站<a href="${ctxStatic}/agreement/transfer_agreement.html" target="_blank" class="btn_text_gold">债权转让说明书</a></p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>债权转让协议<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse5" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>债权转让及受让协议范本</p>
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
