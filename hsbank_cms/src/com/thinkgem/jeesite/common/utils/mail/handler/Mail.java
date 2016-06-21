package com.thinkgem.jeesite.common.utils.mail.handler;

import java.io.Serializable;

/**
 * 邮件
 *
 * @author lzb
 * @version 1.0
 * @since 2015/08/11
 */
public class Mail implements Serializable {
    /**序列化Id*/
	private static final long serialVersionUID = -8085053092186166715L;

	/**
     * 接收者(多个用;分隔)
     * */
    private String receivers;

    /**
     * 主题
     * */
    private String subject;

    /**
     * 模板
     * */
    private String template;

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
