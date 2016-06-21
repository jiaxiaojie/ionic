package com.thinkgem.jeesite.modules.api.to;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CityResp implements Serializable{
   
	private static final long serialVersionUID = 1L;
	@JsonProperty("cityId")
	private String id;
    @JsonProperty("cityName")
	private String name;
    @JsonIgnore
	private String parentId;
	@JsonProperty("districtList")
	private List<AreaResp> areaResp;
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
	public List<AreaResp> getAreaResp() {
		return areaResp;
	}
	public void setAreaResp(List<AreaResp> areaResp) {
		this.areaResp = areaResp;
	}
	
}
