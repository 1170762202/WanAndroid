package com.zlx.library_db.converter;

import android.util.Log;

import androidx.room.TypeConverter;


import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        if (value == null) {
            value = new Date();
        }
        Log.i("DateConverter", "converterDate=" + value.getTime() + "");
        return value.getTime();
    }
}