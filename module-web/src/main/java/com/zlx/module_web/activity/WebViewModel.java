package com.zlx.module_web.activity;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zlx.module_base.viewmodel.BaseViewModel;

public class WebViewModel extends BaseViewModel<WebRepository> {
    public WebViewModel(@NonNull Application application) {
        super(application);
    }
}
