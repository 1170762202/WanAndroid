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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.NonNull;

/**
  * Created by zlx on 2020/9/24 8:57
  * Email: 1170762202@qq.com
  * Description:  默认样式（弹性贝塞尔曲线+箭头）
*/
public class DefaultSlideView implements ISlideView {
    private Path bezierPath;
    private Paint paint, arrowPaint;
    //private LinearGradient shader;

    private int backViewColor = 0xff000000;
    private int arrowColor = Color.WHITE;

    private int arrowWidth;

    private final int width;
    private final int height;


    public DefaultSlideView(@NonNull Context context) {
        width = Utils.d2p(context, 50);
        height = Utils.d2p(context, 200);
        arrowWidth = Utils.d2p(context, 4);
        init(context);
    }

    public void setBackViewColor(int backViewColor) {
        this.backViewColor = backViewColor;
    }

    public void setArrowColor(int arrowColor) {
        this.arrowColor = arrowColor;
    }

    private void init(Context context) {
        bezierPath = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backViewColor);
        paint.setStrokeWidth(Utils.d2p(context, 1.5f));

        arrowPaint = new Paint();
        arrowPaint.setAntiAlias(true);
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arrowPaint.setColor(arrowColor);
        arrowPaint.setStrokeWidth(Utils.d2p(context, 1.5f));
        arrowPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    public boolean scrollVertical() {
        return true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void onDraw(Canvas canvas, float currentWidth) {
        float height = getHeight();
        int maxWidth = getWidth();
        float centerY = height / 2;

        float progress = currentWidth / maxWidth;
        if (progress == 0) {
            return;
        }

        paint.setColor(backViewColor);
        paint.setAlpha((int) (200 * progress));

        //画半弧背景
        /*

        ps: 小点为起始点和结束点，星号为控制点

        ·
        |
        *
             *
             |
             ·
             |
             *
        *
        |
        ·

         */

        float bezierWidth = currentWidth / 2;
        bezierPath.reset();
        bezierPath.moveTo(0, 0);
        bezierPath.cubicTo(0, height / 4f, bezierWidth, height * 3f / 8, bezierWidth, centerY);
        bezierPath.cubicTo(bezierWidth, height * 5f / 8, 0, height * 3f / 4, 0, height);
        canvas.drawPath(bezierPath, paint);


        arrowPaint.setColor(arrowColor);
        arrowPaint.setAlpha((int) (255 * progress));

        //画箭头
        float arrowLeft = currentWidth / 6;
        if (progress <= 0.2) {
            //ingore
        } else if (progress <= 0.7f) {
            //起初变长竖直过程
            float newProgress = (progress - 0.2f) / 0.5f;
            canvas.drawLine(arrowLeft, centerY - arrowWidth * newProgress, arrowLeft, centerY + arrowWidth * newProgress, arrowPaint);
        } else {
            //后面变形到完整箭头过程
            float arrowEnd = arrowLeft + (arrowWidth * (progress - 0.7f) / 0.3f);
            canvas.drawLine(arrowLeft, centerY - arrowWidth, arrowEnd, centerY, arrowPaint);
            canvas.drawLine(arrowEnd, centerY, arrowLeft, centerY + arrowWidth, arrowPaint);

//            canvas.drawLine(arrowEnd, centerY - arrowWidth, arrowLeft, centerY, arrowPaint);
//            canvas.drawLine(arrowLeft, centerY, arrowEnd, centerY + arrowWidth, arrowPaint);
        }


    }
}
