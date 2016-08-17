package com.candao.member.vo;

import java.util.Date;

public class DealDetailVO {
	private int id ;
	private String card_no ;
	private String deal_no ;
	private String deal_addr ;
	private String deal_type ;
	private double amount ;
	private String slip_no ;
	private Date deal_time ;
	private String deal_user ;
	private int  card_class;
	
	private String memberMobile;
	private String memberName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getDeal_no() {
		return deal_no;
	}
	public void setDeal_no(String deal_no) {
		this.deal_no = deal_no;
	}
	public String getDeal_addr() {
		return deal_addr;
	}
	public void setDeal_addr(String deal_addr) {
		this.deal_addr = deal_addr;
	}
	public String getDeal_type() {
		return deal_type;
	}
	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getSlip_no() {
		return slip_no;
	}
	public void setSlip_no(String slip_no) {
		this.slip_no = slip_no;
	}
	public Date getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(Date deal_time) {
		this.deal_time = deal_time;
	}
	public String getDeal_user() {
		return deal_user;
	}
	public void setDeal_user(String deal_user) {
		this.deal_user = deal_user;
	}
	public String getMemberMobile() {
		return memberMobile;
	}
	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getCard_class() {
		return card_class;
	}
	public void setCard_class(int card_class) {
		this.card_class = card_class;
	} 
	
}
