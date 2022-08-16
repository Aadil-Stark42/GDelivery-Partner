package com.vdeliverz_delivery.delivery_details;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.delivery_details.model.DeliveryDetailResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveryDetailsIntract implements DeliveryDetailsContract.GetdeliveryDetailIntractor  {

    String TAG= DeliveryDetailsIntract.class.getSimpleName();
    String order_id;

    interface OnDeliveryDetailListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String orderid, final OnDeliveryDetailListener listener) {
        this.order_id=orderid;

        new Handler().postDelayed(() -> {
            if(order_id == null || order_id.isEmpty()){
                listener.onError("Order ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void deliveryDetailAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().deliverydetailApicall(order_id).enqueue(new Callback<DeliveryDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<DeliveryDetailResponse> call, @NotNull Response<DeliveryDetailResponse> response) {
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
            public void onFailure(@NotNull Call<DeliveryDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
