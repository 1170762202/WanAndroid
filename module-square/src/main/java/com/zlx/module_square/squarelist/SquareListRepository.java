package com.zlx.module_square.squarelist;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

public class SquareListRepository extends BaseModel {

    public void unCollect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().unCollect(id).enqueue(callback);
    }

    public void collect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().collect(id).enqueue(callback);
    }

    private int page = 0;

    public void listArticle(boolean refresh, String id, ApiCallback<ArticleListRes> callback) {
        if (!refresh) {
            page++;
        } else {
            page = 0;
        }
        ApiUtil.getArticleApi().listArticle(page, id).enqueue(callback);
    }
}
