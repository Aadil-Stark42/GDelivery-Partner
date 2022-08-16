package com.vdeliverz_delivery.home.dashdata_mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.home.model.DashboardResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardIntract implements DashboardContract.GetLoginIntractor  {

    String TAG= DashboardIntract.class.getSimpleName();


    interface OnDashistener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final OnDashistener listener) {

        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void dashAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().getOrdersList().enqueue(new Callback<DashboardResponse>(){
            @Override
            public void onResponse(@NotNull Call<DashboardResponse> call, @NotNull Response<DashboardResponse> response) {
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
            public void onFailure(@NotNull Call<DashboardResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
