package com.candao.member.dao;


import java.util.List;
import java.util.Map;

import com.candao.member.model.TCardAccount;

public interface TCardAccountDAO {
	
	final static String PREFIX = TCardAccountDAO.class.getName();
	
	int update(TCardAccount tCardAccount);
	
	int insert(TCardAccount tCardAccount);
	
	TCardAccount getByCardNo(String cardNo);

	List<TCardAccount> getCardAccountByCardnos(String cardnos);

	Map<String, Object> getSum(Map<String, Object> params);
	
}
