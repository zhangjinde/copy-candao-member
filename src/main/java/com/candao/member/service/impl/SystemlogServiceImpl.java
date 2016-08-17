package com.candao.member.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TSystemLogDAO;
import com.candao.member.model.TSystemLog;
import com.candao.member.service.SystemLogService;

@Service
public class SystemlogServiceImpl implements SystemLogService {
	
	@Autowired
	private TSystemLogDAO tSystemLogDAO ;

	@Override
	public boolean saveSystemLog(Map<String, Object> log) {
		// TODO Auto-generated method stub
		
		TSystemLog tSystemLog =  new TSystemLog();
		tSystemLog.setCardNo(log.get("cardNo").toString());
		tSystemLog.setContent(log.get("content").toString());
		tSystemLog.setSlipNo(log.get("slipNo").toString());
		tSystemLog.setOperator("");
		
		return tSystemLogDAO.add(tSystemLog) > 0 ;
	}

}
