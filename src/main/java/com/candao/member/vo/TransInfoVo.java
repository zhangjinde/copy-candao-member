/**    
 * 文件名：TransInfoVo.java    
 *    
 * 版本信息：    
 * 日期：2016年6月30日    
 * Copyright candaochina Corporation 2016     
 * 版权所有    
 *    
 */
package com.candao.member.vo;

import java.math.BigDecimal;

import com.candao.member.utils.ExcelField;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：TransInfoVo    
 * 类描述：    
 * 创建人：dengchao    
 * 创建时间：2016年6月30日 上午9:44:59    
 * 修改人：    
 * 修改时间： 
 * 修改备注：    
 * @version     
 *     
 */
public class TransInfoVo {
    private String slipNo; 
    private String branchId; 
    private BigDecimal amount; 
    private String cardName; 
    private String totalBalance; 
    private String mobile;
    private String cardType; 
    private String memberName; 
    private BigDecimal consumeValue; 
    private BigDecimal consumePoint; 
    private String dealNo; 
    private String dealType; 
    private String cardNo; 
    private String dealName; 
    private String dealTime; 
    private String zstotalBalance; 
    private String dealUser; 
    private BigDecimal cash;
    /**    
     * slipNo    
     *    
     * @return  the slipNo    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    @ExcelField(title="票据号", align=2, sort=1)
    public String getSlipNo() {
        return slipNo;
    }
    /**    
     * @param slipNo the slipNo to set    
     */
    
    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }
    /**    
     * branchId    
     *    
     * @return  the branchId    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="门店编号", align=2, sort=12)
    public String getBranchId() {
        return branchId;
    }
    /**    
     * @param branchId the branchId to set    
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
    /**    
     * amount    
     *    
     * @return  the amount    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="实收金额", align=2, sort=6)
    public BigDecimal getAmount() {
        return amount;
    }
    /**    
     * @param amount the amount to set    
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    /**    
     * cardName    
     *    
     * @return  the cardName    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="会员卡名称", align=2, sort=5)
    public String getCardName() {
        return cardName;
    }
    /**    
     * @param cardName the cardName to set    
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    /**    
     * totalBalance    
     *    
     * @return  the totalBalance    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="充值金额消费", align=2, sort=8)
    public String getTotalBalance() {
        return totalBalance;
    }
    /**    
     * @param totalBalance the totalBalance to set    
     */
    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }
    /**    
     * mobile    
     *    
     * @return  the mobile    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="手机号", align=2, sort=4)
    public String getMobile() {
        return mobile;
    }
    /**    
     * @param mobile the mobile to set    
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**    
     * cardType    
     *    
     * @return  the cardType    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    public String getCardType() {
        return cardType;
    }
    /**    
     * @param cardType the cardType to set    
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    /**    
     * memberName    
     *    
     * @return  the memberName    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="客户名称", align=2, sort=3)
    public String getMemberName() {
        return memberName;
    }
    /**    
     * @param memberName the memberName to set    
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    /**    
     * consumeValue    
     *    
     * @return  the consumeValue    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="会员卡支付", align=2, sort=7)
    public BigDecimal getConsumeValue() {
        return consumeValue;
    }
    /**    
     * @param consumeValue the consumeValue to set    
     */
    public void setConsumeValue(BigDecimal consumeValue) {
        this.consumeValue = consumeValue;
    }
    /**    
     * dealNo    
     *    
     * @return  the dealNo    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    public String getDealNo() {
        return dealNo;
    }
    /**    
     * @param dealNo the dealNo to set    
     */
    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }
    /**    
     * dealType    
     *    
     * @return  the dealType    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    public String getDealType() {
        return dealType;
    }
    /**    
     * @param dealType the dealType to set    
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
    }
    /**    
     * cardNo    
     *    
     * @return  the cardNo    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="卡号", align=2, sort=2)
    public String getCardNo() {
        return cardNo;
    }
    /**    
     * @param cardNo the cardNo to set    
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    /**    
     * dealName    
     *    
     * @return  the dealName    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    public String getDealName() {
        return dealName;
    }
    /**    
     * @param dealName the dealName to set    
     */
    public void setDealName(String dealName) {
        this.dealName = dealName;
    }
    /**    
     * dealTime    
     *    
     * @return  the dealTime    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="交易时间", align=2, sort=13)
    public String getDealTime() {
        return dealTime;
    }
    /**    
     * @param dealTime the dealTime to set    
     */
    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
    /**    
     * zstotalBalance    
     *    
     * @return  the zstotalBalance    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="赠送金额消费", align=2, sort=9)
    public String getZstotalBalance() {
        return zstotalBalance;
    }
    /**    
     * @param zstotalBalance the zstotalBalance to set    
     */
    public void setZstotalBalance(String zstotalBalance) {
        this.zstotalBalance = zstotalBalance;
    }
    /**    
     * dealUser    
     *    
     * @return  the dealUser    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    public String getDealUser() {
        return dealUser;
    }
    /**    
     * @param dealUser the dealUser to set    
     */
    public void setDealUser(String dealUser) {
        this.dealUser = dealUser;
    }
    /**    
     * cash    
     *    
     * @return  the cash    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    @ExcelField(title="现金消费", align=2, sort=10)
    public BigDecimal getCash() {
        return cash;
    }
    /**    
     * @param cash the cash to set    
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }
    /**    
     * consumePoint    
     *    
     * @return  the consumePoint    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */    
    @ExcelField(title="积分消费", align=2, sort=11)
    public BigDecimal getConsumePoint() {
        return consumePoint;
    }
    /**    
     * @param consumePoint the consumePoint to set    
     */
    public void setConsumePoint(BigDecimal consumePoint) {
        this.consumePoint = consumePoint;
    }
    
}
