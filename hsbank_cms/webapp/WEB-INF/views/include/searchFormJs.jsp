<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%--这个 js 的作用是当从查询页面跳走然后跳回来时，查询参数还在 --%>
<script type="text/javascript">

/**
 * 设置回到查询界面的参数 ，如果第一个menu跟查询界面第一个menu的href相同，则在后面设置参数。
 */
function setBackHref(){
	var menus = $("ul[class='nav nav-tabs']").first().find("li");
	 if(menus.length==2){
		 var a = menus.first().find("a").first();
		 
		 if(a.attr("href").split("?")[0]=='${modelMenus.searchHref}'){
			 var newHref = addParams(a.attr("href"),"${fns:urlDecode(modelMenus.jumpForSearchFormParams)}");
			 var urlAndParams = newHref.split("?");
			 a.attr("href","javascript:post('"+urlAndParams[0]+"','"+urlAndParams[1]+"')");//
		 }
		
	 }
	 
}

function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";
   
    var keyValues = PARAMS.split("&");
    
    for (var i=0;i<keyValues.length;i++ ) {
    	
        var opt = document.createElement("textarea");
        var keyValue = keyValues[i].split("=");
        //alert(keyValue);
        opt.name = keyValue[0];        
        opt.value = keyValue[1];
        //alert(keyValue[1]);
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}   

/*
 * 设置跳转参数，将跳回参数作为参数附带在herf上。
 	searchHref：查询href
 	jumpForSearchFormParams：跳转附带的查询参数
 */
function setJumpParams(){
	var menu = $("ul[class='nav nav-tabs']").first().find("li").first();
	var firstA = menu.find("a").first();
	
	
	
	 //如果有一个查询form，且第一个menu是被选中的，则设置根据查询form设置新的【跳转附带的查询参数】和【searchHref（默认是查询页面第一个menu的href，不带参数部分）】到页面a标签herf后面（第一个menu除外）
	 if($("#searchForm").length==1 && menu.attr("class")=="active"){
		 
		 var params = serializeForm('searchForm');  
		 var searchHref = firstA.attr("href").split("?")[0];
		 
		 $("a").not(firstA).each(function(){
			 var href = $(this).attr("href")
			 if(href != null && href != ""){
				 $(this).attr("href",addParams(href,"searchHref="+searchHref+"&"+"jumpForSearchFormParams="+encodeURIComponent(params)));
			 }
		});
	 }//否则，如果有【跳转附带的查询参数】，且第一个menu没有被选中，则将前面页面带来的【跳转附带的查询参数】和【查询href】设置到页面a标签herf后面（第一个menu除外）
	 else if('${modelMenus.jumpForSearchFormParams}' != '' && menu.attr("class")!="active"){
		
		 $("a").not(firstA).each(function(){
			 var href = $(this).attr("href")
			 
			 if(href != null && href != ""){
				
				 var newHref2 = addParams(href,"searchHref="+"${modelMenus.searchHref}"+"&"+"jumpForSearchFormParams="+'${modelMenus.jumpForSearchFormParams}');
				
				 $(this).attr("href",newHref2);
			 }
		});
	 }
}

$(document).ready(function() {
	//页面初始化后调用下面两个方法，设置定时执行，因为有些页面是由js调用产生的，我们要保证以下js是在页面初始化完成后被执行
	window.setTimeout("setBackHref()", "100");
	window.setTimeout("setJumpParams()", "100");
	 
});


</script>

