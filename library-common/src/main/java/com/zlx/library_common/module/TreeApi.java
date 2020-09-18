package com.zlx.library_common.module;

import androidx.lifecycle.LiveData;

import com.zlx.library_common.res_data.NaviListRes;
import com.zlx.library_common.res_data.TreeListRes;
import com.zlx.module_network.bean.ApiResponse;

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
    LiveData<ApiResponse<List<TreeListRes>>> listTrees();

    @GET("navi/json")
    LiveData<ApiResponse<List<NaviListRes>>> listNavis();


}
