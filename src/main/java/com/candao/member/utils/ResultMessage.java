package com.candao.member.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**    
 *     
 * 项目名称：member    
 * 类名称：ResultMessage    
 * 类描述：返回结果集    
 * 创建人：dengchao    
 * 创建时间：2016年6月23日 上午9:55:31    
 * 修改人：   
 * 修改时间：
 * 修改备注：    
 * @version     
 *     
 */
public class ResultMessage implements Serializable{

    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     * @since Ver 1.1    
     */    
    
    @JsonIgnore
    private static final long serialVersionUID = 6338893473708366205L;

    private String code;
    
    private String successNum;
    
    private String errorNum;

    private String message;
    
    private Object successResult;
    
    private Object errorResult;

    
    /**    
     * code    
     *    
     * @return  the code    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public String getCode() {
        return code;
    }

    /**    
     * @param code the code to set    
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**    
     * successNum    
     *    
     * @return  the successNum    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public String getSuccessNum() {
        return successNum;
    }

    /**    
     * @param successNum the successNum to set    
     */
    public void setSuccessNum(String successNum) {
        this.successNum = successNum;
    }

    /**    
     * errorNum    
     *    
     * @return  the errorNum    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public String getErrorNum() {
        return errorNum;
    }

    /**    
     * @param errorNum the errorNum to set    
     */
    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    /**    
     * message    
     *    
     * @return  the message    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public String getMessage() {
        return message;
    }

    /**    
     * @param message the message to set    
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**    
     * successResult    
     *    
     * @return  the successResult    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public Object getSuccessResult() {
        return successResult;
    }

    /**    
     * @param successResult the successResult to set    
     */
    public void setSuccessResult(Object successResult) {
        this.successResult = successResult;
    }

    /**    
     * errorResult    
     *    
     * @return  the errorResult    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public Object getErrorResult() {
        return errorResult;
    }

    /**    
     * @param errorResult the errorResult to set    
     */
    public void setErrorResult(Object errorResult) {
        this.errorResult = errorResult;
    }

    public ResultMessage() {
    }

    public ResultMessage(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    
    public ResultMessage(String code, String message,Object successResult,Object errorResult) {
        super();
        this.code = code;
        this.message = message;
        this.successResult=successResult;
        this.errorResult=errorResult;
    }

    public static ResultMessage newResultMessage(String code, String message) {
        return new ResultMessage(code, message);
    }

    public ResultMessage(String code,String successNum,String errorNum, String message,Object successResult,Object errorResult) {
        super();
        this.code = code;
        this.successNum=successNum;
        this.errorNum=errorNum;
        this.message = message;
        this.successResult=successResult;
        this.errorResult=errorResult;
    }
}
