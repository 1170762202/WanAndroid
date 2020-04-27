package com.zlx.sharelive.base.event;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: Zlx on 2018/7/11/011.
 * Email:1170762202@qq.com
 */
public class OnDoubleClickListener implements View.OnTouchListener {
    private final String TAG = this.getClass().getSimpleName();
    private int count = 0;
    private long firClick = 0;
    private long secClick = 0;
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int interval = 500;
    private DoubleClickCallback mCallback;

    public interface DoubleClickCallback {
        void onDoubleClick();
        void onClick();
    }

    public OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            count++;
            if (1 == count) {
                firClick = System.currentTimeMillis();
                if (mCallback != null) {
                    mCallback.onClick();
                } else {
                    Log.e(TAG, "请在构造方法中传入一个双击回调");
                }
            } else if (2 == count) {
                secClick = System.currentTimeMillis();
                if (secClick - firClick < interval) {
                    if (mCallback != null) {
                        mCallback.onDoubleClick();
                    } else {
                        Log.e(TAG, "请在构造方法中传入一个双击回调");
                    }
                    count = 0;
                    firClick = 0;
                } else {
                    firClick = secClick;
                    count = 1;
                    if (mCallback != null) {
                        mCallback.onClick();
                    } else {
                        Log.e(TAG, "请在构造方法中传入一个双击回调");
                    }
                }
                secClick = 0;
            }
        }
        return false;
    }
}
