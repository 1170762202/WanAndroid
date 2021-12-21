package com.zlx.module_mine.mycollect;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

public class MyCollectRepository extends BaseModel {
    private int page = 0;

    public void listMyCollect(boolean refresh, ApiCallback<ArticleListRes> callback) {
        if (refresh) {
            page = 0;
        } else {
            page++;
        }
        ApiUtil.getArticleApi().listMyCollect(page).enqueue(callback);
    }
    public void unCollect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().unCollect(id).enqueue(callback);
    }
}
