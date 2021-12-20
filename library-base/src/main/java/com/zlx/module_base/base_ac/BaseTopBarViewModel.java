package com.zlx.module_base.base_ac;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zlx.module_base.viewmodel.BaseViewModel;

public class BaseTopBarViewModel<M extends BaseModel> extends BaseViewModel<M> {
    /**
     * 兼容databinding，去泛型化
     */
    public BaseTopBarViewModel toolbarViewModel;
    public ObservableField<String> titleText = new ObservableField<>("WanAndroid123");

    public BaseTopBarViewModel(@NonNull Application application) {
        super(application);
        toolbarViewModel = this;
    }

    public void setTitleText(String title) {
        titleText.set(title);
    }
}
