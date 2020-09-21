package com.zlx.module_network.api2;

import androidx.annotation.NonNull;

import com.zlx.module_network.interceptor.HttpLoggingInterceptor;
import com.zlx.module_network.service.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Zlx on 2017/12/12.
 */
public class RetrofitCreateHelper {
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;

    private static ApiService mApiUrl;
    private static RetrofitCreateHelper instance;

    public static RetrofitCreateHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitCreateHelper.class) {
                instance = new RetrofitCreateHelper();
            }
        }
        return instance;
    }

    private RetrofitCreateHelper() {
    }


    public <T> T create(String baseURL, Class<T> service) {
        return initRetrofit(baseURL, initOkHttp()).create(service);
    }


    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
                .addInterceptor(new HttpLoggingInterceptor("httpLog"))//添加打印拦截器
                .build();
    }


}
