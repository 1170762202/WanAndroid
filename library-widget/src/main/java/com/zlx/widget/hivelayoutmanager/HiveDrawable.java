package com.zlx.widget.hivelayoutmanager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.zlx.widget.R;


public class HiveDrawable extends Drawable {

    private static final String TAG = HiveDrawable.class.getSimpleName();
    private IHiveMathUtils mHiveMathUtils;
    private Rect mRect = new Rect();
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;

    @HiveLayoutManager.Orientation
    private int mOrientation;

    public HiveDrawable(@HiveLayoutManager.Orientation int orientation) {
        this(orientation, null);
    }

    public HiveDrawable(@HiveLayoutManager.Orientation int orientation, Bitmap bitmap) {
        this.mOrientation = orientation;
        init();
        if (bitmap != null) {
            setBitmap(bitmap);
        }
    }
    @SuppressLint("ResourceAsColor")
    public HiveDrawable(@HiveLayoutManager.Orientation int orientation, @ColorInt int color) {
        this.mOrientation = orientation;
        init();
        mPaint.setColor(color);
    }
    private void init() {
        mHiveMathUtils = HiveMathUtils.getInstance();

        initPaint();
        initPath();
    }

    private void ensurePaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
    }

    private void ensurePath() {
        if (mPath == null) {
            mPath = new Path();
        }
    }

    private void initPaint() {
        ensurePaint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(@NonNull Bitmap bitmap) {
        this.mBitmap = bitmap;
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
    }

    private void initPath() {
        ensurePath();
        float l = mHiveMathUtils.calculateLength(mRect, mOrientation);
        if (mOrientation == HiveLayoutManager.HORIZONTAL) {
            float h = (float) (Math.sqrt(3) * l);
            float top = (mRect.height() - h) / 2;
            mPath.reset();
            mPath.moveTo(l / 2 + mRect.left, top + mRect.top);
            mPath.lineTo(0 + mRect.left, h / 2 + top + mRect.top);
            mPath.lineTo(l / 2 + mRect.left, h + top + mRect.top);
            mPath.lineTo((float) (l * 1.5) + mRect.left, h + top + mRect.top);
            mPath.lineTo(2 * l + mRect.left, h / 2 + top + mRect.top);
            mPath.lineTo((float) (l * 1.5) + mRect.left, top + mRect.top);
            mPath.lineTo(l / 2 + mRect.left, top + mRect.top);
            mPath.close();
        } else if (mOrientation == HiveLayoutManager.VERTICAL) {
            float w = (float) (Math.sqrt(3) * l);
            float left = (mRect.width() - w) / 2;
            mPath.reset();
            mPath.moveTo(left + mRect.left, l / 2 + mRect.top);
            mPath.lineTo(w / 2 + left + mRect.left, 0 + mRect.top);
            mPath.lineTo(w + left + mRect.left, l / 2 + mRect.top);
            mPath.lineTo(w + left + mRect.left, (float) (l * 1.5) + mRect.top);
            mPath.lineTo(w / 2 + left + mRect.left, 2 * l + mRect.top);
            mPath.lineTo(left + mRect.left, (float) (l * 1.5) + mRect.top);
            mPath.lineTo(left + mRect.left, l / 2 + mRect.top);
            mPath.close();
        } else {
            Log.e(TAG, String.format("hive drawable orientation mast be horizontal : %d or vertical : %d"
                    , HiveLayoutHelper.HORIZONTAL, HiveLayoutHelper.VERTICAL));
        }
    }

    void setColor(int color){
        mPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mPaint != null) {
            mPaint.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (mPaint != null) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRect.set(left, top, right, bottom);
        initPath();
    }

    @Override
    public int getIntrinsicWidth() {
        if (mBitmap != null) {
            return mBitmap.getWidth();
        } else {
            return super.getIntrinsicWidth();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (mBitmap != null) {
            return mBitmap.getHeight();
        }
        return super.getIntrinsicHeight();
    }

    @HiveLayoutManager.Orientation
    public int getOrientation() {
        return mOrientation;
    }
}