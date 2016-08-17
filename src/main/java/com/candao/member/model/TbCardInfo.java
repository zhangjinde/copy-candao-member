package com.candao.member.model;

import java.util.Date;

public class TbCardInfo {
	private  Integer id; 
	private  Integer tenant_id;
	private  String branch_id;
	private  String cardno; 
	private  String level; 
	private  String channel; 
	private  char status;
	private  Date valid_date;
	private  Date createtime; 
	private  String createuser; 
	private  Date updatetime;
	private  String updateuser;
	private  Integer card_type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(Integer tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
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
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Date getValid_date() {
		return valid_date;
	}
	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
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
	public Integer getCard_type() {
		return card_type;
	}
	public void setCard_type(Integer card_type) {
		this.card_type = card_type;
	}




	/**以下
	 * TbMemberManager中的属性
	 * 为简化json字符串转换问题
	 * */
	private  String name; 
	/** TbMemberManager中的属性**/
	private  String mobile; 
	/** TbMemberManager中的属性**/
	private  char gender; 
	/** TbMemberManager中的属性**/
	private  Date birthday;
	/** TbMemberManager中的属性**/
	private  String password;
	/** TbMemberManager中的属性**/
	private  String branch_addr;
	/** TbMemberManager中的属性**/
	private  String branch_name; 
	/** TbMemberManager中的属性**/
	private  String branch_phone; 
	/** TbMemberManager中的属性**/
	private  String member_avatar;
	/** TbMemberManager中的属性**/
	private  String securityCode;
	/** TbMemberManager中的属性**/
	private  String member_address;
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
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
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
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
}
