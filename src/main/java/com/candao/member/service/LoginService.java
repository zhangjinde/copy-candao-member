package com.candao.member.service;

import java.util.Map;

public interface LoginService {
	
	/**
	 * 调用总店接口，登录
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(Map<String, String> map);

}
