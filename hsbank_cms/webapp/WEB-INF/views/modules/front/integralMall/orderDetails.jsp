<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_menu"/>
		<link href="${ctxStatic}/modules/front/css/integralMall.css?${version }" type="text/css" rel="stylesheet"/>

		<title></title>
	</head>
<body>

<div class="page">
    <div class="div_width_980 nav_row">
    	<div class="left"><a href="${ctxFront }/integralMall/index">花生乐园</a>&nbsp;&gt;&nbsp;<a href="#">订单详情</a></div>
        <div class="right"></div>
    </div>
    
    <div class="div_width_980 clearfix">
    	<div class="fl w720">
        	<div class="allGoods">
            	<div class="section">
                    <div class="step_tit"><span class="order_number">订单编号：<span>${order.orderNo }</span></span><span class="fr">订单状态：<span>${fns:getDictLabel(order.orderStatus, 'integral_mall_order_status_dict', '')}</span></span></div>
                    <div class="step_con" style="padding-bottom:0;">
                    	<table class="table pro_list orderDetails">
                        	<thead>
                            	<tr>
                                	<th class="" width="73">商品图片</th>
                                	<th class="pro_name" width="150">商品名称</th>
                                	<th class="" width="136">购买数量</th>
                                	<th class="pro_date" width="118">下单时间</th>
                                	<th class="" width="92">花生豆总价</th>
                                </tr>
                            </thead>
                        	<tbody>
                            	<tr>
                                	<td class="pro_img"><a href="${ctxFront }/integralMall/commodityDetails?productId=${order.productId}">
                                	<img src="${product.productLogoNormal }" />
                                	<td class="pro_name"><a href="${ctxFront }/integralMall/commodityDetails?productId=${order.productId}">${product.productName}</a></td>
                                	<td class="">${order.productCount}</td>
                                	<td class="pro_date"><fmt:formatDate value="${order.createDt }" pattern="yyyy-MM-dd" /><br/><fmt:formatDate value="${order.createDt }" pattern="HH:mm:ss" /></td>
                                	<td class="">${order.price}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <c:if test="${product.productTypeId == 1 }">
                <div class="section" style="border-bottom:none;">
                    <div class="step_tit" style="margin-top:20px;"><span>收货人信息</span></div>
                    <div class="step_con consignee_info" style="padding-bottom:0;">
                    	<dl class="clearfix">
                        	<dt>收货人：</dt>
                            <dd>${order.showName }</dd>
                        </dl>
                    	<dl class="clearfix">
                        	<dt>联系电话：</dt>
                            <dd>${order.mobile }</dd>
                        </dl>
                    	<dl class="clearfix">
                        	<dt>收货地址：</dt>
                            <dd>${order.addressShow }</dd>
                        </dl>
                    </div>
                </div>
                </c:if>
                <div style="text-align: center;">
                <c:if test="${product.productTypeId == 2 }">请到【我的账户】-【我的现金券】查看</c:if>
                <c:if test="${product.productTypeId == 3 }">请到【我的账户】查看</c:if>
                <c:if test="${product.productTypeId == 4 }">请到【我的账户】-【我的提现券】查看</c:if>
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