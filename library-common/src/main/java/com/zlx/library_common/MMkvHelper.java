package com.zlx.library_common;

import com.tencent.mmkv.MMKV;
import com.zlx.library_common.constrant.C;
import com.zlx.library_common.res_data.UserInfo;

/**
 * FileName: MMkvHelper
 * Created by zlx on 2020/9/21 14:40
 * Email: 1170762202@qq.com
 * Description:
 */
public class MMkvHelper {
    private static MMKV mmkv;

    private MMkvHelper() {
        mmkv = MMKV.defaultMMKV();
    }

    public static MMkvHelper getInstance() {
        return MMkvHolder.INSTANCE;
    }

    private static class MMkvHolder {
        private static final MMkvHelper INSTANCE = new MMkvHelper();
    }



    /**
     * 保存用户信息
     */
    public void saveUserInfo(UserInfo userInfo) {
        mmkv.encode(C.USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        return mmkv.decodeParcelable(C.USER_INFO, UserInfo.class);
    }

}
