/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.web;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.customer.service.CustomerPushSwitchService;
import com.thinkgem.jeesite.modules.entity.CustomMessage;
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.Message;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.CustomMessageDao;
import com.thinkgem.jeesite.modules.message.service.CustomMessageSendChannelService;
import com.thinkgem.jeesite.modules.message.service.CustomMessageService;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.message.service.MessageService;
import com.thinkgem.jeesite.modules.message.service.ReceiveMessageSwitchService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 自定义消息Controller
 * @author ydt
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/message/customMessage")
public class CustomMessageController extends BaseController {
	@Autowired
	private CustomMessageService customMessageService;
	
	@Autowired
	private CustomerPushSwitchService customerPushSwitchService;
	
	@Autowired
	private CustomMessageSendChannelService customMessageSendChannelService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ReceiveMessageSwitchService receiveMessageSwitchService;
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	private CustomMessageDao customMessageDao;
	@ModelAttribute
	public CustomMessage get(@RequestParam(required=false) String id) {
		CustomMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customMessageService.get(id);
		}
		if (entity == null){
			entity = new CustomMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("message:customMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomMessage customMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomMessage> page = customMessageService.findPage(new Page<CustomMessage>(request, response), customMessage); 
		model.addAttribute("page", page);
		return "modules/message/customMessageList";
	}
	/**
	 * 创建消息列表
	 * @param customMessage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:customMessage:create")
	@RequestMapping(value = {"createList"})
	public String createList(CustomMessage customMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		customMessage.setStatus(MessageConstant.CUSTOM_MESSAGE_STAUTS_CREATE);
		Page<CustomMessage> page = customMessageService.findPage(new Page<CustomMessage>(request, response), customMessage); 
		model.addAttribute("page", page);
		return "modules/message/customMessageCreateList";
	}
	
	
	
   /** 
	 * 改变是否为紧急消息
	 * @param customMessage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:customMessage:edit")
	@RequestMapping(value = {"changeStatus"})
	public String changeStatus(CustomMessage customMessage, RedirectAttributes redirectAttributes) {
		customMessageService.changeStatus(customMessage.getId());
		addMessage(redirectAttributes, "已修改为非紧急消息");
		return "redirect:"+Global.getAdminPath()+"/message/customMessage/list?repage";
	}
	
	/**
	 * 创建消息
	 * @param customMessage
	 * @param model
	 * @return
	 */
	
	@RequiresPermissions("message:customMessage:create")
	@RequestMapping(value = "createForm")
	public String createForm(CustomMessage customMessage, Model model) {
		String  id=customMessage.getId();
		customMessage = this.get(id);
		//根据编号查询列表
		List<CustomMessageSendChannel> channelList = customMessageSendChannelService.findListById(id);
		List<String> messageChannelList = new ArrayList<String>();
		for(CustomMessageSendChannel channels : channelList){
			messageChannelList.add(String.valueOf(channels.getMessageChannel()));
		}
		customMessage.setMessageChannelList(messageChannelList);
		model.addAttribute("customMessage", customMessage);
		return "modules/message/customMessageCreateForm";
	}
	
	/**
	 * 审批消息列表
	 * @param customMessage
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("message:customMessage:review")
	@RequestMapping(value = {"reviewList"})
	public String reviewList(CustomMessage customMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		customMessage.setStatus(MessageConstant.CUSTOM_MESSAGE_STAUTS_SUBMIT_REVIEW);
		Page<CustomMessage> page = customMessageService.findPage(new Page<CustomMessage>(request, response), customMessage); 
		model.addAttribute("page", page);
		return "modules/message/customMessageReviewList";
	}
	
	/**
	 * 消息审批列表
	 * @param customMessage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:customMessage:review")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(CustomMessage customMessage, Model model) {
		String  id=customMessage.getId();
		customMessage = this.get(id);
		//根据编号查询列表
		List<CustomMessageSendChannel> channelList = customMessageSendChannelService.findListById(id);
		List<String> messageChannelList = new ArrayList<String>();
		for(CustomMessageSendChannel channels : channelList){
			messageChannelList.add(String.valueOf(channels.getMessageChannel()));
		}
		customMessage.setMessageChannelList(messageChannelList);
		model.addAttribute("customMessage", customMessage);
		return "modules/message/customMessageReviewForm";
	}
	
	
	/**
	 * 审批
	 * @param customMessage
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("message:customMessage:review")
	@RequestMapping(value = "review")
	public String review(CustomMessage customMessage,MessageCreateRule messageCreateRule, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customMessage)){
			return reviewForm(customMessage,model);
		}
		//获得自定义消息编号
		String id = customMessage.getId();
	   String status = customMessage.getStatus();
		customMessage =customMessageService.get(customMessage);
		customMessage.setStatus(status);
		customMessage.setReviewDt(new Date());
		customMessage.setReviewer(new Long(UserUtils.getUser().getId()));
		customMessageService.review(customMessage);
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try{
			String[] filePaths = customMessage.getReceiverData().split("\\|");
			DecimalFormat decimalFormat = new DecimalFormat("#");
			for(String filePath : filePaths){
					if(filePath != null && !"".equals(filePath)){
						filePath = filePath.substring(filePath.indexOf("/userfiles/"));
						filePath=URLDecoder.decode(filePath, "UTF-8");
						//解析excel表格
						ImportExcel ei = new ImportExcel(new File(rootPath,filePath), 1, 0);
						List<CustomerBase> list = ei.getDataList(CustomerBase.class);
						for(int i = 0; i < list.size(); i++){
							try{
								//根据手机号码循环获得accountId
								CustomerBase customerBase = customerBaseService.getByMobile(decimalFormat.format(new BigDecimal(list.get(i).getMobile())));
								Long accountId=customerBase.getAccountId();
								/**
								 * 根据提交审批的自定义消息的编号（id）查询此消息的状态（status）
								 * 如果为状态status='2'则创建消息(message)并添加消息实例（messageInstance）
								 */
								String statuss=customMessageService.findStatusFromCustomMessageByAccountId(id);
								if(accountId != null && statuss.equals("2")){
									Message message = new Message();
									message.setAccountId(accountId);
									message.setTitle(customMessage.getTitle());
									message.setContent(customMessage.getContent());
									message.setSendDt(customMessage.getSendDt());
									message.setType(customMessage.getType());
									message.setLabel(customMessage.getLabel());
									message.setFromType(MessageConstant.MESSAGE_FROM_TYPE_CUSTOM);
									message.setFromId(Long.parseLong(customMessage.getId()));
									messageService.insertMessage(message);
									List<CustomMessageSendChannel> channelList = customMessageSendChannelService.findListById(id);
									for(CustomMessageSendChannel channels : channelList){
									String  messageChannel  =	String.valueOf(channels.getMessageChannel());
										//若接收此消息，则创建消息实例
										if(receiveMessageSwitchService.shouldReceiveMessage(accountId, customMessage.getType(), messageChannel)) {
											//若是android或ios消息，若用户未登录过android或ios应用，则不创建此消息
											if((MessageConstant.MESSAGE_CHANNEL_ANDROID.equals(messageChannel) || MessageConstant.MESSAGE_CHANNEL_IOS.equals(messageChannel))
													&& customerPushSwitchService.getCustomerPushSwitch(message.getAccountId(), messageChannel) == null) {
												continue;
											}
											MessageInstance messageInstance = new MessageInstance();
											messageInstance.setMessageId(Long.parseLong(message.getId()));
											messageInstance.setMessageChannel(messageChannel);
											messageInstance.setCreateDt(new Date());
											messageInstance.setStatus(MessageConstant.MESSAGE_STATUS_CREATE);
											messageInstanceService.insertMessageInstance(messageInstance);
										}
									}
								}
							}
							catch(Exception e){
								e.printStackTrace();
								continue;
							}
							
						}
					}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		addMessage(redirectAttributes, "审批消息成功");
		return "redirect:"+Global.getAdminPath()+"/message/customMessage/reviewList";
	}
	

	/**
	 * 创建自定义消息
	 * @param customMessage
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("message:customMessage:create")
	@RequestMapping(value = "create")
	public String create(CustomMessage customMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customMessage)){
			return form(customMessage, model);
		}
		customMessage.setCreator(new Long(UserUtils.getUser().getId()));
		customMessage.setCreateDt(new Date());
		customMessageService.create(customMessage);
		if(MessageConstant.CUSTOM_MESSAGE_STAUTS_CREATE.equals(customMessage.getStatus())){
			addMessage(redirectAttributes, "保存消息成功");
			}
			else if( MessageConstant.CUSTOM_MESSAGE_STAUTS_SUBMIT_REVIEW.equals(customMessage.getStatus()) )
			{
				addMessage(redirectAttributes, "保存消息并提交审批成功");
			}
		addMessage(redirectAttributes, "创建消息成功");
		return "redirect:"+Global.getAdminPath()+"/message/customMessage/createList";
	}
	
	/**
	 * 标题不重复校验
	 * @param title
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("message:customMessage:create")
	@RequestMapping(value = "checkTitle")
	public String checkTitle(String title,String id) {
		if (title !=null) {
			CustomMessage customMessage = new CustomMessage();
			customMessage.setTitle(title);
			customMessage = customMessageService.getCustomMessage(customMessage);	
			if(customMessage == null){
				return "true";
			}
			else if(id != null && id.equals(customMessage.getId())){
				return "true";
			}
			
		}
		return "false";
	}
	

	
	
	@RequiresPermissions("message:customMessage:view")
	@RequestMapping(value = "form")
	public String form(CustomMessage customMessage, Model model) {
		String  id=customMessage.getId();
		customMessage = this.get(id);
		//根据编号查询列表
		List<CustomMessageSendChannel> channelList = customMessageSendChannelService.findListById(id);
		List<String> messageChannelList = new ArrayList<String>();
		for(CustomMessageSendChannel channels : channelList){
			messageChannelList.add(String.valueOf(channels.getMessageChannel()));
		}
		customMessage.setMessageChannelList(messageChannelList);
		model.addAttribute("customMessage", customMessage);
		return "modules/message/customMessageForm";
	}
	
	/**
	 * 修改消息页面
	 * @param customMessage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("message:customMessage:view")
	@RequestMapping(value = "updateform")
	public String updateform(CustomMessage customMessage, Model model) {
		String  id=customMessage.getId();
		customMessage = this.get(id);
		//根据编号查询列表
		List<CustomMessageSendChannel> channelList = customMessageSendChannelService.findListById(id);
		List<String> messageChannelList = new ArrayList<String>();
		for(CustomMessageSendChannel channels : channelList){
			messageChannelList.add(String.valueOf(channels.getMessageChannel()));
		}
		customMessage.setMessageChannelList(messageChannelList);
		model.addAttribute("customMessage", customMessage);
		return "modules/message/customMessageUpdateForm";
	}
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("message:customMessage:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(CustomerBase customerBase,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {

            String fileName = "mobile.xlsx";
            List<CustomerBase> customerBases= new ArrayList<CustomerBase>();
            CustomerBase temp = new CustomerBase();
            temp.setMobile("12345678912");
            customerBases.add(temp);
    		new ExportExcel("手机号", CustomerBase.class, 2).setDataList(customerBases).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/message/customMessage/customMessageCreateForm?repage";
    }
    
	@RequiresPermissions("message:customMessage:edit")
	@RequestMapping(value = "save")
	public String save(CustomMessage customMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customMessage)){
			return form(customMessage, model);
		}
		customMessage.setStatus(MessageConstant.CUSTOM_MESSAGE_STAUTS_CREATE);
		customMessageService.create(customMessage);
		addMessage(redirectAttributes, "保存自定义消息成功");
		return "redirect:"+Global.getAdminPath()+"/message/customMessage/?repage";
	}
	
    /**
     * 消息创建列表删除
     * @param customMessage
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("message:customMessage:create")
	@RequestMapping(value = "createdelete")
	public String createdelete(CustomMessage customMessage, RedirectAttributes redirectAttributes) {
		customMessageService.delete(customMessage);
		addMessage(redirectAttributes, "删除自定义消息成功");
		return "redirect:"+Global.getAdminPath()+"/message/customMessage/createList?repage";
	}
	

}