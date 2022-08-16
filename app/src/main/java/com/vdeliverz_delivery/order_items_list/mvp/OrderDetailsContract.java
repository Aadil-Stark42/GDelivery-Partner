package com.vdeliverz_delivery.order_items_list.mvp;


import com.vdeliverz_delivery.order_items_list.model.OrderDetailsResponse;

public interface OrderDetailsContract {

    void order_details_success(OrderDetailsResponse orderDetailsResponse);

    void order_details_failure(String msg);

    void dashboard_logout();

    interface GetOrderDetailsIntractor {

        interface OnFinishedListener {
            void onFinished(OrderDetailsResponse orderDetailsResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void order_details_APICall(OnFinishedListener onFinishedListener);
    }
}
