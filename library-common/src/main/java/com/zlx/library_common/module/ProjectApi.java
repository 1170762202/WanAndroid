package com.zlx.library_common.module;

import androidx.lifecycle.LiveData;

import com.zlx.library_common.res_data.ArticleListRes;
import com.zlx.library_common.res_data.ProjectListRes;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
  * FileName:
  * Created by zlx on 2020/9/18 13:44
  * Email: 1170762202@qq.com
  * Description: 
*/
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
