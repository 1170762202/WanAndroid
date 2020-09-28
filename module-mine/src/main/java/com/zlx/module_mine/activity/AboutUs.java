package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zlx.module_base.constant.C;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * FileName: AboutUs
 * Created by zlx on 2020/9/22 11:31
 * Email: 1170762202@qq.com
 * Description:
 */
public class AboutUs extends BaseAc {
    @BindView(R2.id.tvVersion)
    TextView tvVersion;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutUs.class));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.ac_about_us;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @OnClick({R2.id.llOfficial, R2.id.llNetContent, R2.id.llLibAddr})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.llOfficial) {
            RouterUtil.launchWeb(C.WAN_ANDROID);
        } else if (id == R.id.llNetContent) {
            RouterUtil.launchWeb(C.URL_ABOUT);
        } else if (id == R.id.llLibAddr) {
            RouterUtil.launchWeb(C.SOURCE_URL);
        }
    }
}
