package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberAndTenantCard;
import com.candao.member.model.TbMemberManager;


public interface TbMemberManagerDao {
public final static String PREFIX = TbMemberManagerDao.class.getName();
    
	public TbMemberManager get(Map<String, Object> params);
	
	public  TbMemberManager findOne(java.lang.String id);
	

	public <T, K, V> List<T> find(Map<K, V> params);
	
	public <T, K, V> List<T> getCount(Map<K, V> params);
	
	public int insert(TbMemberManager tbPrinterManager);
	
	public int update(TbMemberManager tbPrinterManager);
	
	public int delete(Map<String, Object> params );

	public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);

	public int findMaxID();

	public int insertMemberCard(TbMemberAndTenantCard tbMemberAndTenantCard);

	public List<Map<String, Object>> findListByParams(
			Map<String, Object> jsonMap);

	public Integer findId(TbMemberManager tbMemberManager);

	public Integer findMemberCardId(Object cardInfo);

	public int updateImport(TbMemberManager tbMemberManager);

	public List<Map<String, Object>> findNocardByMobile(Map<String, Object> params);

}
