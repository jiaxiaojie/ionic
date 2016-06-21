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
   	
       <div class="right_title"><span>用户的自我保护</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           	
           	<div class="panel panel-default">
                   <div class="panel-heading">
                     <h4 class="panel-title">
                       <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>牢记花生金服官方网址<i class="icon_plus"></i></a>
                    </h4>
                  </div>
                  <div id="collapse1" class="panel-collapse collapse">
                    <div class="panel-body">
                      <p>牢记花生金服官方网址：<a href="http://www.hsbank360.com/">www.hsbank360.com</a></p>
                      <p>不要点击来历不明的链接访问花生金服，谨防网站钓鱼和欺诈。我们建议您将花生金服官方网址加入浏览器收藏夹，以方便您的下次登录。</p>
                    </div>
                  </div>
              </div>
              
          	<div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>为您的花生金服账户设置高强度的登录密码<i class="icon_plus"></i></a>
                    </h4>
                  </div>
                  <div id="collapse2" class="panel-collapse collapse">
                    <div class="panel-body">
                      <p>您在密码设置时，最好使用数字和字母混合，不要使用纯数字或纯字母，且密码长度要在6位以上。</p>
                      <p>不要使用您的生日、姓名拼音、身份证号码、手机号或是邮箱名作为登录密码。</p>
                      <p>不要使用连续的，或重复的字母、数字组合作为密码，例如：aaaaaa，111111，abcdef，123456。</p>
                      <p>不要使用以下常用密码，例如：qazwsx，qwerty，mima123，password等。</p>
                    </div>
                  </div>
              </div>
              
          	<div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>注重电脑运行环境的安全<i class="icon_plus"></i></a>
                    </h4>
                  </div>
                  <div id="collapse3" class="panel-collapse collapse">
                    <div class="panel-body">
                      <p>1.及时为您的电脑进行系统更新，安装系统安全补丁，以防系统漏洞被黑客利用。</p>
                      <p>2.为您的电脑安装杀毒软件或防火墙，并定期为电脑进行查毒、杀毒。</p>
                      <p>3.避免在网吧等公共场所使用网上银行，不要打开来历不明的电子邮件。</p>
                      <p>4.不要访问危险的网站，不要使用来历不明的软件。</p>
                    </div>
                  </div>
              </div>
              
          	<div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>时刻注意保护个人隐私<i class="icon_plus"></i></a>
                    </h4>
                  </div>
                  <div id="collapse4" class="panel-collapse collapse">
                    <div class="panel-body">
                      <p>用户在平台上交流的过程中，不要向其他用户透露自己真实姓名与住址等，以防个人信息被盗取造成损失。</p>
                    </div>
                  </div>
              </div>
              
          	<div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>避免私下交易<i class="icon_plus"></i></a>
                      </h4>
                    </div>
                    <div id="collapse5" class="panel-collapse collapse">
                      <div class="panel-body">
                        <p>花生金服建议用户避免私下交易。私下交易的约束力极低，造成逾期的风险非常高，同时您的个人信息将有可能被泄漏，存在遭遇诈骗甚至受到严重犯罪侵害的隐患。</p>
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
