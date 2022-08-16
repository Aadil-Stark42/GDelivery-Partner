package com.vdeliverz_delivery.collect_cash;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CashIntract implements CashContract.GetCashIntractor  {

    String TAG= CashIntract.class.getSimpleName();
    String order_id,cash_note;

    interface OnCashListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String orderid,String cash_note, final OnCashListener listener) {
        this.order_id=orderid;

        new Handler().postDelayed(() -> {
            if(order_id == null || order_id.isEmpty()){
                listener.onError("Order ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void cashAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().collectcashApicall(order_id,cash_note).enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call, @NotNull Response<GeneralResponse> response) {
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
