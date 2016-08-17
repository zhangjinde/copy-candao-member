package com.candao.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TDealDetailDAO;
import com.candao.member.model.TDealDetail;


@Repository
public class TDealDetailDAOImpl  implements TDealDetailDAO{
	
	@Autowired
	private DaoSupport dao;
	
	@Override
	  public int add(TDealDetail tDealDetail) {
	    return dao.insert(PREFIX + ".insert", tDealDetail);
	  }
	
	@Override
	public <T> List<T> findByDealNo(String dealNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deal_no", dealNo);
		return dao.find(PREFIX + ".get", params);
	}

	@Override
	public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize) {
		return dao.page(PREFIX + ".page", params, current, pagesize);
	}

	@Override
	public <T, K, V> List<T> findDealByParam(Map<K, V> params) {
		return dao.find(PREFIX + ".findDealByParam", params);
	}

	@Override
	public List<TDealDetail> getStoreDealDetail(Map<String, Object> param) {
		return dao.find(PREFIX + ".getStoreDealDetail", param);
	}

	@Override
	public List<TDealDetail> getDealDetailByCardnos(String cardnos) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardnos", cardnos);
		return dao.find(PREFIX + ".getDealDetailByCardnos", map);
	}
	
	@Override
	public List<TDealDetail> getDealDetailByCardnos(String cardnos,Map<String, Object> param) {
//		Map<String, Object> map = new HashMap<>();
		param.put("cardnos", cardnos);
		return dao.find(PREFIX + ".getDealDetailByCardnos", param);
	}

	@Override
	public List<HashMap<String, Object>> getDealType(String tenant_id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		map.put("tenant_id", tenant_id);
		return dao.find(PREFIX + ".getDealType", map);
	}

	@Override
	public HashMap<String, Object> getAddImportInfo(String tenant_id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		map.put("tenant_id", tenant_id);
		return (HashMap<String, Object>) dao.findOne(PREFIX + ".getAddImportInfo", map);
	}

	@Override
	public List<TDealDetail> getOldCardDealDetailByTime(Map<String, Object> param) {
		return dao.find(PREFIX + ".getOldCardDealDetailByTime", param);
	}

	@Override
	public List<TDealDetail> getDealDetailByTime(Map<String, Object> param) {
		return dao.find(PREFIX + ".getDealDetailByTime", param);
	}

}
