package com.candao.member.service;
import java.util.List;
import java.util.Map;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantCard;
public interface TenantCardService {
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
  * @param tbTenantCard
  * @return
  */
 public List<Map<String,Object>> find(Map<String, Object> params);

 public boolean save(TbTenantCard tbTenantCard);
 /**
  * 更改数据
  * @param tbTenantCard
  * @return
  */
 public boolean update(TbTenantCard tbTenantCard);
 /**
  * 查询单个数据
  * @param id
  * @return
  */
 public TbTenantCard findByParams(Map<String, Object> params);
 public TbTenantCard findById2(String id);
 /**
  * 删除单个数据
  * @param id
  * @return
  */
 public boolean deleteById(Map<String, Object> params);
 public int findMaxID();
}

