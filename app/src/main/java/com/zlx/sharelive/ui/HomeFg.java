package com.zlx.sharelive.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlx.sharelive.R;
import com.zlx.sharelive.base.base_fg.BaseFg;
import com.zlx.sharelive.util.LogUtil;
import com.zlx.sharelive.widget.snake_menu.TumblrRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class HomeFg extends BaseFg {

    @BindView(R.id.snake_layout)
    TumblrRelativeLayout snakeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_home;
    }

    @Override
    protected void initViews() {
        snakeLayout.setMenuListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("click:"+v.getTag());
            }
        });
    }


}
