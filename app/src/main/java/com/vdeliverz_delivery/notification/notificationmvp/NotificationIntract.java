package com.vdeliverz_delivery.notification.notificationmvp;

import android.os.Handler;


import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.notification.model.NotificationListResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationIntract implements  NotificationContract.GetNotificationIntractor {

    String TAG= NotificationIntract.class.getSimpleName();

    interface OnNotificationListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final OnNotificationListener listener) {

        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void notification_APICall(OnFinishedListener onFinishedListener) {
        VdeliverzApi.getClient().getnotificationlist().enqueue(new Callback<NotificationListResponse>(){
            @Override
            public void onResponse(@NotNull Call<NotificationListResponse> call, @NotNull Response<NotificationListResponse> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if(response.body().getStatus()){
                                onFinishedListener.onFinished(response.body());
                            }else {
                                onFinishedListener.onFailure(response.body().getMessage());
                            }
                        }else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }else {
                        onFinishedListener.onFailure(response.message());
                    }
                }else {
                    if(response.code()==401){
                        MnxPreferenceManager.clearAllPreferences();
                        onFinishedListener.do_logout();
                    }else{
                        onFinishedListener.onFailure("Server Error");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });
    }
}
