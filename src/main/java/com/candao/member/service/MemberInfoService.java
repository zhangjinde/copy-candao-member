package com.candao.member.service;

import java.util.List;

import com.candao.member.model.TbMemberInfo;

public interface MemberInfoService {

	
	/**
	 * 查询所有该租户下可用的会员
	 * @param tenantId
	 * @return
	 */
	public List<TbMemberInfo> getAllAvailableMmberByTenantId(String tenantId);
	
	/**
	 * 通过cardno得到会员信息
	 * @param cardNo
	 * @return
	 */
	public TbMemberInfo getMemberInfoByCardNo(String cardNo);
	
	/**
	 * 查询该租户下所有会员，不管是否注销
	 * @param tenantId
	 * @return
	 */
	public List<TbMemberInfo> getAllMmberByTenantId(String tenantId);
	
	/**
	 * 获取用户所有正常卡
	 */
	public List<String> getCardNoByMobile(String mobile,String branchId);
}
