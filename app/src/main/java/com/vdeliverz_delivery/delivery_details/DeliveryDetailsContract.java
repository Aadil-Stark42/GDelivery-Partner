package com.vdeliverz_delivery.delivery_details;


import com.vdeliverz_delivery.delivery_details.model.DeliveryDetailResponse;

public interface DeliveryDetailsContract {

    void deliveryDetail_success(DeliveryDetailResponse deliveryDetailResponse);

    void deliveryDetail_failure(String msg);

    void dashboard_logout();

    interface GetdeliveryDetailIntractor {

        interface OnFinishedListener {
            void onFinished(DeliveryDetailResponse deliveryDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void deliveryDetailAPICall(OnFinishedListener onFinishedListener);
    }

}
