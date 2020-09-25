package com.zlx.library_common.util;

import com.zlx.library_common.module.ArticleApi;
import com.zlx.library_common.module.LoginApi;
import com.zlx.library_common.module.ProjectApi;
import com.zlx.library_common.module.TreeApi;
import com.zlx.library_common.module.UserApi;
import com.zlx.module_network.api1.livedata.RetrofitCreateLiveDataHelper;
import com.zlx.module_network.constrant.U;

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
