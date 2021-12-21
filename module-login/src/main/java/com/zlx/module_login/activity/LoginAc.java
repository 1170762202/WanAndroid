package com.zlx.module_login.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_login.R;
import com.zlx.module_login.databinding.AcLoginBinding;

/**
 * FileName: LoginAc
 * Created by zlx on 2020/9/21 13:59
 * Email: 1170762202@qq.com
 * Description:
 */
@Route(path = RouterActivityPath.Login.PAGER_LOGIN)
public class LoginAc extends BaseMvvmAc<AcLoginBinding, BaseViewModel> {

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_login;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
