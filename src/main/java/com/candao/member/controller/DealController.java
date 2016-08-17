package com.candao.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.CardInfoService;
import com.candao.member.service.DealService;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.SystemLogService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.MemberSwtichCardNOUtil;




@Controller("deal")
@RequestMapping("/deal")
public class DealController {
		
	@Autowired
	private DealService dealService ;
	
	@Autowired
	private CardInfoService cardInfoService ;
	
	@Autowired
	private SystemLogService systemLogService ;
	
	@Autowired
	private MemberManagerService memberManagerService;
	@Autowired
	private TenantService tenantService;
	
	/**
	 * 会员储值
	 * @param params
	 * @return
	 */
	@RequestMapping("/StoreCardDeposit")
	@ResponseBody
	public String storeCardDeposit(@RequestBody String jsonString,HttpServletRequest request){
		String cardNo = null ;
		String cardStatus = null ;
		Map<String, Object> map = null;
		HashMap<String, Object> params = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
		if(params.get("cardno") != null && !params.get("cardno").toString().equals("")){
			cardNo = params.get("cardno").toString();
			
			cardStatus = cardInfoService.getCardStatusByCardNo(cardNo);
			//卡状态正常状态
			if( cardStatus != null && "1".equalsIgnoreCase(cardStatus) ){
				try{
					map = dealService.addValue(params);
				}catch(Exception e){
					HashMap<String , Object> log = new HashMap<String , Object>();
					log.put("cardNo", cardNo);
					log.put("slipNo", params.get("Serial").toString());
					log.put("content", "content: " + e.getMessage());
					systemLogService.saveSystemLog(log);
				}
				if(map != null){
				    map.put("Retcode", 0);
					map.put("RetInfo", "OK");
			    }else{
				    map = new HashMap<String , Object>();
				    map.put("Retcode", 1);
					map.put("RetInfo", "充值失败");
				}
			}else{
			    map = new HashMap<String , Object>();
			    map.put("Retcode", 1);
			    map.put("RetInfo", "会员卡非正常状态，充值失败");
		    }
		}
		return JacksonJsonMapper.objectToJson(map);
	}
	
	private String queryCardno(String cardNo,String branch_id) {
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		TbTenantInfo tbTenantInfo=null;
		if(cardNo.length() == 11){ //前端传入的是手机号码
		
			//分店租户信息
			tbTenantInfo = tenantService.findByBranchId(branch_id);
		   jsonMap.put("mobile", cardNo);
			jsonMap.put("cardno","");
			jsonMap.put("tenant_id", tbTenantInfo.getTenantId());
		}
		if(cardNo.length()!=11){
			//老版本会员账号,以及实体账号
			tbTenantInfo = tenantService.findByBranchId(branch_id.toString());
			String tenantId=tbTenantInfo.getTenantId().length()==12?"":tbTenantInfo.getTenantId();
			 String newCard_no = MemberSwtichCardNOUtil.getInStance().createCardNo(branch_id.toString()+tenantId,cardNo);
			 jsonMap.put("cardno",newCard_no);
		}
		List<Map<String,Object>> list = memberManagerService.findListByParams(jsonMap);
		return list.get(0).get("cardno").toString();
	}

	/**
	 * 会员储值
	 * @param params
	 * @return
	 */
	@RequestMapping("/StoreCardToNewPos")
	@ResponseBody
	public String StoreCardToNewPos(@RequestBody String jsonString,HttpServletRequest request){
		String cardNo = null ;
		String cardStatus = null ;
		Map<String, Object> map = null;
		HashMap<String, Object> params = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
		if(params.get("cardno") != null && !params.get("cardno").toString().equals("")){
			cardNo = params.get("cardno").toString();
			String branch_id=params.get("branch_id").toString();
			cardNo=queryCardno(cardNo,branch_id);
			cardStatus = cardInfoService.getCardStatusByCardNo(cardNo);
			//卡状态正常状态
			if( cardStatus != null && "1".equalsIgnoreCase(cardStatus) ){
				try{
					params.put("cardno", cardNo);
					map = dealService.addValue(params);
				}catch(Exception e){
					HashMap<String , Object> log = new HashMap<String , Object>();
					log.put("cardNo", cardNo);
					log.put("slipNo", params.get("Serial").toString());
					log.put("content", "content: " + e.getMessage());
					systemLogService.saveSystemLog(log);
				}
				if(map != null){
				    map.put("Retcode", 0);
					map.put("RetInfo", "OK");
			    }else{
				    map = new HashMap<String , Object>();
				    map.put("Retcode", 1);
					map.put("RetInfo", "充值失败");
				}
			}else{
			    map = new HashMap<String , Object>();
			    map.put("Retcode", 1);
			    map.put("RetInfo", "会员卡非正常状态，充值失败");
		    }
		}
		return JacksonJsonMapper.objectToJson(map);
	}
	
	
	/**
	 * 会员消费
	 * @param params
	 * branch_id ,SecurityCode,cardno,password,Serial
	 * FCash,FIntegral,FStore,FTicketList,InflatedRate,NetAmount
	 * 
	 * @return
	 * Retcode,TraceCode,StoreCardBalance,IntegralOverall
	 * AddIntegral,DecIntegral,RetInfo
	 */
	@RequestMapping(value = "/MemberSale",produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String memberSale(@RequestBody String jsonString,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> params = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);

//		如果不是微信支付，则传入的参数没有FWeChat，直接解析会报错
		Object weChat = params.get("FWeChat");
		if(weChat == null || "".equals(weChat)){
			params.put("FWeChat", "0.0");
		}
		
//		设置手机号或卡号
		Map<String,Object> memMap = new HashMap<String, Object>();
		String card_no = params.get("cardno").toString();
		if(card_no.length() == 11){ 	//前端传入的是手机号码
			memMap.put("mobile", card_no);
		}else{
			memMap.put("cardno",card_no);
		}
		
//		查询会员信息
		List<Map<String, Object>> memList = memberManagerService.findListByParams(memMap);
		if(memList == null || memList.isEmpty()){
			map.put("Retcode", 1);
			map.put("RetInfo","没有该会员信息");
			return JacksonJsonMapper.objectToJson(map);
		}

//		没有使用会员卡充值的情况下可以不输入密码
		double FIntegral = (double) params.get("FIntegral");
		double FStore = (double) params.get("FStore");
		if(FIntegral != 0 || FStore != 0 ){
//		判断会员密码是否正确
			Map<String, Object> member = memList.get(0);
			Object inputPsd = params.get("password");
			if(inputPsd == "" ||  !String.valueOf(member.get("password")).equals(inputPsd)){
				map.put("Retcode", 1);
				map.put("RetInfo","会员密码错误");
				return JacksonJsonMapper.objectToJson(map);
			}
		}
		
//		判断会员卡状态
		String cardStatus = cardInfoService.getCardStatusByCardNo(card_no);
		if(!cardStatus.equals("1")){
			map.put("Retcode", 1);
			map.put("RetInfo","会员卡状态不正常");
			return JacksonJsonMapper.objectToJson(map);
		}
		
		try{
			map = dealService.saveSale(params);
		}catch(Exception e){
			//e.printStackTrace();
			HashMap<String , Object> log = new HashMap<String , Object>();
			log.put("cardNo", params.get("cardno").toString());
			log.put("slipNo", params.get("Serial").toString());
			log.put("content", "content: " + e.getMessage());
			systemLogService.saveSystemLog(log);
			map.put("Retcode", 1);
			map.put("RetInfo","服务器出了点小问题，消费失败");
		}
		return JacksonJsonMapper.objectToJson(map);
	}
	
	
	@RequestMapping("/CardLose")
	@ResponseBody
	public String cardLose(@RequestBody String jsonString,HttpServletRequest request){
		Map<String, Object> map = null;
		Map<String,Object> params = JacksonJsonMapper.jsonToObject(jsonString, Map.class);
		try{
			String cardno=params.get("cardno")!=null?params.get("cardno").toString():null;
			String branchid=params.get("branch_id")!=null?params.get("branch_id").toString():null;
			if(cardno==null){
				 map=new HashMap<String,Object>();
		         map.put("Retcode", 1);
		         map.put("RetInfo", "卡号不能为空");
		         return JacksonJsonMapper.objectToJson(map);
			}
			if(branchid==null){
				 map=new HashMap<String,Object>();
		         map.put("Retcode", 1);
		         map.put("RetInfo", "门店号不能为空");
		         return JacksonJsonMapper.objectToJson(map);
			}
			if(cardno.length()!=30){
//				分店租户信息
		        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(params.get("branch_id").toString());
		        if (tbTenantInfo == null) {
		        	map=new HashMap<String,Object>();
		            map.put("Retcode", 1);
		            map.put("RetInfo", "没找到门店所属的租户");
		            return JacksonJsonMapper.objectToJson(map);
		        }
				if(cardno.indexOf("CD")>-1){
					cardno=MemberSwtichCardNOUtil.getInStance().createCardNo(cardno);
				}else{
					cardno=MemberSwtichCardNOUtil.getInStance().createCardNo(branchid+tbTenantInfo.getTenantId(), cardno);
				}
				params.put("cardno", cardno);
			}
			map = cardInfoService.updateCardLose(params);
			if(map==null){
				map=new HashMap<String,Object>();
			}
			map.put("Retcode", 0);
			map.put("RetInfo","OK");
		}catch(Exception e){
			//e.printStackTrace();
			map=new HashMap<String,Object>();
			map.put("Retcode", 1);
			map.put("RetInfo","挂失失败");
			HashMap<String , Object> log = new HashMap<String , Object>();
			log.put("cardNo", params.get("cardno").toString());
			log.put("slipNo", "");
			log.put("content", "content: " + e.getMessage());
			systemLogService.saveSystemLog(log);
		}
		return JacksonJsonMapper.objectToJson(map);
	}
	
	@RequestMapping("/UnCardLose")
	@ResponseBody
	public String unCardLose(@RequestBody String jsonString,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String,Object>();
		boolean flag = false ;
		Map<String,Object> params = JacksonJsonMapper.jsonToObject(jsonString, Map.class);
		try{
			String cardno=params.get("cardno")!=null?params.get("cardno").toString():null;
			String branchid=params.get("branch_id")!=null?params.get("branch_id").toString():null;
			if(cardno==null){
				 map=new HashMap<String,Object>();
		         map.put("Retcode", 1);
		         map.put("RetInfo", "卡号不能为空");
		         return JacksonJsonMapper.objectToJson(map);
			}
			if(branchid==null){
				 map=new HashMap<String,Object>();
		         map.put("Retcode", 1);
		         map.put("RetInfo", "门店号不能为空");
		         return JacksonJsonMapper.objectToJson(map);
			}
			if(cardno.length()!=30){
//				分店租户信息
		        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(params.get("branch_id").toString());
		        if (tbTenantInfo == null) {
		        	map=new HashMap<String,Object>();
		            map.put("Retcode", 1);
		            map.put("RetInfo", "没找到门店所属的租户");
		            return JacksonJsonMapper.objectToJson(map);
		        }
				if(cardno.indexOf("CD")>-1){
					cardno=MemberSwtichCardNOUtil.getInStance().createCardNo(cardno);
				}else{
					cardno=MemberSwtichCardNOUtil.getInStance().createCardNo(branchid+tbTenantInfo.getTenantId(), cardno);
				}
				params.put("cardno", cardno);
			}
			flag = cardInfoService.updateUnCardLose(params);
			if(flag){
				map.put("Retcode", 0);
				map.put("RetInfo","OK");
			}else{
				map.put("Retcode", 1);
				map.put("RetInfo","解除挂失失败");
			}
		}catch(Exception e){
			//e.printStackTrace();
			map.put("Retcode", 1);
			map.put("RetInfo","解除挂失失败");
			HashMap<String , Object> log = new HashMap<String , Object>();
			log.put("cardNo", params.get("cardno").toString());
			log.put("slipNo", "");
			log.put("content", "content: " + e.getMessage());
			systemLogService.saveSystemLog(log);
		}
		return JacksonJsonMapper.objectToJson(map);
	}
	
	@RequestMapping("/VoidSale")
	@ResponseBody
	public String voidSale(@RequestBody String jsonString,HttpServletRequest request){
		
		Map<String, Object> map = null;
        HashMap<String, Object> params = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
		try{
			map = dealService.voidSale(params);
		}catch(Exception e){
			HashMap<String , Object> log = new HashMap<String , Object>();
			log.put("cardNo", params.get("cardno").toString());
			log.put("slipNo", "");
			log.put("content", "content: " + e.getMessage());
			systemLogService.saveSystemLog(log);
		}
		
		if(map != null){
			map.put("Retcode", 0);
			map.put("RetInfo","OK");
		}else{
			map = new HashMap<>();
			map.put("Retcode", 1);
			map.put("RetInfo","取消交易失败");
		}
		
		return JacksonJsonMapper.objectToJson(map);
	}
	
	
	@RequestMapping("/memberView")
	@ResponseBody
	public ModelAndView memberView(@RequestParam Map<String, Object> params, HttpServletRequest request){
		ModelAndView mov=new ModelAndView("tenant");
		HashMap<String, Object>  viewMap = new HashMap<String, Object> ();
		viewMap.put("memberTotal", "123456");
		//mov.setViewName(viewName);
		mov.addObject("tenantMap", viewMap);
		return mov;
	}

}
