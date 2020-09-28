package com.zlx.library_common.provier;

import android.app.Application;

/**
 * FileName: AppBridge
 * Created by zlx on 2020/9/22 10:22
 * Email: 1170762202@qq.com
 * Description:
 */
public class AppBridge {

    public static Application getApplicationByReflect() {
        return ActivityLifecycleImpl.INSTANCE.getApplicationByReflect();
    }
}
