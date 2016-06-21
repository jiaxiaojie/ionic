/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.MarketingWheelGetPrizeRecord;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelGetPrizeRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingWheelPrizeInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 大转盘中奖记录Controller
 * @author ydt
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingWheelGetPrizeRecord")
public class MarketingWheelGetPrizeRecordController extends BaseController {

	@Autowired
	private MarketingWheelGetPrizeRecordService marketingWheelGetPrizeRecordService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@Autowired
	private MarketingWheelPrizeInfoService marketingWheelPrizeInfoService;

	@ModelAttribute
	public MarketingWheelGetPrizeRecord get(@RequestParam(required=false) String id) {
		MarketingWheelGetPrizeRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingWheelGetPrizeRecordService.getAllInfo(id);
		}
		if (entity == null){
			entity = new MarketingWheelGetPrizeRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingWheelGetPrizeRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		marketingWheelGetPrizeRecord.setActivityId(marketingWheelGetPrizeRecord.getActivityId() == null ? -1 : marketingWheelGetPrizeRecord.getActivityId());
		Page<MarketingWheelGetPrizeRecord> page = marketingWheelGetPrizeRecordService.findPage(new Page<MarketingWheelGetPrizeRecord>(request, response), marketingWheelGetPrizeRecord); 
		model.addAttribute("page", page);
		List<MarketingActivityInfo> activityList = marketingActivityInfoService.findListByStatus(MarketConstant.MARKETING_ACTIVITY_STATUS_PASS);
		model.addAttribute("activityList", activityList);
		return "modules/marketing/marketingWheelGetPrizeRecordList";
	}

	@RequiresPermissions("marketing:marketingWheelGetPrizeRecord:view")
	@RequestMapping(value = "form")
	public String form(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord, Model model) {
		model.addAttribute("marketingWheelGetPrizeRecord", marketingWheelGetPrizeRecord);
		return "modules/marketing/marketingWheelGetPrizeRecordForm";
	}

	@RequiresPermissions("marketing:marketingWheelGetPrizeRecord:edit")
	@RequestMapping(value = "save")
	public String save(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingWheelGetPrizeRecord)){
			return form(marketingWheelGetPrizeRecord, model);
		}
		marketingWheelGetPrizeRecordService.save(marketingWheelGetPrizeRecord);
		addMessage(redirectAttributes, "保存大转盘中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingWheelGetPrizeRecord/?repage";
	}
	
	@RequiresPermissions("marketing:marketingWheelGetPrizeRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord, RedirectAttributes redirectAttributes) {
		marketingWheelGetPrizeRecordService.delete(marketingWheelGetPrizeRecord);
		addMessage(redirectAttributes, "删除大转盘中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingWheelGetPrizeRecord/?repage";
	}
	
	@RequiresPermissions("marketing:marketingWheelGetPrizeRecord:edit")
	@RequestMapping(value = "given")
	public String given(String id, RedirectAttributes redirectAttributes) {
		MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord = marketingWheelGetPrizeRecordService.get(id);
		if(MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN.equals(marketingWheelGetPrizeRecord.getStatus())) {
			throw new ServiceException("系统异常，请稍后重试");
		}
		marketingWheelGetPrizeRecordService.updateStatusToGiven(id);
		marketingWheelPrizeInfoService.updateInstanceStatus(marketingWheelGetPrizeRecord.getPrizeInstanceId() + "", MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
		MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoService.getByInstanceId(marketingWheelGetPrizeRecord.getPrizeInstanceId());
		MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
		marketingActivityAwardRecord.setAccountId(marketingWheelGetPrizeRecord.getAccountId());
		marketingActivityAwardRecord.setActivityId(marketingWheelPrizeInfo.getActivityId());
		marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
		marketingActivityAwardRecord.setChannelId(marketingWheelGetPrizeRecord.getOpTerm());
		marketingActivityAwardRecord.setAwardType(marketingWheelPrizeInfo.getType());
		marketingActivityAwardRecord.setAwardValue(marketingWheelPrizeInfo.getValue());
		marketingActivityAwardRecord.setAwardReason("活动奖励");
		marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setUser(UserUtils.getUser());
		marketingActivityAwardRecordService.save(marketingActivityAwardRecord);
		addMessage(redirectAttributes, "确认奖品已赠送成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingWheelGetPrizeRecord/?repage";
	}
}