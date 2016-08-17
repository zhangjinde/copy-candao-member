package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberManager;

public interface MemmberInfoManagerDao {
	final static String PREFIX = MemmberInfoManagerDao.class.getName();
	
	<E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);

	void getStoreDealDetail();

	List<Map<String, Object>> findCardLevelInfo(Map<String, Object> params);
	
	List<Map<String, Object>> findCardStatusInfo(Map<String, Object> params);

	Page<Map<String, Object>> pageInfo(Map<String, Object> param, Integer page, Integer rows);

	List<Map<String, Object>> getWriteExcel(Map<String, Object> params);
	
	<E, K, V> Page<E> pageTransInfo(Map<K, V> params, Integer current, Integer pagesize);
	
	List<Map<String, Object>> getTransInfoList(Map<String, Object> params);
	
	List<Map<String, Object>> getTransInfoDetailList(Map<String, Object> params);

	List<Map<String, Object>> findStoresName(Map<String, Object> params);

	Map<String, Object> findMemberByCardNo(Map<String, Object> param);

	void updateMemberInfo(TbMemberManager info);

	List<Map<String, Object>> findCardNum(Map<String, Object> param);

	List<Map<String, Object>> findMemberNum(Map<String, Object> param);

	List<Map<String, Object>> findMemberByMobile(Map<String, Object> param);

	List<Map<String, Object>> findCardNumByCardNo(Map<String, Object> param);

	<E, K, V> Page<E> getReportPage(Map<K, V> params, Integer current, Integer pagesize);

	List<Map<String, Object>> getReportsList(Map<String, Object> param);
}
