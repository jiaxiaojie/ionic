/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.entity.AppMessagePushRecord;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.Message;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.AppMessagePushRecordDao;
import com.thinkgem.jeesite.modules.message.dao.MessageInstanceDao;
import com.thinkgem.jeesite.modules.message.utils.AppMessageUtils;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 消息实例Service
 * @author ydt
 * @version 2016-01-14
 */
@Service
@Transactional(readOnly = true)
public class MessageInstanceService extends CrudService<MessageInstanceDao, MessageInstance> {
	@Autowired
	private MessageInstanceDao messageInstanceDao;
	@Autowired
	private AppMessagePushRecordDao appMessagePushRecordDao;
	
	public MessageInstance get(String id) {
		return super.get(id);
	}
	
	public List<MessageInstance> findList(MessageInstance messageInstance) {
		return super.findList(messageInstance);
	}
	
	public Page<MessageInstance> findPage(Page<MessageInstance> page, MessageInstance messageInstance) {
		return super.findPage(page, messageInstance);
	}
	
	/**
	 * 消息查询
	 * @param page
	 * @param messageInstance
	 * @return
	 */
	public Page<MessageInstance> searchPageList(Page<MessageInstance> page, MessageInstance messageInstance) {
		messageInstance.setPage(page);
		page.setList(dao.searchPageList(messageInstance));
		return page;
	}
	
	/**
	 * 我的消息
	 * @param accountId
	 * @param type
	 * @param pageSearchBean
	 * @return
	 */
	public Page<MessageInstance> searchMyMessagePageList(Long accountId,String type, String messageChannel, PageSearchBean pageSearchBean) {
		Page<MessageInstance> page = new Page<MessageInstance>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("type", type);
		map.put("messageChannel", messageChannel);
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		page.setList(dao.searchMyMessagePageList(map));
		return page;
	}
	
	/**
	 * 消息分页
	 * @param accountId
	 * @param type
	 * @param messageChannel
	 * @param pageSearchBean
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<MessageInstance> getMyMessagePageList(Long accountId,String type, String messageChannel, PageSearchBean pageSearchBean, Integer pageNumber, Integer pageSize) {
		Page<MessageInstance> page = new Page<MessageInstance>(pageNumber, pageSize,true);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("type", type);
		map.put("messageChannel", messageChannel);
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		page.setList(dao.searchMyMessagePageList(map));
		return page;
	}
	
	/**
	 * 是否有未读消息 有未读消息返回 true 否则返回 false
	 * @param accountId
	 * @return
	 */
	public boolean hasRemindOfMsg(Long accountId,String opTerm) {
		if (accountId == null) {
			return false;
		}
		PageSearchBean pageSearchBean = new PageSearchBean();
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("messageChannel", ApiUtil.getPushChannel(opTerm));
		map.put("accountId", accountId);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		boolean hasRemindOfMsg = dao.hasRemindOfMsg(map);
		return hasRemindOfMsg;
	}
	
	/**
	 * 获取未读消息条数
	 * @param accountId
	 * @param type
	 * @param status
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	public int getUnreadCount(Long accountId, String type, String messageChannel,String status, Date startDateTime, Date endDateTime){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("messageChannel", messageChannel);
		map.put("status", status);
		map.put("accountId", accountId);
		map.put("startDateTime", startDateTime);
		map.put("endDateTime", endDateTime);
		return dao.getUnreadCount(map);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageInstance messageInstance) {
		super.save(messageInstance);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageInstance messageInstance) {
		super.delete(messageInstance);
	}

	/**
	 * 获取来源类型为messageFromType，渠道为messageChannel，状态为statuses的消息实例列表
	 * @param messageFromType
	 * @param messageChannel
	 * @param statuses
	 * @return
	 */
	public List<MessageInstance> findListByFromType(String messageFromType, String messageChannel, String... statuses) {
		return dao.findListByFromType(messageFromType, messageChannel, statuses);
	}

	/**
	 * 查询状态为status的app（channel为2或3）的messageInstance列表
	 * @param status
	 * @return
	 */
	public List<MessageInstance> findAppListByStatus(String status) {
		return dao.findAppListByStatus(status);
	}

	/**
	 * 批量更新状态
	 * @param ids
	 * @param status
	 * @param sendDate
	 * @param readDate
	 * @param deleteDate
	 */
	@Transactional(readOnly = false)
	public void updateStatusBatch(List<Long> ids, String status, Date sendDate, Date readDate, Date deleteDate) {
		dao.updateStatusBatch(ids, status, sendDate, readDate, deleteDate);
	}

	/**
	 * 消息发送统计
	 * @param messageInstance
	 * @param beginOpDt
	 * @param endOpDt
	 * @param customerBase
	 * @return
	 */
	public List<Map<String, Object>> selectMessageNum(MessageInstance messageInstance,
			Date beginOpDt, Date endOpDt,CustomerBase customerBase,Message message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", message.getType());
		map.put("customerName",customerBase.getCustomerName());
		map.put("beginOpDt", beginOpDt);
		map.put("endOpDt", endOpDt);
		return dao.selectMessageNum(map);
	}


	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @param sendDate		可为空
	 * @param readDate		可为空
	 * @param deleteDate	可为空
	 */
	@Transactional(readOnly = false)
	public void updateStatus(Long id, String status, Date sendDate, Date readDate, Date deleteDate) {
		dao.updateStatus(id, status, sendDate, readDate, deleteDate);
	}
	
	@Transactional(readOnly = false)
	public int insertMessageInstance(MessageInstance messageInstance) {
		return messageInstanceDao.insert(messageInstance);
		
	}

	/**
	 * 推送app消息
	 * @param messageInstance
	 */
	@Transactional(readOnly = false)
	public void pushMessage(MessageInstance messageInstance) {
		try {
			JSONObject jsonObject = AppMessageUtils.push(messageInstance);
			AppMessagePushRecord appMessagePushRecord = new AppMessagePushRecord();
			appMessagePushRecord.setMessageInstanceId(Long.parseLong(messageInstance.getId()));
			appMessagePushRecord.setPushDt(new Date());
			appMessagePushRecord.setResultData(jsonObject.toString());
			appMessagePushRecordDao.insert(appMessagePushRecord);
			messageInstanceDao.updateStatus(Long.parseLong(messageInstance.getId()), MessageConstant.MESSAGE_STATUS_SEND, new Date(), null, null);
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("push message exception, meesageInstance id:" + messageInstance.getId() + ".");
		}
	}



	public MessageInstance getLatestEmergencyMessage(Long customerId, String messageChannel) {
		return messageInstanceDao.getLatestEmergencyMessage(customerId,messageChannel);
	}

	/**
	 * 获取消息数量
	 * @param map(keys: accountId Long, type String, messageChannel String, status String, statuses String[])
	 * @return
	 */
	public Integer getCount(Map<String,Object> map) {
		return dao.getCount(map);
	}

	/**
	 * 更新用户的消息状态
	 * @param accountId
	 * @param ids
	 * @param messageChannel
	 * @param status
	 * @param readDate
	 * @param deleteDate
	 */
	@Transactional(readOnly = false)
	public void updateStatusByCustomer(Long accountId, String[] ids, String messageChannel, String status, Date readDate, Date deleteDate) {
		dao.updateStatusByCustomer(accountId, ids, messageChannel, status, readDate, deleteDate);
	}
}