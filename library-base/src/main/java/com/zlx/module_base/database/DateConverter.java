package com.zlx.module_base.database;

import androidx.room.TypeConverter;

import com.zlx.module_base.base_util.LogUtils;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        if (value==null){
            value = new Date();
        }
        LogUtils.i("converterDate="+value.getTime()+"");
        return value.getTime();
    }
}