package com.candao.member.plugin.common;

import java.util.HashMap;
import java.util.Map;

public class PermissionCollection {
	private static PermissionCollection instance = new PermissionCollection();

	public static PermissionCollection getInstance() {
		return instance;
	}

	private PermissionCollection() {
	}

	/** sessionID和用户缓存信息的集合 */
	private Map<String, UserCacheBean> mapSU = new HashMap<String, UserCacheBean>();

	public void addSessionUserCache(String sessionID, UserCacheBean uc) {
		mapSU.put(sessionID, uc);
	}

	public void removeSessionUserCache(String sessionID) {
		mapSU.remove(sessionID);
	}

	public UserCacheBean getUserCache(String sessionID) {
		return mapSU.get(sessionID);
	}
	
}
