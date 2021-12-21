package com.zlx.sharelive.business.splash;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class SplashRepository extends BaseModel {

    public void listProjectsTab(ApiCallback<List<ProjectListRes>> callback) {
        ApiUtil.getProjectApi().listProjectsTab().enqueue(callback);
    }
}
