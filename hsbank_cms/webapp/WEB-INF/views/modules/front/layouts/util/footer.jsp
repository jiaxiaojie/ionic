<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<!-- 版权申明区域 -->
<div id="footer_area" class="footer_area">
	<div class="footer_menu">
       	<span class=""><a href="${ctxFront}/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront}/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/index#cooperationAgency">友情链接</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">网站地图</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">法律声明</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">黑名单</a></span>
    </div>
	<div class="div_height_20"></div>
	<div class="footer_text">
    	<span>客服邮箱：service@fdjf.net</span>&nbsp;&nbsp;&nbsp;&nbsp;
        <span>服务热线：<b class="font_size_18">400-969-6599</b>&nbsp;(工作日9:00~18:00)</span>
    </div>
    <div class="div_height_10"></div>
	<div class="footer_text">©2015上海富定金融信息服务股份有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<!-- <a href="javascript:void(0);" class="logo_kx"></a> -->
    	<!-- <a href="javascript:void(0);" class="logo_aq"></a> -->
    	<!-- <a href="javascript:void(0);" class="logo_x"></a> -->
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>
<!-- 右侧工具栏 -->
<div class="bottom_tools">
	<a href="javascript:;" target="_blank" class="icon_erweima"></a>
	<a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzkzODA0NTY4N18zMTkzMTJfNDAwOTY5NjU5OV8yXw"  target="_blank" class="icon_service"></a>
	<a href="javascript:void(0);" id="bt_feedback" class="icon_feedback"></a>
    <a id="scrollUp" href="javascript:;"></a>
    <img class="img_tdc_right" src="${ctxStatic}/modules/front/images/util/img_tdc_right.png">
</div>
<div id="div_feedback" class="feedback hide">
	<div class="feedback_bg"></div>
    <div class="feedback_main">
    	<a href="javascript:void(0);" id="bt_close_feedback" class="icon_close"></a>
    	<div class="feedback_title">意见反馈</div>
        <div class="feedback_textarea">
        	<form id="sendForm" action="feedback/send" method="post">
            	<textarea id="feedback_content" name="content"  maxlength="500"  placeholder="请在此反馈您对我们网站的意见建议！"></textarea>
            </form>
        </div>
        <div class="tips_text">您还可以输入<span id="content_word" style="color:red;">500</span>个文字</div>
        <div class="feedback_button"><a id="bt_submit_send" href="javascript:void(0);" onclick="sendSubmit();" >发送</a></div>
    </div>
</div>

<script type="text/javascript">

  	$(document).ready(function() {
		$(document).on('click', '#logo', function() {
			window.location.href="${ctxFront}/index";
		});
		
		var $body = $(document.body);
		var $bottomTools = $('.bottom_tools');
		var $qrTools = $('.icon_erweima');
		var qrImg = $('.img_tdc_right');
		$(window).scroll(function () {
			var scrollHeight = $(document).height();
			var scrollTop = $(window).scrollTop();
			var $footerHeight = $('.page-footer').outerHeight(true);
			var $windowHeight = $(window).innerHeight();
			scrollTop > 50 ? $("#scrollUp").fadeIn(200).css("display","block") : $("#scrollUp").fadeOut(200);			
			$bottomTools.css("bottom", scrollHeight - scrollTop - $footerHeight > $windowHeight ? 40 : $windowHeight + scrollTop + $footerHeight + 40 - scrollHeight);
		});
		$('#scrollUp').click(function (e) {
			e.preventDefault();
			$('html,body').animate({ scrollTop:0});
		});
		$qrTools.hover(function () {
			qrImg.fadeIn();
		}, function(){
			 qrImg.fadeOut();
		});
		
		//显示反馈意见
		$(document).on('click', '#bt_feedback', function() {
			$('#div_feedback').toggle();
		});
		
		//关闭反馈意见
		$(document).on('click', '#bt_close_feedback', function() {
			$('#div_feedback').toggle();
		});
		//计算输入的文字
		$(document).keyup(function() { 
	  		var text = $("#feedback_content").val(); 
	  	  	var length = text.length;	
			$("#content_word").text(500 - length); 
		}); 
		
	});
  	//发送提交
  	function sendSubmit(){
  		var content = $("#feedback_content").val();
  		if($("#sendForm").valid()) {
  			$.ajax({
  				type : 'post',
  				url : '${ctxFront}/feedback/send',
  				data : {
  					content : content
  				},
  				dataType : 'json',
  				success : function(data) {
  					$('#div_feedback').toggle();
  					if(data.flag=='ok'){
  						$.jBox.success("意见反馈发送成功！");
  					}else{
  						$.jBox.success("意见反馈发送失败！");
  					}
  				}
  			});
  		}
	}
  	//意见反馈内容校验
  	$(function(){
		$("#sendForm").validate({
			rules: {content: {required: true, maxlength : 500}},
			messages: {content: {required: "请输入意见反馈内容.", maxlength: "意见反馈内容最大为500字."}}
		});
	});
</script>
