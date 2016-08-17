package com.candao.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.plugin.common.PermissionCollection;
import com.candao.member.plugin.common.UserCacheBean;
import com.candao.member.service.LoginService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.SessionUtils;
import com.candao.member.vo.LoginUser;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	private LoginService loginService ;
	
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("/loginNew");
		return mav;
	}
	
	@RequestMapping("/tenantIndex")
	public ModelAndView tenantIndex(){
		ModelAndView mav = new ModelAndView("/tenantLogin");
		return mav;
	}
	
	/**
	 * 租户登录会员系统，username只能是租户ID，
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(String username, String password, HttpSession session){
		ModelAndView mav = new ModelAndView("main");
		if(username == null || password ==null){
			if(SessionUtils.get(Constant.CURRENT_TENANT_ID) != null){
				return mav;
			}else{
				mav.addObject("message", "登录超时，请重新登录");
				mav.setViewName("loginNew");
				return mav;
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("tenantId", username);
		map.put("username", username);
		map.put("password", password);
		boolean isSuccess = loginService.login(map);
//		boolean isSuccess = true;
		if(!isSuccess){
			mav.setViewName("loginNew");
			mav.addObject("message", "登录失败，用户名或密码错误");
			return mav;
		}

		SessionUtils.put(Constant.CURRENT_TENANT_ID, username);
		UserCacheBean uc = new UserCacheBean();
		LoginUser user = new LoginUser();
		user.setAccounts(username);
		uc.setUser(user);
		PermissionCollection.getInstance().addSessionUserCache(session.getId(),uc);
		mav.addObject("username", username);
		return mav;
	}
	
	
	@RequestMapping("/tenantLogin")
	@ResponseBody
	public ModelAndView tenantLogin(String username, String password){
		ModelAndView mav = new ModelAndView("tenant/show");
//		ModelAndView mav = new ModelAndView("tenantMain");
		if(username == null || password ==null){
			if("tenantAdmin".equals(SessionUtils.get("CURRENT_USER"))){
				return mav;
			}else{
				mav.addObject("message", "登录超时，请重新登录");
				mav.setViewName("tenantLogin");
				return mav;
			}
		}
		if(username.equals("tenantAdmin") && password.equals("e10adc3949ba59abbe56e057f20f883e")){
			SessionUtils.put("CURRENT_USER", "tenantAdmin");
			return mav;
		}else{
			mav.setViewName("tenantLogin");
			mav.addObject("message", "登录失败，用户名或密码错误");
			return mav;
		}
	}
	
	
	/**
	 * 普通退出
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut")
	public ModelAndView loginOut(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/loginNew");
		request.getSession().invalidate();
		return mav;
	}
	
	/**
	 * 会员后台管理退出
	 * @param request
	 * @return
	 */
	@RequestMapping("/tenantLoginOut")
	public ModelAndView tenantLoginOut(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/tenantLogin");
		request.getSession().invalidate();
		return mav;
	}
}
