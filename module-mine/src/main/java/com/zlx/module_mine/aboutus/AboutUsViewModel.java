package com.zlx.module_mine.aboutus;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.constant.C;

public class AboutUsViewModel extends BaseTopBarViewModel {
    public AboutUsViewModel(@NonNull Application application) {
        super(application);
    }

    public void onOfficialClick() {
        RouterUtil.launchWeb(C.WAN_ANDROID);

    }

    public void onNetClick() {
        RouterUtil.launchWeb(C.URL_ABOUT);

    }

    public void onLibClick() {
        RouterUtil.launchWeb(C.SOURCE_URL);

    }
}
