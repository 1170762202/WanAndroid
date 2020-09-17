package com.zlx.module_network.util;

/**
 * @date: 2019\3\22 0022
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */

import android.net.ParseException;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.Objects;

import retrofit2.HttpException;

/**
 * 异常处理
 */
public class RxExceptionUtil {
    public static String exceptionHandler(Throwable t){
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        } else if (t instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) t;
            Log.e("错误", Objects.requireNonNull(exception.getMessage()));
            msg = exception.getMessage();
        } else if (t instanceof UnknownServiceException) {
            UnknownServiceException exception = (UnknownServiceException) t;
            msg = exception.getMessage();
        } else if (t instanceof NetworkOnMainThreadException) {
            NetworkOnMainThreadException exception = (NetworkOnMainThreadException) t;
            msg = "网络请求在主线程";
        }
        return msg;
    }

    private static String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
