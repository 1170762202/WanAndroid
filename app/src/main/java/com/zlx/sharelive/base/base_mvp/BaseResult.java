package com.zlx.sharelive.base.base_mvp;
/**
*@date: 2019\2\25 0025
*@author: zlx
*@description:
*
*/
public class BaseResult {

    private String msg;
    private int code;


    public BaseResult() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
