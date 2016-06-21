<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="messageChannels" value="${fns:getDictList('message_channel')}"/>
<c:set var="messageTypes" value="${fns:getDictList('message_type')}"/>

<html>
<head>
	<title>用户接收消息开关管理</title>
	<meta name="decorator" content="default"/>

</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/message/receiveMessageSwitch/">用户接收消息开关列表</a></li>
		<shiro:hasPermission name="message:receiveMessageSwitch:edit"><li><a href="${ctx}/message/receiveMessageSwitch/form">用户接收消息开关添加</a></li></shiro:hasPermission>
	</ul>
	<jsp:include page="../customer/common/customerMessageMenu.jsp"/>
	<form:form id="searchForm" modelAttribute="receiveMessageSwitch" action="${ctx}/message/receiveMessageSwitch/customerSwitchSettingList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
				<%-- <li class="btns"><a href="#" id="accountList">选择</a></li> --%>
				<sys:selectPanel idName="resultAccountId" textName="resultAccountName" callbackOnSelected="submitSearchForm()" url="${ctx}/customer/customerAccountInfo/customerAccountInfoList" path="accountId" title="选择账户" ></sys:selectPanel>
			</li>
			
			
			
			<c:if test="${modelMenus.accountId==null }">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="刷新"/></li>
			<li class="btns"><input id="systemSettingBtn" class="btn btn-primary" type="button"  value="查询系统设置"/></li>
			</c:if>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>类型</th>
				
				<c:forEach items="${messageChannels }" var="messageChannel">
					<th>${messageChannel.label }</th>
				</c:forEach>
				
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="row">
			<tr>
				<td>
					${fns:getDictLabel(row.messageType, 'message_type', '')}
				</td>
				<c:set var="isReceives" value="${fns:toMap(row.isReceives, ',', ':',false,false)}"/>
				<c:set var="switchIds" value="${fns:toMap(row.switchIds, ',', ':',false,false)}"/>
				<c:forEach items="${messageChannels }" var="messageChannel">
					<c:set var="channelValue" value="chn_${messageChannel.value}"/>
					<td>
					
					<span  name="isReceiveText">${fns:getDictLabel(isReceives[channelValue], 'is_receive_message', '')}</span>
					<input type="checkbox" style="display: none;"  ${isReceives[channelValue]!=1?'checked="checked"':'' }
					 value="${switchIds[channelValue]}" data-on-
								color="info" name="isReceive"  onchange="doSwitch(this);">
								
					</td>
				</c:forEach>
					<shiro:hasPermission name="message:receiveMessageSwitch:edit">
						<script type="text/javascript">
						$(document).ready(function() {
							$("span[name='isReceiveText']").remove();
							$("input[name='isReceive']").removeAttr("style");
						});
						</script>
					</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
	function submitSearchForm(){
		$("#searchForm").submit();
	}
	function queryEntiyById(myurl,id){
		
		var ey = null;
			$.ajax({
			   type: "POST",
			   url:myurl ,
			   data: "id="+id,
			   async: false,
			   success: function(entity){
				  ey = entity;
			   }
			});
			
		return ey;
	}
	
	function formatStr(str){
		var s = str;//要展示的字符串
		var length = 7;
		if(str.length>length){
		  s=str.substring(0,length)+"...";
		}
		return s;
	}
	
		function formatResult(repo) {
			if (repo.loading)
				return repo.text;
			var markup = "<div class='row' onclick='b'>" + "<div class='span1'>"
					+ formatStr(repo.text) + "</div>" ;
			return markup;
		}
		function formatSelection(repo) {
			
			if(repo.text=='规则名称'){
				$.jBox.tip("第一行为标题，不能选择");
				return false;
			}
			
			return repo.val;
		}
		
		
	

	
		$(document).ready(function() {
			
			
			$('#systemSettingBtn').bind('click', function() {
				$("#accountId").val(0);
				$("#textId_accountId").val("系统设置");
				$("#searchForm").submit();
			});

			
			
			$("#messageRuleList").click(function(){
				top.$.jBox.open("iframe:${ctx}/message/messageCreateRule/list?accountId=${modelMenus.accountId}&status=1&selectView=true&idName=s", "选择规则", 1100, $(top.document).height()-180, {
		            buttons:{"确定":"ok", "清除":"clear", "关闭":true}, submit:function(v, h, f){
		                if (v=="ok"){
		                	var selectedRuleId = h.find("iframe")[0].contentWindow.$("#selectedResultVal").val();
		                	if(selectedRuleId != null && selectedRuleId != ''){
		                		$("#ruleId").attr("value",selectedRuleId);
								$("#searchForm").submit();
		                	}
		                	
			                
		                }else if (v=="clear"){
			                
		                }
		            }, loaded:function(h){
		                $(".jbox-content", top.document).css("overflow-y","hidden");
		            }
		        });
			});
			
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function doSwitch(obj){
			$("input[type='checkbox']").attr("disabled","disabled"); 
			var swId = $(obj).val();
			var swIsReceive = 0;
			if($(obj).attr("checked")=="checked"){
				swIsReceive=0;
			}else{
				swIsReceive=1;
			}
			
			$.ajax({
				type : 'post',
				url : '${ctx}/message/receiveMessageSwitch/customReceiveMsgSwitchSetting',
				data : {
					id:swId,
					accountId:'${params.accountId}',
					isReceive:swIsReceive
				},
				dataType : 'json',
				success : function(data) {
					
					$.jBox.tip(data.msg, 'success');
				   
					var refresh = data.refresh;
					if(refresh==true){
						 window.setTimeout('$("#searchForm").submit()', 1000);
					}
					else{
						$("input[type='checkbox']").removeAttr("disabled"); 
					}
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					$.jBox.error("修改失败","提示");
					window.setTimeout('$("#searchForm").submit()', 1000);
				}
			});
		}
	</script>
	
	
</body>
</html>