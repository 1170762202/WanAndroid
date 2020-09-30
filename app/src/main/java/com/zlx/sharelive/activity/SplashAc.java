package com.zlx.sharelive.activity;

import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.sharelive.R;

import java.util.List;

import butterknife.BindView;
import me.wangyuwei.particleview.ParticleView;

/**
 * Created by zlx on 2020/9/22 16:37
 * Email: 1170762202@qq.com
 * Description:
 */
public class SplashAc extends BaseAc {

    @BindView(R.id.particleview)
    ParticleView particleview;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_splash;
    }

    @Override
    public void initViews() {
        super.initViews();
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
        particleview.startAnim();

    }
}
