package com.vdeliverz_delivery.notification.notificationmvp;


import com.vdeliverz_delivery.notification.model.NotificationListResponse;

public class NotificationPresenter implements NotificationIntract.OnNotificationListener,NotificationContract.GetNotificationIntractor.OnFinishedListener {

    NotificationContract notificationContract;
    NotificationIntract notificationIntract;

    public NotificationPresenter(NotificationContract notificationContract, NotificationIntract notificationIntract){
        this.notificationContract = notificationContract;
        this.notificationIntract = notificationIntract;
    }

    @Override
    public void onFinished(NotificationListResponse notificationListResponse) {
        notificationContract.notification_success(notificationListResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        notificationContract.notification_failure(error_msg);
    }

    @Override
    public void do_logout() {
        notificationContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if(notificationContract != null) {
            notificationIntract.notification_APICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        notificationContract.notification_failure(msg);
    }
    public void validateDetails(){

        notificationIntract.directValidation(this);
    }
}
