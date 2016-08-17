package com.candao.member.plugin.common;

import com.candao.member.vo.LoginUser;

public class UserCacheBean {
	/** sessionID*/
	private String sessionID;
	/** 登录用户 */
	private LoginUser user;

	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}

}
