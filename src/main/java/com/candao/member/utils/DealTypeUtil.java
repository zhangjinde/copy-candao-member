package com.candao.member.utils;

public enum DealTypeUtil {
//		<c:when test="${item.deal_type == '0'}">现金消费</c:when>
//		<c:when test="${item.deal_type == '1'}">现金消费积分</c:when>
//		<c:when test="${item.deal_type == '2'}">会员卡消费</c:when>
//		<c:when test="${item.deal_type == '3'}">储值消费积分</c:when>
//		<c:when test="${item.deal_type == '4'}">积分消费</c:when>
//		<c:when test="${item.deal_type == '5'}">现金充值</c:when>
//		<c:when test="${item.deal_type == '6'}">现金消费反结算</c:when>
//		<c:when test="${item.deal_type == '7'}">现金消费积分反结算</c:when>
//		<c:when test="${item.deal_type == '8'}">会员卡消费反结算</c:when>
//		<c:when test="${item.deal_type == '9'}">储值消费积分反结算</c:when>
//		<c:when test="${item.deal_type == '10'}">积分消费反结算</c:when>
//		<c:when test="${item.deal_type == '11'}">银行卡充值</c:when>
//		<c:when test="${item.deal_type == '12'}">会员注销退款</c:when>
//		<c:when test="${item.deal_type == '17'}">储值赠送</c:when>     
	CASHCONSUMPTION("0","现金消费"),CASHCONSUMPTIONSCORE("1","现金消费积分"),MEMBERCARDCONSUMPTION("2","储值消费"),
    STORCONSUMPTIONINTEGRATION("3","储值消费积分"),INTEGRALCON("4","积分消费"),CASHRECH("5","现金充值"),
    CASHCONCONUTBALANCE("6","现金消费反结算"),CASHCONTIONCONUTBALANCE("7","现金消费积分反结算"),MENBERCONTIONCONUTBALANCE("8","储值消费反结算"),
    STORCONGRACONUTBALANCE("9","储值消费积分反结算"),INTEGRALCONCONUTBALANCE("10","积分消费反结算"),BANKCHARGE("11","银行卡充值"),
    MENBEROUT("12","会员注销退款"),STORGIVE("17","储值赠送");
	private String  dealType;
	private String dealMes;
	DealTypeUtil(String dealType,String dealMes) {
		this.dealType=dealType;
		this.dealMes=dealMes;
	}
	
   public static String dealMes(String typeInx){
	   for(DealTypeUtil typeUtil:DealTypeUtil.values()){
		   	if(typeUtil.getDealType().equals(typeInx)){
		   		return typeUtil.getDealMes();
		   	}
	   }
   return "";
   }
	public String getDealMes() {
		return dealMes;
	}

	
	public String getDealType() {
		return dealType;
	}

	
}
