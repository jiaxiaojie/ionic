package com.thinkgem.jeesite.common.utils.mail.handler;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.StringWriter;
import java.util.Map;

/**
 * 模板生成工具
 * 
 * @author lizibo
 * @since 2015/08/11
 */
@Service
public class VelocityTemplate {
	private static Logger logger = Logger.getLogger(VelocityTemplate.class);
    @Resource
    private VelocityEngine velocityEngine;//注入模板引擎VelocityEngine

    /**
     * 获取邮件模板内容
     * @return String
     */
    public String getMailTemplate(String templateId, String name, String validateCode) {
        String template = "";
        try {
            //实例化一个VelocityContext,插入模板内容
            VelocityContext vc = new VelocityContext();
            vc.put("name", name);
            vc.put("validateCode", validateCode);
            StringWriter sw = new StringWriter();
            String templateSrc = "com/thinkgem/jeesite/common/utils/mail/vm/";
            if("resetPassword".equals(templateId)){
            	templateSrc = templateSrc + "sendResetPasswordValidateCodeMail.vm";
            }else if("changeEmail".equals(templateId)){
            	templateSrc = templateSrc + "sendChangeEmailValidateCodeMail.vm";
            }
            //加载邮件模板
            velocityEngine.mergeTemplate(templateSrc, "UTF-8", vc, sw);
            template = sw.toString();
            sw.flush();
            sw.close();
            logger.info("加载邮件模板成功---" + sw.toString());
        } catch (Exception e) {
            logger.error("加载邮件模板失败---" + e.toString());
        }

        return template;
    }

    /**
     * 获取通用模板内容
     *
     * @return template
     * */
    public String getTemplate(String templateId, Map<String, Object> data) {
        String template = null;
        try {
            String templateSrc = "";
            if("en_zhaohuimima".equals(templateId)) {
                data.put("caozuo","找回密码");
                templateSrc = "com/thinkgem/jeesite/common/utils/mail/vm/mail.vm";
            }else {
                return template;
            }

            VelocityContext vc = new VelocityContext();
            if(null != data) {
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    vc.put(entry.getKey(), entry.getValue());
                }
            }
            StringWriter sw = new StringWriter();
            velocityEngine.mergeTemplate(templateSrc, "UTF-8", vc, sw);
            template = sw.toString();
            sw.flush();
            sw.close();
            logger.info("加载模板成功---" + sw.toString());
        } catch(Exception e) {
            logger.error("加载模板失败---" + e.toString());
        }

        return template;
    }
}
