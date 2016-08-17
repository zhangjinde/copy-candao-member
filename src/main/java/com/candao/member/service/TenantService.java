package com.candao.member.service;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantInfo;

public interface TenantService {

	/**
	 * 插入租户信息
	 * 通过controller 接收总店下发分店数据来完成分店数据的同步
	 * @param tenantInfo
	 * @return
	 */
	public int saveTenantInfo(TbTenantInfo tenantInfo);
	
	/**
	 * 校验租户的密码,校验完成后生成相应的安全码给调用渠道
	 * 传递参数是tenantId 和 password
	 * @return
	 */
	public TbTenantInfo verifyTenantByPwd(TbTenantInfo tenantInfo);
	
	/**
	 * According the security key ,query record if exist
	 * @return 0 false >0 true
	 */
	public boolean verifySecurityKey(String securityKey) ;
	
	/**
	 * 分页
	 * @param params
	 * @param current
	 * @param pagesize
	 * @return
	 */
	public Page<Map<String, Object>> grid(Map<String, Object>  params, int current, int pagesize);
    
	/**
	 * 保存租户
	 * @param tbTeantInfo
	 * @return
	 */
	public Map<String, Object> save(TbTenantInfo tbTenantInfo);

	/**
	 * 修改租户
	 * @param tbTeantInfo
	 * @return
	 */
	public Map<String, Object> update(TbTenantInfo tbTenantInfo);

	/**
	 * 查询租户
	 * @param id
	 * @return
	 */
	public TbTenantInfo findById(String id);
	
	
	/**
	 * 查询租户
	 * @param branchId
	 * @return
	 */
	public TbTenantInfo findByBranchId(String branchId);

	/**
	 * 删除租户
	 * @param id
	 * @return
	 */
	public boolean deleteById(String id);
	
	/**
	 * 查询租户下所有门店
	 */
	
	public List<TbTenantInfo> getTenantInfoListByTenantId(TbTenantInfo tbTenantInfo);
	
}
