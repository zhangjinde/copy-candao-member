package com.candao.member.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.TenantService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.DateUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.PropertiesUtils;


/**
 * 所有对外的服务控制类
 * @author tom-pc
 *
 */
@Controller
@RequestMapping("/externalservice")
public class ExternalServiceController {
	
	@Autowired
	TenantService  tenantService;
	
  

	/**
	 * 提供总店创建分店后的分店信息录入
	 */
	
	@RequestMapping("/savebizinfo")
	@ResponseBody
	public String savebizinfo(@RequestParam Map<String, Object> params){
		 
		if(params == null){
			return Constant.FAILUREMSG;
		}
		Map<String, Object> map = null;
		
		TbTenantInfo tenantInfo = new TbTenantInfo();
		tenantInfo.setAddress((String)params.get("address"));
		tenantInfo.setBizLicence((String)params.get("bizLicence"));
		tenantInfo.setChannelType(params.get("channelType") == null ?0:(int)params.get("channelType"));
		tenantInfo.setExpireDate(params.get("expireDate") == null?new Date():(DateUtils.parse((String)params.get("expireDate"))));
		tenantInfo.setRegisteDate(params.get("registeDate") == null?new Date():(DateUtils.parse((String)params.get("registeDate"))));
		tenantInfo.setSecurityKey(UUID.randomUUID().toString().replaceAll("-",""));
		tenantInfo.setTenantName((String)params.get("tenantName"));
		tenantInfo.setTenantId((String)params.get("tenantId") );
		tenantInfo.setTenantStatus(0);
		tenantInfo.setTenantTel((String)params.get("tenantTel"));
		tenantInfo.setPassword(PropertiesUtils.getValue("defaultPwd"));
		
		tenantService.saveTenantInfo(tenantInfo);
		return JacksonJsonMapper.objectToJson(map);
	}
	
	/**
	 * 提供安全校验
	 * 分店id + 分店默认的密码
	 * 
	 * 返回安全码给调用端
	 */
	
	

	@RequestMapping("/getsecuritykey")
	@ResponseBody
	public String getSecurityKey(@RequestParam Map<String, Object> params){
		
		Map<String,String> retMap = new HashMap<String, String>();
		
		if(params == null){
			retMap.put("result", "1");
			return JacksonJsonMapper.objectToJson(retMap);
		}
		TbTenantInfo tenantInfo = new TbTenantInfo();
		tenantInfo.setPassword((String)params.get("password"));
	    tenantInfo.setTenantId((String)params.get("tenantId"));
	    tenantInfo = tenantService.verifyTenantByPwd(tenantInfo);
	    if(tenantInfo == null){
	    	retMap.put("result", "1");
			return JacksonJsonMapper.objectToJson(retMap);
	    }else{
	    	retMap.put("result", "0");
	    	retMap.put("securityKey", tenantInfo.getSecurityKey());
			return JacksonJsonMapper.objectToJson(retMap);
	    }
	}
 
	 
}
