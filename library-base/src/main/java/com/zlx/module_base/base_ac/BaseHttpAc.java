package com.zlx.module_base.base_ac;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * Created by Administrator on 2019\2\25 0025.
 */

public abstract class BaseHttpAc<T extends ViewModel> extends BaseAc {

    protected T viewModel = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = bindViewModel();
        super.onCreate(savedInstanceState);
    }

    protected abstract T bindViewModel();

}
