package com.zlx.module_login.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

public class AccountLoginViewModel extends BaseViewModel<AccountLoginRepository> {
    public MutableLiveData<UserInfo> loginLiveData = new MutableLiveData<>();

    public AccountLoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String u, String p) {
        model.login(u, p, new ApiCallback<UserInfo>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<UserInfo> response) {
                loginLiveData.postValue(response.getData());
                MMkvHelper.getInstance().saveUserInfo(response.getData());
                onBackPressed();
            }

            @Override
            public void onError(@NonNull Throwable t) {
                loginLiveData.postValue(null);
            }
        });
    }
}
