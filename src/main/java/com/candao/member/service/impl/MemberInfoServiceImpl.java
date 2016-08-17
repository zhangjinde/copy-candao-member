package com.candao.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TMemberCardDao;
import com.candao.member.dao.TMemberInfoDao;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.service.MemberInfoService;

@Service
public class MemberInfoServiceImpl implements MemberInfoService{

	@Autowired
	private TMemberInfoDao tMemberInfoDao;
	
	@Autowired
	private TMemberCardDao tMemberCardDao;

	@Override
	public TbMemberInfo getMemberInfoByCardNo(String cardNo) {
		TMemberCard card = tMemberCardDao.getMemberCardByCardNo(cardNo);
		Integer memberId = card.getMember_id();
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByMemberId(memberId);
		return memberInfo;
	}

	@Override
	public List<TbMemberInfo> getAllAvailableMmberByTenantId(String tenantId) {
		return tMemberInfoDao.getAllAvailableMmberByTenantId(tenantId);
	}

	@Override
	public List<TbMemberInfo> getAllMmberByTenantId(String tenantId) {
		return tMemberInfoDao.getAllMmberByTenantId(tenantId);
	}

    /* (non-Javadoc)    
     * @see com.candao.member.service.MemberInfoService#getCardNoByMobile(java.lang.String)    
     */
    @Override
    public List<String> getCardNoByMobile(String mobile,String branchId) {
        return tMemberInfoDao.getCardNoByMobile(mobile,branchId);
    }
	
}

