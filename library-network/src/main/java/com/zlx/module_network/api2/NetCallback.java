package com.zlx.module_network.api2;


import com.zlx.module_network.bean.ApiResponse;

public interface NetCallback<T extends ApiResponse> {

    void onSuccess(T response);

    void onFail(String msg);
}
