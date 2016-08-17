package com.candao.member.dao;

import java.util.List;

import com.candao.member.model.TMemberCard;

public interface TMemberCardDao {

	final static String PREFIX = TMemberCardDao.class.getName();
	
	TMemberCard getMemberCardByMemberId(int memberId);
	
	TMemberCard getMemberCardByCardNo(String CardNo);
	
	List<TMemberCard> selectMemberCardInMemberIds(String memberIds);
	
	List<TMemberCard> selectMemberCardInCardNos(String cardNos);
}
