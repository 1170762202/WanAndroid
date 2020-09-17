package com.zlx.module_network.bean;

/**
 * @date: 2020\7\24 0024
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class ApiResponse<T> {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 1;

    private int errorCode; //状态码
    private String errorMsg; //信息
    private T data; //数据

    public ApiResponse(int code, String msg) {
        this.errorCode = code;
        this.errorMsg = msg;
        this.data = null;
    }

    public ApiResponse(int code, String msg, T data) {
        this.errorCode = code;
        this.errorMsg = msg;
        this.data = data;
    }

    public static int getCodeSuccess() {
        return CODE_SUCCESS;
    }

    public static int getCodeError() {
        return CODE_ERROR;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        if (errorCode == 0) {
            return true;
        } else {
            return false;
        }
    }
}