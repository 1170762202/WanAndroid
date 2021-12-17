package com.zlx.module_mine.aboutus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.databinding.AcAboutUsBinding;

/**
 * FileName: AboutUs
 * Created by zlx on 2020/9/22 11:31
 * Email: 1170762202@qq.com
 * Description:
 */
public class AboutUsAc extends BaseMvvmAc<AcAboutUsBinding,AboutUsViewModel> {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutUsAc.class));
    }
    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_about_us;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
