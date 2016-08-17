package com.candao.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TDealDetail;
import com.candao.member.model.TbCardInfo;



public interface CardInfoService {
	/**
	 * 分页查询数据
	 * @param params
	 * @param current
	 * @param pagesize
	 * @return
	 */
	 public Page<Map<String,Object>> grid(Map<String, Object> params, int current, int pagesize);
	 /**
	  * 保存数据
	  * @param tbCardInfo
	  * @return
	  */
	 public List<Map<String,Object>> find(Map<String, Object> params);

	 public boolean save(TbCardInfo tbCardInfo);
	 /**
	  * 更改数据
	  * @param tbCardInfo
	  * @return
	  */
	 public boolean update(TbCardInfo tbCardInfo);
	 /**
	  * 查询单个数据
	  * @param id
	  * @return
	  */
	 public TbCardInfo findByParams(Map<String, Object> params);
	 
	 public TbCardInfo findActualByParams(Map<String, Object> params);
	 
	 public TbCardInfo findById2(String id);
	 /**
	  * 删除单个数据
	  * @param id
	  * @return
	  */
	 public boolean deleteByParams(Map<String, Object> params) ;
	 public int findMaxID();
	 
	 public String getCardStatusByCardNo(String CardNo);
	 
	  /**
      * 会员卡挂失
      * @param params
      * @return
      */
	 public Map<String, Object> updateCardLose(Map<String, Object> params);
	 
	 /**
	  * 会员卡解挂
	  * @param params
	  * @return
	  */
	 public boolean updateUnCardLose(Map<String, Object> params);

	 /**
	  * 通过cardno查询消费记录
	  * @param cardno
	  * @return
	  */
	 public List<TDealDetail> findDealDetailByCardno(String cardno,int current);
	 
	 /**
	  * 查询该租户下所有会员的余额
	  * @return
	  */
	 public Map<String, Object> getAllMemberBalance();
	 
	 public int findIsExist(String tenantid, String pno);
	 /**
	  * 查询实体卡是否已经绑定
	  * @param paramsJson
	  */
	public TbCardInfo byUserTouse(HashMap<String,Object> paramsJson);
	 
}

