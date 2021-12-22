package com.zlx.sharelive.business.splash;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.sharelive.BR;
import com.zlx.sharelive.R;
import com.zlx.sharelive.business.splash.SplashViewModel;
import com.zlx.sharelive.databinding.AcSplashLayoutBinding;

/**
 * Created by zlx on 2020/9/22 16:37
 * Email: 1170762202@qq.com
 * Description:
 */
public class SplashAc extends BaseMvvmAc<AcSplashLayoutBinding, SplashViewModel> {

    @Override
    public void initViews() {
        super.initViews();
        viewModel.projectListLiveData().observe(this, projectListRes -> {
            if (projectListRes.size() > 0) {
                MMkvHelper.getInstance().saveProjectTabs(projectListRes);
            }
        });
        viewModel.listProjectsTab();

        binding.particleview.setOnParticleAnimListener(() -> {
            RouterUtil.launchMain();
            finish();
        });

        binding.particleview.startAnim();

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_splash_layout;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
