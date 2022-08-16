package com.vdeliverz_delivery.search;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import com.vdeliverz_delivery.retrofit.VdeliverzApi;
import com.vdeliverz_delivery.search.model.SearchResultResponse;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchIntract implements SearchContract.GetsearchIntractor  {

    String TAG= SearchIntract.class.getSimpleName();
    String search_keyword;

    interface OnSearchListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String search_keyword, final OnSearchListener listener) {
        this.search_keyword=search_keyword;

        new Handler().postDelayed(() -> {
            if(search_keyword == null || search_keyword.isEmpty()){
                listener.onError("Keyword Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void searchAPICall(OnFinishedListener onFinishedListener) {

        VdeliverzApi.getClient().searchApicall(search_keyword).enqueue(new Callback<SearchResultResponse>(){
            @Override
            public void onResponse(@NotNull Call<SearchResultResponse> call, @NotNull Response<SearchResultResponse> response) {
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
            public void onFailure(@NotNull Call<SearchResultResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
