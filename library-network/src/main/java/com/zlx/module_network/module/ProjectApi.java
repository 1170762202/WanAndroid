package com.zlx.module_network.module;

import androidx.lifecycle.LiveData;

import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.res_data.ArticleListRes;
import com.zlx.module_network.res_data.ProjectListRes;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectApi {


    /**
     * 项目分类
     *
     * @return
     */
    @GET("project/tree/json")
    LiveData<ApiResponse<List<ProjectListRes>>> listProjectsTab();


    /**
     * 项目列表
     *
     * @param id   分类id
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("project/list/{page}/json")
    LiveData<ApiResponse<ArticleListRes>> listProjects(@Path("page") int page, @Query("cid") String id);

}
