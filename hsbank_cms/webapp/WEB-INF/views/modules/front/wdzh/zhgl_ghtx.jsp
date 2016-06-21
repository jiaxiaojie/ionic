<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_ghtx.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/jcrop/css/jquery.Jcrop.min.css" rel="stylesheet"/>
		<script src="${ctxStatic}/jcrop/js/jquery.Jcrop.min.js" type="text/javascript"></script>
		<title></title>
		<script type="text/javascript">
			var baseUrl = "<%=baseUrl%>";
			//头像裁剪api
		 	var jcrop_api;
			var bound_x, bound_y;
		</script>
	</head>
	<body>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="line_01">
				<span class="span_text_1">更换头像</span>
			</div>
			<hr/>
			<!-- 上传图片 begin -->
			<form id="form_upload" class="form-horizontal" action="${pageContext.request.contextPath}/upload/file"  method="post" enctype="multipart/form-data" target="upload_iframe">
				<div class="div_upload_file">
					<div class="left_area">
						<div class="line_02">
							<span class="form_label"><span>请选择头像图片：</span><span class="required">&nbsp;&nbsp;</span></span>
							<span class="span_text_2">(文件大小不能超过1M)</span>
						</div>
						<div class="line_02">
							<span class="form_label">&nbsp;&nbsp;</span>
			            	<input id="upload_file" name="upload_file" type="file" style="width:255px;border:none;" onchange="reSelectFile()">
			            </div>
			            <div class="line_03">
			            	<span class="help-inline tip" id="tip_upload_file"></span>
							<div id="progress" style="background:#728820;height:3px;width:0;font-size:11px;display:none;"></div>
							<div id="upload_status" style="display:none"></div>
							<input type="hidden" id="max_size" name="max_size" value="1048576">
		            		<input type="hidden" id="upload_batch_flag">
							<iframe name="upload_iframe" width="0" height="0" style="display:none"></iframe>				
			            </div>
						<div class="div_height_20"></div>
						<div id="bt_upload" class="bt_orange_134x31" style="margin-left:180px;">上传文件</div>
					</div>
					<div class="right_area">
						<img id="avatar" class="img" src="${pageContext.request.contextPath}${avatar_image}"/>
					</div>
				</div>
			</form>
			<!-- 上传图片 end -->
			<form id="form_cut" class="form-horizontal" style="margin-left:20px;display:none;" method="post">
				<div style="overflow:auto:margin-left:20px;max-width:710px;">
					<p style="color:red;height:30px;">提示：双击裁剪框，可以自动保存头像</p>
					<img id="source_image"/>
				</div>
				<input type="hidden" id="source_path_name" name="source_path_name" />
		        <input type="hidden" id="x" name="x" />
		        <input type="hidden" id="y" name="y" />
		        <input type="hidden" id="w" name="w" />
		        <input type="hidden" id="h" name="h" />
		        <input type="hidden" id="root_path" name="root_path" />
		        <div class="div_height_20"></div>
		        <div>
			        <div id="bt_save" class="bt_orange_134x31" style="float:left;margin-left:200px;">保存头像</div>
			        <div id="bt_cancel" class="bt_brown_134x31" style="float:right;margin-right:250px;">取消</div>
		        </div>
		    </form>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
		<script type="text/javascript">
		    $(document).ready(function() {
				$(document).on('click', '#bt_upload', function() {
					uploadFile();
				});
				
				$('#bt_save').click(function(){
					saveAvatar();
		      	});
				
				$('#bt_cancel').click(function(){
					location.reload();
		      	});
		    });
		    
		    //上传完成标识
			var isFinished = false;
		    //刷新进度条的间隔时间(ms)
			var interval;
		    //上传文件
			function uploadFile(){
				reset();
				if($("#upload_file").val() == ""){
					 $("#tip_upload_file").html("请选择你要上传的文件！").show().fadeOut(5000);
					 return false;  
				}
				var pathName = $("#upload_file").val();
				var suffix = pathName.substring(pathName.length - 4, pathName.length);
				if(suffix != ".jpg" && suffix != ".JPG" && suffix != ".png" && suffix != ".PNG" && suffix != ".gif" && suffix != ".GIF") {   
					 $("#tip_upload_file").html("文件必须为jpg、png或gif格式").show().fadeOut(5000);
		    		return false;  
				} 
		         //刷新进度条
				interval = setInterval(refresh, 1000);
				form_upload.submit();
			}
			
			//文件重新选择
		    function reSelectFile(){
		    	reset();
		    	$("#bt_upload").attr("disabled", false);
		    }
			
			//重置
			function reset() {
				clearInterval(interval);
				isFinished = false;
				$("#tip_upload_file").html("");
				$("#upload_status").html("");
				$("#upload_status").hide();
				$("#progress").css("width","0");
				$("#progress").css("width","0%");
				$("#progress").hide();
				$("#upload_batch_flag").val("");
				$("#bt_upload").attr("disabled",true);
			}
		    
		    //刷新进度条
			function refresh(){
				//如果上传已经完成
				if(isFinished){
					reset();
					return;
				}
				var url = "${pageContext.request.contextPath}/upload/progress";
				$.get(url, function(data){
					if (data.error == 'error') {
						reset();
						$("#tip_upload_file").html("文件大小不能超过1M，请检查！").show().fadeOut(5000);
						return;
					} else {
						$("#upload_status").html("正在上传... "+ data.percent + "%，已用时：" + data.time + "秒，剩余时间：" + data.remainingTime + "秒，速度：" + data.speed + "byte/s").show();
						$("#progress").animate({width:data.percent + "%"},500);
						if (data.uploadedSize == data.totalSize) {
							isFinished = true;
							$("#bt_upload").attr("disabled", false);
							$("#upload_status").html("上传已完成，100%，已用时：" + data.time + "秒，剩余时间：" + data.remainingTime + "秒，速度：" + data.speed + "byte/s").show().fadeOut(5000);
				            //上传图片
				            $("#source_image").attr("src", data.newFileUrl);
				            $("#source_path_name").val(data.rootDir + data.newFilePathName);
				            $("#root_path").val(data.rootDir);
				            //------------初始化头像裁剪----------------
				            $('#source_image').Jcrop({
				            	allowSelect:false,
				            	minSize: [10,10],
				                onChange: updateCoords,
				                onSelect: updateCoords,
				                onDblClick: saveAvatar,
				                //保持宽高比例
				                aspectRatio: 1
				             },function(){
				            	 var bounds = this.getBounds();
				                 bound_x = bounds[0];
				                 bound_y = bounds[1];
				                 jcrop_api = this;
				                 jcrop_api.setSelect([0, 0, bound_x > 200 ? 200 : bound_x, bound_y > 200 ? 200 : bound_y]);
				              });
				            //-----------------------------------------
				            $("#form_upload").hide();
				            $("#form_cut").show();
						}
			            //批次标识
			            if ($("#upload_batch_flag").val() == "") {
			                $("#upload_batch_flag").val(data.batchFlag);
			            }
					}
				}, "json");	
			}
		    
			/**更新坐标*/
		    function updateCoords(c) {
		        $('#x').val(c.x);
		        $('#y').val(c.y);
		        $('#w').val(c.w);
		        $('#h').val(c.h);
		    }
			
			/**保存头像*/
			function saveAvatar() {
				$.ajax({
					type : 'post',
					url  : '${ctxFront}/customer/account/save_avatar',
					data : {
						source_path_name: $("#source_path_name").val(),
						source_width:$("#source_image").width(),
						source_height:$("#source_image").height(),
						x: $("#x").val(),
						y: $("#y").val(),
						w: $("#w").val(),
						h: $("#h").val(),
						root_path: $("#root_path").val(),
					},
					dataType : 'json',
					success : function(data) {
	          			//alert("头像更换成功");
	          			location.reload();
	        		}
	        	});
			}
		</script>
	</body>
</html>