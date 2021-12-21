package com.zlx.module_login.register;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

public class AccountRegisterRepository extends BaseModel {
    public void register(String u, String p, ApiCallback<UserInfo> callback) {
        ApiUtil.getLoginApi().register(u, p, p).enqueue(callback);
    }
}
