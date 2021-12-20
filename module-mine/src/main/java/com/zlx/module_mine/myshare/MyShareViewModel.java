package com.zlx.module_mine.myshare;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_api.res_data.MyShareBean;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_mine.sharearticle.ShareArticleAc;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.AbstractMap;

public class MyShareViewModel extends BaseViewModel<MyShareRepository> {
    public MutableLiveData<AbstractMap.SimpleEntry<Boolean, MyShareBean>> shareLiveData = new MutableLiveData<>();

    public MyShareViewModel(@NonNull Application application) {
        super(application);
    }

    public void listMyShare(boolean refresh){
        model.listMyShare(refresh, new ApiCallback<MyShareBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<MyShareBean> response) {
                shareLiveData.postValue(new AbstractMap.SimpleEntry<>(refresh, response.getData()));
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }
    public void deleteArticle(String id){
        model.deleteArticle(id, new ApiCallback<Object>() {
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
    public void onShareClick() {
        ShareArticleAc.launch(getApplication());
    }
}
