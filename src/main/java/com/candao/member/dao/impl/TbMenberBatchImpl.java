package com.candao.member.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.TbMenberBatchDao;

/**
 * 
 * @author Candao
 *业务层实现类
 */
@Repository
public class TbMenberBatchImpl implements TbMenberBatchDao {
	 @Autowired
	 private DaoSupport dao;
	@Override
	public int updateBatchCardNO(Map<String, Object> paramMap) {
         
		return dao.update(PREFIX+".batchCardNO", paramMap);
	}

}
