package com.zlx.module_login.activity;

import com.zlx.library_common.MMkvHelper;
import com.zlx.library_common.res_data.ProjectListRes;
import com.zlx.library_common.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_login.R;
import com.zlx.module_login.R2;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import butterknife.BindView;
import me.wangyuwei.particleview.ParticleView;

/**
 * Created by zlx on 2020/9/22 16:37
 * Email: 1170762202@qq.com
 * Description:
 */
public class SplashAc extends BaseAc {

    @BindView(R2.id.particleview)
    ParticleView particleview;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_splash;
    }

    @Override
    public void initViews() {
        super.initViews();
        particleview.startAnim();

        ApiUtil.getProjectApi().listProjectsTab().observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<ProjectListRes>>>() {
                    @Override
                    public void onSuccess(ApiResponse<List<ProjectListRes>> data) {
                        List<ProjectListRes> dataData = data.getData();
                        if (dataData.size() > 0) {
                            particleview.setOnParticleAnimListener(() -> {
                                MMkvHelper.getInstance().saveProjectTabs(dataData);
                                RouterUtil.launchMain();
                                finish();
                            });
                        }
                    }
                }));
    }
}
