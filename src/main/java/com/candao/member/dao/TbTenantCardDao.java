package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantCard;


public interface TbTenantCardDao {
public final static String PREFIX = TbTenantCardDao.class.getName();
    
	public TbTenantCard get(Map<String, Object> params);
	
	public  TbTenantCard findOne(java.lang.String id);
	

	public <T, K, V> List<T> find(Map<K, V> params);
	
	public <T, K, V> List<T> getCount(Map<K, V> params);
	
	public int insert(TbTenantCard tbTenantCard);
	
	public int update(TbTenantCard tbTenantCard);
	
	public int delete(Map<String, Object> params );

	public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);

	public int findMaxID();

}
