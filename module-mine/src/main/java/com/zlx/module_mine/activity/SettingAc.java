package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.zlx.module_base.base_util.AppUtil;
import com.zlx.module_base.base_util.LanguageUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseVMAc;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_mine.viewmodel.MineViewModel;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.widget.popwindow.PopUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * FileName: SettingAc
 * Created by zlx on 2020/9/22 9:52
 * Email: 1170762202@qq.com
 * Description:
 */
public class SettingAc extends BaseVMAc<MineViewModel> {
    @BindView(R2.id.tvCache)
    TextView tvCache;
    @BindView(R2.id.tvVersion)
    TextView tvVersion;
    @BindView(R2.id.tvLanguage)
    TextView tvLanguage;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingAc.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_setting;
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle(getString(R.string.mine_set));
        initData();

        viewModel.getCache();
        tvVersion.setText(AppUtil.getVersion(this));
    }

    private void initData() {
        viewModel.cacheData.observe(this, s -> tvCache.setText(s));
        Locale currentLanguage = LanguageUtil.getCurrentLanguage();
        tvLanguage.setText(String.format("%s-%s", currentLanguage.getLanguage(), currentLanguage.getCountry()));
    }


    @OnClick({R2.id.llCache, R2.id.llVersion, R2.id.llDesc, R2.id.llAbout, R2.id.tvLogOut, R2.id.llLanguage})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.llCache) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.mine_tip))

                    .setMessage("确定要清除缓存?")
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setPositiveButton(getString(R.string.sure), (dialogInterface, i) -> viewModel.clearCache())
                    .show();
        } else if (id == R.id.llVersion) {
            PopUtil.show(getString(R.string.version_tip));
        } else if (id == R.id.llDesc) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.mine_tip))
                    .setMessage("本App中所有的Api均由WanAndroid官网提供，可作为学习参考，禁止作为商业用途")
                    .setPositiveButton(getString(R.string.sure), null)
                    .show();
        } else if (id == R.id.llAbout) {
            AboutUs.launch(this);
        } else if (id == R.id.tvLogOut) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.mine_tip))
                    .setMessage(getString(R.string.sure_logout))
                    .setPositiveButton(getString(R.string.sure), (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        ApiUtil.getLoginApi().logout().observe(this, new BaseObserver<>(new BaseObserverCallBack<ApiResponse>() {
                            @Override
                            public void onSuccess(ApiResponse data) {
                                MMkvHelper.getInstance().logout();
                                finish();
                            }
                        }));
                    }).setNegativeButton(getString(R.string.cancel), null).show();

        } else if (id == R.id.llLanguage) {
            startActivity(new Intent(this, LanguageSetAc.class));
        }
    }
}
