package com.zlx.module_project.activity;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_project.R;
import com.zlx.module_project.databinding.AcProjectBinding;

/**
 * Created by zlx on 2020/9/30 11:27
 * Email: 1170762202@qq.com
 * Description:
 */
public class ProjectAc extends BaseMvvmAc<AcProjectBinding, BaseViewModel> {

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_project;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
