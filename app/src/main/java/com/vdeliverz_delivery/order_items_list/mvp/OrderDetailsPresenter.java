package com.vdeliverz_delivery.order_items_list.mvp;


import com.vdeliverz_delivery.notification.model.NotificationListResponse;
import com.vdeliverz_delivery.order_items_list.model.OrderDetailsResponse;

public class OrderDetailsPresenter implements OrderDetailsIntract.OnOrderDetailsListener,
        OrderDetailsContract.GetOrderDetailsIntractor.OnFinishedListener {

    OrderDetailsContract notificationContract;
    OrderDetailsIntract notificationIntract;

    public OrderDetailsPresenter(OrderDetailsContract notificationContract, OrderDetailsIntract notificationIntract){
        this.notificationContract = notificationContract;
        this.notificationIntract = notificationIntract;
    }

    @Override
    public void onFinished(OrderDetailsResponse orderDetailsResponse){
        notificationContract.order_details_success(orderDetailsResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        notificationContract.order_details_failure(error_msg);
    }

    @Override
    public void do_logout() {
        notificationContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if(notificationContract != null) {
            notificationIntract.order_details_APICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        notificationContract.order_details_failure(msg);
    }

    public void validateDetails(String order_id){
        notificationIntract.directValidation(order_id,this);
    }
}
