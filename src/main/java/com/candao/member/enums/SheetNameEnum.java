package com.candao.member.enums;

/**    
 *     
 * 项目名称：member    
 * 类名称：SheetNameEnum    
 * 类描述：  
 * 创建人：dengchao    
 * 创建时间：2016年6月22日 下午5:34:26    
 * 修改人：    
 * 修改时间：    
 * 修改备注：    
 * @version     
 *     
 */
public enum SheetNameEnum {

	MemberInfo("会员信息");//会员信息

	private String value;

	private SheetNameEnum(String value) {
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
