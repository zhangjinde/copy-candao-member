package com.candao.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TMemberCardDao;
import com.candao.member.model.TMemberCard;

@Repository
public class TMemberCardDaoImpl implements TMemberCardDao{

	@Autowired
	private DaoSupport dao;

	@Override
	public TMemberCard getMemberCardByMemberId(int memberId) {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("member_id", memberId);
		return dao.get(PREFIX + ".getMemberCardByMemberId", parameter);
	}

	@Override
	public TMemberCard getMemberCardByCardNo(String CardNo) {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("cardNo", CardNo);
		return dao.get(PREFIX + ".getMemberCardByCardNo", parameter);
	}

	@Override
	public List<TMemberCard> selectMemberCardInMemberIds(String memberIds) {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("memberIds", memberIds);
		return dao.find(PREFIX + ".selectMemberCardInMemberIds", parameter);
	}

	@Override
	public List<TMemberCard> selectMemberCardInCardNos(String cardNos) {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("cardNos", cardNos);
		return dao.find(PREFIX + ".selectMemberCardInCardNos", parameter);
	}

}
