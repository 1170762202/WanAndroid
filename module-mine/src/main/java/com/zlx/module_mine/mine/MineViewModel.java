package com.zlx.module_mine.mine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.zlx.library_aop.checklogin.annotation.CheckLogin;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_mine.aboutauthor.AboutAuthorAc;
import com.zlx.module_mine.mycollect.MyCollectAc;
import com.zlx.module_mine.myscore.MyScoreAc;
import com.zlx.module_mine.myshare.MyShareAc;
import com.zlx.module_mine.opensource.OpenSourceAc;
import com.zlx.module_mine.setting.SettingAc;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.lang.ref.WeakReference;

public class MineViewModel extends BaseViewModel<MineRepository> {

    public MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();
    private WeakReference<FragmentActivity> mContext;

    public MineViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(FragmentActivity activity) {
        mContext = new WeakReference<>(activity);
    }

    public void getScore() {
        model.getScore(new ApiCallback<UserInfo>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<UserInfo> response) {
                MMkvHelper.getInstance().saveUserInfo(response.getData());
                userInfoLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }

    public void onSetClick() {
        onItemClick(99);
    }

    @CheckLogin
    public void onItemClick(int pos) {
        switch (pos) {
            case 0:
                MyScoreAc.launch(mContext.get());
                break;
            case 1:
                MyCollectAc.launch(mContext.get());
                break;
            case 2:
                MyShareAc.launch(mContext.get());
                break;
            case 3:
                OpenSourceAc.launch(mContext.get());
                break;
            case 4:
                AboutAuthorAc.launch(mContext.get());
                break;
            case 99:
                SettingAc.launch(mContext.get());
                break;
        }
    }
}
