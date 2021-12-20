package com.zlx.module_square.square;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_square.base.BaseSquareFg;

/**
 * FileName: SystemFg
 * Created by zlx on 2020/9/18 10:16
 * Email: 1170762202@qq.com
 * Description: 体系
 */
@Route(path = RouterFragmentPath.Square.PAGER_SYSTEM)
public class SystemFg extends BaseSquareFg {

    @Override
    protected void initViews() {
        super.initViews();
        showLoading(binding.indicatorScrollView);
        viewModel.treesLiveData.observe(this, treeListRes -> {
            if (treeListRes != null) {
                setData(treeListRes, true);
            }
            showSuccess();
        });
        viewModel.listTrees();
    }
}
