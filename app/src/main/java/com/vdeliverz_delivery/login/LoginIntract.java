package com.vdeliverz_delivery.login;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.login.model.LoginResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginIntract implements LoginContract.GetLoginIntractor  {

    String TAG= LoginIntract.class.getSimpleName();
    String user_name,password,fcm_token;

    interface OnLoginListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String user,String pass,String fcm_token, final OnLoginListener listener) {
        this.user_name=user;
        this.password=pass;
        this.fcm_token=fcm_token;

        new Handler().postDelayed(() -> {
            if(user_name == null || user_name.isEmpty()){
                listener.onError("User ID Error");
            }
            if(password == null || password.isEmpty()){
                listener.onError("User ID Error");
            }if(fcm_token.isEmpty()){
                listener.onError("FCM token issue");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void loginAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().getloginApi(user_name,password,fcm_token).enqueue(new Callback<LoginResponse>(){
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
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
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
