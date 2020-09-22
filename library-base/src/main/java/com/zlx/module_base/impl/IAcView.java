package com.zlx.module_base.impl;

/**
 * Created by zlx on 2020/9/22 13:58
 * Email: 1170762202@qq.com
 * Description:
 */
public interface IAcView {
    void initViews();
    void initEvents();
    void beforeOnCreate();
    void afterOnCreate();
    void initImmersionBar();
}
