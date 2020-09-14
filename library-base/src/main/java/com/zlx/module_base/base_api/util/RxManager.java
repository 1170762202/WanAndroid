package com.zlx.module_base.base_api.util;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zlx on 2017/12/12.
 * 用于管理Rxjava 注册订阅和取消订阅
 */
public class RxManager {
    private static CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者

    public static void register(Disposable d) {
        mCompositeDisposable.add(d);
    }

    public static void unSubscribe() {
        mCompositeDisposable.dispose();// 取消订阅
    }
}