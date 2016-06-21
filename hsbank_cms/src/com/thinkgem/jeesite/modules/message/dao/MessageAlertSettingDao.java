/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MessageAlertSetting;
import com.thinkgem.jeesite.modules.sms.utils.SmsUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息提醒设置DAO接口
 * @author hyc
 * @version 2016-05-09
 */
@MyBatisDao
public interface MessageAlertSettingDao extends CrudDao<MessageAlertSetting> {

    /**
     * 查询风控提醒消息
     * @param messageAlertSetting
     * @return
     */
    MessageAlertSetting findAlertSmsFromMessageAlertSetting();



}