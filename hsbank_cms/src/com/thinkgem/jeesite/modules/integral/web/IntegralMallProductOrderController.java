/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.integral.service.CustomerAddressService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
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
import com.thinkgem.jeesite.modules.entity.IntegralMallProductOrder;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductType;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductOrderService;
import com.thinkgem.jeesite.modules.integral.service.IntegralMallProductTypeService;

/**
 * 花生乐园订单Controller
 * @author lizibo
 * @version 2015-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/integralMallProductOrder")
public class IntegralMallProductOrderController extends BaseController {

	@Autowired
	private IntegralMallProductOrderService integralMallProductOrderService;
	@Autowired
	private IntegralMallProductTypeService integralMallProductTypeService;

	@Autowired
	private AreaService areaService;
	@Autowired
	private CustomerAddressService customerAddressService;
	
	@ModelAttribute
	public IntegralMallProductOrder get(@RequestParam(required=false) String id) {
		IntegralMallProductOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralMallProductOrderService.get(id);
		}
		if (entity == null){
			entity = new IntegralMallProductOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("integral:integralMallProductOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralMallProductOrder integralMallProductOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMallProductOrder> dd= new Page<IntegralMallProductOrder>(request, response);
		dd.setOrderBy(" a.id desc");
		Page<IntegralMallProductOrder> page = integralMallProductOrderService.findPage(dd, integralMallProductOrder); 
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		model.addAttribute("productTypeList", integralMallProductTypeService.findList(integralMallProductType));
		model.addAttribute("page", page);
		return "modules/integral/integralMallProductOrderList";
	}

	@RequiresPermissions("integral:integralMallProductOrder:view")
	@RequestMapping(value = "form")
	public String form(IntegralMallProductOrder integralMallProductOrder, Model model) {
		IntegralMallProductType integralMallProductType = new IntegralMallProductType();
		CustomerAddress address = integralMallProductOrder.getAddress();
		//获取省份、城市、区县名称
		if(address !=null && StringUtils.isNotBlank(address.getDistrictId())){
			Area district = areaService.get(address.getDistrictId());
			if(district != null) {
				integralMallProductOrder.getAddress().setDistrict(district);
				Area city = areaService.get(district.getParentId());
				if(city != null){
					integralMallProductOrder.getAddress().setCity(city);
					Area province = areaService.get(city.getParentId());
					if(province != null) {
						integralMallProductOrder.getAddress().setProvince(province);
					}
				}
			}
		}
		model.addAttribute("productTypeList", integralMallProductTypeService.findList(integralMallProductType));
		model.addAttribute("integralMallProductOrder", integralMallProductOrder);
		return "modules/integral/integralMallProductOrderForm";
	}

	@RequiresPermissions("integral:integralMallProductOrder:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMallProductOrder integralMallProductOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProductOrder)){
			return form(integralMallProductOrder, model);
		}
		integralMallProductOrderService.save(integralMallProductOrder);
		addMessage(redirectAttributes, "保存花生乐园订单成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductOrder/list";
	}
	
	/**
	 * 修改订单及地址信息
	 * @param integralMallProductOrder
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("integral:integralMallProductOrder:edit")
	@RequestMapping(value = "updateProductOrderAndAddress")
	public String updateProductOrderAndAddress(IntegralMallProductOrder integralMallProductOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMallProductOrder)){
			return form(integralMallProductOrder, model);
		}
		integralMallProductOrderService.updateProductOrderAndAddress(integralMallProductOrder);
		CustomerAddress address = integralMallProductOrder.getAddress();
		if(address != null){
			address.setAccountId(integralMallProductOrder.getCustomerAccount());
			address.setId(integralMallProductOrder.getAddressId()+"");
			address.setAddress(integralMallProductOrder.getAddressShow());
			customerAddressService.update(address);
		}
		addMessage(redirectAttributes, "修改订单成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductOrder/list";
	}
	
	@RequiresPermissions("integral:integralMallProductOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMallProductOrder integralMallProductOrder, RedirectAttributes redirectAttributes) {
		integralMallProductOrderService.delete(integralMallProductOrder);
		addMessage(redirectAttributes, "删除花生乐园订单成功");
		return "redirect:"+Global.getAdminPath()+"/integral/integralMallProductOrder/list";
	}

}