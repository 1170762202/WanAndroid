package com.zlx.module_mine.scorerank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.RankBean;
import com.zlx.module_base.base_api.res_data.RankListRes;
import com.zlx.module_mine.R;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.AbstractMap;
import java.util.List;

public class ScoreRankViewModel extends BaseTopBarViewModel<ScoreRankRepository> {
    public MutableLiveData<AbstractMap.SimpleEntry<Boolean, List<RankBean>>> scoreRankLiveData = new MutableLiveData<>();

    public ScoreRankViewModel(@NonNull Application application) {
        super(application);
        setTitleText(application.getString(R.string.mine_scoreboard));
    }


    public void listRank(boolean refresh) {
        model.listRank(refresh, new ApiCallback<RankListRes>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<RankListRes> response) {
                scoreRankLiveData.postValue(new AbstractMap.SimpleEntry<>(refresh, response.getData().getDatas()));
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }
}
