package com.zlx.widget.indicatorview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by zlx on 2020/9/29 10:11
 * Email: 1170762202@qq.com
 * Description: 左边指示器控件
 */
public class IndicatorItemView extends FrameLayout {

    private boolean isExpanding = false;
    private boolean isExpanded = false;
    private IndicatorItem indicatorItem = null;
    private ImageView icon = new ImageView(getContext());
    private TextView textView = new TextView(getContext());
    private int textSize = sp2px(10);

    public IndicatorItemView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private void init() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        setLayoutParams(layoutParams);
//        icon.setLayoutParams(layoutParams);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(textSize);
        //        addView(icon);
        addView(textView);

        invalidate();
    }

    private void updateIndicatorItemView() {
        if (indicatorItem != null) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(indicatorItem.cornerRadius);
            gradientDrawable.setColor(indicatorItem.color);
            this.setBackground(gradientDrawable);
//            icon.setImageDrawable(indicatorItem.icon);
            textView.setTextColor(indicatorItem.textColor);
            textView.setText(indicatorItem.text);

        }
    }

    public void setIndicatorItem(IndicatorItem indicatorItem) {
        this.indicatorItem = indicatorItem;
        updateIndicatorItemView();
    }

    /**
     * 伸展动画
     *
     * @param minHeight 最小高度
     * @param to
     */
    public void expand(int minHeight, int to) {
        if (indicatorItem != null) {
            if (!isExpanded && !isExpanding) {
                isExpanded = true;
                isExpanding = true;
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                valueAnimator.setDuration(indicatorItem.duration);
                valueAnimator.setInterpolator(applyInterpolator(indicatorItem.indicatorAnimation));
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isExpanding = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float value = (float) valueAnimator.getAnimatedValue();
                        int target = minHeight + (int) (to * value);
                        if (target >= to) {
                            target = to;
                        }
                        //动态设置item高度
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.height = target;
                        setLayoutParams(layoutParams);

                    }
                });
                valueAnimator.start();
            }
        }
    }

    /**
     * 收缩动画
     *
     * @param minHeight 最小高度
     * @param from
     */
    public void collapse(int minHeight, int from) {
        if (indicatorItem != null) {
            if (this.isExpanded && !isExpanding) {
                this.isExpanded = false;
                this.isExpanding = true;
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f);
                valueAnimator.setDuration(indicatorItem.duration);
                valueAnimator.setInterpolator(applyInterpolator(indicatorItem.indicatorAnimation));
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isExpanding = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float value = (float) valueAnimator.getAnimatedValue();
                        int target = (int) (from * value);
                        if (target <= minHeight) {
                            target = minHeight;
                        }
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.height = target;
                        setLayoutParams(layoutParams);

                    }
                });
                valueAnimator.start();
            }
        }
    }


    Interpolator applyInterpolator(IndicatorAnimation indicatorAnimation) {
        switch (indicatorAnimation) {
            case BOUNCE:
                return new BounceInterpolator();
            case NORMAL:
                return new LinearInterpolator();
            case ACCELERATE:
                return new AccelerateInterpolator();
            default:
                return null;
        }
    }

}
