package com.candao.member.dao;

import java.util.Map;

/**
 * 
 * @author Candao
 *餐道会员系统相关批处理操作
 */
public interface TbMenberBatchDao {
	public final static String PREFIX = TbMenberBatchDao.class.getName();
	
	public int updateBatchCardNO(Map<String, Object> paramMap);
}
