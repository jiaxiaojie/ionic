<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="my_account"/>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/tipso.css" /><!--提示框样式-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/tzgl_wdzq.css?${version }" />
<script type="text/javascript" src="${ctxStatic}/modules/front/js/tipso.js"></script><!--提示框JS-->
<title></title>
</head>
<body>

<div class="bg_789_top"></div>
<div class="wdzh-main">
    
    <div id="tab_bar">
        <li id="item_1" onClick="selectItem(1)" class="item"><span>持有中</span></li>
        <li id="item_2" onClick="selectItem(2)" class="item"><span>转出中</span></li>
        <li id="item_3" onClick="selectItem(3)" class="item"><span>投标中</span></li>
        <li id="item_4" onClick="selectItem(4)" class="item"><span class="selected">转入中</span></li>
        <li id="item_6" onClick="selectItem(5)" class="item"><span>已转出</span></li>
        <li id="item_6" onClick="selectItem(6)" class="item"><span>已结束</span></li>
    </div>
    
    <div class="list_info clearfix">
    	<div class="fl">转入中：债权转让且未付款的。</div>
    	<div class="fr"></div>
    </div>
    <table class="table table-hover table_list_1">
        <thead>
            <tr>
                <th width="17%">项目名称</th>
                <th width="9%">年化利率</th>
                <th width="9%">转入金额</th>
                <th width="9%">待收本息</th>
                <th width="10%">交易费用</th>
                <th width="10%">使用现金券</th>
                <th width="12%">平台垫付金额</th>
                <th width="11%">应付金额</th>
                <th width="13%">操作</th>
            </tr>
        </thead>
        <tbody>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内--><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
            <tr>
                <td>车辆周转贷车辆周转</td><!--标题字数限制在9个以内-->
                <td>12%</td>
                <td>500元</td>
                <td>500元</td>
                <td>50元</td>
                <td>500元</td>
                <td>500元</td>
                <td>500元</td>
                <td class="operate_area">
                	<a href="javascript:;" class="icon_fk" title="立即付款"></a>
                	<a href="javascript:;" class="icon_qx" title="取消"></a><!--点击弹出“取消”窗口-->
                	<a href="javascript:;" class="icon_jh" title="还款计划"></a><!--点击弹出“还款计划”窗口-->
                </td>
            </tr>
            
        </tbody>
    </table>
    
</div>


<!--点击“取消”按钮后弹出此内容 弹窗 默认为display:none，显示出来为display:block-->
<div class="pop_bg" style="display:none">
	<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
    <div class="pop_main" style=" width:530px; height:178px; margin-left:-265px; margin-top:-79px;">
        <div class="pop_title">你确认取消这笔投资吗？<a href="javascript:;" class="close_pop"></a></div>
        <div class="pop_content">
            <div class="btn_group_one">
                <a href="javascript:;" class="btn_brown_158x31">确认取消</a>
                <a href="javascript:;" class="btn_blue_158x31">立即付款</a>
            </div>
        </div>
    </div>
</div>

<!--还款计划 弹窗 默认为display:none，显示出来为display:block-->
<div class="pop_bg" style="display:none">
	<!--弹窗垂直、水平居中的方法：margin-left的数字是弹窗的高度除以2得来的，margin-top的数字是弹窗的宽度除以2得来的。-->
    <div class="pop_main" style=" width:690px; height:520px; margin-left:-345px; margin-top:-260px;">
        <div class="pop_title">还款计划表<a href="javascript:;" class="close_pop"></a></div>
        <div class="pop_content">
        
        	<dl class="plan_1 clearfix">
            	<dt>车辆周转贷 99464-1-1</dt>
                <dd class="investment">投资金额：<span>100000</span>元</dd>
                <dd class="earnings">投资到期可获得总收益：<span>1136.16</span>元</dd>
            </dl>
            
        	<div class="plan_2">
            	<div class="dumascroll" style="width:650px; height:324px; margin:auto">
                	<div id="col">
                    	<table border="0" class="table table-hover">
                    
                      <thead>
                        <tr>
                            <th width="231">还款日期</th>
                            <th width="220">应收本金（元）</th>
                            <th width="183">应收利息（元）</th>
                        </tr>
                      </thead>
                      
                      <tbody>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                        <tr>
                            <td>31天</td>
                            <td>500</td>
                            <td>500</td>
                        </tr>
                      </tbody>
                      
                    </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="bg_789_bottom"></div>

<!--提示框JS-->
<script type="text/javascript">
$(function() {

	// 1
	$('.tip1').tipso({
		useTitle: false
	});
	
});
function selectItem(index){
	if(index==1){window.location="${ctxFront}/customer/investment/project_cyz";}
	if(index==2){window.location="${ctxFront}/customer/investment/project_zcz";}
	if(index==3){window.location="${ctxFront}/customer/investment/project_tbz";}
	if(index==4){window.location="${ctxFront}/customer/investment/project_zrz";}
	if(index==5){window.location="${ctxFront}/customer/investment/project_yzc";}
	if(index==6){window.location="${ctxFront}/customer/investment/project_yjs";}
}
</script>

</body>
</html>
