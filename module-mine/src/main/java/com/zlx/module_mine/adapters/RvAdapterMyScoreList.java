package com.zlx.module_mine.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.zlx.module_base.base_api.res_data.RankBean;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FileName: RvAdapterMyScoreHeader
 * Created by zlx on 2020/9/22 9:11
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterMyScoreList extends DelegateAdapter.Adapter<RvAdapterMyScoreList.ViewHolder> {


    private List<RankBean> dataList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RvAdapterMyScoreList(List<RankBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_my_score_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankBean rankBean = dataList.get(position);
        holder.tvScore.setText(String.format("+%s", rankBean.getCoinCount()));
        holder.tvTitle.setText(rankBean.getReason());
        holder.tvTime.setText(simpleDateFormat.format(new Date(rankBean.getDate())));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.llItem)
        LinearLayout llItem;
        @BindView(R2.id.tvTitle)
        TextView tvTitle;
        @BindView(R2.id.tvTime)
        TextView tvTime;
        @BindView(R2.id.tvScore)
        TextView tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
