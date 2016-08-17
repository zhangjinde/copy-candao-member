package com.candao.member.model;

import java.util.Date;
import java.util.List;

/**
 * 优惠
 * @author 001
 *
 */
public class TPreferential {
	private Integer id;
	private String name;
	private Integer status;
	
	private String sTime;
	private String eTime;
	
	private Date startTime;
	private Date endTime;
	private Date createTime;
	private Date updateTime;
	private String description;
	private String tenantId;
	private Integer type;
	private Integer dealValue;
	private Integer presentValue;
	private Integer rule;
	private String typeName;
	private Integer countInfo;
	private List<TPreferentialInfo> preferentialInfos;
	private Integer weixinStatus;
	
	public Integer getId() { 
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDealValue() {
		return dealValue;
	}
	public void setDealValue(Integer dealValue) {
		this.dealValue = dealValue;
	}
	public Integer getPresentValue() {
		return presentValue;
	}
	public void setPresentValue(Integer presentValue) {
		this.presentValue = presentValue;
	}
	public Integer getRule() {
		return rule;
	}
	public void setRule(Integer rule) {
		this.rule = rule;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getCountInfo() {
		return countInfo;
	}
	public void setCountInfo(Integer countInfo) {
		this.countInfo = countInfo;
	}
	public List<TPreferentialInfo> getPreferentialInfos() {
		return preferentialInfos;
	}
	public void setPreferentialInfos(List<TPreferentialInfo> preferentialInfos) {
		this.preferentialInfos = preferentialInfos;
	}
	public Integer getWeixinStatus() {
		return weixinStatus;
	}
	public void setWeixinStatus(Integer weixinStatus) {
		this.weixinStatus = weixinStatus;
	}
	
	
}
