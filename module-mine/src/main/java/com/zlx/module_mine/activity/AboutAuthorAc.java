package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.FileUtils;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_network.widget.popwindow.PopUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zlx on 2020/9/22 17:25
 * Email: 1170762202@qq.com
 * Description:
 */
public class AboutAuthorAc extends BaseAc {

    @BindView(R2.id.ivWx)
    ImageView ivWx;
    @BindView(R2.id.ivZfb)
    ImageView ivZfb;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutAuthorAc.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_about_author;
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle(getString(R.string.mine_about_author));

    }

    @OnClick(R2.id.ivWx)
    public void saveWx() {
        FileUtils.saveImage(this, ivWx);
        PopUtil.show(getString(R.string.save_success));
    }

    @OnClick(R2.id.ivZfb)
    public void saveZfb() {
        FileUtils.saveImage(this, ivZfb);
        PopUtil.show(getString(R.string.save_success));
    }

}
