package com.candao.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TbTenantDao;
import com.candao.member.model.TbTenantInfo;

/**
 * 租户插入基础信息和安全校验
 * @author tom-pc
 *
 */
@Repository
public class TbTenantDaoImpl implements TbTenantDao{

	
	@Autowired
    private DaoSupport dao;

	@Override
	public int saveTenantInfo(TbTenantInfo tenantInfo) {
		
		  return dao.insert(PREFIX + ".saveTenantInfo", tenantInfo);
	}

	@Override
	public TbTenantInfo verifyTenantByPwd(TbTenantInfo tenantInfo) {
		 return dao.findOne(PREFIX + ".verifyTenantByPwd", tenantInfo);
	}
	
	@Override
	public int verifySecurityKey(String securityKey){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("securityKey", securityKey);
		return dao.countRecord(PREFIX + ".verifySecurityKey", map);
	}
	

	@Override
	public TbTenantInfo get(Map<String, Object> params) {
		return dao.get(PREFIX + ".get", params);
	}
	
	@Override
	public TbTenantInfo findOne(java.lang.String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return dao.get(PREFIX + ".findOne", params);
	}
	
	@Override
	public <T, K, V> List<T> find(Map<K, V> params) {
		
		return dao.find(PREFIX + ".find", params);
	}
	public <T, K, V> List<T> getCount(Map<K, V> params) {
		return dao.find(PREFIX+".count",params);
	}
	
	@Override
	public int insert(TbTenantInfo tbTenantInfo) {
		return dao.insert(PREFIX + ".insert", tbTenantInfo);
	}

	@Override
	public int update(TbTenantInfo tbTenantInfo) {
		return dao.update(PREFIX + ".update", tbTenantInfo);
	}

	@Override
	public int delete(Map<String, Object> params) {
		return dao.delete(PREFIX + ".delete", params);
	}
	
	@Override
	public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize) {
		return dao.page(PREFIX + ".page", params, current, pagesize);
	}

	@Override
	public int findMaxID() {
		return dao.get(PREFIX + ".findMaxID", null);
	}

	@Override
	public List<Map<String,Object>> findListByParams(Map<String, Object> params) {
		return dao.find(PREFIX+".findListByParams",params);
	}

	@Override
	public int deleteList(Map<String, Object> map) {
		return dao.delete(PREFIX + ".deleteList", map);
	}

	@Override
	public int getTenantInfoCount(Map<String, Object> map) {
		return dao.get(PREFIX + ".getTenantInfoCount", map);
	}

	@Override
	public List<TbTenantInfo> findTenantInfoByBid(String branchId,Integer id) {
		Map<String, Object> map = new HashMap<>();
		map.put("branchId", branchId);
		map.put("id", id);
		return dao.find(PREFIX + ".findTenantInfoByBid", map);
	}
}
