<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.thinkgem.jeesite.modules.sys.entity.Dict"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />
<link href="${ctxStatic}/modules/front/css/wyrz.css?${version }" rel="stylesheet" />
<title></title>
<script type="text/javascript">
	function doBudgetary(){
		if($("#projectWillLoan").valid()){
		var termsPara=$('#terms').val();
		if(termsPara==''){
			termsPara='24';
		}
		$.ajax({
			type : 'post',
			url : '${ctxFront}/willloan/willloan/budgetary',
			data : {
				loanMoney : $('#willLoanMoney').val(),
				terms: termsPara
			},
			dataType : 'json',
			success : function(data) {
				$('#serviceCharge').html(data.serviceCharge+"元");
				$('#monthFeeRate').html(data.monthFeeRate+"%");
				$('#monthRepay').html(data.monthRepay+"元");
				$('#sumRepay').html(data.sumRepay+"元");
			}
		});
		}
	}
	$(function(){
		$("#projectWillLoan").validate({
			rules: {willLoanMoney: {maxlength: 7, min: 100, max: <%=request.getAttribute("canLoanMoney")%>, required: true, amount: true}},
			messages: {willLoanMoney: {max: "您最多还可从平台借贷<%=request.getAttribute("canLoanMoney")%>元，请确认"}},
			errorClass: "error",
			errorLabelContainer: "#tipsContainer"
		});
	});
	function goAuthInfo(){
		window.location="${ctxFront}/customer/account/authInfo";
	}
	function apply(){
		if($("#projectWillLoan").valid()){
			var willLoanMoney=$('#willLoanMoney').val();
			var canLoanMoney=$('#canLoanMoney').val();
			$('#wantLoanMoney').val(willLoanMoney);
			$('#inputForm').submit();
		}
	}

	function selectTerm(value,label){
		$('#terms').val(value);
		$('#showTerms').html(label);
		doBudgetary();
	}
</script>
</head>
<body>
	<div class="f_wrapper div_width_980 borrow_page" id="wyrz_index">
		<div class="f_top">
			<div class="calc_box">
            	<div class="title">借款计算器</div>
				<div class="calc_line clearfix">
					<form id="projectWillLoan" class="clearfix fl">
						
						<div class="money">申请金额<input type="text" id="willLoanMoney" name="willLoanMoney" value="<%=request.getAttribute("canLoanMoney")%>"
onchange="doBudgetary()" onBlur="doBudgetary();" maxlength="7" />元<label id="tipsContainer"></label> <input type="hidden" id="canLoanMoney" value="<%=request.getAttribute("canLoanMoney")%>"></div>
						<div class="time">
							<div class="m-select">
								<div class="m-row clearfix">
									<label class="l m-rowLabel" for="">融资期限</label>
									<div class="l m-drop clearfix dropdown">
										<span class="m-dropInput" id="showTerms">24个月</span>
                                        <a class="m-dropIcon on dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" data-target="#" href="#"></a>
										<ul class="m-dropList dropdown-menu" id="m-dropList" role="menu">
											<!-- js实现：点击按钮.m-dropIcon 显示m-dropList；点击'.m-dropList li'改变 .m-dropHid 的value属性-->
											<%
												List<Dict> termList = (List<Dict>) request.getAttribute("termList");
												for (Dict dict : termList) {
													if (dict.getValue().equals(request.getAttribute("terms"))) {
											%>
											<li name="literm" id="li_<%=dict.getValue()%>"
												data="<%=dict.getValue()%>"
												onclick="selectTerm(<%=dict.getValue()%>,'<%=dict.getLabel()%>')"
												class="active"><%=dict.getLabel()%></li>
											<%
												} else {
											%>
											<li name="literm" id="li_<%=dict.getValue()%>"
												data="<%=dict.getValue()%>"
												onclick="selectTerm(<%=dict.getValue()%>,'<%=dict.getLabel()%>')"><%=dict.getLabel()%></li>
											<%
												}
												}
											%>

										</ul>
									</div>
								</div>
							</div>
						</div>
					</form>
					<div class="calculation"><input type="button" onClick="doBudgetary();" value="计算" /></div>
				</div>
				<div class="credit_line clearfix">
					<div class="credit">
						我的信用评分<b class="score"><%=request.getAttribute("creditScore")%></b><b
							class="fen">分</b>
					</div>
					<div class="lend">
						我申请中额度<b><%=request.getAttribute("applyingMoney")%></b>元，借贷中额度<b><%=request.getAttribute("loaningMoney")%></b>元，我还可以从平台借贷<b><%=request.getAttribute("canLoanMoney")%></b>元
					</div>
					<div class="promote" onClick="javascript:goAuthInfo()">
						马上提升贷款额度！</div>
				</div>
                <div class="title">计算结果</div>
				<div class="calc_result">
					<table>
						<tr>
							<th>服务费</th>
							<th>月综合费率</th>
							<th>月还款额</th>
							<th>总还款额</th>
						</tr>
						<tr>
							<td id="serviceCharge"><%=request.getAttribute("serviceCharge")%>元</td>
							<td id="monthFeeRate"><%=request.getAttribute("monthFeeRate")%>%</td>
							<td id="monthRepay"><%=request.getAttribute("monthRepay")%>元</td>
							<td id="sumRepay"><%=request.getAttribute("sumRepay")%>元</td>
						</tr>
					</table>
				</div>
				<div class="apply_line">
					<form method="post" action="${ctxFront}/willloan/willloan/apply"
						id="inputForm">
						<input type="hidden" id="terms" name="terms"
							value="<%=request.getAttribute("terms")%>" /> <input
							type="hidden" id="wantLoanMoney" name="wantLoanMoney"
							value="<%=request.getAttribute("canLoanMoney")%>" /> <a href="#"
							onclick="apply()">立即申请借款</a>
					</form>
				</div>
			</div>
		</div>
		<div class="f_bot">
			<div class="f_bot_left">
				<dl class="borrow_dl strength">
					<dt>借款优势</dt>
					<dd>
						<img src="${ctxStatic}/modules/front/images/wyrz/strength.jpg"
							alt="借款优势" />
					</dd>
				</dl>
				<dl class="borrow_dl condition">
					<dt>申请条件</dt>
					<dd class="">
						<div class="list">
							<span class="circle">1</span><span class="zi">借款人年龄在22周岁(含)至55周岁之间。</span>
						</div>
						<div class="list">
							<span class="circle">2</span><span class="zi">借款人需为上海户籍。</span>
						</div>
						<div class="list">
							<span class="circle">3</span><span class="zi">借款人名下拥有合法的物业或机动车辆，或者工作单位为国有企业、上市公司、世界500强，并在现单位连续工作满6个月及以上；工作单位为中小企业的须为相关企业法人，企业经营年限两年及以上，保持持续增长及稳定盈收。注：具体情况以网站后台审核为准。</span>
						</div>
					</dd>
				</dl>

				<dl class="borrow_dl flow">
					<dt>借款流程</dt>
					<dd>
						<img src="${ctxStatic}/modules/front/images/wyrz/flow.jpg"
							alt="借款流程" />
					</dd>
				</dl>
			</div>
			<div class="f_bot_right">
				<div class="f_bot_right_top">
					<dl class="borrow_dl questions">
						<dt>借款常见问题</dt>
						<dd class="">
							<ul class="list12">
								<li><a href="${ctxFront }/help/wyrz_jkfy" target="_blank">1.申请借款时，需要缴纳多少服务费？</a></li>
								<li><a href="${ctxFront }/help/wyrz_rhsq" target="_blank">2.申请借款需要提前准备哪些资料？</a></li>
								<li><a href="${ctxFront }/help/wyrz_xysh" target="_blank">3.借款审核一般需要多长时间？</a></li>
								<li><a href="${ctxFront }/help/wyrz_ckytx" target="_blank">4.审核通过后，多久能收到借款？</a></li>
								<li><a href="${ctxFront }/help/wyrz_rhhk" target="_blank">5.如何还款？</a></li>
								<li><a href="${ctxFront }/help/wyrz_rhhk" target="_blank">6.是否支持提前还款？</a></li>
							</ul>
						</dd>
					</dl>
				</div>
				<div class="f_bot_right_bot">
					<dl class="borrow_dl ours">
						<dt>了解花生金服</dt>
						<dd>为广大的个人投资者、中小 企业提供投融产品的互联网金融服务平台</dd>
					</dl>

					<dl class="borrow_dl news">
						<dt>相关报道</dt>
						<dd>
							<ul>
								<c:forEach items="${pageFdxw.list}" var="fdxw">
								<li>
									<a href="${fdxw.urlWithFdxw}">◎${fns:abbr(fdxw.title,96)}</a>
								</li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
				</div>
			</div>
		</div>
	</div>
</body>
</html>