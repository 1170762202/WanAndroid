package com.zlx.module_base.base_util;

import android.widget.Toast;

/**
 * Created by Zlx on 2017/5/3.
 */
public class ToastUtil {
    public static void showShort(String content) {
        MyToast.makeText(UtilsBridge.getApplicationByReflect(), content).show();

    }

    public static void showLong(String content) {
        Toast.makeText(UtilsBridge.getApplicationByReflect(), content, Toast.LENGTH_LONG).show();
    }
}
