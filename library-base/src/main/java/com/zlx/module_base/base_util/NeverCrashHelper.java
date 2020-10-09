package com.zlx.module_base.base_util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zlx on 2020/10/9 17:31
 * Email: 1170762202@qq.com
 * Description:
 */
public class NeverCrashHelper {
    private CrashHandler mCrashHandler;

    private static NeverCrashHelper mInstance;

    private NeverCrashHelper() {

    }

    private static NeverCrashHelper getInstance() {
        if (mInstance == null) {
            synchronized (NeverCrashHelper.class) {
                if (mInstance == null) {
                    mInstance = new NeverCrashHelper();
                }
            }
        }
        return mInstance;
    }

    public static void init(CrashHandler crashHandler) {
        getInstance().setCrashHandler(crashHandler);
    }

    private void setCrashHandler(CrashHandler crashHandler) {
        mCrashHandler = crashHandler;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (mCrashHandler != null) {//捕获异常处理
                            mCrashHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (mCrashHandler != null) {//捕获异常处理
                    mCrashHandler.uncaughtException(t, e);
                }
            }
        });
    }

    public interface CrashHandler {
        void uncaughtException(Thread t, Throwable e);
    }
}
