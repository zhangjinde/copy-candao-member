package com.candao.member.service;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantCard;
import com.candao.member.vo.MemberVo;
import com.candao.member.vo.VipInfoExcelVo;

public interface MemberManagerService {
	/**
	 * 分页查询数据
	 * 
	 * @param params
	 * @param current
	 * @param pagesize
	 * @return
	 */
	public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize);

	/**
	 * 保存数据
	 * 
	 * @param tbMember
	 * @return
	 */
	public List<Map<String, Object>> find(Map<String, Object> params);

	public boolean save(TbMemberManager tbMember);

	public Map<String, Object> save(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo, TbTenantCard tbTenantCard);

	/**
	 * 更改数据
	 * 
	 * @param tbMember
	 * @return
	 */
	public boolean update(TbMemberManager tbMember);

	/**
	 * 查询单个数据
	 * 
	 * @param id
	 * @return
	 */
	public TbMemberManager findByParams(Map<String, Object> params);

	public TbMemberManager findById2(String id);

	/**
	 * 删除单个数据
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteByParams(Map<String, Object> params);

	public int findMaxID();

	public List<Map<String, Object>> findListByParams(Map<String, Object> jsonMap);

	Map<String, Object> deletelistByParams(Map<String, Object> params);

	public void save(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo, TbTenantCard tbTenantCard,
			VipInfoExcelVo excelVo);
	
	public void save(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo, TbTenantCard tbTenantCard,
			MemberVo excelVo,String TENANTID,String cardNo) throws Exception;
	
	public List<TMemberCard> getMemberCards(TbMemberManager tbMemberManager);
	
	public Map<String, Object> insertCard(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo);

	public List<Map<String, Object>> findNocardByMobile(Map<String, Object> params);

}
