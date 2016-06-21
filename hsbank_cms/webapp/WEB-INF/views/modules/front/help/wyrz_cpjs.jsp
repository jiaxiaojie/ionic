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
		<div class="right_title"><span>产品介绍</span></div>     
     	<div class="content">
             <p class="font_size_14">生金服是为个人及中小企业解决融资、贷款需求的互联网金融平台，我们为投资用户提供多样化的投资项目，为融资用户提供方便快捷的信用贷款。</p>
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
