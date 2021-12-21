package com.zlx.module_home.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.zlx.library_db.entity.SearchHistoryEntity;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.SearchBeanRes;
import com.zlx.module_base.base_util.InputTools;
import com.zlx.module_base.event.EventHandlers;
import com.zlx.module_home.BR;
import com.zlx.module_home.R;
import com.zlx.module_home.R2;
import com.zlx.module_home.adapters.RvAdapterSearchClear;
import com.zlx.module_home.adapters.RvAdapterSearchHis;
import com.zlx.module_home.adapters.RvAdapterSearchHot;
import com.zlx.module_home.bean.SearchBean;
import com.zlx.module_home.databinding.AcSearchBinding;
import com.zlx.module_home.searchresult.SearchResultAc;
import com.zlx.module_network.widget.popwindow.PopUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.OnEditorAction;

/**
 * Created by zlx on 2020/9/23 9:37
 * Email: 1170762202@qq.com
 * Description:
 */
public class SearchAc extends BaseMvvmAc<AcSearchBinding, SearchViewModel> {

    private VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
    private DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
    private final ArrayList<DelegateAdapter.Adapter> adapters = new ArrayList<>();
    private RvAdapterSearchHot adapterSearchHot;
    private RvAdapterSearchHis adapterSearchHis;


    @Override
    public void initViews() {
        super.initViews();
        binding.setEventHandlers(new SearchEvent());
        adapterSearchHis = new RvAdapterSearchHis();
        adapters.add(adapterSearchHis);
        RvAdapterSearchClear adapterSearchClear = new RvAdapterSearchClear();
        adapters.add(adapterSearchClear);
        adapterSearchHot = new RvAdapterSearchHot();
        adapters.add(adapterSearchHot);
        delegateAdapter.setAdapters(adapters);
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        binding.recyclerView.setAdapter(delegateAdapter);
        adapterSearchClear.setOnSearchClearCallBack(() -> {
            //清空历史记录
            viewModel.deleteLocalHis();
            adapterSearchHis.clear();
            InputTools.hideInputMethod(this);
        });
        adapterSearchHis.setOnSearchHisCallBack(new RvAdapterSearchHis.OnSearchHisCallBack() {
            @Override
            public void onItemClick(String searchContent) {
                //搜索历史
                skipResult(searchContent);
            }

            @Override
            public void onItemDelete(long id) {
                //删除历史
                viewModel.deleteLocalHisById(id);
            }
        });
        adapterSearchHot.setOnSearchHotCallBack(title -> {
            //热门搜索
            skipResult(title);
        });

        viewModel.hotLiveData.observe(this, new Observer<List<SearchBeanRes>>() {
            @Override
            public void onChanged(List<SearchBeanRes> searchBeanRes) {
                if (searchBeanRes != null) {
                    List<SearchBean> list = new ArrayList<>();
                    SearchBean searchBean = new SearchBean();
                    searchBean.setTitle(getString(R.string.hot_search));
                    searchBean.setData(searchBeanRes);
                    list.add(searchBean);
                    adapterSearchHot.refresh(list);
                }
            }
        });
        viewModel.localLiveData.observe(this,searchHistoryEntities -> {
            Collections.reverse(searchHistoryEntities);
            adapterSearchHis.refresh(searchHistoryEntities);

        });
        viewModel.hotSearch();
        InputTools.showSoftInput(binding.etSearch);

    }


    @Override
    protected void onResume() {
        super.onResume();
        viewModel.selectLocalHis();
    }

    private void skipResult(String keyword) {
//        InputTools.hideInputMethod(this);
        binding.etSearch.setText(keyword);
        Pair<View, String> p = Pair.create(binding.etSearch, "search");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                p);
        SearchResultAc.start(this, keyword, optionsCompat);
    }

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_search;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    public class SearchEvent extends EventHandlers {
        @Override
        public void onClick(View view) {
            super.onClick(view);
            finishAfterTransition();
        }

        @Override
        public boolean onEditTextChange(EditText textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String trim = binding.etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    PopUtil.show("请输入内容再搜索");
                    binding.etSearch.setSelection(0);
                } else {
                    skipResult(trim);
                    viewModel.saveDB(trim);
                }
            }
            return super.onEditTextChange(textView, actionId, keyEvent);
        }
    }
}
