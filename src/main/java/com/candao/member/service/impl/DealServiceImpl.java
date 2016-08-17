package com.candao.member.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.PreferentialDao;
import com.candao.member.dao.TCardAccountDAO;
import com.candao.member.dao.TDealDetailDAO;
import com.candao.member.dao.TDealMasterDAO;
import com.candao.member.dao.TDiscountRuleDao;
import com.candao.member.dao.TMemberCardDao;
import com.candao.member.dao.TMemberInfoDao;
import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TCardAccount;
import com.candao.member.model.TDealDetail;
import com.candao.member.model.TDealMaster;
import com.candao.member.model.TDiscountRule;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TPreferential;
import com.candao.member.model.TPreferentialInfo;
import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.DealService;
import com.candao.member.service.MemberInfoService;
import com.candao.member.service.RuleService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.TraceCodeUtil;
import com.candao.member.vo.DealDetailVO;


@Service
public class DealServiceImpl implements DealService {
	
	@Autowired
	private TDealMasterDAO tDealMasterDAO;
	
	@Autowired
	private TDealDetailDAO tDealDetailDAO;
	
	@Autowired
	private TCardAccountDAO tCardAccountDAO ;
	
	@Autowired
    private TbCardInfoDao tbCardInfoDao;
	
	@Autowired
	private TMemberInfoDao tMemberInfoDao;

	@Autowired
	private TMemberCardDao tMemberCardDao;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private TDiscountRuleDao tDiscountRuleDao;
	
	@Autowired
	private PreferentialDao preferentialDao;
	
	@Autowired
	private RuleService ruleService;
	
	/**
	 * 会员储值
	 * @param valueMap
	 * @return
	 */
	@Override
	public Map<String, Object> addValue(Map<String, Object> valueMap) {
		HashMap<String,Object> result = null;
		String card_no = valueMap.get("cardno").toString();
		TCardAccount cardAccount = tCardAccountDAO.getByCardNo(card_no);
		if(cardAccount != null){
			BigDecimal value = new BigDecimal(cardAccount.getValue());  				// 该会员现有储值余额
			BigDecimal actualValue = new BigDecimal(cardAccount.getActual_value());  	// 该会员现在实际储值
			BigDecimal presentValue = new BigDecimal(cardAccount.getPresent_value());  	// 该会员现在赠送储值
			
			BigDecimal amount = new BigDecimal(valueMap.get("Amount").toString());		// 本次充值从客户收到的的总额

			String giveValue=valueMap.containsKey("giveValue")?valueMap.get("giveValue").toString():"0";
			BigDecimal present = new BigDecimal(giveValue);								// 本次充值赠送金额
			BigDecimal amountValue = amount.add(present);								// 本次充值实际发生的总额
			
			value = value.add(amount).add(present); 									// 本次充值之后该会员的储值总余额
			actualValue = actualValue.add(amount); 										// 本次充值后该会员的实际储值总金额
			presentValue = presentValue.add(present); 									// 本次充值后该会员的赠送储值总金额

			//产生交易号
			String dealNo = TraceCodeUtil.generateTraceCode();

			//添加充值交易
			addDealMaster_CZ(valueMap, card_no, amountValue.doubleValue(), amount.doubleValue(),present.doubleValue(), dealNo);

			//添加充值-储值交易
			addDealDetail_CZ(valueMap, card_no, amount.doubleValue(), dealNo);

			//如果赠送金额大于0,则添加赠送金额的交易记录
			if(present.compareTo(BigDecimal.ZERO) == 1){
				Map<String, Object> map = valueMap;
				map.put("ChargeType", "17");	//设置充值赠送类型
				addDealDetail_CZ(map, card_no, present.doubleValue(), dealNo);
			}
			
			//更新会员卡账户
			updateCardAccount(cardAccount, value.doubleValue(), actualValue.doubleValue(),presentValue.doubleValue(),cardAccount.getPoint());
			
			//设置会员等级
				setMemberLevel(valueMap, card_no, amount.doubleValue());
			result = new HashMap<String, Object>();
	        result.put("TraceCode", dealNo);
	        result.put("StoreCardBalance", value);
	        result.put("GiftAmount", 0);
		}
		return result;
	}
	
	/**
	 * 会员消费
	 * @param saleMap
	 * @return
	 */
	public Map<String, Object> saveSale(Map<String,Object> saleMap){
//		获取当前账户
		HashMap<String,Object> result = new HashMap<String, Object>();
		String card_no = saleMap.get("cardno").toString();
		TCardAccount cardAccount = tCardAccountDAO.getByCardNo(card_no); 

		if(cardAccount != null) {
//			消费金额
			BigDecimal cash = new BigDecimal(saleMap.get("FCash").toString());		   //现金或银行卡
			BigDecimal store = new BigDecimal(saleMap.get("FStore").toString());	   //会员卡
			BigDecimal weChat = new BigDecimal(saleMap.get("FWeChat").toString());	   //微信支付
			BigDecimal integral = new BigDecimal(saleMap.get("FIntegral").toString()); //积分消费
			
//			该会员卡当前余额积分
			BigDecimal value = new BigDecimal(cardAccount.getValue()); 					//该 会员卡当前总余额
			BigDecimal actualValue = new BigDecimal(cardAccount.getActual_value()); 	//该会员卡实际储值总额
			BigDecimal presentValue = new BigDecimal(cardAccount.getPresent_value());	//该会员卡实际赠送总额
			BigDecimal point = new BigDecimal(cardAccount.getPoint());		 			//该会员卡当前总积分
//			BigDecimal presentPoint = new BigDecimal(cardAccount.getPresent_point());   //该会员卡当前赠送积分总额
			
//			根据积分规则和消费现金金额，计算出赠送积分
			BigDecimal presentIntegralCash = new BigDecimal(0);		//现金或银行卡消费积分
			BigDecimal presentIntegralStore = new BigDecimal(0); 	//会员卡余额消费积分
			BigDecimal presentPointWeChat = new BigDecimal(0);		//微信支付消费积分
			if(cash.doubleValue() > 0){
				presentIntegralCash = ruleService.getPresentIntegral(cash,saleMap);
			}
			if(store.doubleValue() > 0){
				presentIntegralStore = ruleService.getPresentIntegral(store,saleMap);
			}
			if(weChat.doubleValue() > 0){
				presentPointWeChat = ruleService.getPresentIntegral(weChat,saleMap);
			}
			
			value = value.subtract(store);			//本次消费后会员卡余额
			point = point.subtract(integral).add(presentIntegralCash).add(presentIntegralStore).add(presentPointWeChat);	//本次消费后会员积分
			
			if(value.compareTo(BigDecimal.ZERO) == -1){
		        result.put("Retcode", 1);
		        result.put("RetInfo", "会员卡余额不足");
		        return result;
			}
			if(point.compareTo(BigDecimal.ZERO) == -1){
		        result.put("Retcode", 1);
		        result.put("RetInfo", "会员卡积分不足");
		        return result;
			}
			
//			产生交易号
			String dealNo = TraceCodeUtil.generateTraceCode();
			
//			本次交易总交易额
			BigDecimal amount = cash.add(store).add(weChat).add(integral);
//			本次交易总积分
			BigDecimal amountpoint = presentIntegralCash.add(presentIntegralStore).add(presentPointWeChat);
			
//			添加交易记录（消费）
			addDealMaster_XF(saleMap, card_no, store, integral, value, point, dealNo, amount, amountpoint);
			
			if(cash.doubleValue() > 0){
				addDealDetail_XF(saleMap, card_no, cash, dealNo,"0");						//"0":现金消费
				if(presentIntegralCash.doubleValue() > 0){
					addDealDetail_XF(saleMap, card_no, presentIntegralCash, dealNo,"1");	//"1":现金消费积分
				}
			}
			
			if(store.doubleValue() > 0){
				addDealDetail_XF(saleMap, card_no, store, dealNo,"2");						//"2":储值消费
				if(presentIntegralStore.doubleValue() > 0){
					addDealDetail_XF(saleMap, card_no, presentIntegralStore, dealNo,"3");	//"3":储值消费积分
				}
			}
			
			if(weChat.doubleValue() > 0){
				addDealDetail_XF(saleMap, card_no, weChat, dealNo,"13");					//"13":微信扫码支付
				if(presentPointWeChat.doubleValue() > 0){
					addDealDetail_XF(saleMap, card_no, presentPointWeChat, dealNo,"15");	//"15":微信扫码支付积分
				}
			}
			
			if(integral.doubleValue() > 0){
				addDealDetail_XF(saleMap, card_no, integral, dealNo,"4");					//"4":积分消费
			}
			
//			更新会员卡账户
			updateCardAccount(cardAccount, value.doubleValue(), actualValue.doubleValue(), presentValue.doubleValue(),point.doubleValue());
			
	        result.put("TraceCode", dealNo);
	        result.put("StoreCardBalance", value);
	        result.put("IntegralOverall", point);
	        result.put("AddIntegral", amountpoint.doubleValue());
	        result.put("DecIntegral", integral);
	        result.put("NetAmount", 0);
	        result.put("InflatedRate", 0);
	        result.put("Retcode", 0);
	        result.put("RetInfo", "OK");
		}
		return result ;
	}
	

	@Override
	public Page<Map<String, Object>> grid(Map<String, Object> params,int current, int pagesize) {
		return tDealDetailDAO.page(params, current, pagesize);
	}
	
	/**
	 * 反结算
	 */
	public Map<String, Object> voidSale(Map<String,Object> saleMap){
		HashMap<String,Object> result = new HashMap<>();
		
		String traceCode = saleMap.get("TraceCode").toString();
		List<TDealDetail> tDealDetailList = new ArrayList<>();
		List<TDealDetail> tDealDetailVoidList = new ArrayList<>();
		
		String card_no = saleMap.get("cardno").toString();
		TCardAccount cardAccount = tCardAccountDAO.getByCardNo(card_no); 
		
		if(cardAccount != null) {
			BigDecimal value = new BigDecimal(cardAccount.getValue());		 			// 该会员卡当前储值总余额
			BigDecimal actualValue = new BigDecimal(cardAccount.getActual_value()); 	// 该会员卡当前实际储值总额
			BigDecimal point = new BigDecimal(cardAccount.getPoint()); 					// 该会员卡当前积分总额
			
			tDealDetailList = tDealDetailDAO.findByDealNo(traceCode);
			
			//产生交易号
			String dealNo = TraceCodeUtil.generateTraceCode();
			BigDecimal integralPresentVoid = new BigDecimal(0);  // 赠送积分取消
			BigDecimal integralConsumeVoid = new BigDecimal(0);  // 消费积分取消
			BigDecimal valueVoid = new BigDecimal(0);    //储值消费取消
			BigDecimal cash = new BigDecimal(0);		 //现金消费
			BigDecimal WeChat = new BigDecimal(0); 		 //微信扫码消费
			
			if(tDealDetailList != null && tDealDetailList.size() > 0){
				tDealDetailVoidList = new ArrayList<TDealDetail>();
				for(TDealDetail tDealDetail : tDealDetailList){
					if("0".equals(tDealDetail.getDeal_type())){  //现金消费
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "6");		//现金消费反结算
						cash = cash.add(new BigDecimal(tDealDetail.getAmount()));
					}
					if("1".equals(tDealDetail.getDeal_type())){  //现金消费积分
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "7");		//现金消费积分反结算
						integralPresentVoid = integralPresentVoid.add(new BigDecimal(tDealDetail.getAmount()));
					}
	                if("2".equals(tDealDetail.getDeal_type())){  //储值消费
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "8");		//储值消费反结算
						valueVoid = valueVoid.add(new BigDecimal(tDealDetail.getAmount()));
					}
	                if("3".equals(tDealDetail.getDeal_type())){  //储值消费积分
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "9");		//储值消费积分反结算
						integralPresentVoid = integralPresentVoid.add(new BigDecimal(tDealDetail.getAmount()));
	                }
	                if("4".equals(tDealDetail.getDeal_type())){  //积分消费
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "10");	//积分反结算
						integralConsumeVoid = integralConsumeVoid.add(new BigDecimal(tDealDetail.getAmount()));
	                }
	                if("13".equals(tDealDetail.getDeal_type())){  //微信扫码支付
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "14");	//微信扫码支付反结算
						WeChat = WeChat.add(new BigDecimal(tDealDetail.getAmount()));
	                }
	                if("15".equals(tDealDetail.getDeal_type())){  //微信扫码支付积分
						addDealDetail_FJS(saleMap, tDealDetailVoidList, card_no, dealNo, tDealDetail, "16");	//微信扫码支付积分反结算
						integralPresentVoid = integralPresentVoid.add(new BigDecimal(tDealDetail.getAmount()));
	                }
				}
				
				//添加反结算交易记录
				TDealMaster tDealMaster = new TDealMaster();
				BigDecimal minus_one = new BigDecimal(-1);	// 负1
				tDealMaster.setBranch_id(saleMap.get("branch_id").toString());
				tDealMaster.setCard_no(card_no);
				tDealMaster.setDeal_no(dealNo);
				tDealMaster.setValue(valueVoid.multiply(minus_one).doubleValue());
				tDealMaster.setActual_value(0);
				tDealMaster.setPresent_value(0);
				tDealMaster.setConsume_value(valueVoid.multiply(minus_one).doubleValue());
				tDealMaster.setCash(cash.multiply(new BigDecimal(-1)).doubleValue());
				tDealMaster.setPoint((integralPresentVoid.multiply(minus_one).add(integralConsumeVoid)).doubleValue());
				tDealMaster.setPresent_point(integralPresentVoid.multiply(minus_one).doubleValue());
				tDealMaster.setConsume_point(integralConsumeVoid.multiply(minus_one).doubleValue());
				tDealMaster.setSlip_no(saleMap.get("Serial").toString());
				tDealMaster.setDeal_type("");
				tDealMaster.setCharge_type("");
				tDealMasterDAO.add(tDealMaster);
				
				//添加取消交易
				for(TDealDetail tDealDetail : tDealDetailVoidList){
					tDealDetailDAO.add(tDealDetail);
				}  
		    }
			//更新会员卡账户
			cardAccount.setValue(value.add(valueVoid).doubleValue());
//			cardAccount.setActual_value(actualValue.add(valueVoid).doubleValue());
			cardAccount.setPoint(point.subtract(integralPresentVoid).add(integralConsumeVoid).doubleValue());
//			cardAccount.setPresent_value(presentValue);
			tCardAccountDAO.update(cardAccount);
			
			result = new HashMap<String, Object>();
	        result.put("TraceCode", dealNo);
	        result.put("StoreCardBalance", value.add(valueVoid).doubleValue());
	        result.put("IntegralOverall", point.subtract(integralPresentVoid).add(integralConsumeVoid).doubleValue());
	        result.put("Integral", integralPresentVoid.multiply(new BigDecimal(-1)).add(integralConsumeVoid).doubleValue());
	        result.put("Store", valueVoid);
	        result.put("UseIntegral", integralConsumeVoid);
	        result.put("RetInfo", "OK");
		}
		return result;
	}

	
	@Override
	public Map<String, Double> getStoreCount(String tenant_id) {
//		List<TbMemberInfo> memberInfos = tMemberInfoDao.getAllMmberByTenantId(tenant_id);
//		
//		BigDecimal cashCount = new BigDecimal(0);		//现金充值总额
//		BigDecimal bankCount = new BigDecimal(0);		//银行卡充值总额
//		BigDecimal refundCount = new BigDecimal(0);		//会员注销退款总额
//		BigDecimal presentCount = new BigDecimal(0);	//储值赠送总额
//		
//		StringBuilder mids = new StringBuilder();
//		for(TbMemberInfo memberInfo : memberInfos){
//			mids.append(memberInfo.getId()).append(",");
//		}
//		String member_ids = mids.toString();
//		member_ids = member_ids.substring(0, member_ids.length() - 1);
//		List<TMemberCard> cards = tMemberCardDao.selectMemberCardInMemberIds(member_ids);
//		StringBuilder cnos = new StringBuilder();
//		for(TMemberCard card : cards){
//			cnos = cnos.append("'").append(card.getCardno()).append("'").append(",");
//		}
//		String card_nos = cnos.toString();
//		card_nos = card_nos.substring(0, card_nos.length() - 1);
//		List<TDealDetail> dealDetails = tDealDetailDAO.getDealDetailByCardnos(card_nos);
//		for(TDealDetail dealDetail : dealDetails){
//			switch (dealDetail.getDeal_type()) {
//			case "5":		//“5”：交易类型为现金充值
//				cashCount = cashCount.add(new BigDecimal(dealDetail.getAmount()));
//				break;
//			case "11":		//“11”：交易类型为银行卡充值
//				bankCount = bankCount.add(new BigDecimal(dealDetail.getAmount()));
//				break;	
//			case "12":		//“12”：交易类型为会员注销退款
//				refundCount = refundCount.add(new BigDecimal(dealDetail.getAmount()));
//				break;
//			case "17":		//“17”：交易类型为储值赠送
//				presentCount = presentCount.add(new BigDecimal(dealDetail.getAmount()));
//				break;
//			default:
//				break;
//			}
//		}
		
//		Map<String, Double> map = new HashMap<>();
//		map.put("cashCount", cashCount.doubleValue());
//		map.put("bankCount", bankCount.doubleValue());
//		map.put("refundCount", refundCount.doubleValue());
//		map.put("presentCount", presentCount.doubleValue());
//		return map;
		Map<String,Double> result = new HashMap<>();
		
		result.put("cashCount", 0d);
		result.put("bankCount", 0d);
		result.put("refundCount", 0d);
		result.put("presentCount", 0d);
		
		List<HashMap<String,Object>> map = tDealDetailDAO.getDealType(tenant_id);
		for(HashMap<String,Object> dealDetail : map){
			if(null == dealDetail.get("dealtype")){
				continue;
			}
			
//			BigDecimal big = new BigDecimal("amount");
			switch (dealDetail.get("dealtype").toString()) {
			case "5":		//“5”：交易类型为现金充值
				
				result.put("cashCount", Double.valueOf(dealDetail.get("amount").toString()));
				break;
			case "11":		//“11”：交易类型为银行卡充值
				result.put("bankCount", Double.valueOf(dealDetail.get("amount").toString()));
				break;	
			case "12":		//“12”：交易类型为会员注销退款
				result.put("refundCount", Double.valueOf(dealDetail.get("amount").toString()));
				break;
			case "17":		//“17”：交易类型为储值赠送
				result.put("presentCount", Double.valueOf(dealDetail.get("amount").toString()));
				break;
			default:
				break;
			}
		}
		
		//查询导入会员的加上金额时候
/*		HashMap<String,Object> resultMap = tDealDetailDAO.getAddImportInfo(tenant_id);
		if(resultMap != null ){
			Double totalbalance = null != resultMap.get("totalbalance")?Double.parseDouble(resultMap.get("totalbalance").toString()):0;
			Double zstotalbalance = null != resultMap.get("zstotalbalance")?Double.parseDouble(resultMap.get("zstotalbalance").toString()):0;
			double temp1 = null != result.get("presentCount") ? result.get("presentCount")+zstotalbalance:zstotalbalance;
			BigDecimal big1 = new BigDecimal(temp1);//(new BigDecimal(temp1),2);
			result.put("presentCount",big1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());//赠送金额
			double temp2 = null != result.get("cashCount") ? result.get("cashCount")+totalbalance:totalbalance;
			BigDecimal big2 = new BigDecimal(temp2);
			result.put("cashCount", big2.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
		}
*/		
		return result;
	}

	@Override
	public Map<String, Object> getDealDetail(List<TbMemberInfo> infos,Map<String, Object> params, 
			int current, int pagesize) {
//		通过memberId查到所有的TMemberCard
		List<TMemberCard> cards = getMemberCards(infos);
		
//		拼接cardnos
		String cardNos = getCardNos(cards);
		params.put("cardNos", cardNos);
		
//		根据参数查询消费记录
		Page<TDealDetail> page = tDealDetailDAO.page(params, current, pagesize);
		List<TDealDetail> dealDetails = (List<TDealDetail>) page.getRows();
		List<DealDetailVO> detailVOs = new ArrayList<>();
//		拼装消费记录在前端页面的展示信息
		for(TDealDetail dealDetail : dealDetails){
			DealDetailVO detailVO = setDetailvo(dealDetail);
			detailVOs.add(detailVO);
		}
		
//		计算交易金额
		List<TDealDetail> details = tDealDetailDAO.findDealByParam(params);
		BigDecimal cardCZ = new BigDecimal(0);
		BigDecimal cashCZ = new BigDecimal(0);
		BigDecimal cardXF = new BigDecimal(0);
		BigDecimal presentCZ = new BigDecimal(0);
		BigDecimal cancelTK = new BigDecimal(0);
		
		for(TDealDetail dealDetail : details){
			int dealType = Integer.parseInt(dealDetail.getDeal_type());
			if(dealType == 5){ //现金充值
				cashCZ = cashCZ.add(new BigDecimal(dealDetail.getAmount()));
			}
			if(dealType == 11){	//银行卡充值
				cardCZ = cardCZ.add(new BigDecimal(dealDetail.getAmount()));
			}
			if(dealType == 12){	//会员注销退款
				cancelTK = cancelTK.add(new BigDecimal(dealDetail.getAmount()));
			}
			if(dealType == 17){	//储值赠送
				presentCZ = presentCZ.add(new BigDecimal(dealDetail.getAmount()));
			}
			if(dealType == 2){	//会员卡消费
				cardXF = cardXF.add(new BigDecimal(dealDetail.getAmount()));
			}
			if(dealType == 8){  //会员卡消费反结算
				cardXF = cardXF.add(new BigDecimal(dealDetail.getAmount()));
			}
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("page", page);
		result.put("detailVOs", detailVOs);
		result.put("cashCZ", cashCZ.doubleValue());
		result.put("cardCZ", cardCZ.doubleValue());
		result.put("cardXF", cardXF.doubleValue());
		result.put("presentCZ", presentCZ.doubleValue());
		result.put("cancelTK", cancelTK.doubleValue());
		return result;
	}

	

	/**
	 * 获取本次储值的赠送金额
	 * @return
	 */
	private BigDecimal getPresent(TPreferential pre,BigDecimal amount){
		BigDecimal present = new BigDecimal(0);
		if(pre != null){
			BigDecimal dealValue = new BigDecimal(pre.getDealValue());
			BigDecimal presentValue = new BigDecimal(pre.getPresentValue());
			Integer rule = pre.getRule();
			if(rule == 1){  //充值规则"1",按充值比例多充多送
				BigDecimal mult = amount.divideToIntegralValue(dealValue); 	//计算充值比例
				present = presentValue.multiply(mult);    //赠送储值
			}else{
				if(amount.compareTo(dealValue) != -1){  //实际储值大于优惠规则的充值金额上线
					present = presentValue;	//赠送金额等于优惠规则的赠送金额
				}else{
					present = new BigDecimal(0);
				}
			}
		}
		return present;
	}

	/**
	 * 获取可用的优惠
	 * @param valueMap
	 * @return
	 */
	private TPreferential getTPreferential(Map<String, Object> valueMap){
		Date now = new Date();
		long nowtime = now.getTime();
		//门店ID
		String branchId = valueMap.get("branch_id").toString();
		//优惠信息ID
		String tpid=!valueMap.containsKey("t_preferential_id")?null:valueMap.get("t_preferential_id").toString();
		TPreferential pre = null;
		if(tpid!=null&&!tpid.isEmpty()){
			//适应新版本
			TPreferential preferential = preferentialDao.getTPreferentialById(Integer.valueOf(tpid));
			Integer status = preferential.getStatus();
			if(status == 0){   //状态为正常（可用）
				pre=preferential;
			}
		}else{
			//兼容老版本
			pre=oldPos(nowtime,branchId);
		}
	
		

		return pre;
	}
	
	
	private TPreferential oldPos(long nowtime, String branchId) {
		TPreferential pre = null;
//		查询充值优惠记录
		List<TPreferentialInfo> infos = preferentialDao.getPreferentialInfoByBranchId(branchId);
		if(infos != null){
			for(TPreferentialInfo info : infos){
				Integer pid = info.getPreferentialId();
				TPreferential preferential = preferentialDao.getTPreferentialById(pid);
				if(preferential == null){
					continue;
				}
				Integer status = preferential.getStatus();
				if(status == 0){   //状态为正常（可用）
					Date sdate = preferential.getStartTime();
					Date edate = preferential.getEndTime();
					if(sdate!=null&&edate!=null){
						long stime = sdate.getTime();
						long etime = edate.getTime();
						if((stime - nowtime < 0) && (etime - nowtime > 0)){
							pre = preferential;
							break;
						}
					}
					
				}
			}
		}
		return pre;
	}

	/**
	 * 设置会员等级
	 * @param valueMap
	 * @param card_no
	 * @param changeValue
	 */
	private void setMemberLevel(Map<String, Object> valueMap, String card_no, double amount) {
		String branchId = (String) valueMap.get("branch_id");
		TbTenantInfo tenantInfo = tenantService.findByBranchId(branchId);
		int level = getLevel(amount,tenantInfo.getTenantId());
		TbCardInfo tbCardInfo = new TbCardInfo();
		tbCardInfo.setCardno(card_no);
		
		//设置新旧级别 新版本不支持级别
		tbCardInfo.setLevel(valueMap.containsKey("giveValue")?"-1":String.valueOf(level));
		tbCardInfoDao.update(tbCardInfo);
	}

	/**
	 * 更新会员卡账户
	 * @param cardAccount
	 * @param value
	 * @param actualValue
	 */
	private void updateCardAccount(TCardAccount cardAccount, double value, double actualValue,double presentValue,double point) {
		cardAccount.setValue(value);
		cardAccount.setActual_value(actualValue);
		cardAccount.setPresent_value(presentValue);
		cardAccount.setPoint(point);
		tCardAccountDAO.update(cardAccount);
	}

	/**
	 * 添加充值-储值交易
	 * @param valueMap
	 * @param card_no
	 * @param changeValue
	 * @param dealNo
	 */
	private void addDealDetail_CZ(Map<String, Object> valueMap, String card_no, double changeValue, String dealNo) {
		TDealDetail tDealDetailValue;
		tDealDetailValue = new TDealDetail();
		tDealDetailValue.setCard_no(card_no);
		tDealDetailValue.setDeal_no(dealNo);
		tDealDetailValue.setDeal_addr("");
		if(Integer.parseInt((String) valueMap.get("ChargeType")) == 1){
			tDealDetailValue.setDeal_type("11"); // 银行卡充值
		}else if(Integer.parseInt((String) valueMap.get("ChargeType")) == 0){
			tDealDetailValue.setDeal_type("5");  // 现金充值
		}else if(Integer.parseInt((String) valueMap.get("ChargeType")) == 17){
			tDealDetailValue.setDeal_type("17"); // 储值赠送
		}
		tDealDetailValue.setAmount(changeValue);
		tDealDetailValue.setSlip_no(valueMap.get("Serial").toString());
		tDealDetailValue.setDeal_user("Test");
		tDealDetailDAO.add(tDealDetailValue);
	}

	/**
	 * 添加充值交易(储值)
	 * @param valueMap
	 * @param card_no
	 * @param value
	 * @param actualValue
	 * @param dealNo
	 */
	private void addDealMaster_CZ(Map<String, Object> valueMap, String card_no, double value, double actualValue,double presentValue,
			String dealNo) {
		TDealMaster tDealMaster = new TDealMaster();
		tDealMaster.setBranch_id(valueMap.get("branch_id").toString());
		tDealMaster.setCard_no(card_no);
		tDealMaster.setDeal_no(dealNo);
		tDealMaster.setValue(value);
		tDealMaster.setActual_value(actualValue);
		tDealMaster.setPresent_value(presentValue);
		tDealMaster.setConsume_value(0);
		tDealMaster.setSlip_no(valueMap.get("Serial").toString());
		tDealMaster.setPoint(0);
		tDealMaster.setPresent_point(0);
		tDealMaster.setConsume_point(0);
		tDealMaster.setDeal_type(valueMap.get("TransType").toString());
		tDealMaster.setCharge_type(valueMap.get("ChargeType").toString());
		tDealMasterDAO.add(tDealMaster);
	}

	/**
	 * 获取会员等级(查询等级规则表)
	 * @param valueMap
	 * @return
	 */
	private int getLevel(double money,String tenantId) {
		Map<String, Object> param = new HashMap<>();
		param.put("tenantId", tenantId);
		List<TDiscountRule> discountRules = tDiscountRuleDao.getdiscountRuleByParam(param);
		int level = -1;
		for(TDiscountRule rule : discountRules){
			double max = rule.getMoneyMax();
			double min = rule.getMoneyMin();
			if(money < max && money >= min){
				level = rule.getLevel();
				break;
			}
		}
		return level;
	}
	
	


	/**
	 * 添加交易明细(消费交易)
	 * @param saleMap
	 * @param card_no
	 * @param amount
	 * @param dealNo
	 * @param deal_type
	 */
	private void addDealDetail_XF(Map<String, Object> saleMap, String card_no, BigDecimal amount, String dealNo,String deal_type) {
		TDealDetail tDealDetail = new  TDealDetail();
		tDealDetail.setCard_no(card_no);
		tDealDetail.setDeal_no(dealNo);
		tDealDetail.setDeal_addr("");
		tDealDetail.setDeal_type(deal_type);
		tDealDetail.setAmount(amount.doubleValue());
		tDealDetail.setSlip_no(saleMap.get("Serial").toString());
		tDealDetail.setDeal_user("Test");
		tDealDetailDAO.add(tDealDetail);
	}

	/**
	 * 添加交易信息(消费)
	 * @param saleMap
	 * @param card_no
	 * @param store
	 * @param integral
	 * @param value
	 * @param point
	 * @param dealNo
	 * @param amount
	 * @param amountpoint
	 */
	private void addDealMaster_XF(Map<String, Object> saleMap, String card_no, BigDecimal store, BigDecimal integral,
			BigDecimal value, BigDecimal point, String dealNo, BigDecimal amount, BigDecimal amountpoint) {
//			添加交易信息
			TDealMaster tDealMaster = new TDealMaster();
			tDealMaster.setBranch_id(saleMap.get("branch_id").toString());
			tDealMaster.setCard_no(card_no);
			tDealMaster.setDeal_no(dealNo);
			tDealMaster.setValue(value.doubleValue());					//本次消费后会员卡剩余余额
			tDealMaster.setActual_value(amount.doubleValue());			//本次消费总交易额
			tDealMaster.setPresent_value(0);							//本次消费赠送金额
			tDealMaster.setConsume_value(store.doubleValue());			//会员卡消费金额
			tDealMaster.setPoint(point.doubleValue());   				//本次消费后会员卡剩余积分
			tDealMaster.setPresent_point(amountpoint.doubleValue());	//本次消费赠总积分
			tDealMaster.setConsume_point(integral.doubleValue());		//本次消费用掉多少积分
			tDealMaster.setSlip_no(saleMap.get("Serial").toString());
			tDealMaster.setDeal_type("");
			tDealMaster.setCharge_type("");
			tDealMasterDAO.add(tDealMaster);
	}
	
	
	/**
	 * 反结算交易记录
	 * @param saleMap
	 * @param tDealDetailVoidList
	 * @param card_no
	 * @param dealNo
	 * @param tDealDetail
	 * @param dealType
	 */
	private void addDealDetail_FJS(Map<String, Object> saleMap, List<TDealDetail> tDealDetailVoidList, String card_no,
			String dealNo, TDealDetail tDealDetail,String dealType) {
		TDealDetail tDealDetailVoid = new TDealDetail();
		tDealDetailVoid.setCard_no(card_no);
		tDealDetailVoid.setDeal_no(dealNo);
		tDealDetailVoid.setDeal_addr("");
		tDealDetailVoid.setDeal_type(dealType);
		tDealDetailVoid.setAmount(-1*tDealDetail.getAmount());
		tDealDetailVoid.setSlip_no(saleMap.get("Serial").toString());
		tDealDetailVoid.setDeal_user("");
		tDealDetailVoidList.add(tDealDetailVoid);
	}
	
	
	
	/**
	 * 通过memberId查到所有的TMemberCard
	 * @param infos
	 * @return
	 */
	private List<TMemberCard> getMemberCards(List<TbMemberInfo> infos){
		String memberIds = new String();
		for(TbMemberInfo info : infos){		//已经在Controller里面判断了为空的情况
			memberIds += info.getId() + ",";
		}
		memberIds = memberIds.substring(0, memberIds.length() - 1);
		List<TMemberCard> cards = tMemberCardDao.selectMemberCardInMemberIds(memberIds);
		return cards;
	}
	
	/**
	 * 拼接cardnos
	 * @param cards
	 * @return
	 */
	private String getCardNos(List<TMemberCard> cards){
		String cardNos = new String();
		for(TMemberCard card : cards){
			cardNos += card.getCardno() + ",";
		}
		cardNos = cardNos.substring(0,cardNos.length() - 1);
		return cardNos;
	}
	
	
	/**
	 * 设置消费记录vo
	 * @param dealDetail
	 * @return
	 */
	private DealDetailVO setDetailvo(TDealDetail dealDetail) {
		DealDetailVO detailVO = new DealDetailVO();
		detailVO.setCard_no(dealDetail.getCard_no());
		detailVO.setDeal_addr(dealDetail.getDeal_addr());
		detailVO.setDeal_no(dealDetail.getDeal_no());
		detailVO.setDeal_time(dealDetail.getDeal_time());
		detailVO.setDeal_type(dealDetail.getDeal_type());
		detailVO.setDeal_user(dealDetail.getDeal_user());
		detailVO.setAmount(dealDetail.getAmount());
		detailVO.setSlip_no(dealDetail.getSlip_no());
		detailVO.setCard_class(dealDetail.getCard_class());
		
		//通过CardNo获取到MemberInfo
		TbMemberInfo memberInfo = new TbMemberInfo();
		memberInfo = memberInfoService.getMemberInfoByCardNo(detailVO.getCard_no());
		detailVO.setMemberMobile(memberInfo.getMobile());
		detailVO.setMemberName(memberInfo.getName());
		return detailVO;
	}

}
