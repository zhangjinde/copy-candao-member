package com.candao.member.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TSystemLogDAO;
import com.candao.member.model.TSystemLog;

@Repository
public class TSytemLogDAOImpl implements TSystemLogDAO {
	
	@Autowired
	private DaoSupport dao;

	@Override
	public int add(TSystemLog tSystemLog) {
		// TODO Auto-generated method stub
		return dao.insert(PREFIX + ".insert", tSystemLog);
	}

}
