<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="front_with_juanzhou" />
<link href="${ctxStatic}/modules/front/css/xszy.css?${version }" rel="stylesheet" />
<title></title>
<script type="text/javascript">
var flag = "${flag }";
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
    	if(flag == "1") {
    		$("#tab2").click();
    	}
    })
});
</script>
</head>
<body>
	<div class="div_width_980 xszy_page" id="xszy">
		<div class="xszy_wrapper">
			<div class="tab_wrapper">
				<ul class="clearfix">
					<li order="1"><span>新手任务</span></li>
					<li id="tab2" order="2"><span>新手项目</span></li>
				</ul>
			</div>
			<div class="content_wrapper">
				<ul>
					<li class="">
						<div class="novice_task">
                        	<div class="container xszy_img_01"></div>
                        	<div class="container xszy_img_02"><a href="${ctxFront }/customer/summary">立即开通平台账户</a></div>
                        	<div class="container xszy_img_03"></div>
                        	<div class="container xszy_img_04"><a href="${ctxFront }/xszq?flag=1">立即投资</a></div>
                        	<div class="container nt_description">
                            	<dl>
                                	<dt>规则说明：</dt>
                                    <dd>1.活动时间：本活动为长期活动，开始时间为2015年9月1日，具体结束时间以活动公告为准；</dd>
                                    <dd>2.任务完成后，奖励会自动发放至”我的账户”“我的现金券”中；</dd>
                                    <dd>3.对于恶意刷奖的行为，平台有权取消用户领取、激活、兑换奖励，并保留相关法律权利；</dd>
                                    <dd>4.严禁利用本活动进行匿名转账、信用卡套现等行为，一经发现将扣除手续费并清退相关资金；</dd>
                                    <dd>5.本活动遵循国家各项法律规定，规则解释权归花生金服所有。</dd>
                                </dl>
                            </div>
                            
                        </div>
					</li>
					<li class="">
                    	<div id="project_list" class="xsxm_list">
			       			<c:if test="${empty isNewUserProjectList }">
			       				<div class="nocontent" style="display:block">
			                    	<div class="nocontent_box"></div>
			                        <div class="nocontent_text red-text">目前没有满足条件的项目</div>
			                    </div>
			       			</c:if>
                           <c:forEach items="${isNewUserProjectList}" var="project">
                            <div class="item">
                            	<c:if test="${project.isNewUser eq '0' }"><div class="icon_newhand"></div></c:if>
                                <div class="top">
                                    <c:if test="${project.projectTypeCode eq '1' }">
										<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_di.png" alt="">
									</c:if>
									<c:if test="${project.projectTypeCode eq '2' }">
										<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_ge.png" alt="">
									</c:if>
									<c:if test="${project.projectTypeCode eq '3' }">
										<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_shang.png" alt="">
									</c:if>
									<c:if test="${project.projectTypeCode eq '4' }">
										<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zhi.png" alt="">
									</c:if>
									<c:if test="${project.projectTypeCode eq '5' }">
										<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zu.png" alt="">
									</c:if>
									<c:if test="${project.projectTypeCode eq '6' }">
										<img class="logo" src="${ctxStatic}/modules/front/images/util/icon_category_zi.png" alt="">
									</c:if>
                                    <span>花生金服</span>
                                    <img class="img" src="${ctxStatic}/modules/front/images/util/line_split.png" alt="">
                                    <span>${fns:getDictLabel(project.repaymentMode, 'project_repayment_mode_dict', '')}</span>
                                </div>
                                <div class="line"></div>
                                <div class="item-title">${p2p:abbrev(project.projectName,100)}</div>
                                <div class="line"></div>
                                <div class="item-line-1">
                                    <span class="span-1">
	                                    <c:choose>
											<c:when test="${project.isIncreaseInterest eq '1' && project.increaseInterestRate > 0 }">
										<fmt:formatNumber value="${(project.annualizedRate - project.increaseInterestRate) * 100 }" pattern="#.#" />+<fmt:formatNumber value="${project.increaseInterestRate * 100 }" pattern="#.#" />
											</c:when>
											<c:otherwise>
										<fmt:formatNumber value="${project.annualizedRate * 100 }" pattern="#.#" />
											</c:otherwise>
										</c:choose>
									</span>
                                    <span class="span-2">%</span>
                                    <span class="span-3">预期年化收益率</span>
                                </div>
                                <div class="clearfix"></div>
                                <div class="item-line-2">
                                    <span class="fl">起投金额</span>
                                    <span class="fr">期限</span>
                                </div>
                                <div class="item-line-3">
                                	<div class="fl">
                                    	<span class="span-1"><fmt:formatNumber  value="${project.startingAmount }" pattern="#0.00" /></span>
                                    	<span class="span-2">元</span>
                                    </div>
                                    <div class="fr">
                                    	<span class="span-3">${project.projectDuration }</span>
                                    	<span class="span-4">月</span>
                                    </div>
                                </div>
                                <div class="line"></div>
                                <div class="item-line-4">
                                    <c:choose> 
										<c:when test="${project.projectStatus eq '3'}"> 
									<a href="${ctxFront }/project_detail?id=${project.projectId }">立即投资</a>
										</c:when> 
										<c:otherwise> 
									<a href="${ctxFront }/project_detail?id=${project.projectId }" class="bt_gray_210x43">查看详情</a>
										</c:otherwise>
									</c:choose>
                                </div>
                                <c:if test="${project.projectStatus eq '3'}"> 
                                <progress class="item-line-5" value="${project.pes.endFinanceMoney }" max="${project.pes.financeMoney }"></progress>
                                <div class="item-line-6"><fmt:formatNumber  value="${project.pes.endFinanceMoney }" pattern="#0.00" /> / <fmt:formatNumber  value="${project.pes.financeMoney }" pattern="#0.00" /></div>
                                </c:if>
                                <div class="item-line-7">
	                                <c:choose> 
										<c:when test="${fn:length(project.projectIntroduce) > 30}"> 
									<c:out value="${fn:substring(project.projectIntroduce, 0, 30)}......" /> 
										</c:when> 
										<c:otherwise> 
									<c:out value="${project.projectIntroduce}" /> 
										</c:otherwise>
									</c:choose>
                                </div>
                            </div>
                           </c:forEach>
                        </div>
                    </li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>