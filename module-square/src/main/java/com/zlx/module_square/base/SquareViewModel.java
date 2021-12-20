package com.zlx.module_square.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class SquareViewModel extends BaseViewModel<SquareRepository> {
    public MutableLiveData<List<TreeListRes>> treesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<TreeListRes>> navisLiveData = new MutableLiveData<>();


    public SquareViewModel(@NonNull Application application) {
        super(application);
    }


    public void listTrees() {
        model.listTrees(new ApiCallback<List<TreeListRes>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<List<TreeListRes>> response) {
                treesLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {
                treesLiveData.postValue(null);
            }
        });
    }

    public void listNavis() {
        model.listNavis(new ApiCallback<List<TreeListRes>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<List<TreeListRes>> response) {
                navisLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {
                navisLiveData.postValue(null);
            }
        });
    }
}
