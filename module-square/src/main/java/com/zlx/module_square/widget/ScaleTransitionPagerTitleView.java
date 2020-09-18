package com.zlx.module_square.widget;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * FileName: ScaleTransitionPagerTitleView
 * Created by zlx on 2020/9/18 10:43
 * Email: 1170762202@qq.com
 * Description:
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {

    private TabSelectListener tabSelectListener;

    public void setTabSelectListener(TabSelectListener tabSelectListener) {
        this.tabSelectListener = tabSelectListener;
    }

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        if (tabSelectListener != null)
            tabSelectListener.onSelect(index, totalCount);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        if (tabSelectListener != null)
            tabSelectListener.onDeselected(index, totalCount);
    }
}