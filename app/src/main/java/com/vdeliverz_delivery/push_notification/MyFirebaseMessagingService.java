package com.vdeliverz_delivery.push_notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import com.vdeliverz_delivery.home.HomeScreenActivity;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG=MyFirebaseMessagingService.class.getSimpleName();
    String title = "", message = "", imageUrl = "" , timestamp = "",type;
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    private NotificationUtils notificationUtils;
    boolean isBackground = false;
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d(TAG, "onNewToken: "+s);

        MnxPreferenceManager.setString(MnxConstant.FCM_TOKEN,s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: 12121" + remoteMessage.getData());


        String dataString = remoteMessage.getData().get("data");
        String channel = remoteMessage.getData().get("notification");



        JSONObject data = null;
        if (dataString != null) {
            try {
                data = new JSONObject(dataString);
                Log.d(TAG, "onMessageReceived:datajosn "+data);
                handleDataMessage(data);

            } catch (JSONException e) {
                Log.e(TAG, "Ignoring push because of JSON exception while processing: " + dataString, e);
                return;
            }
        }


    }


    private void showNotificationMessage(Context context, String title, String message, String imageUrl, Intent resultIntent,
                                         boolean isSound) {

        Log.d(TAG, "showNotificationMessage: ");
        notificationUtils = new NotificationUtils(context);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, imageUrl, resultIntent, isSound);



    }


    private void handleDataMessage(JSONObject json) {


        Log.e(TAG, "push json: " + json.toString());
        try {
            title = json.getString("title");
            message = json.getString("message");
            imageUrl = json.getString("image");
            type=json.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            //notificationUtils.playNotificationSound();
            showNotificationMessage(getApplicationContext(), title, message, imageUrl, pushNotification, true);

        } else {
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
            resultIntent.putExtra("isevent",1);
            resultIntent.putExtra("message", message);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            //notificationUtils.playNotificationSound();
            showNotificationMessage(getApplicationContext(), title, message, imageUrl, resultIntent, true);
        }

    }


}
