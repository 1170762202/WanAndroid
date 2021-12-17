package com.zlx.module_mine.myscore;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_api.res_data.RankListRes;
import com.zlx.module_mine.R;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.AbstractMap;

public class MyScoreViewModel extends BaseTopBarViewModel<MyScoreRepository> {
    public MutableLiveData<AbstractMap.SimpleEntry<Boolean, RankListRes>> scoreLiveData = new MutableLiveData<>();

    public MyScoreViewModel(@NonNull Application application) {
        super(application);
        setTitleText(application.getString(R.string.mine_integral));
    }

    public void listScore(boolean refresh) {
      model.listScore(refresh, new ApiCallback<RankListRes>() {
          @Override
          public void onStart() {

          }

          @Override
          public void onSuccess(@NonNull ApiResponse<RankListRes> response) {
              scoreLiveData.postValue(new AbstractMap.SimpleEntry<>(refresh, response.getData()));
          }

          @Override
          public void onError(@NonNull Throwable t) {

          }
      });
    }
}
