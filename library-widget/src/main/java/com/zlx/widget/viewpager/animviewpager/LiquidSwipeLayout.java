package com.zlx.widget.viewpager.animviewpager;

/**
 * Created by zlx on 2020/10/22 10:46
 * Email: 1170762202@qq.com
 * Description:
 */
public interface LiquidSwipeLayout {
    public ClipPathProvider clipPathProvider();

    public Float currentRevealPercent();

    public void revealForPercentage(Float percent);
}
