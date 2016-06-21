<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="menus" type="java.lang.String" required="true" description="菜单"%>
<%@ attribute name="topMenus" type="java.lang.String" required="true" description="顶层菜单"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="唯一标识"%>


<%--
	功能说明：
	在项目中，经常要把几个页面通过选项菜单进行关联，
	把这个标签放到某个jsp中，会覆盖隐藏这个页面之前的菜单栏，生成一个新的菜单选项卡（即此标签定义的菜单），并会根据当前页的地址自动选中某个选项卡菜单，
	使用说明：
	
	在要嵌入二级menu的页面嵌入这段代码，topMenus是第一级menu列表，menus是第二级menu列表，id是标识，如果页面有多个这种标签，注意id一定不能重复
	<sys:menus 
topMenus="[
{title:'',href='',selected=false},
{title:'',href='#',selected=true}]" 

menus="[
{title:'',href='',selected=true},
{title:'',href=''},
{title:'',href=''},
{title:'',href=''},
{title:'',href=''}]" 

id="" ></sys:menus>

在从其他页面跳进带有二级menu菜单（这个标签）的页面时，必须带有pageType属性，pageType指定当前显示那个menus菜单，pageType的值即sys:menus标签的id；
还可以选带一个参数currentMenuId，来指示当前选中的二级菜单项是哪个，目前currentMenuId是自定生成，其值为sys:menus 的id+顺序号，其中顺序号码从0开始。
若不带currentMenuId参数，则会根据当前页面的地址进行智能选择
 --%>

<c:if test="${modelMenus.pageType == id}">
<script type="text/javascript">


var queryString='${modelMenus.queryString}';
var disabledForm = '${modelMenus.disabledForm}';
var disabledQueryForm = '${modelMenus.disabledQueryForm}';
var hasTopMenus = '${modelMenus.hasTopMenus}';
var emptyValNoDisabled = '${modelMenus.emptyValNoDisabled}';
var readonly = '${modelMenus.readonly}';


function getFirstValueForArray(myarray){
	var result = null;
	
	for(var i = 0; i < myarray.length; i++){
		if(myarray[i] != null && myarray[i] != ""){
			result = myarray[i]
			break;
		}
	}
	return result;
}

/**
 * 跳转到指定页面
 */
function toUrl(obj){
	
	var menuElementId = $(obj).parent().attr("id").replace("${id}", "");
	//alert($(obj).attr("href").replace("#",""));
	window.location.href=addParams($(obj).attr("href").replace("#",""),"pageType=${id}&readonly="+readonly+"&currentMenuId="+menuElementId+"&hasTopMenus="+hasTopMenus); 
	
}

function setUnableAlterFormByQueryString(form,queryStr,canSubmit)
{
	
	var myQueryParams = queryStr.split("&");
	
	for(var i = 0; i < myQueryParams.length; i++){
		var keyValue = myQueryParams[i].split("=");
		var name = keyValue[0];
		var val = keyValue[1];
		
		if(name != "" && val != ""){
			setUnableAlterForm(form,name,canSubmit);
		}
	}
}
//将name为names的form中的元素设置为不可修改
//canSubmit为true是只读，false是禁用
function batchSetUnableAlterForm(form,names,canSubmit){
	for(var i = 0; i < names.length; i++){
		var name = names[i];
		setUnableAlterForm(form,name,canSubmit);
	}
}
function setUnableAlterForm(form,name,canSubmit){
	var input = $(form).find("input[name='"+name+"']");
	if(canSubmit){
		input.attr("readonly", "readonly");
	}else{
		input.attr("disabled",true);
	}
}

//过滤查询参数，将form中有的参数，以及key或value为空的，从查询字符串中去除
function filterQueryString(formId, queryStr){
	
	
	var resultParams = "";
	if($("#"+formId).length > 0){
		
		var paramString = serializeForm(formId);
		paramString += "&"+$("#"+formId).attr("action").split("?")[1];

		$.each( $(queryStr.split("&")), function(i, n){
			var key = n.split("=")[0];
			var val = n.split("=")[1];
			
			var isGuolv = false;
			if(key == "" || val == "")
			{
				isGuolv = true;
			}
			else{
				$.each( $(paramString.split("&")), function(i, n){
					var k = n.split("=")[0];
					var v = n.split("=")[1];
					if(key == k && $("input[name='"+k+"']").attr("disabled") != "disabled"){
						
						isGuolv = true;
					}
					
				});
			}
			
			if(!isGuolv){
				resultParams += "&"+n;
			}
		});
	}
	
	return resultParams;
}

//初始化searchForm
function initSearchForm(){
	var searchFormId = "searchForm";
	var searchform = $("#"+searchFormId);
	if(searchform.length > 0){
		
		//如果没有明确指示说不禁用查询表单，则禁用表单
		if(disabledQueryForm  != 'false'){
			setUnableAlterFormByQueryString(searchform,queryString,false);
		}
		
		//过滤searchForm
		queryString = filterQueryString(searchFormId, queryString);
	}
}

//将输入表单的提交改为ajax形式
function inputFormCovertToAjaxSubmit(inputForm){
	var btnSubmit = inputForm.find("#btnSubmit");
	btnSubmit.bind('click', function() {
		var params = serializeForm('inputForm');
		
		//alert(params);
		$.ajax({
			type : 'post',
			url : inputForm.attr("action"),
			data : params,
			dataType : 'text',
			success : function(data) {
				var el = document.createElement( 'div' );
				el.innerHTML = data; 
				var htlm = $(el);
				
				var messageBox = htlm.find("#messageBox");
				$.jBox.tip(messageBox.text().substr(1), 'success');
				window.setTimeout('$("#messageBox").animate({opacity: "hide"}, "slow")', 1000); 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
					$.jBox.error("操作失败","提示");
				   window.setTimeout("location.reload(true);", 1000);
			}
		});
		
		  return false;
	});
}



function initInputForm(){
	
	var inputFormId = "inputForm";
	var inputForm = $("#"+inputFormId);
	if(inputForm.length > 0){
		
		
		//如果没有明确指示说不禁用查询表单，则禁用表单
		if(disabledForm != 'false'){
			
			//inputForm.find(":input").not($("#btnCancel")).attr("readonly", "readonly");
			setUnableAlterFormByQueryString(inputForm,queryString,false);
		}
		//过滤searchForm
		queryString = filterQueryString(inputFormId, queryString);
		
		
		inputFormCovertToAjaxSubmit(inputForm);
		
		
	}
	
	
}


function settingCurrentMenu(){
	// 设置当前menu
	$("#menu_2").find("li").attr("class","");
	var currentMenu = $("#${id}${modelMenus.currentMenuId}");
	if(currentMenu.length == 1){
		currentMenu.attr("class","active");
	}
	else{
		var currentUrl = window.location.href.split("?")[0].replace(window.location.protocol+"//"+window.location.host,"");
		$("#menu_2").find("a[href^='#"+currentUrl+"']").parent().attr("class","active")
	}
}

function removeDuoyuMenu(){
	//删除多余menu
	$("ul[class='nav nav-tabs']").not($("#menu_1")).not($("#menu_2")).remove();
	$("#menuDiv").prevAll("br").remove();
}

function settingSearchFormQueryString(){
	//为此页的查询表单设置参数
	$("#searchForm").each(function(){
			var action = $(this).attr("action").split("?");
		var actionUrl = action[0];
		var actionParams = action[1];
		  $(this).attr("action",actionUrl+"?"+actionParams+queryString);
	});
}

function settingInputFormQueryString(){
	//为此页的查询表单设置参数
	$("#inputForm").each(function(){
		  $(this).attr("action",$(this).attr("action")+"?"+queryString);
	});
}



function initMenus(){
	
	initSearchForm();
	
	initInputForm();
	settingCurrentMenu();
	removeDuoyuMenu();
	settingInputFormQueryString();
	settingSearchFormQueryString();
	
	if(readonly != "true"){
		
	}else{
		$("#inputForm").find(":input").not($("#btnCancel")).attr("disabled",true);
	}
	
}



$(document).ready(function() {
	initMenus();
});





</script>
<div id="menuDiv">
	
	<ul class="nav nav-tabs" <c:if test="${!(modelMenus.hasTopMenus != false) or empty fns:getMapFromJsonArrayStr(topMenus)}">style="display: none;"</c:if> id="menu_1">
		<c:forEach items="${fns:getMapFromJsonArrayStr(topMenus)}" var="menu">
			<li class="${menu.selected?'active':''}"><a href="${menu.href }">${menu.title }</a></li>
		</c:forEach>
	</ul><br id="mybr"/>
	
	
	<ul class="nav nav-tabs" id="menu_2">
		<c:forEach items="${fns:getMapFromJsonArrayStr(menus)}" var="menu" varStatus="vs">
			<li  id="${id}${vs.index}" class="${menu.selected?'active':''}" ><a href="#${menu.href }" onclick="toUrl(this)">${menu.title }</a></li>
		</c:forEach>
	</ul>
	
	<br/>
	</div>
</c:if>