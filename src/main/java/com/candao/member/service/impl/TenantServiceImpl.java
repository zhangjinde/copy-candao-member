package com.candao.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.candao.member.dao.TbTenantDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantCard;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.TenantCardService;
import com.candao.member.service.TenantService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class TenantServiceImpl implements TenantService{

	@Autowired
	private TbTenantDao  tenantDao;
	
	@Autowired
	private TenantCardService tenantCardService;
	
	@Override
	public int saveTenantInfo(TbTenantInfo tenantInfo) {
		  return tenantDao.saveTenantInfo(tenantInfo);
	}

	@Override
	public TbTenantInfo verifyTenantByPwd(TbTenantInfo tenantInfo) {
		return tenantDao.verifyTenantByPwd(tenantInfo);
		
	}

	@Override
	public boolean verifySecurityKey(String securityKey) {
		int recordNum = tenantDao.verifySecurityKey(securityKey);
		if(recordNum == 0 ){
			return false;
		}
		return true;
	}
	
	@Override
	public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize) {
		Page<Map<String, Object>> pagemap = tenantDao.page(params, current, pagesize);
		return pagemap;   
	}

	/**
	 * 新增租户门店信息
	 */
	@Override
	@Transactional
	public Map<String, Object> save(TbTenantInfo tbTenantInfo) {
		Map<String, Object> map = new HashMap<>();
		String branchId = tbTenantInfo.getBranchId();
		List<TbTenantInfo> infos = tenantDao.findTenantInfoByBid(branchId,null);
		if(infos.size() > 0){
			map.put("code", "0");
			map.put("msg", "新增失败，门店ID不能重复");
			return map;
		}
		boolean tenantInfoFalg = tenantDao.insert(tbTenantInfo) > 0;
		if(!tenantInfoFalg){
			map.put("code", "0");
			map.put("msg", "新增失败");
			return map;
		}else{
			Map<String, Object> param = new HashMap<>();
			param.put("tenant_id", tbTenantInfo.getTenantId());
			TbTenantCard tenantCard = tenantCardService.findByParams(param);
			if(tenantCard == null){
				TbTenantCard tbTenantCard = new TbTenantCard();
				tbTenantCard.setTenant_id(tbTenantInfo.getTenantId());
				tbTenantCard.setStart_no(0);
				tbTenantCard.setEnd_no(999999);
				tbTenantCard.setCurrent_no(0);
				tbTenantCard.setStatus('1');
				tenantCardService.save(tbTenantCard);
			}
			map.put("code", "1");
			map.put("msg", "新增成功");
		}
		return map;
	}

	/**
	 * 修改租户门店信息
	 */
	@Override
	@Transactional
	public Map<String, Object> update(TbTenantInfo tbTenantInfo) {
		Map<String, Object> map = new HashMap<>();
		String branchId = tbTenantInfo.getBranchId();
		Integer id = tbTenantInfo.getId();
		List<TbTenantInfo> infos = tenantDao.findTenantInfoByBid(branchId,id);
		if(infos.size() > 0){
			map.put("code", "0");
			map.put("msg", "修改失败,门店ID不能重复");
			return map;
		}
		boolean flag = tenantDao.update(tbTenantInfo) > 0;
		if(!flag){
			map.put("code", "0");
			map.put("msg", "修改失败");
		}else{
			Map<String, Object> param = new HashMap<>();
			param.put("tenant_id", tbTenantInfo.getTenantId());
			TbTenantCard tenantCard = tenantCardService.findByParams(param);
			if(tenantCard == null){
				TbTenantCard tbTenantCard = new TbTenantCard();
				tbTenantCard.setTenant_id(tbTenantInfo.getTenantId());
				tbTenantCard.setStart_no(0);
				tbTenantCard.setEnd_no(999999);
				tbTenantCard.setCurrent_no(0);
				tenantCardService.save(tbTenantCard);
			}
			map.put("code", "1");
			map.put("msg", "修改成功");
		}
		return map;
	}

	@Override
	public TbTenantInfo findById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return tenantDao.get(params);
	}
	
	@Override
	public TbTenantInfo findByBranchId(String branchId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchId", branchId);
		return tenantDao.get(params);
	}

	@Override
	public boolean deleteById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return tenantDao.delete(params) > 0;
	}

    /* (non-Javadoc)    
     * @see com.candao.member.service.TenantService#getTenantInfoListByTenantId(com.candao.member.model.TbTenantInfo)    
     */
    @Override
    public List<TbTenantInfo> getTenantInfoListByTenantId(TbTenantInfo tbTenantInfo) {
        HashMap<String, Object> params=new HashMap<>();
        params.put("tenantId", tbTenantInfo.getTenantId());
        List<TbTenantInfo> ti =tenantDao.find(params);
        return ti;
    }
	
}
