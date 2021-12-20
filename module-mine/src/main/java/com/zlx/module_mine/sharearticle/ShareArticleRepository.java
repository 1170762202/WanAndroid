package com.zlx.module_mine.sharearticle;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

public class ShareArticleRepository extends BaseModel {

    public void shareArticle(String title, String link, ApiCallback<Object> callback){
        ApiUtil.getArticleApi().shareArticle(title,link).enqueue(callback);
    }
}
