package com.thinkgem.jeesite.modules.api.to;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

 
public class ProvinceResp implements Serializable{
    	
	private static final long serialVersionUID = 1L;
	@JsonProperty("provinceId")
	private String id;
	@JsonProperty("provinceName")
	private String name;
	@JsonIgnore
	private String parentId;
	@JsonProperty("cityList")
	private List<CityResp> cityResp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<CityResp> getCityResp() {
		return cityResp;
	}
	public void setCityResp(List<CityResp> cityResp) {
		this.cityResp = cityResp;
	}
	 
	
}
