/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.ProjectBaseInfoResp;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.entity.front.ProjectSearchBean;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 债权Controller
 * 
 * @author lzb
 * @version 2015-12-31
 */
@Controller
@RequestMapping("${frontPath}/api/assignment")
public class AssignmentController extends APIBaseController {
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;

	/**
	 * 债权项目详情信息
	 * @param response
	 * @param transferProjectId
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletResponse response, HttpServletRequest request, String client, String transferProjectId) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api detail start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
	    ProjectTransferInfo transferInfo = new ProjectTransferInfo();
	    transferInfo.setTransferProjectId(NumberUtils.toLong(transferProjectId, 0L));
	    transferInfo = projectTransferInfoService.get(transferInfo);
	    String projectId = String.valueOf(transferInfo.getProjectId());
		ProjectBaseInfo pInfo = projectBaseInfoService.get(projectId);
		ProjectExecuteSnapshot pSnapshot = projectExecuteSnapshotService.getTransferSnapshot(NumberUtils.toLong(projectId, 0L), transferInfo.getTransferProjectId());
		pInfo.setPes(pSnapshot);
		if(pInfo != null){
			//项目详情数据
			this.detailCreate(request,null,transferInfo, pInfo, pSnapshot, map);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api detail end...");
		logger.info("api detail total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	

	
	/**
	 * 可投资金额
	 * @param financeMoney
	 * @param endFinanceMoney
	 * @return
	 */
	public Double voteAmount(Double financeMoney, Double endFinanceMoney){
		Double investmentFinanceMoney = financeMoney - endFinanceMoney < 0 ? 0 : financeMoney - endFinanceMoney;
		investmentFinanceMoney = LoanUtil.formatAmount(investmentFinanceMoney);
		return investmentFinanceMoney;
	}
	
	/**
	 * 债权项目分页列表
	 * @param projectSearchBean
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "pageList", method = RequestMethod.POST)
	public String pageList(ProjectSearchBean projectSearchBean, HttpServletResponse response, String client, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api pageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProjectTransferInfo> List = projectTransferInfoService.getProjectListPage(pageNumber, pageSize);
		//项目分页列表数据
		List<ProjectBaseInfoResp> pRespList =	this.detailCreatePageList(List);
		ApiUtil.mapRespData(map, pRespList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api pageList end...");
		logger.info("api pageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	




}
