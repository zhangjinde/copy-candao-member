package com.candao.member.service.impl.membermanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.MemmberInfoManagerDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberManager;
import com.candao.member.service.membermanage.MemberManageService;

@Service
public class MemberManageServiceImpl implements MemberManageService{

	@Autowired
	private MemmberInfoManagerDao memberManageDao;
	
	@Override
	public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize) {
		// TODO Auto-generated method stub
		Page<Map<String,Object>> page = memberManageDao.page(params, current, pagesize);
		return page;
	}

	@Override
	public Map<String, Object> findCardInfo(Map<String, Object> params) {//查看会员卡状态，会员卡名称
		// TODO Auto-generated method stub
		List<Map<String, Object>> cardLevel = memberManageDao.findCardLevelInfo(params);
		List<Map<String, Object>> cardStatus = memberManageDao.findCardStatusInfo(params);
		List<Map<String, Object>> storesName = memberManageDao.findStoresName(params);
		
		List<Object> result = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> levelZeroMap = new HashMap<>();
		levelZeroMap.put("name", "全部");
		levelZeroMap.put("value", "");
		result.add(levelZeroMap);
		for(int i = 0;i<cardLevel.size();i++){
			Map<String,Object> maptemp = new HashMap<>();
			Map<String,Object> temp = cardLevel.get(i);
			maptemp.put("value", temp.get("level").toString());
			maptemp.put("name", temp.get("discount").toString());
			result.add(maptemp);
		}
		
		if(cardLevel.isEmpty()){
			Map<String,Object> maptemp = new HashMap<>();
			maptemp.put("value", "0");
			maptemp.put("name", "无折扣");
			result.add(maptemp);
		}
		
		map.put("name",result);
		result = new ArrayList<>();
		Map<String,Object> statusZeroMap = new HashMap<>();
		statusZeroMap.put("name", "全部");
		statusZeroMap.put("value", "");
		result.add(statusZeroMap);
		for(int i = 0;i<cardStatus.size();i++){
			Map<String,Object> maptemp = new HashMap<>();
			Map<String,Object> temp = cardStatus.get(i);
			if(temp.get("status").toString().equals("0")){
				continue;
			}
			maptemp.put("value", temp.get("status").toString());
			switch(temp.get("status").toString()){
//				case "0" :
//					maptemp.put("name", "注销");
//					break;
				case "1" :
					maptemp.put("name", "正常");
					break;
				case "2" :
					maptemp.put("name", "挂失");
					break;
				default :
					maptemp.put("name", "-");
			}
			result.add(maptemp);
		}
		map.put("status", result);
		
		result = new ArrayList<>();
		for(int i = 0;i<storesName.size();i++){
			Map<String,Object> maptemp = new HashMap<>();
			Map<String,Object> temp = storesName.get(i);
			
			maptemp.put("value", temp.get("branchid").toString());
			maptemp.put("name", temp.get("tenantname").toString());
			result.add(maptemp);
		}
		map.put("stores", result);
		return map;
	}

	@Override
	public Page<Map<String, Object>> gridInfo(Map<String, Object> param, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		Page<Map<String,Object>> pageInfo = memberManageDao.pageInfo(param, page, rows);
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> getWriteExcel(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return memberManageDao.getWriteExcel(params);
	}
	
	@Override
    public Page<Map<String, Object>> gridTransInfo(Map<String, Object> params, Integer current, Integer pagesize) {
        Page<Map<String,Object>> page = memberManageDao.pageTransInfo(params, current, pagesize);
        return page;
    }

    /* (non-Javadoc)    
     * @see com.candao.member.service.membermanage.MemberManageService#getTransInfoList(java.util.Map)    
     */
    @Override
    public List<Map<String, Object>> getTransInfoList(Map<String, Object> params) {
        List<Map<String,Object>> list = memberManageDao.getTransInfoList(params);
        return list;
    }

    /* (non-Javadoc)    
     * @see com.candao.member.service.membermanage.MemberManageService#getTransInfoDetailList(java.util.Map)    
     */
    @Override
    public List<Map<String, Object>> getTransInfoDetailList(Map<String, Object> params) {
        List<Map<String,Object>> list = memberManageDao.getTransInfoDetailList(params);
        return list;
    }

	@Override
	public Map<String, Object> findMemberByCardNo(Map<String, Object> param) {
		return memberManageDao.findMemberByCardNo(param);
	}

	@Override
	public void updateMemberInfo(TbMemberManager info) {
		memberManageDao.updateMemberInfo(info);
	}

	@Override
	public int findCardNum(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = memberManageDao.findCardNum(param);
		int resultNum = 0;
		if(result != null && !result.isEmpty()){
			resultNum = Integer.valueOf(result.get(0).get("cardnum").toString());
		}
		return resultNum;
	}

	@Override
	public int findMemberNum(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = memberManageDao.findMemberNum(param);
		int resultNum = 0;
		int id = 0;
		if(result != null && !result.isEmpty()){
			resultNum = Integer.valueOf(result.get(0).get("membernum").toString());
			id = Integer.valueOf(result.get(0).get("id").toString());
		}
		if(null != param.get("id") && param.get("id").equals(id+"")){
			resultNum = 0;
		}
		return resultNum;
	}

	@Override
	public Map<String, Object> findMemberByMobile(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = memberManageDao.findMemberByMobile(param);
		List<Map<String, Object>> storesName = memberManageDao.findStoresName(param);
		List<Object> result = new ArrayList<>();
//		Map<String,Object> map = new HashMap<>();
		for(int i = 0;i<storesName.size();i++){
			Map<String,Object> maptemp = new HashMap<>();
			Map<String,Object> temp = storesName.get(i);
			
			maptemp.put("value", temp.get("branchid").toString());
			maptemp.put("name", temp.get("tenantname").toString());
			result.add(maptemp);
		}
		List<Map<String, Object>> cardLevel = memberManageDao.findCardLevelInfo(param);
		for(int i = 0;i<cardLevel.size();i++){
			Map<String,Object> maptemp = new HashMap<>();
			Map<String,Object> temp = cardLevel.get(i);
			maptemp.put("value", temp.get("level").toString());
			maptemp.put("name", temp.get("discount").toString());
			result.add(maptemp);
		}
		if(cardLevel.isEmpty()){
			Map<String,Object> maptemp = new HashMap<>();
			maptemp.put("value", "0");
			maptemp.put("name", "无折扣");
			result.add(maptemp);
		}
		
//		map.put("stores", result);
		if(null != list&&!list.isEmpty()){
			list.get(0).put("stores", result);
			list.get(0).put("cardlevel",result);
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public int findCardNumByCardNo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = memberManageDao.findCardNumByCardNo(param);
		int resultNum = 0;
		if(result != null && !result.isEmpty()){
			resultNum = Integer.valueOf(result.get(0).get("cardnum").toString());
		}
		return resultNum;
	}
}
