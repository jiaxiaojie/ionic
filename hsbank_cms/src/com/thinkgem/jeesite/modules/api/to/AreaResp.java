package com.thinkgem.jeesite.modules.api.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AreaResp implements Serializable{
    
	private static final long serialVersionUID = 1L;
	@JsonProperty("districtId")
	private String id;
    @JsonProperty("districtName")
	private String name;
	
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
	 
}
