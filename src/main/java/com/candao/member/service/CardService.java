package com.candao.member.service;

import java.util.Map;

public interface CardService {
	
	public Map<String,Object> getCardInfoByCardNo(String CardNo);
	
	public int findIsExist(String tenantid, String pno);

}
