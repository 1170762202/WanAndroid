package com.zlx.sharelive.base.base_fg;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.zlx.sharelive.base.base_mvp.presenter.BaseContact;

import androidx.annotation.Nullable;


/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public abstract class BaseMvpFg<P extends BaseContact.BasePresenter> extends BaseFg implements BaseContact.IViewData {

    protected P mPresenter;
    private ProgressDialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        attachView();
    }
    /**
     * 在子View中初始化Presenter
     *
     * @return
     */
    protected abstract P initPresenter();



    /**
     * 挂载view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 卸载view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onRequestStart() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getContext());
        }
        mDialog.show();
    }

    @Override
    public void onRequestError(String s) {

    }

    @Override
    public void onRequestFinished() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();

    }
}
