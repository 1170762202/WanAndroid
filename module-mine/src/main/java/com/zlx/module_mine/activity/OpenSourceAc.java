package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_mine.adapters.RvAdapterOpenSourcePro;
import com.zlx.module_mine.utils.DataUtil;

import butterknife.BindView;

/**
 * Created by zlx on 2020/9/22 17:15
 * Email: 1170762202@qq.com
 * Description:
 */
public class OpenSourceAc extends BaseAc {


    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    RvAdapterOpenSourcePro adapterOpenSourcePro;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, OpenSourceAc.class));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.ac_open_source;
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle(getString(R.string.mine_open_source_project));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterOpenSourcePro = new RvAdapterOpenSourcePro());
        adapterOpenSourcePro.setList(DataUtil.getPros());
    }
}
