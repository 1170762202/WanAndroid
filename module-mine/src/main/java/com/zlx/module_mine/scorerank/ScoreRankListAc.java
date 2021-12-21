package com.zlx.module_mine.scorerank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.RankBean;
import com.zlx.module_base.base_api.res_data.UserInfo;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.adapters.RvAdapterScoreRankList;
import com.zlx.module_mine.databinding.AcScoreRankListBinding;

import java.util.List;

/**
 * FileName: ScoreRankListAc
 * Created by zlx on 2020/9/21 15:49
 * Email: 1170762202@qq.com
 * Description:
 */
public class ScoreRankListAc extends BaseMvvmAc<AcScoreRankListBinding, ScoreRankViewModel> implements OnRefreshLoadMoreListener {

    RvAdapterScoreRankList adapterScoreRankList;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ScoreRankListAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();
        adapterScoreRankList = new RvAdapterScoreRankList();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapterScoreRankList);
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        adapterScoreRankList.setOnItemClickListener((adapter, view, position) -> {
            List<RankBean> data = adapterScoreRankList.getData();
        });
        UserInfo userInfo = MMkvHelper.getInstance().getUserInfo();
        binding.tvName.setText(userInfo.getUsername());
        binding.tvCoins.setText(userInfo.getCoinCount());
        String rank = userInfo.getRank();
        if (!TextUtils.isEmpty(rank)) {
            if (rank.equals("1") || rank.equals("2") || rank.equals("3")) {
                binding.ivLogo.setVisibility(View.VISIBLE);
                binding.ivLogo.setImageResource(rank.equals("1") ? R.mipmap.gold_crown : (rank.equals("2") ? R.mipmap.silver_crown : R.mipmap.cooper_crown));
                binding.tvLogo.setVisibility(View.GONE);
            } else {
                binding.ivLogo.setVisibility(View.GONE);
                binding.tvLogo.setVisibility(View.VISIBLE);
                binding.tvLogo.setText(rank);
            }
        } else {
            binding.tvLogo.setVisibility(View.GONE);
            binding.ivLogo.setVisibility(View.GONE);
        }
        viewModel.scoreRankLiveData.observe(this, booleanListSimpleEntry -> {
            if (booleanListSimpleEntry != null) {
                List<RankBean> value = booleanListSimpleEntry.getValue();
                if (booleanListSimpleEntry.getKey()) {
                    adapterScoreRankList.setList(value);
                } else {
                    adapterScoreRankList.addData(value);
                }
            }
            binding.smartRefreshLayout.finishLoadMore();
            binding.smartRefreshLayout.finishRefresh();                        showSuccess();
            showSuccess();
        });
        showLoading();
        viewModel.listRank(true);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listRank(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listRank(true);
    }

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_score_rank_list;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }
}
