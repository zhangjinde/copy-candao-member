package com.candao.member.service;

import java.math.BigDecimal;
import java.util.Map;

public interface RuleService {
	
	/**
	 * 根据积分规则和消费现金金额，计算出赠送积分
	 * @param value
	 * @return
	 */
	public BigDecimal getPresentIntegral(BigDecimal value,Map<String, Object> param);
	
	
	/**
	 * 根据储值赠送规则和储值金额，计算出赠送储值
	 * @param value
	 * @return
	 */
	public double getPresentValue(double value);

}
