package com.thinkgem.jeesite.modules.front;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.UserFeedbackInfo;
import com.thinkgem.jeesite.modules.feedback.service.UserFeedbackInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

@Controller
@RequestMapping(value = "${frontPath}/feedback")
public class FeedbackController extends BaseController {
	@Autowired
	private UserFeedbackInfoService userFeedbackInfoService;
	
	/**
	 * 发送用户意见反馈信息
	 * @param userFeedbackInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "send")
	@ResponseBody
	public Map<String,Object> send(UserFeedbackInfo userFeedbackInfo, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map=new HashMap<String,Object>();
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = 0L;
		if(principal != null){
			accountId = principal.getAccountId();
			userFeedbackInfo.setAccountId(accountId);
		}
		userFeedbackInfo.setChannelId(ProjectConstant.OP_TERM_DICT_PC);
		userFeedbackInfo.setCreateDt(new Date());
		userFeedbackInfo.setResult("0");	//未处理
		List<String> messages  = validateBean(userFeedbackInfo);
		String flag = "no";
		if(messages ==null){
			if(userFeedbackInfo != null && StringUtils.isNotBlank(userFeedbackInfo.getContent())){
				userFeedbackInfoService.save(userFeedbackInfo);
				flag = "ok";
			}
		}
		map.put("flag", flag);
		return map;
	}
	
	
	/**
	 * 
	 * @param customerAddress
	 * @return
	 */
	private List<String> validateBean(UserFeedbackInfo userFeedbackInfo) {
		List<String> messages = null;
		try{
			BeanValidators.validateWithException(validator, userFeedbackInfo);
		}catch(ConstraintViolationException ex){
			messages =  BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
		}
		return messages;
	}
}
