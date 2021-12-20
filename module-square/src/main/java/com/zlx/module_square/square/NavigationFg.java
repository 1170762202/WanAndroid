package com.zlx.module_square.square;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_square.base.BaseSquareFg;

/**
 * FileName: NavigationFg
 * Created by zlx on 2020/9/18 10:16
 * Email: 1170762202@qq.com
 * Description: 导航
 */
@Route(path = RouterFragmentPath.Square.PAGER_NAVIGATION)
public class NavigationFg extends BaseSquareFg {

    @Override
    protected void initViews() {
        super.initViews();
        showLoading(binding.indicatorScrollView);
        viewModel.navisLiveData.observe(this, treeListRes -> {
            if (treeListRes != null) {
                setData(treeListRes, false);
            }
            showSuccess();
        });
        viewModel.listNavis();
    }
}
