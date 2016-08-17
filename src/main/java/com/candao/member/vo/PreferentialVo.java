package com.candao.member.vo;

import java.util.Date;
import java.util.List;

public class PreferentialVo {
	private Integer preferentialId;
	private String name;
	private Integer status;
	private Date startTime;
	private Date endTime;
	private Integer preferentialType;
	private Double dealValue;
	private Double presentValue;
	private Integer rule;
	private List<Branch> branchs;
	
	public Integer getPreferentialId() {
		return preferentialId;
	}
	public void setPreferentialId(Integer preferentialId) {
		this.preferentialId = preferentialId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getPreferentialType() {
		return preferentialType;
	}
	public void setPreferentialType(Integer preferentialType) {
		this.preferentialType = preferentialType;
	}
	public Double getDealValue() {
		return dealValue;
	}
	public void setDealValue(Double dealValue) {
		this.dealValue = dealValue;
	}
	public Double getPresentValue() {
		return presentValue;
	}
	public void setPresentValue(Double presentValue) {
		this.presentValue = presentValue;
	}
	public Integer getRule() {
		return rule;
	}
	public void setRule(Integer rule) {
		this.rule = rule;
	}
	public List<Branch> getBranchs() {
		return branchs;
	}
	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}


	class Branch{
		private Integer preferential_id;
		private String branch_id;
		private String branch_name;
		public Integer getPreferential_id() {
			return preferential_id;
		}
		public void setPreferential_id(Integer preferential_id) {
			this.preferential_id = preferential_id;
		}
		public String getBranch_id() {
			return branch_id;
		}
		public void setBranch_id(String branch_id) {
			this.branch_id = branch_id;
		}
		public String getBranch_name() {
			return branch_name;
		}
		public void setBranch_name(String branch_name) {
			this.branch_name = branch_name;
		}
	}
}
