package com.zlx.module_base.base_fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;


import com.zlx.module_base.base_api.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * api2
 * @param <VM>
 */
@Deprecated
public abstract class BaseHttpFg<VM extends BaseViewModel> extends BaseFg {


    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVM();

    }

    private void initVM() {
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                modelClass = BaseViewModel.class;
            }
//            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
            viewModel = (VM) new ViewModelProvider(this).get(modelClass);
        }
    }
}
