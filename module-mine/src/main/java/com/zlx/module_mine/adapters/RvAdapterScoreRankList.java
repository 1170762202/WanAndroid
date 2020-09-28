package com.zlx.module_mine.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlx.module_base.base_api.res_data.RankBean;
import com.zlx.module_mine.R;

import org.jetbrains.annotations.NotNull;

/**
 * FileName: RvAdapterScoreRankList
 * Created by zlx on 2020/9/21 15:56
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterScoreRankList extends BaseQuickAdapter<RankBean, BaseViewHolder> {
    public RvAdapterScoreRankList() {
        super(R.layout.rv_item_score_rank_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RankBean rankListRes) {
        String rank = rankListRes.getRank();
        baseViewHolder.setGone(R.id.ivLogo, (!rank.equals("1") && !rank.equals("2") && !rank.equals("3")))
                .setImageResource(R.id.ivLogo, rank.equals("1") ? R.mipmap.gold_crown : (rank.equals("2") ? R.mipmap.silver_crown : R.mipmap.cooper_crown))
                .setGone(R.id.tvLogo, (rank.equals("1") || rank.equals("2") || rank.equals("3")))
                .setText(R.id.tvLogo, rank)
                .setText(R.id.tvName, rankListRes.getUsername())
                .setText(R.id.tvCoins, rankListRes.getCoinCount() + "");

    }
}
