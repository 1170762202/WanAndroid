package com.zlx.sharelive.base.base_ac;

import android.os.Bundle;


import com.zlx.sharelive.base.base_mvp.NewBasePresenter;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @date: 2019\2\26 0026
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public abstract class BaseMvpAc extends BaseAc {


    private List<? extends NewBasePresenter> presenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenters = getPresenters();
    }

    protected abstract List<? extends NewBasePresenter> getPresenters();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (NewBasePresenter presenter : presenters) {
            presenter.detachView();
        }

    }
}
