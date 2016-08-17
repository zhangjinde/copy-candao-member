package com.candao.member.model;

import java.util.Date;

public class TSystemLog {
	private int id ;
	private String cardNo ;
	private String slipNo ;
	private String content ;
	private Date operationTime ;
	private String operator ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
