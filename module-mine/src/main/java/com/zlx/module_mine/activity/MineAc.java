package com.zlx.module_mine.activity;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_mine.R;
import com.zlx.module_mine.databinding.AcMineBinding;

/**
 * Created by zlx on 2020/9/30 11:28
 * Email: 1170762202@qq.com
 * Description: 需要结束进程重新打开该module
 */
public class MineAc extends BaseMvvmAc<AcMineBinding, BaseViewModel> {

    @Override
    public void beforeOnCreate() {
        super.beforeOnCreate();

    }

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_mine;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
