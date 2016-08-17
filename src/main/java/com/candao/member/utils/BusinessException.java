/**
 * 
 */
package com.candao.member.utils;


/**
 * 业务操作异常，必须初始化异常消息。
 * @author zhao
 *
 */
public class BusinessException extends RuntimeException {
    private String errorType;
    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
