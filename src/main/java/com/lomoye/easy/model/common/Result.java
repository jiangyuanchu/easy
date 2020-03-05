package com.lomoye.easy.model.common;

import java.io.Serializable;

public class Result implements Serializable {
    private String resultCode;
    private String resultMessage;
    private Object extra; //额外的一些信息

    public boolean isSuccess() {
        return "0".equals(resultCode);
    }

    public Result() {
        resultCode = "0";
        resultMessage = "处理成功";
    }

    public Result(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public void setCodeAndMsg(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
