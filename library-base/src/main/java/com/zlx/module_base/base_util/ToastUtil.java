package com.zlx.module_base.base_util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Zlx on 2017/5/3.
 */
public class ToastUtil {
    public static void showShort(Context context, String content) {
//        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        MyToast.makeText(context, content).show();
    }

    public static void showLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
