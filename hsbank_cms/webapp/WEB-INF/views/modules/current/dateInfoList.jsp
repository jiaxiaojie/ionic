<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日期信息管理</title>
	<meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link href='${ctxStatic}/fullcalendar-2.5.0/fullcalendar.css' rel='stylesheet' />
	<link href='${ctxStatic}/fullcalendar-2.5.0/fullcalendar.print.css' rel='stylesheet' media='print' />
	<script src='${ctxStatic}/fullcalendar-2.5.0/lib/moment.min.js'></script>
	<script src='${ctxStatic}/fullcalendar-2.5.0/lib/jquery.min.js'></script>
	<script src='${ctxStatic}/fullcalendar-2.5.0/fullcalendar.min.js'></script>
	<script src='${ctxStatic}/fullcalendar-2.5.0/lang-all.js'></script>
<script>
		$(document).ready(function() {
			//日历显示字体
			var currentLangCode = 'zh-cn';
			// build the language selector's options
			$.each($.fullCalendar.langs, function(langCode) {
				$('#lang-selector').append(
					$('<option/>')
						.attr('value', langCode)
						.prop('selected', langCode == currentLangCode)
						.text(langCode)
				);
			});

			// rerender the calendar when the selected option changes
			$('#lang-selector').on('change', function() {
				if (this.value) {
					currentLangCode = this.value;
					$('#calendar').fullCalendar('destroy');
					renderCalendar();
				}
			});
			
			
			$('#calendar').fullCalendar({
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month,agendaWeek,agendaDay'
				},
				  //点击day触发
				 dayClick: function(date, allDay, jsEvent, view){
				       var dates =date.format();
				          $.ajax({
				        	  url:"${ctx}/current/dateInfo/updateisworkday?date="+dates,
				        	  async:false
				           })
				           $('#calendar').fullCalendar('refetchEvents');
				          	$("#message").empty();
				           $("#message").append('<div style="display: block;" id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><div>修改成功</div></div>');
				        }, 
				lang: currentLangCode,
				/* defaultDate: '2015-12-12', */
				editable: true,
				eventLimit: true, // allow "more" link when too many events
				events: {
					url: '${ctx}/current/dateInfo/queryByDate',
					error: function() {
						$('#script-warning').show();
					}
				},
				loading: function(bool) {
					
					$('#loading').toggle(bool);
				}
			});
			
		});
		
</script>
<style>
	body {margin: 40px 10px;padding: 0;font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;font-size: 14px;}
	#top{width=1500px;height=800px;}
    #intruduce{width:200px;height:200px;}
    #int1{width:100px;height:60px;background-color: #FF9F89;}
     #int2{width:100px;height:60px;background-color: #FFF68F;}
	#calendar {max-width: 900px;margin: 0 auto;}
</style>
</head>
<body>
<div id="message"></div>
<table id="top">
  <tr>
    <td id="iswork">
     <div id="intruduce">
      <div id="int1">工作日</div>
      &nbsp;
      <div id="int2">休息</div>
     </div>
   </td>
    <td>
	   <div id='calendar'></div>
    </td>
 </tr>
</table>
</body>
</html>
