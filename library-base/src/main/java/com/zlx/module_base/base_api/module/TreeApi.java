package com.zlx.module_base.base_api.module;

import androidx.lifecycle.LiveData;

import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCall;

import java.util.List;

import retrofit2.http.GET;

/**
 * Copyright (C)
 * FileName: ArticleApi
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:48
 * Description: 体系api
 */
public interface TreeApi {

    /**
     * 获取轮播图
     *
     * @return
     */
    @GET("tree/json")
    ApiCall<List<TreeListRes>> listTrees();

    @GET("navi/json")
    ApiCall<List<TreeListRes>> listNavis();


}
