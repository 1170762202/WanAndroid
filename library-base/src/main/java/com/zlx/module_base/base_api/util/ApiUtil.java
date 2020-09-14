package com.zlx.module_base.base_api.util;

import com.zlx.module_base.base_api.ApiService;
import com.zlx.module_base.constant.WebUrl;

public class ApiUtil {

    public static ApiService getApi(){
        return RetrofitCreateHelper.getApiUrlWithLiveData(WebUrl.BASE_URL);
    }
}
