package com.candao.member.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.model.TbCardInfo;
/**
 * 数据访问接口
 * @author mew
 *
 */
@Repository
public class TbCardInfoDaoImpl implements TbCardInfoDao {
	
    @Autowired
    private DaoSupport dao;
	
    @Override
	public TbCardInfo get(Map<String, Object> params) {
		return dao.get(PREFIX + ".get", params);
	}
	
	@Override
	public TbCardInfo findOne(java.lang.String id) {
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
	public int insert(TbCardInfo tbCardInfo) {
		return dao.insert(PREFIX + ".insert", tbCardInfo);
	}

	@Override
	public int update(TbCardInfo tbCardInfo) {
		return dao.update(PREFIX + ".update", tbCardInfo);
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
	public HashMap<String, Object> getByCardNo(String cardNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardno", cardNo);
		return dao.get(PREFIX + ".getByCardNo", params);
	}

	@Override
	public List<Map<String,Object>> findListByParams(Map<String, Object> params) {
		return dao.find(PREFIX+".findListByParams",params);
	}

	@Override
	public int deleteList(Map<String, Object> map) {
		return dao.update(PREFIX + ".deleteList", map);
	}

	@Override
	public List<TbCardInfo> getAllCardInfo(Map<String, Object> map) {
		return dao.find(PREFIX + ".getAllCardInfo",map);
	}

	@Override
	public int findIsExist(String tenantid, String pno) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantid", tenantid);
		params.put("pno", pno);
		return dao.get(PREFIX+".findIsExist", params);
	}

	@Override
	public TbCardInfo getCardInfoByParam(Map<String, Object> map) {
		return dao.get(PREFIX + ".getCardInfoByParam", map);
	}

	@Override
	public TbCardInfo byUserToUse(HashMap<String, Object> paramsJson) {
		return  dao.get(PREFIX + ".byUserToUse", paramsJson);
	}

	@Override
	public HashMap<String, Object> findInfoId(String tENANTID, String cardNo) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantid", tENANTID);
		params.put("pno", cardNo);
		return dao.get(PREFIX + ".findInfoId", params);
	}

	@Override
	public Integer findId(String tENANTID, String cardNo) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantid", tENANTID);
		params.put("pno", cardNo);
		return dao.get(PREFIX + ".findId", params);
	}

    /* (non-Javadoc)    
     * @see com.candao.member.dao.TbCardInfoDao#getActualCard(java.util.Map)    
     */
    @Override
    public TbCardInfo getActualCard(Map<String, Object> params) {
        return dao.get(PREFIX + ".getActualCard", params);
    }

	@Override
	public List<TbCardInfo> getCardInfoByTime(Map<String, Object> param) {
		return dao.find(PREFIX + ".getCardInfoByTime",param);
	}
	
}


