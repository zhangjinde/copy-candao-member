package com.candao.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.candao.member.service.LoginService;
import com.candao.member.utils.HttpUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.PropertiesUtils;


@Service
public class LoginServiceImpl implements LoginService{
	

	@Override
	public boolean login(Map<String, String> map) {
		String result = HttpUtils.doPost(PropertiesUtils.getValue("cloud.host") + "newspicyway/login/ssoLogin.json", map);
		if(result == ""){
			return false;
		}
		result = result.replaceAll("[\\s\\S]*[(]", "");
		result = result.replaceAll("[)][\\s\\S]*", "");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = JacksonJsonMapper.parseJSON2Map(result);
		boolean flag = (boolean)resultMap.get("isSuccess");
		if(!flag){
			return false;
		}
		return true;
	}
}
