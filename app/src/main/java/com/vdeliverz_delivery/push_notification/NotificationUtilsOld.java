package com.vdeliverz_delivery.push_notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.vdeliverz_delivery.BuildConfig;
import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.home.HomeScreenActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtilsOld {

    private static String TAG = NotificationUtilsOld.class.getSimpleName();

    private Context mContext;
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public NotificationUtilsOld(Context mContext) {
        this.mContext = mContext;
    }


    public void showNotificationMessage(final String title, final String message, final String imageUrl, Intent intent, boolean isSound) {


        Log.d(TAG, "showNotificationMessage: class ");
        // Check for empty push messag
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.drawable.ic_push_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        Log.e(TAG, "showNotificationMessage: notification" );

        Notification.Builder notification=new Notification.Builder(mContext);
     //   showSmallNotification(notification, icon, title, message,imageUrl , resultPendingIntent);

     /*   final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");*/

       if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, notification, icon, title, message, imageUrl, resultPendingIntent);
                } else {
                    showSmallNotification(notification, icon, title, message,imageUrl , resultPendingIntent);
                }
            }
        } else {
            showSmallNotification(notification, icon, title, message,imageUrl, resultPendingIntent);
            if(isSound){
                //playNotificationSound();
            }
        }
    }


    private void showSmallNotification( Notification.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent) {


        final Uri NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification_beep);
        final long[] VIBRATE_PATTERN    = {0, 500};

        Log.e(TAG, "showSmallNotification: small notification ");
        Log.d(TAG, "showSmallNotification: titile "+title);
        Log.d(TAG, "showSmallNotification: message "+message);
        Log.d(TAG, "showSmallNotification: resultPendingIntent "+resultPendingIntent);

        Intent intent = new Intent(mContext, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";

        /*NotificationCompat.Builder builder = new  NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setSound(NOTIFICATION_SOUND_URI)
                .setVibrate(VIBRATE_PATTERN)
                .setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent);*/

        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setSound(NOTIFICATION_SOUND_URI)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification_beep"))
                .setVibrate(VIBRATE_PATTERN)
                .setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    private void showBigNotification(Bitmap bitmap,Notification.Builder  mBuilder, int icon, String title, String message, String imageurl, PendingIntent resultPendingIntent) {


        final Uri NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification_beep);
        final long[] VIBRATE_PATTERN    = {0, 500};

        Log.d(TAG, "showBigNotification: big images");
        
        NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
        bpStyle.bigPicture(bitmap);

        Intent intent = new Intent(mContext, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";



        


        NotificationCompat.Builder builder = new  NotificationCompat.Builder(mContext, channelId);

        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(icon)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification_beep"))
                .setVibrate(VIBRATE_PATTERN)
                .setWhen(System.currentTimeMillis())
                .setStyle(bpStyle);
        

        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());





    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
