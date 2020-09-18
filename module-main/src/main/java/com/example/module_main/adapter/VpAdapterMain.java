package com.example.module_main.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.module_main.impl.TabPagerListener;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class VpAdapterMain extends FragmentPagerAdapter {

    private TabPagerListener listener;

    public void setListener(TabPagerListener listener) {
        this.listener = listener;
    }

    public VpAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listener.getFragment(position);
    }

    @Override
    public int getCount() {
        return listener.count();
    }
}
