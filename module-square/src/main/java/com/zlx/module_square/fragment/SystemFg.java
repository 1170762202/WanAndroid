package com.zlx.module_square.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_square.R;
import com.zlx.module_square.R2;
import com.zlx.module_square.adapters.RvAdapterTree;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * FileName: SystemFg
 * Created by zlx on 2020/9/18 10:16
 * Email: 1170762202@qq.com
 * Description: 体系
 */
@Route(path = RouterFragmentPath.Square.PAGER_SYSTEM)
public class SystemFg extends BaseFg {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    RvAdapterTree rvAdapterTree;
    private List<TreeListRes> dataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fg_system;
    }

    @Override
    protected void initViews() {
        super.initViews();
        rvAdapterTree = new RvAdapterTree(dataList);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvAdapterTree);
        showLoading(view.findViewById(R.id.root));
        listSystem();
    }

    private void listSystem() {
        ApiUtil.getTreeApi().listTrees().observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<TreeListRes>>>() {
                    @Override
                    public void onSuccess(ApiResponse<List<TreeListRes>> data) {
                        rvAdapterTree.refresh(data.getData());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        showSuccess();
                    }
                }));
    }
}
