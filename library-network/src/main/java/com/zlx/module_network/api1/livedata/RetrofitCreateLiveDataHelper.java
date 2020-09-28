package com.zlx.module_network.api1.livedata;

import androidx.annotation.NonNull;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zlx.library_common.provier.AppProvider;
import com.zlx.module_network.interceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zlx on 2017/12/12.
 */
public class RetrofitCreateLiveDataHelper {
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;

    private static RetrofitCreateLiveDataHelper instance;

    public static RetrofitCreateLiveDataHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitCreateLiveDataHelper.class) {
                instance = new RetrofitCreateLiveDataHelper();
            }
        }
        return instance;
    }

    private RetrofitCreateLiveDataHelper() {
    }


    public <T> T create(String baseURL, Class<T> service) {
        return initRetrofitWithLiveData(baseURL, initOkHttp()).create(service);
    }
    /**
     * 初始化Retrofit
     */
    @NonNull
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
                .cookieJar(getCookieJar())
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .build();
    }
    private ClearableCookieJar cookieJar;

    public ClearableCookieJar getCookieJar() {
        if (cookieJar == null) {
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(AppProvider.getInstance().getApp()));
        }
        return cookieJar;
    }

}
