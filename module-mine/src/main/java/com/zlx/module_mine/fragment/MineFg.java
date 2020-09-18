package com.zlx.module_mine.fragment;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.widget.waveview.WaveView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 * FileName: MineFg
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 11:30
 * Description: 我的
 */
@Route(path = RouterFragmentPath.Mine.PAGER_MINE)
public class MineFg extends BaseFg {

    @BindView(R2.id.waveView)
    WaveView waveView;
    @BindView(R2.id.llHead)
    LinearLayout llHead;
    @BindView(R2.id.root)
    LinearLayout root;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_mine;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }

    @Override
    protected void initViews() {
        super.initViews();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llHead.getLayoutParams();
        waveView.setOnWaveAnimationListener(y -> {
            layoutParams.setMargins(0, (int)y, 0, 0);
            llHead.setLayoutParams(layoutParams);
        });
    }

}
