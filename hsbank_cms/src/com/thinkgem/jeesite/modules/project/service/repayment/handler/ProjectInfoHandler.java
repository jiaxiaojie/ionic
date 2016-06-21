/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.repayment.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;

/**
 * @author yangtao
 *
 */
@Component("projectInfoHandler")
public class ProjectInfoHandler {
	@Autowired
	private ProjectBaseInfoDao baseDao;
	/**单例*/
	private static ProjectInfoHandler instance = null;
	
	/**私有构造函数*/
	private ProjectInfoHandler() {
	}
	
	/**得到单例*/
	public static ProjectInfoHandler getInstance() {
        return instance == null ? instance = new ProjectInfoHandler() : instance;
    }
	/**
	 * 获取项目信息
	 * @param projectId
	 * @return
	 */
	public ProjectBaseInfo getInfo(String projectId){
		return baseDao.get(projectId);
	}

}
