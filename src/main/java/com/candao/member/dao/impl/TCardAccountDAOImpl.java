package com.candao.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TCardAccountDAO;
import com.candao.member.model.TCardAccount;

@Repository
public class TCardAccountDAOImpl implements TCardAccountDAO {
	@Autowired
	private DaoSupport dao;
	
	@Override
    public int update(TCardAccount tCardAccount){
    	return dao.update(PREFIX + ".update", tCardAccount);
    }
	
	public int insert(TCardAccount tCardAccount){
		return dao.insert(PREFIX + ".insert", tCardAccount);
	}
	
	@Override
	public TCardAccount getByCardNo(String cardNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_no", cardNo);
		return dao.get(PREFIX + ".get", params);
	}

	@Override
	public List<TCardAccount> getCardAccountByCardnos(String cardnos) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardnos", cardnos);
		return dao.find(PREFIX + ".getCardAccountByCardnos", map);
	}

	@Override
	public Map<String, Object> getSum(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.findOne(PREFIX + ".getSum", params);
	}

}
