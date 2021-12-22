package com.zlx.module_public.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.PublicAuthorListRes;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.AbstractMap;
import java.util.List;

public class PublicViewModel extends BaseViewModel<PublicRepository> {
    public MutableLiveData<AbstractMap.SimpleEntry<Boolean, List<ArticleBean>>> publicLiveData = new MutableLiveData<>();
    public MutableLiveData<List<PublicAuthorListRes>> authorLiveData = new MutableLiveData<>();

    public PublicViewModel(@NonNull Application application) {
        super(application);
    }

    public void listPublicAuthor() {
        model.listPublicAuthor(new ApiCallback<List<PublicAuthorListRes>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<List<PublicAuthorListRes>> response) {
                authorLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }

    public void listArticle(boolean refresh, String id) {
        model.listArticle(refresh, id, new ApiCallback<ArticleListRes>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<ArticleListRes> response) {
                publicLiveData.postValue(new AbstractMap.SimpleEntry<>(refresh, response.getData().getDatas()));
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
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

    public void collect(String id) {
        model.collect(id, new ApiCallback<Object>() {
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
}
