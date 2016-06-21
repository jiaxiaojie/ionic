<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet">
	<link href="${ctxStatic }/modules/front/css/util/util.css?${version }" rel="stylesheet">

	<title>花生金服 hsbank360.com-更优质、更安全、更便捷、更有诚意的互联网金融P2P平台</title>
</head>

<body>

<div class="div_bg_001">
	<div class="agreement_area">
    	<div class="agreement">
            <div class="title"><span>债权转让协议</span><p>合同编号：<b></b></p></div>
            
            <div class="loan_field transfer_field">
            	<dl class="clearfix">
                	<dt>转让方（以下简称甲方）：</dt>
                    <dd>${p2p:vagueName(customerAccount.customerBase.customerName) }</dd>
                </dl>
            	<dl class="clearfix">
                	<dt>身份证号码：</dt>
                    <dd>${customerAccount.customerBase.certNum }</dd>
                </dl>
            	<dl class="clearfix">
                	<dt>通讯地址：</dt>
                    <dd>${p2p:vagueAddress(customerAccount.customerBase.address) }</dd>
                </dl>
            	<dl class="clearfix">
                	<dt>花生金服用户名：</dt>
                    <dd>${p2p:vagueName(customerAccount.accountName) }</dd>
                </dl>
            </div>       
            <dl class="item">
                <dd>根据《中华人民共和国民法通则》、《中华人民共和国合同法》等相关法律、法规以及规章的规定，甲、乙遵循自愿、公平、诚实信用的原则，经友好协商，甲方向乙方转让其对实际债务人拥有的合法债权。甲乙双方均为上海富定金融信息服务股份有限公司（简称花生金服，线上平台：<a href="http://www.hsbank360.com/" class="btn_text_gold">www.hsbank360.com</a>）的注册用户，现双方就相关债权转让事宜达成一致意见，签订本协议（以下简称“本协议”）。</dd>
            </dl>
            <dl class="item">
                <dt>第一条、债权转让</dt>
                <dd>1、截至于本协议书签订之日，甲方同意按本协议约定向乙方转让合法债权（详见于<span class="underline">${theYear }</span>年<span class="underline">${theMonth }</span>月<span class="underline">${theDay }</span>日签订的《借款协议书》，合同编号：               ），乙方同意按本协议约定受让债权。</dd>
                <dd>2、具体转让标的债权如下：</dd>
                <dd>
                	<table class="table_loan" width="960" cellpadding="0" cellspacing="0" border="1">
                    	<tbody>
                        	<tr>
                        		<td width="34%" height="40" align="center">债权人（花生金服用户名）</td>
                                <td width="66%">${p2p:vagueName(customerAccount.customerBase.customerName) }</td>
                            </tr>
                        	<tr>
                        		<td height="40" align="center">还款日</td>
                                <td>${theDate }</td>
                            </tr>
                        	<tr>
                        		<td height="40" align="center">剩余还款分期月数</td>
                                <td>${executeSnapshot.remainingTime }个月</td>
                            </tr>
                        </tbody>
                    </table>
                </dd>
                <dd>3、经各方一致同意，本协议项下的债权转让之对价为人民币<span class="underline">${amount }</span>元，在债权转让完成后，由实际债务人直接向乙方履行合同义务。</dd>
                <dd>4、甲方不可撤销地授权花生金服自行或委托第三方支付机构或合作的金融机构（若有），将转让价款在扣除甲方应支付给花生金服的转让管理费人民币<span class="underline">${serviceCharge }</span>元之后划转、支付给乙方。</dd>
            </dl>
            <dl class="item">
                <dt>第二条、甲方保证承诺</dt>
                <dd>1、本协议生效前，转让标的从未转让给任何第三方，并对转让标的拥有合法、有效的处分权。</dd>
                <dd>2、本协议生效前，向乙方移交与转让标的有关的各项证明文件及资料的原件和复印件（包括但不限于借款协议、担保协议（如有）、担保物的他项权利证书等），且对真实性、完整性、合法性负责，并承担因隐瞒、虚报所引起的一切法律责任。</dd>
                <dd>3、本协议生效后的3个工作日内，应积极、认真地配合乙方办理与转让标的相关的手续，迟延履行或拒不履行的，承担由此受到的实际损失。</dd>
            </dl>
            <dl class="item">
                <dt>第三条、乙方保证承诺</dt>
                <dd>1、乙方承诺其所受让标的债权的资金来源合法，乙方对该资金拥有处方权。</dd>
                <dd>2、本协议生效后，积极配合甲方办理与该转让债权有关的手续，并承担一切手续费用。</dd>
            </dl>
            <dl class="item">
                <dt>第四条、违约责任</dt>
                <dd>甲、乙双方应认真履行本协议项下约定义务，不履行或迟延履行，给对方造成损失的，应承担相应的赔偿责任，并一次性支付所转让债权总额20%的违约金。</dd>
            </dl>
            <dl class="item">
                <dt>第五条、争议解决</dt>
                <dd>有关本协议的解释或履行，当事人之间发生争议的，应由双方协商解决；协商解决不成的，任何一方均可向甲方所在地有管辖权的人民法院起诉。</dd>
            </dl>
            <dl class="item">
                <dt>第六条、其他事项</dt>
                <dd>1、甲乙双方认可本协议以电子文本形式签署确认，对于双方均具有法律约束力。</dd>
                <dd>2、双方一致同意并确认，通过自行或授权有关方根据花生金服网站有关规则和说明，在花生金服网站进行债权转让和受让购买操作等方式确认签署本协议。</dd>
                <dd>3、双方接受本协议且花生金服审核通过时，本协议即告成立，并待转让价款支付完成时生效。协议成立的同时甲方不可撤销地授权花生金服自行或委托第三方支付机构或合作的金融机构（若有），将转让价款在扣除甲方应支付给花生金服的转让管理费之后划转、支付给乙方，上述转让价款划转完成即视为本协议生效且标的债权转让成功；同时甲方不可撤销地授权花生金服将其代为保管的甲方与标的债权借款人签署的电子文本形式的 《借款协议书》(下称“借款协议”)及借款人相关信息在花生金服网站有关系统板块向乙方进行展示。</dd>
                <dd>4、本协议生效且标的债权转让成功后，双方特此委托花生金服将标的债权的转让事项及有关信息通过站内短信等形式通知与标的债权对应的债务人。</dd>
                <dd>5、自标的债权转让成功之日起，乙方成为标的债权新的债权人，承继《借款协议书》项下出借人的权利并承担出借人的义务。</dd>
                <dd>6、本协议生效后，甲、乙双方对本协议内容的变更或补充应采用书面形式订立，并作为本协议的附件，附件与本协议具有同等法律效力。</dd>
            </dl>
            
            <div class="loan_field transfer_field">
            	<dl class="clearfix">
                	<dt>甲方：</dt>
                    <dd>${p2p:vagueName(customerAccount.accountName) }</dd>
                </dl>
            	<dl class="clearfix">
                	<dt>日期：</dt>
                    <dd>${theDate }</dd>
                </dl>
            </div>
            
      </div>
    </div>
</div>



</body>
</html>
