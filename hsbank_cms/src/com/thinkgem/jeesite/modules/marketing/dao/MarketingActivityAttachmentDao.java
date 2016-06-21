/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAttachment;

/**
 * 活动关联附件DAO接口
 * @author lizibo
 * @version 2015-09-09
 */
@MyBatisDao
public interface MarketingActivityAttachmentDao extends CrudDao<MarketingActivityAttachment> {
	
}