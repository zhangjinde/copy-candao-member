package com.candao.member.model;

import java.util.Date;

public class TbTenantCard {
	private  Integer id; 
	private  String tenant_id; 
	private  int start_no; 
	private  int end_no; 
	private  int current_no;
	private  char status;
	private  Date createtime;
	private  String createuser;
	private  Date updatetime;
	private  String updateuser;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public int getStart_no() {
		return start_no;
	}
	public void setStart_no(int start_no) {
		this.start_no = start_no;
	}
	public int getEnd_no() {
		return end_no;
	}
	public void setEnd_no(int end_no) {
		this.end_no = end_no;
	}
	public int getCurrent_no() {
		return current_no;
	}
	public void setCurrent_no(int current_no) {
		this.current_no = current_no;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
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

}
