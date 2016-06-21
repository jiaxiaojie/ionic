package com.thinkgem.jeesite.modules.api.web;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.MyMessageResp;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.*;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 消息Controller
 *<p/>
 * @createDate 2016-5-13
 */
@Controller("apiWebMessageController")
@RequestMapping(value="${frontPath}/api/myMessage", method = RequestMethod.POST)
public class MessageController extends APIBaseController {
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageCreateRuleService messageCreateRuleService;
	@Autowired
	private CustomMessageService customMessageService;
	@Autowired
	private ReceiveMessageSwitchService receiveMessageSwitchService;

	/**
	 * 我的消息
	 * @param client
	 * @param token
	 * @param type
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("messagePageList")
	@ResponseBody
	public String messagePageList(String client, String token, String type, Integer pageSize, Integer pageNumber) {
		String opTerm = ApiUtil.getOperTerm(client);
		Map<String, Object> result = new HashMap<>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ) {
			pageSize = pageSize == null ? 10 : pageSize;
			pageNumber = pageNumber == null ? 1 : pageNumber;
			PageSearchBean pageSearchBean = new PageSearchBean();
			Long accountId = clientToken.getCustomerId();
			String messageChannel = ApiUtil.getPushChannel(opTerm);
			pageSearchBean.setDefaultDateRangeWithMonths(-3);
			Page<MessageInstance> page = messageInstanceService.getMyMessagePageList(accountId, type, messageChannel, pageSearchBean, pageNumber, pageSize);
			List<Object> dataList = new ArrayList<>();
			messageList(page.getList(), dataList);
			Map<String,Object> data = new HashMap();
			data.put("count", page.getCount());
			data.put("resultList", dataList);
			ApiUtil.mapRespData(result, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(result);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 统计信息
	 * @param client
	 * @param token
	 * @param type
	 * @return
	 */
	@RequestMapping("messageStatistic")
	@ResponseBody
	public String messageStatistic(String client, String token, String type) {
		String opTerm = ApiUtil.getOperTerm(client);
		Map<String, Object> result = new HashMap<>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ) {
			Long accountId = clientToken.getCustomerId();
			String messageChannel = ApiUtil.getPushChannel(opTerm);
			PageSearchBean pageSearchBean = new PageSearchBean();
			pageSearchBean.setDefaultDateRangeWithMonths(-3);
			Map<String,Object> data = new HashMap<>();
			Map<String,Object> map = new HashMap<>();
			map.put("accountId", accountId);
			map.put("type", type);
			map.put("messageChannel", messageChannel);
			map.put("statuses", new String[]{"0", "1", "2"});
			map.put("startDateTime", pageSearchBean.getStartDateTime());
			map.put("endDateTime", pageSearchBean.getEndDateTime());
			data.put("total", messageInstanceService.getCount(map));
			map.put("statuses", new String[]{"0", "1"});
			data.put("unreadCount", messageInstanceService.getCount(map));
			ApiUtil.mapRespData(result, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(result);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 获取消息设置信息
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping("settingInfo")
	@ResponseBody
	public String settingInfo(String client, String token) {
		Map<String, Object> result = new HashMap<>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null ) {
			Map<String,Object> data = new HashMap<>();
			Long accountId = clientToken.getCustomerId();
			Map<String,Object> accountWeb = new HashMap<>();
			accountWeb.put("type", "1");
			accountWeb.put("name", "站内信");
			accountWeb.put("isReceive", receiveMessageSwitchService.getIsReceive(accountId, MessageConstant.MESSAGE_TYPE_ACCOUNT, MessageConstant.MESSAGE_CHANNEL_WEB));
			Map<String,Object> accountSms = new HashMap<>();
			accountSms.put("type", "2");
			accountSms.put("name", "短信");
			accountSms.put("isReceive", receiveMessageSwitchService.getIsReceive(accountId, MessageConstant.MESSAGE_TYPE_ACCOUNT, MessageConstant.MESSAGE_CHANNEL_SMS));
			Map<String,Object> systemWeb = new HashMap<>();
			systemWeb.put("type", "3");
			systemWeb.put("name", "站内信");
			systemWeb.put("isReceive", receiveMessageSwitchService.getIsReceive(accountId, MessageConstant.MESSAGE_TYPE_SYSTEM, MessageConstant.MESSAGE_CHANNEL_WEB));
			Map<String,Object> systemSms = new HashMap<>();
			systemSms.put("type", "4");
			systemSms.put("name", "短信");
			systemSms.put("isReceive", receiveMessageSwitchService.getIsReceive(accountId, MessageConstant.MESSAGE_TYPE_SYSTEM, MessageConstant.MESSAGE_CHANNEL_SMS));
			data.put("accountWeb", accountWeb);
			data.put("accountSms", accountSms);
			data.put("systemWeb", systemWeb);
			data.put("systemSms", systemSms);
			ApiUtil.mapRespData(result, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(result);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 消息设置
	 * @param client
	 * @param token
	 * @param types
	 * @param isReceives
	 * @return
	 */
	@RequestMapping("messageSetting")
	@ResponseBody
	public String messageSetting(String client, String token, String[] types, String[] isReceives) {
		Map<String, Object> result = new HashMap<>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null ) {
			Long accountId = clientToken.getCustomerId();
			for(int i = 0; i < types.length; i++) {
				if(!(MessageConstant.IS_RECEIVE_MESSAGE_YES.equals(isReceives[i]) || MessageConstant.IS_RECEIVE_MESSAGE_NO.equals(isReceives[i]))
						|| !("1".equals(types[i]) || "2".equals(types[i]) || "3".equals(types[i]) || "4".equals(types[i]))) {
					throw new ServiceException("系统异常，请稍后重试");
				}
			}
			for(int i = 0; i < types.length; i++) {
				if("1".equals(types[i])) {
					receiveMessageSwitchService.settingSwitch(accountId, MessageConstant.MESSAGE_TYPE_ACCOUNT, MessageConstant.MESSAGE_CHANNEL_WEB, isReceives[i]);
				} else if("2".equals(types[i])) {
					receiveMessageSwitchService.settingSwitch(accountId, MessageConstant.MESSAGE_TYPE_ACCOUNT, MessageConstant.MESSAGE_CHANNEL_SMS, isReceives[i]);
				} else if("3".equals(types[i])) {
					receiveMessageSwitchService.settingSwitch(accountId, MessageConstant.MESSAGE_TYPE_SYSTEM, MessageConstant.MESSAGE_CHANNEL_WEB, isReceives[i]);
				} else if("4".equals(types[i])) {
					receiveMessageSwitchService.settingSwitch(accountId, MessageConstant.MESSAGE_TYPE_SYSTEM, MessageConstant.MESSAGE_CHANNEL_SMS, isReceives[i]);
				}
			}
			ApiUtil.mapRespData(result, ApiConstant.API_RESP_DATA_DEFAULT, "设置成功", true);
		}else{
			ApiUtil.tokenInvalidRespMap(result);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 消息更新(标记已读或删除)
	 * @param client
	 * @param token
	 * @param updateType
	 * @param messageIds
	 * @return
	 */
	@RequestMapping("updateMessage")
	@ResponseBody
	public String updateMessage(String client, String token, String updateType, String messageIds) {
		String opTerm = ApiUtil.getOperTerm(client);
		Map<String, Object> result = new HashMap<>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ) {
			Long accountId = clientToken.getCustomerId();
			String messageChannel = ApiUtil.getPushChannel(opTerm);
			if("1".equals(updateType)) {
				messageInstanceService.updateStatusByCustomer(accountId, ("all".equals(messageIds) ? null : messageIds.split(",")), messageChannel, "2", new Date(), null);
			} else if("2".equals(updateType)) {
				messageInstanceService.updateStatusByCustomer(accountId, ("all".equals(messageIds) ? null : messageIds.split(",")), messageChannel, "-1", null, new Date());
			} else {
				throw new ServiceException("系统异常，请稍后重试");
			}
			ApiUtil.mapRespData(result, ApiConstant.API_RESP_DATA_DEFAULT, "设置成功", true);
		}else{
			ApiUtil.tokenInvalidRespMap(result);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 数据转化
	 * @param list
	 * @param dataList
	 */
	public void messageList(List<MessageInstance> list, List<Object> dataList){
		for(MessageInstance mes : list){
			MyMessageResp mResp = new MyMessageResp();
			MyBeanUtils.copyBean2Bean(mResp, mes);
			mResp.setMessageId(mes.getId());
			mResp.setStatus(NumberUtil.toLong(mes.getStatus(), 0L));
			String statusName = "2".equals(mes.getStatus()) ? "已读" : "未读";
			mResp.setStatusName(statusName);
			mResp.setCreateDt(DateUtils.formatDateTime(mes.getCreateDt()));


			Message message = messageService.get(mes.getMessageId()==null?null:mes.getMessageId().toString());

			if(MessageConstant.MESSAGE_FROM_TYPE_RULE.equals(message.getFromType())){//如果是规则消息
				MessageCreateRule messageCreateRule = messageCreateRuleService.getByMessageId(mes.getMessageId());
				mResp.setTarget(messageCreateRule.getTarget());
				mResp.setTargetType( NumberUtils.parseInt(messageCreateRule.getTargetType(),null) );
				mResp.setIsClick( NumberUtils.parseInt(messageCreateRule.getIsClick(),0) );
				mResp.setIsMmergency(NumberUtils.parseInt(messageCreateRule.getIsUrgent(),0));
			}else if(MessageConstant.MESSAGE_FROM_TYPE_CUSTOM.equals(message.getFromType())){//如果是自定义产生
				CustomMessage customMessage =  customMessageService.getByMessageId(mes.getMessageId());
				mResp.setTarget(customMessage.getTarget());
				mResp.setTargetType( NumberUtils.parseInt(customMessage.getTargetType(),null) );
				mResp.setIsClick( NumberUtils.parseInt(customMessage.getIsClick(),0) );
				mResp.setIsMmergency(NumberUtils.parseInt(customMessage.getIsUrgent(),0));
			}
			dataList.add(mResp);
		}
	}
}
