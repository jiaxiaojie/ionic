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
   	
       <div class="right_title"><span>账户及隐私安全</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           	
           	<div class="panel panel-default">
                   <div class="panel-heading">
                     <h4 class="panel-title">
                       <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>网站技术保障<i class="icon_plus"></i></a>
                              </h4>
                            </div>
                            <div id="collapse1" class="panel-collapse collapse">
                              <div class="panel-body">
                                <p>花生金服网站运用各种先进的安全技术保护用户在花生金服账户中存储的个人信息、账户信息以及交易记录，以免用户账户遭受未经授权的访问、使用以及信息泄露。</p>
                                <p>花生金服网站有着完善的安全监测系统，可以及时发现网站的非正常访问并做相应的安全响应。对于用户的账户信息，花生金服网站会对其进行高强度的加密，以防止用户信息的外泄。</p>
                                <p>同时，我们还会持续更新和改进网站的安全策略，以保证网站安全策略的有效性和健壮性。</p>
                                <p><span class="label label-info">权限管理和隐私安全</span></p>
                                <p>我们严格遵守国家相关的法律法规，对用户的隐私信息进行保护。未经您的同意，花生金服不会向任何第三方公司、组织和个人披露您的个人信息、账户信息以及交易信息（法律法规另有规定的除外）。</p>
                                <p>同时，花生金服公司内部也设有严格、完善的权限管理体系，以保证每一位内部员工都只能查看自己职责和权限之内的数据和信息。</p>
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
