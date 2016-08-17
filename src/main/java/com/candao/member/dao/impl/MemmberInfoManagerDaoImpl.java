package com.candao.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.MemmberInfoManagerDao;
import com.candao.member.model.TbMemberManager;

@Repository
public class MemmberInfoManagerDaoImpl implements MemmberInfoManagerDao {

    @Autowired
    private DaoSupport dao;

    @Override
    public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize) {
        // TODO Auto-generated method stub
        return dao.page(PREFIX + ".page", params, current, pagesize);
    }

    @Override
    public void getStoreDealDetail() {
        // TODO Auto-generated method stub
        dao.find(PREFIX + ".getStoreDealDetail");
    }

    @Override
    public List<Map<String, Object>> findCardLevelInfo(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return dao.find(PREFIX + ".findCardLevelInfo", params);
    }

    @Override
    public List<Map<String, Object>> findCardStatusInfo(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return dao.find(PREFIX + ".findCardStatusInfo", params);
    }

    @Override
    public Page<Map<String, Object>> pageInfo(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
        return dao.page(PREFIX + ".pagememberinfo", param, page, rows);
    }

    @Override
    public List<Map<String, Object>> getWriteExcel(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return dao.find(PREFIX + ".getWriteExcel", params);
    }

    @Override
    public <E, K, V> Page<E> pageTransInfo(Map<K, V> params, Integer current, Integer pagesize) {
        return dao.page(PREFIX + ".pageTransInfo", params, current, pagesize);
    }

    /* (non-Javadoc)    
     * @see com.candao.member.dao.MemmberInfoManagerDao#getTransInfoList(java.util.Map)    
     */
    @Override
    public List<Map<String, Object>> getTransInfoList(Map<String, Object> params) {
        return dao.find(PREFIX + ".pageTransInfo", params);
    }

    /* (non-Javadoc)    
     * @see com.candao.member.dao.MemmberInfoManagerDao#getTransInfoDetailList(java.util.Map)    
     */
    @Override
    public List<Map<String, Object>> getTransInfoDetailList(Map<String, Object> params) {
        return dao.find(PREFIX + ".TransInfoDetail", params);
    }

	@Override
	public List<Map<String, Object>> findStoresName(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.find(PREFIX + ".findStoresName", params);
	}

	@Override
	public Map<String, Object> findMemberByCardNo(Map<String, Object> param) {
		return dao.get(PREFIX+".findMemberByCardNo",param);
	}

	@Override
	public void updateMemberInfo(TbMemberManager info) {
		dao.update(PREFIX+".updateMemberInfo", info);
	}

	@Override
	public List<Map<String, Object>> findCardNum(Map<String, Object> param) {
		// TODO Auto-generated method stub
		
		return dao.find(PREFIX+".findCardNum", param);
	}

	@Override
	public List<Map<String, Object>> findMemberNum(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dao.find(PREFIX+".findMemberNum", param);
	}

	@Override
	public List<Map<String, Object>> findMemberByMobile(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dao.find(PREFIX+".findMemberByMobile", param);
	}

	@Override
	public List<Map<String, Object>> findCardNumByCardNo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dao.find(PREFIX+".findCardNumByCardNo", param);
	}

	@Override
	public <E, K, V> Page<E> getReportPage(Map<K, V> params, Integer current, Integer pagesize) {
		return dao.page(PREFIX + ".getReportPage", params, current, pagesize);
	}

	@Override
	public List<Map<String, Object>> getReportsList(Map<String, Object> param) {
		return dao.find(PREFIX + ".getReportPage", param);
	}
}
