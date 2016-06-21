<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="decorator" content="help_center"/>
	<link href="${ctxStatic}/modules/front/css/help_center.css?${version }" rel="stylesheet"/>
	<title></title>
</head>
<body>
<div id="content_bottom" class="help_content">
        	
<div class="help_main">
   	
    <div class="right_title"><span>信用等级与额度</span></div>
   
   	<div class="content">
           <div class="panel-group" id="accordion">
           
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>什么是信用分数和信用等级？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse1" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>用户的信用分数是在通过花生金服审核后获得的，信用等级由信用分数转化而来，每个信用等级都对应的信用分数范围，信用分数和信用等级是融资人的信用属性，也是投资人判断融资人违约风险的重要依据之一。通常来讲融资人信用等级越高，其相应的费用越低，相应的借款成功率越高。</p>
                   <p>具体请参考信用等级的分数区间：</p>
                   <table class="table">
                   	<thead>
                       	<tr>
                           	<th>EX</th>
                           	<th>A+</th>
                           	<th>A</th>
                           	<th>B+</th>
                           	<th>B</th>
                           	<th>C+</th>
                           	<th>C</th>
                           	<th>D+</th>
                           	<th>D</th>
                           	<th>E</th>
                           </tr>
                       </thead>
                   	<tbody>
                       	<tr>
                           	<td>100-96</td>
                           	<td>95-91</td>
                           	<td>90-86</td>
                           	<td>85-81</td>
                           	<td>80-76</td>
                           	<td>75-71</td>
                           	<td>70-66</td>
                           	<td>65-61</td>
                           	<td>60-56</td>
                           	<td>低于55</td>
                           </tr>
                       </tbody>
                   </table>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>什么是信用额度？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse2" class="panel-collapse collapse">
                 <div class="panel-body">
                 	<p>用户的信用额度是在通过花生金服审核员对所提供材料的审核后获得的，既是融资人单笔借款的上限也是借款者累积尚未还清借款的上限。</p>
                   <p>例如，如果一个融资人信用额度为1万元，则在没有其他借款的情况下，用户可发布总额最高为1万元的借款请求。也可以分多次发布借款请求，但尚未还清借款（以整笔借款金额计算）的总额不得超过1万元。</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>如何获得信用分数、信用等级与信用额度？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse3" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>请您申请借款并上传认证资料，审核完成后，您将获得相应的信用分数、信用等级和信用额度。</p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>如何查看我的信用分数、信用等级与信用额度？<i class="icon_plus"></i></a>
                 </h4>
               </div>
               <div id="collapse4" class="panel-collapse collapse">
                 <div class="panel-body">
                   <p>登陆花生金服网站，打开【我的账户】--【账户管理】--【认证信息】页面，即可查看您的信用分数、信用等级、信用额度，及您进行的各项信用认证的情况。<a href="" class="btn_text_gold">查看操作流程>></a></p>
                 </div>
               </div>
             </div>
             
             <div class="panel panel-default">
               <div class="panel-heading">
                 <h4 class="panel-title">
                   <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse6" class="collapsed"><em class="icon_arrow_right"></em>如何提高我的信用额度？<i class="icon_plus"></i></a>
                  </h4>
                </div>
                <div id="collapse6" class="panel-collapse collapse">
                  <div class="panel-body">
                    <dl>
                    	<dt>提额申请资格说明</dt>
                        <dd>您之前已获得信用额度，且至少已成功借款一笔，并正常还款六个月以上，目前您名下的借款已全部结清，无在还借款。</dd>
                    </dl>
                    <dl>
                    	<dt>提额申请办法</dt>
                        <dd>如果您目前的状况满足上述情况，请您重新发布借款申请，申请金额请按照实际需求填写，不限定在已获得的信用额度范围之内。</dd>
                    </dl>
                  </div>
                </div>
              </div>
              
              
                   
            </div>
        </div>
        
    </div>
    
</div>
<script type="text/javascript">
	function collapseClick(element) {
		if($(element).hasClass("collapsed")) {
			$(element).find("i").removeClass("icon_plus").addClass("icon_minus");
		}else {
			$(element).find("i").removeClass("icon_minus").addClass("icon_plus");
		}
	}
</script>
</body>
</html>
