package com.zlx.sharelive.widget.slide_close;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.zlx.sharelive.R;

import androidx.core.content.ContextCompat;


/**
 * 圆形进度条
 * <p>
 * Created by Bamboy on 2018/10/18.
 */
public class SlopeProgress extends View {
    /**
     * 当前进度
     */
    private int progress = 0;
    /**
     * 总进度
     */
    private int maxProgress = 100;
    /**
     * 圆环颜色
     */
    private int ringColor;
    /**
     * 直径
     */
    private float diam = 100;
    /**
     * 线条
     */
    private float line = 13;

    public SlopeProgress(Context context) {
        super(context);

        ringColor = ContextCompat.getColor(context, R.color.main);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int diameter = Math.min(width, height);

        line = diameter * 0.13f;
        diam = diameter - line * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        float length = Math.min(width, height);

        // =============== 声明画笔 ===============
        // 初始化画笔
        Paint ringPaint = new Paint();
        // 设置消除锯齿
        ringPaint.setAntiAlias(true);
        // 设置防抖，即边缘柔化
        ringPaint.setDither(true);
        // 设置颜色
        ringPaint.setColor(ringColor);
        // 设置实心
        ringPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔的宽度
        ringPaint.setStrokeWidth(line);
        // 设置线条圆角
        ringPaint.setStrokeCap(Paint.Cap.ROUND);

        // =============== 画圆环 ===============
        // 进度
        float pro = (float) progress / (float) maxProgress;
        // 旋转角度
        float startAngle = 180 - (pro * 180);
        // 圆环进度
        float sweepAngle = pro * 360;
        // 计算内边距
        int padding = (int) ((length - diam) / 2);
        RectF oval = new RectF(padding, padding, padding + diam, padding + diam);

        // 绘制圆圈
        canvas.drawArc(oval, startAngle, sweepAngle, false, ringPaint);


        // =============== 画叉号 ===============
        // 修改透明度
        ringPaint.setAlpha(pro == 1 ? 255 : (int) (pro / 2.5 * 255));
        // 修改画笔的宽度
        ringPaint.setStrokeWidth(line * 0.7f);

        // 线距离边缘的距离，值越小，叉号越大，值范围在 0 ~ 0.5 之间
        float linePadding = 0.373f;

        // 第一根直线的【起点/终点】坐标
        float lineStartX_1 = length * linePadding;
        float lineStartY_1 = length * linePadding;
        float lineEndX_1 = length * (1 - linePadding);
        float lineEndY_1 = length * (1 - linePadding);

        // 第二根直线的【起点/终点】坐标
        float lineStartX_2 = length * linePadding;
        float lineStartY_2 = length * (1 - linePadding);
        float lineEndX_2 = length * (1 - linePadding);
        float lineEndY_2 = length * linePadding;

        // 绘制直线
        canvas.drawLines(new float[]{
                // 绘制一根直线 每四数字(两个点的坐标)确定一条线
                lineStartX_1, lineStartY_1, lineEndX_1, lineEndY_1,
                // 绘制第二根直线 每四数字(两个点的坐标)确定一条线
                lineStartX_2, lineStartY_2, lineEndX_2, lineEndY_2
        }, ringPaint);
    }

    /**
     * 总进度
     */
    public int getMaxProgress() {
        return maxProgress;
    }

    /**
     * 总进度
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 设置颜色
     */
    public void setRingColor(int ringColor) {
        if (ringColor != 0)
            this.ringColor = ringColor;
    }

    /**
     * 线条
     */
    public float getLine() {
        return line;
    }

    /**
     * 线条
     */
    public void setLine(float line) {
        this.line = line;
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > maxProgress) {
            progress = maxProgress;
        }

        this.progress = progress;
        invalidate();
    }
}
