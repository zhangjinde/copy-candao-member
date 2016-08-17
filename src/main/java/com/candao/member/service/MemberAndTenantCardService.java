package com.candao.member.service;
import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberAndTenantCard;
public interface MemberAndTenantCardService {
/**
 * 分页查询数据
 * @param params
 * @param current
 * @param pagesize
 * @return
 */
// public Page<Map<String,Object>> grid(Map<String, Object> params, int current, int pagesize);
// /**
//  * 保存数据
//  * @param tbMemberAndTenantCard
//  * @return
//  */
// public List<Map<String,Object>> find(Map<String, Object> params);

 public boolean save(TbMemberAndTenantCard tbMemberAndTenantCard);
 /**
  * 更改数据
  * @param tbMemberAndTenantCard
  * @return
  */
// public boolean update(TbMemberAndTenantCard tbMemberAndTenantCard);
// /**
//  * 查询单个数据
//  * @param id
//  * @return
//  */
// public TbMemberAndTenantCard findByParams(Map<String, Object> params);
// /**
//  * 删除单个数据
//  * @param id
//  * @return
//  */
// public boolean deleteById(Map<String, Object> params);
}

