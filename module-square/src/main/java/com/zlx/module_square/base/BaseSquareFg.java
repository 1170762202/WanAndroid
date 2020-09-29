package com.zlx.module_square.base;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.base_util.ResourceUtil;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_square.R;
import com.zlx.module_square.R2;
import com.zlx.widget.indicatorview.IndicatorItem;
import com.zlx.widget.indicatorview.IndicatorScrollView;
import com.zlx.widget.indicatorview.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zlx on 2020/9/29 9:23
 * Email: 1170762202@qq.com
 * Description: 广场base
 */
public class BaseSquareFg extends BaseFg {


    @BindView(R2.id.indicatorScrollView)
    protected IndicatorScrollView indicatorScrollView;
    @BindView(R2.id.indicatorView)
    IndicatorView indicatorView;
    @BindView(R2.id.llParent)
    LinearLayout llParent;

    private LayoutInflater layoutInflater = null;

    List<Integer> colorList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fg_base_square;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initColors();
        indicatorScrollView.bindIndicatorView(indicatorView);
    }

    private void initColors() {
        for (int i = 0; i < 19; i++) {
            int resId = ResourceUtil.getResId("in" + (i + 1), R.color.class);
            colorList.add(resId);
        }
    }

    private View findItem() {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(getContext());
        return (View) layoutInflater.inflate(R.layout.rv_item_flex, null, false);
    }

    private AppCompatTextView findLabel(FlexboxLayout flexboxLayout) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(flexboxLayout.getContext());
        return (AppCompatTextView) layoutInflater.inflate(R.layout.flextlayout_item_label, flexboxLayout, false);
    }

    protected void setData(List<TreeListRes> data,boolean system) {
        indicatorView.removeAllViews();
        llParent.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            TreeListRes res = data.get(i);
            String name = res.getName();
            String firstName = TextUtils.isEmpty(name) ? "" : name.substring(0, 1);
            View view = findItem();
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(name);
            FlexboxLayout flexboxLayout = view.findViewById(R.id.flexLayout);
            if (system){
                List<TreeListRes.ChildrenBean> children = res.getChildren();
                for (TreeListRes.ChildrenBean child : children) {
                    AppCompatTextView textView = findLabel(flexboxLayout);
                    textView.setText(child.getName());
                    textView.setOnClickListener(v -> {
                        RouterUtil.launchArticleList(child.getId(), child.getName());
                    });
                    flexboxLayout.addView(textView);
                }
            }else {
                List<ArticleBean> articles = res.getArticles();
                for (ArticleBean article : articles) {
                    AppCompatTextView textView = findLabel(flexboxLayout);
                    textView.setText(article.getTitle());
                    textView.setOnClickListener(v -> {
                        RouterUtil.launchWeb(article.getLink());
                    });
                    flexboxLayout.addView(textView);
                }

            }
            llParent.addView(view);
            int i1 = i % colorList.size();
            indicatorView.addIndicatorItem(new IndicatorItem.Builder(view).setItemText(firstName).setItemColorResource(colorList.get(i1)).setItemIconResource(R.mipmap.ic_uncollect).build());
        }
    }
}
