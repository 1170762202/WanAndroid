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

import android.app.Activity;

import androidx.annotation.NonNull;

/**
  * Created by zlx on 2020/9/24 8:58
  * Email: 1170762202@qq.com
  * Description:
*/
public class SlideBack {
    private ISlideView slideView;   //样式
    private OnSlide onSlide;        //滑动监听
    private int canSlideWidth;      //左边触发距离

    public static SlideBack create() {
        return new SlideBack();
    }

    /**
     * 滑动返回样式
     *
     * @param slideView the slide view
     * @return the slide back
     */
    public SlideBack slideView(ISlideView slideView) {
        this.slideView = slideView;
        return this;
    }

    /**
     * 左边开始触发距离
     *
     * @param canSlideWidth the can slide width
     * @return the slide back
     */
    public SlideBack canSlideWidth(int canSlideWidth) {
        this.canSlideWidth = canSlideWidth;
        return this;
    }

    /**
     * 滑动触发监听
     *
     * @param onSlide the on slide
     * @return the slide back
     */
    public SlideBack onSlide(OnSlide onSlide) {
        this.onSlide = onSlide;
        return this;
    }


    public SlideControlLayout attachToActivity(@NonNull Activity activity) {
        if (slideView == null) {
            slideView = new DefaultSlideView(activity);
        }

        if (canSlideWidth == 0) {
            canSlideWidth = Utils.d2p(activity, 18);
        }

        return new SlideControlLayout(activity, canSlideWidth, slideView, onSlide)
                .attachToActivity(activity);
    }
}
