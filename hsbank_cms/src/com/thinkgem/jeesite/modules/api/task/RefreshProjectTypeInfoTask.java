package com.thinkgem.jeesite.modules.api.task;

import com.thinkgem.jeesite.modules.api.web.ProjectInfoUtils;
import org.springframework.stereotype.Component;

/**
 * 更新缓存中的项目信息
 * Created by ydt on 2016/5/18.
 */
@Component
public class RefreshProjectTypeInfoTask {

	/**
	 * 每5分钟执行一次，更新缓存中的项目信息
	 */
	public void job() {
		ProjectInfoUtils.refreshProjectTypeInfo();
	}
}
