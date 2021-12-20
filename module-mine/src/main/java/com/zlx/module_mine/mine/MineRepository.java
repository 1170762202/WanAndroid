package com.zlx.module_mine.mine;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

public class MineRepository extends BaseModel {
    public void getScore(ApiCallback<UserInfo> callback) {
        ApiUtil.getUserApi().getIntegral().enqueue(callback);
    }
}
