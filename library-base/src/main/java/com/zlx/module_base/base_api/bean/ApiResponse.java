package com.zlx.module_base.base_api.bean;

/**
 * @date: 2020\7\24 0024
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class ApiResponse<T> {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 1;

    public int errorCode; //状态码
    public String errorMsg; //信息
    public T data; //数据

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
}