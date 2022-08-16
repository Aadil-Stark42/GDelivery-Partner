package com.vdeliverz_delivery.order_items_list.mvp;

import android.os.Handler;

import com.vdeliverz_delivery.notification.model.NotificationListResponse;
import com.vdeliverz_delivery.order_items_list.model.OrderDetailsResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsIntract implements  OrderDetailsContract.GetOrderDetailsIntractor {

    String TAG= OrderDetailsIntract.class.getSimpleName();
    String order_id;

    interface OnOrderDetailsListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String order_id,final OnOrderDetailsListener listener) {
        this.order_id=order_id;

        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void order_details_APICall(OnFinishedListener onFinishedListener) {
        VdeliverzApi.getClient().get_order_details(order_id).enqueue(new Callback<OrderDetailsResponse>(){
            @Override
            public void onResponse(@NotNull Call<OrderDetailsResponse> call,
                                   @NotNull Response<OrderDetailsResponse> response) {
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
            public void onFailure(@NotNull Call<OrderDetailsResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });
    }
}
