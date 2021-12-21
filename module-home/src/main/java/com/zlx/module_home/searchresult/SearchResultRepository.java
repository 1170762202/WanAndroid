package com.zlx.module_home.searchresult;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.constant.PageImpl;
import com.zlx.module_network.factory.ApiCallback;

public class SearchResultRepository extends BaseModel {
    private PageImpl pageImpl = new PageImpl();

    public void unCollect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().unCollect(id).enqueue(callback);
    }

    public void collect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().collect(id).enqueue(callback);
    }

    public void search(boolean refresh, String searchContent, ApiCallback<ArticleListRes> callback) {
        if (refresh) {
            pageImpl.resetZero();
        } else {
            pageImpl.nextZeroPage();
        }
        ApiUtil.getArticleApi().search(pageImpl.zeroPage, searchContent).enqueue(callback);
    }

}
