package com.zlx.module_base.impl;

import android.view.View;

/**
 * FileName: INetView
 * Created by zlx on 2020/9/22 10:38
 * Email: 1170762202@qq.com
 * Description:
 */
public interface INetView {

    void showLoading();

    void showLoading(View view);

    void showSuccess();

    void showEmpty();

    void onRetryBtnClick();

}
