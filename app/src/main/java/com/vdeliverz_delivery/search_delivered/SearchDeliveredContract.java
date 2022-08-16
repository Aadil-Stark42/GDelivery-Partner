package com.vdeliverz_delivery.search_delivered;


import com.vdeliverz_delivery.search_delivered.model.DeliveredSearchResponse;

public interface SearchDeliveredContract {

    void search_delivered_success(DeliveredSearchResponse searchResultResponse);

    void search_delivered_failure(String msg);

    void dashboard_logout();

    interface GetsearchIntractor {
        interface OnFinishedListener {
            void onFinished(DeliveredSearchResponse searchResultResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void search_delivered_APICall(OnFinishedListener onFinishedListener);
    }

}
