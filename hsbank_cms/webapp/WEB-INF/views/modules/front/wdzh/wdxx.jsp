<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/wdxx.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div id="tab_bar" style="padding-left:20px;">
				<li id="item_1" class="item"><span class="selected">全部消息</span></li>
				<li id="item_2" class="item"><span>系统消息</span></li>
				<li id="item_3" class="item"><span>充值提现</span></li>
				<li id="item_4" class="item"><span>融资消息</span></li>
				<li id="item_5" class="item"><span>活动消息</span></li>
				<li id="item_6" class="item"><span>项目消息</span></li>
			</div>
			<!-- 全部消息 -->
			<div id="record_list_1" class="show">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>全选</th>
							<th>类型</th>
							<th>内容</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>系统消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>系统消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>系统消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 系统消息 -->
			<div id="record_list_2" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>全选</th>
							<th>类型</th>
							<th>内容</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>系统消息</td>
							<td>bbbbbbbbbbbbbbbb</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>系统消息</td>
							<td>bbbbbbbbbbbbbbbbbbbbbbbbbbbb</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>系统消息</td>
							<td>bbbbbbbbbbbbbbbbbbbbbbbbb</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 充值提现 -->
			<div id="record_list_3" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>全选</th>
							<th>类型</th>
							<th>内容</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>充值提现</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>充值提现</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>充值提现</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 融资消息 -->
			<div id="record_list_4" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>全选</th>
							<th>类型</th>
							<th>内容</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>融资消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>融资消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>融资消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 活动消息 -->
			<div id="record_list_5" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>全选</th>
							<th>类型</th>
							<th>内容</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>活动消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>活动消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>活动消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 项目消息 -->
			<div id="record_list_6" class="hide">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>全选</th>
							<th>类型</th>
							<th>内容</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>项目消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>项目消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
						<tr>
							<td><div id="checkbox_brown"></div></td>
							<td>项目消息</td>
							<td>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td>
							<td>2015-01-01 12:12:12</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#tab_bar li').click(function(){
					if ($(this).children("span").hasClass("selected")) {
						return ;
					}
					$("#tab_bar li").each(function(){
				        if($(this).children("span").hasClass("selected")){
				            $(this).children("span").removeClass("selected");
				        }
				    });
					$(this).children("span").addClass("selected");
					if ($('#item_1 span').hasClass("selected")){
						$('#record_list_1').show();
						$('#record_list_2').hide();
						$('#record_list_3').hide();
						$('#record_list_4').hide();
						$('#record_list_5').hide();
						$('#record_list_6').hide();
					} else if ($('#item_2 span').hasClass("selected")){
						$('#record_list_1').hide();
						$('#record_list_2').show();
						$('#record_list_3').hide();
						$('#record_list_4').hide();
						$('#record_list_5').hide();
						$('#record_list_6').hide();
					}  else if ($('#item_3 span').hasClass("selected")){
						$('#record_list_1').hide();
						$('#record_list_2').hide();
						$('#record_list_3').show();
						$('#record_list_4').hide();
						$('#record_list_5').hide();
						$('#record_list_6').hide();
					} else if ($('#item_4 span').hasClass("selected")){
						$('#record_list_1').hide();
						$('#record_list_2').hide();
						$('#record_list_3').hide();
						$('#record_list_4').show();
						$('#record_list_5').hide();
						$('#record_list_6').hide();
					} else if ($('#item_5 span').hasClass("selected")){
						$('#record_list_1').hide();
						$('#record_list_2').hide();
						$('#record_list_3').hide();
						$('#record_list_4').hide();
						$('#record_list_5').show();
						$('#record_list_6').hide();
					} else if ($('#item_6 span').hasClass("selected")){
						$('#record_list_1').hide();
						$('#record_list_2').hide();
						$('#record_list_3').hide();
						$('#record_list_4').hide();
						$('#record_list_5').hide();
						$('#record_list_6').show();
					}
				});
			});
		</script>
	</body>
</html>