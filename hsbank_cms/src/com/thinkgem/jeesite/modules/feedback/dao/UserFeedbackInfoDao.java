/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.UserFeedbackInfo;

/**
 * 用户意见反馈DAO接口
 * @author lizibo
 * @version 2015-09-07
 */
@MyBatisDao
public interface UserFeedbackInfoDao extends CrudDao<UserFeedbackInfo> {
	
}