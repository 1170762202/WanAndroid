package com.zlx.widget.slide_close;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * BamActivity触摸工具类
 * <p/>
 * Created by Bamboy on 2018/10/24.
 */
public class UtilBaseGesture {

    /**
     * 起始触点的X轴范围
     * 大于这个范围则不会触发右滑关闭
     */
    public final static int SLIDE_SCREEN_WIDTH_SCALE = 30;

    private Activity mActivity;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 滑动起点
     */
    private int startSlidingX;
    /**
     * 界面移动距离
     */
    private int move;
    /**
     * 移动最大值
     */
    private int maxMove;
    /**
     * 移动与滑动的比例
     * 同样滑动距离的情况下，比例越大，界面移动越小
     */
    private float moveScaling;

    /**
     * Activity的Layout的容器
     */
    private ViewGroup rootGroup;
    /**
     * Activity的Layout的根View
     * 【滑动过程中会移动的View】
     */
    private View rootView;
    /**
     * 滑动进度条
     */
    private SlopeProgress mProgress;
    /**
     * 进度条宽度
     */
    private int progressWidth;

    /**
     * 滑动关闭开关
     */
    private boolean slideOpen = true;
    /**
     * 抬起关闭
     * 【true：手指抬起后再关闭页面】
     * 【false：进度条圆满就立刻关闭页面】
     */
    private boolean upFinish = true;

    /**
     * 构造
     *
     * @param activity
     */
    public UtilBaseGesture(Activity activity) {
        this.mActivity = activity;

        // 获取根布局，用于右滑关闭
        rootGroup = activity.findViewById(android.R.id.content);
        rootView = rootGroup.getChildAt(0);

        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        mScreenWidth = Math.min(width, height);
        maxMove = (int) (mScreenWidth * 0.06);
        moveScaling = 0.2f;
        progressWidth = mScreenWidth / 9;
    }

    /**
     * 初始化进度条
     */
    private void initProgress() {
        mProgress = new SlopeProgress(mActivity);
        mProgress.setMaxProgress(100);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(progressWidth, progressWidth);
        mProgress.setLayoutParams(params);

        if (rootGroup != null)
            rootGroup.addView(mProgress);
    }

    //==============================================================================================
    //======================= 以 下 是 关 于 属 性 方 法 =============================================
    //==============================================================================================

    /**
     * 开启滑动关闭界面
     *
     * @param open
     */
    public void openSlideFinish(boolean open) {
        slideOpen = open;
    }

    /**
     * 抬起关闭
     *
     * @param upFinish 【true：手指抬起后再关闭页面】
     *                 【false：进度条圆满就立刻关闭页面】
     */
    public void setUpFinish(boolean upFinish) {
        this.upFinish = upFinish;
    }

    /**
     * Activity的Layout的根View
     * 【滑动过程中会移动的View】
     */
    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    /**
     * 获取移动距离
     *
     * @return
     */
    public int getMove() {
        return move;
    }

    /**
     * 设置进度条颜色
     */
    public void setProgressColor(int color) {
        if (mProgress == null)
            initProgress();

        if (mProgress != null)
            mProgress.setRingColor(color);
    }

    /**
     * 设置移动距离
     *
     * @param move
     */
    public void setMove(int move) {

        if (move > maxMove)
            move = (int) (maxMove + (move - maxMove) * 0.5f);

        rootView.setX(move);
        this.move = move;

        if (move > maxMove)
            move = maxMove;

        // 更新进度条
        mProgress.setProgress((int) (101 * ((float) move / (float) maxMove)));

        // 判断是否关闭Activity
        if (false == upFinish && mProgress.getProgress() >= mProgress.getMaxProgress()) {
            finish();
        }
    }

    //==============================================================================================
    //======================= 以 下 是 关 于 手 势 触 摸 计 算 ========================================
    //==============================================================================================

    /**
     * 处理触摸事件
     *
     * @param motionEvent 触摸事件
     */
    public boolean motionEvent(MotionEvent motionEvent) {
        if (false == slideOpen) {
            return false;
        }
        try {
            // 触摸监听，用于监听手指运动
            switch (motionEvent.getAction()) {
                // 手指按下
                case MotionEvent.ACTION_DOWN:
                    return actionDown(motionEvent);

                // 手指移动
                case MotionEvent.ACTION_MOVE:
                    return actionMove(motionEvent);

                // 手指抬起
                case MotionEvent.ACTION_UP:
                    return actionUp(motionEvent);

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 手指按下
     *
     * @param motionEvent
     * @return
     */
    private boolean actionDown(MotionEvent motionEvent) {
        // 计算X轴
        startSlidingX = (int) motionEvent.getX();
        if (false == slideOpen ||
                rootView == null ||
                startSlidingX > mScreenWidth / SLIDE_SCREEN_WIDTH_SCALE) {
            return false;
        }

        // 添加进度条
        if (mProgress == null) {
            // 初始化进度条
            initProgress();
        }

        // 设置背景色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && rootView.getBackground() != null) {
            rootGroup.setBackground(rootView.getBackground());
        }

        return true;
    }

    /**
     * 手指移动
     *
     * @param motionEvent
     * @return
     */
    private boolean actionMove(MotionEvent motionEvent) {
        float nowX = motionEvent.getX();
        if (false == slideOpen ||
                rootView == null ||
                startSlidingX > mScreenWidth / SLIDE_SCREEN_WIDTH_SCALE) {
            return false;
        }

        // 计算滑动距离
        float nowMove = (int) ((nowX > startSlidingX ? nowX - startSlidingX : 0) * moveScaling);

        // 更新移动位置
        setMove((int) nowMove);

        // 进度条的Y轴跟随手指，
        // 这里减去（mProgress.getHeight() * 0.7f）
        // 是因为手指触摸时实际触点和视觉触点不一致，所以要减小这个视觉误差
        mProgress.setTranslationY(motionEvent.getY() - mProgress.getHeight() * 0.7f);
        return true;
    }

    /**
     * 手指抬起
     *
     * @param motionEvent
     * @return
     */
    private boolean actionUp(MotionEvent motionEvent) {
        if (false == slideOpen
                || rootView == null
                || rootView.getX() == 0
                || startSlidingX == mScreenWidth)
            return false;

        // 判断是否关闭Activity
        if (upFinish && mProgress.getProgress() >= mProgress.getMaxProgress()) {
            finish();
        } else {
            ObjectAnimator.ofInt(this, "move", getMove(), 0).setDuration(200).start();
        }
        return true;
    }

    private void finish() {
        slideOpen = false;
        startSlidingX = mScreenWidth;
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "move", getMove(), 0);
        anim.setStartDelay(100);
        anim.setDuration(200);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                slideOpen = true;
            }
        });
        anim.start();

        mActivity.finish();
    }
}
