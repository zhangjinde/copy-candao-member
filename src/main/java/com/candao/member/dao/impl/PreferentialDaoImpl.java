package com.candao.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.candao.member.dao.DaoSupport;
import com.candao.member.dao.PreferentialDao;
import com.candao.member.model.TPreferential;
import com.candao.member.model.TPreferentialInfo;

@Repository
public class PreferentialDaoImpl implements PreferentialDao{

	@Autowired
    private DaoSupport dao;
	
	@Override
	public Page<TPreferential> page(Map<String, Object> map,Integer current,Integer pagesize) {
		return dao.page(PREFIX + ".page", map, current, pagesize);
	}
	
	@Override
	public List<TPreferential> getPreferential(Map<String, Object> map) {
		return dao.find(PREFIX + ".getPreferential", map);
	}
	
	@Override
	public Integer getCount(Map<String, Object> map) {
		return dao.get(PREFIX + ".getCount", map);
	}
	
	@Override
    public List<TPreferentialInfo> getInfoByPId(Integer pid) {
        Map<String, Object> map = new HashMap<>();
        map.put("preferentialId", pid);
        return dao.find(PREFIX + ".getInfoByPId", map);
    }
	
	@Override
	public List<TPreferentialInfo> getInfoByPId(Integer pid,String branchId) {
		Map<String, Object> map = new HashMap<>();
		map.put("preferentialId", pid);
		map.put("branchId", branchId);
		return dao.find(PREFIX + ".getInfoByPIdBrancId", map);
	}
	
	@Override
	public TPreferential getTPreferentialById(Integer id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		return dao.get(PREFIX + ".getTPreferentialById", map);
	}

	@Override
	public void modifyStatusById(Integer id,Integer status, String updateStatus) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("status", status);
		dao.update(PREFIX + ".modifyStatusById", map);
		if("del".equals(updateStatus))
			dao.delete(PREFIX + ".modifyTperferenInfo", map);
	}

	@Override
	public void addPreferential(TPreferential tPreferential) {
		dao.insert(PREFIX + ".addPreferential", tPreferential);
	}

	@Override
	public int addPreferential(Map<String, Object> map) {
		return dao.insert(PREFIX + ".insertPreferential", map);
	}
	
	@Override
	public void addPreferentialInfo(TPreferentialInfo info) {
		dao.insert(PREFIX + ".addPreferentialInfo", info);
	}

	@Override
	public void updatePreferential(TPreferential tPreferential) {
		dao.update(PREFIX + ".updatePreferential", tPreferential);
	}

	@Override
	public void deletePreferentialInfo(Integer pid) {
		Map<String, Object> map = new HashMap<>();
		map.put("preferentialId", pid);
		dao.delete(PREFIX + ".deletePreferentialInfo", map);
	}

	@Override
	public List<TPreferential> getPreferentialByParam(Map<String, Object> map) {
		return dao.find(PREFIX + ".getPreferentialByParam", map);
	}

	@Override
	public List<TPreferentialInfo> getTPreferentialInfoByPid(Integer pid) {
		Map<String, Object> map = new HashMap<>();
		map.put("preferentialId", pid);
		return dao.find(PREFIX + ".getTPreferentialInfoByPid",map);
	}

	@Override
	public List<TPreferentialInfo> getPreferentialInfoByBranchId(String branchId) {
		Map<String, Object> map = new HashMap<>();
		map.put("branchId", branchId);
		return dao.find(PREFIX + ".getPreferentialInfoByBranchId",map);
	}

	@Override
	public List<TPreferential> findPreferentialByParam(Map<String, Object> map) {
		return dao.find(PREFIX + ".findPreferentialByParam",map);
	}

	@Override
	public List<TPreferential> getPreferentialByIds(String ids) {
		Map<String, Object> map = new HashMap<>();
		map.put("ids", ids);
		return dao.find(PREFIX + ".getPreferentialByIds",map);
	}

	@Override
	public List<TPreferential> getPreferentialInfoToWeixin(Map<String,Object> paramMap) {
		  List<TPreferential> list = dao.find(PREFIX + ".getPreferentialInfoToWeixin",paramMap);
		return list;
	}

}
