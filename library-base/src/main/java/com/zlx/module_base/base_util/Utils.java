package com.zlx.module_base.base_util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * FileName: Utils
 * Created by zlx on 2020/9/22 10:17
 * Email: 1170762202@qq.com
 * Description:
 */
public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Application app;


    public static Application getApp() {
        if (app == null) {
            app = UtilsBridge.getApplicationByReflect();
        }
        return app;
    }
}
