/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车贷项目要素Entity
 * @author yangtao
 * @version 2015-07-08
 */
public class ProjectFactorCarFlow extends DataEntity<ProjectFactorCarFlow> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目流水号
	private String projectCode;		// 项目编号
	private String carModel;		// 车辆型号
	private String degreesDepreciation;		// 新旧状况
	private String carPrice;		// 购买价格
	
	public ProjectFactorCarFlow() {
		super();
	}

	public ProjectFactorCarFlow(String id){
		super(id);
	}

	@Length(min=0, max=11, message="项目流水号长度必须介于 0 和 11 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=50, message="项目编号长度必须介于 0 和 50 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=0, max=200, message="车辆型号长度必须介于 0 和 200 之间")
	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	
	@Length(min=0, max=20, message="新旧状况长度必须介于 0 和 20 之间")
	public String getDegreesDepreciation() {
		return degreesDepreciation;
	}

	public void setDegreesDepreciation(String degreesDepreciation) {
		this.degreesDepreciation = degreesDepreciation;
	}
	
	public String getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(String carPrice) {
		this.carPrice = carPrice;
	}
	
}