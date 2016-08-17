package com.candao.member.vo;

import java.io.Serializable;

import com.candao.member.utils.Constant;

public class VipInfoExcelVo implements Serializable {
	private String pno;//编号
	private String name;//姓名
	private String level;//等级
	private String type;//类型
	private String status;//状态
	private String glevel;//挂账等级
	private String mobile;//手机
	private String gender;//性别
	private String birthday;//生日
	private String cmoney;//充值金额
	private String zsmoney;//赠送金额
	private String value;//余额
	private String point;//积分
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGlevel() {
		return glevel;
	}
	public void setGlevel(String glevel) {
		this.glevel = glevel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		if(birthday==null || "".equals(birthday) || birthday.length()<5){
			birthday=Constant.DEFAULTBIRTHDAY;
		}
		this.birthday = birthday;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCmoney() {
		return cmoney;
	}
	public void setCmoney(String cmoney) {
		if(cmoney!=null && cmoney.contains(".")){
			cmoney=cmoney.substring(0,cmoney.indexOf("."));
		}
		this.cmoney = cmoney;
	}
	public String getZsmoney() {
		return zsmoney;
	}
	public void setZsmoney(String zsmoney) {
		this.zsmoney = zsmoney;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	
	
}
