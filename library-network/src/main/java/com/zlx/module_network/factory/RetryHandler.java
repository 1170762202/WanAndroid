package com.zlx.module_network.factory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class RetryHandler<T> implements Function<Observable<Throwable>, Observable<T>> {

    private int retryCount;
    private long retryDelay;
    private long increaseDelay;

    public RetryHandler(int retryCount, long retryDelay, long increaseDelay) {
        this.retryCount = retryCount;
        this.retryDelay = retryDelay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable apply(Observable<Throwable> throwableObservable) {
        return throwableObservable.zipWith(Observable.range(1, retryCount + 1),
                new BiFunction<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper apply(Throwable throwable, Integer integer)
                            throws Exception {
                        return new Wrapper(throwable, integer);
                    }
                }).flatMap(new Function<Wrapper, Observable<Long>>() {
            @Override
            public Observable<Long> apply(Wrapper wrapper) {
                //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                if (canRetry(wrapper)) {
                    return Observable.timer(retryDelay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
                }
                return Observable.error(wrapper.throwable);
            }
        });
    }

    private boolean canRetry(Wrapper wrapper) {
        return (wrapper.throwable instanceof ConnectException || wrapper.throwable instanceof SocketTimeoutException ||
                wrapper.throwable instanceof UnknownHostException ||
                wrapper.throwable instanceof TimeoutException) && wrapper.index < retryCount + 1;
    }

    public class Wrapper {

        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}