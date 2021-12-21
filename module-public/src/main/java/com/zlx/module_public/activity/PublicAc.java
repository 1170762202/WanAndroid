package com.zlx.module_public.activity;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_public.R;
import com.zlx.module_public.databinding.AcPublicBinding;

/**
 * Created by zlx on 2020/9/30 11:25
 * Email: 1170762202@qq.com
 * Description:
 */
public class PublicAc extends BaseMvvmAc<AcPublicBinding, BaseViewModel> {

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_public;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
