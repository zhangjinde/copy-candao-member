package com.candao.member.dao;

import com.candao.member.model.TSystemLog;

public interface TSystemLogDAO {
	public final static String PREFIX = TSystemLogDAO.class.getName();
	
	public int add(TSystemLog tSystemLog);
}
