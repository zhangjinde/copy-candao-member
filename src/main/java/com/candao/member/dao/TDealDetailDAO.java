package com.candao.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TDealDetail;

public interface TDealDetailDAO {
	
	final static String PREFIX = TDealDetailDAO.class.getName();
	
	int add(TDealDetail tDealDetail);
	
	<T> List<T> findByDealNo(String dealNo);

	<E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);
	
	<T, K, V> List<T> findDealByParam(Map<K, V> params);
	
	List<TDealDetail> getStoreDealDetail(Map<String, Object> param);
	
	List<TDealDetail> getDealDetailByCardnos(String cardnos);
	
	List<TDealDetail> getDealDetailByCardnos(String cardnos,Map<String, Object> param);

	List<HashMap<String, Object>> getDealType(String tenant_id);

	HashMap<String, Object> getAddImportInfo(String tenant_id);

	List<TDealDetail> getOldCardDealDetailByTime(Map<String, Object> param);
	
	List<TDealDetail> getDealDetailByTime(Map<String, Object> param);
}
