package com.zlx.module_base.base_util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.zlx.library_common.provier.AppProvider;
import com.zlx.module_base.database.MMkvHelper;

import java.util.Locale;


public class LanguageUtil {

    public static String getSystemLanguage() {
        String language = Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
        return language;
    }

    public static Locale getCurrentLanguage() {
        Locale locale = MMkvHelper.getInstance().getLanguage();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public static void switchChinese() {
        changeLanguage(Locale.SIMPLIFIED_CHINESE);
    }

    public static void switchEnglish() {
        changeLanguage(Locale.US);
    }

    public static void switchLanguage(Locale locale) {
        changeLanguage(locale);
    }

    private static void changeLanguage(Locale locale) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.setConfiguration(AppProvider.getInstance().getApp(), locale);
        }
        MMkvHelper.getInstance().saveLanguage(locale);
    }

    /**
     * @param context
     * @param locale  想要切换的语言类型 比如 "en" ,"zh"
     */
    @SuppressLint("NewApi")
    public static void setConfiguration(Context context, Locale locale) {
        if (locale == null) {
            return;
        }
        //获取应用程序的所有资源对象
        Resources resources = context.getResources();
        //获取设置对象
        Configuration configuration = resources.getConfiguration();
        //如果API < 17
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.locale = locale;
        } else //如果 17 < = API < 25 Android 7.7.1
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                configuration.setLocale(locale);
            } else {//API 25  Android 7.7.1
                configuration.setLocale(locale);
                configuration.setLocales(new LocaleList(locale));
            }
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }


    public static Context attachBaseContext(Context context) {
        Locale locale = LanguageUtil.getCurrentLanguage();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, locale);
        } else {
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }
}
