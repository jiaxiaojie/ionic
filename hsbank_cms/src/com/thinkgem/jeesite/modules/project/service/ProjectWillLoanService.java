/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectWillLoan;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectWillLoanDao;

/**
 * 借贷意向管理Service
 * @author yangtao
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectWillLoanService extends CrudService<ProjectWillLoanDao, ProjectWillLoan> {
	@Autowired
	ProjectWillLoanDao projectWillLoanDao;
	public ProjectWillLoan get(String id) {
		return super.get(id);
	}
	
	public List<ProjectWillLoan> findList(ProjectWillLoan projectWillLoan) {
		return super.findList(projectWillLoan);
	}
	
	public Page<ProjectWillLoan> findPage(Page<ProjectWillLoan> page, ProjectWillLoan projectWillLoan) {
		return super.findPage(page, projectWillLoan);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectWillLoan projectWillLoan) {
		super.save(projectWillLoan);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectWillLoan projectWillLoan) {
		super.delete(projectWillLoan);
	}
	
	/**
	 * 查询用户的借款申请意向
	 * @param projectWillLoan
	 * @return
	 */
	public List<ProjectWillLoan> findMyList1(Long accountId,Date startDate,Date endDate,String status) {
		return projectWillLoanDao.findMyList11(accountId,startDate,endDate,status);
	}
	
	/**
	 * 查询用户借贷申请中的项目总额度
	 * 被拒绝、已审批通过不包含在此额度中
	 * @param accountId
	 * @return
	 */
	public Double findNotEndApplyMoneyOfCustomer(String accountId){
		return projectWillLoanDao.findNotEndApplyMoneyOfCustomer(accountId);
	}

	/**
	 * 查询用户的借款申请意向
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public Page<ProjectWillLoan> findListByAccountIdAndStatus(Long accountId, String status, PageSearchBean pageSearchBean) {
		pageSearchBean.setDefaultDateRangeWithDays(-7);
		Page<ProjectWillLoan> page = new Page<ProjectWillLoan>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createUserId", accountId);
		map.put("startDate", pageSearchBean.getStartDateTime());
		map.put("endDate", pageSearchBean.getEndDateTime());
		map.put("status", status);
		map.put("page", page);
		page.setList(dao.findListByAccountIdAndStatus(map));
		return page;
	}

	/**
	 * 取消借款申请，此时借款申请应处于审核中状态，即projectWillLoan.status为（0）
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void cancelLoan(String id) {
		dao.cancelLoan(id);
	}
}