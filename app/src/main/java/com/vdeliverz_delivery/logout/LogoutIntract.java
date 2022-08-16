package com.vdeliverz_delivery.logout;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogoutIntract implements LogoutContract.GetLogoutIntractor  {

    String TAG= LogoutIntract.class.getSimpleName();

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
    public void logoutAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().do_logout_apicall().enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call,
                                   @NotNull Response<GeneralResponse> response) {
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
            public void onFailure(@NotNull Call<GeneralResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
