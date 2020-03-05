package com.lomoye.easy.exception;

/**
 * @author tommy on 2017/6/8.
 * 业务异常类.
 */
public class BusinessException extends RuntimeException {
    private String errorCode; //出错的errorCode

    private String errorMessage; //出错的message

    public BusinessException() {
        this("", "");
    }

    public BusinessException(String errorCode) {
        this(errorCode, "");
    }

    public BusinessException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.errorCode = "";
        this.errorMessage = cause.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "|BusinessException|errorCode=" + errorCode + "|errorMessage=" + errorMessage;
    }

    @Override
    public String getMessage() {
        return "|BusinessException|errorCode=" + errorCode + "|errorMessage=" + errorMessage;
    }
}

