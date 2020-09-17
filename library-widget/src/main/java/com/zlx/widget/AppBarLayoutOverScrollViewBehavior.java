package com.zlx.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class AppBarLayoutOverScrollViewBehavior extends AppBarLayout.Behavior {
    private int mAppBarHeight;
    private View mCardView;
    private boolean isAnimate;
    private float mTotalDy;
    private float mLastScale;
    private int mLastBottom;
    private int mCardViewHeight;
    private int mLimitHeight;
    private View mToolBar;
    private float scaleValue = 2f / 3;// 显示卡片的三分之一 所以抛出三分之二
    private View mNameTitle;

    public AppBarLayoutOverScrollViewBehavior() {
    }

    public AppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        if (null == mCardView) {
            mCardView = parent.findViewById(R.id.cardview);
        }
        if (null == mToolBar) {
            mToolBar = parent.findViewById(R.id.toolbar);
        }
        if (null == mNameTitle) {
            mNameTitle = parent.findViewById(R.id.name);
        }

        init(abl);
        return handled;
    }


    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100) {
            isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        //恢复位置
        if (abl.getBottom() > mLimitHeight) {
            recovery(abl);
        }
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        //开始滚动了 就动画归位
        isAnimate = true;
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (mCardView != null && ((dy <= 0 && child.getBottom() >= mLimitHeight) || (dy > 0 && child.getBottom() > mLimitHeight))) {
            scrollY(child, target, dy);
        } else {
            setViewAlpha(child, dy);
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }


    /**
     * 初始化数据
     *
     * @param appBarLayout
     */
    private void init(final AppBarLayout appBarLayout) {
        appBarLayout.setClipChildren(false);
        //整个AppbarLayout高度
        mAppBarHeight = appBarLayout.getMeasuredHeight();
        //卡片的高度
        mCardViewHeight = mCardView.getMeasuredHeight();
        //折叠正常的高度
        mLimitHeight = mAppBarHeight - (int) (mCardViewHeight * scaleValue);

        //默认1s折叠
        appBarLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator anim = ValueAnimator.ofFloat(0, 1f).setDuration(200);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        appBarLayout.setBottom((int) (mAppBarHeight - value * mCardViewHeight * scaleValue));
                    }
                });
                anim.start();
            }
        }, 1000);

    }


    /**
     * 混动
     *
     * @param child
     * @param target
     * @param dy
     */
    private void scrollY(AppBarLayout child, View target, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, mLimitHeight);
        mLastScale = Math.max(1f, 1f + (mTotalDy / mLimitHeight));
        mLastBottom = mLimitHeight + (int) (mCardViewHeight * scaleValue * (mLastScale - 1));
        child.setBottom(mLastBottom);
        target.setScrollY(0);
    }


    /**
     * 根据滑动设置 toolbar  名字显示效果
     *
     * @param target
     * @param dy
     */
    private void setViewAlpha(View target, int dy) {
        float percent = Math.abs(target.getY() / mLimitHeight);
        if (percent >= 1) {
            percent = 1f;
        }
        //设置toolbar的透明度
        mToolBar.setAlpha(percent);

        //设置名字缩放
        mNameTitle.setScaleX(Math.max(0.8f, 1 - percent));
        mNameTitle.setScaleY(Math.max(0.8f, 1 - percent));

        //设置名字平移
        int offset = mNameTitle.getTop() - mToolBar.getTop();
        mNameTitle.setTranslationY(-offset * percent);
    }

    /**
     * 恢复位置
     *
     * @param abl
     */
    private void recovery(final AppBarLayout abl) {
        if (mTotalDy >= 0) {
            mTotalDy = 0;
            if (isAnimate) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(200);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        int offsetY = abl.getBottom() - mLimitHeight;
                        abl.setBottom((int) (abl.getBottom() - (value * offsetY)));
                        abl.setScrollY(0);
                    }
                });
                valueAnimator.start();
            } else {
                abl.setBottom(mLimitHeight);
                abl.setScrollY(0);
            }
        }
    }


}
