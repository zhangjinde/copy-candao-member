package com.candao.member.model;

import java.io.Serializable;

/**
 * 
 * @author Candao 卡信息
 */
public class CardNoInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean flag;// 是否是实体卡 true 是 false 不是
	private String tenantID;// 租户ID
	private String cardNo;// 卡号，不论是否是实体卡获取虚拟卡

	public CardNoInfo(boolean falg, String tenantID, String cardNo) {
		this.flag=falg;
		this.tenantID=tenantID;
		this.cardNo=cardNo;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
