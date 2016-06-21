package com.thinkgem.jeesite.modules.api;

public class PageBean {

	public Integer startNumber;		//开始条数
	public Integer endNumber;		//结束条数
	public Integer pageNumber;		//当前页码
	public Integer pageSize;		//页面容量
	
	/**
	 * 构造方法
	 * @param pageNumber
	 * @param pageSize
	 */
	public PageBean(Integer pageNumber, Integer pageSize){
		pageNumber = pageNumber == null || pageNumber < 0 ? 1 : pageNumber;
		pageSize = pageSize == null || pageSize < 0 ? 10 : pageSize;
		this.setPageNumber(pageNumber);
		this.setPageSize(pageSize);
		this.setStartNumber(pageNumber, pageSize);
		this.setEndNumber(pageNumber, pageSize);
	}
	public Integer getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(Integer pageNumber, Integer pageSize) {
		Integer startNumber = (pageNumber - 1) * pageSize;
		this.startNumber = startNumber;
	}
	public Integer getEndNumber() {
		return endNumber;
	}
	public void setEndNumber(Integer pageNumber, Integer pageSize) {
		Integer endNumber = pageSize;
		this.endNumber = endNumber;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
    
	
}
