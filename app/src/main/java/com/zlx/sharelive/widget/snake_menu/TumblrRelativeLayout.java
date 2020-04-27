package com.zlx.sharelive.widget.snake_menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zlx.sharelive.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * @date: 2019\3\6 0006
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class TumblrRelativeLayout extends RelativeLayout {

    private AnimateImageView topImageView;
    private List<AnimateImageView> imageViewList = new ArrayList<AnimateImageView>();
    private int[] imageIds = {R.drawable.icon_tab1_press, R.drawable.icon_beauty_pressed,
            R.drawable.icon_tab1_press, R.drawable.icon_tab1_press};
    private int[] buttonColors = {R.color.main, R.color.main, R.color.main, R.color.main};
    private int marginBottom = 40;
    private int marginRight = 40;

    /* 拖拽工具类 */
    private final ViewDragHelper mDragHelper;
    private GestureDetectorCompat gestureDetector;
    private ViewTrackController viewTrackController;

    public TumblrRelativeLayout(Context context) {
        this(context, null);
    }

    public TumblrRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TumblrRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        marginBottom = (int) getResources().getDimension(R.dimen.float_marginBottom);
        marginRight = (int) getResources().getDimension(R.dimen.float_marginRight);

        mDragHelper = ViewDragHelper
                .create(this, 10f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
        gestureDetector = new GestureDetectorCompat(context,
                new MoveDetector());

        viewTrackController = ViewTrackController.create();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 底部的圆形按钮 动态添加到布局中
        int len = imageIds.length;
        Resources res = getResources();
        for (int i = 0; i < len; i++) {
            // AnimateImageView的父类是FloatingActionButton，在android 5.0以下的手机中，会有重影现象
            // 其实父类不重要，只要能实现那个圆形的效果就好。
            // 美工扎实的同学，可以直接用ImageView去实现圆形按钮
            AnimateImageView imageView = new AnimateImageView(getContext());
            imageView.setImageResource(imageIds[i]);
            imageView.setBackgroundTintList(res.getColorStateList(buttonColors[i]));
            imageViewList.add(imageView);

            // 添加到RelativeLayout中去
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, marginRight, marginBottom);
            lp.addRule(ALIGN_PARENT_BOTTOM);
            lp.addRule(ALIGN_PARENT_RIGHT);
            addView(imageView, lp);

            // 如果不是最顶层的view，可以去除阴影
            if (i == len - 1) {
                topImageView = imageView;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setElevation(0);
                }
            }
        }

        // 初始化viewTrackController
        viewTrackController.init(imageViewList);
    }

    class MoveDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                                float dy) {
            return Math.abs(dy) + Math.abs(dx) > 5;
        }
    }

    /**
     * 这是拖拽效果的主要逻辑
     */
    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            viewTrackController.onTopViewPosChanged(left, top);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 只跟踪最顶层的view
            if (child == topImageView) {
                topImageView.stopAnimation();
                return true;
            }

            return false;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            // 这个用来控制拖拽过程中松手后，自动滑行的速度
            return 1;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 滑动松开后，交给ViewTrackController去处理
            viewTrackController.onRelease();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // 手指拖到哪是哪
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // 手指拖到哪是哪
            return left;
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewTrackController.setOriginPos(topImageView.getLeft(), topImageView.getTop());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // onClick的时候会有异常，在最初的时候，mDragHelper先释放一下
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mDragHelper.abort();
        }
        return super.dispatchTouchEvent(ev);
    }

    /* touch事件的拦截与处理都交给mDraghelper来处理 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean yScroll = gestureDetector.onTouchEvent(ev);
        boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        int action = ev.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            mDragHelper.processTouchEvent(ev);
        }

        return shouldIntercept && yScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // 统一交给mDragHelper处理，由DragHelperCallback实现拖动效果
        try {
            mDragHelper.processTouchEvent(e); // 该行代码可能会抛异常，正式发布时请将这行代码加上try catch
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public void setMenuListener(View.OnClickListener clickListener) {
        topImageView.setOnClickListener(clickListener);
    }
}