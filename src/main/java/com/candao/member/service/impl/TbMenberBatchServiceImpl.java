package com.candao.member.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TbMenberBatchDao;
import com.candao.member.service.TbMenberBatchService;

@Service
public class TbMenberBatchServiceImpl implements TbMenberBatchService {
 
	@Autowired
	 private TbMenberBatchDao tbmenberBatchDao;
	@Override
	public int updateBatchCardNO(Map<String, Object> paramMap) {
		return tbmenberBatchDao.updateBatchCardNO(paramMap);
	}

}
