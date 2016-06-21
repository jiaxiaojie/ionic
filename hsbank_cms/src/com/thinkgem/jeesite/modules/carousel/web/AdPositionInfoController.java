/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.carousel.service.AdPositionShowTermService;
import com.thinkgem.jeesite.modules.entity.AdPositionShowTerm;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.hsbank.util.type.NumberUtil;
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
import com.thinkgem.jeesite.modules.entity.AdPositionInfo;
import com.thinkgem.jeesite.modules.carousel.service.AdPositionInfoService;

import java.util.Date;
import java.util.List;

/**
 * 广告位显示信息Controller
 * @author huangyuchen
 * @version 2016-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/carousel/adPositionInfo")
public class AdPositionInfoController extends BaseController {
	@Autowired
	private AdPositionShowTermService adPositionShowTermService;

	@Autowired
	private AdPositionInfoService adPositionInfoService;

	@ModelAttribute
	public AdPositionInfo get(@RequestParam(required=false) String id) {
		AdPositionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adPositionInfoService.get(id);
		}
		if (entity == null){
			entity = new AdPositionInfo();
		}
		return entity;
	}

	@RequiresPermissions("carousel:adPositionInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdPositionInfo adPositionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdPositionInfo> page = adPositionInfoService.findPage(new Page<AdPositionInfo>(request, response), adPositionInfo);
		model.addAttribute("page", page);
		return "modules/carousel/adPositionInfoList";
	}

	/**
	 * 查看
	 * @param adPositionInfo
	 * @param model
     * @return
     */
	@RequiresPermissions("carousel:adPositionInfo:view")
	@RequestMapping(value = "form")
	public String form(AdPositionInfo adPositionInfo, Model model) {
			String  id =  adPositionInfo.getId();
			adPositionInfo = this.get(String.valueOf(id));
			//根据轮播图编号查询列表
			AdPositionShowTerm adPositionShowTerm = adPositionShowTermService.findListByAdPositionId(id);
			adPositionInfo.setTermCode(adPositionShowTerm.getTermCode());
		model.addAttribute("adPositionInfo", adPositionInfo);
		return "modules/carousel/adPositionInfoForm";
	}

	/**
	 * 跳转到创建页面
	 * @param adPositionInfo
	 * @param model
     * @return
     */
	@RequiresPermissions("carousel:adPositionInfo:edit")
	@RequestMapping(value = "createForm")
	public String createForm(AdPositionInfo adPositionInfo, Model model) {
		return "modules/carousel/adPositionInfoCreateForm";
	}

	/**
	 * 创建广告位消息
	 * @param adPositionInfo
	 * @param model
	 * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("carousel:adPositionInfo:edit")
	@RequestMapping(value = "save")
	public String save(AdPositionInfo adPositionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, adPositionInfo)){
			return form(adPositionInfo, model);
		}
		adPositionInfo.setStatus(ProjectConstant.CAROUSEL_INFO_STATUS_CREATE);
		adPositionInfo.setCreateDt(new Date());
		adPositionInfo.setCreateUserId(new Long(UserUtils.getUser().getId()));
		adPositionInfoService.save(adPositionInfo);
		addMessage(redirectAttributes, "保存广告位显示信息成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/adPositionInfo/?repage";
	}

	/**
	 * 审批
	 * @param adPositionInfo
	 * @param model
	 * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("carousel:adPositionInfo:edit")
	@RequestMapping(value = "review")
	public String review(AdPositionInfo adPositionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, adPositionInfo)){
			return form(adPositionInfo, model);
		}
		//设置状态为审批通过、审批人、审批时间
		String status = adPositionInfo.getStatus();
		adPositionInfoService.get(adPositionInfo);
		adPositionInfo.setStatus(status);
		adPositionInfo.setReviewUserId(new Long(UserUtils.getUser().getId()));
		adPositionInfo.setReviewDt(new Date());
		adPositionInfoService.review(adPositionInfo);
		addMessage(redirectAttributes, "审批广告位显示信息成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/adPositionInfo/reviewList";
	}

	@RequiresPermissions("carousel:adPositionInfo:view")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(AdPositionInfo adPositionInfo, Model model) {
		String  id =  adPositionInfo.getId();
		adPositionInfo = this.get(String.valueOf(id));
		//根据轮播图编号查询列表
		AdPositionShowTerm adPositionShowTerm = adPositionShowTermService.findListByAdPositionId(id);
		adPositionInfo.setTermCode(adPositionShowTerm.getTermCode());
		model.addAttribute("adPositionInfo", adPositionInfo);
		return "modules/carousel/adPositionInfoReviewForm";
	}

	/**
	 * 审批列表
	 * @param adPositionInfo
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequiresPermissions("carousel:adPositionInfo:view")
	@RequestMapping(value = {"reviewList"})
	public String reviewList(AdPositionInfo adPositionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		adPositionInfo.setStatus(ProjectConstant.CAROUSEL_INFO_STATUS_CREATE);
		Page<AdPositionInfo> page = adPositionInfoService.findPage(new Page<AdPositionInfo>(request, response), adPositionInfo);
		model.addAttribute("page", page);
		return "modules/carousel/adPositionInfoReviewList";
	}




	@RequiresPermissions("carousel:adPositionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AdPositionInfo adPositionInfo, RedirectAttributes redirectAttributes) {
		adPositionInfoService.delete(adPositionInfo);
		addMessage(redirectAttributes, "删除广告位显示信息成功");
		return "redirect:"+Global.getAdminPath()+"/carousel/adPositionInfo/?repage";
	}



   /**
	 * 验证渠道名是否有效
	 * @param termCode
	 * @return
	 */
    @ResponseBody
	@RequiresPermissions("carousel:adPositionInfo:edit")
	@RequestMapping(value = "checkTermCode")
	public Boolean checkName(String termCode,String code) {
			List<AdPositionShowTerm> list = adPositionShowTermService.getAdPositionShowTermTermCodeByTermCode(code);
		Boolean res=true;
		if(list!=null){
			for(AdPositionShowTerm adPositionShowTerm : list){
				if(adPositionShowTerm.getTermCode().equals(termCode)){
					res=false;
				}
			}
		}else{
			res=true;
        }
		return  res;
	}

}
