package com.zlx.module_mine.opensource;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zlx.module_base.base_ac.BaseTopBarViewModel;
import com.zlx.module_mine.R;
import com.zlx.module_mine.bean.OpenSourcePro;
import com.zlx.module_mine.utils.DataUtil;

import java.util.List;

public class OpenSourceViewModel extends BaseTopBarViewModel {
    public MutableLiveData<List<OpenSourcePro>> liveData = new MutableLiveData<>();

    public OpenSourceViewModel(@NonNull Application application) {
        super(application);
        setTitleText(application.getString(R.string.mine_open_source_project));
    }

    public void listData(){
        liveData.setValue(DataUtil.getPros());
    }
}
