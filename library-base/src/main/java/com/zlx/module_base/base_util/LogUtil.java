package com.zlx.module_base.base_util;

import android.util.Log;

import com.zlx.module_base.BuildConfig;


/**
 * Created by Zlx on 2017/5/3.
 */
public class LogUtil {
    private static final String TAG = "TAG";
    public final static int logSubLenth = 3000;//每行log长度

    public static void show(String content) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, content);
        }
    }

    public static void logSplit(String explain, String message, int i) {
        //TODO 添加非debug下不打印日志
        //        if (!BuildConfig.DEBUG) return;
        if (i > 10) return;
        if (message.length() <= logSubLenth) {
            Log.i(explain, "logSplit=" + explain + i + "：     " + message);
            return;
        }

        String msg1 = message.substring(0, logSubLenth);
        Log.i(explain, "logSplit=" + explain + i + "：     " + msg1);
        String msg2 = message.substring(logSubLenth);
        logSplit(explain, msg2, ++i);
    }
}
