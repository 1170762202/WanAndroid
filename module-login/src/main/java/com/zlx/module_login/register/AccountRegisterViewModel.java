package com.zlx.module_login.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

public class AccountRegisterViewModel extends BaseViewModel<AccountRegisterRepository> {
    public MutableLiveData<UserInfo> registerLiveData = new MutableLiveData<>();

    public AccountRegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(String u, String p) {
        model.register(u, p, new ApiCallback<UserInfo>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<UserInfo> response) {
                registerLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {
                registerLiveData.postValue(null);
            }
        });
    }
}
