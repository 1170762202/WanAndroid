package com.zlx.module_base.base_api.module;

import androidx.lifecycle.LiveData;

import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_network.annotation.RetryCount;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCall;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Callback;
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
    @RetryCount(value = 3)
    @GET("project/tree/json")
    ApiCall<List<ProjectListRes>> listProjectsTab();

    /**
     * 项目列表
     *
     * @param id   分类id
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("project/list/{page}/json")
    ApiCall<ArticleListRes> listProjects(@Path("page") int page, @Query("cid") String id);


}
