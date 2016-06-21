/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTransactionNotify;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;

/**
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "/yeepay/direct/notify")
public class YeepayDirectNotifyController extends BaseController {
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	
	/**
	 * 直接转账notify操作
	 * 		更新logThirdParty表notify数据
	 * @param request
	 * @return
	 */
	@RequestMapping("directTransaction")
	@ResponseBody
	public String directTransaction(HttpServletRequest request) {
		String notify = request.getParameter("notify");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if(SignUtil.verifySign(notify, sign)) {
			DirectTransactionNotify bean=JaxbMapper.fromXml(notify, DirectTransactionNotify.class);
			String requestNo = bean.getRequestNo();
			String notifyCode = bean.getCode();
			
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
			if(logThirdParty==null){
				return "ERROR";
			}else{
				if(StringUtils.isBlank(logThirdParty.getNotifyCode())) {
					logThirdPartyService.updateWithNotify(requestNo, notify, notifyCode);
				}
			}
			return "SUCCESS";
		}
		return "ERROR";
	}
}
