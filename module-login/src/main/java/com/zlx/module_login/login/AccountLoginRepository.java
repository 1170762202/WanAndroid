package com.zlx.module_login.login;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

public class AccountLoginRepository extends BaseModel {
    public void login(String u, String p, ApiCallback<UserInfo> callback) {
        ApiUtil.getLoginApi().login(u, p).enqueue(callback);
    }
}
