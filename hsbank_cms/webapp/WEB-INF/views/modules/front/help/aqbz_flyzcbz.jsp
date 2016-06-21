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
    	
        <div class="right_title"><span>法律与政策保障</span></div>
    
    	<div class="content">
            <div class="panel-group" id="accordion">
            	
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>花生金服平台提供居间撮合服务的合法性<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>《合同法》第23章专门对“居间合同”作出规定，其第424条明确定义为：“居间合同是居间人向委托人报告订立合同的机会或者提供订立合同的媒介服务，委托人支付报酬的合同”。花生金服平台是合法设立的居间服务机构，致力于为民间借贷业务提供优质高效的撮合服务，以促成借贷双方形成借贷关系，然后收取相关报酬。此种居间服务有着明确的法律依据。</p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>投资人及融资人之间的借贷关系的合法性<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>《合同法》第196条规定：“借款合同是融资人向贷款人借款，到期返还借款并支付利息的合同”；根据《合同法》第十二章“借款合同”和最高人民法院《关于人民法院审理借贷案件的若干意见》，我国法律允许自然人等普通民事主体之间发生借贷关系，并允许出借方到期可以收回本金和符合法律规定的利息。投资人作为出借人，与融资人之间形成的借贷关系受到法律保护。</p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>投资人通过花生金服平台获得的出借理财收益的合法性<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse3" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>根据 《最高人民法院关于审理民间借贷案件适用法律若干问题的规定》，第二十六条，借贷双方约定的利率未超过年利率24%，出借人请求借款人按照约定的利率支付利息的，人民法院应予支持。借贷双方约定的利率超过年利率36%，超过部分的利息约定无效。借款人请求出借人返还已支付的超过年利率36%部分的利息的，人民法院应予支持。</p>
                      </div>
                    </div>
                </div>
                
            	<div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>电子合同的合法性<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse4" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>根据《合同法》第11条的规定，当事人可以采用合同书、信件和数据电文（包括电报、电传、传真、电子数据交换和电子邮件）等形式订立合同。电子合同是法律认可的书面合同形式之一。花生金服采取用户网上点击确认的方式签署电子合同。点击确认后的电子合同符合《中华人民共和国合同法》规定的合同成立、生效的要件，其有效性也被人民法院的司法实践所接受。</p>
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
