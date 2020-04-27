package com.zlx.sharelive.base.base_mvp.mobdel;

/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public interface IBaseModelListener {
    /**
     * 请求成功
     */
    void onSuccess(String s);

    /**
     * 网络异常
     */
    void onError(String s);
}
