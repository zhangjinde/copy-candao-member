package com.candao.member.model;

import java.util.Date;

/**
 * 签约的租户数据
 * 对于新辣道集团就是分店号信息
 * 如果是其他的商户是另外的签约数据
 * @author tom-pc
 *
 */
public class TbTenantInfo implements java.io.Serializable {

	/**
	 * 这个默认的序列号的问题 是在处理判断是不是同一个类的问题
	 */
	private static final long serialVersionUID = 1L;
    
	/**
	 * 自增的类型
	 */
	private int id;
	
	/**
	 * 签约租户的id
	 */
	private String tenantId;
	
	/**
	 * 分店的id
	 */
	private String branchId;
	
	/**
	 * 租户名称
	 */
	private String tenantName;
	
	/**
	 * 租户电话
	 */
	private String tenantTel;
	
	/**
	 * 租户地址
	 */
	private String address;
	
	/**
	 * 租户状态  0 正常 1 不正常 或注销 冻结
	 */
	private int tenantStatus;
	
	/**
	 * 租户营业执照
	 */
	private String bizLicence;
	
	/**
	 * 签约开始日期
	 */
	private Date registeDate;
	
	/**
	 * 签约到期日
	 */
	private Date expireDate;
	
	/**
	 * 商户 签约渠道 
	 * 
	 * 0 线下签约 1 网签 2 电话 3 合作签约   默认 0 
	 */
	private int channelType;
	
	/**
	 * 租户交易安全码
	 */
	private String securityKey;
	
	/**
	 * 默认密码
	 */
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantTel() {
		return tenantTel;
	}

	public void setTenantTel(String tenantTel) {
		this.tenantTel = tenantTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(int tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	public String getBizLicence() {
		return bizLicence;
	}

	public void setBizLicence(String bizLicence) {
		this.bizLicence = bizLicence;
	}

	public Date getRegisteDate() {
		return registeDate;
	}

	public void setRegisteDate(Date registeDate) {
		this.registeDate = registeDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
