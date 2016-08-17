package com.candao.member.dao;

import com.candao.member.model.TMemberDeal;

public interface TMemberDealDAO {
	
	public final static String PREFIX = TMemberDealDAO.class.getName();
	
	public int add(TMemberDeal tMemberDeal);

}
