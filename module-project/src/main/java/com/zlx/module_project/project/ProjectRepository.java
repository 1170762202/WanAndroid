package com.zlx.module_project.project;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class ProjectRepository extends BaseModel {
    public List<ProjectListRes> listLocalProjectTabs() {
        return MMkvHelper.getInstance().getProjectTabs(ProjectListRes.class);
    }
    public void unCollect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().unCollect(id).enqueue(callback);
    }

    public void collect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().collect(id).enqueue(callback);
    }
    public void listProjectsTab(ApiCallback<List<ProjectListRes>> callback) {
        ApiUtil.getProjectApi().listProjectsTab().enqueue(callback);
    }

    int page = 0;

    public void listProjects(boolean refresh, String id, ApiCallback<ArticleListRes> callback) {
        if (refresh) {
            page = 0;
        } else {
            page++;
        }
        ApiUtil.getProjectApi().listProjects(page, id).enqueue(callback);
    }
}
