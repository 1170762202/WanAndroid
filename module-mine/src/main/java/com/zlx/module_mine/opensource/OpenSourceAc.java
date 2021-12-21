package com.zlx.module_mine.opensource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.adapters.RvAdapterOpenSourcePro;
import com.zlx.module_mine.bean.OpenSourcePro;
import com.zlx.module_mine.databinding.AcOpenSourceBinding;

import java.util.List;

/**
 * Created by zlx on 2020/9/22 17:15
 * Email: 1170762202@qq.com
 * Description:
 */
public class OpenSourceAc extends BaseMvvmAc<AcOpenSourceBinding, OpenSourceViewModel> {

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
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapterOpenSourcePro = new RvAdapterOpenSourcePro());
        viewModel.liveData.observe(this, new Observer<List<OpenSourcePro>>() {
            @Override
            public void onChanged(List<OpenSourcePro> openSourcePros) {
                adapterOpenSourcePro.setList(openSourcePros);
            }
        });
        viewModel.listData();
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_open_source;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
