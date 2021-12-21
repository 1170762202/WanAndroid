package com.zlx.module_mine.mycollect;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_mine.R;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.AbstractMap;
import java.util.List;

public class MyCollectViewModel extends BaseTopBarViewModel<MyCollectRepository> {
    public MutableLiveData<AbstractMap.SimpleEntry<Boolean, List<ArticleBean>>> collectLiveData = new MutableLiveData<>();

    public MyCollectViewModel(@NonNull Application application) {
        super(application);
        setTitleText(application.getString(R.string.mine_collect));
    }
    public void unCollect(String id) {
        model.unCollect(id, new ApiCallback<Object>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<Object> response) {

            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }


    public void listMyCollect(boolean refresh) {
        model.listMyCollect(refresh, new ApiCallback<ArticleListRes>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<ArticleListRes> response) {
                List<ArticleBean> datas = response.getData().getDatas();
                for (ArticleBean articleBean : datas) {
                    articleBean.setCollect(true);
                }
                collectLiveData.postValue(new AbstractMap.SimpleEntry<>(refresh, datas));
            }

            @Override
            public void onError(@NonNull Throwable t) {
                collectLiveData.postValue(null);
            }
        });
    }
}
