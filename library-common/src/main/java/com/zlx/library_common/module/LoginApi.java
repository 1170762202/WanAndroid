package com.zlx.library_common.module;

import androidx.lifecycle.LiveData;

import com.zlx.library_common.res_data.NaviListRes;
import com.zlx.library_common.res_data.TreeListRes;
import com.zlx.library_common.res_data.UserInfo;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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


}
