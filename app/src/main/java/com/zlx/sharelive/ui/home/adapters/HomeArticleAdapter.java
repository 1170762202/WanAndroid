package com.zlx.sharelive.ui.home.adapters;

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
import com.zlx.module_base.bean.res_data.ArticleListRes;
import com.zlx.sharelive.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeArticleAdapter extends DelegateAdapter.Adapter<HomeArticleAdapter.ViewHolder> {

    private List<ArticleListRes.DatasBean> articleListResList;

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
        holder.tvChapter.setText(TextUtils.isEmpty(datasBean.getChapterName()) ? datasBean.getSuperChapterName() : datasBean.getChapterName());
        holder.tvTime.setText(datasBean.getNiceDate());
    }

    @Override
    public int getItemCount() {
        return articleListResList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvChapter)
        TextView tvChapter;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
