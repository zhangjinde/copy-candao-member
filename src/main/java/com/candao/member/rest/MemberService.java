package com.candao.member.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.DateUtils;
import com.candao.member.utils.JacksonJsonMapper;

@Path("/memberService")
public class MemberService {
	
    @Autowired
    private MemberManagerService memberManagerService;
    @Autowired
    private TenantService tenantService;    
	
    @POST
    @Path("/memberEditService")
    @Produces({MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON})	
	public Response memberEditService(String jsonString) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean result = false;
        HashMap paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        if (paramsJson.get("mobile") != null && !paramsJson.get("mobile").toString().equals("")
                && paramsJson.get("branch_id") != null && !paramsJson.get("branch_id").toString().equals("")) {
            //分店租户信息
            TbTenantInfo tbTenantInfo = tenantService.findByBranchId(paramsJson.get("branch_id").toString());
            if (tbTenantInfo == null) {
                map.put("retcode", 1);
                map.put("retInfo", "没找到门店所属的租户");
                return Response.ok(URLDecoder.decode(JacksonJsonMapper.objectToJson(map),"utf-8")).build();
            }
            Map<String, Object> params2 = new HashMap<String, Object>();
            if (paramsJson.get("new_mobile") != null && !paramsJson.get("new_mobile").toString().equals("")) {
            	params2.put("mobile", paramsJson.get("new_mobile").toString());
            	params2.put("tenant_id", tbTenantInfo.getTenantId());
                List<Map<String, Object>> list = memberManagerService.findNocardByMobile(params2);
                if (list != null && list.size() > 0) {
                	map.put("retcode", 1);
                	map.put("retInfo", "该手机号码已注册");
                    return Response.ok(URLDecoder.decode(JacksonJsonMapper.objectToJson(map),"utf-8")).build();
                }             	
            }            
            
            params2.put("mobile", paramsJson.get("mobile").toString());
            params2.put("tenant_id", tbTenantInfo.getTenantId());
            TbMemberManager findMember = memberManagerService.findByParams(params2);
            if (findMember != null) {
                //手机号是否被使用
                if (paramsJson.get("password") != null && !paramsJson.get("password").toString().equals("")) {
                    findMember.setPassword(paramsJson.get("password").toString());
                }
                if (paramsJson.get("name") != null && !paramsJson.get("name").toString().equals("")) {
                    findMember.setName(paramsJson.get("name").toString());
                }
                if (paramsJson.get("gender") != null && !paramsJson.get("gender").toString().equals("")) {
                    if ("1".equals(paramsJson.get("gender").toString())) {
                        findMember.setGender('1');
                    } else if ("0".equals(paramsJson.get("gender").toString())) {
                        findMember.setGender('0');
                    }
                }
                if (paramsJson.get("new_mobile") != null && !paramsJson.get("new_mobile").toString().equals("")) {
                    findMember.setNew_mobile(paramsJson.get("new_mobile").toString());
                }
                if (paramsJson.get("birthday") != null && !paramsJson.get("birthday").toString().equals("")) {
                    findMember.setBirthday(DateUtils.parse(paramsJson.get("birthday").toString()));
                }
                if (paramsJson.get("member_address") != null
                        && !paramsJson.get("member_address").toString().equals("")) {
                    findMember.setMember_address(paramsJson.get("member_address").toString());
                }
                if (paramsJson.get("member_avatar") != null && !paramsJson.get("member_avatar").toString().equals("")) {
                    findMember.setMember_avatar(paramsJson.get("member_avatar").toString());
                }
                result = memberManagerService.update(findMember);
            }
        }
        if (result) {
            map.put("retcode", 0);
            map.put("retInfo", "OK");
        } else {
            map.put("retcode", 1);
            map.put("retInfo", "修改会员失败");
        }
        
        return Response.ok(URLDecoder.decode(JacksonJsonMapper.objectToJson(map),"utf-8")).build();		
	}
}
