/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.UserFeedbackInfo;
import com.thinkgem.jeesite.modules.feedback.dao.UserFeedbackInfoDao;

/**
 * 用户意见反馈Service
 * @author lizibo
 * @version 2015-09-07
 */
@Service
@Transactional(readOnly = true)
public class UserFeedbackInfoService extends CrudService<UserFeedbackInfoDao, UserFeedbackInfo> {

	public UserFeedbackInfo get(String id) {
		return super.get(id);
	}
	
	public List<UserFeedbackInfo> findList(UserFeedbackInfo userFeedbackInfo) {
		return super.findList(userFeedbackInfo);
	}
	
	public Page<UserFeedbackInfo> findPage(Page<UserFeedbackInfo> page, UserFeedbackInfo userFeedbackInfo) {
		return super.findPage(page, userFeedbackInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(UserFeedbackInfo userFeedbackInfo) {
		super.save(userFeedbackInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserFeedbackInfo userFeedbackInfo) {
		super.delete(userFeedbackInfo);
	}
	
}