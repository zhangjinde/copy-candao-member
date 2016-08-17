package com.candao.member.model;

import java.util.Date;

public class TMemberDeal {
	private int id ;
	private int card_id ;
	private String card_no;
	private String deal_addr ;
	private String deal_type ;
	private double point_change ;
	private double point ;
	private double value_change ;
	private double value ;
	private String charge_type ;
	private String slip_no ;
	private String status ;
	private int deal_id ;
	private Date deal_time ;
	private String deal_user ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
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
	public double getPoint_change() {
		return point_change;
	}
	public void setPoint_change(double point_change) {
		this.point_change = point_change;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	public double getValue_change() {
		return value_change;
	}
	public void setValue_change(double value_change) {
		this.value_change = value_change;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
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
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(int deal_id) {
		this.deal_id = deal_id;
	}
}
