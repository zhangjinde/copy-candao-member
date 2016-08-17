package com.candao.member.model;

/**
 * 优惠明细
 * @author 001
 *
 */
public class TPreferentialInfo {
	
	private Integer id;
	private Integer preferentialId;
	private String branchId;
	private String tenantId;
	private String branchName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPreferentialId() {
		return preferentialId;
	}
	public void setPreferentialId(Integer preferentialId) {
		this.preferentialId = preferentialId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}
