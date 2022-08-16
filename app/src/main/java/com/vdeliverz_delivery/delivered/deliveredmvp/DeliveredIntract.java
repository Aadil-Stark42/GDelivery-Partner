package com.vdeliverz_delivery.delivered.deliveredmvp;

import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import com.vdeliverz_delivery.delivered.model.DeliveredListResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveredIntract implements DeliveredContract.GetDeliveredIntractor {

    String TAG= DeliveredIntract.class.getSimpleName();

    interface OnDeliveredListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final OnDeliveredListener listener) {

        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }


    @Override
    public void delivered_APICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().getdeliveredlist().enqueue(new Callback<DeliveredListResponse>(){
            @Override
            public void onResponse(@NotNull Call<DeliveredListResponse> call, @NotNull Response<DeliveredListResponse> response) {
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
            public void onFailure(@NotNull Call<DeliveredListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }

    }
