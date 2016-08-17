package com.candao.member.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TbMemberManagerDao;
import com.candao.member.model.TbMemberAndTenantCard;
import com.candao.member.model.TbMemberManager;
/**
 * 数据访问接口
 * @author mew
 *
 */
@Repository
public class TbMemberManagerDaoImpl implements TbMemberManagerDao {
    @Autowired
    private DaoSupport dao;
	@Override
	public TbMemberManager get(Map<String, Object> params) {
		return dao.get(PREFIX + ".get", params);
	}
	
	@Override
	public TbMemberManager findOne(java.lang.String id) {
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
	/*public <T, K, V> List<T> find(Map<K, V> params) {
		return dao.find(PREFIX + ".find", params);
	}*/
	@Override
	public int insert(TbMemberManager tbMemberManager) {
		return dao.insert(PREFIX + ".insert", tbMemberManager);
	}

	@Override
	public int update(TbMemberManager tbMemberManager) {
		return dao.update(PREFIX + ".update", tbMemberManager);
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
	public int insertMemberCard(TbMemberAndTenantCard tbMemberAndTenantCard) {
		return dao.insert(PREFIX + ".insertMemberCard", tbMemberAndTenantCard);
	}

	@Override
	public List<Map<String, Object>> findListByParams(
			Map<String, Object> jsonMap) {
		return dao.find(PREFIX + ".findListByParams", jsonMap);
	}

	@Override
	public Integer findId(TbMemberManager tbMemberManager) {
		// TODO Auto-generated method stub
		return dao.find(PREFIX+".findId",tbMemberManager);
	}
	
	@Override
	public Integer findMemberCardId(Object prams) {
		// TODO Auto-generated method stub
		return dao.find(PREFIX+".findMemberCardId",prams);
	}

	@Override
	public int updateImport(TbMemberManager tbMemberManager) {
		// TODO Auto-generated method stub
		return dao.update(PREFIX + ".updateImport", tbMemberManager);
	}

	@Override
	public List<Map<String, Object>> findNocardByMobile(Map<String, Object> params) {
		return dao.find(PREFIX+".findNocardByMobile", params);
	}
}


