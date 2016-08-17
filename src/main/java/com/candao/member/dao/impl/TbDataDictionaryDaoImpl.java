package com.candao.member.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TbDataDictionaryDao;
import com.candao.member.model.TbDataDictionary;
/**
 * 数据访问接口
 * @author mew
 *
 */
@Repository
public class TbDataDictionaryDaoImpl implements TbDataDictionaryDao {
    @Autowired
    private DaoSupport dao;
	@Override
	public TbDataDictionary get(java.lang.String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return dao.get(PREFIX + ".get", params);
	}
	@Override
    public <K, V> Map<K, V>  getDish(java.lang.String id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return dao.get(PREFIX + ".getDish", params);
	}
	@Override
	public  <K, V> Map<K, V>  getDishUnit(java.lang.String id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return dao.get(PREFIX + ".getDishUnit", params);
	}
	@Override
	public <K, V> Map<K, V> findOne(java.lang.String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return dao.get(PREFIX + ".findOne", params);
	}

	@Override
	public <T, K, V> List<T> find(Map<K, V> params) {
		return dao.find(PREFIX + ".find", params);
	}

	@Override
	public int insert(TbDataDictionary tbDataDictionary) {
		return dao.insert(PREFIX + ".insert", tbDataDictionary);
	}

	@Override
	public int update(TbDataDictionary tbDataDictionary) {
		return dao.update(PREFIX + ".update", tbDataDictionary);
	}
	
	@Override
	public int delete(java.lang.String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dishTasteId", id);
		return dao.delete(PREFIX + ".deleteTasteId", params);
	}
	
	@Override
	public List<Map<String, Object>> getDataDictionaryTag() {
		return dao.find(PREFIX+".getDataDictionaryTag");
	}

	@Override
	public List<Map<String, Object>> getTypeandTypename() {
		// TODO Auto-generated method stub
		return dao.find(PREFIX+".getTypeandTypename");
	}

	@Override
	public List<Map<String, Object>> getDatasByType(String type) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		return dao.find(PREFIX+".getDatasByType",params);
	}

	@Override
	public int delDishTasteDao(String dishTasteId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dishTasteId", dishTasteId);
		return dao.delete(PREFIX + ".deleteTasteId", params);
	}

	@Override
	public <T, K, V> List<T> getSystemList() {
		Map<String, Object> params = new HashMap<String, Object>();
		return dao.find(PREFIX + ".getAllSystem", params);
	}

}


