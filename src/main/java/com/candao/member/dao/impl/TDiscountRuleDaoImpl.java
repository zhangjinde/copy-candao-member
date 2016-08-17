package com.candao.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TDiscountRuleDao;

@Repository
public class TDiscountRuleDaoImpl implements TDiscountRuleDao{
	
	@Autowired
    private DaoSupport dao;

	@Override
	public <K, V, E> List<E> getdiscountRuleByParam(Map<K, V> param) {
		return dao.find(PREFIX + ".getdiscountRuleByParam", param);
	}

}
