package com.zlx.module_mine.aboutauthor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.databinding.AcAboutAuthorBinding;


/**
 * Created by zlx on 2020/9/22 17:25
 * Email: 1170762202@qq.com
 * Description:
 */
public class AboutAuthorAc extends BaseMvvmAc<AcAboutAuthorBinding,AboutAuthorViewModel> {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutAuthorAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_about_author;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
