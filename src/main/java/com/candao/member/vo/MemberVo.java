package com.candao.member.vo;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.candao.member.utils.ExcelField;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：MemberVo    
 * 类描述：会员实例    
 * 创建人：dengchao    
 * 创建时间：2016年6月23日 下午4:43:33    
 * 修改人：   
 * 修改时间：   
 * 修改备注：    
 * @version     
 *     
 */
public class MemberVo implements Serializable {

    private static final long serialVersionUID = -5399070731253601264L;

    public String memberCardNo; //会员卡号 

    public String memberName; //客户名称  

    public String phone; //手机号

    public String sex; //性别  

    public String birth; //出生日期   

    public String address; //住址  

    public String memberCardName; //会员卡名称  

    public String chargeAmount; //累计充值金额  

    public String presentAmount; //累计赠送金额  

    public String balanceAmount; //余额  

    public String totalPoint; //累计积分   

    public String currentPoint; //当前积分    

    public String debt; //挂帐/欠款  

    public String creatorEmployeeNo; //开卡员工编号 

    public String createDate; //开卡时间   

    public String cardStatus; //会员卡状态   

    public String storeNo; //门店编号

    @NotEmpty(message="会员卡号不能为空")
    @Pattern(regexp="(^\\d{1,30}$)",message="卡号格式不正确")
    @ExcelField(title="会员卡号", align=2, sort=1)
    public String getMemberCardNo() {
        return memberCardNo;
    }

    
    public void setMemberCardNo(String memberCardNo) {
        this.memberCardNo = memberCardNo;
    }

    
    @ExcelField(title="客户名称", align=2, sort=2)
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Pattern(regexp="(^$|^1(3[0-9]|4[57]|5[0-9]|7[0678]|8[0-9])\\d{8}$)",message="手机号格式不正确")
    @ExcelField(title="手机号", align=2, sort=3)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ExcelField(title="性别", align=2, sort=4)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @ExcelField(title="出生日期", align=2, sort=5)
    public String getBirth() {
        return birth;
    }

    
    public void setBirth(String birth) {
        this.birth = birth;
    }

    @ExcelField(title="住址", align=2, sort=6)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotEmpty(message="会员卡名称不能为空")
    @ExcelField(title="会员卡名称", align=2, sort=7)
    public String getMemberCardName() {
        return memberCardName;
    }

    public void setMemberCardName(String memberCardName) {
        this.memberCardName = memberCardName;
    }
    
    @Pattern(regexp="(^$|^[1-9]\\d{0,5}|0)([.]?|(\\.\\d{1,2})?$)",message="累计充值金额")
    @ExcelField(title="累计充值金额", align=2, sort=8)
    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @Pattern(regexp="(^$|^[1-9]\\d{0,5}|0)([.]?|(\\.\\d{1,2})?$)",message="累计赠送金额")
    @ExcelField(title="累计赠送金额", align=2, sort=9)
    public String getPresentAmount() {
        return presentAmount;
    }

    public void setPresentAmount(String presentAmount) {
        this.presentAmount = presentAmount;
    }

    @Pattern(regexp="(^$|^[1-9]\\d{0,5}|0)([.]?|(\\.\\d{1,2})?$)",message="余额")
    @ExcelField(title="余额", align=2, sort=10)
    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @ExcelField(title="累计积分", align=2, sort=11)
    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

    @ExcelField(title="当前积分", align=2, sort=12)
    public String getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(String currentPoint) {
        this.currentPoint = currentPoint;
    }
    
    @ExcelField(title="挂帐/欠款  ", align=2, sort=13)
    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    @ExcelField(title="开卡员工编号", align=2, sort=14)
    public String getCreatorEmployeeNo() {
        return creatorEmployeeNo;
    }

    public void setCreatorEmployeeNo(String creatorEmployeeNo) {
        this.creatorEmployeeNo = creatorEmployeeNo;
    }

    @ExcelField(title="开卡时间", align=2, sort=15)
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Pattern(regexp="^[0-9]*$",message="输入不合法，请输入数字")
    @ExcelField(title="会员卡状态", align=2, sort=16)
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    @NotEmpty(message="门店编号不能为空")
    @ExcelField(title="门店编号", align=2, sort=17)
    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }
}
