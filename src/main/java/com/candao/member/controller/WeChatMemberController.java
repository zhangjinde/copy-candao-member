package com.candao.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.service.DealService;
import com.candao.member.service.WeChatMemberService;
import com.candao.member.utils.JacksonJsonMapper;

@Controller
@RequestMapping("/weChatMember")
public class WeChatMemberController extends BaseController{
	
	@Autowired
	private WeChatMemberService weChatMemberService;
	
	@Autowired
	private DealService dealService;
	
	@RequestMapping("/index")
	public String index(){
		return "wechat/index";
	}
	
	
	/**
	 * 跳转登录页
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "wechat/login";
	}
	
	/**
	 * 用户中心
	 * @return
	 */
	@RequestMapping("/userCenter")
	public String userCenter(){
		return "wechat/userCenter";
	}
	
	/**
	 * 个人信息
	 * @return
	 */
	@RequestMapping("/personalInfo")
	public String personalInfo(){
		return "wechat/personalInfo";
	}
	
	/**
	 * 申请会员
	 * @return
	 */
	@RequestMapping("/apply")
	public String apply(){
		return "wechat/apply";
	}
	
	/**
	 * 忘记密码
	 * @return
	 */
	@RequestMapping("/forgetPassword")
	public String forgetPassword(){
		return "wechat/forgetPassword";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(){
		return "wechat/modifyPassword";
	}
	
	/**
	 * 完善资料
	 * @return
	 */
	@RequestMapping("/perfectInfo")
	public String perfectInfo(){
		return "wechat/perfectInfo";
	}
	
	/**
	 * 交易记录
	 * @return
	 */
	@RequestMapping("/record")
	public String record(){
		return "wechat/record";
	}

	/**
	 * 操作提示
	 * @return
	 */
	@RequestMapping("/wxTips")
	public String wxTips(){
		return "wechat/wxTips";
	}
	
	/**
	 * 我的余额
	 * @return
	 */
	@RequestMapping("/mybalance")
	public String myBalance(){
		return "wechat/balance";
	}
	
	/**
	 * 绑定微信会员（登录）
	 * @return
	 */
	@RequestMapping("/binding")
	@ResponseBody
	public String binding(@RequestBody String body){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try{
			Map<String, Object> resultMap = weChatMemberService.binding(params);
			if(resultMap.isEmpty()){
				result = getFailure("账号或密码错误");
			}else{
				result = getSuccess(resultMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			result = getFailure("绑定失败");
		}
		return result;
	}


	/**
	 * 会员注册
	 * @param body
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public String register(@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try{
			Map<String, Object> resultMap = weChatMemberService.register(params,request,response);
			String code = (String) resultMap.get("code");
			if("1".equals(code)){
				result = getFailure((String)resultMap.get("msg"));
			}else{
				result = getSuccess(resultMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			result = getFailure("注册会员失败");
		}
		return result;
	}

	/**
	 * 查询个人余额
	 * @return
	 */
	@RequestMapping("/balance")
	@ResponseBody
	public String balance(@RequestBody String body){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try{
			Map<String, Object> resultMap = weChatMemberService.balance(params);
			if(resultMap.isEmpty()){
				result = getFailure("个人余额查询失败");
			}else{
				result = getSuccess(resultMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			result = getFailure("个人余额查询失败");
		}
		return result;
	}
	
	/**
	 * 查询会员个人信息
	 * @param body
	 * @return
	 */
	@RequestMapping("/getMemberInfo")
	@ResponseBody
	public String getMemberInfo(@RequestBody String body){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		Map<String, Object> result = new HashMap<>();
		String resultMap = "";
		try {
			result = weChatMemberService.getMemberInfo(params);
			resultMap = getSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = getFailure("查询会员个人信息失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 完善会员信息
	 * @return
	 */
	@RequestMapping("/modifyMember")
	@ResponseBody
	public String modifyMember(@RequestBody String body){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try {
			weChatMemberService.modifyMember(params);
			result = getSuccess(); 
		} catch (Exception e) {
			result = getFailure("完善会员信息失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 验证手机号是否已经注册
	 * @return
	 */
	@RequestMapping("/verifyPhoneNumber")
	@ResponseBody
	public String verifyPhoneNumber(@RequestBody String body){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try {
			TbMemberInfo memberInfo = weChatMemberService.verifyPhoneNumber(params);
			if(memberInfo != null){
				result = getFailure("该手机号已被注册");
			}else{
				result = getSuccess();
			}
		} catch (Exception e) {
			result = getFailure("手机号验证失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 验证会员是否是默认信息（默认的个人资料）
	 * @return
	 */
	@RequestMapping("/verifyDefaultInfo")
	@ResponseBody
	public String verifyDefaultInfo(@RequestBody String body){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		Map<String, Object> map = new HashMap<>();
		try {
			Boolean flag = weChatMemberService.verifyDefaultInfo(params);
			if(flag){
				map.put("isDefaultInfo", true);
				result = getSuccess(map);
			}else{
				map.put("isDefaultInfo", false);
				result = getSuccess(map);
			}
		} catch (Exception e) {
			result = getFailure("操作失败");
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 修改会员登录密码
	 * @return
	 */
	@RequestMapping("/modifyPassword")
	@ResponseBody
	public String modifyPassword(@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try {
			Map<String,Object> map = weChatMemberService.modifyPassword(params,request,response);
			if("0".equals(map.get("code"))){
				result = getSuccess(map.get("msg"));
			}else{
				result = getFailure((String)map.get("msg"));
			}
		} catch (Exception e) {
			result = getFailure("修改会员登录密码失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取验证码
	 * @return
	 */
	@RequestMapping("/getVerifyCode")
	@ResponseBody
	public String getVerifyCode(@RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = JacksonJsonMapper.jsonToObject(body, Map.class);
		String result = "";
		try {
			Map<String, Object> map = weChatMemberService.getVerifyCode(params,request,response);
			result = getSuccess(map);
		} catch (Exception e) {
			result = getFailure("发送验证码失败");
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 根据会员卡号查询消费记录
	 * @param cardno
	 * @param current
	 * @return
	 */
	@RequestMapping("/myRecord")
	@ResponseBody
	public String myRecord(String cardno,@RequestParam(value = "current", defaultValue = "1") Integer current,
			@RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("card_no", cardno);
		Page<Map<String, Object>> pagemap = dealService.grid(map, current, pagesize);
		resultMap.put("detailTotal", pagemap.getTotal());
		resultMap.put("detailTotalpage", pagemap.getPageCount());
		resultMap.put("detailCurrent", current);
		resultMap.put("detailDatas", pagemap.getRows());
		resultMap.put("cardno", cardno);
		return JacksonJsonMapper.objectToJson(resultMap);
	}
	
	
}
