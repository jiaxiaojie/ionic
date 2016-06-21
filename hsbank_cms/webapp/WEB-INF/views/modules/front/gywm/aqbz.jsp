<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="about_us"/>
		<link href="${ctxStatic}/modules/front/css/gywm/aqbz.css?${version }" rel="stylesheet"/>
		<title></title>
        <script type="text/javascript">
			function showPop() {
				document.getElementById("popup").style.display = "block";
			}
			function closePop() {
				document.getElementById("popup").style.display = "none";
			}
		</script>

	</head>
	<body>
		<div id="content_center" class="about_right_content">
			<div class="title"><span class="font_color_brown">安全保障</span></div>
			<div class="item clearfix">
            	<div class="aqbz_img fl"><img src="${ctxStatic}/modules/front/images/gywm/aqbz_img01.jpg"/></div>
                <div class="aqbz_text fr">
                	<div class="aqbz_title">资金托管</div>
                	<p>花生金服平台将投资人资金存放于指定的第三方支付机构，并以每位投资人名义单独立户管理，由第三方进行账户监管。花生金服平台不涉及投资人资金，采取第三方托管以保障客户资金安全为目的的资金存管模式。</p>
                </div>
            </div>
			<div class="item clearfix">
            	<div class="aqbz_img fl"><img src="${ctxStatic}/modules/front/images/gywm/aqbz_img03.jpg"/></div>
                <div class="aqbz_text fr">
                	<div class="aqbz_title">风险保证金</div>
                	<p>花生金服平台首期设立1500万风险保证金用于保障投资人利益，在投资人资金无法顺利回款的情况下，在该项目到期日之后的3个工作日内启动平台风险保证金收购该项目债权。该计划适用于花生金服所有产品、标的。</p>
                </div>
            </div>
			<div class="item clearfix">
                <div class="aqbz_text fl">
                	<div class="aqbz_title">法律保障</div>
                	<!-- <p>花生金服平台所有产品、标的均由国内知名律师事务所提供全方位法律援助服务，确保平台所有产品合规、合法，保障投资人权益。</p> -->
                	<p>花生金服平台所有产品、标的均由国内知名律师事务所提供全方位法律援助服务，确保平台所有产品合规、合法，保障投资人权益。</p>
                </div>
            	<div class="aqbz_img fr"><img src="${ctxStatic}/modules/front/images/gywm/aqbz_img04.jpg"/></div>
            </div> 
		</div>
		
		<div class="pop_bg" style="display: none" id="popup">
            <div class="pop_main" style="width: 690px; height: 501px; margin-left: -345px; margin-top: -250px;">
                <div class="pop_title">重要告知<a href="javascript:;" class="close_pop" onClick="closePop()"></a>
                </div>
                <div class="pop_content">
                    <div class="important_content">
                        <p>（1）保障范围</p>
                        <p>交易资金损失险保障花生金服管理的或在其交易平台进行交易的的个人客户账户中的资金在交易过程（充值、提现、申购、赎回。按照业务涉及到的流程，选择上述4项中的一项或多项）中的资金被他人（除花生金服以外的第三方）盗转、盗用等保险事故造成的个人账户资金损失，由此造成的花生金服的直接经济损失。对此直接经济损失，保险公司将按照保险合同约定，在保险单列明的赔偿限额内，向花生金服及时赔付。</p>
                        <p>（2）责任免除</p>
                        <p>以下原因造成的资金损失，不在交易资金损失险的赔偿范围：</p>
                        <p>· 借款人无还款能力；</p>
                        <p>· 投资项目逾期还款；</p>
                        <p>· 花生金服倒闭、破产或挪用投资资金；</p>
                        <p>· 交易资金损失险条款（点击下载条款原文，请仔细阅读）责任免除中约定的其他事项：</p>
                        <p>第五条  由于下列原因造成的损失，保险人不负责赔偿：</p>
                        <p>（一）投保人、被保险人及其代表的故意或违法犯罪行为；</p>
                        <p>（二）被保险人未遵循国家相关监管机关以及银行、支付机构等账户发行机构关于个人账户的管理、交易规则。</p>
                        <p>（三）被保险人管理的个人账户的持有人及其家庭成员的故意或违法犯罪行为；</p>
                        <p>（四）被保险人管理的个人账户的持有人出租、转借其个人账户，或被他人诈骗；</p>
                        <p>（五）被保险人管理的个人账户的持有人在没有被胁迫的情况下，向他人透露个人账户号及密码；</p>
                        <p>（六）被保险人管理的个人账户超出本保险合同约定的保险责任起讫时间范围的损失；</p>
                        <p>（七）被保险人管理的个人账户的持有人未遵循国家相关监管机关以及银行、支付机构等账户发行机构关于个人账户的使用规则。</p>
                        <p>第六条  下列损失、费用或责任，保险人也不负责赔偿：</p>
                        <p>（一）利息以及透支利息、手续费、滞纳金、超限费*、罚息、罚金、账户年费、会员费、补发新卡费等以及任何形式的间接损失或费用；</p>
                        <p>（二）因制卡、读卡、验卡设备原因造成的个人账户内的资金损失；</p>
                        <p>（三）诉讼费用，个人账户挂失、冻结手续费，重新补办手续费；</p>
                        <p>（四）任何形式的个人账户附加功能的损失；</p>
                        <p>（五）被保险人管理的个人账户不能证明是个人本人名下的有效个人账户（包括但不限于第三方支付账户）中的资金损失；</p>
                        <p>（六）已经由第三方承担的任何损失或费用，包括但不限于已经由发卡银行、支付机构、受理行等账户发行机构承担的任何损失或费用； </p>
                        <p>（七）投保人在投保之前已经知道或者可以合理预见的损失或费用；</p>
                        <p>（八）本保险合同约定的免赔额，或按本保险合同约定的免赔率计算的免赔额。 </p>
                        <p>第七条  其他不属于本保险合同责任范围内的损失和费用，保险人不负责赔偿。</p>
                        <p>（3）交易资金损失险的赔款将及时赔付给被保险人花生金服。</p>
                    </div>
                </div>
            </div>
		</div> 
	</body>
</html>