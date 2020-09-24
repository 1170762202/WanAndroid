/*
 * Copyright 2019. SHENQINCI(沈钦赐)<946736079@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zlx.module_base.widget.slideback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
  * Created by zlx on 2020/9/24 8:58
  * Email: 1170762202@qq.com
  * Description: 滑动手势控制等
*/
@SuppressLint("ViewConstructor")
public class SlideControlLayout extends FrameLayout {
    private final SlideBackView slideBackView;
    private final OnSlide onSlide;
    private int canSlideWidth;
    private boolean enable = true;

    private float downX;
    private float moveX;
    private boolean startDrag = false;

    SlideControlLayout(@NonNull Context context, int canSlideWidth, ISlideView slideView, OnSlide onSlide) {
        super(context);
        this.canSlideWidth = canSlideWidth;
        this.onSlide = onSlide;
        slideBackView = new SlideBackView(context, slideView);
        addView(slideBackView);
    }


    SlideControlLayout attachToActivity(@NonNull Activity activity) {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
        }
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();

        decor.addView(this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return this;
    }

    private void onBack() {
        if (onSlide == null) {
            Utils.getActivityContext(getContext()).onBackPressed();
        } else {
            onSlide.onSlideBack();
        }
    }


    private void setSlideViewY(SlideBackView view, int y) {
        if (!view.getSlideView().scrollVertical()) {
            scrollTo(0, 0);
            return;
        }
        scrollTo(0, -(y - view.getHeight() / 2));
    }

    //region 手势控制
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!enable) {
            return false;
        }

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (motionEvent.getRawX() <= canSlideWidth) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!enable) {
            return super.onTouchEvent(motionEvent);
        }

        float currentX = motionEvent.getRawX();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float currentY = motionEvent.getRawY();
                if (currentY > Utils.d2p(getContext(), 100) && currentX <= canSlideWidth) {
                    downX = currentX;
                    startDrag = true;
                    slideBackView.updateRate(0, false);
                    setSlideViewY(slideBackView, (int) (motionEvent.getRawY()));
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (startDrag) {
                    moveX = currentX - downX;
                    if (Math.abs(moveX) <= slideBackView.getWidth() * 2) {
                        slideBackView.updateRate(Math.abs(moveX) / 2, false);
                    } else {
                        slideBackView.updateRate(slideBackView.getWidth(), false);
                    }
                    setSlideViewY(slideBackView, (int) (motionEvent.getRawY()));
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (startDrag && moveX >= slideBackView.getWidth() * 2) {
                    onBack();
                    slideBackView.updateRate(0, false);
                } else {

                    slideBackView.updateRate(0, startDrag);
                }
                moveX = 0;
                startDrag = false;
                break;
        }

        return startDrag || super.onTouchEvent(motionEvent);
    }
    //endregion


}
