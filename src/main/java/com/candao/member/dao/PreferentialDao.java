package com.candao.member.dao;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TPreferential;
import com.candao.member.model.TPreferentialInfo;

public interface PreferentialDao {
	public final static String PREFIX = PreferentialDao.class.getName();
	
	Page<TPreferential> page(Map<String, Object> map,Integer current,Integer pagesize);
	
	List<TPreferential> getPreferential(Map<String, Object> map);
	
	List<TPreferential> getPreferentialByParam(Map<String, Object> map);
	
	List<TPreferential> findPreferentialByParam(Map<String, Object> map);
	
	Integer getCount(Map<String, Object> map);
	
	List<TPreferentialInfo> getInfoByPId(Integer pid);
	
	List<TPreferentialInfo> getInfoByPId(Integer pid,String brancId);
	
	void addPreferential(TPreferential tPreferential);
	 
	int addPreferential(Map<String, Object> map);
	
	void addPreferentialInfo(TPreferentialInfo info);
	
	TPreferential getTPreferentialById(Integer id);
	
	void modifyStatusById(Integer id,Integer status, String updateStatus);
	
	void updatePreferential(TPreferential tPreferential);
	
	void deletePreferentialInfo(Integer pid);
	
	List<TPreferentialInfo> getTPreferentialInfoByPid(Integer pid);
	
	List<TPreferentialInfo> getPreferentialInfoByBranchId(String branchId);
	
	List<TPreferential> getPreferentialByIds(String ids);
	List<TPreferential> getPreferentialInfoToWeixin(Map<String,Object> paramMap);
}
