package com.zlx.module_mine.setting;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_base.base_util.CacheUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_mine.R;
import com.zlx.module_mine.aboutus.AboutUsAc;
import com.zlx.module_mine.language.LanguageSetAc;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.factory.ApiCallback;
import com.zlx.module_network.scheduler.IoMainScheduler;
import com.zlx.module_network.widget.popwindow.PopUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SettingViewModel extends BaseTopBarViewModel<SettingRepository> {
    public MutableLiveData<String> cacheData = new MutableLiveData<>();
    private FragmentActivity mContext;

    public SettingViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    public void onCacheClick() {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.mine_tip))
                .setMessage("确定要清除缓存?")
                .setNegativeButton(mContext.getString(R.string.cancel), null)
                .setPositiveButton(mContext.getString(R.string.sure), (dialogInterface, i) -> clearCache())
                .show();
    }

    public void onVersionClick() {
        PopUtil.show(mContext.getString(R.string.version_tip));
    }

    public void onDescClick() {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.mine_tip))
                .setMessage("本App中所有的Api均由WanAndroid官网提供，可作为学习参考，禁止作为商业用途")
                .setPositiveButton(mContext.getString(R.string.sure), null)
                .show();
    }

    public void onAboutClick() {
        AboutUsAc.launch(mContext);
    }

    public void onExitClick() {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.mine_tip))
                .setMessage(mContext.getString(R.string.sure_logout))
                .setPositiveButton(mContext.getString(R.string.sure), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    logout();
                }).setNegativeButton(mContext.getString(R.string.cancel), null).show();
    }

    public void onLanguageClick() {
        mContext.startActivity(new Intent(mContext, LanguageSetAc.class));
    }

    public void logout() {
        model.logout(new ApiCallback<ApiResponse>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NonNull ApiResponse<ApiResponse> response) {
                MMkvHelper.getInstance().logout();
                mContext.finish();
            }

            @Override
            public void onError(@NonNull Throwable t) {

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mContext = null;
    }

    public void getCache() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String totalCacheSize = CacheUtil.getTotalCacheSize();
            if (!emitter.isDisposed()) {
                emitter.onNext(totalCacheSize);
            }
        }).compose(new IoMainScheduler<>())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        cacheData.postValue(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void clearCache() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                CacheUtil.clearAllCache();
                String size = CacheUtil.getTotalCacheSize();
                if (!emitter.isDisposed()) {
                    emitter.onNext(size);
                }
            }
        }).compose(new IoMainScheduler<>())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String size) {
                        cacheData.postValue(size);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
