package com.candao.member.model;

import java.util.Date;

public class TbMemberInfo {
	private int id; 
	private String name; 
	private String mobile;
	private String gender; 
	private Date birthday;
	private String password;
	private String tenant_id;
	private String branch_id;
	private String branch_addr;
	private String branch_name;
	private String branch_phone;
	private Date createtime;
	private String createuser;
	private Date updatetime;
	private String updateuser; 
	private String member_avatar;
	private int status;
	private String member_address;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getBranch_addr() {
		return branch_addr;
	}
	public void setBranch_addr(String branch_addr) {
		this.branch_addr = branch_addr;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBranch_phone() {
		return branch_phone;
	}
	public void setBranch_phone(String branch_phone) {
		this.branch_phone = branch_phone;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public String getMember_avatar() {
		return member_avatar;
	}
	public void setMember_avatar(String member_avatar) {
		this.member_avatar = member_avatar;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMember_address() {
		return member_address;
	}
	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}
	
	@Override
	public String toString() {
		return "TbMemberInfo [id=" + id + ", name=" + name + ", mobile=" + mobile + ", gender=" + gender + ", birthday="
				+ birthday + ", password=" + password + ", tenant_id=" + tenant_id + ", branch_id=" + branch_id
				+ ", branch_addr=" + branch_addr + ", branch_name=" + branch_name + ", branch_phone=" + branch_phone
				+ ", createtime=" + createtime + ", createuser=" + createuser + ", updatetime=" + updatetime
				+ ", updateuser=" + updateuser + ", member_avatar=" + member_avatar + ", status=" + status
				+ ", member_address=" + member_address + "]";
	}
	
}
