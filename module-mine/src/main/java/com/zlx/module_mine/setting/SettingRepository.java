package com.zlx.module_mine.setting;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

public class SettingRepository extends BaseModel {

    public void logout(ApiCallback<Object> callback){
        ApiUtil.getLoginApi().logout2().enqueue(callback);
    }
}
