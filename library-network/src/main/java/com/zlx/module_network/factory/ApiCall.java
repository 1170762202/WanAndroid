package com.zlx.module_network.factory;

import android.content.Context;

import com.zlx.module_network.annotation.RetryCount;
import com.zlx.module_network.annotation.RetryDelay;
import com.zlx.module_network.annotation.RetryIncreaseDelay;
import com.zlx.module_network.bean.ApiResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class ApiCall <R> {

    private final Observable<Response<ApiResponse<R>>> mEnqueueObservable;
    private int mRetryCount;
    private long mRetryDelay;
    private long mRetryIncreaseDelay;
    private Disposable mDisposable;
    private Call<ApiResponse<R>> mCall;

    ApiCall(Annotation[] annotations, Call<ApiResponse<R>> call) {
        mCall = call;
        mEnqueueObservable = RxJavaPlugins.onAssembly(new CallEnqueueObservable<>(call));
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> clazz = annotation.annotationType();
            if (clazz == RetryCount.class) {
                RetryCount retryCount = (RetryCount) annotation;
                mRetryCount = retryCount.value();
            } else if (clazz == RetryDelay.class) {
                RetryDelay retryDelay = (RetryDelay) annotation;
                mRetryDelay = retryDelay.value();
            } else if (clazz == RetryIncreaseDelay.class) {
                RetryIncreaseDelay retryIncreaseDelay = (RetryIncreaseDelay) annotation;
                mRetryIncreaseDelay = retryIncreaseDelay.value();
            }
        }
    }

    /**
     * 进入请求队列
     * 不绑定activity生命周期
     * 建议使用传入activity的方式，以绑定生命周期
     *
     * @param callback 请求回调
     */
    public <T extends ApiCallback<R>> void enqueue(T callback) {
        enqueue(null, ProgressType.NONE, false, callback);
    }

    /**
     * 进入请求队列
     * 不绑定activity生命周期
     * 建议使用传入activity的方式，以绑定生命周期
     *
     * @param callback 请求回调
     */
    public <T extends ApiCallback<R>> void enqueue(T callback, ProgressType type) {
        enqueue(null, type, false, callback);
    }

    /**
     * 进入请求队列
     * 无进度框，无toast
     * 自动绑定activity生命周期
     *
     * @param callback 请求回调
     */
    public void enqueue(Context activity, final ApiCallback<R> callback) {
        enqueue(activity, ProgressType.NONE, false, callback);
    }

    /**
     * 进入请求队列
     * 带可取消进度框，有toast
     * 自动绑定activity生命周期
     *
     * @param callback 请求回调
     */
    public void enqueue2(Context activity, final ApiCallback<R> callback) {
        enqueue(activity, ProgressType.CANCELABLE, true, callback);
    }

    /**
     * 进入请求队列
     *
     * @param activity     界面
     * @param progressType 进度框类型
     * @param callback     请求回调
     */
    public void enqueue(Context activity, ProgressType progressType, final ApiCallback<R> callback) {
        enqueue(activity, progressType, false, callback);
    }

    /**
     * 进入请求队列
     *
     * @param activity   界面
     * @param toastError 是否弹错误toast
     * @param callback   请求回调
     */
    public void enqueue(Context activity, boolean toastError, final ApiCallback<R> callback) {
        enqueue(activity, ProgressType.NONE, toastError, callback);
    }

    /**
     * 进入请求队列
     *
     * @param activity     界面
     * @param progressType 进度框类型
     * @param toastError   是否弹错误toast
     * @param callback     请求回调
     */
    public void enqueue(Context activity, ProgressType progressType, final boolean toastError,
                        final ApiCallback<R> callback) {
        Observable<Response<ApiResponse<R>>> observable;
        /*if (activity instanceof RxAppCompatActivity) {
            RxAppCompatActivity rxAppCompatActivity = (RxAppCompatActivity) activity;
            observable = mEnqueueObservable.compose(rxAppCompatActivity.<Response<ApiResponse<R>>>bindToLifecycle());
        } else {
            observable = mEnqueueObservable;
        }*/
        observable = mEnqueueObservable;
        mDisposable = observable.retryWhen(new RetryHandler<R>(mRetryCount, mRetryDelay, mRetryIncreaseDelay))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        callback.onStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ApiResponse<R>>>() {
                    @Override
                    public void accept(Response<ApiResponse<R>> response) throws Exception {
                        ApiResponse<R> body = response.body();
                        if (!response.isSuccessful() || body == null) {
                            onError(callback, new HttpException(response), toastError);
                            cancel();
                            return;
                        }
                        callback.onSuccess(body);
                        cancel();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onError(callback, throwable, toastError);
                        cancel();
                    }
                });
    }

    /**
     * Synchronously send the request and return its response.
     *
     * @throws IOException if a problem occurred talking to the server.
     */
    public Response<ApiResponse<R>> exectue() throws IOException {
        return mCall.clone().execute();
    }


    /**
     * 处理错误
     *
     * @param callback  回调
     * @param throwable 错误
     */
    private void onError(ApiCallback<R> callback, Throwable throwable, boolean toast) {
        callback.onError(throwable);
    }

    public void cancel() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    /**
     * 进度条类型
     */
    public enum ProgressType {

        /**
         * 无进度条
         */
        NONE,
        /**
         * 可取消进度条
         */
        CANCELABLE,
        /**
         * 不可取消进度条
         */
        UN_CANCELABLE

    }
}

