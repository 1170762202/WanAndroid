package com.zlx.sharelive.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class AnimUtil {

    public static void reveal(View parent, int centerX, int centerY, float startRadius, float endRadius, Animator.AnimatorListener listener) {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(parent, centerX, centerY, startRadius, endRadius);
        circularReveal.setDuration(300);
        if (listener != null) {
            circularReveal.addListener(listener);
        }
        circularReveal.start();
    }

    public static void reveal(View parent, int centerX, int centerY, float startRadius, float endRadius) {
        reveal(parent, centerX, centerY, startRadius, endRadius, null);
    }

    public static void reveal(final Context context, final View parent, final View bgOverLay, final int newColor, int centerX, int centerY, float startRadius, float endRadius) {
        parent.clearAnimation();
        bgOverLay.clearAnimation();
        reveal(parent, centerX, centerY, startRadius, endRadius, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                onCancel();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onCancel();
            }

            private void onCancel() {
                parent.setBackgroundColor(ContextCompat.getColor(context, newColor));
                bgOverLay.setVisibility(View.GONE);
            }
        });

        bgOverLay.setBackgroundColor(ContextCompat.getColor(context, newColor));
        bgOverLay.setVisibility(View.VISIBLE);
    }


    public static void setBackgroundWithRipple(int position, View clickedView, final View backgroundView,
                                               final View bgOverlay, final int newColor, int animationDuration) {
        int centerX = (int) (clickedView.getX() + (clickedView.getMeasuredWidth() / 2));
        int centerY = clickedView.getMeasuredHeight() / 2;
        int finalRadius = backgroundView.getWidth();

        //tablayout 无法获取子view的具体信息
        centerX += 2 * position * centerX;
        backgroundView.clearAnimation();
        bgOverlay.clearAnimation();

        Animator circularReveal;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils
                    .createCircularReveal(bgOverlay, centerX, centerY, 0, finalRadius);
        } else {
            bgOverlay.setAlpha(0);
            circularReveal = ObjectAnimator.ofFloat(bgOverlay, "alpha", 0, 1);
        }

        circularReveal.setDuration(animationDuration);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onCancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onCancel();
            }

            private void onCancel() {
//                backgroundView.setBackgroundColor(newColor);
//                bgOverlay.setVisibility(View.GONE);
            }
        });

        bgOverlay.setBackgroundColor(newColor);
        bgOverlay.setVisibility(View.VISIBLE);
        circularReveal.start();
    }


    public static void backgroundCircularRevealAnimation(int position, final View parent, View clickedView,
                                                         final View backgroundOverlay, final int newColor) {

        parent.clearAnimation();
        backgroundOverlay.clearAnimation();

        backgroundOverlay.setBackgroundColor(newColor);
        backgroundOverlay.setVisibility(View.VISIBLE);


        int centerX = (int) (ViewCompat.getX(clickedView) + (clickedView.getMeasuredWidth() / 2));
        int yOffset = (int) ViewCompat.getY(clickedView);
        int centerY = yOffset + clickedView.getMeasuredHeight() / 2;
        int startRadius = 0;
        int finalRadius = backgroundOverlay.getWidth();

        centerX += 2 * position * centerX;

        Animator animator = ViewAnimationUtils.createCircularReveal(
                backgroundOverlay,
                centerX,
                centerY,
                startRadius,
                finalRadius
        );
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onEnd();
            }

            private void onEnd() {
                parent.setBackgroundColor(newColor);
                backgroundOverlay.setVisibility(View.INVISIBLE);
                ViewCompat.setAlpha(backgroundOverlay, 1);
            }
        });

        animator.start();
    }

}
