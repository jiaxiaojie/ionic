
package com.thinkgem.jeesite.modules.customer.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.AdChannelInfo;
import com.thinkgem.jeesite.modules.sys.service.AdChannelInfoService;

/**
 * 推广渠道信息Controller
 * @author wanduanrui
 * @version 2015-11-17
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/adChannelInfo")
public class AdChannelInfoController extends BaseController {

	@Autowired
	private AdChannelInfoService adChannelInfoService;
	
	@ModelAttribute
	public AdChannelInfo get(@RequestParam(required=false) String id) {
		AdChannelInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adChannelInfoService.get(id);
		}
		if (entity == null){
			entity = new AdChannelInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:adChannelInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdChannelInfo adChannelInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdChannelInfo> page = adChannelInfoService.findPage(new Page<AdChannelInfo>(request, response), adChannelInfo); 
		model.addAttribute("page", page);
		return "modules/customer/adChannelInfoList";
	}

	@RequiresPermissions("customer:adChannelInfo:view")
	@RequestMapping(value = "form")
	public String form(AdChannelInfo adChannelInfo, Model model) {
		model.addAttribute("adChannelInfo", adChannelInfo);
		return "modules/customer/adChannelInfoForm";
	}

	@RequiresPermissions("customer:adChannelInfo:edit")
	@RequestMapping(value = "save")
	public String save(AdChannelInfo adChannelInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, adChannelInfo)){
			return form(adChannelInfo, model);
		}
		if(!StringUtils.isNotBlank(adChannelInfo.getId())){
			adChannelInfo.setChannel(UUID.randomUUID().toString());
		}
		
		adChannelInfoService.save(adChannelInfo);
		addMessage(redirectAttributes, "保存推广渠道信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/adChannelInfo/?repage";
	}
	
	@RequiresPermissions("customer:adChannelInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AdChannelInfo adChannelInfo, RedirectAttributes redirectAttributes) {
		adChannelInfoService.delete(adChannelInfo);
		addMessage(redirectAttributes, "删除推广渠道信息成功");
		return "redirect:"+Global.getAdminPath()+"/customer/adChannelInfo/?repage";
	}
	
	@RequiresPermissions("customer:adChannelInfo:view")
	@RequestMapping(value = "registPopChannelsStatisticsList")
	public String registPopChannelsStatisticsList(AdChannelInfo adChannelInfo,Date beginDate,Date endDate, 
			HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Map<String,Object>> page = adChannelInfoService.registPopChannelsStatisticsPage( new Page<Map<String,Object>>(request, response)
				,adChannelInfo.getId(), beginDate, endDate);
		
		AdChannelInfo params = new AdChannelInfo();
		params.setStatus(null);
		List<AdChannelInfo> channelInfos = adChannelInfoService.findList(params);
		model.addAttribute("channelInfos", channelInfos);
		model.addAttribute("page", page);
		model.addAttribute("adChannelInfo", adChannelInfo);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		return "modules/customer/registPopChannelsStatisticsList";
	}
	
	/**
	 * 验证渠道名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("customer:adChannelInfo:edit")
	@RequestMapping(value = "checkName")
	public String checkName(String channelName,String id) {
		if (channelName !=null) {
			AdChannelInfo adChannelInfo = adChannelInfoService.getAdChannelInfoByName(channelName);	
			if(adChannelInfo == null){
				return "true";
			}
			else if(id != null && id.equals(adChannelInfo.getId())){
				return "true";
			}
			
		}
		return "false";
	}

}