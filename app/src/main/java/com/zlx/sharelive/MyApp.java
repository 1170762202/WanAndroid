package com.zlx.sharelive;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.zlx.module_base.BaseApplication;
import com.zlx.module_base.base_util.LogUtils;
import com.zlx.module_base.config.ModuleLifecycleConfig;
import com.zlx.module_network.util.LogUtil;


/**
 * @date: 2019\3\8 0008
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class MyApp extends BaseApplication implements Application.ActivityLifecycleCallbacks {


    @Override
    public void onCreate() {
        super.onCreate();

        ModuleLifecycleConfig.getInstance().initModuleAhead(this);

        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        LogUtils.i("onActivityCreated="+activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtils.i("onActivityStarted="+activity.getLocalClassName());

    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtils.i("onActivityResumed="+activity.getLocalClassName());

    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtils.i("onActivityPaused="+activity.getLocalClassName());

    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtils.i("onActivityStopped="+activity.getLocalClassName());

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        LogUtils.i("onActivitySaveInstanceState="+activity.getLocalClassName());

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.i("onActivityDestroyed="+activity.getLocalClassName());

    }
}
