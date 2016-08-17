package com.candao.member.model;

public class TbMemberAndTenantCard {
	private Integer member_id;
	private String cardno;
	private TbMemberManager tbMemberManager;
	private TbCardInfo tbCardInfo;
	
	public Integer getMember_id() {
		return member_id;
	}
	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public TbMemberManager getTbMemberManager() {
		return tbMemberManager;
	}
	public void setTbMemberManager(TbMemberManager tbMemberManager) {
		this.tbMemberManager = tbMemberManager;
	}
	public TbCardInfo getTbCardInfo() {
		return tbCardInfo;
	}
	public void setTbCardInfo(TbCardInfo tbCardInfo) {
		this.tbCardInfo = tbCardInfo;
	}
	
	
}
