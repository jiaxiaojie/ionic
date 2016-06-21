<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借贷产品管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/project/projectBaseInfo/loanlist">合同放款列表</a></li>
		<li class="active"><a href="#">合同放款</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectBaseInfo" action="${ctx}/project/projectBaseInfo/loan" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="isAutoRepay" name="isAutoRepay" value="${projectBaseInfo.isAutoRepay }" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				${projectBaseInfo.projectCode }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否新手项目：</label>
			<div class="controls">
			${fns:getDictLabel(projectBaseInfo.isNewUser, 'project_is_new_user_dict', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自动还款授权：</label>
			<div class="controls">
			${fns:getDictLabel(projectBaseInfo.isAutoRepay, 'project_is_auto_repay_dict', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否放款：</label>
			<div class="controls">
			${fns:getDictLabel(projectBaseInfo.isLoan, 'yes_no', '')}
			</div>
		</div>
		<c:if test="${not empty projectBaseInfo.loanDt }">
			<div class="control-group">
			<label class="control-label">放款时间：</label>
			<div class="controls">
				<fmt:formatDate value="${projectBaseInfo.loanDt }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">项目类型：</label>
			<div class="controls">
			${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				${projectBaseInfo.projectName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
			${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资人：</label>
			<div class="controls">
			    ${projectBaseInfo.bUser.customerName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资代理人：</label>
			<div class="controls">
			    ${projectBaseInfo.aUser.customerName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目区域位置：</label>
			<div class="controls">
			${projectBaseInfo.area.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
			    ${projectBaseInfo.annualizedRate}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本期融资金额：</label>
			<div class="controls">
			   <fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00" /> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费：</label>
			<div class="controls">
			    <fmt:formatNumber value="${projectBaseInfo.serviceCharge}" pattern="#0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已融资金额：</label>
			<div class="controls">
			    <fmt:formatNumber value="${projectExecuteSnapshot.endFinanceMoney}" pattern="#0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已冻结服务费：</label>
			<div class="controls">
			    <fmt:formatNumber value="${projectExecuteSnapshot.sumServiceCharge}" pattern="#0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已冻结抵用额：</label>
			<div class="controls">
			    <fmt:formatNumber value="${projectExecuteSnapshot.sumPlatformAmount}" pattern="#0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已冻结抵用券额度：</label>
			<div class="controls">
			    <fmt:formatNumber value="${projectExecuteSnapshot.sumTicketMoney}" pattern="#0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认信息：</label>
			<div class="controls">
			                此项目原融资金额：<fmt:formatNumber value="${projectBaseInfo.financeMoney}" pattern="#0.00" />元 </br>
				    原服务费：  <fmt:formatNumber value="${projectBaseInfo.serviceCharge}" pattern="#0.00" />元</br>
				    已募资：<fmt:formatNumber value="${projectExecuteSnapshot.endFinanceMoney}" pattern="#0.00" />元  </br>
				    已锁定服务费：<fmt:formatNumber value="${projectExecuteSnapshot.sumServiceCharge}" pattern="#0.00" />元</br>
				    投资者累计使用抵用券：<fmt:formatNumber value="${projectExecuteSnapshot.sumTicketMoney}" pattern="#0.00" />元 ， 使用抵用额：<fmt:formatNumber value="${projectExecuteSnapshot.sumPlatformAmount}" pattern="#0.00" />元</br>
                                                    确认放款，请确保平台账户余额足够支付：<fmt:formatNumber value="${stayPaidAmount}" pattern="#0.00" />元
		    </div>
		</div>
		<div class="form-actions">
		    <c:if test="${projectBaseInfo.isLoan eq '0'}">
		    	<shiro:hasPermission name="project:projectBaseInfo:loan"><input id="btnSubmit" class="btn btn-primary" type="button" value="确认放款"/>&nbsp;</shiro:hasPermission>
		    </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
		$(document).ready(function(){
			//确认放款
			$('#btnSubmit').click(function() {
				var isAutoRepay = $("#isAutoRepay").val();
				if(isAutoRepay == '0'){
					alert("还未授权，请先授权！");
					return;
				}else{
					loading('正在提交，请稍等...');
					$("#inputForm").submit();
				}
				
			});
		});
	</script>
</body>
</html>