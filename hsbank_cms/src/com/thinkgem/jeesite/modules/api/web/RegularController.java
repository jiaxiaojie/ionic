package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.generator.convert.HSAPIConvertAction;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.web.param.AboutFilesParams;
import com.thinkgem.jeesite.modules.api.web.param.InvestRepaymentPlanParams;
import com.thinkgem.jeesite.modules.api.web.param.InvestmentRecordsParams;
import com.thinkgem.jeesite.modules.api.web.param.ProjectPaymentPlanParams;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;
import com.thinkgem.jeesite.modules.entity.api.ProjectSearch;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.api.ProjectSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.*;
import com.thinkgem.jeesite.modules.project.service.util.handler.RepaymentPlanHandler;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定期项目Controller
 *<p/>
 * @createDate 2016-5-13
 */

@Controller("apiRegularController")
@RequestMapping(value="${frontPath}/api/regular")
public class RegularController extends APIBaseController {


    @Autowired
    private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	@Autowired
	private ProjectRepaymentPlanService projectRepaymentPlanService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	CustomerAccountService customerAccountService;

    /**
     * 项目的相关文件信息
     * @param aboutFilesParams 项目ID
     * @author 万端瑞
     * @return
     */
	@Formater(path="${frontPath}/api/regular/aboutFiles")
    @ResponseBody
    @RequestMapping(value = "aboutFiles")
    public Map<String,Object> aboutFiles(AboutFilesParams aboutFilesParams){
        Map<String,String> typeNameMapping = StringUtils.toMap("1:营业执照,2:贸易合同,3:借款合同,4:央行登记信息,5:实地考察照片,6:发票,7:物流签收单据,8:其他资料",",",":");

        //查询数据
        Map<String, Object> aboutFiles = projectBaseInfoService.getAboutFiles(aboutFilesParams.getProjectId());

        //转换数据为接口
        API api = APIGenerator.toAPIWithCollection(aboutFiles.keySet(), new HSAPIConvertAction<String>(){
			@Override
			public Map<String, Object> convert(String type) {
				String fileTypeName = typeNameMapping.get(type);
				String[] resultList = StringUtils.split(StringUtils.toStr(aboutFiles.get(type)),"|");
				for(int i = 0; i < resultList.length; i++){
					resultList[i] = ApiUtil.imageUrlConver(resultList[i]);
				}
				APIObjectNode apiObjectNode = new APIObjectNode()
						.putNodeWithObject("fileType",type)
						.putNodeWithObject("fileTypeName",fileTypeName)
						.putNodeWithObject("resultList",resultList);

				return apiObjectNode;
			}
        });

        return api;
    }

	/**
	 * 项目的还款计划
	 * @param projectPaymentPlanParams
	 * @author 万端瑞
     * @return
     */
	@Formater(path="${frontPath}/api/regular/projectPaymentPlan")
    @ResponseBody
    @RequestMapping(value = "projectPaymentPlan")
    public Map<String,Object> projectPaymentPlan(ProjectPaymentPlanParams projectPaymentPlanParams){
		//查询数据
		Page<ProjectRepaymentPlan> page = new Page<ProjectRepaymentPlan>(projectPaymentPlanParams.getPageNumber(),projectPaymentPlanParams.getPageSize(),true);
		ProjectRepaymentPlan projectRepaymentPlan = new ProjectRepaymentPlan();
		projectRepaymentPlan.setProjectId(projectPaymentPlanParams.getProjectId());
		Page<ProjectRepaymentPlan> currentPage = projectRepaymentPlanService.findPage(page,projectRepaymentPlan);

		//封装数据为接口
		APINode resultList = APIGenerator.toAPINodeWithCollection(currentPage.getList(),new HSAPIConvertAction<ProjectRepaymentPlan>(){
			@Override
			public Map<String, Object> convert(ProjectRepaymentPlan projectRepaymentPlan) {
				Map<String, Object> result = MapUtils.bean2map(projectRepaymentPlan);
				result.put("statusName",DictUtils.getDictLabel(projectRepaymentPlan.getStatus(), "project_repayment_plan_status_dict", ""));
				return result;
			}
		});

		API api = APIGenerator.createAPIBuilder().putDataChildNode("count",currentPage.getCount())
				.putDataChildNode("resultList",resultList).build();

		return api;
    }


	/**
	 * 确认投资-还款计划
	 * @param investRepaymentPlanParams
	 * @author 万端瑞
     * @return
     */
	@Formater(path="${frontPath}/api/regular/investRepaymentPlan")
	@ResponseBody
	@RequestMapping(value = "investRepaymentPlan")
	public Map<String,Object> investRepaymentPlan(InvestRepaymentPlanParams investRepaymentPlanParams){

		//查询数据
		//1.项目基本信息
		ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
		projectBaseInfo.setId(investRepaymentPlanParams.getProjectId());
		projectBaseInfo = projectBaseInfoService.get(projectBaseInfo);
		//2.还款计划
		Page<RepaymentPlanItem> repaymentPlanPage = repaymentPlanHandler.generateForInvestment(projectBaseInfo, investRepaymentPlanParams.getAmount(), "",investRepaymentPlanParams.getPageSize(),investRepaymentPlanParams.getPageNumber());




		//封装数据为接口
		API api = APIGenerator.toAPI(projectBaseInfo, new HSAPIConvertAction<ProjectBaseInfo>(){
			@Override
			public Map<String, Object> convert(ProjectBaseInfo projectBaseInfo) {

				//还款起始日期
				String repaymentBeginDate = ("1".equals(projectBaseInfo.getRepaymentMode()) && repaymentPlanPage.getList().size() > 0 ?
						DateUtils.formatDate(repaymentPlanPage.getList().get(0).getEndDate(),"yyyy-MM-dd"): DateUtils.formatDate(projectBaseInfo.getPlannedRepaymentDate(),"yyyy-MM-dd")  ) ;

				//计算总利息
				Double totalInterest = 0d;
				List<RepaymentPlanItem> repaymentPlans = repaymentPlanHandler.generateForInvestment(projectBaseInfo, investRepaymentPlanParams.getAmount(), "");
				for(RepaymentPlanItem repaymentPlanItem : repaymentPlans){
					totalInterest += repaymentPlanItem.getInterest();
				}
				APINode resultList = APIGenerator.toAPINodeWithCollection(repaymentPlanPage.getList());

				APIObjectNode node = new APIObjectNode()
						.putNodeWithObject("interestDate",DateUtils.getDate())
						.putNodeWithObject("repaymentBeginDate",repaymentBeginDate)
						.putNodeWithObject("repaymentMode",projectBaseInfo.getRepaymentMode())
						.putNodeWithObject("repaymentModeName",DictUtils.getDictLabel(projectBaseInfo.getRepaymentMode(), "project_repayment_mode_dict", ""))
						.putNodeWithObject("totalInterest",totalInterest)
						.putNodeWithObject("count",repaymentPlanPage.getCount())
						.putNodeWithObject("resultList",resultList);

				return node;
			}
		});

		return api;
	}

	/**
	 *	得到投资记录列表
	 * @param investmentRecordsParams
	 * @author 万端瑞
     * @return
     */
	@Formater(path="${frontPath}/api/regular/investmentRecords")
	@ResponseBody
	@RequestMapping(value = "investmentRecords")
	public Map<String,Object> investmentRecords(InvestmentRecordsParams investmentRecordsParams){
		//查询项目投资记录数据
		ProjectInvestmentRecord projectInvestmentRecord = new ProjectInvestmentRecord();
		projectInvestmentRecord.setStatus("0,3");
		projectInvestmentRecord.setQueryProjectId(investmentRecordsParams.getProjectId());
		projectInvestmentRecord.setTransferProjectId(0L);
		Page<ProjectInvestmentRecord> page = projectInvestmentRecordService.findPage(new Page<ProjectInvestmentRecord>(investmentRecordsParams.getPageNumber(),investmentRecordsParams.getPageSize(),true),projectInvestmentRecord);

		//执行API数据构建
		APINode apiDataNode = APIGenerator.toAPINodeWithCollection(page.getList());

		//组装api
		API api = APIGenerator.createAPIBuilder().putDataChildNode("count",page.getCount())
				.putDataChildNode("resultList",apiDataNode).build();

		return api;
	}

	/**
	 * 新花生
	 * @param client
	 * @return
	 */
	@RequestMapping("noviceProjectDetails")
	@ResponseBody
	public String noviceProjectDetails(String client) {
		ProjectSearchBean projectSearchBean = new ProjectSearchBean();
		ProjectSearch projectSearch = new ProjectSearch();
		projectSearch.setIsNewUser("0");
		projectSearchBean.setProjectSearch(projectSearch);
		projectSearchBean.setIndex(0);
		projectSearchBean.setLimit(1);
		List<Map<String,Object>> list = projectBaseInfoService.findProjectList(projectSearchBean);
		Map<String,Object> result = new HashMap();
		if(list.size() > 0) {
			Map<String,Object> data = list.get(0);
			dealProjectResultData(data);
			ApiUtil.mapRespData(result, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		} else {
			ApiUtil.mapRespData(result, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 可投资数量
	 * @param client
	 * @return
	 */
	@Formater(path="${frontPath}/api/regular/getInvestCount")
	@RequestMapping("getInvestCount")
	@ResponseBody
	public Map<String,Object> getInvestCount(String client) {
		Map<String,Object> result = new HashMap();
		ApiUtil.mapRespData(result, ProjectInfoUtils.getProjectTypeInfo(), ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		return result;
	}

	/**
	 * 统计信息
	 * @param client
	 * @return
	 */
	@RequestMapping("getRegularStatisticInfo")
	@ResponseBody
	public String getRegularStatisticInfo(String client) {
		Map<String,Object> result = new HashMap();
		Map<String,Object> data = new HashMap();
		//融资项目数量
		data.put("projectCount", projectBaseInfoService.getOnlineProjectCount());
		//投标中项目数量
		data.put("tenderingCount", projectBaseInfoService.getTenderingProjectCount());
		//投标完成项目数量
		data.put("tenderedCount", projectBaseInfoService.getTenderedProjectCount());
		//还款中项目数量
		data.put("repaymentingCount", projectBaseInfoService.getRepaymentingProjectCount());
		//还款完成项目数量
		data.put("repaymentedCount", projectBaseInfoService.getRepaymentedProjectCount());
		ApiUtil.mapRespData(result, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 分页列表
	 * @param client
	 * @param status
	 * @param rate
	 * @param repaymentMode
	 * @param type
	 * @param search
	 * @param pageSize
	 * @param pageNumber
	 * @param flag
	 * @return
	 */
	@RequestMapping("pageList")
	@ResponseBody
	public String pageList(String client, String status, String duration, String rate, String repaymentMode,
						   String type, ProjectSearch search, Integer pageSize, Integer pageNumber, String flag) {
		pageSize = pageSize == null ? 10 : pageSize;
		pageNumber = pageNumber == null ? 1 : pageNumber;
		ProjectSearchBean projectSearchBean = new ProjectSearchBean(status, duration, rate, repaymentMode, type, search, pageSize, pageNumber);
		List<Map<String,Object>> list;
		int count = 0;
		list = projectBaseInfoService.findProjectList(projectSearchBean);
		for(Map<String,Object> data : list) {
			dealProjectResultData(data);
		}
		if("1".equals(flag)) {
			count = projectBaseInfoService.getCount(projectSearchBean);
		}
		Map<String,Object> result = new HashMap();
		Map<String,Object> data = new HashMap();
		data.put("count", count);
		data.put("resultList", list);
		ApiUtil.mapRespData(result, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		return JsonMapper.toJsonString(result);
	}

	private void dealProjectResultData(Map<String, Object> data) {
		List<ProjectShowTerm> showTermList = projectShowTermService.findListByProjectId(String.valueOf(data.get("projectId")));
		StringBuffer terminalCodes = new StringBuffer();
		for(ProjectShowTerm showTerm : showTermList){
			terminalCodes.append(showTerm.getTermCode()).append(",");
		}
		if(terminalCodes.indexOf(",") != -1){
			terminalCodes.deleteCharAt(terminalCodes.length() - 1);
		}
		data.put("projectTypeName", DictUtils.getDictLabel(String.valueOf(data.get("projectType")), "project_type_dict", ""));
		data.put("repaymentModeName", DictUtils.getDictLabel((String)data.get("repaymentMode"), "project_repayment_mode_dict", ""));
		data.put("amount", NumberUtils.sub(NumberUtils.parseDouble(data.get("financeMoney")), NumberUtils.parseDouble(data.get("endFinanceMoney"))));
		Double rate = NumberUtils.parseDouble(data.get("endFinanceMoney")) / NumberUtils.parseDouble(data.get("financeMoney")) * 100;
		data.put("rate", ApiUtil.formatRate(rate));
		data.put("statusName", DictUtils.getDictLabel((String)data.get("status"), "project_status_dict", ""));
		Double annualizedRateAdd = "1".equals(data.get("isIncreaseInterest")) ? NumberUtils.parseDouble(data.get("increaseInterestRate")) : 0d;
		data.put("annualizedRateNormal", NumberUtils.sub(NumberUtils.parseDouble(data.get("annualizedRate")), annualizedRateAdd));
		data.put("annualizedRateAdd", annualizedRateAdd);
		String activityRemark = "";
		if("1".equals(data.get("isIncreaseInterest"))) {
			if ("0".equals(String.valueOf(data.get("isNewUser")))) {
				activityRemark = "新手专享加息+";
			} else if(terminalCodes.indexOf(ProjectConstant.OP_TERM_DICT_ANDROID) != -1 || terminalCodes.indexOf(ProjectConstant.OP_TERM_DICT_IOS) != -1) {
				activityRemark = "app专享加息+";
			} else {
				activityRemark = "平台贴息+";
			}
			activityRemark += NumberUtils.mul(annualizedRateAdd, 100D) + "%";
		}
		data.put("activityRemark", activityRemark);
		data.put("durationUnitName", DictUtils.getDictLabel((String)data.get("durationUnit"), "project_duration_type_dict", ""));
		data.put("durationUnit", Integer.parseInt((String)data.get("durationUnit")));
		data.put("safeguardModeName", DictUtils.getDictLabel(String.valueOf(data.get("safeguardMode")), "project_safeguard_mode_dict", ""));
		data.put("isNewUser", String.valueOf(data.get("isNewUser")));
		data.put("isRecommend", "1".equals(String.valueOf(data.get("isRecommend"))) ? "0" : "1");
		data.put("isUseTicket", "1".equals(String.valueOf(data.get("isNewUser"))) ? "0" : "1");
		data.put("isCanAssign", !(new Long(-1)).equals(data.get("transferCode")) ? "0" : "-1");
		data.put("terminalCodes", terminalCodes.toString());
	}
}