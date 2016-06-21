/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.LogSendValidateCode;

/**
 * 验证码发送日志DAO接口
 * @author ydt
 * @version 2015-08-13
 */
@MyBatisDao
public interface LogSendValidateCodeDao extends CrudDao<LogSendValidateCode> {

	/**
	 * 获取发送给email的最后一条验证码
	 * @param email
	 * @return
	 */
	LogSendValidateCode getLastEmailValidateCodeRecordByEmail(@Param("email") String email);
	
}