<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <%@ attribute name="menus" type="java.lang.String" required="true" description="菜单"%>
<%@ attribute name="topMenus" type="java.lang.String" required="true" description="顶层菜单"%>
<%@ attribute name="join" type="java.lang.String" required="true" description="关联input的id"%>--%>
<%@ attribute name="url" type="java.lang.String" required="true" description="选择面板url"%>
<%@ attribute name="idName" type="java.lang.String" required="true" description="选择面板页id集合的name值"%>
<%@ attribute name="textName" type="java.lang.String" required="true" description="选择面板页text集合的name值"%>
<%@ attribute name="path" type="java.lang.String" required="true" description="input的name和id"%>
<%@ attribute name="title" type="java.lang.String" required="false" description="选择面板标题"%>
<%@ attribute name="callbackOnSelected" type="java.lang.String" required="false" description="选择之后的回掉函数"%>

<%@ attribute name="idField" type="java.lang.String" required="false" description="选择面板查询实体id字段名称"%>


<input  id="textId_${path}" readonly="readonly" class="input-medium" value="${modelMenus.selectedText }"  maxlength="20" type="text">
<%-- <input  id="${path}" name="${path}" class="input-medium"  maxlength="20" type="hidden"> --%>

<a href="#" id="${path }selectList">选择</a>
<script type="text/javascript">


$(document).ready(function() {
	var disableSelect = "${empty modelMenus.disableSelect?false:modelMenus.disableSelect}";
	var fullUrl = addParams("${url}","selectView=true&idName=${idName}&textName=${textName}");
	var withoutParamsUrl = addParams("${url}".split("?")[0],"selectView=true&idName=${idName}&textName=${textName}");
	
	var textName = '${textName}';
	var idField = '${idField}';
	
	getQById("${path}").attr("style","display:none;");
	
	$("#searchForm").unbind('submit');
	$("#searchForm").bind('submit', function() {
			var selectedTextParams = "";
			$.each($("input[id^='textId_']"), function(i, n){
				var mythis = $(this);
				selectedTextParams += mythis.attr("id") + "=" + mythis.val() + "&";
			});
			//alert(selectedTextParams);
			$(this).attr("action",addParams($(this).attr("action"),"selectedTextParams="+encodeURIComponent(selectedTextParams)));
	});
	
	//设置参数回显
	var selectedTextParams = "${fns:urlDecode(modelMenus.selectedTextParams)}".split("&");
	$.each(selectedTextParams, function(i, n){
		//alert($(this)[0]);
		var idValue = $(this)[0].split("=");
		var id = idValue[0];
		var value = idValue[1];
		//alert("name:"+name);
		//alert("value:"+value);
		$("#"+id).attr("value",value);
	});
	
	//如果没有回显 
	/**/
	var textInput = getQById("${path}");//$(document.getElementById(""));//$("#");
	var value = textInput.val();
	if(value !=null && value != ""){
		var name = textInput.attr("name");
		if(idField != ""){
			name = idField;
		}
		
		$.ajax({
			type : 'post',
			url : addParams( withoutParamsUrl ,name+"="+value),
			dataType : 'html',
			success : function(data) {
				var el = document.createElement( 'div' );
				el.innerHTML = data; 
				var textVal = $(el).find("input[name='"+textName+"']").val();
				
				 
				getQById("textId_${path}").attr("value",textVal);
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//alert("出现异常");
			}
		});
	}
	
	//如果id input不可用则隐藏选择按钮
	if(getQById("${path}").attr("disabled")=="disabled" || disableSelect=="true"){
		
		getQById("${path}selectList").remove();
		
	}
	
	getQById("${path }"+"selectList").click(function(){
		
		top.$.jBox.open("iframe:"+fullUrl, '${title==null?"请选择":title}', 1400, $(top.document).height()-180, {
	        buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
	            if (v=="ok"){
	            	
	            	var selectedRuleId = h.find("iframe")[0].contentWindow.$("#selectedResultVal").val();
	            	var selectedResultText =  h.find("iframe")[0].contentWindow.$("#selectedResultText").val();
	            	
	            	
	            	getQById("textId_${path}").attr("value",selectedResultText);
            		getQById("${path}").attr("value",selectedRuleId);
            		
            		if('${callbackOnSelected}' != ''){
            			window.setTimeout("${callbackOnSelected}", 300);
            		}
	            		
	            	
	            	
	                
	            }else if (v=="clear"){
	                
	            }
	        }, loaded:function(h){
	            $(".jbox-content", top.document).css("overflow-y","hidden");
	        }
	    });
		
		top.$.jBox.getBox().find("button[value='ok']").attr("style","display:none;");
	});
	
});

function getQById(id){
	return $(document.getElementById(id));
}
</script>