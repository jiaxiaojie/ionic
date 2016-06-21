package com.thinkgem.jeesite.modules.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.entity.ReceiveMessageSwitch;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.message.service.ReceiveMessageSwitchService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

@Controller
@RequestMapping("${frontPath}/customer/message")
public class CustomerMessageController extends BaseController {
	
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	CustomerAccountService customerAccountService;
	@Autowired
	private ReceiveMessageSwitchService receiveMessageSwitchService;
	
	
	
	/**
	 * 我的消息：账户消息
	 * @param pageSearchBean
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myMessage")
	public String searchMyMessagePageList(PageSearchBean pageSearchBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		String type = MessageConstant.MESSAGE_TYPE_ACCOUNT;
		String messageChannel = MessageConstant.MESSAGE_CHANNEL_WEB;
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		Page<MessageInstance> page = messageInstanceService.searchMyMessagePageList(accountId, type, messageChannel, pageSearchBean);
		int totalCount = (int) (page != null ? page.getCount() : 0);
		int unreadCount = messageInstanceService.getUnreadCount(accountId, type, messageChannel,MessageConstant.MESSAGE_STATUS_READ, pageSearchBean.getStartDateTime(), pageSearchBean.getEndDateTime());
		//消息设置信息
		setMessageInfo(accountId, model, request, response);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("unreadCount", unreadCount);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdxx");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/message/myMessage'>我的消息</a>&nbsp;&gt;&nbsp;<a href='#'>账户消息</a>");
		return "modules/front/message/my_message";
	}
	
	/**
	 * 我的消息：账户消息
	 * @param pageSearchBean
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("sysMessage")
	public String searchSysMessagePageList(PageSearchBean pageSearchBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		String type = MessageConstant.MESSAGE_TYPE_SYSTEM;
		String messageChannel = MessageConstant.MESSAGE_CHANNEL_WEB;
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		Page<MessageInstance> page = messageInstanceService.searchMyMessagePageList(accountId, type, messageChannel, pageSearchBean);
		int totalCount = (int) (page != null ? page.getCount() : 0);
		int unreadCount = messageInstanceService.getUnreadCount(accountId, type, messageChannel,MessageConstant.MESSAGE_STATUS_READ, pageSearchBean.getStartDateTime(), pageSearchBean.getEndDateTime());
		//消息设置信息
		setMessageInfo(accountId, model, request, response);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("unreadCount", unreadCount);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdxx");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/message/myMessage'>我的消息</a>&nbsp;&gt;&nbsp;<a href='#'>系统消息</a>");
		return "modules/front/message/sys_message";
	}
	
	/**
	 * 查找消息设置信息
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 */
	public void setMessageInfo(long accountId, Model model, HttpServletRequest request, HttpServletResponse response){
		
		ReceiveMessageSwitch receiveMessageSwitch = new ReceiveMessageSwitch();
		receiveMessageSwitch.setAccountId(accountId);
		receiveMessageSwitch.setMessageChannels(new String[]{MessageConstant.MESSAGE_CHANNEL_WEB,MessageConstant.MESSAGE_CHANNEL_SMS});
		
		//系统消息
		receiveMessageSwitch.setMessageType(MessageConstant.MESSAGE_TYPE_SYSTEM);
		Map<String,Object> systemMessage = Collections3.getFirst(receiveMessageSwitchService.findCustomerSwitchPage(new Page<Map<String,Object>>(request, response), receiveMessageSwitch).getList()); 
		//用户消息
		receiveMessageSwitch.setMessageType(MessageConstant.MESSAGE_TYPE_ACCOUNT);
		Map<String,Object> accountMessage = Collections3.getFirst(receiveMessageSwitchService.findCustomerSwitchPage(new Page<Map<String,Object>>(request, response), receiveMessageSwitch).getList()); 
		
		model.addAttribute("accountId", accountId);
		model.addAttribute("sysMsgKey", "chn_"+MessageConstant.MESSAGE_CHANNEL_SMS);
		model.addAttribute("webMsgkey", "chn_"+MessageConstant.MESSAGE_CHANNEL_WEB);
		model.addAttribute("systemMessage", systemMessage);
		model.addAttribute("accountMessage", accountMessage);
	}
	
	/**
	 * 阅读消息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "readMessage")
	@ResponseBody
	public Map<String, Object> readMessage(Long id, String type) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		long accountId = CustomerUtils.getCurrentAccountId();
		messageInstanceService.updateStatus(id, MessageConstant.MESSAGE_STATUS_READ, null, new Date(), null);
		MessageInstance mes = messageInstanceService.get(String.valueOf(id));
		boolean isResult = MessageConstant.MESSAGE_STATUS_READ.equals(mes.getStatus()) ? true : false;
		Date endDateTime = new Date();
		Date startDateTime = DateUtils.dateAddMonth(endDateTime, -3);
		int unreadCount = messageInstanceService.getUnreadCount(accountId, type, MessageConstant.MESSAGE_CHANNEL_WEB, MessageConstant.MESSAGE_STATUS_READ, startDateTime, endDateTime);
		retMap.put("isResult", isResult);
		retMap.put("unreadCount", unreadCount);
		return retMap;
	}
	
	
	
	/**
	 * 修改消息状态(删除/标记已读)
	 * @param messageInstance
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("modifyMyMessage")
	public String modifyMyMessage(PageSearchBean pageSearchBean, MessageInstance messageInstance, HttpServletRequest request, Model model) {
		String forwardUrl = MessageConstant.MESSAGE_TYPE_SYSTEM.equals(messageInstance.getType()) ? "sysMessage" : "myMessage";
		if(messageInstance.getMessageIdList() !=null && messageInstance.getMessageIdList().size() > 0){
			//批量修改
			messageInstanceService.updateStatusBatch(messageInstance.getMessageIdList(), messageInstance.getStatus(), null, new Date(), null);
		}
		return "redirect:"+Global.getFrontPath()+"/customer/message/"+forwardUrl+"?pageNo="+pageSearchBean.getPageNo();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "receiveMsgSwitchSetting")
	public HashMap<String,Object> customReceiveMsgSwitchSetting(String[] id, String[] isReceive,Model model) {
		String messageKey = "message";
		String successKey = "success";
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put(messageKey, "消息提醒设置保存失败");
		result.put(successKey, false);
		
		try{
			long accountId = CustomerUtils.getCurrentAccountId();
			List<ReceiveMessageSwitch> rmss = new ArrayList<ReceiveMessageSwitch>();
			for(int i = 0; i < id.length; i++){
				ReceiveMessageSwitch rms = new ReceiveMessageSwitch();
				rms.setId(id[i]);
				rms.setIsReceive(isReceive[i]);
				rms.setAccountId(accountId);
				rmss.add(rms);
				if (!beanValidator(model, rms)){
					return result;
				}
			}
			receiveMessageSwitchService.settingMessageSwitch(rmss);
			result.put(messageKey, "消息提醒设置保存成功");
			result.put(successKey, true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
}
