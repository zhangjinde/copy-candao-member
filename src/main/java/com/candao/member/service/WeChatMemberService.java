package com.candao.member.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.candao.member.model.TbMemberInfo;

public interface WeChatMemberService {

	Map<String, Object> binding(Map<String, Object> map) throws Exception;
	
	Map<String, Object> register(Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) throws Exception;

	Map<String, Object> balance(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getMemberInfo(Map<String, Object> map) throws Exception;
	
	TbMemberInfo verifyPhoneNumber(Map<String, Object> map) throws Exception;

	void modifyMember(Map<String, Object> map) throws Exception;

	boolean verifyDefaultInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> modifyPassword(Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	Map<String, Object> getVerifyCode(Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) throws Exception;
}
