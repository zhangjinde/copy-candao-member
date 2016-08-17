/**    
 * 文件名：DealTypeEnum.java    
 *    
 * 版本信息：    
 * 日期：2016年7月1日    
 * Copyright candaochina Corporation 2016     
 * 版权所有    
 *    
 */
package com.candao.member.enums;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：DealTypeEnum    
 * 类描述：交易类型枚举类    
 * 创建人：dengchao    
 * 创建时间：2016年7月1日 上午11:29:18    
 * 修改人：    
 * 修改时间： 
 * 修改备注：    
 * @version     
 *     
 */
public enum DealTypeEnum {
    
    CASH("0","现金消费"),
    CASH_POINT("1","现金消费积分"),
    CARD("2","储值消费"),
    CARD_POINT("3","储值消费积分"),
    POINT("4","积分消费"),
    CASH_STORE("5","现金充值"),
    CANCEL_CASH("6","现金消费反结算"),
    CANCEL_CASH_POINT("7","现金消费积分反结算"),
    CANCEL_CARD("8","储值消费反结算"),
    CANCEL_CARD_POINT("9","储值消费积分反结算"),
    CANCEL_POINT("10","积分消费反结算"),
    BANK_RECHARGE("11","银行卡充值"),
    MENBEROUT("12","会员注销退款"),
    WECHAT_PAY("13","微信扫码支付"),
    WECHAT_COUNTER("14","微信扫码支付反结算"),
    WECHAT_PAY_POINT("15","微信扫码支付积分"),
    WECHAT_COUNTER_POINT("16","微信扫码支付积分反结算"),
    STORGIVE("17","储值赠送");
    
    private String code;

    private String value;
    
    private DealTypeEnum(String code,String value){
        this.code=code;
        this.value=value;
    }
    
    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
