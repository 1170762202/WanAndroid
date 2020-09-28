package com.zlx.module_square.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_base.base_api.res_data.NaviListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_square.R;
import com.zlx.module_square.R2;
import com.zlx.module_square.adapters.RvAdapterNavi;

import java.util.List;

import butterknife.BindView;

/**
 * FileName: NavigationFg
 * Created by zlx on 2020/9/18 10:16
 * Email: 1170762202@qq.com
 * Description: 导航
 */
@Route(path = RouterFragmentPath.Square.PAGER_NAVIGATION)
public class NavigationFg extends BaseFg {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    RvAdapterNavi adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_navigation;
    }

    @Override
    protected void initViews() {
        super.initViews();
        adapter = new RvAdapterNavi();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        showLoading(view.findViewById(R.id.root));
        listSystem();
    }
    private void listSystem() {
        ApiUtil.getTreeApi().listNavis().observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<NaviListRes>>>() {
                    @Override
                    public void onSuccess(ApiResponse<List<NaviListRes>> data) {
                        adapter.setList(data.getData());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        showSuccess();
                    }
                }));
    }
}
