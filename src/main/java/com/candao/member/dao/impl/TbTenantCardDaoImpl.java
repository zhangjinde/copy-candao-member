package com.candao.member.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TbTenantCardDao;
import com.candao.member.model.TbTenantCard;
/**
 * 数据访问接口
 * @author mew
 *
 */
@Repository
public class TbTenantCardDaoImpl implements TbTenantCardDao {
    @Autowired
    private DaoSupport dao;
	@Override
	public TbTenantCard get(Map<String, Object> params) {
		return dao.get(PREFIX + ".get", params);
	}
	
	@Override
	public TbTenantCard findOne(java.lang.String id) {
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
	public int insert(TbTenantCard tbTenantCard) {
		return dao.insert(PREFIX + ".insert", tbTenantCard);
	}

	@Override
	public int update(TbTenantCard tbTenantCard) {
		return dao.update(PREFIX + ".update", tbTenantCard);
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
	
}


