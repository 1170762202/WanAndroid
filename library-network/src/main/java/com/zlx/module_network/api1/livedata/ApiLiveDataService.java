package com.zlx.module_network.api1.livedata;

import androidx.lifecycle.LiveData;

import retrofit2.http.POST;

public interface ApiLiveDataService {
    @POST
    LiveData<String> post();
}
