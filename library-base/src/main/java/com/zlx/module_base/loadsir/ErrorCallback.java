package com.zlx.module_base.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.zlx.module_base.R;

/**
 * FileName: ErrorCallback
 * Created by zlx on 2020/9/17 17:35
 * Email: 1170762202@qq.com
 * Description:
 */
public class ErrorCallback extends Callback {

    @Override
    protected int onCreateView()
    {
        return R.layout.base_layout_error;
    }
}
