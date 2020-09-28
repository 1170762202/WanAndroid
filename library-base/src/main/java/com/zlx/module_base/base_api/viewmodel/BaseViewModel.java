package com.zlx.module_base.base_api.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseViewModel extends AndroidViewModel implements IBaseViewModel, Consumer<Disposable> {

    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected void addDisposable(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }


    @Override
    public void accept(Disposable disposable) throws Exception {
        addDisposable(disposable);
    }
}
