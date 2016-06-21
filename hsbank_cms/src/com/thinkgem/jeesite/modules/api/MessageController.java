package com.thinkgem.jeesite.modules.api;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils.ConvertAction;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.MyMessageResp;
import com.thinkgem.jeesite.modules.entity.CustomMessage;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.Message;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.CustomMessageService;
import com.thinkgem.jeesite.modules.message.service.MessageCreateRuleService;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.message.service.MessageService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.hsbank.util.type.NumberUtil;

/**
 * 消息Controller
 * 
 * @author lzb
 * @version 2016-02-17
 */
@Controller("apiMessageController")
@RequestMapping(value="${frontPath}/api/message",method=RequestMethod.POST)
public class MessageController extends APIBaseController{
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	private CustomMessageService customMessageService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageCreateRuleService messageCreateRuleService;
	
	/**
	 * 我的消息-账户消息
	 * @param response
	 * @param client
	 * @param token
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "accountMessagePageList")
	public String accountMessagePageList(HttpServletResponse response, String client, String token, 
			@RequestParam(required=false,defaultValue="10")Integer pageSize, 
			@RequestParam(required=false,defaultValue="1")Integer pageNumber) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api accountMessagePageList start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null ){
			PageSearchBean pageSearchBean = new PageSearchBean();
			Long accountId = clientToken.getCustomerId();
			String type = MessageConstant.MESSAGE_TYPE_ACCOUNT;
			String messageChannel = ApiUtil.getPushChannel(opTerm);
			pageSearchBean.setDefaultDateRangeWithMonths(-3);
			Page<MessageInstance> page = messageInstanceService.getMyMessagePageList(accountId, type, messageChannel, pageSearchBean, pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			this.messageList(page.getList(), dataList);
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api accountMessagePageList end...");
		logger.info("api accountMessagePageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 我的消息-系统消息
	 * @param response
	 * @param client
	 * @param token
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "systemMessagePageList")
	public String systemMessagePageList(HttpServletResponse response, String client, String token, 
			@RequestParam(required=false,defaultValue="10")Integer pageSize, 
			@RequestParam(required=false,defaultValue="1")Integer pageNumber) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api systemMessagePageList start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null ){
			PageSearchBean pageSearchBean = new PageSearchBean();
			Long accountId = clientToken.getCustomerId();
			String type = MessageConstant.MESSAGE_TYPE_SYSTEM;
			String messageChannel = ApiUtil.getPushChannel(opTerm);
			pageSearchBean.setDefaultDateRangeWithMonths(-3);
			Page<MessageInstance> page = messageInstanceService.getMyMessagePageList(accountId, type, messageChannel, pageSearchBean, pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			this.messageList(page.getList(), dataList);
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api systemMessagePageList end...");
		logger.info("api systemMessagePageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
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
	
	/**
	 * 标记为已读
	 * @param response
	 * @param client
	 * @param token
	 * @param messageIds
	 * @return
	 */
	@RequestMapping(value = "read")
	public String read(HttpServletResponse response, String client, String token, String messageIds) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api read start...");
		logger.info("messageIds:" + messageIds);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			if(StringUtils.isNotBlank(messageIds)){
				String[] messageStrs =  StringUtils.toArray(messageIds);
				List<Long> messageIdList = new ArrayList<Long>();
				for(String id : messageStrs){
					messageIdList.add(NumberUtil.toLong(id, 0L));
				}
				messageInstanceService.updateStatusBatch(messageIdList, MessageConstant.MESSAGE_STATUS_READ, null, new Date(), null);
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "消息ids不能为空", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api read end...");
		logger.info("api read total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 未读信息总记录
	 * @param client
	 * @author wanduanrui
	 * @return
	 */
	@Authenticate
	@ResponseBody
	@RequestMapping(value = "unreadTotal")
	public Map<String,Object> unreadTotal(String client){
		//获得当前用户
		CustomerClientToken customerClientToken = TokenUtils.getCurrentCustomerClientToken();
		
		//获得请求渠道
		String opTerm = ApiUtil.getOperTerm(client);
		String messageChannel = ApiUtil.getPushChannel(opTerm);
		
		//设置消息查询时间区间
		PageSearchBean pageSearchBean = new PageSearchBean();
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		
		//查询未读信息总记录
		int sysUnreadCount = messageInstanceService.getUnreadCount(customerClientToken.getCustomerId(),MessageConstant.MESSAGE_TYPE_SYSTEM,messageChannel,MessageConstant.MESSAGE_STATUS_READ,pageSearchBean.getStartDateTime(), pageSearchBean.getEndDateTime());
		int accountUnreadCount = messageInstanceService.getUnreadCount(customerClientToken.getCustomerId(),MessageConstant.MESSAGE_TYPE_ACCOUNT,messageChannel,MessageConstant.MESSAGE_STATUS_READ,pageSearchBean.getStartDateTime(), pageSearchBean.getEndDateTime());
		
		//将未读信息总记录包装成API
		Map<String, Object> apiMap = APIUtils.createAPIMapByDataKeyVals(new String[]{"account","system"},new Object[]{accountUnreadCount,sysUnreadCount});
		
		return apiMap;
	}
	
	/**
	 * 我的消息-紧急消息
	 * @param client
	 * @author wanduanrui
	 * @return
	 */
	@Authenticate
	@ResponseBody
	@RequestMapping(value = "emergencyMessage")
	public Map<String,Object> emergencyMessage(String client){
		//获得当前用户
		CustomerClientToken customerClientToken = TokenUtils.getCurrentCustomerClientToken();
		
		//获得请求渠道
		String opTerm = ApiUtil.getOperTerm(client);
		String messageChannel = ApiUtil.getPushChannel(opTerm);
		
		//查询紧急消息数据
		MessageInstance messageInstance = messageInstanceService.getLatestEmergencyMessage(customerClientToken.getCustomerId(),messageChannel);
		
		//把紧急消息数据包装成API
		Map<String,Object> apiMap = APIUtils.toAPIMap(messageInstance, new ConvertAction<MessageInstance>(){
			@Override
			public Map<String, Object> convert(MessageInstance messageInstance) {
				Map<String,Object> dataMap = JsonUtils.beanToMap(messageInstance, "emergencyMessage");
				String statusName = "2".equals(messageInstance.getStatus()) ? "已读" : "未读";
				
				dataMap.put("messageId", messageInstance.getId());
				dataMap.put("statusName", statusName);
				dataMap.put("createDt",DateUtils.formatDate(messageInstance.getCreateDt(), "yyyy-MM-dd HH:mm:ss"));
				dataMap.put("status", Integer.parseInt(messageInstance.getStatus()));
				
				CustomMessage customMessage =  customMessageService.getByMessageId(messageInstance.getMessageId());
				dataMap.put("isClick", NumberUtils.parseInt(customMessage.getIsClick(),null));
				dataMap.put("targetType", NumberUtils.parseInt(customMessage.getTargetType(),null));
				dataMap.put("target", customMessage.getTarget());
				return dataMap;
			}
		});
		
		return apiMap;
	}
}
