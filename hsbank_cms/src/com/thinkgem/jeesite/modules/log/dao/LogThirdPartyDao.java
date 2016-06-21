/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;

/**
 * 第三方交互日志DAO接口
 * @author yangtao
 * @version 2015-08-03
 */
@MyBatisDao
public interface LogThirdPartyDao extends CrudDao<LogThirdParty> {

	/**
	 * 易宝callback通知处理
	 * @param requestNo
	 * @param respContent
	 * @param respCode
	 */
	void updateWithCallback(@Param("requestNo") String requestNo, @Param("respContent") String respContent,
							@Param("respCode") String respCode, @Param("respDt") Date respDt);

	/**
	 * 易宝notify通知处理
	 * @param requestNo
	 * @param respContent
	 * @param respCode
	 */
	void updateWithNotify(@Param("requestNo") String requestNo, @Param("notifyContent") String notifyContent,
						  @Param("notifyCode") String notifyCode, @Param("notifyDt") Date notifyDt);

	/**
	 * 根据requestNo获取日志
	 * @return
	 */
	LogThirdParty getByRequestNo(@Param("requestNo") String requestNo);
	
	/**
	 * 根据requestNo和service获取日志
	 * @param requestNo
	 * @param service
	 * @return
	 */
	LogThirdParty getByRequestNoAndService(@Param("requestNo") String requestNo, @Param("service") String service);
	
	/**
	 * 根据platformUserNo和service获取易宝callback成功的日志
	 * @param platformUserNo
	 * @param service
	 * @return
	 */
	LogThirdParty getByPlatformUserNoAndService(@Param("platformUserNo") String platformUserNo, @Param("service") String service);
	
	
	/**
	 * 更新对应的确认结果记录
	 * @param requestNo
	 * @param date
	 * @param confirmReqContent
	 * @param confirmRespContent
	 * @param respCode
	 */
	public void updateWithConfirm(@Param("requestNo") String requestNo, @Param("confirmDt") Date date, @Param("confirmReq") String confirmReqContent, @Param("confirmResp") String confirmRespContent, @Param("confirmCode") String respCode);
	
	
	/**
	 * 更新对应的查询结果记录
	 * @param requestNo
	 * @param date
	 * @param confirmReqContent
	 * @param confirmRespContent
	 * @param respCode
	 */
	public void updateWithQuery(@Param("requestNo") String requestNo, @Param("queryDt") Date date, @Param("queryReq") String queryReq, @Param("queryResp") String queryResp, @Param("queryCode") String respCode);
	
}