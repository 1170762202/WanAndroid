package com.zlx.module_mine.adapters;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.constant.C;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FileName: RvAdapterMyScoreHeader
 * Created by zlx on 2020/9/22 9:11
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterMyScoreHeader extends DelegateAdapter.Adapter<RvAdapterMyScoreHeader.ViewHolder> {


    private String score;

    public void setScore(String score) {
        this.score = score;
        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_my_score_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        startAnim(holder.tvScore);
    }
    private void startAnim(TextView textView){
        String coinCount = MMkvHelper.getInstance().getUserInfo().getCoinCount();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, Integer.parseInt(coinCount));
        //播放时长
        valueAnimator.setDuration(C.DURATION);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            //获取改变后的值
            int currentValue = (int) valueAnimator1.getAnimatedValue();
            textView.setText(currentValue + "");
        });
        valueAnimator.start();
    }
    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.tvScore)
        TextView tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
