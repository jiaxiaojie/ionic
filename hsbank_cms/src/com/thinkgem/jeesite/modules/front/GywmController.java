/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.cms.CmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.entity.Link;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.cms.service.LinkService;
import com.thinkgem.jeesite.modules.cms.service.SiteService;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;

/**
 * 关于我们Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "${frontPath}/gywm")
public class GywmController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private LinkService linkService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SiteService siteService;

	@RequestMapping("index")
	public String gywm(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "gsjj");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='#'>关于我们</a>");
		return "modules/front/gywm/gsjj";
	}
	
	/**
	 * 关于我们-公司简介
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("gsjj")
	public String gsjj(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "gsjj");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm/index'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>公司简介</a>");
		return "modules/front/gywm/gsjj";
	}
	
	/**
	 * 关于我们-管理团队
	 * @param model
	 * @return
	 */
	@RequestMapping("gltd")
	public String gltd(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "gltd");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm/index'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>管理团队</a>");
		return "modules/front/gywm/gltd";
	}
	
	/**
	 * 关于我们-安全保障
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz")
	public String aqbz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm/index'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>安全保障</a>");
		return "modules/front/gywm/aqbz";
	}
	
	/**
	 * 关于我们-加入我们
	 * @param model
	 * @return
	 */
	@RequestMapping("jrwm")
	public String jrwm(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "jrwm");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm/index'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>加入我们</a>");
		return "modules/front/gywm/jrwm";
	}
	
	/**
	 * 关于我们-联系我们
	 * @param model
	 * @return
	 */
	@RequestMapping("lxwm")
	public String lxwm(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "lxwm");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm/index'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>联系我们</a>");
		return "modules/front/gywm/lxwm";
	}
	
	/**
	 * 关于我们-公司新闻
	 * @param request
	 * @param response
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("gsxw")
	public String newsList(HttpServletRequest request, HttpServletResponse response, 
			Model model, @RequestParam(required=false, defaultValue="1") Integer pageNo,
			@RequestParam(required=false, defaultValue="15") Integer pageSize) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "gsxw");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm/index'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>公司新闻</a>");
		String categoryId = CmsConstant.CATEGORYID_FDXW;
		String returnPath = "modules/front/gywm/gsxw";
		

		return getArticleList(pageNo, pageSize, model, categoryId, returnPath);
	}
	
	private String getArticleList(Integer pageNo, Integer pageSize,
			Model model, String categoryId, String returnPath) {
		Category category = categoryService.get(categoryId);
		if (category==null){
			Site site = CmsUtils.getSite(Site.defaultSiteId());
			model.addAttribute("site", site);
			return "error/404";
		}
		Site site = siteService.get(category.getSite().getId());
		model.addAttribute("site", site);
		// 2：简介类栏目，栏目第一条内容
		if("2".equals(category.getShowModes()) && "article".equals(category.getModule())){
			// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
			List<Category> categoryList = Lists.newArrayList();
			if (category.getParent().getId().equals("1")){
				categoryList.add(category);
			}else{
				categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId());
			}
			model.addAttribute("category", category);
			model.addAttribute("categoryList", categoryList);
			// 获取文章内容
			Page<Article> page = new Page<Article>(1, 1, -1);
			Article article = new Article(category);
			page = articleService.findPage(page, article, false);
			if (page.getList().size()>0){
				article = page.getList().get(0);
				article.setArticleData(articleDataService.get(article.getId()));
				articleService.updateHitsAddOne(article.getId());
			}
			model.addAttribute("article", article);
            CmsUtils.addViewConfigAttribute(model, category);
            CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
    		return returnPath;
//			return "modules/cms/front/themes/"+site.getTheme()+"/"+getTpl(article);
		}else{
			List<Category> categoryList = categoryService.findByParentId(category.getId(), category.getSite().getId());
			// 展现方式为1 、无子栏目或公共模型，显示栏目内容列表
			if("1".equals(category.getShowModes())||categoryList.size()==0){
				// 有子栏目并展现方式为1，则获取第一个子栏目；无子栏目，则获取同级分类列表。
				if(categoryList.size()>0){
					category = categoryList.get(0);
				}else{
					// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
					if (category.getParent().getId().equals("1")){
						categoryList.add(category);
					}else{
						categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId());
					}
				}
				model.addAttribute("category", category);
				model.addAttribute("categoryList", categoryList);
				// 获取内容列表
				if ("article".equals(category.getModule())){
					Page<Article> page = new Page<Article>(pageNo, pageSize);
					//System.out.println(page.getPageNo());
					page = articleService.findPage(page, new Article(category), false);
					model.addAttribute("page", page);
					// 如果第一个子栏目为简介类栏目，则获取该栏目第一篇文章
					if ("2".equals(category.getShowModes())){
						Article article = new Article(category);
						if (page.getList().size()>0){
							article = page.getList().get(0);
							article.setArticleData(articleDataService.get(article.getId()));
							articleService.updateHitsAddOne(article.getId());
						}
						model.addAttribute("article", article);
			            CmsUtils.addViewConfigAttribute(model, category);
			            CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
			    		return returnPath;
//						return "modules/cms/front/themes/"+site.getTheme()+"/"+getTpl(article);
					}
				}else if ("link".equals(category.getModule())){
					Page<Link> page = new Page<Link>(1, -1);
					page = linkService.findPage(page, new Link(category), false);
					model.addAttribute("page", page);
				}
	            CmsUtils.addViewConfigAttribute(model, category);
                site =siteService.get(category.getSite().getId());
                //System.out.println("else 栏目第一条内容 _2 :"+category.getSite().getTheme()+","+site.getTheme());
        		return returnPath;
//				return "modules/cms/front/themes/"+siteService.get(category.getSite().getId()).getTheme()+view;
				//return "modules/cms/front/themes/"+category.getSite().getTheme()+view;
			}
			// 有子栏目：显示子栏目列表
			else{
				model.addAttribute("category", category);
				model.addAttribute("categoryList", categoryList);
	            CmsUtils.addViewConfigAttribute(model, category);
	    		return returnPath;
//				return "modules/cms/front/themes/"+site.getTheme()+view;
			}
		}
	}
	/**
	 * 关于我们-公司新闻-新闻详情
	 * @param model
	 * @return
	 */
	@RequestMapping("gsxw/detail")
	public String gsxwDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "gywm");
		model.addAttribute("one_menu", "gsxw");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/gywm'>关于我们</a>&nbsp;&gt;&nbsp;<a href='#'>公司新闻</a>");
		return "modules/front/gywm/gsxw/detail";
	}
	
}
