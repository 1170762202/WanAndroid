package com.zlx.sharelive.base.base_mvp.presenter;

import java.util.Map;

/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public interface BaseContact {

    interface BasePresenter<T extends BaseView> {
        void attachView(T view);

        void detachView();
    }

    interface BaseView {
        void onRequestStart();

        void onRequestSuccess(String s);

        void onRequestFailed(String s);

        void onRequestError(String s);

        void onRequestFinished();
    }

    interface IViewData extends BaseView {
        Map<String, Object> getRequestMap();
    }
}
