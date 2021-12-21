package com.zlx.module_home.search;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.library_db.entity.SearchHistoryEntity;
import com.zlx.module_base.base_api.res_data.SearchBeanRes;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class SearchViewModel extends BaseViewModel<SearchRepository> {
    public MutableLiveData<List<SearchBeanRes>> hotLiveData = new MutableLiveData<>();
    public MutableLiveData<List<SearchHistoryEntity>> localLiveData = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }


    public void deleteLocalHis() {
        model.deleteLocalHis();
    }

    public void deleteLocalHisById(long id) {
        model.deleteLocalHisById(id);
    }

    public void selectLocalHis() {
        List<SearchHistoryEntity> localHis = model.selectLocalHis();
        localLiveData.postValue(localHis);
    }

    public long insertPerson(SearchHistoryEntity entity) {
        return model.insertPerson(entity);
    }

    public void saveDB(String trim) {
        List<SearchHistoryEntity> searchHistoryEntities = model.selectLocalHis();
        long id = 0;
        for (SearchHistoryEntity searchHistoryEntity : searchHistoryEntities) {
            if (TextUtils.equals(searchHistoryEntity.getName(), trim)) {
                id = searchHistoryEntity.getId();
                break;
            }
        }
        if (id != 0) {
            model.deleteLocalHisById(id);
        }
        model.insertPerson(new SearchHistoryEntity(trim));
    }

    public void hotSearch() {
        model.hotSearch(new ApiCallback<List<SearchBeanRes>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<List<SearchBeanRes>> response) {
                hotLiveData.postValue(response.getData());
            }

            @Override
            public void onError(@NonNull Throwable t) {
                hotLiveData.postValue(null);
            }
        });
    }
}
