package com.candao.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TCardAccountDAO;
import com.candao.member.model.TCardAccount;
import com.candao.member.service.CardAccountService;

@Service
public class CardAccountServiceImpl implements CardAccountService {
	
	@Autowired
	private TCardAccountDAO tCardAccountDAO ;

	@Override
	public boolean save(String cardNo) {
		
		TCardAccount tCardAccount = new TCardAccount();
		//tCardAccount.setCard_id();
		tCardAccount.setCard_no(cardNo);
		tCardAccount.setValue(0);
		tCardAccount.setActual_value(0);
		tCardAccount.setPresent_value(0);
		tCardAccount.setPoint(0);
		tCardAccount.setPresent_point(0);
        
		return tCardAccountDAO.insert(tCardAccount) > 0;

	}

	/**
	 * 查询当前会员卡的总余额和总积分
	 */
	@Override
	public Map<String, Object> getBalanceByCardNo(String cardNo) {
		HashMap<String,Object> balance = null;
		TCardAccount tCardAccount = null ;
		tCardAccount = tCardAccountDAO.getByCardNo(cardNo);
		if(tCardAccount != null){
			balance = new HashMap<String,Object>();
			balance.put("value", tCardAccount.getValue());
			balance.put("point", tCardAccount.getPoint());
		}
		return balance;
	}

}
