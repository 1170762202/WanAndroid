package com.zlx.library_common.module;

import androidx.lifecycle.LiveData;

import com.zlx.library_common.res_data.ArticleListRes;
import com.zlx.library_common.res_data.BannerRes;
import com.zlx.library_common.res_data.PublicAuthorListRes;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Copyright (C)
 * FileName: ArticleApi
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:48
 * Description: 文章api
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


    /**
     * 知识体系下的文章
     *
     * @param page
     * @param id
     * @return
     */
    @GET("article/list/{page}/json")
    LiveData<ApiResponse<ArticleListRes>> listArticle(@Path("page") int page, @Query("cid") String id);


    /**
     * 我的收藏列表
     *
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    LiveData<ApiResponse<ArticleListRes>> listMyCollect(@Path("page") int page);

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    LiveData<ApiResponse> collect(@Path("id") String id);

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    LiveData<ApiResponse> unCollect(@Path("id") String id);

}
