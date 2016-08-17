package com.candao.member.service;

import java.util.Map;

public interface SystemLogService {
	
	/**
	 * 
	 * @param log
	 * @return
	 */
	public boolean saveSystemLog(Map<String,Object> log);

}
