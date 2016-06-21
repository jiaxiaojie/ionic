package com.thinkgem.jeesite.modules.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${frontPath}/help")
public class HelpController extends BaseController {
	
	/**
	 * 我要投资-新手必读
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wytz_xsbd")
	public String wytz_xsbd(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wytz");
		model.addAttribute("two_menu", "xsbd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>新手必读</a>");
		return "modules/front/help/wytz_xsbd";
	}
	
	/**
	 * 我要投资-产品介绍-风控
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wytz_cpjs")
	public String wytz_cpjs(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wytz");
		model.addAttribute("two_menu", "cpjs");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>产品介绍-风控</a>");
		return "modules/front/help/wytz_cpjs";
	}
	
	/**
	 * 我要投资-收益与费用
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wytz_syyfy")
	public String syyfy(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wytz");
		model.addAttribute("two_menu", "syyfy");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>收益与费用</a>");
		return "modules/front/help/wytz_syyfy";
	}
	
	/**
	 * 我要投资-项目投资
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wytz_xmtz")
	public String xmtz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wytz");
		model.addAttribute("two_menu", "xmtz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>项目投资</a>");
		return "modules/front/help/wytz_xmtz";
	}
	
	/**
	 * 我要投资-债权转让
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wytz_zqzr")
	public String zqzr(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wytz");
		model.addAttribute("two_menu", "zqzr");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>债权转让</a>");
		return "modules/front/help/wytz_zqzr";
	}
	
	/**
	 * 我要融资-产品介绍
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_cpjs")
	public String wyrz_cpjs(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "cpjs");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>产品介绍</a>");
		return "modules/front/help/wyrz_cpjs";
	}
	
	/**
	 * 我要融资-借款费用
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_jkfy")
	public String wyrz_jkfy(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "jkfy");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>借款费用</a>");
		return "modules/front/help/wyrz_jkfy";
	}
	
	/**
	 * 我要融资-如何申请
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_rhsq")
	public String wyrz_rhsq(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "rhsq");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>如何申请</a>");
		return "modules/front/help/wyrz_rhsq";
	}
	
	/**
	 * 我要融资-认证资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_rzzl")
	public String wyrz_rzzl(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "rzzl");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>认证资料</a>");
		return "modules/front/help/wyrz_rzzl";
	}
	
	/**
	 * 我要融资-信用审核
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_xysh")
	public String wyrz_xysh(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "xysh");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>信用审核</a>");
		return "modules/front/help/wyrz_xysh";
	}
	
	/**
	 * 我要融资-诚信认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_cxrz")
	public String wyrz_cxrz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "cxrz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>诚信认证</a>");
		return "modules/front/help/wyrz_cxrz";
	}
	
	/**
	 * 我要融资-信用等级与额度
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_xydjyed")
	public String wyrz_xydjyed(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "xydjyed");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>信用等级与额度</a>");
		return "modules/front/help/wyrz_xydjyed";
	}
	
	/**
	 * 我要融资-筹款与体现
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_ckytx")
	public String wyrz_ckytx(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "ckytx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>筹款与体现</a>");
		return "modules/front/help/wyrz_ckytx";
	}
	
	/**
	 * 我要融资-如何还款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("wyrz_rhhk")
	public String wyrz_rhhk(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "wyrz");
		model.addAttribute("two_menu", "rhhk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>如何还款</a>");
		return "modules/front/help/wyrz_rhhk";
	}
	
	/**
	 * 账户管理-登录注册
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_dlzc")
	public String zhgl_dlzc(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "dlzc");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>登录注册</a>");
		return "modules/front/help/zhgl_dlzc";
	}
	
	/**
	 * 账户管理-登录注册
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_zhmm")
	public String zhgl_zhmm(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "zhmm");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>账户密码</a>");
		return "modules/front/help/zhgl_zhmm";
	}
	
	/**
	 * 账户管理-充值
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_cz")
	public String zhgl_cz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "cz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>充值</a>");
		return "modules/front/help/zhgl_cz";
	}
	
	/**
	 * 账户管理-体现
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_tx")
	public String zhgl_tx(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "tx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>体现</a>");
		return "modules/front/help/zhgl_tx";
	}
	
	/**
	 * 账户管理-安全认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_aqrz")
	public String zhgl_aqrz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "aqrz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>安全认证</a>");
		return "modules/front/help/zhgl_aqrz";
	}
	
	/**
	 * 账户管理-消息中心
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_xxzx")
	public String zhgl_xxzx(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "xxzx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>消息中心</a>");
		return "modules/front/help/zhgl_xxzx";
	}
	
	/**
	 * 账户管理-优惠卷和花生豆
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("zhgl_yhq")
	public String zhgl_yhq(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "zhgl");
		model.addAttribute("two_menu", "yhq");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>优惠卷和花生豆</a>");
		return "modules/front/help/zhgl_yhq";
	}
	
	/**
	 * 安全保障-本金保障计划
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz_bjbzjh")
	public String aqbz_bjbzjh(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "bjbzjh");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>本金保障计划</a>");
		return "modules/front/help/aqbz_bjbzjh";
	}
	
	/**
	 * 安全保障-法律与政策保障
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz_flyzcbz")
	public String aqbz_flyzcbz(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "flyzcbz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>法律与政策保障</a>");
		return "modules/front/help/aqbz_flyzcbz";
	}
	
	/**
	 * 安全保障-借款审核与风控
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz_jkshyfk")
	public String aqbz_jkshyfk(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "jkshyfk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>借款审核与风控</a>");
		return "modules/front/help/aqbz_jkshyfk";
	}
	
	/**
	 * 安全保障-账户及隐私安全
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz_zhjysaq")
	public String aqbz_zhjysaq(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "zhjysaq");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>账户及隐私安全</a>");
		return "modules/front/help/aqbz_zhjysaq";
	}
	
	/**
	 * 安全保障-用户的自我保护
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz_yhdzwbh")
	public String aqbz_yhdzwbh(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "yhdzwbh");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>用户的自我保护</a>");
		return "modules/front/help/aqbz_yhdzwbh";
	}
	
	/**
	 * 安全保障-用户的自我保护
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aqbz_wzxgxy")
	public String aqbz_wzxgxy(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("menu", "help");
		model.addAttribute("one_menu", "aqbz");
		model.addAttribute("two_menu", "wzxgxy");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/help/wytz_xsbd'>帮助中心</a>&nbsp;&gt;&nbsp;<a href='#'>网站相关协议</a>");
		return "modules/front/help/aqbz_wzxgxy";
	}
}
