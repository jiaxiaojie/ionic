<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>操作成功</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
	var getJsonData = function() {
		var url = "${ctxFront}/test/acceptBasicDataAndReturnJson";
		var data = {};
		data.customerId = 3;
		data.customerName = 'bbb';
		$.get(url,data,function(data){
			$("#jsonData").text(data);
		})
	}
	var getXmlData = function() {
		var url = "${ctxFront}/test/acceptBasicDataAndReturnXml";
		var data = {};
		data.customerId = 3;
		data.customerName = 'bbb';
		$.get(url,data,function(data){
			$("#xmlData").text(data);
		})
	}
	var getCollectionXmlData = function() {
		var url = "${ctxFront}/test/acceptBasicDataAndReturnCollectionXml";
		var data = {};
		data.accountId = 23;
		$.get(url,data,function(data){
			$("#collectionXmlData").text(data);
		})
	}
	</script>
</head>
<body>
	<a href="javascript:void(0)" onclick="getJsonData()">获取json数据</a>path:f/test/acceptBasicDataAndReturnJson?customerId=3&customerName=bbb
	<div id="jsonData" style="color:blue;height:150px;margin:50px 0 50px 0;"></div>
	<a href="javascript:void(0)" onclick="getXmlData()">获取xml数据</a>path:f/test/acceptBasicDataAndReturnXml?accountId=23
	<div id="xmlData" style="color:blue;height:150px;margin:50px 0 50px 0;"></div>
	<a href="javascript:void(0)" onclick="getCollectionXmlData()">获取collection xml数据</a>path:f/test/acceptBasicDataAndReturnCollectionXml?accountId=23
	<div id="collectionXmlData" style="color:blue;height:150px;margin:50px 0 50px 0;"></div>
</body>