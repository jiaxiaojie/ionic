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
    	
        <div class="right_title"><span>产品介绍 - 风控</span></div>
    
    	<div class="content">
            <div class="panel-group" id="accordion">
            
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title">
                    <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>什么是债权转让？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse1" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>指债权持有人通过花生金服债权转让平台将债权挂出且与购买人签订债权转让协议，将所持有的债权转让给购买人的操作。<a href="#" class="btn_text_gold">查看详情>></a></p>
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
