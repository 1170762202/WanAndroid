package com.zlx.module_mine.myscore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.RankBean;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.constant.C;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.adapters.RvAdapterMyScoreHeader;
import com.zlx.module_mine.adapters.RvAdapterMyScoreList;
import com.zlx.module_mine.databinding.AcMyScoreBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: MyScoreAc
 * Created by zlx on 2020/9/21 17:52
 * Email: 1170762202@qq.com
 * Description: 我的积分
 */
public class MyScoreAc extends BaseMvvmAc<AcMyScoreBinding, MyScoreViewModel> implements OnRefreshLoadMoreListener {

    private VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
    private DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
    private final ArrayList<DelegateAdapter.Adapter> adapters = new ArrayList<>();

    private RvAdapterMyScoreHeader adapterMyScoreHeader;
    private RvAdapterMyScoreList adapterMyScoreList;
    private List<RankBean> dataList = new ArrayList<>();


    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyScoreAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();
        setRightImg(R.mipmap.ic_question);
        setOnRightImgClickListener(view -> {
            RouterUtil.launchWeb(C.INTERGRAL_URL);
        });

        adapterMyScoreHeader = new RvAdapterMyScoreHeader();
        adapters.add(adapterMyScoreHeader);
        adapterMyScoreList = new RvAdapterMyScoreList(dataList);
        adapters.add(adapterMyScoreList);
        delegateAdapter.setAdapters(adapters);
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        binding.recyclerView.setAdapter(delegateAdapter);

        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        viewModel.scoreLiveData.observe(this, booleanRankListResSimpleEntry -> {
            if (booleanRankListResSimpleEntry != null) {
                if (booleanRankListResSimpleEntry.getKey()) {
                    dataList.clear();
                }
                dataList.addAll(booleanRankListResSimpleEntry.getValue().getDatas());
                adapterMyScoreList.notifyDataSetChanged();
            }
            binding.smartRefreshLayout.finishRefresh();
            binding.smartRefreshLayout.finishLoadMore();
            showSuccess();
        });
        showLoading();
        viewModel.listScore(true);

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listScore(false);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listScore(true);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_my_score;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
