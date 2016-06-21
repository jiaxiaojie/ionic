package com.thinkgem.jeesite.modules.entity.front;

import java.util.Arrays;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;

/**
 * 项目查询Bean
 * @author ydt
 *
 */
public class ProjectSearchBean {
	String[] statuses = {"0", "1", "2", "3", "4"};
	String[] durations = {"0", "1", "2", "3", "4", "5", "6"};
	String[] rates = {"0", "1", "2", "3", "4", "5"};
	String[] repaymentModes = {"0", "1", "2", "3"};
	String[] projectTypes = {"0", "1", "2", "3", "4", "5","6"};
	
	private String status;				//状态
	private String duration;			//期限
	private String rate;				//利率
	private String repaymentMode;		//还款方式
	private String projectType;			//项目类型
	private Page<ProjectBaseInfo> page;
	
	public ProjectSearchBean() {}
	
	public ProjectSearchBean(String status, String duration, String rate, String repaymentMode, String projectType) {
		this.status = status;
		this.duration = duration;
		this.rate = rate;
		this.repaymentMode = repaymentMode;
		this.projectType = projectType;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	/**
	 * 若属性为空或不合法，则设置为默认值"0"
	 */
	public void setDefaultSearch() {
		if(StringUtils.isBlank(status) || !Arrays.asList(statuses).contains(status)) {
			status = "0";
		}
		if(StringUtils.isBlank(duration) || !Arrays.asList(durations).contains(duration)) {
			duration = "0";
		}
		if(StringUtils.isBlank(rate) || !Arrays.asList(rates).contains(rate)) {
			rate = "0";
		}
		if(StringUtils.isBlank(repaymentMode) || !Arrays.asList(repaymentModes).contains(repaymentMode)) {
			repaymentMode = "0";
		}
		if(StringUtils.isBlank(projectType) || !Arrays.asList(projectTypes).contains(projectType)) {
			projectType = "0";
		}
	}

	public void setPage(Page<ProjectBaseInfo> page) {
		this.page = page;
	}

	public Page<ProjectBaseInfo> getPage() {
		return page;
	}
	
}
