package com.zlx.module_square.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.constant.RouterConstant;
import com.zlx.module_square.R;

/**
 * Copyright (C)
 * FileName: SquareFg
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 11:27
 * Description: 广场
 */
@Route(path = RouterConstant.ROUT_FG_SQUARE)
public class SquareFg extends BaseFg {

    @Override
    protected int getLayoutId() {
        return R.layout.fg_square;
    }
}
