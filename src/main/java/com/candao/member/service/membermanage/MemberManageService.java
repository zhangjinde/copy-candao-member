package com.candao.member.service.membermanage;

import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberManager;

public interface MemberManageService {

	/**
	 * 分页查询数据
	 * @param params
	 * @param current
	 * @param pagesize
	 * @return
	 */
	 public Page<Map<String,Object>> grid(Map<String, Object> params, int current, int pagesize);

	 public Map<String,Object> findCardInfo(Map<String, Object> params);

	public Page<Map<String, Object>> gridInfo(Map<String, Object> param, Integer page, Integer rows);

	public List<Map<String, Object>> getWriteExcel(Map<String, Object> params);
	
	public Page<Map<String,Object>> gridTransInfo(Map<String, Object> params, Integer current, Integer pagesize);
	
	public List<Map<String,Object>> getTransInfoList(Map<String, Object> params);
	
	public List<Map<String,Object>> getTransInfoDetailList(Map<String, Object> params);

	public Map<String, Object> findMemberByCardNo(Map<String, Object> param);

	public void updateMemberInfo(TbMemberManager info);

	public int findCardNum(Map<String, Object> param);

	public int findMemberNum(Map<String, Object> param);

	public Map<String, Object> findMemberByMobile(Map<String, Object> param);

	public int findCardNumByCardNo(Map<String, Object> param);

}
