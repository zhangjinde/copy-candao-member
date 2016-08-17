package com.candao.member.controller.membermanage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.TenantService;
import com.candao.member.service.membermanage.MemberManageService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.DateUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.MemberSwtichCardNOUtil;
import com.candao.member.utils.SessionUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/memberbase/memberinfo")
public class MemberInfoController {
	@Autowired
	private MemberManageService memberManageService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private MemberManagerService memberManagerService;
	@RequestMapping("/memberbaseinfo")
	@ResponseBody
	public Object memberInfo(String telepno,String cardno,String name,String cardname,String cardstat,String point,
			@RequestParam(value = "page", defaultValue = "1") Integer page, String birstarttime,
			String birendtime, String pointflag,Integer  balance,String balanceflag,Integer totalbalance,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,String totalbalanceflag) { //会员消费信息
		Map<String, Object> param = new HashMap<>();
		String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		
		param.put("birstarttime", null);
		param.put("birendtime", null);
		param.put("birnorflag", -1);
		if(birstarttime != null && birstarttime != ""){
			int birnorflag = birstarttime.substring(0, 4).equals(birendtime.substring(0, 4))?1:0;//在同一年中用ture。or 用 flase
			param.put("birnorflag", birnorflag);//生日判断是否为同一年
			param.put("birstarttime", birstarttime.substring(5));//保留月份与日期
			param.put("birendtime", birendtime.substring(5));
		}
		
//		if(number != null && isTelephNumber(number)){
			param.put("telepno",telepno);//电话号码
//		}else if(number != null){
		/*if(cardno != null && cardno != ""){
			String s = "00000000000000000000000000000000000000000000";
			s = s.substring(0, 30-(tenantId+cardno).length());
			param.put("cardno", s+tenantId+cardno);//会员卡号
		}*/
		if(cardno != null && cardno != ""){
			param.put("cardno",tenantId+cardno);
		}
//		}
		
		param.put("tenant_id", tenantId);
		param.put("pointflag", pointflag);
		
		param.put("name", name);
		param.put("cardlevel", cardname);//卡名称
		param.put("cardstat", cardstat);//卡状态
		param.put("point", point);//积分
		param.put("pointflag", pointflag);//积分大于小于等于类型
		param.put("balance", balance);//余额
		param.put("balanceflag", balanceflag);//大于小于等于类型
		param.put("totalbalance", totalbalance);//累计余额
		param.put("totalbalanceflag", totalbalanceflag);//大于小于等于类型

		Page<Map<String, Object>> resultPage = memberManageService.gridInfo(param, page, rows);
		Collection<Map<String, Object>> result = resultPage.getRows();
		Map<String,Object> resultMap = new HashMap<>();
		
		int tenantIdLenth = tenantId.length();
		
		for(Map<String,Object> temp : result){
			String cardnoTemp =  temp.get("cardno").toString();
			String cardclass = null != temp.get("cardclass") ?temp.get("cardclass").toString():"0";
			temp.put("cardno_temp", cardnoTemp);
			switch(temp.get("gender").toString()){
				case "0" :
					temp.put("gender", "男");
					break;
				case "1" :
					temp.put("gender", "女");
					break;
			}
			if(cardnoTemp.contains("CD")||cardclass.equals("0")){
				temp.put("cardno","-");
				continue;
			}
			String branchId=temp.get("branch_id")!=null?temp.get("branch_id").toString():"";
			temp.put("cardno", MemberSwtichCardNOUtil.getCardNo(tenantId, branchId, cardnoTemp));
			/*	case "2" :
					temp.put("cardstatus", "挂失");
					break;
				default :
					temp.put("cardstatus", "-");
			}*/
		}
		resultMap.put("memberInfoList", result);
		resultMap.put("page", page);
		resultMap.put("records", resultPage.getTotal());//总记录数
		resultMap.put("total", resultPage.getPageCount());//总页数
		String jsonObject = JacksonJsonMapper.objectToJson(resultMap);//JSONObject.fromObject(resultMap);
		return jsonObject;
	}
	
	@RequestMapping(value="/findMemberByCardNo")
	@ResponseBody
	public Map<String,Object> findMemberByCardNo(String cardno,String branchid){
		Map<String, Object> map=new HashMap<String,Object>();
		try{
			if(cardno!=null && !"".equals(cardno) && branchid!=null && !"".equals(branchid)){
				//分店租户信息
				TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branchid);
				if(tbTenantInfo == null){
			    	map.put("Retcode", 1);
				    map.put("RetInfo", "没找到门店所属的租户");
				    return map;
				}
				String tenantId = tbTenantInfo.getTenantId();
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("cardno", cardno);
				param.put("tenant_id", tbTenantInfo.getTenantId());
				Map<String,Object> temp=memberManageService.findMemberByCardNo(param);
				if(temp!=null&&!temp.isEmpty()){
					map.put("Retcode", 0);
					map.put("RetInfo","");
					map.put("cardno_temp", temp.get("cardno"));
					int card_type=(Integer)temp.get("card_type");
					if(card_type==0){
						temp.put("cardno", "-");
					}else{
						temp.put("cardno", MemberSwtichCardNOUtil.getCardNo(tenantId, temp.get("branch_id").toString(), temp.get("cardno").toString()));
					}
					map.putAll(temp);
				}else{
					map.put("Retcode", 1);
					map.put("RetInfo","没有对应的会员信息");
				}
			}else{
				map.put("Retcode", 1);
				map.put("RetInfo","卡号和门店号不能为空");
			}
		}catch(Exception e){
			//e.printStackTrace();
			map.put("Retcode", 1);
			map.put("RetInfo","获取会员信息失败");
		}
		return map;
	}
	
	@RequestMapping(value="/updateMemberInfo")
	@ResponseBody
	public Map<String,Object> updateMemberInfo(Integer id,String name,String mobile,
			char gender,String birthday,String tenant_id,String member_address){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			TbMemberManager info=new TbMemberManager();
			info.setId(id);
			info.setName(name);
			info.setMobile(mobile);
			info.setGender(gender);
			info.setBirthday(DateUtils.parse(birthday, "yyyy-MM-dd"));
			info.setTenant_id(tenant_id);
			info.setMember_address(member_address);
			//验证手机号是否重复
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("mobile", info.getMobile());
			param.put("tenant_id", info.getTenant_id());
			TbMemberManager findMember = memberManagerService.findByParams(param);
			if(findMember==null || (findMember.getId().equals(info.getId()))){
				memberManageService.updateMemberInfo(info);
				map.put("Retcode", 0);
				map.put("RetInfo","");
			}else{
				map.put("Retcode", 1);
				map.put("RetInfo","该手机号码已存在.");
			}
		}catch(Exception e){
			//e.printStackTrace();
			map.put("Retcode", 1);
			map.put("RetInfo","修改会员信息失败");
		}
		return map;
	}
}
