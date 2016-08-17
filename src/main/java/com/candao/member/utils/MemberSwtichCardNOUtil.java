package com.candao.member.utils;

import com.candao.member.model.CardNoInfo;
import com.mysql.jdbc.log.Log;

public class MemberSwtichCardNOUtil {

	private static class SingletonHolder {

		private static MemberSwtichCardNOUtil ininstance = new MemberSwtichCardNOUtil();
	}

	private MemberSwtichCardNOUtil() {
	}

	public static MemberSwtichCardNOUtil getInStance() {
		return SingletonHolder.ininstance;
	}
	
	
	/**
     * 生成新的虚拟卡号
     * 
     * @param enCardNo
     * @return
     */
    public String createCardNo(String sourceCard) {
        String tagCarNo =sourceCard;
        String autoCard = "000000000000000000000000000000";
        int sourceCardSize = tagCarNo.trim().length();
        if (sourceCardSize < autoCard.length()) {
            tagCarNo = autoCard.substring(0, autoCard.length() - sourceCardSize) + tagCarNo;
        }
        return tagCarNo;
    }
	

	/**
	 * 生成新的虚拟卡号
	 * 
	 * @param enCardNo
	 * @return
	 */
	public String createCardNo(String tenantID, String sourceCard) {
		String tagCarNo ="";
		if(sourceCard.contains(tenantID)){
			tagCarNo=sourceCard;
		}else{
			 tagCarNo = tenantID + sourceCard;
		}

		String autoCard = "000000000000000000000000000000";
		int sourceCardSize = tagCarNo.trim().length();
		if (sourceCardSize < autoCard.length()) {
			tagCarNo = autoCard.substring(0, autoCard.length() - sourceCardSize) + tagCarNo;
		}
		return tagCarNo;
	}

	/**
	 * 
	 * @param tenantID
	 *            租户号
	 * @param sourceCard
	 *            卡号
	 * @return 返回租户号以及卡号数组 下标0，为租户号，如果没有租户表示为实体卡号， 下表1，返回实体
	 */
	public CardNoInfo parseCardNo(String tenantID, String cardNostrategy, String sourceCard) {
		// 对数据卡号信息分解成，租户号与卡号
		String newStr = sourceCard.replaceAll("^(0+)", "");// 去0字符串
		//如000000000000000000 100017 000041
		//000000000000000000000000 880822
		//0000000000000000100017 00000880822
		String cardNO = newStr.startsWith(tenantID)
				? newStr.replaceAll("^(" + tenantID + ")", "").replaceAll("^(0+)", "") 
				: newStr.replaceAll("^(0+)", "");
        long numStrategy=Long.parseLong(cardNostrategy);
        long cardNO_num=Long.parseLong(cardNO);
        //返回对象
         CardNoInfo cardNoInfo = null;
        if(numStrategy<cardNO_num){
        	//表示不是餐道会员系统生成的卡号
        	cardNoInfo =new CardNoInfo(true, tenantID, cardNO);
        }else{
        	cardNoInfo =new CardNoInfo(false, tenantID, cardNO);
        }
		return cardNoInfo;
	}
	
	public static String getCardNo(String tenantID,String branchId,String sourceCard){
	    return sourceCard.length()==30?sourceCard.substring(sourceCard.indexOf(tenantID)+tenantID.length()/*+branchId.length()*/):sourceCard;
	}
}
