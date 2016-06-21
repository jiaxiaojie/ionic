/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.api.to.NoticeResp;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {
	
	public List<Article> findByIdIn(String[] ids);
//	{
//		return find("from Article where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	
	public int updateHitsAddOne(String id);
//	{
//		return update("update Article set hits=hits+1 where id = :p1", new Parameter(id));
//	}
	
	public int updateExpiredWeight(Article article);
	
	public List<Category> findStats(Category category);
//	{
//		return update("update Article set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}


	/**
	 * API 获取文章(新闻公告、媒体报道)分页列表
	 * @param categoryId 分类ID
	 * @param startNumber 分页 - 开始数量
	 * @param endNumber 分页 - 结束数量
	 * @return
	 */
	public List<Article> getArticlePageList(@Param("categoryId") String categoryId, @Param("startNumber") int startNumber, @Param("endNumber") int endNumber);

	/**
	 * API 获取文章(新闻公告、媒体报道)数量
	 * @param categoryId 分类ID
	 * @return
     */
	public long countArticle(String categoryId);

	/**
	 * API 根据id获取Article对象(单表查询)
	 * @param id
	 * @return
	 */
	public Article getArticleById(String id);

}
