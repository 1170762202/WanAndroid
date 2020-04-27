package com.zlx.sharelive.base.base_mvp.presenter;


import com.zlx.sharelive.util.LogUtil;

/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public class BasePresenter<T extends BaseContact.BaseView> implements BaseContact.BasePresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            LogUtil.e("detach");
            mView = null;
        }
    }
}
