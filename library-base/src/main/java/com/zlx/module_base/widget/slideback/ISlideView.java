

package com.zlx.module_base.widget.slideback;

import android.graphics.Canvas;

public interface ISlideView {
        /**
         * 是否可以垂直滑动
         *
         * @return
         */
        boolean scrollVertical();

        /**
         * 宽度
         *
         * @return
         */
        int getWidth();

        /**
         * 高度
         *
         * @return
         */
        int getHeight();

        /**
         * 绘制
         *
         * @param canvas
         * @param currentWidth 根据手指滑动得出的当前宽度（最大值为getWidth())
         */
        void onDraw(Canvas canvas, float currentWidth);
    }