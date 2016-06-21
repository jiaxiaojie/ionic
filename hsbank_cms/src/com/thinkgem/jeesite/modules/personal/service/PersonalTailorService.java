/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personal.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.po.PersonalTailorReq;
import com.thinkgem.jeesite.modules.api.to.PersonalTailorResp;
import com.thinkgem.jeesite.modules.entity.PersonalTailor;
import com.thinkgem.jeesite.modules.personal.dao.PersonalTailorDao;

/**
 * 私人订制项目Service
 * @author yubin
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class PersonalTailorService extends CrudService<PersonalTailorDao, PersonalTailor> {

	public PersonalTailor get(String id) {
		return super.get(id);
	}
	
	public List<PersonalTailor> findList(PersonalTailor personalTailor) {
		return super.findList(personalTailor);
	}
	
	public Page<PersonalTailor> findPage(Page<PersonalTailor> page, PersonalTailor personalTailor) {
		return super.findPage(page, personalTailor);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonalTailor personalTailor) {
		super.save(personalTailor);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonalTailor personalTailor) {
		super.delete(personalTailor);
	}

	public void queryPersonalList(PersonalTailorReq personalTailor,HashMap<String, Object> map,HttpServletRequest request) throws  Exception{
		Integer count=0;
		HashMap<String, Object> data=new HashMap<String, Object>();
		List<PersonalTailorResp>personalTailorResps=
				                dao.queryPersonalList((
								personalTailor.getPageNumber()-1)*personalTailor.getPageSize(),
								personalTailor.getPageSize());
		if(personalTailor.getFlag().equals(1)){
              count=this.countPersonalList();
		}
		if(personalTailorResps!=null&& personalTailorResps.size()>0){
			for(PersonalTailorResp p:personalTailorResps){
				p.setDescPic(ApiUtil.prefixSrc(p.getDescPic()));
			}
		}
		data.put("resultList",personalTailorResps);
		data.put("count",count);
		ApiUtil.mapRespData(map,data,"正常", true);
	}
	public Integer countPersonalList()throws  Exception{
		return dao.countPersonalList();
	}
	public PersonalTailorResp findByPersonalId(PersonalTailorReq personalTailor)throws  Exception{
		return dao.findByPersonalId(personalTailor.getProjectId());
	}

	/**
	 * 获取可投资项目数量
	 * @return
	 */
	public Integer getCanInvestmentCount() {
		return dao.getCanInvestmentCount();
	}
	/**
	 * 
	 * <p>
	 * Description:查询项目名是否重复<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年6月1日
	 * @param name
	 * @return
	 * PersonalTailor
	 */
	public PersonalTailor gePersonalTailorByName(String name){
		return dao.gePersonalTailorByName(name);
	}
}