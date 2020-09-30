package com.zlx.sharelive.impl;

import androidx.fragment.app.Fragment;

/**
 * FileName: TabPagerListener
 * Created by zlx on 2020/9/18 14:40
 * Email: 1170762202@qq.com
 * Description:
 */
public interface TabPagerListener {
    Fragment getFragment(int position);

    int count();
}
