package com.zlx.sharelive.base.base_mvp;



import com.zlx.sharelive.base.base_mvp.mobdel.BaseModel;
import com.zlx.sharelive.base.base_mvp.mobdel.IBaseModelListener;
import com.zlx.sharelive.base.base_mvp.presenter.BaseContact;
import com.zlx.sharelive.base.base_mvp.presenter.BasePresenter;
import com.zlx.sharelive.util.LogUtil;

import java.util.Map;

/**
 * Created by zlx on 2017/6/26.
 */

public abstract class NewBasePresenter extends BasePresenter<BaseContact.IViewData> {
    private BaseModel baseModel;

    public NewBasePresenter(BaseContact.IViewData iRealView) {
        baseModel = new BaseModel();
        attachView(iRealView);
    }

    public void post(String url, Map<String, Object> map, final BaseContact.BaseView baseView){
        baseView.onRequestStart();
        LogUtil.e("onRequestStart");
        baseModel.post(url, map, new IBaseModelListener() {
            @Override
            public void onSuccess(String s) {
//                BaseResult baseBean = JSON.parseObject(s, BaseResult.class);
                int statusCode = 200;
                if (statusCode == 200) {
                    baseView.onRequestSuccess(s);
                    LogUtil.e("onRequestSuccess:"+s);

                } else if (statusCode == 999) {
                    baseView.onRequestFailed(s);
                    LogUtil.e("onRequestFailed:"+s);

                } else {
                    baseView.onRequestFailed(s);
                    LogUtil.e("onRequestFailed:"+s);

                }
                baseView.onRequestFinished();
                LogUtil.e("onRequestFinished");

            }

            @Override
            public void onError(String s) {
                baseView.onRequestError(s);
                baseView.onRequestFinished();
                LogUtil.e("onRequestError:"+s);
                LogUtil.e("onRequestFinished");
            }
        });
    }


}
