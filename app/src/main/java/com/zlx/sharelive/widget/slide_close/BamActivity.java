package com.zlx.sharelive.widget.slide_close;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zlx.sharelive.R;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity基类
 * <p>
 * Created by Bamboy on 2018/10/24.
 */
public class BamActivity extends AppCompatActivity {
    /**
     * 触摸工具类
     */
    private UtilBaseGesture mUtilGesture;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        /**
         * 如果是Android 4.4 以上，就兼容沉浸式
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                // 初始化状态栏
                setImmerseTitleBar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 手势工具类
        mUtilGesture = new UtilBaseGesture(this);
    }

    //==============================================================================================
    //======================= 以 下 是 关 于 沉 浸 式 状 态 栏 ========================================
    //==============================================================================================

    /**
     * 设置沉浸TitleBar
     */
    private void setImmerseTitleBar() {

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // Android 7.0以上 去除状态栏半透明底色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(window.getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return 状态栏高度
     */
    public int getBarHeight(Context context) {
        int barHeight = -1;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            barHeight = 0;
        }

        if (barHeight == -1) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0;

            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                barHeight = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
                return 0;
            }
        }
        return barHeight;
    }

    //==============================================================================================
    //======================= 以 下 是 关 于 手 势 右 滑 关 闭 ========================================
    //==============================================================================================

    /**
     * 绑定手势
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != mUtilGesture && mUtilGesture.motionEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开启滑动关闭界面
     *
     * @param open
     */
    protected void openSlideFinish(boolean open) {
        if (mUtilGesture == null) {
            return;
        }
        mUtilGesture.openSlideFinish(open);
    }

    /**
     * 抬起关闭
     *
     * @param upFinish 【true：手指抬起后再关闭页面】
     *                 【false：进度条圆满就立刻关闭页面】
     */
    public void setUpFinish(boolean upFinish) {
        if (mUtilGesture == null) {
            return;
        }
        mUtilGesture.setUpFinish(upFinish);
    }

    /**
     * 设置进度条颜色
     */
    public void setProgressColor(int color) {
        if (mUtilGesture != null)
            mUtilGesture.setProgressColor(color);
    }

    /**
     * 滑动View
     * 【滑动过程中会移动的View】
     */
    public void setMoveView(View SlideView) {
        mUtilGesture.setRootView(SlideView);
    }

    //==============================================================================================
    //======================= 以 下 是 关 于 界 面 跳 转 动 画 ========================================
    //==============================================================================================

    /**
     * 打开新Activity
     *
     * @param intent intent
     */
    public void startActivity(Intent intent) {
        startActivity(intent, true);
    }

    /**
     * 打开新Activity
     *
     * @param intent  intent
     * @param animIn  新Activity进入的动画
     * @param animOut 当前Activity退出的动画
     */
    public void startActivity(Intent intent, int animIn, int animOut) {
        super.startActivity(intent);
        overridePendingTransition(animIn, animOut);
    }

    /**
     * 打开新的Activity
     *
     * @param intent intent
     * @param isAnim 是否开启过渡动画
     */
    public void startActivity(Intent intent, boolean isAnim) {
        if (isAnim) {
            startActivity(intent, R.anim.act_right_in, R.anim.act_left_out);
        } else {
            super.startActivity(intent);
        }
    }


    @Override
    public void finish() {
        finish(true);
    }

    /**
     * 退出Activity
     *
     * @param animIn  老Activity进入的动画
     * @param animOut 当前Activity退出的动画
     */
    public void finish(int animIn, int animOut) {
        super.finish();
        overridePendingTransition(animIn, animOut);
    }

    /**
     * 退出Activity
     *
     * @param isAnim 是否开启过渡动画
     */
    public void finish(boolean isAnim) {
        if (isAnim) {
            finish(R.anim.act_left_in, R.anim.act_right_out);
        } else {
            super.finish();
        }
    }
}
