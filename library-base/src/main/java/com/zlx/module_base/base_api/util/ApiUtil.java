package com.zlx.module_base.base_api.util;

import com.zlx.module_base.base_api.module.ArticleApi;
import com.zlx.module_base.base_api.module.LoginApi;
import com.zlx.module_base.base_api.module.ProjectApi;
import com.zlx.module_base.base_api.module.TreeApi;
import com.zlx.module_base.base_api.module.UserApi;
import com.zlx.module_network.api1.livedata.RetrofitCreateLiveDataHelper;
import com.zlx.module_network.constrant.U;

/**
 * Created by zlx on 2020/9/28 15:09
 * Email: 1170762202@qq.com
 * Description: 不同模块BASE_URL可能不同
 */
public class ApiUtil {

    public static ProjectApi getProjectApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, ProjectApi.class);
    }

    public static ArticleApi getArticleApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, ArticleApi.class);
    }

    public static TreeApi getTreeApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, TreeApi.class);
    }

    public static LoginApi getLoginApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, LoginApi.class);
    }

    public static UserApi getUserApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, UserApi.class);
    }


}
