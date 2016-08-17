package com.candao.member.model;

public class TDealMaster {
	private int id ;
	private String branch_id ;
	private String card_no ;
	private String deal_no ;
	private double value ;
	private double actual_value ;
	private double present_value ;
	private double consume_value ;
	private double cash ;
	private double point ;
	private double present_point ;
	private double consume_point ;
	private String slip_no ;
	private String deal_type ;
	private String charge_type ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
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
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getPresent_value() {
		return present_value;
	}
	public void setPresent_value(double present_value) {
		this.present_value = present_value;
	}
	public double getConsume_value() {
		return consume_value;
	}
	public void setConsume_value(double consume_value) {
		this.consume_value = consume_value;
	}
	
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	public double getPresent_point() {
		return present_point;
	}
	public void setPresent_point(double present_point) {
		this.present_point = present_point;
	}
	public double getConsume_point() {
		return consume_point;
	}
	public void setConsume_point(double consume_point) {
		this.consume_point = consume_point;
	}
	public String getSlip_no() {
		return slip_no;
	}
	public void setSlip_no(String slip_no) {
		this.slip_no = slip_no;
	}
	public double getActual_value() {
		return actual_value;
	}
	public void setActual_value(double actual_value) {
		this.actual_value = actual_value;
	}
	public String getDeal_type() {
		return deal_type;
	}
	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	
}
