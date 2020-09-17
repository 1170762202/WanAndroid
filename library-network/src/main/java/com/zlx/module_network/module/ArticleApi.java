package com.zlx.module_network.module;

import androidx.lifecycle.LiveData;

import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.res_data.ArticleListRes;
import com.zlx.module_network.res_data.BannerRes;
import com.zlx.module_network.res_data.PublicAuthorListRes;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Copyright (C)
 * FileName: ArticleApi
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:48
 * Description:
 */
public interface ArticleApi {

    /**
     * 获取轮播图
     *
     * @return
     */
    @GET("banner/json")
    LiveData<ApiResponse<List<BannerRes>>> getBanner();



    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("article/list/{page}/json")
    LiveData<ApiResponse<ArticleListRes>> listArticle(@Path("page") int page);


    /**
     * 公众号文章列表
     *
     * @param id   公众号id
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    LiveData<ApiResponse<ArticleListRes>> listArticle(@Path("id") String id, @Path("page") int page);


    /**
     * 公众号作业列表
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    LiveData<ApiResponse<List<PublicAuthorListRes>>> listPublicAuthor();
}
