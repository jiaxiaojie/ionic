/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.integral.dao.CustomerAddressDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 花生乐园用户地址Service
 * @author lizibo
 * @version 2015-09-21
 */
@Service
@Transactional(readOnly = true)
public class CustomerAddressService extends CrudService<CustomerAddressDao, CustomerAddress> {
	@Autowired
	private CustomerAddressDao customerAddressDao;
	@Autowired
	private AreaDao areaDao;

	public CustomerAddress get(String id) {
		return super.get(id);
	}
	
	public List<CustomerAddress> findList(CustomerAddress customerAddress) {
		return super.findList(customerAddress);
	}
	
	public Page<CustomerAddress> findPage(Page<CustomerAddress> page, CustomerAddress customerAddress) {
		
		return super.findPage(page, customerAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerAddress customerAddress) {
		updateIsOrNotDefaultAddress(customerAddress);
		super.save(customerAddress);
	}

	/**
	 * 更新地址信息，更新策略是：只能更新自己账户下的地址，且会保证指定账户下默认地址只有一个
	 * @param customerAddress
     */
	@Transactional(readOnly = false)
	public void update(CustomerAddress customerAddress) {
		CustomerAddress ca = dao.get(customerAddress.getId());
		if(ca.getAccountId().longValue() != customerAddress.getAccountId().longValue()) {
			throw new ServiceException("只能修改本人的地址。");
		}
		updateIsOrNotDefaultAddress(customerAddress);
		customerAddressDao.update(customerAddress);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerAddress customerAddress) {
		super.delete(customerAddress);
	}

	/**
	 * 新增地址
	 * @param customerAddress
	 */
	@Transactional(readOnly = false)
	public void insert(CustomerAddress customerAddress) {
		customerAddress.setCreateDt(new Date());
		customerAddress.setStatus(ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL);
		
		updateIsOrNotDefaultAddress(customerAddress);
		customerAddressDao.insert(customerAddress);
		
		
	}

	/**
	 * 更新地址为是或不是默认地址
	 * @author wanduanrui
	 * @param customerAddress
	 */
	@Transactional(readOnly = false)
	private void updateIsOrNotDefaultAddress(CustomerAddress customerAddress) {
		if(ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_YES.equals(customerAddress.getIsDefault())){
			updateAllToNotDefaultAddress(customerAddress);
		}
	}
	
	
	
	/**
	 * 返回旧默认地址id，并将customerAddress设置为默认地址
	 * @param customerAddress
	 */
	@Transactional(readOnly = false)
	public String setDefaultAddress(CustomerAddress customerAddress) {
		
		CustomerAddress ca = dao.get(customerAddress.getId());
		if(ca.getAccountId().longValue() != customerAddress.getAccountId().longValue()) {
			throw new ServiceException("只能修改本人的地址。");
		}
		
		String oldDefaultAddressId = updateAllToNotDefaultAddress(customerAddress);
		customerAddress.setIsDefault(ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_YES);
		dao.update(customerAddress);
		return oldDefaultAddressId;
	}

	/**
	 * @author wanduanrui
	 * @param customerAddress
	 * @return
	 */
	private String updateAllToNotDefaultAddress(CustomerAddress customerAddress) {
		String oldDefaultAddressId = "";
		List<CustomerAddress> customerAddressList = dao.findListByAccountId(customerAddress.getAccountId(), ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL);
		for(CustomerAddress c : customerAddressList) {
			if(ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_YES.equals(c.getIsDefault())) {
				oldDefaultAddressId = c.getId();
				c.setIsDefault(ProjectConstant.CUSTOMER_ADDRESS_IS_DEFAULT_NOT);
				dao.update(c);
			} else {
				break;
			}
		}
		return oldDefaultAddressId;
	}
	
	

	/**
	 * 根据accountId获取可用地址列表
	 * @param accountId
	 * @return
	 */
	public List<CustomerAddress> findListByAccountId(Long accountId) {
		return dao.findListByAccountId(accountId, ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL);
	}


	/**
	 * 更新账户下的收货地址，有地址id则为更新，没有则为创建
	 * @param customerAddress
     */
	@Transactional(readOnly = false)
	public void updateAccountAddress(CustomerAddress customerAddress) {
		Area area = areaDao.get(customerAddress.getDistrictId());
		if(area != null && "4".equals(area.getType())){
			if(StringUtils.isBlank(customerAddress.getId())) {//新增
				insert(customerAddress);
			} else{//编辑
				update(customerAddress);
			}
		}else{
			throw new ServiceException("districtId不合法");
		}
	}

	/**
	 * 查询详细地址分页列表
	 * @param customerAddressPage
	 * @param customerAddress
     * @return
     */
	public Page<CustomerAddress> findDetailAddressPage(Page<CustomerAddress> customerAddressPage, CustomerAddress customerAddress) {
		customerAddress.setPage(customerAddressPage);
		return customerAddressPage.setList(dao.findDetailAddressList(customerAddress));
	}
}