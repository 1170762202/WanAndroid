package com.zlx.module_mine.sharearticle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;
import com.zlx.module_network.widget.popwindow.PopUtil;

public class ShareArticleViewModel extends BaseTopBarViewModel<ShareArticleRepository> {

    public MutableLiveData<Object> shareLiveData = new MutableLiveData<>();
    public ShareArticleViewModel(@NonNull Application application) {
        super(application);
        setTitleText("分享文章");
    }

    public void shareArticle(String title, String link) {
        model.shareArticle(title, link, new ApiCallback<Object>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<Object> response) {
                PopUtil.show("分享成功", () -> onBackPressed());
                shareLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {
                shareLiveData.postValue(t.getMessage());
            }
        });
    }
}
