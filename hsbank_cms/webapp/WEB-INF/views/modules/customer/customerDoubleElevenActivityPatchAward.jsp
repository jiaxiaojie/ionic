<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function() {
			if(${isShowOpenThirdAccountPatchTicketToCustomer}) {
				$("#openThirdAccountPatchTicketToCustomer").show();
			}
			if(${isShowOpenThirdAccountPatchTicketToCustomerAndRecommender}) {
				$("#openThirdAccountPatchTicketToCustomerAndRecommender").show();
			}
			if(${isShowOpenThirdAccountPatchAmountToCustomer}) {
				$("#openThirdAccountPatchAmountToCustomer").show();
			}
			if(${isShowOpenThirdAccountPatchAmountToCustomerAndRecommender}) {
				$("#openThirdAccountPatchAmountToCustomerAndRecommender").show();
			}
			if(${isShowFirstInvestmentPatchTicketToCustomer}) {
				$("#firstInvestmentPatchTicketToCustomer").show();
			}
		});

		function openThirdAccountPatchTicketToCustomerClick() {
			window.location.href = "${ctx}/customer/customerDoubleElevenActivity/openThirdAccountPatchTicketToCustomer?mobile=" + $("#mobile").val();
		}
		function openThirdAccountPatchTicketToCustomerAndRecommenderClick() {
			window.location.href = "${ctx}/customer/customerDoubleElevenActivity/openThirdAccountPatchTicketToCustomerAndRecommender?mobile=" + $("#mobile").val();
		}
		function openThirdAccountPatchAmountToCustomerClick() {
			window.location.href = "${ctx}/customer/customerDoubleElevenActivity/openThirdAccountPatchAmountToCustomer?mobile=" + $("#mobile").val();
		}
		function openThirdAccountPatchAmountToCustomerAndRecommenderClick() {
			window.location.href = "${ctx}/customer/customerDoubleElevenActivity/openThirdAccountPatchAmountToCustomerAndRecommender?mobile=" + $("#mobile").val();
		}
		function firstInvestmentPatchTicketToCustomerClick() {
			window.location.href = "${ctx}/customer/customerDoubleElevenActivity/firstInvestmentPatchTicketToCustomer?mobile=" + $("#mobile").val();
		}
		function searchMobile() {
			window.location.href = "${ctx}/customer/customerDoubleElevenActivity/patchAward?mobile=" + $("#mobile").val();
		}
	</script>
</head>
<body>
	<form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<input type="text" id="mobile" name="mobile" value="${mobile }" />
				<input class="btn" type="button" value="查询" onclick="searchMobile()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账号时间信息：</label>
			<div class="controls">
				注册时间：<fmt:formatDate value="${customerAccount.registerDt }" pattern="yyyy-MM-dd HH:mm:ss" /><br/>
				开通第三方账号时间：<fmt:formatDate value="${openThirdAccountDt }" pattern="yyyy-MM-dd HH:mm:ss" /><br/>
				首次投资时间：<fmt:formatDate value="${firstRecord.opDt }" pattern="yyyy-MM-dd HH:mm:ss" /><br/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补送开通第三方账号操作：</label>
			<div class="controls">
				<input id="openThirdAccountPatchTicketToCustomer" style="display:none;" class="btn" type="button" value="仅补送注册人20元现金券" onclick="openThirdAccountPatchTicketToCustomerClick()"/>
				<input id="openThirdAccountPatchTicketToCustomerAndRecommender" style="display:none;" class="btn" type="button" value="补送注册人及推荐人各20元现金券" onclick="openThirdAccountPatchTicketToCustomerAndRecommenderClick()"/>
				<input id="openThirdAccountPatchAmountToCustomer" style="display:none;" class="btn" type="button" value="仅补送注册人1.88元现金" onclick="openThirdAccountPatchAmountToCustomerClick()"/>
				<input id="openThirdAccountPatchAmountToCustomerAndRecommender" style="display:none;" class="btn" type="button" value="补送注册人及推荐人各1.88元现金" onclick="openThirdAccountPatchAmountToCustomerAndRecommenderClick()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补送首次投资操作：</label>
			<div class="controls">
				<input id="firstInvestmentPatchTicketToCustomer" style="display:none;" class="btn" type="button" value="补送200元现金券" onclick="firstInvestmentPatchTicketToCustomerClick()"/>
			</div>
		</div>
	</form>
</body>
</html>