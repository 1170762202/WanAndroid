package com.zlx.module_home.listener;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OnRvVerticalScrollListener extends RecyclerView.OnScrollListener {

    private View top;
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    public OnRvVerticalScrollListener(View view) {
        this.top = view;
    }


    /**
     * dx > 0 时为手指向左滚动,列表滚动显示右面的内容
     * dx < 0 时为手指向右滚动,列表滚动显示左面的内容
     * dy > 0 时为手指向上滚动,列表滚动显示下面的内容
     * dy < 0 时为手指向下滚动,列表滚动显示上面的内容
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        }
        else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    protected void onHide() {
        Resources resources = top.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
//        buttom.animate()
//                .translationY(height - buttom.getHeight())
//                .setInterpolator(new AccelerateInterpolator(2))
//                .setDuration(800)
//                .start();
        top.animate()
                .translationY(-height)
                .setDuration(800)
                .setInterpolator(new AccelerateInterpolator(2))
                .start();


    }

    protected void onShow() {

//        buttom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).setDuration(800).start();
        top.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).setDuration(800).start();

    }
}
