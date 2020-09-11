package com.zlx.module_base.base_api;

import androidx.lifecycle.Observer;

import com.zlx.module_base.base_api.bean.ApiResponse;

public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onChanged(Object o) {
        if (o instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) o;
            if (apiResponse.errorCode == ApiResponse.CODE_SUCCESS) {
                onSuccess((T) o);
            } else {
                onFail(apiResponse.errorMsg);
            }
        } else {
            onFail("系统繁忙");
        }
    }

    protected abstract void onSuccess(T data);

    protected abstract void onFail(String msg);
}
