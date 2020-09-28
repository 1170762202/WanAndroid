package com.zlx.module_network.widget.popwindow;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zlx on 2020/9/28 9:34
 * Email: 1170762202@qq.com
 * Description: 全局通用弹窗工具类
 */
public class PopUtil {

    public static void show(String msg) {
        show(msg, null);
    }

    public static void show(String msg, OnPopCallBack onPopCallBack) {
        CommonPop commonPop = new CommonPop(msg);
        commonPop.showPopupWindow();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (commonPop != null && commonPop.isShowing()) {
                    commonPop.dismiss();
                }
                if (onPopCallBack != null) {
                    onPopCallBack.dismiss();
                }
            }
        }, 2000);
    }

    public interface OnPopCallBack {
        void dismiss();
    }
}
