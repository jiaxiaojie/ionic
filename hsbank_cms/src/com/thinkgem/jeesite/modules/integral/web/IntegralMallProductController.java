/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductPrice;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductType;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.integral.IntegralConstant;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductPriceService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductTypeService;
import com.thinkgem.jeesite.modules.sys.service.InvestmentTicketTypeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 花生乐园上架产品Controller
 * 
 * @author lizibo
 * @version 2015-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/integralMallProduct")
public class IntegralMallProductController extends BaseController {

	@Autowired
	private IntegralMallProductService integralMallProductService;
	@Autowired
	private IntegralMallProductTypeService integralMallProductTypeService;
	@Autowired
	private InvestmentTicketTypeService investmentTicketTypeService;
	@Autowired
	private IntegralMallProductPriceService integralMallProductPriceService;
	

	@ModelAttribute
	public IntegralMallProduct get(@RequestParam(required = false) String id) {
		IntegralMallProduct entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = integralMallProductService.get(id);
			
			if(entity.getProductId() != null){
				IntegralMallProductPrice integralMallProductPrice = new IntegralMallProductPrice();
				integralMallProductPrice.setProductId(entity.getProductId());
				integralMallProductPrice = integralMallProductPriceService.get(integralMallProductPrice);
				entity.setIntegralMallProductPrice(integralMallProductPrice);
			}
		}
		if (entity == null) {
			entity = new IntegralMallProduct();
		}
		return entity;
	}

	@RequiresPermissions("integral:integralMallProduct:view")
	@RequestMapping(value = { "list", "" })
	public String list(IntegralMallProduct integralMallProduct,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<IntegralMallProduct> dd= new Page<IntegralMallProduct>(request, response);
		dd.setOrderBy(" product_id desc");
		Page<IntegralMallProduct> page = integralMallProductService.findPage(dd,
				integralMallProduct);
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService
				.findList(integralMallProductType));
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductList";
	}

	/**
	 * 查看产品
	 * 
	 * @param integralMallProduct
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProduct:view")
	@RequestMapping(value = { "querylist", "" })
	public String querylist(IntegralMallProduct integralMallProduct,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<IntegralMallProduct> dd= new Page<IntegralMallProduct>(request, response);
		dd.setOrderBy(" product_id desc");
		Page<IntegralMallProduct> page = integralMallProductService.findPage(
				dd,
				integralMallProduct);
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService
				.findList(integralMallProductType));
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductQueryList";
	}

	/**
	 * 审核查看
	 * 
	 * @param integralMallProduct
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProduct:review")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(IntegralMallProduct integralMallProduct,
			Model model) {
		model.addAttribute("integralMallProduct", integralMallProduct);
		InvestmentTicketType investmentTicketType = new InvestmentTicketType();
		model.addAttribute("ticketList",
				investmentTicketTypeService.findList(investmentTicketType));
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService
				.findList(integralMallProductType));
		return "modules/integral/integralMallProductReviewForm";
	}

	/**
	 * 查看产品信息
	 * 
	 * @param integralMallProduct
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProduct:view")
	@RequestMapping(value = "queryForm")
	public String queryForm(IntegralMallProduct integralMallProduct, Model model) {
		model.addAttribute("integralMallProduct", integralMallProduct);
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService
				.findList(integralMallProductType));
		return "modules/integral/integralMallProductQueryForm";
	}

	/**
	 * 产品审核
	 * 
	 * @param integralMallProduct
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProduct:review")
	@RequestMapping(value = { "reviewList", "" })
	public String reviewList(IntegralMallProduct integralMallProduct,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<IntegralMallProduct> dd= new Page<IntegralMallProduct>(request, response);
		dd.setOrderBy(" product_id desc");
		Page<IntegralMallProduct> page = integralMallProductService.findPage(
				dd,
				integralMallProduct);
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService
				.findList(integralMallProductType));
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductReviewList";
	}

	/**
	 * 审核
	 * 
	 * @param integralMallProduct
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProduct:review")
	@RequestMapping(value = "review")
	public String review(IntegralMallProduct integralMallProduct, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProduct)) {
			return form(integralMallProduct, model);
		}
		if (integralMallProduct.getProductIntroduction() != null) {
			integralMallProduct
					.setProductIntroduction(StringEscapeUtils.unescapeHtml4(integralMallProduct
									.getProductIntroduction()));
		}
		integralMallProduct.setReviewDt(new Date());
		integralMallProduct.setReviewUserId(new Long(UserUtils.getUser()
				.getId()));
		integralMallProductService.review(integralMallProduct);
		String status=integralMallProduct.getStatus();
		if(status.equals("0")){
			addMessage(redirectAttributes, "拒绝花生乐园产品成功");
		}else{
			addMessage(redirectAttributes, "审批花生乐园产品成功");
		}
		return "redirect:" + Global.getAdminPath()
				+ "/integral/integralMallProduct/reviewList";
	}

	@RequiresPermissions("integral:integralMallProduct:view")
	@RequestMapping(value = "form")
	public String form(IntegralMallProduct integralMallProduct, Model model) {
		
		
		
		
		model.addAttribute("integralMallProduct", integralMallProduct);
		
		
		/*IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService
				.findList(integralMallProductType));*/
		
		
	
		
		InvestmentTicketType investmentTicketType = new InvestmentTicketType();
		model.addAttribute("ticketList",
				investmentTicketTypeService.findList(investmentTicketType));
		return "modules/integral/integralMallProductForm";
	}

	@RequiresPermissions("integral:integralMallProduct:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMallProduct integralMallProduct, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProduct)) {
			return form(integralMallProduct, model);
		}
		//integralMallProduct.setProductSurplus(integralMallProduct.getProductCount());
		
		
		integralMallProduct.setCreateDt(new Date());
		integralMallProduct.setCreateUserId(new Long(UserUtils.getUser()
				.getId()));
		if (integralMallProduct.getStatus() == null
				|| "".equals(integralMallProduct.getStatus())) {
			integralMallProduct
					.setStatus(IntegralConstant.INTEGRAL_PROJECT_STATUS_CREATE);
		}

		if (integralMallProduct.getProductIntroduction() != null) {
			integralMallProduct
					.setProductIntroduction(StringEscapeUtils.unescapeHtml4(integralMallProduct
									.getProductIntroduction()));
		}
		integralMallProductService.save(integralMallProduct);
		
		//保存价格策略
		integralMallProduct.getIntegralMallProductPrice().setProductId(Long.parseLong(integralMallProduct.getId()));
		integralMallProduct.getIntegralMallProductPrice().setCreateUserId(integralMallProduct.getCreateUserId());
		integralMallProduct.getIntegralMallProductPrice().setCreateDt(new Date());
		integralMallProductPriceService.save(integralMallProduct.getIntegralMallProductPrice());
		
		addMessage(redirectAttributes, "保存花生乐园产品成功");
		return "redirect:" + Global.getAdminPath()
				+ "/integral/integralMallProduct/list";
	}

	@RequiresPermissions("integral:integralMallProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMallProduct integralMallProduct,
			RedirectAttributes redirectAttributes) {
		integralMallProductService.delete(integralMallProduct);
		addMessage(redirectAttributes, "删除花生乐园产品成功");
		return "redirect:" + Global.getAdminPath()
				+ "/integral/integralMallProduct/list";
	}

}