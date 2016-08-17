package com.candao.member.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TbMemberManagerDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberAndTenantCard;
import com.candao.member.model.TbMemberManager;
import com.candao.member.service.MemberAndTenantCardService;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.TenantCardService;
@Service
public class MemberAndTenantServiceImpl implements MemberAndTenantCardService {
@Autowired
  private TbMemberManagerDao tbMemberManagerDao;
//	@Override
//	public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize) {
//		return tbMemberManagerDao.page(params, current, pagesize);
//	}
//	public List<Map<String,Object>> find(Map<String, Object> params){
//		return tbMemberManagerDao.find(params);
//	}
	
	/*public List<Map<String,Object>> findAll(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",  id);
		return tbasicDataDao.find( params);
	}*/
	@Override
	public boolean save(TbMemberAndTenantCard tbMemberAndTenantCard) {
		return tbMemberManagerDao.insertMemberCard(tbMemberAndTenantCard)>0;
	}
//	@Override
//	public TbMemberAndTenantCard findByParams(Map<String, Object> params) {
//		return tbMemberManagerDao.get(params);
//	}
//	@Override
//	public TbMemberAndTenantCard findById2(String id) {
//		return tbMemberManagerDao.findOne(id);
//	}
//	@Override
//	public boolean update(TbMemberAndTenantCard tbMemberAndTenantCard) {
//		return tbMemberManagerDao.update(tbMemberAndTenantCard)>0;
//	}
//	@Override
//	public boolean deleteById(Map<String, Object> params) {
//		return tbMemberManagerDao.delete(params)>0;
//	}
//	public int findMaxID() {
//		return tbMemberManagerDao.findMaxID();
//	}

}

