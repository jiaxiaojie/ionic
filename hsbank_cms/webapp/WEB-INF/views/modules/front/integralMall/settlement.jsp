<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_menu"/>
		<link href="${ctxStatic}/modules/front/css/integralMall.css?${version }" type="text/css" rel="stylesheet"/>
		
		<script type="text/javascript">
			var isSuccess = ${isSuccess};
			var message = "${message}";
			$(function() {
				if(isSuccess) {
					$("#success").show();
					$("#unSuccess").hide();
				} else {
					$("#success").hide();
					$("#unSuccess").show();
					$("#lookOrderDetail").hide();
					$("#orderNo").hide();
				}
				$("#message").html(message);
			});
		</script>
		<title></title>
	</head>

<body>

<div class="page">
    <div class="div_width_980 nav_row">
    	<div class="left"><a href="${ctxFront }/integralMall/index">花生乐园</a>&nbsp;&gt;&nbsp;<a href="#">结算页</a></div>
        <div class="right"></div>
    </div>
    
    <div class="div_width_980 clearfix">
    	<div class="fl w720">
        	<div class="allGoods settlement">
            	<div class="content">
                	<div class="icon_check">
                    	<!--订单提交成功显示下面的图标-->
                    	<i id="success" class="icon_check_blue" style="display:block;"></i>
                    	<!--订单提交未成功显示下面的图标-->
                    	<i id="unSuccess" class="icon_check_red" style="display:none;"></i>
                    </div>
                    <p class="tips_text" id="message"></p>
                    <p id="orderNo" class="tips_text">订单号：<span>${orderNo }</span></p>
                	<div class="control_btns"><a id="lookOrderDetail" href="${ctxFront }/integralMall/orderDetails?orderNo=${orderNo}">查看订单状态</a><a href="${ctxFront }/integralMall/index">继续购物</a></div>
                </div>
            </div>
        </div>
        
    	<div class="fr w240">
            <div class="record">
            	<div class="tdc_img"><img src="${ctxStatic}/modules/front/images/integralMall/tdc_img.jpg"></div>                
            </div>            
        	<div class="record">
            	<div class="ag_title" style="font-size:14px; color:#333; font-weight:normal;">参与记录</div>
                <ul class="record_list">
                	<c:forEach var="order" items="${buyOrderList }">
                	<li class="clearfix">
                    	<div class="text">
                    	<span class="username">${order.customerAccountShow }</span>兑换了
                    	<a href="${ctxFront }/integralMall/commodityDetails?productId=${order.productId}" class="title">${order.product.productName }</a>${order.productCount }个</div>
                        <a href="${ctxFront }/integralMall/commodityDetails?productId=${order.productId}" class="img"><img src=${order.product.productLogoNormal}/></a>
                    </li>
                   	</c:forEach>
                </ul>
            </div>       
        </div>        
    </div>
    
</div>

</body>
</html>