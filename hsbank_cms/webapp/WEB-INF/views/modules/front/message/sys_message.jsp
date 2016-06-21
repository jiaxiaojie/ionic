<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="isReceivesAccount" value="${fns:toMap(accountMessage.isReceives, ',', ':',false,false)}"/>
<c:set var="switchIdsAccount" value="${fns:toMap(accountMessage.switchIds, ',', ':',false,false)}"/>
<c:set var="isReceivesSystem" value="${fns:toMap(systemMessage.isReceives, ',', ':',false,false)}"/>
<c:set var="switchIdsSystem" value="${fns:toMap(systemMessage.switchIds, ',', ':',false,false)}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="my_account" />
<!--提示框样式-->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/modules/front/css/wdzh/tzgl_wdzq.css?${version }" />
<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet">
<link href="${ctxStatic }/modules/front/css/util/util.css?${version }" rel="stylesheet">
<title></title>
</head>
<body>

	<div class="bg_789_top"></div>
	<div class="wdzh-main">
		<div id="tab_bar" class="messages-bar">
	        <li id="item_1" onClick="selectItem(1)" class="item"><span>账户消息</span></li>
	        <li id="item_2" onClick="selectItem(2)" class="item"><span class="selected">系统消息</span></li>
	        <li class="pull-right"><span><a href="javascript:void(0);" class="blue-text" id="bt_set_msg">消息设置</a></span></li>
	    </div>
	    
	    <div class="messages-tips clearfix">
	    	<div class="fl"><a href="javascript:void(0);" id="bt_del_mes" class="btn_brown mr10">删除</a><a href="javascript:void(0);" id="bt_read_mes" class="btn_brown">标为已读</a></div>
	    	<div class="fr">仅显示最近3个月的消息：共 <span>${totalCount }</span> 条消息，<span id="span_unread_show">${unreadCount }</span> 条未读</div>
	    </div>
		<div class="messages-list">
        <form id="inputListForm" action="modifyMyMessage" method="post">
            <input type="hidden" id="status" name="status" value="" />
            <input type="hidden" id="type" name="type" value="" />
            <input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
        	<div class="messages-list">
            	<dl>
                
                	<dt class="clearfix">
                        <span class="pull-left status"><input name="" id="checkedAll" type="checkbox"/>状态</span>
                        <span class="pull-left title">内容</span>
                        <span class="pull-right date">创建时间</span>
                    </dt>
                    
                    <c:forEach var="message" items="${page.list}" varStatus="status">
                    	<c:choose>
                    		<c:when test="${message.status == '2' }">
                    			<dd id="msg_${message.id }" class="have-read">
			                    	<p class="field clearfix">
			                        	<span class="pull-left status"><input name="messageIdList" type="checkbox" value="${message.id }" /><em>${fns:getDictLabel(message.status, 'message_status', '')}</em></span>
			                        	<span class="pull-left title"><a href="javascript:void(0);" onclick="showContent('${message.id }','${message.status }');"><i class="symbol" id="icon_${message.id }">+</i>${p2p:abbrev(message.content, 30) }</a></span>
			                        	<span class="pull-right date"><fmt:formatDate value="${message.createDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                        </p>
			                        <p id="content_${message.id }" class="text hide" style="word-warp:break-word; word-break:break-all;"><i class="arrow-top"></i>${message.content }</p>
			                    </dd>
                    		</c:when>
                    		<c:otherwise>
                    			<dd id="msg_${message.id }" class="unread">
			                    	<p class="field clearfix">
			                        	<span class="pull-left status"><input name="messageIdList" type="checkbox" value="${message.id }" /><em id="em_${message.id }">未读</em></span>
			                        	<span class="pull-left title"><a href="javascript:void(0);" onclick="showContent('${message.id }','${message.status }');"><i class="symbol" id="icon_${message.id }">+</i>${p2p:abbrev(message.content, 30) }</a></span>
			                        	<span class="pull-right date"><fmt:formatDate value="${message.createDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                        </p>
			                        <p id="content_${message.id }" class="text hide" style="word-warp:break-word; word-break:break-all;"><i class="arrow-top"></i>${message.content }</p>
			                    </dd>
                    		</c:otherwise>
                    	</c:choose>
	                	
                    </c:forEach>
                    
                </dl>
            </div>
        </form>
    </div>
    <div class="div_height_30"></div>
    
     <!--消息设置弹出层-->
    <div id="popup" class="pop_bg" style="display:none">
        <div class="pop_main" style=" width:600px; height:282px; margin-left:-300px; margin-top:-141px;">
            <div class="pop_title">消息设置<a href="javascript:void(0);" class="close_pop" id="bt_close_msg"></a></div>
            <div class="pop_content">
            <form id="receiveMsgSwitchSetting" action="${ctxFront}/customer/message/receiveMsgSwitchSetting" method="POST">
            	<div class="messages-content">
                	<dl class="clearfix no_border_bottom">
                    	<dt class="pull-left">账户通知</dt>
                    	<dd class="pull-left mc-text"><p class="line">项目回款</p></dd><%--提现到账、提现被拒、项目放款、项目回款<br />债权转让成功、债权转让失败 --%>
                    	<dd class="pull-left mc-sle clearfix">
                        	<span class="pull-left"><sys:icheckbox idName="id" name="isReceive" idVal="${switchIdsAccount[webMsgkey] }" checked="${isReceivesAccount[webMsgkey]!=1}" value="${isReceivesAccount[webMsgkey] }"/>站内信</span>
                       	  	<span class="pull-left mr0"><sys:icheckbox idName="id" name="isReceive" idVal="${switchIdsAccount[sysMsgKey] }" checked="${isReceivesAccount[sysMsgKey]!=1}" value="${isReceivesAccount[sysMsgKey] }"/>短信</span>
                        </dd>
                    </dl>
                	<dl class="clearfix">
                    	<dt class="pull-left">系统通知</dt>
                    	<dd class="pull-left mc-text"><p class="line">公告通知、平台活动、其他消息</p></dd>
                    	<dd class="pull-left mc-sle clearfix">
                        	<span class="pull-left"><sys:icheckbox idName="id" name="isReceive" idVal="${switchIdsSystem[webMsgkey] }" checked="${isReceivesSystem[webMsgkey]!=1}" value="${isReceivesSystem[webMsgkey] }"/>站内信</span>
                            <span class="pull-left mr0"><sys:icheckbox idName="id" name="isReceive" idVal="${switchIdsSystem[sysMsgKey] }" checked="${isReceivesSystem[sysMsgKey]!=1}" value="${isReceivesSystem[sysMsgKey] }"/>短信</span>
                        </dd>
                    </dl>
                </div>
                <!--点击确定按钮后会像“签到”了后一样，会提示一句话：“消息提醒设置保存成功”-->
                <div class="btn_group_one">
                    <a href="javascript:void(0);" id="receiveMsgSwitchSettingSubmit" class="btn_brown_158x31">确定</a>
                </div>
                </form>
            </div>
        </div>
    </div>

		<!-- 分页开始 -->
		<div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		<script type="text/javascript">
			function page(n,s) {
				var url = "${ctxFront}/customer/message/sysMessage?pageNo=" + n;
				window.location.href = url;
		}
		</script>
		<!-- 分页结束 -->
	</div>
    <div class="bg_789_bottom"></div>
	
	<!--提示框JS-->
	<script type="text/javascript">
	    $(document).ready(function(){
	    	//打开消息设置
	    	$(document).on('click', '#bt_set_msg', function() {
				$('#popup').toggle();
			});
	    	
	    	//关闭消息设置
			$(document).on('click', '#bt_close_msg', function() {
				$('#popup').toggle();
			});
	    	
	    	//全选或取消
			$(document).on('click', '#checkedAll', function() {
				if ($("#checkedAll").attr("checked")) {
	    	            $(":checkbox").attr("checked", true);
    	        } else {
    	            $(":checkbox").attr("checked", false);
    	        }
			});
	    	
	    	//删除消息
	    	$(document).on('click','#bt_del_mes',function(){
	    		if($("input[name='messageIdList']:checked").length ==0){
					$.jBox.alert("请选择要删除的消息！","提示");
				}else{
					$.jBox.confirm("确定删除选择的消息吗？", "操作提示",
							function(v, h, f) {
								if(v==1) {
									$("#status").val("-1");
									$("#type").val("0");
									$("#inputListForm").submit();
								}
							}, {
								buttons: {
									'确定' : 1,
									'取消' : -1
								}
							}
						);
				}
	    	});
	    	
	    	//标为已读
	    	$(document).on('click','#bt_read_mes',function(){
	    		if($("input[name='messageIdList']:checked").length ==0){
					$.jBox.alert("请选择要标为已读的消息！","提示");
				}else{
					$("#status").val("2");
					$("#type").val("0");
					$("#inputListForm").submit();
				}
	    	});
	    	
	    	//选择开关时给隐藏的表单赋值
	    	$('#receiveMsgSwitchSetting').find(":checkbox").bind('click', function() {
				var id = $(this).val();
				var isReceive = $("#"+id);
				if($(this).is(':checked')){
					isReceive.val(0);
				}else{
					isReceive.val(1);
				}
			});

	    	//点击确定时，保存消息设置
			$("#receiveMsgSwitchSettingSubmit").bind('click', function() {
				
				  var form = $("#receiveMsgSwitchSetting");
				  var params = serializeForm('receiveMsgSwitchSetting'); 
				  
				  $.ajax({
					   type: "POST",
					   url: form.attr("action"),
					   data: params,
					   success: function(data){
						   
						   if(data.success){
							   $.jBox.tip(data.message, 'success');
						   }else{
							   $.jBox.error(data.message,"提示");
							   window.setTimeout("location.reload(true);", 1000);
							   
						   }
					   },
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							
							$.jBox.error("消息提醒设置保存失败","提示");
							window.setTimeout("location.reload(true);", 1000);
						}
					});
				  
				  $('#popup').toggle();
				  return false;
			});
			
	    });
	    
		function selectItem(index) {
			if (index == 1) {
				window.location = "${ctxFront}/customer/message/myMessage";
			}else if (index == 2) {
				window.location = "${ctxFront}/customer/message/sysMessage";
			}
		}
		
		//读取消息
		function showContent(id,status){
			if($("#content_"+id).hasClass("text hide")){
				$("#icon_"+id).html("-");
				$("#content_"+id).removeClass("text hide").addClass("text show");
				var em_text = $("#em_"+id).text();
				if(status !='2' && em_text =='未读'){
					var data = {};
					data.id = id;
					data.type = "0";
					$.get("${ctxFront}/customer/message/readMessage",data,function(data){
						var isResult = data.isResult;
						var unreadCount = data.unreadCount;
						if(isResult){
							$("#em_"+id).html("已读");
							$("#span_unread_show").html(unreadCount);
							$("#msg_"+id).removeClass("unread").addClass("have-read");
						}
					});
				}
			}else{
				$("#icon_"+id).html("+");
				$("#content_"+id).removeClass("text show").addClass("text hide");
			}
		}
	</script>

</body>
</html>
