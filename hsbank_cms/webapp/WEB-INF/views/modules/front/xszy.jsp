<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />
<link href="${ctxStatic}/modules/front/css/xszy.css?${version }" rel="stylesheet" />
<title></title>
<script type="text/javascript">

$(function() {
	
	//全局tab切换
    $('.tab_wrapper li').click(function() {
        $(this).siblings().removeClass('current');
        $(this).addClass('current');
        $('.xszy_wrapper>.content_wrapper>ul>li').hide();
        $(
            '.xszy_wrapper>.content_wrapper>ul>li:nth-child('
            + $(this).attr('order') + ')').fadeIn();
    });
    $('.tab_wrapper li:first-child').click();

    //算一算
    function showanimate(){
        $(".rrd").css("height","0px").hide();
        $(".huoqi").css("height","0px").hide();
        $(".baobao").css("height","0px").hide();

        $(".rrd").stop(true,true).animate({height:"144px"}).show();
        $(".huoqi").stop(true,true).animate({height:"18px"}).show();
        $(".baobao").stop(true,true).animate({height:"46px"}).show();

        $(".rrd label").css("left","-"+(parseInt($(".rrd label").css("width"))/2-15)+"px");
        $(".huoqi label").css("left","-"+(parseInt($(".huoqi label").css("width"))/2-15)+"px");
        $(".baobao label").css("left","-"+(parseInt($(".baobao label").css("width"))/2-15)+"px");

    }

    function validate( ){
        var value =$("#much").val();
        //$("#much").val(value.replace(/\D/g,''));
        if(value == ""){
            $(".placeholder").show();
            return true; 
        }
        if(!/^\d{1,10}$/.test(value) || Number(value)%50!=0||value=="0"){
            $(".error").show();
            $("#much").addClass("err");
            return false;
        }else{
            $(".error").hide();
            $("#much").removeClass("err");
            return true; 
        }

    };
    function fixMath(m, n, op) {
        var a = (m+"");
        var b = (n+"");
        var x = 1;
        var y = 1;    
        var c = 1;
        if(a.indexOf(".")>0) {
            x = Math.pow(10, a.length - a.indexOf(".") - 1);

        }
        if(b.indexOf(".")>0) {
            y = Math.pow(10, b.length - b.indexOf(".") - 1);
        }
        switch(op)
        {
            case '+':
            case '-':
                c = Math.max(x,y);
                m = Math.round(m*c);
                n = Math.round(n*c);
                break;
            case '*':
                c = x*y
                m = Math.round(m*x);
                n = Math.round(n*y);
                break;
            case '/':
                c = Math.max(x,y);
                m = Math.round(m*c);
                n = Math.round(n*c);
                c = 1;
                break;
        }
        return eval("("+m+op+n+")/"+c);
    };

    function toFixed(floatNumber){
        var num = parseFloat(parseFloat(Math.round(floatNumber * 100) / 100, 10).toFixed(2));
        return  num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }


    $(".how-choice span").on("click",function(e){
        var el = $(e.currentTarget);
        $(".how-choice .selected").removeClass("selected");
        el.addClass("selected");
    })
    $("#much").on("focus",function(){
        $(".placeholder").hide(); 
    }).on("blur",function(){
        validate();
    }).on("input propertychange",function(){
        setTimeout(function(){
            var value =$("#much").val();
            $("#much").val(value.replace(/\D/g,''));
            },1000)

    });
    $(".placeholder").on("click",function(){
        $(".placeholder").hide(); 
    });
    $("#count").on("click",function(){
        var amount = $("#much").val(), month=Number($("span.selected").attr("data")); 

        if(validate()){
            if(amount==""){
                amount = 10000;
            }else{
                amount = Number(amount);
            }
        }else{
            return;
        }

        var current,baby,paper;
        var currentRate =fixMath(fixMath(0.35,100,"*"),10000,"/"), babyRate =fixMath( 4,100,"/"),paperRate =fixMath( 10,100,"/");

        current = fixMath(fixMath(amount*month,currentRate,"*"),12,"/");
        baby = fixMath(fixMath(amount*month,babyRate,"*"),12,"/");
        paper =fixMath(fixMath(amount*month,paperRate,"*"),12,"/");
        $(".huoqi label").text("约"+toFixed(current)+"元");
        $(".baobao label").text("约"+toFixed(baby)+"元");
        $(".rrd label").text("约"+toFixed(paper)+"元"); 

        showanimate();
        $("#how").text(amount+"元");
        $("#time").text(month+"个月");       
    });
    $(function(){
        showanimate();
    })

    $("#much").focus(function(){
    	$(this).attr("placeholder", "");
    }).blur(function(){
    	$(this).attr("placeholder", "10000");
    });
});
</script>
</head>
<body>
	<div class="div_width_980 xszy_page" id="xszy">
		<div class="xszy_wrapper">
			<div class="tab_wrapper">
				<ul class="clearfix">
					<li order="1"><span>关于投资</span></li>
					<!-- <li order="2"><span>关于融资</span></li> -->
					<li order="3"><span>安全保障</span></li>
				</ul>
			</div>
			<div class="content_wrapper">
				<ul>
					<li class="">
						<div class="invest_wrapper">
							
							<dl class="why">
								<dt>为什么选择花生金服？</dt>
								<dd>
									<ul class="clearfix">
										<li><img src="${ctxStatic}/modules/front/images/xszy/icon_safe.png" alt="投资安全" />
											<div class="title">投资安全</div>
											<div class="">注册资金<b>1.8</b>亿（实资）</div>
											<div class="">全程第三方资金托管</div>
                                        </li>
										<li><img src="${ctxStatic}/modules/front/images/xszy/icon_advance.png" alt="品牌优势" />
											<div class="title">品牌优势</div>
											<div class="">金融行业<b>AAA</b>级信用单位</div>
											<div class="">多家权威机构认证</div>
                                        </li>
										<li><img src="${ctxStatic}/modules/front/images/xszy/icon_gain.png" alt="高收益" />
											<div class="title">高收益</div>
											<!-- <div class="">年化收益<b>7%-15%</b></div>
											<div class="">远远高于银行及宝宝类产品</div> -->
											<div class="">年化收益<b>8%-16%</b></div>
                                        </li>
									</ul>
								</dd>
							</dl>
							<dl class="calc">
								<dt>算算我能赚多少钱？</dt>
								<dd>
									<div class="reg-wrap">
										<ul class="how-much fn-clear">
											<li class="how-choice">
												<div>
													<label>投资多少</label> <input id="much" placeholder="10000">
													<p class="error" style="display: none">输入金额只能是小于10位的数字，且须为50的倍数</p>
													<label class="unit">元</label>
												</div>
												<div>
													<label>投资多久</label> <span data="3">3个月</span><span data="6">6个月</span><span
														data="12" class="">12个月</span><span data="24" class="">24个月</span><span
														data="36" class="selected">36个月</span>
												</div>
												<div style="text-align: center">
													<a class="loginnow" id="count">算一算</a>
												</div>
											</li>
											<li class="how-result"><label class="ti">预期收益</label>
												<div class="line">
													<ul>
														<li></li>
														<li></li>
														<li class="last"></li>
													</ul>
													<div class="mm">
														<span style="margin: 0px 35px 0px 50px">活期存款</span><span
															style="margin: 0px 35px 0px 6px">宝宝类</span><span
															style="margin: 0px 0px 0px 15px">花生金服</span>
													</div>
													<p>
														注：投资<span id="how">10000元</span>，<span id="time">36个月</span>预期收益
													</p>

												</div>
												<div class="column">
													<ul>
														<li class="huoqi"
															style="height: 18px; display: list-item;"><label
															style="left: -11.5px;">约105元</label></li>
														<li class="baobao"
															style="height: 46px; display: list-item;"><label
															style="left: -17px;">约1,200元</label></li>
														<li class="rrd" style="height: 144px; display: list-item;"><label
															style="left: -17px;">约3,000元</label></li>

													</ul>
												</div></li>
										</ul>
									</div>
								</dd>
							</dl>
							<dl class="flow">
								<dt>投资流程</dt>
								<dd>
									<img src="${ctxStatic}/modules/front/images/xszy/icon_flow.png"
										alt="算算我能赚多少钱？" />
								</dd>
							</dl>
						</div>
					</li>
					<li class="">
						<div class="borrow_wrapper">
							<ul>
								<li class="what">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<div class="clearfix">
												<img class="pull-left" src="${ctxStatic}/modules/front/images/xszy/borrow_what.png"  alt=""/>
												<ul class="pull-left">
													<li>
														<div class="content"><b>花生金服</b>是由上海蓄积金融信息服务有限公司运营的互联网金融平台，是目前中国互联网金融P2P信贷行业的领军企业。平台提供便捷的个人信用贷款服务，借款用户可以在花生金服平台上发布借款请求来实现个人快捷的融资需要。融资过程无需任何抵押、担保。</div>
													</li>
												</ul>
											</div>
										</div>
										<div class="footer"></div>
									</div>
								</li>
								<li class="why">
									<div class="">
										<div class="header"></div>
										<div class="inner clearfix">
											<div class="fl">
												<ul class="pull-left">
													<li>
														<div class="title">无需任何抵押、担保，低门槛，纯信用贷款</div>
														<div class="content">花生金服为借款人提供纯信用借款，无须任何抵押或担保，只需要提供必要申请材料并通过审核，即可获得最高50万的借款额度。</div>
													</li>
													<li>
														<div class="title">全程在线操作，最便捷的借款方式</div>
														<div class="content">无需出门，提交材料、审核、放款全程互联网操作，键盘加鼠标轻松搞定，7*24的互联网金融服务。</div>
													</li>
													<li>
														<div class="title">最安全的平台保障</div>
														<div class="content">采用业界最先进的加密技术，对您的注册信息、账户信息进行加密处理，花生金服绝不会以任何形式将这些信息透露给第三方。</div>
													</li>
												</ul>
											</div>
											<div class="fr">
                                            	<div class="div_height_100"></div>
												<div><img class="pull-left" src="${ctxStatic}/modules/front/images/xszy/borrow_why_03.png"  alt=""/></div>
											</div>
										</div>
										<div class="footer"></div>
									</div>
								</li>
								<li class="loan">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<div class="clearfix">
												<ul class="pull-right" style="width:auto">
													<li>
                                                    	<div class="div_height_30"></div>
														<div class="title">轻松借款，只需四步</div>
														<div class="content four_step">
															<ul style="width:auto">
                                                            	<li>第一步 注册成为花生金服用户，开通托管账户，完成实名认证。</li>
                                                                <li>第二步 完善个人资料，上传必要证件，进行信用评级。</li>
                                                                <li>第三步 提交借款申请，等待平台审核。</li>
                                                                <li>第四步 审核后发布筹款项目，满标后即可放款。</li>
                                                                <li>平台放款后，您只需每月按时还款即可。</li>
                                                            </ul>
														</div>
													</li>
												</ul>
												<img class="pull-left" src="${ctxStatic}/modules/front/images/xszy/borrow_loan.png"  alt=""/>
											</div>
										</div>
										<div class="footer"></div>
									</div>
								</li>
							</ul>
						</div>
					</li> 
					<li class="">
						<div class="safe_wrapper">
							<ul>
								<li class="capital">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<ul>
												<li>
													<div class="title">什么是第三方资金托管？</div>
													<div class="content">第三方托管是指资金流运行在第三方托管公司，而不经过平台的银行账户。从而避免平台因为经营不善导致挪用交易资金而给交易双方带来风险。花生金服牵手易宝支付为广大投资用户提供安全的互联网投资渠道。</div>
												</li>
												<li>
													<div class="title">第三方资金托管如何运作？</div>
													<div class="content">投资人和借款人分别在第三方支付开通自己的账户，整个过程投资人都能看到自己资金的准确去向;平台也在第三方支付开通了商户号，但只能做资金解冻和退款两种操作，而不能执行转账与提现操作。</div>
                                                    <div class="div_height_10"></div>
                                                    <div class="content">满标后，资金即会从投资人的账户进入借款人的账户;流标后投标资金会直接退回到投资人的账户。</div>
												</li>
												<li>
													<div class="title">花生金服的第三方托管服务商是谁？</div>
													<!-- <div class="content">易宝支付（YeePay.com）是中国行业支付的开创者和领导者，也是互联网金融和移动互联领军企业。易宝于2003年8月成立，总部位于北京，现有员工逾千人，在北京、上海、天津、广东、四川、浙江、山东、江苏、福建等20余个省市设有分公司。2013年，公司成立十周年之际，易宝发布了“支付+金融+营销”的升级战略，以领跑电子支付，互联网金融和移动互联大潮。</div> -->
												<div class="content">易宝支付旗下懒猫金服，全称北京懒猫金融信息服务有限公司（www.lanmao.com），2014年12月由北京市金融局批准设立，是易宝集团专注互联网金融行业的理财平台。</div>
												
												</li>
											</ul>
										</div>
                                        <div class="footer"></div>
									</div>
								</li>
								<li class="account">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<ul>
												<li>
													<div class="title">网站技术保障</div>
													<div class="content">花生金服运用先进的安全技术保护用户账户中存储的个人信息、账户信息以及交易记录的安全。花生金服拥有完善的安全监测系统，可以及时发现网站的非正常访问并做相应的安全响应。</div>
												</li>
												<li>
													<div class="title">权限管理保障</div>
													<div class="content">花生金服严格遵守国家相关的法律法规，对用户的隐私信息进行保护。未经您的同意，不会向任何第三方公司、组织和个人披露您的个人信息、账户信息以及交易信息（法律法规另有规定的除外）。</div>
												</li>
												<li>
													<div class="title">第三方资金交易</div>
													<div class="content">花生金服牵手易宝支付为广大投资用户提供安全的互联网投资渠道。</div>
												</li>
											</ul>
										</div>
                                        <div class="footer"></div>
									</div>
								</li>
								<li class="leagle">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<ul>
												<li>
													<div class="title">花生金服平台提供居间撮合服务的合法性</div>
												<div class="content">《合同法》第23章专门对“居间合同”作出规定，其第424条明确定义为：“居间合同是居间人向委托人报告订立合同的机会或者提供订立合同的媒介服务，委托人支付报酬的合同”。花生金服平台是合法设立的中介服务机构，致力于为民间借贷业务提供优质高效的撮合服务，以促成借贷双方形成借贷关系，然后收取相关报酬。此种居间服务有着明确的法律依据。</div>
												</li>
												<li>
													<div class="title">理财人及借款人之间的借贷关系的合法性</div>
													<div class="content">《合同法》第196条规定：“借款合同是借款人向贷款人借款，到期返还借款并支付利息的合同”；根据《合同法》第十二章“借款合同”和《最高人民法院关于审理民间借贷案件适用法律若干问题的规定》，我国法律允许自然人等普通民事主体之间发生借贷关系，并允许出借方到期可以收回本金和符合法律规定的利息。法人之间、其他组织之间以及它们相互之间为生产、经营需要订立的民间借贷合同，除存在合同法第五十二条、本规定第十四条规定的情形外，当事人主张民间借贷合同有效的，人民法院应予支持。理财人作为借款人，与贷款人之间形成的借贷关系受到法律保护。</div>
												</li>
												<li>
													<div class="title">理财人通过平台获得的出借理财收益的合法性</div>
													<div class="content">根据 《最高人民法院关于审理民间借贷案件适用法律若干问题的规定》，第二十六条，借贷双方约定的利率未超过年利率24%，出借人请求借款人按照约定的利率支付利息的，人民法院应予支持。借贷双方约定的利率超过年利率36%，超过部分的利息约定无效。借款人请求出借人返还已支付的超过年利率36%部分的利息的，人民法院应予支持。</div>
												</li>
												<li>
													<div class="title">电子合同的合法性</div>
													<div class="content">根据《合同法》第11条的规定，当事人可以采用合同书、信件和数据电文（包括电报、电传、传真、电子数据交换和电子邮件）等形式订立合同。电子合同是法律认可的书面合同形式之一。花生金服采取用户网上点击确认的方式签署电子合同。点击确认后的电子合同符合《中华人民共和国合同法》规定的合同成立、生效的要件，其有效性也被人民法院的司法实践所接受。</div>
												</li>
											</ul>
										</div>
                                        <div class="footer"></div>
									</div>
								</li>
								<li class="lend">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<ul>
												<li>
													<div class="title">严格的贷前审核</div>
													<div class="content">在客户提出借款申请后，花生金服会对客户的基本资料进行分析。通过网络、电话及其他可以掌握的有效渠道进行详实、仔细的调查。避免不良客户的欺诈风险。在资料信息核实完成后，根据个人信用风险分析系统进行评估，由经验丰富的借款审核人员进行双重审核确认后最终决定批核结果。</div>
												</li>
												<li>
													<div class="title">完善的贷后管理</div>
													<div class="content">如果用户逾期未归还借款，贷后管理部门将第一时间通过短信、电话等方式提醒用户进行还款，花生金服将会联系该用户的紧急联系人、直系亲属、单位等督促用户尽快还款。如果用户仍未还款，交由专业的高级催收团队与第三方专业机构合作进行包括上门等一系列的催收工作，直至采取法律手段。</div>
												</li>
											</ul>
										</div>
										<div class="footer"></div>
									</div>
								</li>
								<li class="self">
									<div class="">
										<div class="header"></div>
										<div class="inner">
											<p>账户安全需要网站和用户的共同努力。在此，我们提倡各位用户在网站使用过程中，注意以下几点：</p>
											<ul>
												<li>
													<div class="title">牢记花生金服官方网址：www.hsbank360.com</div>
													<div class="content">不要点击来历不明的链接访问花生金服，谨防网站钓鱼和欺诈。我们建议您将花生金服官方网址加入浏览器收藏夹，以方便您的下次登录。</div>
												</li>
												<li>
													<div class="title">为您的花生金服账户设置高强度的登录密码</div>
													<div class="content">您在密码设置时，最好使用数字和字母混合，不要使用纯数字或纯字母，且密码长度要在6位以上。</div>
												</li>
												<li>
													<div class="title">注重电脑运行环境的安全</div>
													<div class="content">及时为您的电脑进行系统更新、安装安全补丁，以防系统漏洞被黑客利用。为您的电脑安装杀毒软件或防火墙，并定期为电脑进行查毒、杀毒。</div>
												</li>
												<li>
													<div class="title">时刻注意保护个人隐私</div>
													<div class="content">用户在平台上交流的过程中，不要向其他用户透露自己真实姓名与住址等，以防个人信息被盗取造成损失。</div>
												</li>
												<li>
													<div class="title">避免私下交易</div>
													<div class="content">花生金服建议用户避免私下交易。私下交易的约束力极低，造成逾期的风险非常高，同时您的个人信息将有可能被泄漏，存在遭遇诈骗甚至受到严重犯罪侵害的隐患。</div>
												</li>
											</ul>
										</div>
										<div class="footer"></div>
									</div>
								</li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>