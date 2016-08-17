package com.candao.member.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TMemberServiceDAO;
import com.candao.member.model.TMemberService;

@Repository
public class TMemberServiceDAOImpl implements TMemberServiceDAO {
	
	@Autowired
	private DaoSupport dao;

	@Override
	public int insert(TMemberService tMemberService) {
		return dao.insert(PREFIX + ".insert", tMemberService);
	}

}
