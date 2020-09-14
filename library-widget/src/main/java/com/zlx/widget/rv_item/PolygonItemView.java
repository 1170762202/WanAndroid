package com.zlx.widget.rv_item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;

import com.zlx.widget.R;

public class PolygonItemView extends View {

    //正六边形的边数量
    private static final int POLYGON_COUNT = 6;

    //默认的边框宽度
    private static final int DEFAULT_OUTER_WIDTH = 4;

    //默认的边框颜色
    public static final int DEFAULT_OUTER_COLOR = Color.parseColor("#f5c421");

    //包裹着正六边形的圆的半径
    private int mMaxRadius;
    private int mRadius;
    //边框线的宽度
    private int mOuterWidth;

    /**
     * 内侧颜色和外侧的颜色
     */
    private int mOuterColor;
    private int mInnerColor;


    private Paint mOuterPaint;
    private Paint mInnerPaint;

    private Path mViewPath;

    private Region mRegion;

    /**
     * 控件的中心位置
     */
    private int mCenterX;
    private int mCenterY;

    //是否无边框填充
    private boolean isHasStroke;

    //绘制阴影状态变量
    private float mShadowRadius;
    private float mShadowDx, mShadowDy;
    private int mShadowColor;


    public PolygonItemView(Context context) {
        this(context, null);
    }

    public PolygonItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolygonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.Polygon, defStyleAttr, 0);
        mRadius = mTypeArray.getDimensionPixelSize(R.styleable.Polygon_radius, 0);
        mInnerColor = mTypeArray.getColor(R.styleable.Polygon_innerColor, Color.WHITE);
        mOuterColor = mTypeArray.getColor(R.styleable.Polygon_outerColor, DEFAULT_OUTER_COLOR);
        mOuterWidth = mTypeArray.getDimensionPixelSize(R.styleable.Polygon_outerWidth, DEFAULT_OUTER_WIDTH);
        isHasStroke = mTypeArray.getBoolean(R.styleable.Polygon_isHasStroke, true);
        mTypeArray.recycle();
        initData();
    }

    private void initData() {
        //关闭硬件加速，为了可以设置阴影
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(mOuterWidth);
        mOuterPaint.setColor(mOuterColor);


        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mInnerPaint.setColor(mInnerColor);


        mRegion = new Region();
        mViewPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mMaxRadius = Math.min(mCenterX, mCenterY);
        if (mRadius <= 0 || mRadius > mMaxRadius) {
            mRadius = mMaxRadius;
        }
        lineMultShape(POLYGON_COUNT);
    }


    /**
     * 绘制多边形
     */
    public void lineMultShape(int count) {
        if (count < POLYGON_COUNT) {
            return;
        }
        mViewPath.reset();
        for (int i = 0; i < count; i++) {
            //当前角度
            int angle = 360 / count * i;
            if (i == 0) {
                mViewPath.moveTo(mCenterX + mRadius * MathUtil.cos(angle), mCenterY + mRadius * MathUtil.sin(angle));
            } else {
                mViewPath.lineTo(mCenterX + mRadius * MathUtil.cos(angle), mCenterY + mRadius * MathUtil.sin(angle));
            }
        }
        mViewPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mViewPath, mInnerPaint);
        if (isHasStroke) {
            canvas.drawPath(mViewPath, mOuterPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isEventInPath(event)) {
                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 判断是否在多边形内
     *
     * @param event
     * @return
     */
    private boolean isEventInPath(MotionEvent event) {
        RectF bounds = new RectF();
        //计算Path的边界
        mViewPath.computeBounds(bounds, true);
        //将边界赋予Region中
        mRegion.setPath(mViewPath, new Region((int) bounds.left, (int) bounds.top,
                (int) bounds.right, (int) bounds.bottom));
        //判断 当前的触摸点是否在这个范围内
        return mRegion.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * 设置多边形边框颜色
     */
    public void setOuterColor(int color) {
        mOuterColor = color;
        mOuterPaint.setColor(color);
        invalidate();
    }

    public int getOuterColor() {
        return mOuterColor;
    }

    /**
     * 设置正六边形的内部填充颜色
     */
    public void setInnerColor(int color) {
        mInnerColor = color;
        mInnerPaint.setColor(color);
        invalidate();
    }

    public int getInnerColor() {
        return mInnerColor;
    }

    /**
     * 设置正六边形的边框大小
     */
    public void setOuterWidth(int outerWidth) {
        if (outerWidth < 0) {
            return;
        }
        mOuterWidth = outerWidth;
        mOuterPaint.setStrokeWidth(outerWidth);
        invalidate();
    }

    public int getOutWidth() {
        return mOuterWidth;
    }

    /**
     * 设置正六边形是否被填充(无边框)
     */
    public void setViewFullMode(boolean isFull) {
        this.isHasStroke = isFull;
        invalidate();
    }

    public boolean getViewFullMode() {
        return isHasStroke;
    }

    /**
     * 设置正六边形的外圆半径
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
        lineMultShape(POLYGON_COUNT);
        invalidate();
    }

    public int getRadius() {
        return mRadius;
    }

    /**
     * 设置阴影  指的是实心的阴影
     */
    public void setShadowLayer(float radius, float dx, float dy, int color, boolean isOuter) {
        if (isOuter && isHasStroke) {
            mOuterPaint.setShadowLayer(radius, dx, dy, color);
        } else if (!isOuter) {
            mInnerPaint.setShadowLayer(radius, dx, dy, color);
        } else {
            return;
        }
        mShadowRadius = radius;
        mShadowDx = dx;
        mShadowDy = dy;
        mShadowColor = color;
        invalidate();
    }

    public float getShadowRadius() {
        return mShadowRadius;
    }

    public float getShadowDx() {
        return mShadowDx;
    }

    public float getShadowDy() {
        return mShadowDy;
    }

    @ColorInt
    public int getShadowColor() {
        return mShadowColor;
    }

}