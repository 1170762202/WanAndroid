package com.zlx.module_mine.myshare;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.MyShareBean;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.constant.PageImpl;
import com.zlx.module_network.factory.ApiCallback;

public class MyShareRepository extends BaseModel {
    private final PageImpl pageImpl = new PageImpl();

    public void listMyShare(boolean refresh, ApiCallback<MyShareBean> callback) {
        if (refresh) {
            pageImpl.reset();
        } else {
            pageImpl.nextPage();
        }
        ApiUtil.getArticleApi().listMyShare(pageImpl.page).enqueue(callback);
    }

    public void deleteArticle(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().deleteArticle(id).enqueue(callback);
    }
    public void unCollect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().unCollect(id).enqueue(callback);
    }

    public void collect(String id, ApiCallback<Object> callback) {
        ApiUtil.getArticleApi().collect(id).enqueue(callback);
    }
}
