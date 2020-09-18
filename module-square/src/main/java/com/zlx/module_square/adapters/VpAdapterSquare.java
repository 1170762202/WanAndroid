package com.zlx.module_square.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_square.impl.TabPagerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class VpAdapterSquare extends FragmentPagerAdapter {

    public TabPagerListener listener;
    private int PAGE_COUNT;

    public void setListener(TabPagerListener listener) {
        this.listener = listener;
    }

    public VpAdapterSquare(FragmentManager fm, int PAGE_COUNT) {
        super(fm);
        this.PAGE_COUNT = PAGE_COUNT;

    }

    @Override
    public Fragment getItem(int position) {
        return listener.getFragment(position);

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


}
