<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/zhgl_grjcxx.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			var changeBaseInfo = function(){
				$("#divBaseInfo").hide();
				$("#divChangeBaseInfo").show();
			}
			var cancelChangeBaseInfo = function(){
				$("#divChangeBaseInfo").hide();
				$("#divBaseInfo").show();
			}
			var saveSubmit = function(){
				$("#inputForm").submit();
			}
			$(function(){
				$("#inputForm").validate({
					errorPlacement: function(error, element) {
						error.insertBefore(element);
					}
				});
			});
		</script>
	</head>
	<body>
    	<div class="bg_789_top"></div>
		<div id="content_layout" class="bg_area_004">
			<div class="wdzh_right_title">
				<span>个人基础信息</span>
		    </div>
		    <div class="div_height_20"></div>
            <!--点击“修改信息”按钮后隐藏下面的内容，显示“modification”里的内容-->
			<div id="divBaseInfo" style="display:block">
            	<div class="user_info clearfix">
                	<div class="left">
                    	<div class="avatar_area">
                        	<a href="#" class="modify_avatar">修改头像</a>
                            <img id="avatar" src="${pageContext.request.contextPath}${avatar_image}" title="点击更换头像" alt="点击更换头像"/>
                        </div>
                    </div>
                	<div class="center">
                    	<dl class="clearfix">
                        	<dt><span class="red-text">*</span>昵称</dt>
                            <c:choose>
                            	<c:when test="${empty customerAccount.nickname }">
                            <dd>${customerAccount.accountName }</dd>
                            <dd class="text_tip"><a title="去设置" href="${ctxFront }/customer/account/safeCenter">您还未设置过昵称</a></dd>
                            	</c:when>
                            	<c:otherwise>
                            <dd>${customerAccount.nickname }</dd>
                            	</c:otherwise>
                            </c:choose>
                        </dl>
                    	<dl class="clearfix">
                        	<dt><span class="red-text">*</span>手机号码</dt>
                            <dd>${customerBase.mobile }</dd>
                            <dd class="text_tip fl"><i class="icon_check_blue"></i><a href="${ctxFront }/customer/account/safeCenter" title="修改手机号">已认证</a></dd>
                        </dl>
                    	<dl class="clearfix">
                        	<dt><span class="red-text">*</span>邮箱</dt>
                            <dd>${customerBase.email }</dd>
                            <dd class="text_tip fl">
                            <c:choose>
                            	<c:when test="${customerBase.emailAuthCode eq '1' }">
                           	<i class="icon_check_blue"></i><a href="${ctxFront }/customer/account/safeCenter" title="修改邮箱">已认证</a>
                            	</c:when>
                            	<c:otherwise>
                           	<i class="icon_check_gray"></i><a href="${ctxFront }/customer/account/safeCenter">去认证</a>
                            	</c:otherwise>
                           	</c:choose>
                            </dd>
                        </dl>
                    </div>
                	<div class="right"><a href="javascript:void(0);" onclick="changeBaseInfo()" class="btn_brown">修改信息</a></div>
              	</div>
                <div class="user_info user_info_list no_border_bottom">
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>真实姓名</dt>
                        <dd>${customerBase.customerName }</dd>
                        <dd class="text_tip fr">
                        <c:choose>
                        	<c:when test="${customerAccount.hasOpenThirdAccount eq '1' }">
                       	<i class="icon_check_blue"></i>已认证
                        	</c:when>
                        	<c:otherwise>
                       	<i class="icon_check_gray"></i><a href="${ctxFront }/customer/thirdAccount/open">去认证</a>
                        	</c:otherwise>
                       	</c:choose>
                        </dd>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>身份证号</dt>
                        <dd>${customerBase.certNum }</dd>
                        <dd class="text_tip fr">
                        <c:choose>
                        	<c:when test="${customerAccount.hasOpenThirdAccount eq '1' }">
                       	<i class="icon_check_blue"></i>已认证
                        	</c:when>
                        	<c:otherwise>
                       	<i class="icon_check_gray"></i><a href="${ctxFront }/customer/thirdAccount/open">去认证</a>
                        	</c:otherwise>
                       	</c:choose>
                        </dd>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>生日</dt>
                        <dd>${customerBase.getBirthday() }</dd>
                        <c:if test="${empty customerBase.certNum }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>性别</dt>
                        <dd>${fns:getDictLabel(customerBase.genderCode, 'sex', '')}</dd>
                        <c:if test="${empty customerBase.genderCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>最高学历</dt>
                        <dd>${fns:getDictLabel(customerBase.educationCode, 'education_dict', '')}</dd>
                        <c:if test="${empty customerBase.educationCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">  </span>毕业院校</dt>
                        <dd>${customerBase.educationSchool }</dd>
                        <c:if test="${empty customerBase.educationSchool }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>婚姻状况</dt>
                        <dd>${fns:getDictLabel(customerBase.marriageCode, 'customer_marriage', '')}</dd>
                        <c:if test="${empty customerBase.marriageCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>居住地址</dt>
                        <dd>${customerBase.address }</dd>
                        <c:if test="${empty customerBase.address }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>公司性质</dt>
                        <dd>${fns:getDictLabel(customerWork.companyTypeCode, 'customer_company_type', '')}</dd>
                        <c:if test="${empty customerWork.companyTypeCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>公司规模</dt>
                        <dd>${fns:getDictLabel(customerWork.companySizeCode, 'customer_company_size', '')}</dd>
                        <c:if test="${empty customerWork.companySizeCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>职位</dt>
                        <dd>${customerWork.position }</dd>
                        <c:if test="${empty customerWork.position }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>年收入</dt>
                        <dd>${fns:getDictLabel(customerWork.incomeCode, 'customer_income', '')}</dd>
                        <c:if test="${empty customerWork.incomeCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>             
                </div>
			</div>
            
            <!--点击“修改信息”按钮后显示下面的内容，隐藏“content_center”里的内容-->
            <div id="divChangeBaseInfo" class="content_center" style="display:none">
            	<form:form id="inputForm" modelAttribute="customerChangeInfo" action="${ctxFront}/customer/account/save" method="post">
            		<form:hidden path="accountId"/>
            		<form:hidden path="customerId"/>
            	<div class="user_info clearfix">
                	<div class="left">
                    	<div class="avatar_area">
                        	<a href="#" class="modify_avatar">修改头像</a>
                        	<img id="avatar1" src="${pageContext.request.contextPath}${avatar_image}" title="点击更换头像" alt="点击更换头像"/>
                        </div>
                    </div>
                	<div class="center">
                    	<dl class="clearfix">
                    		<dt><span class="red-text">*</span>昵称</dt>
                            <c:choose>
                            	<c:when test="${empty customerAccount.nickname }">
                            <dd>${customerAccount.accountName }</dd>
                            <dd class="text_tip"><a title="去设置" href="${ctxFront }/customer/account/safeCenter">您还未设置过昵称</a></dd>
                            	</c:when>
                            	<c:otherwise>
                            <dd>${customerAccount.nickname }</dd>
                            	</c:otherwise>
                            </c:choose>
                        </dl>
                    	<dl class="clearfix">
                        	<dt><span class="red-text">*</span>手机号码</dt>
                            <dd>${customerBase.mobile }</dd>
                            <dd class="text_tip fl"><i class="icon_check_blue"></i><a href="${ctxFront }/customer/account/safeCenter" title="修改手机号">已认证</a></dd>
                        </dl>
                    	<dl class="clearfix">
                        	<dt><span class="red-text">*</span>邮箱</dt>
                            <dd>${customerBase.email }</dd>
                            <dd class="text_tip fl">
                            <c:choose>
                            	<c:when test="${customerBase.emailAuthCode eq '1' }">
                           	<i class="icon_check_blue"></i><a href="${ctxFront }/customer/account/safeCenter" title="修改邮箱">已认证</a>
                            	</c:when>
                            	<c:otherwise>
                           	<i class="icon_check_gray"></i><a href="${ctxFront }/customer/account/safeCenter">去认证</a>
                            	</c:otherwise>
                           	</c:choose>
                            </dd>
                        </dl>
                    </div>
                	<div class="right"><a href="javascript:void(0);" onclick="cancelChangeBaseInfo()" class="btn_brown">取消修改</a></div>
              	</div>
                <div class="user_info user_info_list no_border_bottom">
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>真实姓名</dt>
                        <dd>${customerBase.customerName }</dd>
                        <dd class="text_tip fr">
                        <c:choose>
                        	<c:when test="${customerAccount.hasOpenThirdAccount eq '1' }">
                       	<i class="icon_check_blue"></i>已认证
                        	</c:when>
                        	<c:otherwise>
                       	<i class="icon_check_gray"></i><a href="${ctxFront }/customer/thirdAccount/open">去认证</a>
                        	</c:otherwise>
                       	</c:choose>
                        </dd>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>身份证号</dt>
                        <dd>${customerBase.certNum }</dd>
                        <dd class="text_tip fr">
                        <c:choose>
                        	<c:when test="${customerAccount.hasOpenThirdAccount eq '1' }">
                       	<i class="icon_check_blue"></i>已认证
                        	</c:when>
                        	<c:otherwise>
                       	<i class="icon_check_gray"></i><a href="${ctxFront }/customer/thirdAccount/open">去认证</a>
                        	</c:otherwise>
                       	</c:choose>
                        </dd>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>生日</dt>
                        <dd>${customerBase.getBirthday() }</dd>
                        <c:if test="${empty customerBase.certNum }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>性别</dt>
                        <dd>${fns:getDictLabel(customerBase.genderCode,"sex","")}</dd>
                        <form:hidden path="genderCode" />
                        <c:if test="${empty customerBase.genderCode }">
                        <dd class="text_tip fr"><i class="icon_check_gray"></i>未设置</dd>
                        </c:if>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>最高学历</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.educationCodeCanChange }">
                       	<form:hidden path="educationCode" />
                        <dd>${fns:getDictLabel(customerChangeInfo.educationCode,"education_dict","")}</dd>
	                        	</c:when>
	                        	<c:otherwise>
                       	<dd class="text_tip fr w528">
                        	<form:select path="educationCode" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('education_dict')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
                        </dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">  </span>毕业院校</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.educationSchoolCanChange }">
                       	<form:hidden path="educationSchool" />
                        <dd>${customerChangeInfo.educationSchool }</dd>
	                        	</c:when>
	                        	<c:otherwise>
                       	<dd class="text_tip fr w528"><input name="educationSchool" value="${customerChangeInfo.educationSchool }" type="text" class="w256 " maxlength="15"></dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>婚姻状况</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.marriageCodeCanChange }">
                       	<form:hidden path="marriageCode" />
                        <dd>${fns:getDictLabel(customerChangeInfo.marriageCode,"customer_marriage","")}</dd>
	                        	</c:when>
	                        	<c:otherwise>
                        <dd class="text_tip fr w528">
                            <p>
                                <form:radiobuttons path="marriageCode" items="${fns:getDictList('customer_marriage')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
                            </p>
                        </dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>
                    <dl class="clearfix">
                      <dt><span class="red-text">*</span>居住地址</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.addressCanChange }">
                       	<form:hidden path="address" />
                        <dd>${customerChangeInfo.address }</dd>
	                        	</c:when>
	                        	<c:otherwise>
                        <dd class="text_tip fr w528"><input name="address" value="${customerChangeInfo.address }" type="text" class="w500 " maxlength="100"></dd>
	                        	</c:otherwise>
	                        </c:choose>
                  </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>公司性质</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.companyTypeCodeCanChange }">
                       	<form:hidden path="companyTypeCode" />
                        <dd>${fns:getDictLabel(customerChangeInfo.companyTypeCode,"customer_company_type","")}</dd>
	                        	</c:when>
	                        	<c:otherwise>
                        <dd class="text_tip fr w528">
                        	<form:select path="companyTypeCode" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('customer_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
                      	</dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>公司规模</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.companySizeCodeCanChange }">
                       	<form:hidden path="companySizeCode" />
                        <dd>${fns:getDictLabel(customerChangeInfo.companySizeCode,"customer_company_size","")}</dd>
	                        	</c:when>
	                        	<c:otherwise>
                        <dd class="text_tip fr w528">
                        	<form:select path="companySizeCode" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('customer_company_size')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
                      	</dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>职位</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.positionCanChange }">
                       	<form:hidden path="position" />
                        <dd>${customerChangeInfo.position }</dd>
	                        	</c:when>
	                        	<c:otherwise>
                        <dd class="text_tip fr w528"><input name="position" value="${customerChangeInfo.position }" type="text" class="w256 " maxlength="15"></dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>
                    <dl class="clearfix">
                        <dt><span class="red-text">*</span>年收入</dt>
                        	<c:choose>
	                        	<c:when test="${!customerChangeInfo.incomeCodeCanChange }">
                       	<form:hidden path="incomeCode" />
                        <dd>${fns:getDictLabel(customerChangeInfo.incomeCode,"customer_income","")}</dd>
	                        	</c:when>
	                        	<c:otherwise>
                        <dd class="text_tip fr w528">
                        	<form:select path="incomeCode" disabled="${!customerChangeInfo.incomeCodeCanChange }" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('customer_income')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
                      	</dd>
	                        	</c:otherwise>
	                        </c:choose>
                    </dl>             
                </div>
                
                <div class="btn_group_one"><a href="javascript:void(0);" onclick="saveSubmit()" class="btn_brown_158x31">保存</a></div>
                </form:form>
			</div>
            
			<div class="div_height_50"></div>
			<div class="bottom"></div>
			<script type="text/javascript">
	    		$(document).ready(function() {
					$(document).on('click', '#avatar', function() {
						window.location.href="${ctxFront}/customer/account/change_avatar";
					});
					$(document).on('click', '#avatar1', function() {
						window.location.href="${ctxFront}/customer/account/change_avatar";
					});
				});
			</script>
		</div>
        <div class="bg_789_bottom"></div>
	</body>
</html>