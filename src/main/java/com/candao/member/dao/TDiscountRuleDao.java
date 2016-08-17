package com.candao.member.dao;

import java.util.List;
import java.util.Map;

public interface TDiscountRuleDao {
	
	public final static String PREFIX = TDiscountRuleDao.class.getName();
	
	public<K, V, E> List<E> getdiscountRuleByParam(Map<K, V> param); 
	
}
