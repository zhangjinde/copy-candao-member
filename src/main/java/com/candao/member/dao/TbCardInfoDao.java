package com.candao.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbCardInfo;


public interface TbCardInfoDao {
public final static String PREFIX = TbCardInfoDao.class.getName();
    
	TbCardInfo get(Map<String, Object> params);
	
	TbCardInfo getActualCard(Map<String, Object> params);
	
	TbCardInfo findOne(java.lang.String id);

	<T, K, V> List<T> find(Map<K, V> params);
	
	<T, K, V> List<T> getCount(Map<K, V> params);
	
	int insert(TbCardInfo tbCardInfo);
	
	int update(TbCardInfo tbCardInfo);
	
	int delete(Map<String, Object> params );

	<E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);

	int findMaxID();
	
	HashMap<String,Object> getByCardNo(String cardNo);

	List<Map<String,Object>> findListByParams(Map<String, Object> params);

	int deleteList(Map<String, Object> map);

	List<TbCardInfo> getAllCardInfo(Map<String, Object> map);

	int findIsExist(String tenantid, String pno);

	TbCardInfo getCardInfoByParam(Map<String, Object> map);

	TbCardInfo byUserToUse(HashMap<String, Object> paramsJson);

	Integer findId(String tENANTID, String cardNo);
	
	public HashMap<String, Object> findInfoId(String tENANTID, String cardNo);

    /**     * findIsExist(这里用一句话描述这个方法的作用) 
     * TODO(这里描述这个方法适用条件 – 可选) 
     * TODO(这里描述这个方法的执行流程 – 可选) 
     * TODO(这里描述这个方法的使用方法 – 可选) 
     * TODO(这里描述这个方法的注意事项 – 可选) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
	
	List<TbCardInfo> getCardInfoByTime(Map<String, Object> param);
}
