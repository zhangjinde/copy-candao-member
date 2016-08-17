package com.candao.member.service;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TPreferential;
import com.candao.member.model.TPreferentialInfo;

public interface PreferentialService {

	Page<TPreferential> getPreferential(Map<String, Object> parmMap);
	Map<String, Object> addPreferential(TPreferential tPreferential);
	Map<String, Object> updatePreferential(TPreferential tPreferential) throws Exception;
	
	Map<String, Object> getPreferentialDetail(String id);
	
	void modifyStatusById(Integer id,Integer status, String updateStatus);

	List<Map<String, Object>> getBranchByTenantId(String tenantId,Integer pid);
	List<TPreferential> getPreferentialInfoToWeixin(Map<String,Object> paramMap);
	List<TPreferentialInfo> getTPreferentialInfoByPid(Integer pid);
}
