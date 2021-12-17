package com.zlx.module_network.factory;

import androidx.annotation.NonNull;

import com.zlx.module_network.bean.ApiResponse;

public interface ApiCallback<T> {

    /**
     * 开始加载
     */
    void onStart();

    /**
     * 加载成功
     *
     * @param response 接口回调
     */
    void onSuccess(@NonNull ApiResponse<T> response);

    /**
     * 加载失败
     *
     * @param t 异常
     */
    void onError(@NonNull Throwable t);
}
