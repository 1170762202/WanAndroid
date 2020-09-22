package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.FileUtils;
import com.zlx.module_base.base_util.ToastUtil;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;

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
        setAcTitle("关于作者");

    }

    @OnClick(R2.id.ivWx)
    public void saveWx() {
        FileUtils.saveImage(this, ivWx);
        ToastUtil.showShort("保存成功");
    }

    @OnClick(R2.id.ivZfb)
    public void saveZfb() {
        FileUtils.saveImage(this, ivZfb);
        ToastUtil.showShort("保存成功");
    }

}
