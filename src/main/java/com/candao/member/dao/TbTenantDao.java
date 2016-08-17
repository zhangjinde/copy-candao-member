package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantInfo;

/**
 * 租户管理处理接口
 * @author tom-pc
 *
 */
public interface TbTenantDao {
	
	
	final static String PREFIX = TbTenantDao.class.getName();

	/**
	 * 插入租户信息
	 * 通过controller 接收总店下发分店数据来完成分店数据的同步
	 * @param tenantInfo
	 * @return
	 */
	int saveTenantInfo(TbTenantInfo tenantInfo);
	
	/**
	 * 校验租户的密码,校验完成后生成相应的安全码给调用渠道
	 * 传递参数是tenantId 和 password
	 * @return
	 */
	TbTenantInfo verifyTenantByPwd(TbTenantInfo tenantInfo);

	/**
	 * query if exist security key
	 * @param securityKey
	 * @return
	 */
	int verifySecurityKey(String securityKey);
	
    
	TbTenantInfo get(Map<String, Object> params);
	
	TbTenantInfo findOne(java.lang.String id);
	
	<T, K, V> List<T> find(Map<K, V> params);
	
	<T, K, V> List<T> getCount(Map<K, V> params);
	
	int insert(TbTenantInfo tbTenantInfo);
	
	int update(TbTenantInfo tbTenantInfo);
	
	int delete(Map<String, Object> params );
	
	<E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);

	int findMaxID();

	List<Map<String,Object>> findListByParams(Map<String, Object> params);

	int deleteList(Map<String, Object> map);
	
	int getTenantInfoCount(Map<String, Object> map);
	
	List<TbTenantInfo> findTenantInfoByBid(String branchId,Integer id);
}

