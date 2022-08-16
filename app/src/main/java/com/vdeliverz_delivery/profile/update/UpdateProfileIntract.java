package com.vdeliverz_delivery.profile.update;

import android.os.Handler;
import android.util.Log;
import android.util.Patterns;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileIntract implements UpdateProfileContract.UpdateProfileIntractor  {

    String TAG= UpdateProfileIntract.class.getSimpleName();
    String mobile_num,user_name;
    String email_id;

    interface OnUpdateProfieListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String mobile,String email, String name, final OnUpdateProfieListener listener) {

        this.mobile_num=mobile;
        this.email_id=email;
        this.user_name=name;

        new Handler().postDelayed(() -> {
            if(mobile_num==null && mobile_num.isEmpty()){
                listener.onError("Mobile number issue");
            }
            if(email_id!=null){
                Log.d(TAG, "directValidation: emaill "+email_id);
                if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()){
                    listener.onError("Email ID Error");
                }
            }
            if(user_name==null && user_name.isEmpty()){
                listener.onError("User name error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void updateProfileAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().updateuser_profileApi(mobile_num,email_id,user_name).enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call, @NotNull Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 1");
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: 2");
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: 3");
                            if(response.body().getStatus()){
                                Log.d(TAG, "onResponse: 4");
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
