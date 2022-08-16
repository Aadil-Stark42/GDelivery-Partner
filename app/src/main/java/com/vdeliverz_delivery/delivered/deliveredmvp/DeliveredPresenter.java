package com.vdeliverz_delivery.delivered.deliveredmvp;

import com.vdeliverz_delivery.delivered.model.DeliveredListResponse;


public class DeliveredPresenter implements  DeliveredIntract.OnDeliveredListener,DeliveredContract.GetDeliveredIntractor.OnFinishedListener{

    DeliveredContract deliveredContract;
    DeliveredIntract deliveredIntract;

    public DeliveredPresenter(DeliveredContract deliveredContract, DeliveredIntract deliveredIntract){
        this.deliveredContract = deliveredContract;
        this.deliveredIntract = deliveredIntract;
    }

    @Override
    public void onSuccess() {
        if(deliveredContract != null) {
            deliveredIntract.delivered_APICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        deliveredContract.delivered_failure(msg);

    }

    @Override
    public void onFinished(DeliveredListResponse deliveredListResponse) {
        deliveredContract.delivered_success(deliveredListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        deliveredContract.delivered_failure(error_msg);
    }

    @Override
    public void do_logout() {
        deliveredContract.dashboard_logout();
    }

    public void validateDetails(){
        deliveredIntract.directValidation(this);
    }

}
