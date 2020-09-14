package com.example.module_main.fragment;

import com.example.module_main.R;
import com.zlx.module_base.base_fg.BaseFg;

/**
 * @date: 2019\3\8 0008
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class FourthFg extends BaseFg {

    @Override
    protected int getLayoutId() {
        return R.layout.fg4;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }

    @Override
    protected void initViews() {

    }

}
