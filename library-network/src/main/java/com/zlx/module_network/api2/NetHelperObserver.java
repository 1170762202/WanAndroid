package com.zlx.module_network.api2;

import android.util.Log;

import androidx.lifecycle.LifecycleObserver;

import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.util.RxExceptionUtil;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class NetHelperObserver<T extends ApiResponse> implements Observer<T>, LifecycleObserver {

    private final NetCallback<T> mCallback;

    public NetHelperObserver(NetCallback<T> callback) {
        mCallback = callback;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (mCallback != null) {
            mCallback.onSubscribe(d);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        try {
            if (mCallback != null) {
                mCallback.getClass()
                        .getMethod(t.isSuccess() ? "onSuccess" : "onFail", new Class[]{Object.class})
                        .invoke(mCallback, t);
            }
        } catch (Exception e) {
            if (mCallback != null) {
                mCallback.onError(e.getMessage());
            }
        }
    }

    @Override
    public void onError(@NonNull Throwable t) {
        Log.e("请求错误", Objects.requireNonNull(t.getMessage()));
        if (mCallback != null) {
            mCallback.onError(RxExceptionUtil.exceptionHandler(t));
        }
    }

    @Override
    public void onComplete() {

    }
}