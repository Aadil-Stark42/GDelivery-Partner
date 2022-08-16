package com.vdeliverz_delivery.ongoing_orders;


import com.vdeliverz_delivery.ongoing_orders.model.OngoingResponse;

public interface OngoingContract {

    void ongoing_success(OngoingResponse ongoingResponse);

    void ongoing_failure(String msg);

    void dashboard_logout();

    interface GetOngoingIntractor {

        interface OnFinishedListener {
            void onFinished(OngoingResponse ongoingResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void ongoingAPICall(OnFinishedListener onFinishedListener);
    }

}
