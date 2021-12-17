package com.zlx.module_mine.language;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_util.LanguageUtil;
import com.zlx.module_mine.bean.LanguageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageViewModel extends BaseTopBarViewModel {
    public MutableLiveData<List<LanguageBean>> languageLiveData = new MutableLiveData<>();

    public LanguageViewModel(@NonNull Application application) {
        super(application);
    }


    public void getData() {
        List<LanguageBean> list = new ArrayList<>();
        list.add(new LanguageBean(Locale.SIMPLIFIED_CHINESE, "简体中文"));
        list.add(new LanguageBean(Locale.US, "英文"));
        Locale currentLanguage = LanguageUtil.getCurrentLanguage();
        for (LanguageBean languageBean : list) {
            if (currentLanguage.equals(languageBean.getLocale())) {
                languageBean.setPress(true);
                break;
            }
        }
        languageLiveData.postValue(list);
    }
}
