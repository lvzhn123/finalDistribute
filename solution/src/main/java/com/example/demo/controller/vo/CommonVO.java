package com.example.demo.controller.vo;

/**
 * 通用VO
 */
public class CommonVO {

    /**
     * 错误码
     */
    private int resultCode = 0;

    /**
     * 错误信息
     */
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}
