package com.candao.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.MemmberInfoManagerDao;
import com.candao.member.dao.TDealDetailDAO;
import com.candao.member.dao.TDiscountRuleDao;
import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TDealDetail;
import com.candao.member.model.TDiscountRule;
import com.candao.member.model.TbCardInfo;
import com.candao.member.utils.Constant;
import com.candao.member.utils.SessionUtils;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private MemmberInfoManagerDao memberManageDao;
	
	@Autowired
	private TDealDetailDAO tDealDetailDAO;
	
	@Autowired
	private TbCardInfoDao tbCardInfoDao;
	
	@Autowired
	private TDiscountRuleDao tDiscountRuleDao;
	
	@Override
	public Map<String, Object> grid(Map<String, Object> params, int current, int pagesize) {
		Map<String, Object> resultmap = new HashMap<>();
		Page<Map<String, Object>> page = memberManageDao.getReportPage(params, current, pagesize);
		List<Map<String, Object>> maps = (List<Map<String, Object>>) page.getRows();
		setReportMap(maps);
		resultmap.put("reportdate", maps);
		resultmap.put("records", page.getTotal());//总记录数
		resultmap.put("total", page.getPageCount());//总页数
		return resultmap;
	}


	/**
	 * 设置报表展示信息，
	 * 列表和导出数据共用的公共代码
	 * @param maps
	 */
	private void setReportMap(List<Map<String, Object>> maps) {
		String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		for(Map<String, Object> map : maps){
			String cardno = (String) map.get("cardno");
			if(cardno.contains("CD") ){
				map.put("cardno", "-");
			}else if(cardno.contains(tenantId)){
				String[] splitCardno = cardno.split(tenantId,2);
				map.put("cardno", splitCardno[1]);
			}
			
//			获取会员卡等级
			Map<String, Object> cardnoMap = new HashMap<>();
			cardnoMap.put("cardno", cardno);
			TbCardInfo cardInfo = tbCardInfoDao.getCardInfoByParam(cardnoMap);
			String level = cardInfo.getLevel();
			Map<String, Object> disParam = new HashMap<>();
			disParam.put("level", level);
			disParam.put("tenantId", tenantId);
			List<TDiscountRule> discountRule = tDiscountRuleDao.getdiscountRuleByParam(disParam);
			String discount = "-";
			if(discountRule.size() > 0){
				discount = discountRule.get(0).getDiscount();
			}
			map.put("discount", discount);
			
//			反结算的次数
			int settlementcount = Integer.parseInt(map.get("settlementcount").toString());
			
//			消费次数
			int consumecount = Integer.parseInt(map.get("countconsume").toString());
			
//			统计消费次数
			int countconsume = consumecount - settlementcount;
			map.put("countconsume", countconsume);
			
			map.remove("settlementcount");
		}
	}

	
	@Override
	public Map<String, Object> reportCollect(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<>();
		int newcardcount = 0;				//新会员卡总数
		double newMemberIncome = 0;			//新会员收入
		double oldMemaberIncome = 0; 		//老会员收入
		double newMemberPresent = 0;		//新会员赠送金额
		double oldMemberPresent = 0;		//老会员赠送
		double cashPay = 0;					//现金支付
		double bankPay = 0;					//银联支付
		double weChatPay = 0;				//微信支付
		double alipay = 0;					//支付宝支付
		double valueConsumption = 0;		//充值金额消费
		double consumptionCount = 0;		//消费次数
		
		Map<String, Object> newCard = getNewcard(param);
		List<TbCardInfo> newCardInfos = (List<TbCardInfo>) newCard.get("cardInfos");
		
		newcardcount = Integer.parseInt(newCard.get("count").toString());
		
		Map<String, Double> newmemberMap = getNewMemberInfo(newCardInfos,param);
		newMemberIncome = newmemberMap.get("newMemberIncom");
		newMemberPresent = newmemberMap.get("newMemberPresent");

		Map<String, Double> oldmemberMap = getOldmemberInfo(newCardInfos, param);
		oldMemaberIncome = oldmemberMap.get("oldmemberIncom");
		oldMemberPresent = oldmemberMap.get("oldMemberPresent");
		
		Map<String, Double> infomap = getInfo(param);
		cashPay = infomap.get("cash");
		bankPay = infomap.get("bank");
		valueConsumption = infomap.get("value");
		consumptionCount = infomap.get("countconsume");
		
		map.put("newcardcount", newcardcount);
		map.put("newMemberIncome", newMemberIncome);
		map.put("oldMemaberIncome", oldMemaberIncome);
		map.put("newMemberPresent", newMemberPresent);
		map.put("oldMemberPresent", oldMemberPresent);
		map.put("cashPay", cashPay);
		map.put("bankPay", bankPay);
		map.put("weChatPay", weChatPay);
		map.put("alipay", alipay);
		map.put("valueConsumption", valueConsumption);
		map.put("consumptionCount", consumptionCount);
		return map;
	}

	/**
	 * 获取新会员卡
	 * @return
	 */
	private Map<String, Object> getNewcard(Map<String, Object> param){
		List<TbCardInfo> cardInfos = tbCardInfoDao.getCardInfoByTime(param);
		Map<String, Object> map = new HashMap<>();
		map.put("cardInfos", cardInfos);
		map.put("count", cardInfos.size());
		return map;
	}
	
	/**
	 * 获取新会员交易信息
	 * 充值，赠送金额
	 * @return
	 */
	private Map<String, Double> getNewMemberInfo(List<TbCardInfo> cardInfos,Map<String, Object> param){
		double newMemberIncom = 0;
		double newMemberPresent = 0;
		String cardnos = getCardnos(cardInfos);
		param.put("cardnos", cardnos);
		List<TDealDetail> dealDetails = tDealDetailDAO.getDealDetailByTime(param);
		param.remove("cardnos");	//如果不移除掉已经存在的cardnos，对后续的操作有影响
		for(TDealDetail dealDetail : dealDetails){
			String dealType = dealDetail.getDeal_type();
			//现金充值,银行卡充值,支付宝充值（暂无），微信充值（暂无）
			if("5".equals(dealType) || "11".equals(dealType)){
				newMemberIncom = newMemberIncom + dealDetail.getAmount();
			}
			//储值赠送
			if("17".equals(dealType)){
				newMemberPresent = newMemberPresent + dealDetail.getAmount();
			}
		}
		Map<String, Double> map = new HashMap<>();
		map.put("newMemberIncom", newMemberIncom);
		map.put("newMemberPresent", newMemberPresent);
		return map;
	}
	
	/**
	 * 获取老会员交易信息
	 * 收入,赠送金额
	 * @return
	 */
	private Map<String, Double> getOldmemberInfo(List<TbCardInfo> newCardInfos,Map<String, Object> param){
		double oldmemberIncom = 0;
		double oldMemberPresent = 0;
		String cardnos = getCardnos(newCardInfos);
		param.put("cardnos", cardnos);
		List<TDealDetail> dealDetails = tDealDetailDAO.getOldCardDealDetailByTime(param);
		param.remove("cardnos");	//不移除掉会对后续操作产生影响
		for(TDealDetail dealDetail : dealDetails){
			String dealType = dealDetail.getDeal_type();
			//现金充值,银行卡充值,支付宝充值（暂无），微信充值（暂无）
			if("5".equals(dealType) || "11".equals(dealType)){
				oldmemberIncom += dealDetail.getAmount();
			}
			//储值赠送
			if("17".equals(dealType)){
				oldMemberPresent += dealDetail.getAmount();
			}
		}
		Map<String, Double> map = new HashMap<>();
		map.put("oldmemberIncom", oldmemberIncom);
		map.put("oldMemberPresent", oldMemberPresent);
		return map;
	}

	
	/**
	 * 获取某一时间段内的交易信息
	 * @return
	 */
	private Map<String, Double> getInfo(Map<String, Object> param){
		double cash = 0;
		double bank = 0;
		double value = 0; 	//储值消费
		int countconsume = 0;	//消费次数
		int settlementcount = 0;	//反结算次数
		List<TDealDetail> dealDetails = tDealDetailDAO.getDealDetailByTime(param);
		for(TDealDetail dealDetail : dealDetails){
			String dealType = dealDetail.getDeal_type();
			double amount = dealDetail.getAmount();
			//现金充值,银行卡充值
			if("5".equals(dealType)){
				cash += amount;
			}
			if("11".equals(dealType)){
				bank += amount;
			}
			//储值消费,储值消费反结算
			if("2".equals(dealType) || "8".equals(dealType)){
				value += amount;
			}
			//统计储值消费与积分消费的次数
			if("2".equals(dealType) || "4".equals(dealType)){
				countconsume = countconsume + 1;
			}
			//统计储值消费反结算与积分消费反结算的次数
			if("8".equals(dealType) || "10".equals(dealType)){
				settlementcount = settlementcount + 1;
			}
		}
		Map<String, Double> map = new HashMap<>();
		map.put("cash", cash);
		map.put("bank", bank);
		map.put("value", value);
		int count = countconsume - settlementcount;
		map.put("countconsume", (double) count);
		return map;
	}
	
	
	/**
	 * 拼接卡号
	 * @param cardInfos
	 * @return
	 */
	private String getCardnos(List<TbCardInfo> cardInfos){
		StringBuffer cardnos = new StringBuffer();
		for(TbCardInfo cardInfo : cardInfos){
			String cardno = cardInfo.getCardno();
			cardnos.append("'").append(cardno).append("'").append(",");
		}
		if(cardnos.length() == 0){
			cardnos.append("'").append("'").append(",");
		}
		return cardnos.substring(0, (cardnos.length()-1));
	}


	/**
	 * 获取报表列表数据
	 */
	@Override
	public List<Map<String, Object>> getReportsList(Map<String, Object> param) {
		List<Map<String, Object>> reports = memberManageDao.getReportsList(param);
		setReportMap(reports);
		return reports;
	}
}
