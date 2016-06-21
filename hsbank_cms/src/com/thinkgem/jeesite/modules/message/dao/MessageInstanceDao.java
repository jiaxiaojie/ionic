/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.entity.ReceiveMessageSwitch;

/**
 * 消息实例DAO接口
 * @author ydt
 * @version 2016-01-14
 */
@MyBatisDao
public interface MessageInstanceDao extends CrudDao<MessageInstance> {

	/**
	 * 根据accountId、channel获取列表
	 * @param map
	 * @return
	 */
	List<MessageInstance> findListByAccountIdAndMessageChannel(Map<String, Object> map);
	
	/**
	 * 消息查询
	 * @param map
	 * @return
	 */
	List<MessageInstance> searchPageList(MessageInstance messageInstance);
	
	/**
	 * 我的消息
	 * @param map
	 * @return
	 */
	List<MessageInstance> searchMyMessagePageList(Map<String, Object> map);
	
	/**
	 * 是否有未读消息
	 * @param map
	 * @return
	 */
	boolean hasRemindOfMsg(Map<String, Object> map);
	
	/**
	 * 获取未读消息的条数
	 * @param map
	 * @return
	 */
	int getUnreadCount(Map<String, Object> map);

	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @param sendDate		可为空
	 * @param readDate		可为空
	 * @param deleteDate	可为空
	 */
	void updateStatus(@Param("id") Long id, @Param("status") String status,
					  @Param("sendDt") Date sendDt, @Param("readDt") Date readDt, @Param("deleteDt") Date deleteDt);

	/**
	 * 获取来源类型为messageFromType，状态为statuses的消息实例列表
	 * @param messageFromType
	 * @param messageChannel
	 * @param statuses
	 * @return
	 */
	List<MessageInstance> findListByFromType(@Param("messageFromType") String messageFromType,
											 @Param("messageChannel") String messageChannel, @Param("statuses") String[] statuses);

	/**
	 * 查询状态为status的app（channel为2或3）的messageInstance列表
	 * @param status
	 * @return
	 */
	List<MessageInstance> findAppListByStatus(@Param("status") String status);

	/**
	 * 批量更新状态
	 * @param ids
	 * @param status
	 */
	void updateStatusBatch(@Param("ids") List<Long> ids, @Param("status") String status,
						   @Param("sendDt") Date sendDt, @Param("readDt") Date readDt, @Param("deleteDt") Date deleteDt);
	
	/**
	 * 消息发送统计
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectMessageNum(Map<String, Object> map);

	
	
	/**
	 * 根据消息开关设置，将状态置为过期
	 * @param accountId
	 * @return
	 */
	void expireMessageSwitch(ReceiveMessageSwitch receiveMessageSwitch);

	MessageInstance getLatestEmergencyMessage(@Param("accountId") Long customerId, @Param("messageChannel") String messageChannel);

	/**
	 * 获取消息数量
	 * @param map(keys: accountId Long, type String, messageChannel String, status String, statuses String[])
	 * @return
	 */
	Integer getCount(Map<String, Object> map);

	/**
	 * 更新用户的消息状态
	 * @param accountId
	 * @param ids
	 * @param messageChannel
	 * @param status
	 * @param readDate
	 * @param deleteDate
	 */
	void updateStatusByCustomer(@Param("accountId") Long accountId, @Param("ids") String[] ids, @Param("messageChannel") String messageChannel,
								@Param("status") String status, @Param("readDate") Date readDate, @Param("deleteDate") Date deleteDate);
}