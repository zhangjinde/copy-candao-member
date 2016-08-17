package com.candao.member.model;

import java.util.Date;
public class TbMemberManager {
	private  Integer id; 
	private  String name; 
	private  String mobile; 
	private  String new_mobile; 
	private  Character gender; 
	private  Date birthday;
	private  String password;
	private  String tenant_id;
	private  String branch_id;
	private  String branch_addr;
	private  String branch_name; 
	private  String branch_phone; 
	private  Date createtime; 
	private  String createuser; 
	private  Date updatetime;
	private  String updateuser;
	private  String member_avatar;
	private  Character status;
	private  String securityCode;
	private  String member_address;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
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
	
	public String getMember_address() {
		return member_address;
	}
	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}



	public String getNew_mobile() {
		return new_mobile;
	}
	public void setNew_mobile(String new_mobile) {
		this.new_mobile = new_mobile;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}








	/**以下
	 * TbCardInfo中的属性
	 * 为简化json字符串转换问题
	 * */
	private  String cardno; 
	/** TbCardInfo中的属性**/
	private  String level; 
	/** TbCardInfo中的属性**/
	private  String channel; 
	/** TbCardInfo中的属性**/
	private  Date valid_date;
	/** TbCardInfo中的属性**/
	private  Integer card_type;
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Date getValid_date() {
		return valid_date;
	}
	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
	}
	public Integer getCard_type() {
		return card_type;
	}
	public void setCard_type(Integer card_type) {
		this.card_type = card_type;
	}
	
}
