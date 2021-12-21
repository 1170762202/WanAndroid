package com.zlx.module_home.home;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.BannerRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class HomeRepository extends BaseModel {

    public void unCollect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().unCollect(id).enqueue(callback);
    }

    public void collect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().collect(id).enqueue(callback);
    }

    public void getBanner(ApiCallback<List<BannerRes>> callback) {
        ApiUtil.getArticleApi().getBanner().enqueue(callback);
    }

    int page = 0;

    public void listArticle(boolean refresh, ApiCallback<ArticleListRes> callback) {
        if (!refresh) {
            page++;
        } else {
            page = 0;
        }
        ApiUtil.getArticleApi().listArticle(page).enqueue(callback);
    }
}
