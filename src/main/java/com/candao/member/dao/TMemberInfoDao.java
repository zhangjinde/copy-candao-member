package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.model.TbMemberInfo;

public interface TMemberInfoDao {
	
	final static String PREFIX = TMemberInfoDao.class.getName();
	
	List<TbMemberInfo> getAllAvailableMmberByTenantId(String tenantId);
	
	List<TbMemberInfo> getAllMmberByTenantId(String tenantId);
	
	TbMemberInfo getMemberInfoByMemberId(int memberId);
	
	TbMemberInfo getMemberInfoByParam(Map<String, Object> map);

	void addMemberInfo(TbMemberInfo info);
	
	void updateMemberInfo(TbMemberInfo info);

	void modifyPassword(Map<String, Object> map);
	
	List<String> getCardNoByMobile(String mobile,String branchId);
}
