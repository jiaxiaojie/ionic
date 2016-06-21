<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_menu"/>
		<link href="${ctxStatic}/modules/front/css/integralMall.css?${version }" type="text/css" rel="stylesheet"/>
		
		<script type="text/javascript">
			var surplus = ${product.productSurplus };
			var showIntroduction = function() {
				$("#introduction").show();
				$("#introductionSpan").addClass("selected");
				$("#specification").hide();
				$("#specificationSpan").removeClass("selected");
			}
			var showSpecification = function() {
				$("#introduction").hide().addClass("selected");
				$("#introductionSpan").removeClass("selected");
				$("#specification").show().removeClass("selected");
				$("#specificationSpan").addClass("selected");
			}
			
			var addCount = function(number) {
				if($("#inputForm").valid()) {
					var count = parseInt($("#count").val()) + number;
					if(count > 0 && count <= surplus) {
						$("#count").val(count);
					}
				}
			}
			var submit = function() {
				$("#inputForm").submit();
			}
			$(function() {
				$("#inputForm").validate({
					messages: {count: {required: "请输入您要购买的数量", digits: "请输入整数", max: "<br/><br/>库存为：${product.productSurplus }，购买数量不能大于库存数量"}},
					errorClass: "tips2",
					errorLabelContainer: "#errorContainer"
				});
			});
		</script>
		<title></title>
	</head>
<body>

<div class="page">
    <div class="div_width_980 nav_row">
    	<div class="left"><a href="${ctxFront }/integralMall/index">花生乐园</a>&nbsp;&gt;&nbsp;<a href="#">${product.productName }</a></div>
        <div class="right"></div>
    </div>
    
    <div class="clearfix proinfo">
    	<div class="fl detail_img">
    		<img src="${product.productLogoMax}" />
   		</div>
   		<div class="fr detail_text">
   			<form id="inputForm" action="${ctxFront }/integralMall/confirmOrder" method="post">
   				<input type="hidden" name="productId" id="productId" value="${product.productId }"/>
	        	<dl class="title clearfix">
	            	<dt>${product.productName }</dt>
	            </dl>
	        	<dl class="clearfix">
	            	<dt>商品编号：</dt>
	                <dd>${product.productId }</dd>
	            </dl>
	        	<dl class="clearfix">
	            	<dt>原兑换花生豆：</dt>
	                <dd>${product.price }</dd>
	            </dl>
	        	<dl class="clearfix">
	            	<dt>现兑换花生豆：</dt>
	                <dd>${product.showPrice }</dd>
	            </dl>
	            <!-- 
	        	<dl class="clearfix">
	            	<dt>市场价：</dt>
	                <dd>￥1980</dd>
	            </dl>
	             -->
	        	<dl class="clearfix">
	            	<dt>库存：</dt>
	                <dd>${product.productSurplus }</dd>
	            </dl>
	       	  <dl class="number clearfix">
	            	<dt>数量：</dt>
	                <dd>
	                	<a href="javascript:;" onclick="addCount(-1)">-</a><input type="text" id="count" name="count" value="1" min="1" max="${product.productSurplus }" class="required digits" /><a href="javascript:;" onclick="addCount(1)">+</a>
	                	<div id="errorContainer"></div>
	                </dd>
	            </dl>
	            <dl class="btn_area clearfix">
	            	<dt>&nbsp;</dt>
	            	<dd><a href="javascript:;" class="btn_brown_158x31" onclick="submit()">立即兑换</a></dd>
	            </dl>
	            <!-- 
	            <dl class="clearfix">
	            	<dt>&nbsp;</dt>
	            	<dd>每人限购2件</dd>
	            </dl>
	             -->
             </form>
        </div>
    </div>
    
    <div class="div_width_980 clearfix">
    	<div class="fl w720">
        	<div class="allGoods pd0">
            	<div id="tab_bar" class="tab_bar">
                    <li id="item_1" onclick="showIntroduction()" class="item"><span id="introductionSpan" class="selected">产品描述</span></li>
                    <li id="item_2" onclick="showSpecification()" class="item"><span id="specificationSpan">规格参数</span></li>
                </div>
                
                <!--产品描述-->
                <div id="introduction" class="product_des" style="display:block;">
                	<p>${product.productIntroduction }</p>
                </div>
                
                <!--规格参数-->
                <div id="specification" class="specification" style="display:none">
                	<div class="title">基本属性</div>
                	<table class="table">
                    	<tbody>
                    		<c:if test="${empty spsList }">
                    		<tr><td></td><td>暂无参数</td></tr>
                    		</c:if>
                    		<c:forEach var="sps" items="${spsList }">
	                        	<tr>
	                            	<th>${sps.paraName }</th>
	                                <td width="562">${sps.paraVal }</td>
	                            </tr>
                    		</c:forEach>
                    		<tr><td></td><td>&nbsp;</td></tr>
                        </tbody>
                    </table>
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
