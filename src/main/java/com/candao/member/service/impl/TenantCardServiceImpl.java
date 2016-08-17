package com.candao.member.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TbTenantCardDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantCard;
import com.candao.member.service.TenantCardService;
@Service
public class TenantCardServiceImpl implements TenantCardService {
	
	@Autowired
	private TbTenantCardDao tbTenantCardDao;
	
	@Override
	public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize) {
		return tbTenantCardDao.page(params, current, pagesize);
	}
	
	public List<Map<String,Object>> find(Map<String, Object> params){
		return tbTenantCardDao.find(params);
	}
	
	@Override
	public boolean save(TbTenantCard tbTenantCard) {
		return tbTenantCardDao.insert(tbTenantCard)>0;
	}
	
	@Override
	public TbTenantCard findByParams(Map<String, Object> params) {
		return tbTenantCardDao.get(params);
	}
	
	@Override
	public TbTenantCard findById2(String id) {
		return tbTenantCardDao.findOne(id);
	}
	
	@Override
	public boolean update(TbTenantCard tbTenantCard) {
		return tbTenantCardDao.update(tbTenantCard)>0;
	}
	
	@Override
	public boolean deleteById(Map<String, Object> params) {
		return tbTenantCardDao.delete(params)>0;
	}
	
	public int findMaxID() {
		return tbTenantCardDao.findMaxID();
	}

}

