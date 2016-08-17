package com.candao.member.model;

import java.util.Date;

public class TMemberService {
	private int id ;
	private int memberId ;
	private int cardId ;
	private String cardNo ;
	private String tenantId ;
	private String branchId ;
	private String service ;
	private String comment ;
	private Date serviceTime ;
	private String serviceUser ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getServiceUser() {
		return serviceUser;
	}
	public void setServiceUser(String serviceUser) {
		this.serviceUser = serviceUser;
	}
}
