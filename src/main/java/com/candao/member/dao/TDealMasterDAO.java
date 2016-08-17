package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.model.TDealMaster;

public interface TDealMasterDAO {

	public final static String PREFIX = TDealMasterDAO.class.getName();
	
	public int add(TDealMaster tDealMaster);
	
	public List<TDealMaster> findDealDetailByCardno(Map<String, Object> map);
}
