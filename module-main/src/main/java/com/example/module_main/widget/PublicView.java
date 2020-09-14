package com.example.module_main.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.module_main.R;
import com.example.module_main.R2;
import com.example.module_main.adapter.PublicAdapter;
import com.zlx.widget.rv_item.PolygonLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicView extends RelativeLayout {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    private PublicAdapter publicAdapter;
    private List<String> dataList = new ArrayList<>();

    public PublicView(Context context) {
        super(context, null);
    }

    public PublicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_public, this, true);
        ButterKnife.bind(this);


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
