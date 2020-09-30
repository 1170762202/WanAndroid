package com.zlx.module_mine.activity;

import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_mine.R;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;

/**
 * Created by zlx on 2020/9/30 11:28
 * Email: 1170762202@qq.com
 * Description: 需要结束进程重新打开该module
 */
public class MineAc extends BaseAc {
    @Override
    protected int getLayoutId() {
        return R.layout.ac_mine;
    }

    @Override
    public void beforeOnCreate() {
        super.beforeOnCreate();
        ApiUtil.getLoginApi().login("zlxx", "111111").observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(ApiResponse<UserInfo> data) {
                        MMkvHelper.getInstance().saveUserInfo(data.getData());
                    }

                    @Override
                    public boolean showErrorMsg() {
                        return true;
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                }));
    }

}
