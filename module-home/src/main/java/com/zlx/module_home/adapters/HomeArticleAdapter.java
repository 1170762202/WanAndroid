package com.zlx.module_home.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_network.res_data.ArticleListRes;
import com.zlx.module_home.R;
import com.zlx.module_home.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeArticleAdapter extends DelegateAdapter.Adapter<HomeArticleAdapter.ViewHolder> {

    private List<ArticleListRes.DatasBean> articleListResList;

    private boolean hasTop = false;

    public void setHasTop(boolean hasTop) {
        this.hasTop = hasTop;
    }

    public HomeArticleAdapter(List<ArticleListRes.DatasBean> articleListResList) {
        this.articleListResList = articleListResList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_home_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleListRes.DatasBean datasBean = articleListResList.get(position);
        holder.tvContent.setText(datasBean.getTitle());
        holder.tvAuthor.setText(TextUtils.isEmpty(datasBean.getAuthor()) ? datasBean.getShareUser() : datasBean.getAuthor());
        holder.tvChapter.setText(String.format("%s·%s", datasBean.getSuperChapterName(), datasBean.getChapterName()));
        holder.tvTime.setText(datasBean.getNiceDate());
        holder.tvRefresh.setVisibility(datasBean.isFresh() ? View.VISIBLE : View.GONE);
        if (position == 0 && hasTop) {
            holder.top.setVisibility(View.VISIBLE);
        } else {
            holder.top.setVisibility(View.GONE);
        }

        holder.vItem.setOnClickListener(view -> {
            RouterUtil.launchWeb(articleListResList.get(position).getLink());
        });
    }

    @Override
    public int getItemCount() {
        return articleListResList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tvChapter)
        TextView tvChapter;
        @BindView(R2.id.tvTime)
        TextView tvTime;
        @BindView(R2.id.tvContent)
        TextView tvContent;
        @BindView(R2.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R2.id.tvRefresh)
        TextView tvRefresh;
        @BindView(R2.id.top)
        View top;

        @BindView(R2.id.vItem)
        View vItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
