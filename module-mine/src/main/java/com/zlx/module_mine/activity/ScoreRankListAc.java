package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.base_api.res_data.RankBean;
import com.zlx.module_base.base_api.res_data.RankListRes;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_mine.adapters.RvAdapterScoreRankList;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import butterknife.BindView;

/**
 * FileName: ScoreRankListAc
 * Created by zlx on 2020/9/21 15:49
 * Email: 1170762202@qq.com
 * Description:
 */
public class ScoreRankListAc extends BaseAc implements OnRefreshLoadMoreListener {


    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.tvName)
    TextView tvName;
    @BindView(R2.id.tvCoins)
    TextView tvCoins;
    @BindView(R2.id.ivLogo)
    ImageView ivLogo;
    @BindView(R2.id.tvLogo)
    TextView tvLogo;

    RvAdapterScoreRankList adapterScoreRankList;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ScoreRankListAc.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_score_rank_list;
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle(getString(R.string.mine_scoreboard));
        adapterScoreRankList = new RvAdapterScoreRankList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterScoreRankList);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        adapterScoreRankList.setOnItemClickListener((adapter, view, position) -> {
            List<RankBean> data = adapterScoreRankList.getData();
            RankBean datasBean = data.get(position);
        });
        UserInfo userInfo = MMkvHelper.getInstance().getUserInfo();
        tvName.setText(userInfo.getUsername());
        tvCoins.setText(userInfo.getCoinCount());
        String rank = userInfo.getRank();
        if (!TextUtils.isEmpty(rank)){
            if (rank.equals("1") || rank.equals("2") || rank.equals("3")) {
                ivLogo.setVisibility(View.VISIBLE);
                ivLogo.setImageResource(rank.equals("1") ? R.mipmap.gold_crown : (rank.equals("2") ? R.mipmap.silver_crown : R.mipmap.cooper_crown));
                tvLogo.setVisibility(View.GONE);
            } else {
                ivLogo.setVisibility(View.GONE);
                tvLogo.setVisibility(View.VISIBLE);
                tvLogo.setText(rank);
            }
        }else {
            tvLogo.setVisibility(View.GONE);
            ivLogo.setVisibility(View.GONE);
        }
        showLoading();
        listRank(true);
    }


    private int page = 1;

    private void listRank(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        ApiUtil.getUserApi().listScoreRank(page).observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<RankListRes>>() {
                    @Override
                    public void onSuccess(ApiResponse<RankListRes> data) {
                        if (refresh) {
                            adapterScoreRankList.setList(data.getData().getDatas());
                        } else {
                            adapterScoreRankList.addData(data.getData().getDatas());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishLoadMore();
                        smartRefreshLayout.finishRefresh();
                        showSuccess();
                    }
                }));

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        listRank(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        listRank(true);
    }
}
