/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.DataQuery;
import com.thinkgem.jeesite.modules.entity.DataQueryForm;
import com.thinkgem.jeesite.modules.entity.DataQueryMenu;
import com.thinkgem.jeesite.modules.entity.DataQueryRow;
import com.thinkgem.jeesite.modules.operation.dao.DataQueryDao;
import com.thinkgem.jeesite.modules.operation.dao.DataQueryFormDao;
import com.thinkgem.jeesite.modules.operation.dao.DataQueryMenuDao;
import com.thinkgem.jeesite.modules.operation.dao.DataQueryRowDao;

/**
 * 数据查询Service
 * @author ydt
 * @version 2016-03-17
 */
@Service
@Transactional(readOnly = true)
public class DataQueryService extends CrudService<DataQueryDao, DataQuery> {
	@Autowired
	private DataQueryRowDao dataQueryRowDao;
	@Autowired
	private DataQueryFormDao dataQueryFormDao;
	@Autowired
	private DataQueryMenuDao dataQueryMenuDao;

	public DataQuery get(String id) {
		return super.get(id);
	}
	
	public List<DataQuery> findList(DataQuery dataQuery) {
		return super.findList(dataQuery);
	}
	
	public Page<DataQuery> findPage(Page<DataQuery> page, DataQuery dataQuery) {
		return super.findPage(page, dataQuery);
	}
	
	@Transactional(readOnly = false)
	public void save(DataQuery dataQuery) {
		super.save(dataQuery);
	}
	
	@Transactional(readOnly = false)
	public void delete(DataQuery dataQuery) {
		super.delete(dataQuery);
	}

	public List<DataQueryRow> getDataQueryRowList(Long queryId) {
		return dataQueryRowDao.findListByQueryId(queryId);
	}

	@Transactional(readOnly = false)
	public void saveDataQueryRowList(DataQuery dataQuery) {
		dataQueryRowDao.deleteByQueryId(dataQuery.getId());
		for(DataQueryRow dataQueryRow : dataQuery.getDataQueryRowList()) {
			if(dataQueryRow.getQueryId() != null) {
				dataQueryRowDao.insert(dataQueryRow);
			}
		}
	}

	public List<DataQueryForm> getDataQueryFormList(Long queryId) {
		return dataQueryFormDao.findListByQueryId(queryId);
	}

	@Transactional(readOnly = false)
	public void saveDataQueryFormList(DataQuery dataQuery) {
		dataQueryFormDao.deleteByQueryId(dataQuery.getId());
		for(DataQueryForm dataQueryForm : dataQuery.getDataQueryFormList()) {
			if(dataQueryForm.getQueryId() != null) {
				dataQueryFormDao.insert(dataQueryForm);
			}
		}
	}

	public Page<Map<String,Object>> query(Map<String, Object> para, Page<Map<String,Object>> page) {
		para.put("page", page);
		page.setList(dao.query(para));
		return page;
	}

	public List<Map<String,Object>> query(Map<String, Object> para) {
		return dao.query(para);
	}

	public List<DataQueryMenu> getDataQueryMenuList(Long queryId) {
		return dataQueryMenuDao.getListByQueryId(queryId);
	}

	@Transactional(readOnly = false)
	public void saveDataQueryMenuList(DataQuery dataQuery) {
		dataQueryMenuDao.deleteByQueryId(dataQuery.getId());
		for(DataQueryMenu dataQueryMenu : dataQuery.getDataQueryMenuList()) {
			if(dataQueryMenu.getQueryId() != null) {
				dataQueryMenuDao.insert(dataQueryMenu);
			}
		}
	}

	public DataQuery getByMenuId(String menuId) {
		return dao.getByMenuId(menuId);
	}
	
}