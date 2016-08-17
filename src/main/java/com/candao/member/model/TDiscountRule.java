package com.candao.member.model;

public class TDiscountRule {
	private String id;
	private String tenantId;
	private String discount;
	private int level;
	private double moneyMax;
	private double moneyMin;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getMoneyMax() {
		return moneyMax;
	}
	public void setMoneyMax(double moneyMax) {
		this.moneyMax = moneyMax;
	}
	public double getMoneyMin() {
		return moneyMin;
	}
	public void setMoneyMin(double moneyMin) {
		this.moneyMin = moneyMin;
	}
	@Override
	public String toString() {
		return "TDiscountRule [id=" + id + ", tenantId=" + tenantId + ", discount="
				+ discount + ", level=" + level + ", moneyMax=" + moneyMax + ", moneyMin=" + moneyMin + "]";
	}
	
}
