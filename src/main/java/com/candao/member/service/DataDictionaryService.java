package com.candao.member.service;

import java.util.List;
import java.util.Map;

import com.candao.member.model.TbDataDictionary;
public interface DataDictionaryService {

 /**
  * 保存数据
  * @param tbDataDictionary
  * @return
  */
 public boolean save(TbDataDictionary tbDataDictionary);
 /**
  * 更改数据
  * @param tbDataDictionary
  * @return
  */
 public boolean update(TbDataDictionary tbDataDictionary);
 /**
  * 查询单个数据
  * @param id
  * @return
  */
 public TbDataDictionary findById(String id);
 /**
  * 删除单个数据
  * @param id
  * @return
  */
 public boolean deleteById(String id);
 /**
  * 取得数据字典
  * @return
  */
  public List<Map<String,Object>> getDataDictionaryTag();
  public List<Map<String,Object>> getTypeandTypename();
  /**
   * 获取某个分类下的所有字典数据
   * @param type 分类
   * @return
   */
  public List<Map<String,Object>> getDatasByType(String type);
  
  /**
   * 获取公共密码
   * @author zhao
   * @param string
   */
  public String find(String string);
public boolean delDishTasteService(String dishTasteId);
public List<Map<String, Object>> findByParams(Map<String, Object> map);
}
