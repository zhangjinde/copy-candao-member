package com.candao.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TDealMasterDAO;
import com.candao.member.model.TDealMaster;

@Repository
public class TDealMasterDAOImpl implements TDealMasterDAO {
	
	@Autowired
	private DaoSupport dao;

	@Override
	public int add(TDealMaster tDealMaster) {
		// TODO Auto-generated method stub
		return dao.insert(PREFIX + ".insert", tDealMaster);
	}

	@Override
	public List<TDealMaster> findDealDetailByCardno(Map<String, Object> map) {
		return dao.find(PREFIX + ".findDealDetailByCardno" , map);
	}

}
