package com.zlx.module_login.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_login.R;

/**
 * FileName: LoginAc
 * Created by zlx on 2020/9/21 13:59
 * Email: 1170762202@qq.com
 * Description:
 */
@Route(path = RouterActivityPath.Login.PAGER_LOGIN)
public class LoginAc extends BaseAc {


    @Override
    protected int getLayoutId() {
        return R.layout.ac_login;
    }


}
