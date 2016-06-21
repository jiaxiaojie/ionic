/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.service;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.carousel.dao.CarouselInfoDao;
import com.thinkgem.jeesite.modules.carousel.dao.CarouselShowTermDao;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;
import com.thinkgem.jeesite.modules.entity.CarouselShowTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 轮播图管理Service
 * @author hyc
 * @version 2015-11-11
 */
@Service
@Transactional(readOnly = true)
public class CarouselInfoService extends CrudService<CarouselInfoDao, CarouselInfo> {
	
	
	@Autowired
	private CarouselInfoDao carouselInfoDao;
	@Autowired
	private CarouselShowTermDao carouselShowTermDao;

	public CarouselInfo get(String id) {
		return super.get(id);
	}
	
	public List<CarouselInfo> findList(CarouselInfo carouselInfo) {
		return super.findList(carouselInfo);
	}
	
	public Page<CarouselInfo> findPage(Page<CarouselInfo> page, CarouselInfo carouselInfo) {
		return super.findPage(page, carouselInfo);
	}
	
	/**
	 * 审核
	 * @param carouselInfo
	 */
	@Transactional(readOnly = false)
	public void review(CarouselInfo carouselInfo) {
		carouselInfoDao.update(carouselInfo);
	}

	
	@Transactional(readOnly = false)
	public void save(CarouselInfo carouselInfo) {
		if (carouselInfo.getCarouselId()!= null && !"".equals(carouselInfo.getCarouselId())) {
			carouselInfoDao.update(carouselInfo);
		} else {
			carouselInfoDao.insert(carouselInfo);
		}
		Long carouselId = carouselInfo.getCarouselId();
		// 维护活动渠道限制数据
		List<String> termCodeList = carouselInfo.getTermCodeList();
		if (termCodeList != null && termCodeList.size() > 0) {
			this.showTermData(termCodeList, carouselId);
		}
	}

	/**
	 * 显示终端数据
	 * 
	 * @param termCodeList
	 * @param carouselId
	 */
	public void showTermData(List<String> termCodeList, Long carouselId) {
		List<CarouselShowTerm> carouselChannelList = new ArrayList<CarouselShowTerm>();
		for (String termCode : termCodeList) {
			CarouselShowTerm showTerm = new CarouselShowTerm();
			showTerm.setCarouselId(carouselId);
			showTerm.setTermCode(NumberUtil.toLong(termCode, 0L));
			carouselChannelList.add(showTerm);
		}
		// 先删除（根据轮播图编号）
		carouselShowTermDao.deleteByCarouselId(carouselId);
		// 批量插入
		carouselShowTermDao.insertBatch(carouselChannelList);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(CarouselInfo carouselInfo) {
		super.delete(carouselInfo);
		Long carouselId = carouselInfo.getCarouselId();
		// 删除终端限制（根据轮播图编号）
		carouselShowTermDao.deleteByCarouselId(carouselId);
		
	}
	
	
	public List<CarouselInfo> findAllList() {
		return dao.findAllList(new CarouselInfo());
	}
	
/**
	 * 获取轮播图列表
	 * @param status
	 * @param term
	 * @return
	 */
	public List<CarouselInfo> getCarouselListByStatusAndTermAndShow(String status, String term){
		List<CarouselInfo> list = dao.getCarouselListByStatusAndTermAndShow(status, term);
		for(CarouselInfo info :list){
			if(info.getActivityTime() != null && new Date().compareTo(info.getActivityTime()) < 0){
				info.setDispaly("1");
			}else{
			    info.setActivityTime(new Date());
				info.setDispaly("0");
			}
		}
		return list;
	}
	/**
	 * 根据状态及操作终端获取列表
	 * @param status
	 * @param term
	 * @return
	 */
	public List<CarouselInfo> getCarouselListByStatusAndTerm(String status, String term,String isNewWebsite, String theDate){
		List<CarouselInfo> list = dao.getCarouselListByStatusAndTerm(status, term,isNewWebsite, theDate);
		for(CarouselInfo info :list){
			if(info.getActivityTime() != null && new Date().compareTo(info.getActivityTime()) < 0){
				info.setDispaly("1");
			}else{
			    info.setActivityTime(new Date());
				info.setDispaly("0");
			}
		}
		return list;
	}
	
	/**
	 * 分页查询
	 * @param status
	 * @param term
	 * @param theDate
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CarouselInfo> getCarouselListPage(String status, String term, String theDate, Integer pageNumber, Integer pageSize){
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("status", status);
		map.put("term", term);
		map.put("theDate", theDate);
		return dao.getCarouselListPage(map);
	}
	
	
}