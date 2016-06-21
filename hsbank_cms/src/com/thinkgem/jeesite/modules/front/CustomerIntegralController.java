package com.thinkgem.jeesite.modules.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralHisService;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralSnapshotService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralSnapshot;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 我的花生豆
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/integral")
public class CustomerIntegralController extends BaseController {
	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;
	@Autowired
	private CustomerIntegralHisService customerIntegralHisService;
	@Autowired
	private CustomerAccountService customerAccountService;
	
	/**
	 * 我的花生豆-花生豆概况
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("summary")
	public String summary(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		CustomerIntegralSnapshot customerIntegralSnapshot = customerIntegralSnapshotService.getByAccountId(accountId);
		model.addAttribute("customerIntegralSnapshot", customerIntegralSnapshot);
		//累计获得花生豆
		model.addAttribute("getTotalIntegral", customerIntegralHisService.getTotalIntegral(accountId));
		//本月获得花生豆
		model.addAttribute("getIntegralCurrentMonth", customerIntegralHisService.getIntegralCurrentMonth(accountId));
		CustomerIntegralHis customerIntegralHis = new CustomerIntegralHis();
		customerIntegralHis.setAccountId(accountId);
		List<CustomerIntegralHis> customerIntegralHisList = customerIntegralHisService.findList(customerIntegralHis);
		model.addAttribute("customerIntegralHisList", customerIntegralHisList);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdjf");
		model.addAttribute("two_menu", "jfgk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账号</a>&nbsp;&gt;&nbsp;我的花生豆&nbsp;&gt;&nbsp;<a href='#'>花生豆概况</a>");
		return "modules/front/wdzh/integral/summary";
	}

	/**
	 * 我的花生豆-收支明细
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, String changeTypeCode, PageSearchBean pageSearchBean, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		pageSearchBean.setDefaultDateRangeWithMonths(-1);
		Page<CustomerIntegralHis> page = customerIntegralHisService.findListWithQuery(accountId, changeTypeCode, pageSearchBean);
		model.addAttribute("page", page);
		model.addAttribute("changeTypeCode",changeTypeCode);
		model.addAttribute("pageSearchBean",pageSearchBean);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdjf");
		model.addAttribute("two_menu", "szmx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;我的花生豆&nbsp;&gt;&nbsp;<a href='#'>收支明细</a>");
		return "modules/front/wdzh/integral/detail";
	}
	
	/**
	 * 我的花生豆-使用花生豆
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("use")
	public String use(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdjf");
		model.addAttribute("two_menu", "syjf");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;我的花生豆&nbsp;&gt;&nbsp;<a href='#'>使用花生豆</a>");
		return "modules/front/wdzh/integral/use";
	}
	
	/**
	 * 签到
	 * @param customerAccount
	 * @return
	 */
	@RequestMapping("sign")
	@ResponseBody
	public String sign() {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		Map<String, Object> result = new HashMap<String,Object>();
		CustomerAccount customerAccount = customerAccountService.get(accountId + "");
		if(canSign(customerAccount)) {
			Map map = customerIntegralSnapshotService.sign(customerAccount.getAccountId(), ProjectConstant.OP_TERM_DICT_PC);
			result.put("success", true);
			result.put("signCoins", map.get("signIntegral"));
		} else {
			result.put("success", false);
			result.put("message", "今日已完成签到！");
		}
		return JsonMapper.toJsonString(result);
	}
	
	/**
	 * 检查用户是否可以进行签到操作
	 * @param customerAccount
	 * @return
	 */
	@RequestMapping("canSign")
	@ResponseBody
	public boolean canSign(CustomerAccount customerAccount) {
		if(customerAccount == null) {
			return false;
		}
		return customerIntegralSnapshotService.canSign(customerAccount.getAccountId());
	}
}
