package com.thinkgem.jeesite.modules.customer.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;

@Controller
@RequestMapping(value = "${adminPath}/customer/customerServiceInquiry")
public class CustomerServiceInquiryController extends BaseController {
	@Autowired
	private CustomerBaseService customerBaseService;

	@ModelAttribute
	public CustomerBase get(@RequestParam(required = false) String id) {
		CustomerBase entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = customerBaseService.get(id);
		}
		if (entity == null) {
			entity = new CustomerBase();
		}
		return entity;
	}
	/**
	 * 客服数据查询
	 * @param customerBase
	 * @param beginOpDt
	 * @param endOpDt
	 * @param beginRegisterDt
	 * @param endRegisterDt
	 * @param userStatus
	 * @param customerAccount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("customer:customerServiceInquiry:view")
	@RequestMapping(value = { "list", "" })
	public String redemptionInfoList(CustomerBase customerBase, Date beginOpDt, Date endOpDt, Date
	beginRegisterDt, Date endRegisterDt, String userStatus, CustomerAccount customerAccount,ProjectInvestmentRecord projectInvestmentRecord, HttpServletRequest request,HttpServletResponse response, Model model) {
		//查询数据显示的条数
		List<Map<String, Object>> list = customerBaseService.selectCoustmerMoboileNoPage(customerBase, customerAccount, beginRegisterDt, beginOpDt, endOpDt, endRegisterDt, userStatus);
		//coustomerCount数据显示的条数
		model.addAttribute("coustomerCount", list.size());
		model.addAttribute("beginRegisterDt", beginRegisterDt);
		model.addAttribute("endRegisterDt", endRegisterDt);
		model.addAttribute("beginOpDt", beginOpDt);
		model.addAttribute("endOpDt", endOpDt);
		model.addAttribute("userStatus", userStatus);
		return "modules/customer/customerServiceInquiryList";
	}

	/**
	 * 导出数据
	 * 
	 * @return
	 */
	@RequiresPermissions("customer:customerServiceInquiry:view")
	@RequestMapping(value = "export1", method = RequestMethod.POST)
	public String exportFile(CustomerBase customerBase, Date beginOpDt, Date endOpDt, Date beginRegisterDt, Date endRegisterDt, String userStatus, CustomerAccount customerAccount, HttpServletRequest request, HttpServletResponse
	response, Model model, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "客户手机号" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<Map<String, Object>> list = customerBaseService.selectCoustmerMoboileNoPage(customerBase, customerAccount, beginRegisterDt, beginOpDt, endOpDt, endRegisterDt, userStatus);
			LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
			fieldMap.put("mobile", "手机号");
			new ExportExcel("手机号信息", fieldMap).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目手机号数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/modules/customer/customerServiceInquiry/list?repage";
	}

}
