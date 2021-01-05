package com.zlx.module_mine.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.utils.widget.ImageFilterButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.library_aop.checklogin.annotation.CheckLogin;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_mine.activity.AboutAuthorAc;
import com.zlx.module_mine.activity.MyCollectAc;
import com.zlx.module_mine.activity.MyScoreAc;
import com.zlx.module_mine.activity.MyShareAc;
import com.zlx.module_mine.activity.OpenSourceAc;
import com.zlx.module_mine.activity.ScoreRankListAc;
import com.zlx.module_mine.activity.SettingAc;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.widget.waveview.WaveView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 * FileName: MineFg
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 11:30
 * Description: 我的
 */
@Route(path = RouterFragmentPath.Mine.PAGER_MINE)
public class MineFg extends BaseFg {

    @BindView(R2.id.waveView)
    WaveView waveView;
    @BindView(R2.id.llHead)
    LinearLayout llHead;
    @BindView(R2.id.root)
    LinearLayout root;
    @BindView(R2.id.ivAvatar)
    ImageFilterButton ivAvatar;
    @BindView(R2.id.tvName)
    TextView tvName;
    @BindView(R2.id.tvId)
    TextView tvId;
    @BindView(R2.id.tvLevel)
    TextView tvLevel;
    @BindView(R2.id.tvMyScore)
    TextView tvMyScore;
    private UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_mine;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }

    @Override
    protected void initViews() {
        super.initViews();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llHead.getLayoutParams();
        waveView.setOnWaveAnimationListener(y -> {
            layoutParams.setMargins(0, (int) y, 0, 0);
            llHead.setLayoutParams(layoutParams);
        });
        getScore();
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfo = MMkvHelper.getInstance().getUserInfo();
        if (userInfo != null) {
            tvName.setText(userInfo.getUsername());
            getScore();
        } else {
            tvId.setText("");
            tvMyScore.setText("");
            tvName.setText(R.string.unlogin);
            tvLevel.setVisibility(View.GONE);
        }
    }


    private void getScore() {
        ApiUtil.getUserApi().getIntegral().observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(ApiResponse<UserInfo> data) {

                        tvLevel.setVisibility(View.VISIBLE);
                        UserInfo userInfo1 = data.getData();
                        tvId.setText(String.format("ID: %s", userInfo1.getUserId()));
                        tvLevel.setText(String.format("lv.%d", userInfo1.getLevel()));
                        tvMyScore.setText(String.format(getString(R.string.mine_current_score)+": %s", userInfo1.getCoinCount()));
                        if (userInfo != null) {
                            userInfo1.setUsername(userInfo.getUsername());
                        }
                        MMkvHelper.getInstance().saveUserInfo(userInfo1);
                    }
                }));
    }

    @CheckLogin
    @OnClick({R2.id.ivSet, R2.id.tvName, R2.id.tvScoreRankList, R2.id.llHead, R2.id.llScore,
            R2.id.llCollect, R2.id.llShare, R2.id.llProjects, R2.id.llAbout})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.ivSet) {
            SettingAc.launch(getContext());
        } else if (id == R.id.tvName) {
        } else if (id == R.id.tvScoreRankList) {
            ScoreRankListAc.launch(getContext());
        } else if (id == R.id.llHead) {
        } else if (id == R.id.llScore) {
            MyScoreAc.launch(getContext());
        } else if (id == R.id.llCollect) {
            MyCollectAc.launch(getContext());
        } else if (id == R.id.llShare) {
            MyShareAc.launch(getContext());
        } else if (id == R.id.llProjects) {
            OpenSourceAc.launch(getContext());
        } else if (id == R.id.llAbout) {
            AboutAuthorAc.launch(getContext());
        }
    }
}
