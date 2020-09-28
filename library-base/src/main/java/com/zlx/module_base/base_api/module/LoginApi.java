package com.zlx.module_base.base_api.module;

import androidx.lifecycle.LiveData;

import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_network.bean.ApiResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Copyright (C)
 * FileName: ArticleApi
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:48
 * Description: 体系api
 */
public interface LoginApi {

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    LiveData<ApiResponse<UserInfo>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    LiveData<ApiResponse<UserInfo>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @GET("user/logout/json")
    LiveData<ApiResponse> logout();
}
