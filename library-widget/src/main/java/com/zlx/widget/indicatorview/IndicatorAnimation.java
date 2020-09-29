package com.zlx.widget.indicatorview;

/**
 * Created by zlx on 2020/9/29 9:58
 * Email: 1170762202@qq.com
 * Description:
 */
public enum IndicatorAnimation {
    NORMAL(0),
    ACCELERATE(1),
    BOUNCE(2);

    int value;

    IndicatorAnimation(int value) {
        this.value = value;
    }
}
