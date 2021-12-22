package com.zlx.module_square.system;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexboxLayout;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_base.base_fg.BaseMvvmFg;
import com.zlx.module_base.base_util.ResourceUtil;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_square.BR;
import com.zlx.module_square.R;
import com.zlx.module_square.base.SquareViewModel;
import com.zlx.module_square.databinding.FgBaseSquareBinding;
import com.zlx.widget.indicatorview.IndicatorItem;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: SystemFg
 * Created by zlx on 2020/9/18 10:16
 * Email: 1170762202@qq.com
 * Description: 体系
 */
@Route(path = RouterFragmentPath.Square.PAGER_SYSTEM)
public class SystemFg extends BaseMvvmFg<FgBaseSquareBinding, SquareViewModel> {

    private LayoutInflater layoutInflater = null;

    List<Integer> colorList = new ArrayList<>();

    @Override
    protected void initViews() {
        super.initViews();
        initColors();
        binding.indicatorScrollView.bindIndicatorView(binding.indicatorView);
        showLoading(binding.indicatorScrollView);
        viewModel.treesLiveData.observe(this, treeListRes -> {
            if (treeListRes != null) {
                setData(treeListRes, true);
            }
            showSuccess();
        });
        viewModel.listTrees();
    }
    private void initColors() {
        for (int i = 0; i < 19; i++) {
            int resId = ResourceUtil.getResId("in" + (i + 1), R.color.class);
            colorList.add(resId);
        }
    }

    protected void setData(List<TreeListRes> data, boolean system) {
        binding.indicatorView.removeAllViews();
        binding.llParent.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            TreeListRes res = data.get(i);
            String name = res.getName();
            String firstName = TextUtils.isEmpty(name) ? "" : name.substring(0, 1);
            View view = findItem();
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(name);
            FlexboxLayout flexboxLayout = view.findViewById(R.id.flexLayout);
            if (system) {
                List<TreeListRes.ChildrenBean> children = res.getChildren();
                for (TreeListRes.ChildrenBean child : children) {
                    AppCompatTextView textView = findLabel(flexboxLayout);
                    textView.setText(child.getName());
                    textView.setOnClickListener(v -> {
                        RouterUtil.launchArticleList(child.getId(), child.getName());
                    });
                    flexboxLayout.addView(textView);
                }
            } else {
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
            binding.llParent.addView(view);
            int i1 = i % colorList.size();
            binding.indicatorView.addIndicatorItem(new IndicatorItem.Builder(view).setItemText(firstName).setItemColorResource(colorList.get(i1)).setItemIconResource(R.mipmap.ic_uncollect).build());
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
    @Override
    protected int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fg_base_square;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }
}
