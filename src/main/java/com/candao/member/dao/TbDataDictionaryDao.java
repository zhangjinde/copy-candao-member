package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.model.TbDataDictionary;

/**
 * 数据访问接口
 * 
 */
public interface TbDataDictionaryDao {
	public final static String PREFIX = TbDataDictionaryDao.class.getName();

	public TbDataDictionary get(java.lang.String id);
	
	public <K, V> Map<K, V> getDish(java.lang.String id);
	
	public <K, V> Map<K, V> getDishUnit(java.lang.String id);

	public <K, V> Map<K, V> findOne(java.lang.String id);

	public <T, K, V> List<T> find(Map<K, V> params);

	public int insert(TbDataDictionary tbDataDictionary);
	

	public int update(TbDataDictionary tbDataDictionary);

	public int delete(java.lang.String id);

	/**
	 * 取得数据分类
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getDataDictionaryTag();

	public List<Map<String, Object>> getTypeandTypename();

	/**
	 * 获取某个分类下的所有字典数据
	 * 
	 * @param type 分类
	 * @return
	 */
	public List<Map<String, Object>> getDatasByType(String type);

	public int delDishTasteDao(String dishTasteId);


	public <T, K, V> List<T> getSystemList();

}
