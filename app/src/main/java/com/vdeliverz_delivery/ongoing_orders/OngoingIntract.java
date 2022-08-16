package com.vdeliverz_delivery.ongoing_orders;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.ongoing_orders.model.OngoingResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OngoingIntract implements OngoingContract.GetOngoingIntractor  {

    String TAG= OngoingIntract.class.getSimpleName();


    interface OnOngoingListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final OnOngoingListener listener) {
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void ongoingAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().getOngoingList().enqueue(new Callback<OngoingResponse>(){
            @Override
            public void onResponse(@NotNull Call<OngoingResponse> call, @NotNull Response<OngoingResponse> response) {
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
            public void onFailure(@NotNull Call<OngoingResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
