package com.zlx.widget.submit_button;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


import androidx.core.content.ContextCompat;

import com.zlx.widget.R;

/**
 * @date: 2019\3\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class SubmitButton extends View {


    private long DURATION_RECT2ROUND = 500;
    private long DURATION_GATHER = 500;

    private int width;
    private int height;

    private Paint paint;
    private Paint paintArc;
    private String text = "登录";

    private RectF rectF;
    private float centerAngle = 10;
    private float two_circle_distance;
    private ValueAnimator animRect2Round;
    private ValueAnimator animGather;
    private ValueAnimator arcValueAnimator;
    private int startAngle = 0;

    private float default_two_circle_distance;
    private Paint textPaint;
    private float textSize = 60f;
    private boolean isMorphing = false;
    private AnimatorSet animatorSet;

    public SubmitButton(Context context) {
        super(context);
        init(context);
    }

    public SubmitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SubmitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(context, R.color.main_text));

        paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setColor(ContextCompat.getColor(context, R.color.white));
        paintArc.setStrokeWidth(4);
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setTextSize(2);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(context, R.color.white));
        textPaint.setTextSize(textSize);

        rectF = new RectF();

    }


    /**
     * 矩形两边过度成半圆的动画
     */
    private void initAnimSetRect2Round() {
        if (animRect2Round == null) {
            animRect2Round = ValueAnimator.ofFloat(centerAngle, height / 2);
            animRect2Round.setDuration(DURATION_RECT2ROUND);
            animRect2Round.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    centerAngle = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
        }
    }

    /**
     * 两边向中间靠拢
     */
    private void initAnimGather() {
        if (animGather == null) {
            animGather = new ValueAnimator().ofFloat(0, default_two_circle_distance);
            animGather.setDuration(DURATION_GATHER);
            animGather.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    two_circle_distance = (float) animation.getAnimatedValue();
                    //文字透明度
                    float alpha = 255 - (two_circle_distance / default_two_circle_distance) * 255;
                    textPaint.setAlpha((int) alpha);
                    postInvalidate();
                }
            });
        }
    }

    private void showArc() {
        arcValueAnimator = ValueAnimator.ofInt(0, 1080);
        arcValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        arcValueAnimator.setInterpolator(new LinearInterpolator());
        arcValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        arcValueAnimator.setDuration(2000);
        arcValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isMorphing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setText(String text) {
        this.text = text;
        postInvalidate();
    }

    /**
     * 总开关
     */
    public void startAnim() {
        animatorSet = new AnimatorSet();
        animatorSet.play(arcValueAnimator).after(animRect2Round).after(animGather);
        animatorSet.start();
    }

    public void reset() {
        isMorphing = false;
        two_circle_distance = 0;
        centerAngle = 10;
        textPaint.setAlpha(255);
        if (animatorSet!=null){
            animatorSet.cancel();
        }
        postInvalidate();
    }

    public void success() {
        isMorphing = false;
        two_circle_distance = 0;
        centerAngle = 10;
        textPaint.setAlpha(255);
        postInvalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        default_two_circle_distance = (width - height) / 2;
        initAnimSetRect2Round();
        initAnimGather();
        showArc();
    }

    private void drawTextInCenterOfButton(Canvas c, Paint mPaint, float x, float y, String text) {
        float w = mPaint.measureText(text);
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        float h = Math.abs(fm.descent - fm.ascent);
        c.drawText(text, x - w / 2, y + h / 2 - fm.descent, mPaint);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
        drawTextInCenterOfButton(canvas, textPaint, width / 2, height / 2, text);
        drawArc(canvas);
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas 画布
     */
    private void drawRoundRect(Canvas canvas) {
        rectF.left = two_circle_distance;
        rectF.top = 0;
        rectF.right = width - two_circle_distance;
        rectF.bottom = height;
        canvas.drawRoundRect(rectF, centerAngle, centerAngle, paint);
    }

    private void drawArc(Canvas canvas) {
        if (isMorphing) {
            final RectF rectF = new RectF(getWidth() * 5 / 12, getHeight() / 7,
                    getWidth() * 7 / 12, getHeight() - getHeight() / 7);
            canvas.drawArc(rectF, startAngle, 270, false, paintArc);
        }
    }

}
