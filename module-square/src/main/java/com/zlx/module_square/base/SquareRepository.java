package com.zlx.module_square.base;

import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class SquareRepository extends BaseModel {

    public void listTrees(ApiCallback<List<TreeListRes>> callback) {
        ApiUtil.getTreeApi().listTrees().enqueue(callback);
    }

    public void listNavis(ApiCallback<List<TreeListRes>> callback) {
        ApiUtil.getTreeApi().listNavis().enqueue(callback);
    }
}
