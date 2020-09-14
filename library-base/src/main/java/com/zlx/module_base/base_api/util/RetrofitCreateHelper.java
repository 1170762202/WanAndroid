package com.zlx.module_base.base_api.util;

import androidx.annotation.NonNull;

import com.zlx.module_base.base_api.ApiService;
import com.zlx.module_base.base_api.interceptor.BodyInterceptor;
import com.zlx.module_base.base_api.interceptor.LogInterceptor;
import com.zlx.module_base.constant.WebUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Zlx on 2017/12/12.
 */
public class RetrofitCreateHelper {
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;

    private static ApiService mApiUrl;

    private RetrofitCreateHelper() {
    }

    /**
     * 单例模式
     */
    public static ApiService getApiUrl() {
        if (mApiUrl == null) {
            synchronized (RetrofitCreateHelper.class) {
                if (mApiUrl == null) {
                    mApiUrl = new RetrofitCreateHelper().getRetrofit();
                }
            }
        }
        return mApiUrl;
    }

    public static ApiService getApiUrlWithLiveData(String baseURL) {
        mApiUrl = new RetrofitCreateHelper().getRetrofitWithLiveData(baseURL);
        return mApiUrl;
    }

    private ApiService getRetrofit() {
        // 初始化Retrofit
        ApiService apiUrl = initRetrofit(initOkHttp()).create(ApiService.class);
        return apiUrl;
    }

    private ApiService getRetrofitWithLiveData(String baseURL) {
        // 初始化Retrofit
        ApiService apiUrl = initRetrofitWithLiveData(baseURL,initOkHttp()).create(ApiService.class);
        return apiUrl;
    }


    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(WebUrl.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private Retrofit initRetrofitWithLiveData(String baseURL,OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseURL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        return new OkHttpClient().newBuilder()
                .readTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//设置写入超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                //失败重连
                .retryOnConnectionFailure(true)
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .addInterceptor(new BodyInterceptor())
                .build();
    }


}
