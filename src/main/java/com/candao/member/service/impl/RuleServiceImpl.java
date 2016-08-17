package com.candao.member.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.RuleService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.PropertiesUtils;

@Service
public class RuleServiceImpl implements RuleService {
	
	
	@Autowired
	private TenantService tenantService;
	
//	储值赠送比例
	private static final int ratio = 10;

	@Override
	public BigDecimal getPresentIntegral(BigDecimal value,Map<String, Object> map) {
		BigDecimal presentIntegral = new BigDecimal(0);    //消费积分
		TbTenantInfo tenantInfo = tenantService.findByBranchId(map.get("branch_id").toString());
		String tenantIds = PropertiesUtils.getValue("not_integral_tenant");
		String[] tIds = tenantIds.split(",");
		boolean falg = false;
		for(String tid : tIds){
			if(tid.equals(tenantInfo.getTenantId())){
				falg = true;
				break;
			}
		}
		if(!falg){
			presentIntegral = value;	//消费一元积一分（五折）
		}
		return presentIntegral;
	}
	
	
	@Override
	public double getPresentValue(double value){
		double presentValue = 0.00;
//		咖啡理不积分，暂时屏蔽
//		if(value > 0){
//			presentValue = value * ratio / 100 ;
//		}
		return presentValue ;
	}

}
