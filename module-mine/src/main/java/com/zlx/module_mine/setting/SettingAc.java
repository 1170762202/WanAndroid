package com.zlx.module_mine.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_util.AppUtil;
import com.zlx.module_base.base_util.LanguageUtil;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.databinding.AcSettingBinding;

import java.util.Locale;

/**
 * FileName: SettingAc
 * Created by zlx on 2020/9/22 9:52
 * Email: 1170762202@qq.com
 * Description:
 */
public class SettingAc extends BaseMvvmAc<AcSettingBinding, SettingViewModel> {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle(getString(R.string.mine_set));
        viewModel.register(this);
        initData();

        viewModel.getCache();
        binding.tvVersion.setText(AppUtil.getVersion(this));
    }

    private void initData() {
        viewModel.cacheData.observe(this, s -> binding.tvCache.setText(s));
        Locale currentLanguage = LanguageUtil.getCurrentLanguage();
        binding.tvLanguage.setText(String.format("%s-%s", currentLanguage.getLanguage(), currentLanguage.getCountry()));
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
