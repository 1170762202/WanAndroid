package com.zlx.module_base.base_util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationUtils extends ContextWrapper {

    private int notifyId = 0;
    private String channelId = "0";
    private int smallIcon;
    private String title;
    private String content;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private NotificationManagerCompat notificationManagerCompat;

    private final String[] CHANNEL_NAME = new String[]{
            "常规通知"
    };

    public NotificationUtils(Context context, int smallIcon, String title, String content) {
        this(context, 1, null, smallIcon, title, content);
    }

    public NotificationUtils(Context context, int notifyId, int smallIcon, String title, String content) {
        this(context, notifyId, null, smallIcon, title, content);
    }

    public NotificationUtils(Context context, int notifyId, String channelId, int smallIcon, String title, String content) {
        super(context);
        this.notifyId = notifyId;
        this.channelId = channelId != null ? channelId : this.notifyId+"";
        this.smallIcon = smallIcon;
        this.title = title;
        this.content = content;
        baseNotification();
    }

    private void baseNotification() {
        builder = getBuilder(getApplicationContext(), channelId)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);
    }

    private NotificationCompat.Builder getBuilder(Context context, String channelId) {
        return (builder = new NotificationCompat.Builder(context, channelId));
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        return (builder = new NotificationCompat.Builder(context));
    }

    public NotificationCompat.Builder getBuilder() {
        return builder;
    }

    public void notified() {
        notify(builder);
    }

    public void notified(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notify(builder.setContentIntent(pendingIntent));
    }

    private void notify(NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager();
            notificationManager.notify(notifyId, builder.build());
        } else {
            getNotificationManagerCompat();
            notificationManagerCompat.notify(notifyId, builder.build());
        }
    }

    private void getNotificationManager() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, CHANNEL_NAME[notifyId], importance);
            //设置震动声音等
            channel.setVibrationPattern(new long[]{0,1300,500,1700});
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void getNotificationManagerCompat() {
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
    }

    //进度通知栏
    public void notifyProgress(int max, int progress, String title, String content) {
        if (builder != null && progress > 0) {
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setProgress(max, progress, false);
            notify();
        }
    }

    public void completeProgress(String title, String content) {
        notifyProgress(0, 0, title, content);
    }


    public void cancel(int notifyId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancel(notifyId);
        } else {
            notificationManagerCompat.cancel(notifyId);
        }
    }

    public NotificationManager getManager() {
        return this.notificationManager;
    }

}
