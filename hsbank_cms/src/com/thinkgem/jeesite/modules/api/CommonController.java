/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.hsbank.util.tool.MobileUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 公共Controller
 * 
 * @author lzb
 * @version 2015-12-31
 */
@Controller("apiCommonController")
@RequestMapping("${frontPath}/api/common")
public class CommonController extends APIBaseController {
	/**
	 * 是不是正确的手机号码
	 * @param response
	 * @param client
	 * @param mobile
     * @return
     */
	@RequestMapping(value = "isMobile", method = RequestMethod.POST)
	public String detail(HttpServletResponse response, String client, String mobile) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		mobile = StringUtil.dealString(mobile);
		if (MobileUtil.isMobile(mobile)) {
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		} else {
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "请输入正确的手机号码", false);
		}
		return renderString(response, map);
	}
}
