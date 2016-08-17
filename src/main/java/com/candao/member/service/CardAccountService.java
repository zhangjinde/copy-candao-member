package com.candao.member.service;

import java.util.Map;



public interface CardAccountService {
	
	/**
	 * 保存卡账户
	 * @param params
	 * @return
	 */

	
	/**
	 * 查询卡账户的储值余额和积分余额
	 * @param cardNo
	 * @return
	 */
	public Map<String, Object>  getBalanceByCardNo(String cardNo);

	boolean save(String cardNo);
	
}
