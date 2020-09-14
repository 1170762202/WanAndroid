package com.zlx.module_base.constant;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Config {

    /**
     * 每个用户单独对应一个数据库
     */
    public static final String SQLITE_NAME = "SQLITE_NAME";
    public static final long CHAT_TIME = 60000;


    public static final String VIDEO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/zt/video";
    public static final String PHOTO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/zt/photo";
    public static final String AUDIO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/zt/audio";
    public static final String FILE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/zt/file";
    public static final String FILE_ENCRYPTED = Environment.getExternalStorageDirectory().getPath() + "/zt/encrypted_file";
    public static final String FILE_DECRYPTED = Environment.getExternalStorageDirectory().getPath() + "/zt/decrypted_file";


    static {
        createFile(VIDEO_SAVE_DIR);
        createFile(PHOTO_SAVE_DIR);
        createFile(AUDIO_SAVE_DIR);
        createFile(FILE_ENCRYPTED);
        createFile(FILE_DECRYPTED);
        Log.e("TAG", "create");
    }

    public static String getEnCryptPath() {
        return FILE_ENCRYPTED + File.separator + System.currentTimeMillis() + ".png";
    }

    public static String getDecryptPath() {
        return Config.FILE_DECRYPTED + File.separator + System.currentTimeMillis() + ".png";
    }

    public static String getPath() {
        return FILE_SAVE_DIR + File.separator + System.currentTimeMillis() + ".png";
    }

    private static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
