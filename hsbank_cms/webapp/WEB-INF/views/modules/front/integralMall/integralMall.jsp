<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_menu"/>
		<link href="${ctxStatic}/modules/front/css/integralMall.css?${version }" type="text/css" rel="stylesheet"/>
		<title></title>
		
		<script type="text/javascript">
			var searchType = "${searchType}";
			var searchType_integralRang = "integralRang";
			var searchType_keywords = "keywords";
			
			var integralRange = "${integralRange}";
			var integralRange_1 = "integralRange0";
			var integralRange_1 = "integralRange1";
			var integralRange_2 = "integralRange2";
			var integralRange_3 = "integralRange3";
			var integralRange_4 = "integralRange4";
			var integralRange_5 = "integralRange5";
			
			var orderBy = "${page.orderBy}";
			var orderBy_upDt_desc = "up_dt desc";
			var orderBy_upDt_asc = "up_dt asc";
			var orderBy_price_desc = "price desc";
			var orderBy_price_asc = "price asc";
			$(function() {
				//搜索类型初始化
				$("#searchType").val(searchType);
				if(searchType == searchType_keywords) {
					showKeywordsSearchInput();
				}
				$("#searchType").change(function(){
					if($(this).children('option:selected').val() == searchType_integralRang) {
						showIntegralRangSearchInput();
					} else {
						showKeywordsSearchInput();
					}
				});
				//花生豆范围初始化
				$("#integralRange").val(integralRange);
				$("#showTerms").html($("#integralRangSearchInput li[data='" + integralRange + "']").html());	//根据花生豆范围显示相应的label
				$("#integralRangSearchInput li[name='literm']").click(function() {
					var value = $(this).attr("data");
					var label = $(this).html();
					
					$("#integralRange").val(value);
					$("#showTerms").html(label);
				});
				//排序初始化
				$("#orderBy").val(orderBy);
				if(orderBy == orderBy_upDt_asc) {
					$("#orderByUpDtAsc").show().addClass("selected");
					$("#orderByUpDtDesc").hide();
					$("#orderByPriceDesc").show().removeClass("selected");
					$("#orderByPriceAsc").hide();
				} else if(orderBy == orderBy_price_asc) {
					$("#orderByUpDtDesc").show().removeClass("selected");
					$("#orderByUpDtAsc").hide();
					$("#orderByPriceAsc").show().addClass("selected");
					$("#orderByPriceDesc").hide();
				} else if(orderBy == orderBy_price_desc) {
					$("#orderByUpDtDesc").show().removeClass("selected");
					$("#orderByUpDtAsc").hide();
					$("#orderByPriceDesc").show().addClass("selected");
					$("#orderByPriceAsc").hide();
				}
			});
			
			var showIntegralRangSearchInput = function() {
				$("#integralRangSearchInput").show();
				$("#keywords").hide();
			}
			
			var showKeywordsSearchInput = function() {
				$("#keywords").show();
				$("#integralRangSearchInput").hide();
			}
			
			var orderByWith = function(orderBy) {
				$("#orderBy").val(orderBy);
				$("#searchForm").submit();
			}
			
			var submit = function() {
				$("#searchForm").submit();
			}
		</script>
	</head>
<body>
<div class="page">
    <!-- Banner区域 -->
    <div class="div_height_20"></div>
    <div class="banner"><img src="${ctxStatic}/modules/front/images/integralMall/integralMall_banner.jpg" /></div>
    
    <div class="div_width_980 clearfix">
    	<div class="fl w720">
        	<div class="allGoods">
                <div class="ag_title">全部商品</div>
                <div class="retrieval clearfix">
                    <form id="searchForm" action="${ctxFront }/integralMall/index" method="post" class="fl clearfix">
                    	<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }" />
                    	<input type="hidden" id="orderBy" name="orderBy" />
                        <select id="searchType" name="searchType" size="1" class="fl select">
                          <option value="integralRang">花生豆范围</option>
                          <option value="keywords">礼品关键字</option>
                        </select>
                        <div id="integralRangSearchInput" class="m-select" style="display:block;">
                          <div class="m-row clearfix">
                            <div class="l m-drop clearfix dropdown">
                              <span class="m-dropInput" id="showTerms">0~100000颗</span>
                              <a class="m-dropIcon on dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" data-target="#"></a>
                              <ul class="m-dropList dropdown-menu" id="m-dropList" role="menu">
                                <!-- js实现：点击按钮.m-dropIcon 显示m-dropList；点击'.m-dropList li'改变 .m-dropHid 的value属性-->
                                <li name="literm" id="li_0" data="integralRange0">全部</li>
                                <li name="literm" id="li_1" data="integralRange1">0~10万颗</li>
                                <li name="literm" id="li_2" data="integralRange2">10万颗~50万颗</li>
                                <li name="literm" id="li_3" data="integralRange3">50万颗~100万颗</li>
                                <li name="literm" id="li_4" data="integralRange4">100万颗~5000万颗</li>
                                <li name="literm" id="li_5" data="integralRange5">5000万颗以上</li>
                              </ul>
                            </div>
                          </div>
                        </div>
                        <input type="hidden" id="integralRange" name="integralRange" />
                        <input class="fl" id="keywords" name="keywords" value="${keywords}" type="text" style="display:none" />
                        <div class="fl bt_yellow_85x24" onclick="submit()">查询</div>
                    </form>
                    <div class="sort fr">
                        <p class="">排序：</p>
                        <!--选中加上一个类：“selected”-->
                        <a href="javascript:;" class="first selected" id="orderByUpDtDesc" onclick="orderByWith(orderBy_upDt_asc)"><span style="display:block">上架时间&nbsp;↓</span></a>
                        <a href="javascript:;" class="first" id="orderByUpDtAsc" onclick="orderByWith(orderBy_upDt_desc)" style="display:none"><span style="display:block">上架时间&nbsp;↑</span></a>
                        <a href="javascript:;" class="last" id="orderByPriceDesc" onclick="orderByWith(orderBy_price_asc)"><span style="display:block">花生豆值&nbsp;↓</span></a>
                        <a href="javascript:;" class="last" id="orderByPriceAsc" onclick="orderByWith(orderBy_price_desc)" style="display:none"><span style="display:block">花生豆值&nbsp;↑</span></a>
                    </div>
                </div>
                    <ul class="ag_list clearfix">
                    	<c:forEach var="product" items="${page.list}">
                        <li>
                            <a href="${ctxFront }/integralMall/commodityDetails?productId=${product.productId}" class="img">
                            	<img src="${product.productLogoNormal}" />
                           	</a>
                            <a href="${ctxFront }/integralMall/commodityDetails?productId=${product.productId}" class="title">${product.productName }</a>
                            <p class="integral">原价:<span>${product.price}</span>花生豆<br/>现价:<span>${product.showPrice}</span>花生豆</p>
                        </li>
                    	</c:forEach>
                       
                    <div style="clear:both;"></div>
                    <br/>
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
        </div>
        
    	<div class="fr w240">
            <div class="record pc_area"><a href="${ctxFront }/integralMall/personalCenter" class="btn_pc"></a></div> 
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
                    	<a href="${ctxFront }/integralMall/commodityDetails?productId=${order.productId}" class="title">${order.product.productName }</a>&nbsp;${order.productCount }个</div>
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
