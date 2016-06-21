/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.modules.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.cms.CmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.carousel.service.CarouselInfoService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectShowTermService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;

/**
 * 首页Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class IndexController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private CarouselInfoService carouselInfoService;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	

	@RequestMapping("site")
	public String site(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/front/layouts/default";
	}

	@RequestMapping("")
	public String f(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return index(request, response, model);
	}

	@RequestMapping("index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String upPath = (String) request.getSession().getAttribute("up_path");
		if ((upPath != null) && (!upPath.equals("")) && (upPath.length() > 8)) {
			String loaction = "";
			if (request.getServerPort() != 80) {
				loaction = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getRequestURI()
						+ "?" + request.getQueryString();
			} else {
				loaction = request.getScheme() + "://"
						+ request.getServerName() + request.getRequestURI()
						+ "?" + request.getQueryString();
			}
			if (StringUtils.isSameDomain(upPath, loaction)) {
				if (!loaction.startsWith(upPath)
						&& upPath.indexOf("resetPassword") == -1 && upPath.indexOf("register") == -1 && upPath.indexOf("login") == -1) {
					request.getSession().removeAttribute("up_path");
					return "redirect:" + upPath;
				}
			}
		}
		// 获取最新公告及富定新闻的第一条文章
		Category categoryZxgg = new Category();
		categoryZxgg.setId(CmsConstant.CATEGORYID_ZXGG);
		Page<Article> pageZxgg = new Page<Article>(1, 3, -1);
		Article articleZxgg = new Article(categoryZxgg);
		pageZxgg = articleService.findPage(pageZxgg, articleZxgg, false);
		if (pageZxgg.getList().size() > 0) {
			articleZxgg = pageZxgg.getList().get(0);
			articleZxgg.setArticleData(articleDataService.get(articleZxgg
					.getId()));
			for(Article zxgg : pageZxgg.getList()){
				zxgg.setArticleData(articleDataService.get(zxgg
						.getId()));
			}
			articleService.updateHitsAddOne(articleZxgg.getId());
			model.addAttribute("articleZxgg", articleZxgg);
			model.addAttribute("pageZxgg", pageZxgg);
		}

		Category categoryFdxw = new Category();
		categoryFdxw.setId(CmsConstant.CATEGORYID_FDXW);
		Page<Article> pageFdxw = new Page<Article>(1, 3, -1);
		Article articleFdxw = new Article(categoryFdxw);
		pageFdxw = articleService.findPage(pageFdxw, articleFdxw, false);
		if (pageFdxw.getList().size() > 0) {
			articleFdxw = pageFdxw.getList().get(0);
			articleFdxw.setArticleData(articleDataService.get(articleFdxw
					.getId()));
			for(Article fdxw : pageFdxw.getList()){
				fdxw.setArticleData(articleDataService.get(fdxw
						.getId()));
			}
			model.addAttribute("pageFdxw", pageFdxw);
			articleService.updateHitsAddOne(articleFdxw.getId());
			model.addAttribute("articleFdxw", articleFdxw);
		}

		// 获取推荐项目列表
		List<ProjectBaseInfo> projectBaseInfoList = projectBaseInfoService.getRecommendList();
		for(ProjectBaseInfo projectBaseInfo : projectBaseInfoList) {
			List<String> showTermList = new ArrayList<String>();
			for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId())) {
				showTermList.add(projectShowTerm.getTermCode());
			}
			projectBaseInfo.setShowTermList(showTermList);
		}
		// 获取推荐转让项目列表
		List<ProjectTransferInfo> projectTransferInfoList = projectTransferInfoService.getRecommendList();
		for(ProjectTransferInfo projectTransferInfo : projectTransferInfoList) {
			List<String> showTermList = new ArrayList<String>();
			for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectTransferInfo.getProjectId() + "")) {
				showTermList.add(projectShowTerm.getTermCode());
			}
			projectTransferInfo.getProjectBaseInfo().setShowTermList(showTermList);
		}
		model.addAttribute("projectBaseInfoList", projectBaseInfoList);
		model.addAttribute("projectTransferInfoList", projectTransferInfoList);
		// 是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		
		//获取轮播图信息
		String theDate = DateUtils.getDateTime();
		List<CarouselInfo> carouselInfos = carouselInfoService.getCarouselListByStatusAndTerm(ProjectConstant.CAROUSEL_INFO_STATUS_PASS, ProjectConstant.OP_TERM_DICT_PC, ProjectConstant.DICT_DEFAULT_VALUE, theDate);
		model.addAttribute("carouselInfos", carouselInfos);
		
		//获取活期理财数据
		CurrentProjectInfo currentProjectInfo = currentProjectInfoService.getFirstTenderingCurrentProjectInfo();
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		model.addAttribute("DAYS_IN_YEAR", CurrentProjectConstant.DAYS_IN_YEAR);
		if(currentProjectInfo != null && currentProjectInfo.getRate() != null){
			//每万元日收益
			model.addAttribute("everyWanDayEarnings", NumberUtils.formatNotRoundWithScale((10000*currentProjectInfo.getRate()/CurrentProjectConstant.DAYS_IN_YEAR), 2));
		}
		
		return "modules/front/index";
	}

	@RequestMapping("xszy")
	public String xszy(ProjectBaseInfo projectBaseInfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		model.addAttribute("nav", "<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='#'>新手指引</a>");
		return "modules/front/xszy";
	}

	@RequestMapping("xszq")
	public String xszq(ProjectBaseInfo projectBaseInfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		projectBaseInfo.setIsNewUser("0");
		List<ProjectBaseInfo> isNewUserProjectList = projectBaseInfoService
				.findIsNewUserProjectList(projectBaseInfo);
		// 新手项目
		model.addAttribute("isNewUserProjectList", isNewUserProjectList);
		model.addAttribute("menu", "xszy");
		model.addAttribute("flag", "1".equals(request.getParameter("flag")) ? "1" : "0");
		model.addAttribute("nav", "<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='#'>新手专区</a>");
		return "modules/front/xszq";
	}
	
	/**
	 * 
	 * 点击实时直播界面跳转控制
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("live")
	public String live(HttpServletRequest request, HttpServletResponse response,Model model) {
		return "modules/front/live";
	}
	
	/**
	 * 注册协议
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("agreement")
	public String agreement(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/front/agreement";
	}
	
	/**
	 * 注册协议
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("downloadApp")
	public String downloadApp(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/front/downloadApp";
	}

	/**
	 * 在弹出窗口中操作成功后跳转的页面
	 * 例如：jbox弹出iframe，在iframe中操作后iframe跳转到此页面；并在3秒后关闭jbox且刷新父页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "operateTip/success")
	public String operateTipSuccess() {
		return "modules/front/operateTip/success";
	}
	
	
}
