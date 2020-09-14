package com.example.module_main.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.example.module_main.R;
import com.example.module_main.R2;
import com.example.module_main.adapter.PublicAdapter;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.widget.rv_item.PolygonLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicFg extends BaseFg {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    private PublicAdapter publicAdapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fg_public;
    }

    @Override
    protected void initViews() {
        super.initViews();
        for (int i = 0; i < 10; i++) {
            dataList.add("");
        }
        publicAdapter = new PublicAdapter(dataList);
        PolygonLayoutManager manager = new PolygonLayoutManager(true);
        manager.setLandscapeInterval(0);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(publicAdapter);
    }
}
