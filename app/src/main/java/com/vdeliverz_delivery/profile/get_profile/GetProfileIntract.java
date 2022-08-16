package com.vdeliverz_delivery.profile.get_profile;

import android.os.Handler;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.profile.get_profile.model.GetProfileResponse;
import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetProfileIntract implements GetProfileContract.GetProfileIntractor  {

    String TAG= GetProfileIntract.class.getSimpleName();

    interface OnGetProfieListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation( final OnGetProfieListener listener) {
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getProfileAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().getuser_details().enqueue(new Callback<GetProfileResponse>(){
            @Override
            public void onResponse(@NotNull Call<GetProfileResponse> call, @NotNull Response<GetProfileResponse> response) {
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
            public void onFailure(@NotNull Call<GetProfileResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
