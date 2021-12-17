package com.zlx.sharelive.business.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_base.base_util.LogUtils;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class SplashViewModel extends BaseTopBarViewModel<SplashRepository> {

    public MutableLiveData<List<ProjectListRes>> projectListLiveData;

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ProjectListRes>> projectListLiveData() {
        if (projectListLiveData == null) {
            projectListLiveData = new MutableLiveData<>();
        }
        return projectListLiveData;
    }

    public void listProjectsTab() {
        model.listProjectsTab(new ApiCallback<List<ProjectListRes>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(@NonNull ApiResponse<List<ProjectListRes>> response) {
                projectListLiveData().postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }
}
