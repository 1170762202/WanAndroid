package com.zlx.library_common.provier;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by zlx on 2020/9/28 10:00
 * Email: 1170762202@qq.com
 * Description:
 */
public class AppContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context == null) return false;
        context = context.getApplicationContext();
        if (context instanceof Application) {
            AppProvider.init((Application) context);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
