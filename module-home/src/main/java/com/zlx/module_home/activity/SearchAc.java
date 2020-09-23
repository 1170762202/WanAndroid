package com.zlx.module_home.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.zlx.library_common.res_data.SearchBeanRes;
import com.zlx.library_common.util.ApiUtil;
import com.zlx.library_db.dao.SearchHistoryDao;
import com.zlx.library_db.entity.SearchHistoryEntity;
import com.zlx.library_db.manager.DbUtil;
import com.zlx.module_base.OnItemClickListener;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.InputTools;
import com.zlx.module_base.base_util.ToastUtil;
import com.zlx.module_home.R;
import com.zlx.module_home.R2;
import com.zlx.module_home.adapters.RvAdapterSearchClear;
import com.zlx.module_home.adapters.RvAdapterSearchHis;
import com.zlx.module_home.adapters.RvAdapterSearchHot;
import com.zlx.module_home.bean.SearchBean;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by zlx on 2020/9/23 9:37
 * Email: 1170762202@qq.com
 * Description:
 */
public class SearchAc extends BaseAc {

    @BindView(R2.id.etSearch)
    AppCompatEditText etSearch;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    private SearchHistoryDao searchHistoryDao;

    private VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
    private DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
    private final ArrayList<DelegateAdapter.Adapter> adapters = new ArrayList<>();
    private RvAdapterSearchHot adapterSearchHot;
    private RvAdapterSearchHis adapterSearchHis;


    @Override
    protected int getLayoutId() {
        return R.layout.ac_search;
    }

    @Override
    public void initViews() {
        super.initViews();
        searchHistoryDao = DbUtil.getInstance().getAppDataBase().searchHistoryDao();
        adapterSearchHis = new RvAdapterSearchHis();
        adapters.add(adapterSearchHis);
        RvAdapterSearchClear adapterSearchClear = new RvAdapterSearchClear();
        adapters.add(adapterSearchClear);
        adapterSearchHot = new RvAdapterSearchHot();
        adapters.add(adapterSearchHot);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setLayoutManager(virtualLayoutManager);
        recyclerView.setAdapter(delegateAdapter);
        adapterSearchClear.setOnSearchClearCallBack(() -> {
            //清空历史记录
            LogUtil.show("deleteAll");
            searchHistoryDao.deleteAll();
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
                LogUtil.show("delete=" + id);
                searchHistoryDao.deleteById(id);
            }
        });
        adapterSearchHot.setOnSearchHotCallBack(new RvAdapterSearchHot.OnSearchHotCallBack() {
            @Override
            public void onHot(String title) {
                //热门搜索
                skipResult(title);
            }
        });

        ApiUtil.getArticleApi().hotSearch().observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<SearchBeanRes>>>() {
                    @Override
                    public void onSuccess(ApiResponse<List<SearchBeanRes>> data) {
                        List<SearchBean> list = new ArrayList<>();
                        SearchBean searchBean = new SearchBean();
                        searchBean.setTitle("热门搜索");
                        searchBean.setData(data.getData());
                        list.add(searchBean);
                        adapterSearchHot.refresh(list);


                    }
                }));


    }

    @OnEditorAction(R2.id.etSearch)
    public boolean OnEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String trim = etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                ToastUtil.showShort("请输入内容再搜索");
                etSearch.setFocusable(true);
                etSearch.requestFocus();
            } else {
                skipResult(trim);
                saveDB(trim);
            }
        }
        return false;

    }

    private void saveDB(String trim) {
        List<SearchHistoryEntity> searchHistoryEntities = searchHistoryDao.selectHis();
        long id = 0;
        for (SearchHistoryEntity searchHistoryEntity : searchHistoryEntities) {
            if (TextUtils.equals(searchHistoryEntity.getName(), trim)) {
                id = searchHistoryEntity.getId();
                break;
            }
        }
        if (id != 0) {
            searchHistoryDao.deleteById(id);
        }
        searchHistoryDao.insertPerson(new SearchHistoryEntity(trim));
    }

    @Override
    protected void onResume() {
        super.onResume();
        InputTools.showSoftInput(etSearch);

        filterHist();
    }

    @Override
    protected boolean hasAcAnim() {
        return false;
    }

    private void filterHist() {
        List<SearchHistoryEntity> searchHistoryEntities = searchHistoryDao.selectHis();
        Collections.reverse(searchHistoryEntities);
        adapterSearchHis.refresh(searchHistoryEntities);
    }

    private void skipResult(String keyword) {
//        InputTools.hideInputMethod(this);
        etSearch.setText(keyword);
        Pair<View, String> p = Pair.create(etSearch, "search");

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                p);
        SearchResultAc.start(this, keyword, optionsCompat);
    }

    @OnClick(R2.id.ivBack)
    public void back() {
        finishAfterTransition();
    }
}
