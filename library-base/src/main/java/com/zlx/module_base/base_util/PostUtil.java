package com.zlx.module_base.base_util;

import android.os.Handler;
import android.os.Looper;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;

/**
 * FileName: PostUtil
 * Created by zlx on 2020/9/17 18:03
 * Email: 1170762202@qq.com
 * Description:
 */
public class PostUtil{
        private static final int DELAY_TIME = 1000;

        public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz) {
            postCallbackDelayed(loadService, clazz, DELAY_TIME);
        }

        public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz, long
                delay) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadService.showCallback(clazz);
                }
            }, delay);
        }

        public static void postSuccessDelayed(final LoadService loadService) {
            postSuccessDelayed(loadService, DELAY_TIME);
        }

        public static void postSuccessDelayed(final LoadService loadService, long delay) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadService.showSuccess();
                }
            }, delay);
        }
}