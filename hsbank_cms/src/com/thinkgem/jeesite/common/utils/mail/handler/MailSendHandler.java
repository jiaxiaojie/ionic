package com.thinkgem.jeesite.common.utils.mail.handler;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


/**
 * 邮件服务接口实现
 *
 * @author lizibo
 * @since 2015/08/11
 */
@Service
public class MailSendHandler{
    private static Logger logger = Logger.getLogger(MailSendHandler.class);

    @Resource
    JavaMailSenderImpl mailSender;
    @Resource
    TaskExecutor taskExecutor;
    /**
     * 邮件服务接口
     *
     * @param mailDTO
     */
    public boolean sendEmail(Mail mail) {
        logger.info("------邮件服务接口开始------");
        sendMailByAsynchronousMode(mail.getReceivers(), mail.getSubject(), mail.getTemplate());
        logger.info("邮件发送成功");
        logger.info("------邮件服务接口结束------");

        return true;
    }
    /**
     * 异步发送
     *
     * @param receivers 邮件接收人列表
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    private void sendMailByAsynchronousMode(final String receivers, final String subject, final String content) {
        logger.info("开始异步发送邮件");
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMailBySynchronizationMode(receivers, subject, content);
                } catch (Exception e) {
                    logger.error("邮件发送失败---" + e.toString());
                }
            }
        });
    }

    /**
     * 同步发送
     *
     * @param receivers 邮件接收人列表
     * @param subject   邮件主题
     * @param content   邮件内容
     * @throws Exception
     */
    private void sendMailBySynchronizationMode(String receivers, String subject, String content) throws Exception {
        if (receivers == null) {
            throw new IllegalArgumentException("收件人不能为空");
        }
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "utf-8");

        receivers = receivers.replaceAll("\\;", ",");
        // 设置收件人，寄件人
        InternetAddress[] toAddress = InternetAddress.parse(receivers);
        // 发送给多个账号
        mailMessage.setRecipients(Message.RecipientType.TO, toAddress);
        // 发件人
        messageHelper.setFrom(mailSender.getUsername());
        // 主题
        messageHelper.setSubject(subject);
        // true 表示启动HTML格式的邮件
        messageHelper.setText(content, true);
        // 发送邮件
        mailSender.send(mailMessage);
    }

    
}
