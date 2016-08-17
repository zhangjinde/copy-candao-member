package com.candao.member.model;

public class TCardAccount {
	private int id ;
	private int card_id;
	private String card_no ;
	private double point ;
	private double present_point;
	private double value;
	private double present_value;
	private double  actual_value;
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
	public double getActual_value() {
		return actual_value;
	}
	public void setActual_value(double actual_value) {
		this.actual_value = actual_value;
	}
	
}
