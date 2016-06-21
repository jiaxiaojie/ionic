/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.yeepay.query.QueryCpTranscationItem;
import com.thinkgem.jeesite.common.yeepay.query.QueryCpTranscationResp;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransErrorRecord;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransErrorRecordDao;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 异常交易记录表Service
 * @author lzb
 * @version 2016-03-03
 */
@Service
@Transactional(readOnly = true)
public class ProjectTransErrorRecordService extends CrudService<ProjectTransErrorRecordDao, ProjectTransErrorRecord> {
	//转账记录
	public static String DIRECT_QUERY_MODE="CP_TRANSACTION";
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	public ProjectTransErrorRecord get(String id) {
		return super.get(id);
	}
	
	public List<ProjectTransErrorRecord> findList(ProjectTransErrorRecord projectTransErrorRecord) {
		return super.findList(projectTransErrorRecord);
	}
	
	public Page<ProjectTransErrorRecord> findPage(Page<ProjectTransErrorRecord> page, ProjectTransErrorRecord projectTransErrorRecord) {
		return super.findPage(page, projectTransErrorRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectTransErrorRecord projectTransErrorRecord) {
		super.save(projectTransErrorRecord);
	}
	
	@Transactional(readOnly = false)
	public void insert(ProjectTransErrorRecord projectTransErrorRecord) {
		dao.insert(projectTransErrorRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectTransErrorRecord projectTransErrorRecord) {
		super.delete(projectTransErrorRecord);
	}
	
	/**
	 * 删除 days前的异常交易记录
	 * @param days
	 */
	@Transactional(readOnly = false)
	public void deleteBeforeDaysErrorRecord(int days){
		dao.deleteBeforeDaysErrorRecord(days);
	}
	
	/**
	 * 初始化异常交易
	 * @param record
	 */
	@Transactional(readOnly = false)
	public void initTransErrorRecord(ProjectInvestmentRecord record){
		if(StringUtils.isNotBlank(record.getThirdPartyOrder())){
			String resp = yeepayCommonHandler.query(record.getThirdPartyOrder(), DIRECT_QUERY_MODE);
			QueryCpTranscationResp queryCpTranscationResp = JaxbMapper.fromXml(resp, QueryCpTranscationResp.class);
			List<QueryCpTranscationItem> items = queryCpTranscationResp.getRecords();
			if(items != null && items.size() > 0){
				QueryCpTranscationItem item = items.get(0);
				if(!"CANCEL".equals(item.getStatus())){
					this.addTransErrorRecord(record, resp);
				}
			}
		}
	}
	
	/**
	 * 异常交易数据处理
	 * @param record
	 * @param resp
	 */
	public void addTransErrorRecord(ProjectInvestmentRecord record, String resp){
		int i = dao.getCountByThirdPartySeq(record.getThirdPartyOrder());
		if(i <= 0){
			ProjectTransErrorRecord errorRecord = new ProjectTransErrorRecord();
			errorRecord.setAccountId(record.getInvestmentUserId());
			errorRecord.setBizType(DIRECT_QUERY_MODE);
			errorRecord.setThirdPartySeq(record.getThirdPartyOrder());
			errorRecord.setThirdPartyResult(resp);
			errorRecord.setStatus("0");
			errorRecord.setCreateDt(new Date());
			dao.insert(errorRecord);
		}
	}
	
}