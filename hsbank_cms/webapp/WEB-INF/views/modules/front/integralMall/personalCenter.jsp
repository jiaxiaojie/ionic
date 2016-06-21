<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_menu"/>
		<link href="${ctxStatic}/modules/front/css/integralMall.css?${version }" type="text/css" rel="stylesheet"/>

		<script type="text/javascript">
			var recentMonth = "${recentMonth}";
			$(function() {
				var defaultUlHeigth = $("#addressList").css("height");
				$("#moreAddress").click(function() {
					$("#addressList").css("height", "auto");
					$("#moreAddress").removeClass("show").addClass("hide");
					$("#foldAddress").removeClass("hide").addClass("show");
				});
				$("#foldAddress").click(function() {
					$("#addressList").css("height", defaultUlHeigth);
					$("#moreAddress").removeClass("hide").addClass("show");
					$("#foldAddress").removeClass("show").addClass("hide");
				});
				$("#recentMonth").val(recentMonth);
				$("#recentMonth").change(function() {
					$("#searchForm").submit();
				});
				
				$("#addressForm").validate({
					submitHandler : function() {
						$.ajax({
							url: "${ctxFront}/integralMall/saveAddress",
							dataType: "json",
							type: "post",
							data: $("#addressForm").serialize(),
							success: function(data) {
								var address = data.customerAddress;
								var li = "<li class=\"clearfix\" data=\"" + address.id + "\"><i class=\"icon_selected\"></i><div class=\"fl addr_detail\">"
									+ "<span>" + address.showName + "</span><span>" + address.address + "</span>"
									+ "<span>" + address.mobile + "</span></div><div class=\"fr op_btns\">"
									+ "<a href=\"javascript:;\" onclick=\"setDefaultAddress(" + address.id + ")\">设为默认地址</a>"
									+ "<a href=\"javascript:;\" onclick=\"editAddress(" + address.id + ")\">编辑</a>"
									+ "<a href=\"javascript:;\" onclick=\"deleteAddress(" + address.id + ")\">删除</a></div></li>";
								if(address.isDefault == "1") {
									li = "<li class=\"clearfix\" data=\"" + address.id + "\"><i class=\"icon_selected\"></i><div class=\"fl addr_detail selected\">"
										+ "<span>" + address.showName + "</span><span>" + address.address + "</span>"
										+ "<span>" + address.mobile + "</span></div><div class=\"fr op_btns\">"
										+ "<span>默认地址</span>"
										+ "<a href=\"javascript:;\" onclick=\"editAddress(" + address.id + ")\">编辑</a>"
										+ "<a href=\"javascript:;\" onclick=\"deleteAddress(" + address.id + ")\">删除</a></div></li>";
								}
								//新增地址
								if(data.isAdd) {
									$("#addressForm")[0].reset();
									$("#id").val("");
									$("#isDefault").val("");
									$("#status").val("");
									$(li).appendTo($("#addressList"));
									$("#noAddressDiv").hide();
									$("#addressListDiv").show();
									$("#moreAddress").click();
									closePop();
									$.jBox.tip("新增地址成功！", "success");
								}
								//修改地址
								else {
									$("#addressList li[data=" + address.id + "]").prop("outerHTML", li);
									$("#addressForm")[0].reset();
									$("#id").val("");
									$("#isDefault").val("");
									$("#status").val("");
									closePop();
									$.jBox.tip("编辑地址成功！", "success");
								}
							}
						})
						return false;//阻止表单提交
					},
					rules: {
						showName: {required: true, realName: true},
						mobile: {required: true, mobile: true},
						address: {required: true, minlength: 8, maxlength: 80},
						postCode: {required: true, zipCode: true}
					},
					errorClass: "tips3",
					messages: {
						showName: {required: "<br/><br/>请输入收件人姓名。", realName: "<br/><br/>姓名只能为2-10个汉字。"},
						mobile: {required: "<br/><br/>请输入收件人手机号码。", mobile: "<br/><br/>请输入正确的手机号码。"},
						address: {required: "<br/><br/>请输入详细收货地址。", minlength: "<br/><br/>收货地址最少为8位。", maxlength: "<br/><br/>收货地址最多为80位。"},
						postCode: {required: "<br/><br/>请输入邮编。", zipCode: "<br/><br/>请输入正确的邮编。"}
					}
				});
				
				//初始化搜索框
				$("#recentMonth").val("${recentMonth}");
			});
			
			var closePop = function() {
				$("#pop").hide();
			}
			var addAddress = function() {
				$("#addressForm")[0].reset();
				$("#id").val("");
				$("#isDefault").val("");
				$("#status").val("");
				$("#pop").show();
			}
			var editAddress = function(addressId) {
				$.getJSON("${ctxFront}/integralMall/getCustomerAddress?id=" + addressId, function(address){
					$("#id").val(address.id);
					$("#isDefault").val(address.isDefault);
					$("#status").val(address.status);
					$("#showName").val(address.showName);
					$("#mobile").val(address.mobile);
					$("#address").val(address.address);
					$("#postCode").val(address.postCode);
					$("#pop").show();
				});
			}
			var deleteAddress = function(addressId) {
				$.jBox.confirm("确定删除此收货地址吗？", "操作提示",
					function(v, h, f) {
						if(v==1) {
							$.getJSON("${ctxFront}/integralMall/deleteCustomerAddress?id=" + addressId, function(id) {
								$("#addressList li[data=" + id + "]").remove();
								if($("#addressList li").length < 1) {
									$("#noAddressDiv").show();
									$("#addressListDiv").hide();
								}
								$.jBox.tip("删除地址成功！", "success");
							});
						}
					}, {
						buttons: {
							'确定' : 1,
							'取消' : -1
						}
					}
				);
			}
			var setDefaultAddress = function(addressId) {
				$.getJSON("${ctxFront}/integralMall/setDefaultCustomerAddress?id=" + addressId, function(oldDefaultAddressId){
					if(oldDefaultAddressId != "") {
						$("#addressList li[data=" + oldDefaultAddressId + "]").removeClass("selected");
						var newBtn = "<a href=\"javascript:;\" onclick=\"setDefaultAddress(" + oldDefaultAddressId + ")\">设为默认地址</a>"
							+ "<a href=\"javascript:;\" onclick=\"editAddress(" + oldDefaultAddressId + ")\">编辑</a>"
							+ "<a href=\"javascript:;\" onclick=\"deleteAddress(" + oldDefaultAddressId + ")\">删除</a></div></li>";
						$("#addressList li[data=" + oldDefaultAddressId + "] .op_btns").html(newBtn);
					}
					$("#addressList li[data=" + addressId + "]").addClass("selected");
					var newBtn = "<span>默认地址</span>"
						+ "<a href=\"javascript:;\" onclick=\"editAddress(" + addressId + ")\">编辑</a>"
						+ "<a href=\"javascript:;\" onclick=\"deleteAddress(" + addressId + ")\">删除</a></div></li>";
					$("#addressList li[data=" + addressId + "] .op_btns").html(newBtn);
					$.jBox.tip("设为默认地址成功！", "success");
				});
			}
			var submit = function() {
				$("#addressForm").submit();
			}
		</script>
		<title></title>
	</head>
<body>

<div class="page">
    <div class="div_width_980 nav_row">
    	<div class="left"><a href="${ctxFront }/integralMall/index">花生乐园</a>&nbsp;&gt;&nbsp;<a href="#">个人中心</a></div>
        <div class="right"></div>
    </div>
    
    <div class="div_width_980 clearfix">
    	<div class="fl w720">
        	<div class="allGoods">
            	<div class="section1 clearfix">
                	<div class="fl avatar"><img src="${pageContext.request.contextPath}${avatar_image}" /></div>
                    <div class="fl name_date"><span>${customerBase.customerName }</span><p>上次登录时间：<fmt:formatDate value="${customerAccount.lastLoginDt }" pattern="yyyy-MM-dd HH:mm:ss" /></p></div>
                    <div class="fl integral"><span>花生豆</span><b>${customerIntegralSnapshot.integralBalance }</b></div>
                </div>
            </div>
            
        	<div class="allGoods">
        		<form id="searchForm" action="${ctxFront }/integralMall/personalCenter" method="post">
        		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }" />
            	<div class="section">
                    <div class="step_tit">
                    	<span>我的订单</span>
                    	<div class="fr">
                        	<select id="recentMonth" name="recentMonth" style="margin:0">
                              <option value="one">近1个月订单</option>
                              <option value="three">近3个月订单</option>
                              <option value="all">所有订单</option>
                            </select>
                    	</div>
                    </div>
                    <!--订单如果出现6条就有翻页-->
                    <div class="step_con" style="padding-bottom:0;">
                    	<table class="table pro_list">
                        	<thead>
                            	<tr>
                                	<th class="pro_name" width="150">商品名称</th>
                                	<th class="" width="136">购买数量</th>
                                	<th class="pro_date" width="118">下单时间</th>
                                	<th class="" width="92">花生豆总价</th>
                                	<th class="" width="81">状态</th>
                                	<th class="" width="73">操作</th>
                                </tr>
                            </thead>
                        	<tbody>
                        		<c:forEach var="order" items="${page.list }">
                       			<tr>
                                	<td class="pro_name"><a href="${ctxFront }/integralMall/commodityDetails?productId=${order.productId }">${order.productName }</a></td>
                                	<td class="">${order.productCount }</td>
                                	<td class="pro_date"><fmt:formatDate value="${order.createDt }" pattern="yyyy-MM-dd" /><br/><fmt:formatDate value="${order.createDt }" pattern="HH:mm:ss" /></td>
                                	<td class="">${order.price }</td>
                                	<td class="">${fns:getDictLabel(order.orderStatus, 'integral_mall_order_status_dict', '')}</td>
                                	<td class=""><a href="${ctxFront }/integralMall/orderDetails?orderNo=${order.orderNo}">详情</a></td>
                                </tr>
                        		</c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
		        </form>
                
                <!-- 分页开始 -->
		        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
		        <script type="text/javascript">
		        	function page(n,s) {
		        		$("#pageNo").val(n);
		        		$("#searchForm").submit();
		        	}
		        </script>
		        <!-- 分页结束 -->
		        
            </div>
            
            <div class="allGoods">
            	<div class="section">
                    <div class="step_tit"><span>收货人信息</span><a href="javascript:;" onclick="addAddress()">+&nbsp;新增收货地址</a></div>
                    <!--如果从未添加过地址的话，显示下面的内容：“no_addr”-->
                    <c:choose>
                    	<c:when test="${empty customerAddressList }">
                    <div id="noAddressDiv" class="no_addr">
                        <p>你还未添加过收货地址，点击“<span onclick="addAddress()" style="cursor:pointer">+&nbsp;新增收货地址</span>”添加一个呗。</p>
                    </div>
                    <div id="addressListDiv" class="step_con" style="display:none">
                        <!--点击展开后（addr_list）的高度为auto，如<ul class="addr_list" style=" height:auto;">-->
                        <ul id="addressList" class="addr_list">
                        </ul>
                        <div class="more_addr">
                        	<a href="javascript:;" id="moreAddress" class="show">更多地址↓</a>
                        	<a href="javascript:;" id="foldAddress" class="hide">收起地址↑</a>
                       	</div>
                    </div>
                    	</c:when>
                    	<c:otherwise>
                    <div id="noAddressDiv" class="no_addr" style="display:none">
                        <p>你还未添加过收货地址，点击“<span onclick="addAddress()" style="cursor:pointer">+&nbsp;新增收货地址</span>”添加一个呗。</p>
                    </div>
                    <div id="addressListDiv" class="step_con" style="display:block">
                        <!--点击展开后（addr_list）的高度为auto，如<ul class="addr_list" style=" height:auto;">-->
                        <ul id="addressList" class="addr_list">
	                    	<c:forEach var="customerAddress" items="${customerAddressList }">
	                    		<c:choose>
	                    			<c:when test="${customerAddress.isDefault eq '1' }">
                   			<li class="clearfix selected" data="${customerAddress.id }">
                            	<i class="icon_selected"></i>
                                <div class="fl addr_detail">
                                    <span>${customerAddress.showName }</span>
                                    <span>${customerAddress.address }</span>
                                    <span>${customerAddress.mobile }</span>
                                </div>
                                <div class="fr op_btns">
                                    <span>默认地址</span>
                                    <a href="javascript:;" onclick="editAddress(${customerAddress.id})">编辑</a>
                                    <a href="javascript:;" onclick="deleteAddress(${customerAddress.id})">删除</a>
                                </div>
                            </li>
	                    			</c:when>
	                    			<c:otherwise>
	                    	<li class="clearfix" data="${customerAddress.id }">
                            	<i class="icon_selected"></i>
                                <div class="fl addr_detail">
                                    <span>${customerAddress.showName }</span>
                                    <span>${customerAddress.address }</span>
                                    <span>${customerAddress.mobile }</span>
                                </div>
                                <div class="fr op_btns">
                                    <a href="javascript:;" onclick="setDefaultAddress(${customerAddress.id})">设为默认地址</a>
                                    <a href="javascript:;" onclick="editAddress(${customerAddress.id})">编辑</a>
                                    <a href="javascript:;" onclick="deleteAddress(${customerAddress.id})">删除</a>
                                </div>
                            </li>
	                    			</c:otherwise>
	                    		</c:choose>
                    		</c:forEach>
                        </ul>
                        <div class="more_addr">
                        	<a href="javascript:;" id="moreAddress" class="show">更多地址↓</a>
                        	<a href="javascript:;" id="foldAddress" class="hide">收起地址↑</a>
                       	</div>
                    </div>	
                    	</c:otherwise>
                    </c:choose>
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
<!--编辑收货人和新增收货人信息都用此弹窗-->
<div id="pop" class="pop_bg" style="display: none">
	<div class="pop_main"
		style="width: 690px; height: 452px; margin-left: -345px; margin-top: -226px;">
		<div class="pop_title">
			编辑收货人信息<a href="javascript:;" onClick="closePop()" class="close_pop"></a>
		</div>
		<div class="pop_content">
			<div class="consignee_info clearfix">
				<form id="addressForm" action="" method="post">
					<input type="hidden" id="id" name="id" />
					<input type="hidden" id="status" name="status" value="" />
					<input type="hidden" id="isDefault" name="isDefault" value="" />
					<dl class="clearfix">
						<dt>
							<span>*</span>收件人：
						</dt>
						<dd>
							<input id="showName" name="showName" type="text" />
						</dd>
					</dl>
					<dl class="clearfix">
						<dt>
							<span>*</span>手机号码：
						</dt>
						<dd>
							<input id="mobile" name="mobile" type="text" />
						</dd>
					</dl>
					<dl class="street_addr clearfix">
						<dt>
							<span>*</span>详细地址：
						</dt>
						<dd>
							<input id="address" name="address" type="text" />
						</dd>
					</dl>
					<dl class="clearfix">
						<dt>
							<span>*</span>邮政编码：
						</dt>
						<dd>
							<input id="postCode" name="postCode" type="text" />
						</dd>
					</dl>
					<dl class="clearfix">
						<dt>&nbsp;</dt>
						<dd>
							<a href="javascript:;" onclick="submit()" class="btn_brown_158x31" style="display: inline-block">保存</a>
						</dd>
					</dl>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>