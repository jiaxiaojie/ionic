/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralSnapshotDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralSnapshot;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductOrder;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductPrice;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.integral.IntegralConstant;
import com.thinkgem.jeesite.modules.integral.dao.CustomerAddressDao;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductDao;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductOrderDao;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductPriceDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.dao.InvestmentTicketTypeDao;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import com.hsbank.util.type.NumberUtil;

/**
 * 花生乐园订单Service
 * @author lizibo
 * @version 2015-09-21
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallProductOrderService extends CrudService<IntegralMallProductOrderDao, IntegralMallProductOrder> {
	@Autowired
	private IntegralMallProductOrderDao integralMallProductOrderDao;
	@Autowired
	private CustomerAddressDao customerAddressDao;
	@Autowired
	private IntegralMallProductDao integralMallProductDao;
	@Autowired
	private CustomerIntegralSnapshotDao customerIntegralSnapshotDao;
	@Autowired
	private IntegralMallProductPriceDao integralMallProductPriceDao;
	
	
	@Autowired
	private InvestmentTicketTypeDao investmentTicketTypeDao;
	@Autowired
	private CustomerInvestmentTicketDao customerInvestmentTicketDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private CustomerBalanceHandler customerBalanceHandler;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private CustomerIntegralHisDao customerIntegralHisDao;
	
	

	public IntegralMallProductOrder get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMallProductOrder> findList(IntegralMallProductOrder integralMallProductOrder) {
		return super.findList(integralMallProductOrder);
	}
	
	public Page<IntegralMallProductOrder> findPage(Page<IntegralMallProductOrder> page, IntegralMallProductOrder integralMallProductOrder) {
		return super.findPage(page, integralMallProductOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMallProductOrder integralMallProductOrder) {
		super.save(integralMallProductOrder);
	}
	
	@Transactional(readOnly = false)
	public void updateProductOrderAndAddress(IntegralMallProductOrder integralMallProductOrder) {
		integralMallProductOrderDao.update(integralMallProductOrder);
		
		if(ProjectConstant.INTEGRAL_MALL_ORDER_STATUS_CANCEL.equals(integralMallProductOrder.getOrderStatus())) {//如果是取消状态
			IntegralMallProduct integralMallProduct = integralMallProductDao.get(integralMallProductOrder.getProductId() + "");
			CustomerIntegralSnapshot customerIntegralSnapshot = customerIntegralSnapshotDao.getByAccountId(integralMallProductOrder.getCustomerAccount());
			//1.更改商品库存
			integralMallProduct.setProductSurplus(integralMallProduct.getProductSurplus() + integralMallProductOrder.getProductCount());
			integralMallProductDao.update(integralMallProduct);
			//2.更改用户花生豆值
			customerIntegralSnapshot.setIntegralBalance(customerIntegralSnapshot.getIntegralBalance() + integralMallProductOrder.getPrice());
			customerIntegralSnapshotDao.update(customerIntegralSnapshot);
			//3.添加花生豆变更流水
			CustomerIntegralHis customerIntegralHis = new CustomerIntegralHis();
			customerIntegralHis.setAccountId(integralMallProductOrder.getCustomerAccount());
			customerIntegralHis.setChangeVal(integralMallProductOrder.getPrice());
			customerIntegralHis.setChangeReason("");
			customerIntegralHis.setExchangeGoods(integralMallProductOrder.getProductId() + "");
			customerIntegralHis.setOpDt(new Date());
			customerIntegralHis.setChangeType(ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_CANCEL_EXPENSE);
			customerIntegralHis.setOpTermType(ProjectConstant.OP_TERM_DICT_PC);
			customerIntegralHisDao.insert(customerIntegralHis);
		}/**/
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMallProductOrder integralMallProductOrder) {
		super.delete(integralMallProductOrder);
	}

	/**
	 * 购买/兑换 商品
	 * 		若花生豆、库存足够
	 * 			1.更改商品库存
	 * 			2.更改用户花生豆值
	 * 			3.添加花生豆变更流水
	 * 			4.新增兑换订单
	 * @param accountId
	 * @param productId
	 * @param addressId
	 * @param count
	 */
	@Transactional(readOnly = false)
	public Map<String,Object> buy(Long accountId, String productId, String addressId, int count, String opTerm) {
		Map<String,Object> result = new HashMap<String,Object>();
		IntegralMallProduct integralMallProduct = integralMallProductDao.get(productId);
		integralMallProduct.setShowPrice(getRightShowPrice(integralMallProduct.getProductId().longValue()+"", integralMallProduct.getPrice()));
		long totalCost = count * integralMallProduct.getShowPrice();
		CustomerIntegralSnapshot customerIntegralSnapshot = customerIntegralSnapshotDao.getByAccountId(accountId);
		//产品校验
		checkMallProduct(integralMallProduct);
		//地址校验
		checkAddress(accountId, addressId);
		if(count <= 0) {
			result.put("isSuccess", false);
			result.put("message", "购买数量不合法！");
		} else if(customerIntegralSnapshot.getIntegralBalance() < totalCost) {
			result.put("isSuccess", false);
			result.put("message", "当前花生豆不足，请确认！");
		} else if(integralMallProduct.getProductSurplus() < count) {
			result.put("isSuccess", false);
			result.put("message", "库存不足！");
		} else {
			//1.更改商品库存
			integralMallProduct.setProductSurplus(integralMallProduct.getProductSurplus() - count);
			integralMallProductDao.update(integralMallProduct);
			//2.处理用户积分信息
			String integralChangeReason = "兑换商品：" + integralMallProduct.getProductName() + "，兑换数量：" + count + "。";
			customerIntegralSnapshotHandler.changeIntegralValue(accountId, -(int)totalCost,
					ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_EXPENSE, integralChangeReason, opTerm);
	 		//3.新增兑换订单
			//IntegralMallProductType integralMallProductType = integralMallProductTypeDao.get(integralMallProduct.getProductTypeId() + "");
			CustomerAddress customerAddress = customerAddressDao.get(addressId);
			if(customerAddress == null){
				customerAddress = new CustomerAddress();
			}
			IntegralMallProductOrder integralMallProductOrder = new IntegralMallProductOrder();
			integralMallProductOrder.setProductTypeId(integralMallProduct.getProductTypeId() + "");
			integralMallProductOrder.setProductId(new Long(productId));
			integralMallProductOrder.setProductCount(count);
			integralMallProductOrder.setProductPrice(integralMallProduct.getShowPrice());
			integralMallProductOrder.setPrice((int)totalCost);
			integralMallProductOrder.setCustomerAccount(accountId);
			integralMallProductOrder.setAddressId(NumberUtil.toLong(addressId, 0L));
			Date createDt = new Date();
			integralMallProductOrder.setCreateDt(createDt);
			integralMallProductOrder.setCreateChannelId(opTerm);
			String orderNo = generateOrderNo();
			integralMallProductOrder.setOrderNo(orderNo);
			integralMallProductOrder.setShowName(customerAddress.getShowName());
			integralMallProductOrder.setMobile(customerAddress.getMobile());
			integralMallProductOrder.setAddressShow(customerAddress.getAddress());
			integralMallProductOrder.setPostCode(customerAddress.getPostCode());
			integralMallProductOrder.setDistrictIds(customerAddress.getDistrictId());
			String productTypeId = integralMallProduct.getProductTypeId()+"";
			//实物
			if(ProjectConstant.MARKETING_PRODUCT_TYPE_OBJECT.equals(productTypeId)) {
				if((addressId == null || "".equals(addressId)) && integralMallProductOrder.getAddressId()==0l){
					throw new ServiceException("实物请选择收货地址！");
				}
				integralMallProductOrder.setOrderStatus(ProjectConstant.INTEGRAL_MALL_ORDER_STATUS_ORDER);
				result.put("message", "恭喜你，订单提交成功！");
			}
			else //虚拟物品
			{
				
				//现金
				if(ProjectConstant.MARKETING_PRODUCT_TYPE_MONEY.equals(productTypeId)){
					for(int i = 0; i < count; i++) {
					yeepayCommonHandler.transferToCustomerFromPlatform(accountId, integralMallProduct.getProductParValue(), ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT,
							"花生豆兑换","");
					}
				}//免费提现次数
				else if(ProjectConstant.MARKETING_PRODUCT_TYPE_FREE_WITHDRAW_COUNT.equals(productTypeId)){
					for(int i = 0; i < count; i++) {
					customerBalanceHandler.updateFreeWithCount(accountId, integralMallProduct.getProductParValue(), ProjectConstant.FREE_WITHDRAW_COUNT_CHANGE_TYPE_PEANUT_EXCHANGE);
					}
				}
				else if(ProjectConstant.MARKETING_PRODUCT_TYPE_INVESTMENT_TICKET.equals(productTypeId)){	//现金券
					InvestmentTicketType investmentTicketType = investmentTicketTypeDao.get(integralMallProduct.getRelTicketId() + "");
					for(int i = 0; i < count; i++) {
						CustomerInvestmentTicket customerInvestmentTicket = new CustomerInvestmentTicket();
						customerInvestmentTicket.setAccountId(accountId);
						customerInvestmentTicket.setTicketTypeId(new Long(investmentTicketType.getId()));
						customerInvestmentTicket.setGetDt(new Date());
						customerInvestmentTicket.setGetRemark("花生豆兑换");
						customerInvestmentTicket.setInvalidDt(DateUtils.dateAddDay(DateUtils.dateFormate(new Date()), investmentTicketType.getTermOfValidity()));
						customerInvestmentTicket.setStatus(ProjectConstant.CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL);
						customerInvestmentTicketDao.insert(customerInvestmentTicket);
					}
				}
				
				integralMallProductOrder.setOrderStatus(ProjectConstant.INTEGRAL_MALL_ORDER_STATUS_FINISH);
				result.put("message", "恭喜你，兑换物品成功！");
			}
			
			integralMallProductOrderDao.insert(integralMallProductOrder);
			result.put("isSuccess", true);
			result.put("orderNo", orderNo);
			result.put("createDt", DateUtils.formatDateTime(createDt));
		}
		CustomerIntegralSnapshot cis = customerIntegralSnapshotDao.getByAccountId(accountId);
		if(cis.getIntegralBalance() < 0) {
			throw new ServiceException("花生豆不足，兑换失败！");
		}
		return result;
	}
	
	/**
	 * 产品校验
	 * @param integralMallProduct
	 */
	public void checkMallProduct(IntegralMallProduct integralMallProduct){
		Date theDate = new Date();
		if(!IntegralConstant.INTEGRAL_PROJECT_STATUS_REVIEW_PASS.equals(integralMallProduct.getStatus())){
			throw new ServiceException("产品未审批通过，您不能下单");
		}else if(theDate.compareTo(integralMallProduct.getUpDt()) < 0){
			throw new ServiceException("产品未上架");
		}else if(theDate.compareTo(integralMallProduct.getDowDt()) > 0){
			throw new ServiceException("产品已下架");
		}
	}
	
	/**
	 * 收货地址校验
	 * @param accountId
	 * @param addressId
	 */
	public void checkAddress(Long accountId, String addressId){
		if(StringUtils.isNotBlank(addressId)){
			CustomerAddress customerAddress = customerAddressDao.get(addressId);
			if(!ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL.equals(customerAddress.getStatus())){
				throw new ServiceException("收货地址无效");
			}else if(!String.valueOf(accountId).equals(String.valueOf(customerAddress.getAccountId()))){
				throw new ServiceException("收货地址不属于该账户");
			}
		}
	}
	/**
	 * 生成17位数字订单号
	 * @return
	 */
	private String generateOrderNo() {
		return new Date().getTime()+ "" + ((int)(Math.random() * 900) +100);
	}
	
	/**
	 * 根据产品价格策略获得当前有效的价格
	 * @param productId
	 * @param oldPrice
	 * @return
	 */
	public int getRightShowPrice(String productId, int oldPrice){
		List<IntegralMallProductPrice> list = integralMallProductPriceDao.getListByProductId(productId);
		if((list==null)||(list.size()==0)){
			return oldPrice;
		}else{
			IntegralMallProductPrice item=list.get(0);
			if(item.getPriceType().equals("1")){
				return item.getMarketNewPrice();
			}else{
				return (int)(item.getMarketDiscount() * oldPrice);
			}
		}
	}
	
	public List<IntegralMallProductOrder> getLastOrders(int limit){
		return integralMallProductOrderDao.getLastOrders(limit);
	}

	public Page<IntegralMallProductOrder> findCustomerList(IntegralMallProductOrder integralMallProductOrder, String pageNo) {
		Page<IntegralMallProductOrder> page = new Page<IntegralMallProductOrder>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		integralMallProductOrder.setPage(page);
		page.setList(integralMallProductOrderDao.findCustomerList(integralMallProductOrder));
		return page;
	}

	public IntegralMallProductOrder getByOrderNo(String orderNo, long accountId) {
		IntegralMallProductOrder integralMallProductOrder = dao.getByOrderNo(orderNo);
		if(integralMallProductOrder.getCustomerAccount().longValue() != accountId) {
			throw new ServiceException("只能查看本人的数据。");
		}
		return integralMallProductOrder;
	}
	
	/**
	 * 分页查询订单列表
	 * @param accountId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<IntegralMallProductOrder> findPageList(Long accountId, Integer pageNumber, Integer pageSize){
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("accountId", accountId);
		return dao.findPageList(map);
	}

	/**
	 * API
	 * 我的花生-兑换记录(订单)分页列表
	 * @param accountId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<IntegralMallProductOrder> getOrderPageList(Integer searchFlag, Long accountId, Integer pageNumber, Integer pageSize){
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		PageSearchBean pageSearchBean = new PageSearchBean();
		if(searchFlag == 1){//1个月内数据
			pageSearchBean.setDefaultDateRangeWithMonths(-1);
		}else if(searchFlag == 2){//3个月内数据
			pageSearchBean.setDefaultDateRangeWithMonths(-3);
		}else{//6个月内数据
			pageSearchBean.setDefaultDateRangeWithMonths(-6);
		}
		return dao.getOrderPageList(accountId,pageBean.getStartNumber(),pageBean.getEndNumber(),pageSearchBean.getStartDateTime(),pageSearchBean.getEndDateTime());
	}

	/**
	 * API
	 * 我的订单总数量
	 * @param accountId
	 * @return
	 */
	public long countOrderByAccountId(Integer searchFlag,Long accountId){
		PageSearchBean pageSearchBean = new PageSearchBean();
		if(searchFlag == 1){//1个月内数据
			pageSearchBean.setDefaultDateRangeWithMonths(-1);
		}else if(searchFlag == 2){//3个月内数据
			pageSearchBean.setDefaultDateRangeWithMonths(-3);
		}else{//6个月内数据
			pageSearchBean.setDefaultDateRangeWithMonths(-6);
		}
		return dao.countOrderByAccountId(accountId,pageSearchBean.getStartDateTime(),pageSearchBean.getEndDateTime());
	}


	/**
	 * API
	 * 我的花生-兑换记录(订单)详情
	 * @param accountId
	 * @param orderNo
	 * @return
	 */
	public IntegralMallProductOrder getDetailsByOrderNo(Long accountId, String orderNo) {
		IntegralMallProductOrder integralMallProductOrder = dao.getDetailsByOrderNo(accountId, orderNo);
		return integralMallProductOrder;
	}
	/**
	 * API
	 * 花生乐园-参与记录
	 * @param limit
	 * @return
	 */
	public List<IntegralMallProductOrder>  getOrderListByLast(int limit) {
		return dao.getOrderListByLast(limit);
	}
}