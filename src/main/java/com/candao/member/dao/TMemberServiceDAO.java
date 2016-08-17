package com.candao.member.dao;

import com.candao.member.model.TMemberService;

public interface TMemberServiceDAO {
	
	public final static String PREFIX = TMemberServiceDAO.class.getName();
	
	public int insert(TMemberService tMemberService);

}
