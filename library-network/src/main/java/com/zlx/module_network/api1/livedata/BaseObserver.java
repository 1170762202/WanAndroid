package com.zlx.module_network.api1.livedata;

import com.zlx.module_base.BaseApplication;
import com.zlx.module_base.base_util.ToastUtil;
import com.zlx.module_network.bean.ApiResponse;

public class BaseObserver<T> implements IBaseObserver<T> {

    private BaseObserverCallBack<T> baseObserverCallBack;

    public BaseObserver(BaseObserverCallBack<T> baseObserverCallBack) {
        this.baseObserverCallBack = baseObserverCallBack;
    }

    @Override
    public void onChanged(T t) {
        if (t instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) t;
            if (apiResponse.isSuccess()) {
                baseObserverCallBack.onSuccess(t);
            } else {
                baseObserverCallBack.onFail(apiResponse.getErrorMsg());
                if (baseObserverCallBack.showErrorMsg()) {
                    ToastUtil.showShort(BaseApplication.getInstance(), apiResponse.getErrorMsg());
                }
            }
        } else {
            baseObserverCallBack.onFail("系统繁忙!");
        }
        baseObserverCallBack.onFinish();
    }
}
