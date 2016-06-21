/**
 * 
 */
package com.thinkgem.jeesite.modules.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 投资管理
 * 
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/investment")
public class CustomerInvestmentController extends BaseController {
	@Autowired
	CustomerAccountService customerAccountService;
	@Autowired
	CustomerBalanceService customerBalanceService;
	@Autowired
	ProjectInvestmentRecordService projectInvestmentRecordService;
	
	@Autowired
	ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	ProjectExecuteSnapshotService projectExecuteSnapshotService;

	/**
	 * 投资统计
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tongji")
	public String tongji(CustomerBalanceHis customerBalanceHis, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获取当前账号余额信息
		CustomerBalance customerBalance = customerBalanceService.get(accountId
				.longValue() + "");
		if (customerBalance == null) {
			customerBalanceService.createInitCustomerBalance(accountId);
			customerBalance = customerBalanceService.get(accountId.longValue()
					+ "");
		}
		model.addAttribute("customerBalance", customerBalance);
		// 获得持有中项目列表
		List<ProjectInvestmentRecord> investmentProjectList = projectInvestmentRecordService
				.findInvestmentRunningProjectListByAccountId(accountId
						.longValue() + "");

		int runningCount = getDistinctProjectCount(investmentProjectList);
		model.addAttribute("runningCount", runningCount);
		// 获得可转让项目数量
		List<ProjectInvestmentRecord> canTransferList = new ArrayList<ProjectInvestmentRecord>();
		if (investmentProjectList != null) {
			for (ProjectInvestmentRecord projectInvestmentRecord : investmentProjectList) {
				Map<String, String> ret = projectInvestmentRecordService
						.canTransfer(projectInvestmentRecord.getId());
				String flag = ret.get("flag");
				if (flag.equals("true")) {
					canTransferList.add(projectInvestmentRecord);
				}
			}
		}
		int canTransferCount = getDistinctProjectCount(canTransferList);
		model.addAttribute("canTransferCount", canTransferCount);
		// 获得转让中项目数量
		int transferCount = projectTransferInfoService
				.getTransferingProjectCount(accountId.longValue() + "");
		model.addAttribute("transferCount", transferCount);
		// 逾期项目数 逻辑尚未实现
		int overdueCount = 0;
		model.addAttribute("overdueCount", overdueCount);
		// 获得代偿项目数量 逻辑尚未实现
		int compensatoryRunningCount = 0;
		model.addAttribute("compensatoryRunningCount", compensatoryRunningCount);
		// 获得投资冻结中项目数量（申请中）
		int congealCount = projectInvestmentRecordService
				.findCongealProjectCountByAccountId(accountId.longValue() + "");
		model.addAttribute("congealCount", congealCount);
		// 获得已结束项目列表
		List<ProjectInvestmentRecord> investmentEndProjectList = projectInvestmentRecordService
				.findInvestmentEndProjectListByAccountId(accountId.longValue()
						+ "");
		int endCount = getDistinctProjectCount(investmentEndProjectList);
		model.addAttribute("endCount", endCount);
		// 获得已转让项目数量
		int transferEndCount = projectTransferInfoService
				.getTransferEndProjectCount(accountId.longValue() + "");
		model.addAttribute("transferEndCount", transferEndCount);
		// 获得已代偿项目数量 逻辑尚未实现
		int compensatoryEndCount = 0;
		model.addAttribute("compensatoryEndCount", compensatoryEndCount);
		// 获得提前还款项目数量
		int preRepaymentCount = projectInvestmentRecordService
				.getPreRepaymentProjectCount(accountId.longValue() + "");
		model.addAttribute("preRepaymentCount", preRepaymentCount);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tztj");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='javascript:void(0);'>投资统计</a>");

		return "modules/front/wdzh/tzgl_tztj";
	}

	/**
	 * 获取根据项目编号虑重项目数量
	 * 
	 * @param list
	 * @return
	 */
	public int getDistinctProjectCount(List<ProjectInvestmentRecord> list) {
		List<ProjectInvestmentRecord> newList = new ArrayList<ProjectInvestmentRecord>();
		for (ProjectInvestmentRecord item : list) {
			if (!contains(newList, item)) {
				newList.add(item);
			}
		}
		return newList.size();
	}

	public boolean contains(List<ProjectInvestmentRecord> list,
			ProjectInvestmentRecord item) {
		for (ProjectInvestmentRecord theRecord : list) {
			if (theRecord.getProjectBaseInfo().getProjectId()
					.equals(item.getProjectBaseInfo().getProjectId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 投资项目
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project")
	public String project(HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		// 获得正在运行的投资项目
		List<ProjectInvestmentRecord> investmentRunningProjectList = projectInvestmentRecordService
				.findInvestmentRunningProjectListByAccountId(accountId
						.longValue() + "");
		model.addAttribute("investmentRunningProjectList",
				investmentRunningProjectList);
		// 获得结束的投资项目
		List<ProjectInvestmentRecord> investmentEndProjectList = projectInvestmentRecordService
				.findInvestmentEndProjectListByAccountId(accountId.longValue()
						+ "");
		model.addAttribute("investmentEndProjectList", investmentEndProjectList);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tzxm");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的债权</a>");
		return "modules/front/wdzh/tzgl_tzxm";
	}

	/**
	 * 投资项目(持有中)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_cyz")
	public String projectCyz(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获得正在运行的投资项目
		Page<ProjectInvestmentRecord> page = projectInvestmentRecordService.findListForCyz(accountId.longValue() + "", pageNo);
		for (ProjectInvestmentRecord item : page.getList()) {
			String investmentRecordId=item.getId();
			Map<String, String> map=projectInvestmentRecordService.canTransfer(investmentRecordId);
			String flag=(String)map.get("flag");
			if(flag.equals("true")){
				item.setCanTransferFlag(true);
			}else{
				item.setCanTransferFlag(false);
			}
			//将record.pes.remainingTime的值设置为天数供前端显示使用
			ProjectRepaymentSplitRecord pSplitRecord = projectRepaymentSplitRecordService.getRepaymentInfoByProjectAndccountId(item.getProjectBaseInfo().getProjectId(), accountId, NumberUtils.toLong(item.getId(), 0L));
			item.getPes().setRemainingTime((int)DateUtils.getDistanceOfTwoDate(DateUtils.dateFormate(new Date()), pSplitRecord.getLastRepaymentDt()));
		}
		model.addAttribute("page", page);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tzxm");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的债权</a>");

		return "modules/front/wdzh/tzgl_wdzq_cyz";
	}

	/**
	 * 持有中债权转让明细信息
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "project_cyz_transfer_detail")
	@ResponseBody
	public Map<String, Object> projectCyzTransferDetail(String recordId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		ProjectInvestmentRecord pir = projectInvestmentRecordService
				.get(recordId);
		String projectId = pir.getProjectId().longValue() + "";
		ProjectBaseInfo pbi = projectBaseInfoService.get(projectId);
		try {
//			double hasReceive = pir.getActualAmount() + pir.getWillProfit()+pir.getPlatformAmount()+pir.getTicketAmount()
//					- pir.getWillReceivePrincipal()
//					- pir.getWillReceiveInterest();
			//已收本息=债权+利息-待收本金-待收利息
			double hasReceive = pir.getAmount() + pir.getWillProfit()
					- pir.getWillReceivePrincipal()
					- pir.getWillReceiveInterest();
			if (hasReceive < 0) {
				hasReceive = 0;
			}

			pir.setHasReceive(formatDouble(hasReceive));
			pir.setWillReceiveInterest(formatDouble(pir
					.getWillReceiveInterest()));
			pir.setWillReceivePrincipal(formatDouble(pir
					.getWillReceivePrincipal()));
		} catch (Exception e) {
			pir.setHasReceive(new Double("0"));
		}
		//转让服务费
		double serviceCharge = pir.getAmount()
				* (ProjectConfig.getInstance().getAssignmentFeeRateUp() );
		retMap.put("serviceCharge", formatDouble(serviceCharge));
		retMap.put("discountDate", DateUtils.formatDate(
				DateUtils.addDays(new Date(), 3), "yyyy-MM-dd"));
		retMap.put("invest", pir);
		retMap.put("pro", pbi);
		return retMap;
	}

	public double formatDouble(double val) {
		BigDecimal b1 = new BigDecimal(val);
		double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	/**
	 * 根据投资记录编号获取对应的还款计划列表
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "project_plan_list")
	@ResponseBody
	public Map<String, Object> projectPlanList(String recordId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		ProjectInvestmentRecord pir = projectInvestmentRecordService
				.get(recordId);
		String projectId = pir.getProjectId().longValue() + "";
		ProjectBaseInfo pbi = projectBaseInfoService.get(projectId);
		retMap.put("project_show", pbi.getProjectName());
		retMap.put("investment_amount", pir.getAmount().doubleValue() + "");
		retMap.put("sum_profit", pir.getWillProfit().doubleValue() + "");
		List<ProjectRepaymentSplitRecord> splitList = projectRepaymentSplitRecordService
				.getRepaymentListByRecordId(recordId);
		retMap.put("splitList", splitList);
		return retMap;
	}

	/**
	 * 根据投资记录编号获取对应的还款计划列表
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "do_transfer")
	@ResponseBody
	public Map<String, Object> doTransfer(String recordId, String money,
			String dateLine) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, String> canTransferFlag = projectInvestmentRecordService
					.canTransfer(recordId);
			String flag = canTransferFlag.get("flag");
			if (flag.equals("true")) {
				projectTransferInfoService.makeNewTransfer(recordId, money,
						dateLine);
				retMap.put("flag", "ok");
			} else {
				retMap.put("flag", "err");
				retMap.put("mes", canTransferFlag.get("mes"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("flag", "err");
			retMap.put("mes", "系统异常,转让失败");
		}
		return retMap;
	}

	/**
	 * 投资项目(转出中)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_zcz")
	public String projectZcz(String pageNo, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获得正在运行的投资项目
		Page<ProjectTransferInfo> page = projectTransferInfoService
				.findTransferingProjectListByAccountId(accountId.longValue()
						+ "", pageNo);
		model.addAttribute("page", page);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tzxm");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的债权</a>");
		return "modules/front/wdzh/tzgl_wdzq_zcz";
	}

	/**
	 * 获得转出中的明细详情
	 * 
	 * @param transferProjectId
	 * @return
	 */
	@RequestMapping(value = "transfer_detail")
	@ResponseBody
	public Map<String, Object> getTransferDetail(String transferProjectId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 转让项目明细
		ProjectTransferInfo detail = projectTransferInfoService
				.get(transferProjectId);
		String projectId = detail.getProjectId().longValue() + "";
		// 对应原始项目信息
		ProjectBaseInfo pbi = projectBaseInfoService.get(projectId);
		String investmentId = detail.getInvestmentRecordId().longValue() + "";
		// 对应的原始投资记录
		ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordService
				.get(investmentId);
		// 转让快照
		ProjectExecuteSnapshot pes = projectExecuteSnapshotService
				.getByProjectIdAndTransferId(projectId, transferProjectId);
		// 获得已产生的平台服务费
		double serviceCharge = projectInvestmentRecordService
				.getUpServiceCharge(transferProjectId);

		retMap.put("projectName", pbi.getProjectName());
		retMap.put("annualizedRate", pbi.getAnnualizedRate());
		retMap.put("discountDate",
				DateUtils.formatDateTime(detail.getDiscountDate()));
		if (projectInvestmentRecord.getTransferProjectId().longValue() == 0) {
			retMap.put("getType", "直接投资");
		} else {
			retMap.put("getType", "债权投资");
		}
		if (projectInvestmentRecord.getWillProfit() == null) {
			projectInvestmentRecord.setWillProfit(new Double("0"));
		}
		if (projectInvestmentRecord.getWillReceiveInterest() == null) {
			projectInvestmentRecord.setWillReceiveInterest(new Double("0"));
		}
		if (projectInvestmentRecord.getWillReceivePrincipal() == null) {
			projectInvestmentRecord.setWillReceivePrincipal(new Double("0"));
		}
		if (projectInvestmentRecord.getAmount() == null) {
			projectInvestmentRecord.setAmount(new Double("0"));
		}
		if (pes.getEndFinanceMoney() == null) {
			pes.setEndFinanceMoney(new Double("0"));
		}
		retMap.put("investDate",
				DateUtils.formatDateTime(projectInvestmentRecord.getOpDt()));
		retMap.put("investAmount", projectInvestmentRecord.getAmount());
		retMap.put("hasReciveAmount", projectInvestmentRecord.getWillProfit()
				- projectInvestmentRecord.getWillReceiveInterest()
				- projectInvestmentRecord.getWillReceivePrincipal());
		retMap.put("endFinanceMoney", pes.getEndFinanceMoney() == null ? 0
				: pes.getEndFinanceMoney());
		retMap.put("surplusMoney",
				projectInvestmentRecord.getAmount() - pes.getEndFinanceMoney());
		retMap.put("serviceCharge",formatDouble(serviceCharge));
		return retMap;
	}

	/**
	 * 获得转出中的明细详情
	 * 
	 * @param transferProjectId
	 * @return
	 */
	@RequestMapping(value = "transfer_list")
	@ResponseBody
	public Map<String, Object> getTransferList(String transferProjectId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 转让项目明细
		ProjectTransferInfo detail = projectTransferInfoService
				.get(transferProjectId);
		String projectId = detail.getProjectId().longValue() + "";
		// 对应原始项目信息
		ProjectBaseInfo pbi = projectBaseInfoService.get(projectId);
		String investmentId = detail.getInvestmentRecordId().longValue() + "";
		// 对应的原始投资记录
		ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordService
				.get(investmentId);
		// 转让快照
		ProjectExecuteSnapshot pes = projectExecuteSnapshotService
				.getByProjectIdAndTransferId(projectId, transferProjectId);
		// 获得已产生的平台服务费
		double serviceCharge = projectInvestmentRecordService
				.getUpServiceCharge(transferProjectId);

		retMap.put("projectName", pbi.getProjectName());

		if (projectInvestmentRecord.getWillProfit() == null) {
			projectInvestmentRecord.setWillProfit(new Double("0"));
		}
		if (projectInvestmentRecord.getWillReceiveInterest() == null) {
			projectInvestmentRecord.setWillReceiveInterest(new Double("0"));
		}
		if (projectInvestmentRecord.getWillReceivePrincipal() == null) {
			projectInvestmentRecord.setWillReceivePrincipal(new Double("0"));
		}
		if (projectInvestmentRecord.getAmount() == null) {
			projectInvestmentRecord.setAmount(new Double("0"));
		}
		if (pes.getEndFinanceMoney() == null) {
			pes.setEndFinanceMoney(new Double("0"));
		}
		retMap.put("investAmount", projectInvestmentRecord.getAmount());
		retMap.put("endFinanceMoney", pes.getEndFinanceMoney() == null ? 0
				: pes.getEndFinanceMoney());
		retMap.put("surplusMoney",
				projectInvestmentRecord.getAmount() - pes.getEndFinanceMoney());
		retMap.put("serviceCharge", formatDouble(serviceCharge));
		List<ProjectInvestmentRecord> investList = projectInvestmentRecordService
				.findListByProjectIdAndTransferProjectId(projectId, new Long(
						transferProjectId), "0");
		retMap.put("investList", investList);
		return retMap;
	}

	/**
	 * 获得转出中的明细详情
	 * 
	 * @param transferProjectId
	 * @return
	 */
	@RequestMapping(value = "transfer_cancel")
	@ResponseBody
	public Map<String, Object> transferCancel(String transferProjectId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 转让项目明细
		projectTransferInfoService.updateStatus(transferProjectId,
				ProjectConstant.PROJECT_TRANSFER_STATUS_CANCEL);
		retMap.put("flag", "ok");
		return retMap;

	}

	/**
	 * 投资项目(投标中)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_tbz")
	public String projectTbz(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获得正在运行的投资项目
		Page<ProjectInvestmentRecord> page = projectInvestmentRecordService
				.findListForTbz(accountId.longValue() + "", pageNo);
		model.addAttribute("page", page);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tzxm");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的债权</a>");
		return "modules/front/wdzh/tzgl_wdzq_tbz";
	}

	/**
	 * 投资项目(已转出)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_yzc")
	public String projectYzc(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获得正在运行的投资项目
		Page<ProjectTransferInfo> page = projectTransferInfoService
				.findTransferEndProjectListByAccountId(accountId.longValue()
						+ "", pageNo);
		model.addAttribute("page", page);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tzxm");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的债权</a>");
		return "modules/front/wdzh/tzgl_wdzq_yzc";
	}

	/**
	 * 投资项目(已结束)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_yjs")
	public String projectYjs(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获得已结束的投资项目列表
		Page<ProjectInvestmentRecord> page = projectInvestmentRecordService
				.findListForYjs(accountId.longValue() + "", pageNo);
		model.addAttribute("page", page);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "tzxm");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的债权</a>");
		return "modules/front/wdzh/tzgl_wdzq_yjs";
	}

	/**
	 * 待收款明细
	 * @param customerAccount
	 * @param pageSearchBean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detailUnReceiveMoney")
	public String detailUnReceiveMoney(PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		pageSearchBean.setDefaultDateRangeWithMonths(3);
		Page<ProjectRepaymentSplitRecord> page = projectRepaymentSplitRecordService.searchPage(accountId, ProjectConstant.PROJECT_REPAY_STATUS_BUDGET, pageSearchBean);
		
		Map<String,Object> unReceivedMoneySummary = projectRepaymentSplitRecordService.getUnReceivedMoneySummaryByAccountId(accountId);
		
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "skmx");
		model.addAttribute("unReceivedMoneySummary", unReceivedMoneySummary);
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>收款明细</a>");
		return "modules/front/wdzh/investment/detailUnReceiveMoney";
	}

	/**
	 * 已收款明细
	 * @param customerAccount
	 * @param pageSearchBean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detailReceivedMoney")
	public String detailReceivedMoney(PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		Page<ProjectRepaymentSplitRecord> page = projectRepaymentSplitRecordService.searchPage(accountId, ProjectConstant.PROJECT_REPAY_STATUS_ALREADY, pageSearchBean);
		
		Map<String,Object> receivedMoneySummary = projectRepaymentSplitRecordService.getReceivedMoneySummaryByAccountId(accountId);
		
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "skmx");
		model.addAttribute("receivedMoneySummary", receivedMoneySummary);
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>收款明细</a>");
		return "modules/front/wdzh/investment/detailReceivedMoney";
	}
	
	/**
	 * 债权转让 暂不实现 old
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "transferProject")
	public String transferProject(HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		// 获得持有中项目列表
		List<ProjectInvestmentRecord> investmentProjectList = projectInvestmentRecordService
				.findInvestmentRunningProjectListByAccountId(accountId
						.longValue() + "");
		// 获得可转让项目列表
		List<ProjectInvestmentRecord> canTransferList = new ArrayList<ProjectInvestmentRecord>();
		if (investmentProjectList != null) {
			for (ProjectInvestmentRecord projectInvestmentRecord : investmentProjectList) {
				Map<String, String> ret = projectInvestmentRecordService
						.canTransfer(projectInvestmentRecord.getId());
				String flag = ret.get("flag");
				if (flag.equals("true")) {
					canTransferList.add(projectInvestmentRecord);
				}
			}
		}
		model.addAttribute("canTransferList", canTransferList);
		// 获得已转让项目列表

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "zqzr");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>债权转让</a>");

		return "modules/front/wdzh/tzgl_zqzr";
	}

	/**
	 * 检查用户是否为新手
	 * 
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value = "checkIsNewCustomer")
	@ResponseBody
	public boolean checkIsNewCustomer(String accountId) {
		return projectInvestmentRecordService.isNewCustomer(accountId);
	}
}
