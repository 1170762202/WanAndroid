package com.zlx.widget.viewpager.animviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Created by zlx on 2020/10/22 10:43
 * Email: 1170762202@qq.com
 * Description:
 */
public class LiquidSwipeViewPager extends ViewPager {
    private int DEFAULT_SCROLLER_DURATION = 1000;

    public LiquidSwipeViewPager(@NonNull Context context) {
        this(context, null);
    }

    public LiquidSwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        setPageTransformer(true, new LiquidSwipePageTransformer());
        long scrollerDuration = DEFAULT_SCROLLER_DURATION;
        setDuration(scrollerDuration);
    }

    private void setDuration(long scrollerDuration) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), DEFAULT_SCROLLER_DURATION);
            mScroller.set(this,scroller);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }


    class LiquidSwipePageTransformer implements PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (page instanceof LiquidSwipeLayout) {
                if (position < -1) {
                    ((LiquidSwipeLayout) page).revealForPercentage(0f);
                } else if (position < 0) {
                    page.setTranslationX(-(page.getWidth() * position));
                    ((LiquidSwipeLayout) page).revealForPercentage(100 - Math.abs(position * 100));
                } else if (position <= 1) {
                    ((LiquidSwipeLayout) page).revealForPercentage(100f);
                    page.setTranslationX(-(page.getWidth() * position));
                }
            }
        }
    }
}
