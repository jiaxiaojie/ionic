<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:if test="${modelMenus.selectView}">
						<script type="text/javascript">
						var queryString='${modelMenus.queryString}';
						var idName = '${modelMenus.idName}';
						var textName =  '${modelMenus.textName}';
						$(document).ready(function() {
							//禁用表单
							var myQueryParams = queryString.split("&");
							for(var i = 0; i < myQueryParams.length; i++){
								var keyValue = myQueryParams[i].split("=");
								var key = keyValue[0];
								var val = keyValue[1];
								var myelement = $("#"+key);
								myelement.attr("disabled",true);
								
								//如果是select则将value值为其他的删掉（解决ie中disabled不能用的问题）
								if($(myelement).is('select')){
									myelement.find("option[value!='"+val+"']").remove();
									
							    }
							}
							
							//设置之前页面传入的查询参数
							$("#searchForm").each(function(){
								  $(this).attr("action",addParams($(this).attr("action"),queryString));
								});
							
							//去掉页面中其他可以点击的功能
							$.each( $("table tr td a").not($(".toSelected")).not($(".toSelected").nextAll()), function(i, n){
								  $(this).replaceWith($(this).text());
							});
							$(".toSelected").nextAll().remove(); 
							$("ul[class='nav nav-tabs']").remove();
							
							//写入结果input存放选择结果
							$("body").prepend('<input type="hidden" id="selectedResultVal" value="" />');
							$("body").prepend('<input type="hidden" id="selectedResultText" value="" />');
							
							var ids = $("input[name='"+idName+"']");
							var texts = $("input[name='"+textName+"']");
							$.each( $("table tr").not($("table tr").first()), function(i, n){
								var lastTd =  $(this).find("td").last();
								lastTd.empty();
								var idVal = $(ids.get(i)).val();
								var textVal =  $(texts.get(i)).val();
								//alert(textVal);
								var methodStr = 'selected('+idVal+',&quot;'+textVal+'&quot;);';
								//alert(methodStr);
								lastTd.append('<a class="toSelected" href="#" onclick="'+methodStr+'">选择</a>');
							});
							
							
							
						});
						function selected(ruleId,textVal){
							
							$("#selectedResultVal").val(ruleId);
							$("#selectedResultText").val(textVal);
							
							top.$.jBox.getBox().find("button[value='ok']").trigger("click");
							
							return false;
						}
				    	
						</script>
						
						
	</c:if>