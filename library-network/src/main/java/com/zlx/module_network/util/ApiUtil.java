package com.zlx.module_network.util;

import com.zlx.module_network.api1.livedata.RetrofitCreateLiveDataHelper;
import com.zlx.module_network.constrant.U;
import com.zlx.module_network.module.ArticleApi;
import com.zlx.module_network.module.ProjectApi;

public class ApiUtil {

//    public static ApiLiveDataService getApi() {
//        return RetrofitCreateLiveDataHelper.getInstance(U.BASE_URL).create();
//    }

    public static ProjectApi getProjectApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, ProjectApi.class);
    }

    public static ArticleApi getArticleApi() {
        return RetrofitCreateLiveDataHelper.getInstance().create(U.BASE_URL, ArticleApi.class);
    }


}
