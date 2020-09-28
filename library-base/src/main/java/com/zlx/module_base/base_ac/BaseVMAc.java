package com.zlx.module_base.base_ac;

import androidx.lifecycle.ViewModelProvider;


import com.zlx.module_base.base_api.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2019\2\25 0025.
 * api2
 */

@Deprecated
public abstract class BaseVMAc<VM extends BaseViewModel> extends BaseAc {

    protected VM viewModel;

    @Override
    public void afterOnCreate() {
        super.afterOnCreate();
        initViewModel();
        getLifecycle().addObserver(viewModel);
    }

    private void initViewModel() {
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) new ViewModelProvider(this).get(modelClass);
        }
    }
}
