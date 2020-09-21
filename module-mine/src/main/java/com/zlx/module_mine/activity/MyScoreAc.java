package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_mine.R;

/**
 * FileName: MyScoreAc
 * Created by zlx on 2020/9/21 17:52
 * Email: 1170762202@qq.com
 * Description: 我的积分
 */
public class MyScoreAc extends BaseAc {


    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyScoreAc.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_my_score;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setAcTitle("我的积分");
        setRightImg(R.mipmap.ic_question);
        setOnRightImgClickListener(view -> {

        });
    }
}
