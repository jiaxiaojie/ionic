package com.thinkgem.jeesite.modules.entity.api;

/**
 * api端项目查询bean
 * Created by ydt on 2016/5/16.
 */
public class ProjectSearchBean {
	private String[] statuses;
	private String durationType;
	private Integer minDuration;
	private Integer maxDuration;
	private Double minRate;
	private Double maxRate;
	private String repaymentMode;
	private String type;

	private ProjectSearch projectSearch;	//定期项目查询信息
	private Integer index;	//查询结果的起始条数（从0开始）
	private Integer limit;	//查询条数

	public ProjectSearchBean() {}
	public ProjectSearchBean(String status, String duration, String rate, String repaymentMode,
							 String type, ProjectSearch search, Integer pageSize, Integer pageNumber) {
		if("1".equals(status)) {
			this.statuses = new String[]{"3"};
		} else if("2".equals(status)) {
			this.statuses = new String[]{"4"};
		} else if("3".equals(status)) {
			this.statuses = new String[]{"5"};
		} else if("4".equals(status)) {
			this.statuses = new String[]{"6", "7"};
		}
		if("1".equals(duration)) {
			this.durationType = "2";
			this.maxDuration = 3;
		} else if("2".equals(duration)) {
			this.durationType = "2";
			this.minDuration = 4;
			this.maxDuration = 6;
		} else if("3".equals(duration)) {
			this.durationType = "2";
			this.minDuration = 7;
			this.maxDuration = 12;
		} else if("4".equals(duration)) {
			this.durationType = "2";
			this.minDuration = 13;
		}
		if("1".equals(rate)) {
			this.maxRate = 0.09;
		} else if("2".equals(rate)) {
			this.minRate = 0.09;
			this.maxRate = 0.1;
		} else if("3".equals(rate)) {
			this.minRate = 0.1;
			this.maxRate = 0.11;
		} else if("4".equals(rate)) {
			this.minRate = 0.11;
		}
		if("1".equals(repaymentMode) || "2".equals(repaymentMode) || "3".equals(repaymentMode)) {
			this.repaymentMode = repaymentMode;
		}
		if("1".equals(type) || "2".equals(type) || "3".equals(type) || "4".equals(type)
				|| "5".equals(type) || "6".equals(type) || "7".equals(type)) {
			this.type = type;
		}
		this.projectSearch = search;
		this.index = (pageNumber - 1) * pageSize;
		this.limit = pageSize;
	}

	public String[] getStatuses() {
		return statuses;
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public Integer getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}

	public Integer getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}

	public Double getMinRate() {
		return minRate;
	}

	public void setMinRate(Double minRate) {
		this.minRate = minRate;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	public String getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProjectSearch getProjectSearch() {
		return projectSearch;
	}

	public void setProjectSearch(ProjectSearch projectSearch) {
		this.projectSearch = projectSearch;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
