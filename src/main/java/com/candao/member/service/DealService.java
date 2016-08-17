package com.candao.member.service;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberInfo;


public interface DealService {
	
	
	/**
	 * 会员卡充值交易
	 * @param valueMap
	 * @return
	 */
	public Map<String, Object> addValue(Map<String,Object> valueMap);
	
	
	
	/**
	 * 会员消费
	 * @param saleMap
	 * @return
	 */
	public Map<String, Object> saveSale(Map<String,Object> saleMap);
	/**
	 * 分页查询数据
	 * @param params
	 * @param current
	 * @param pagesize
	 * @return
	 */
	 public Page<Map<String,Object>> grid(Map<String, Object> params, int current, int pagesize);
	 
	 /**
		 * 取消交易
		 * @param saleMap
		 * @return
		 */
	public Map<String, Object> voidSale(Map<String,Object> saleMap);
	
	/**
	 * 会员储值总额
	 */
	public Map<String, Double> getStoreCount(String tenant_id);

	/**
	 * 会员消费信息
	 * @param map
	 * @param current
	 * @param pagesize
	 * @return
	 */
	public  Map<String, Object> getDealDetail(List<TbMemberInfo> infos,Map<String, Object> map,
			int current,int pagesize);
	
}
