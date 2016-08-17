package com.candao.member.controller.membermanage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.dao.impl.Page;
import com.candao.member.service.DealService;
import com.candao.member.service.membermanage.MemberManageService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.MemberSwtichCardNOUtil;
import com.candao.member.utils.SessionUtils;

import net.sf.json.JSONObject;

/**
 * 会员消费信息change 
 * 
 * @author 001
 */
@Controller
@RequestMapping("/memberbase/membermanage")
public class MemberDealInfoController {

	@Autowired
	private MemberManageService memberManageService;
	
	@Autowired
	private DealService dealService;
	
	@RequestMapping("/memberdealinfo")
	@ResponseBody
	public Object memberInfo(String telepno,String cardno,String name,String cardname,String cardstat,String point,
			@RequestParam(value = "page", defaultValue = "1") Integer page, String birstarttime,
			String birendtime, String pointflag,Integer  balance,String balanceflag,Integer totalbalance,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,String totalbalanceflag) { //会员消费信息
		Map<String, Object> param = new HashMap<>();
		
		String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		
		param.put("birstarttime", null);
		param.put("birendtime", null);
		
		if(birstarttime != null && birstarttime != ""){
			int birnorflag = birstarttime.substring(0, 4).equals(birendtime.substring(0, 4))?1:0;//在同一年中用ture。or 用 flase
			param.put("birnorflag", birnorflag);//生日判断是否为同一年
			param.put("birstarttime", birstarttime.substring(5));//保留月份与日期
			param.put("birendtime", birendtime.substring(5));
		}
		
//		if(number != null && isTelephNumber(number)){
			param.put("telepno",telepno);//电话号码
//		}else if(number != null){
		if(cardno != null && cardno != ""){
//			String s = "00000000000000000000000000000000000000000000";
//			s = s.substring(0, 30-(tenantId+cardno).length());
//			param.put("cardno", s+tenantId+cardno);//会员卡号
			param.put("cardno", tenantId+cardno);//会员卡号
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
		
		Page<Map<String, Object>> resultPage = memberManageService.grid(param, page, rows);
		Collection<Map<String, Object>> result = resultPage.getRows();
		Map<String,Object> resultMap = new HashMap<>();
		
		int tenantIdLenth = tenantId.length();
		
		for(Map<String,Object> temp : result){//截取添加的多余的数字
			String cardnoTemp =  temp.get("cardno").toString();
			String cardclass = null != temp.get("cardclass") ?temp.get("cardclass").toString():"0";
			temp.put("membercard", cardnoTemp);//新增完整会员卡号
			if(cardnoTemp.contains("CD")||cardclass.equals("0")){//虚拟卡
				temp.put("cardno","-");
				continue;
			}
			temp.put("cardno", cardnoTemp.length()==30?cardnoTemp.substring(cardnoTemp.indexOf(tenantId)+tenantIdLenth):cardnoTemp);
			/*switch(temp.get("cardstatus").toString()){
				case "0" :
					temp.put("cardstatus", "注销");
					break;
				case "1" :
					temp.put("cardstatus", "正常");
					break;
				case "2" :
					temp.put("cardstatus", "挂失");
					break;
				default :
					temp.put("cardstatus", "-");
			}*/
		}
		resultMap.put("dealInfoList", result);
		resultMap.put("page", page);
		resultMap.put("records", resultPage.getTotal());//总记录数
		resultMap.put("total", resultPage.getPageCount());//总页数
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject;
	}

	/*private boolean isTelephNumber(String number) {
		// TODO Auto-generated method stub 校验电话号码
		 Pattern p = null;  
	     p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
	     return p.matcher(number).matches();  
	}*/
	
	/**
	 * 根据会员卡号查询消费记录
	 * @param cardno
	 * @param current
	 * @return
	 */
	@RequestMapping("/findDealDetailByCardno")
	@ResponseBody
	public String findDealDetailByCardno(String cardno,@RequestParam(value = "page", defaultValue = "1") Integer page){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		if(cardno != null && cardno != ""){
//			String s = "00000000000000000000000000000000000000000000";
//			s = s.substring(0, 30-(tenantId+cardno).length());
//			map.put("card_no", s+tenantId+cardno);//会员卡号
			map.put("card_no",cardno);
		}
//		map.put("card_no", cardno);
		Page<Map<String, Object>> pagemap = dealService.grid(map, page, 10);
		resultMap.put("records", pagemap.getTotal());
		resultMap.put("total", pagemap.getPageCount());
		resultMap.put("page", page);
		resultMap.put("detailDatas", pagemap.getRows());
		resultMap.put("cardno", cardno);
		return JacksonJsonMapper.objectToJson(resultMap);
	}
	
	/**
	 * 高级查询反显会员卡名称，与会员卡状态
	 * 
	 */
	@RequestMapping("/findCardInfo")
	@ResponseBody
	public Object findCardInfo(String branchId,String mobile){
		Map<String, Object> param = new HashMap<>();
		Map<String,Object> result = new HashMap<>();
		
		param.put("tenant_id", SessionUtils.get(Constant.CURRENT_TENANT_ID).toString());
		param.put("branchId",branchId);
		result = memberManageService.findCardInfo(param);
		
		return JSONObject.fromObject(result);
	}
	
	/**
	 * 高级查询反显会员卡名称，与会员卡状态 change to 根据电话号码判断会员是否存在
	 * 
	 */
	@RequestMapping("/newcardmemberinfo")
	@ResponseBody
	public Object newfindMemberCard(String mobile,String id,String tenantId){
		Map<String, Object> param = new HashMap<>();
		Map<String,Object> result = new HashMap<>();
		param.put("mobile", mobile);
		param.put("id", id);
		tenantId = null != tenantId ? tenantId : SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		param.put("tenantId", tenantId);
		int membernum = memberManageService.findMemberNum(param);
		result.put("membernum", membernum);
		return JSONObject.fromObject(result);
	}
	
	//绑定会员实体卡时，查询电话号码失去焦点返显
	@RequestMapping("/newmemberinfo")
	@ResponseBody
	public Object newmemberinfo(String mobile,String branchId){
		Map<String, Object> param = new HashMap<>();
		Map<String,Object> result = new HashMap<>();
		
		param.put("mobile", mobile);
		param.put("tenant_id", SessionUtils.get(Constant.CURRENT_TENANT_ID).toString());
		
		result = memberManageService.findMemberByMobile(param);
		if(result == null){
			result = new HashMap<>();
			result.put("resultData", "0");//返回查询手机号码无对应信息时，
			return JSONObject.fromObject(result);
		}
		int cardnum = memberManageService.findCardNum(param);
		result.put("cardnum", cardnum);
		
		return JSONObject.fromObject(result);
	}
	
	//绑定卡时，输入会员卡时失去焦点 返回是否存在该会员卡
	@RequestMapping("/findMemberCard")
	@ResponseBody
	public Object findMemberCard(String cardno,String branchId){
		Map<String, Object> param = new HashMap<>();
		Map<String,Object> result = new HashMap<>();
		cardno = MemberSwtichCardNOUtil.getInStance().createCardNo(branchId+SessionUtils.get(Constant.CURRENT_TENANT_ID).toString(),
				cardno);
		param.put("cardno", cardno);
		int cardnum = memberManageService.findCardNumByCardNo(param);
		
		result.put("cardnum", cardnum);//默认无会员信息。
		return JSONObject.fromObject(result);
	}
}
