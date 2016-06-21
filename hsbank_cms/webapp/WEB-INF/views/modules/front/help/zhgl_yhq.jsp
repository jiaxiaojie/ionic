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
   	
       <div class="right_title"><span>现金券和花生豆</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           	
           	<div class="panel panel-default">
                   <div class="panel-heading">
                     <h4 class="panel-title">
                       <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>现金券及其使用规则<i class="icon_plus"></i></a>
                     </h4>
                   </div>
                   <div id="collapse1" class="panel-collapse collapse">
                     <div class="panel-body">
	                     <dl>
	                     	<dt>现金券额度：</dt>
	                         <dd>5元、10元、20元、50元、100元</dd>
	                     </dl>
	                     <dl>
	                     	<dt>获取现金券：</dt>
	                     	 <dd>•	邀请好友注册即可获得现金券哦；</dd>
	                         <dd>•	网站不定期活动赠送现金券；</dd>
	                     </dl>
	                     <dl>
	                     	<dt>现金券使用规则：</dt>
	                     	 <dd>•	按照投资额5‰的比例使用现金券，例如：投资满1000可使用5元现金券；投资满2000可使用10元现金券；</dd>
	                         <dd>•	现金券从发放日起，有效期3个月；</dd>
	                     </dl>
                     </div>
                   </div>
               </div>
               
           	<div class="panel panel-default">
                   <div class="panel-heading">
                     <h4 class="panel-title">
                       <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>花生豆及其使用规则<i class="icon_plus"></i></a>
                     </h4>
                   </div>
                   <div id="collapse2" class="panel-collapse collapse">
                     <div class="panel-body">
	                     <dl>
	                     	<dt>获取花生豆：</dt>
	                     	 <dd>•	交易送花生豆，按投资额获取花生豆（体验金以及收益不能花生豆）；</dd>
	                         <dd>•	每日签到可获得10花生豆，每日只可签到一次；</dd>
	                     </dl>
	                     <dl>
	                     	<dt>花生豆规则：</dt>
	                     	 <dd>1.	根据的交易金额为您累积花生豆，投资或融资每1元积1分，不满1元不花生豆；</dd>
	                         <dd>2.	花生豆永久有效；</dd>
	                         <dd>3.	花生豆不能兑换现金，不可转让。</dd>
	                     </dl>
	                     <dl>
	                     	<dt>花生豆使用：</dt>
	                     	 <dd>•	在花生乐园兑换商品；</dd>
	                     </dl>
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
