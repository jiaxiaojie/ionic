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
   	
    <div class="right_title"><span>认证资料</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>必要认证资料和可选认证资料有哪些？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>花生金服根据您提供的信用资料进行评级，您提供的资料越详细，您的信用评级将会越高。</p>
                   <dt>认证项如下：</dt>
                   <p>身份认证、个人信用报告、劳动合同或在职证明、近3个月工资卡银行流水、房产认证、购车认证、结婚认证、学历认证、居住地证明。</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>身份认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                 	<p>您上传的身份证照片须和您绑定的身份证一致，否则将无法通过认证。</p>
                 	<dl>
                   	<dt>请同时提供下面两项资料：</dt>
                       <dd>1.本人身份证原件的正、反两面照片。</dd>
                       <dd>2.本人手持身份证正面头部照，（确保身份证上的信息没有被遮挡，避免证件与头部重叠，确保身份证内容完整清晰可见）。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证示例：</dt>
                       <dd><a href="" class="btn_text_gold">身份证正反面照片</a></dd>
                       <dd><a href="" class="btn_text_gold">本人手持身份证正面头部照片</a></dd>
                   </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>信用报告<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>个人信用报告是由中国人民银行出具，全面记录个人信用活动，反映个人信用基本状况的文件。本报告是花生金服了解您信用状况的一个重要参考资料。</p>
                 	<dl>
                   	<dt>认证说明：</dt>
                       <dd>个人信用报告需15日内开具。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证条件：</dt>
                       <dd>信用记录良好。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证有效期：</dt>
                       <dd>6个月</dd>
                   </dl>
                 	<dl>
                   	<dt>认证示例：</dt>
                       <dd><a href="" class="btn_text_gold">个人信用报告</a></dd>
                   </dl>
                 	<dl>
                   	<dt>如何办理：</dt>
                       <dd>可去当地人民银行打印，部分地区可登录个人信用信息服务平台。</dd>
                       <dd><a href="http://www.pbccrc.org.cn/zxzx/lxfs/lxfs.shtml" class="btn_text_gold" target="_blank">全国各地征信中心联系方式查询</a></dd>
                       <dd><a href="http://www.pbccrc.org.cn/" class="btn_text_gold" target="_blank">个人信用信息服务平台</a></dd>
                       <dd><a href="http://www.pbccrc.org.cn/zxzx/zxdt/201405/ef4aad432f2449a0a6c8650914c89977.shtml" class="btn_text_gold" target="_blank">关于个人查询本人信用报告实施收费的公告</a></dd>
                   </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>工作认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse4" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>在职证明</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>收入认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse5" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>收入证明</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse6" class="collapsed"><em class="icon_arrow_right"></em>房产认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse6" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>房产认证是证明融资人资产及还款能力的重要凭证之一。</p>
                 	<dl>
                   	<dt>认证说明：</dt>
                       <dd>请提供下面任意一项资料：</dd>
                       <dd>1.房屋产权证明。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证条件：</dt>
                       <dd>必须是商品房，且房产是本人名下所有或共有的。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证示例：</dt>
                       <dd><a href="" class="btn_text_gold">房产证</a></dd>
                       <dd><a href="" class="btn_text_gold">购房发票</a></dd>
                       <dd><a href="" class="btn_text_gold">购房合同</a></dd>
                       <dd><a href="" class="btn_text_gold">还款证明</a></dd>
                       <dd><a href="" class="btn_text_gold">房屋查档资料</a></dd>
                   </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse7" class="collapsed"><em class="icon_arrow_right"></em>购车认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse7" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>购车认证是证明融资人资产及还款能力的重要凭证之一。</p>
                 	<dl>
                   	<dt>认证说明：</dt>
                       <dd>请同时提供下面两项资料：</dd>
                       <dd>1.车辆行驶证的原件照片。</dd>
                       <dd>2.本人和车辆的合影（照片需露出车牌号码）。</dd>
                       <dd>3.车辆登记证原件照片</dd>
                   </dl>
                 	<dl>
                   	<dt>认证条件：</dt>
                       <dd>车辆必须是本人名下所有。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证示例：</dt>
                       <dd><a href="" class="btn_text_gold">车辆行驶证</a></dd>
                       <dd><a href="" class="btn_text_gold">车辆登记证</a></dd>
                   </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse8" class="collapsed"><em class="icon_arrow_right"></em>结婚认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse8" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>融资人婚姻状况的稳定性，是花生金服考核融资人信用的评估因素之一。</p>
                 	<dl>
                   	<dt>认证说明：</dt>
                       <dd>请同时提供下面三项资料：</dd>
                       <dd>1.结婚证书的原件照片</dd>
                       <dd>2.本人和配偶的近照合影一张</dd>
                       <dd>3.户口本</dd>
                       <dd>4.已婚证明</dd>
                   </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse9" class="collapsed"><em class="icon_arrow_right"></em>学历认证<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse9" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>投资人在选择借款申请投标时，融资人的学历也是一个重要的参考因素。为了让投资人更好、更快地相信您的学历是真实的，建议您对学历进行在线验证。</p>
                 	<dl>
                   	<dt>认证说明：</dt>
                       <dd>学历证明扫描件</dd>
                   </dl>
                 	<dl>
                   	<dt>认证条件：</dt>
                       <dd>大专或以上学历（普通全日制）。</dd>
                   </dl>
                 	<dl>
                   	<dt>认证示例：</dt>
                       <dd><a href="" class="btn_text_gold">学历认证1</a></dd>
                       <dd><a href="" class="btn_text_gold">学历认证2</a></dd>
                   </dl>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse10" class="collapsed"><em class="icon_arrow_right"></em>居住地证明<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse10" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>户口簿扫描件或照片</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse11" class="collapsed"><em class="icon_arrow_right"></em>“完整的银行流水，中间不能出现断层”是什么意思？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse11" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>要求银行流水有连续月份，内容包括交易日期、交易金额、余额、交易地点、交易摘要等。（点击<a href="" class="btn_text_gold">银行卡流水</a>，查看示例图片）</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse12" class="collapsed"><em class="icon_arrow_right"></em>如何上传、补充、修改或撤销资料？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse12" class="panel-collapse collapse">
                  <div class="panel-body">
                    <p>登录花生金服网站—我的账户—账号管理—认证信息，在认证信息页面您可查看、修改您的信用资料</p>
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
