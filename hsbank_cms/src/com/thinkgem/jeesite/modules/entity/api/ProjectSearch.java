package com.thinkgem.jeesite.modules.entity.api;

import java.io.Serializable;

/**
 * 定期项目查询信息
 * Created by ydt on 2016/5/17.
 */
public class ProjectSearch implements Serializable{
	private Integer projectDuration;	//项目期限
	private Integer durationUnit;	//项目期限单位(1:按天，2:按月)
	private String isNewUser;	//是否新手项目（0是，其它不是）

	public Integer getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(Integer projectDuration) {
		this.projectDuration = projectDuration;
	}

	public Integer getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(Integer durationUnit) {
		this.durationUnit = durationUnit;
	}

	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}
}