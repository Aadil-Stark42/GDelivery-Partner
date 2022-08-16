package com.vdeliverz_delivery.delivered.deliveredmvp;

import com.vdeliverz_delivery.delivered.model.DeliveredListResponse;

public interface DeliveredContract {

    void delivered_success(DeliveredListResponse deliveredListResponse);

    void delivered_failure(String msg);

    void dashboard_logout();

    interface GetDeliveredIntractor {

        interface OnFinishedListener {
            void onFinished(DeliveredListResponse deliveredListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void delivered_APICall(GetDeliveredIntractor.OnFinishedListener onFinishedListener);
    }
}
