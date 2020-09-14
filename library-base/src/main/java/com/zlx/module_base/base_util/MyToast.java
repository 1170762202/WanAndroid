package com.zlx.module_base.base_util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zlx.module_base.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Zlx on 2018/5/8/008.
 * Email:1170762202@qq.com
 */
public class MyToast {
    private Toast mToast;
    private int duration=2000;
    private MyToast(Context context, CharSequence text) {
        if (context == null){
            return;
        }
        View v = LayoutInflater.from(context).inflate(R.layout.toast, null);
        v.setBackground(context.getResources().getDrawable(R.drawable.toast));

        TextView textView = (TextView) v.findViewById(R.id.textView1);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setView(v);
    }

    public static MyToast makeText(Context context, CharSequence text) {
        return new MyToast(context, text);
    }
    public void show() {
        if (mToast != null) {
            mToast.setGravity(Gravity.CENTER,0,0);
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mToast.show();
                }
            }, 0, 3000);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mToast.cancel();
                    timer.cancel();
                }
            }, duration);

        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
