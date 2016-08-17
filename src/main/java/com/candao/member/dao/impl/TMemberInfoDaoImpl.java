package com.candao.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TMemberInfoDao;
import com.candao.member.model.TbMemberInfo;

@Repository
public class TMemberInfoDaoImpl implements TMemberInfoDao{
	
	@Autowired
	private DaoSupport dao;


	@Override
	public TbMemberInfo getMemberInfoByMemberId(int memberId) {
		Map<String, String> map = new HashMap<>();
		map.put("memberId", String.valueOf(memberId));
		return dao.get(PREFIX + ".getMemberInfoByMemberId", map);
	}

	@Override
	public List<TbMemberInfo> getAllAvailableMmberByTenantId(String tenantId) {
		Map<String, String> map = new HashMap<>();
		map.put("tenantId", tenantId);
		return dao.find(PREFIX + ".getAllAvailableMmberByTenantId", map);
	}

	@Override
	public List<TbMemberInfo> getAllMmberByTenantId(String tenantId) {
		Map<String, String> map = new HashMap<>();
		map.put("tenantId", tenantId);
		return dao.find(PREFIX + ".getAllMmberByTenantId", map);
	}

	@Override
	public TbMemberInfo getMemberInfoByParam(Map<String, Object> map) {
		return dao.get(PREFIX + ".getMemberInfoByParam", map);
	}

	@Override
	public void addMemberInfo(TbMemberInfo info) {
		dao.insert(PREFIX + ".addMemberInfo", info);
	}

	@Override
	public void updateMemberInfo(TbMemberInfo info) {
		dao.update(PREFIX + ".updateMemberInfo", info);
	}

	@Override
	public void modifyPassword(Map<String, Object> map) {
		dao.update(PREFIX + ".modifyPassword", map);
	}

    /* (non-Javadoc)    
     * @see com.candao.member.dao.TMemberInfoDao#getCardNoByMobile(java.lang.String)    
     */
    @Override
    public List<String> getCardNoByMobile(String mobile,String branchId) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("branchId", branchId);
        return dao.find(PREFIX + ".getCardNoByMobile", map);
    }
}
