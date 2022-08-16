package com.vdeliverz_delivery.mark_as_delivered;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class DeliveredPresenter implements DeliveredIntract.OnDeliveredListener, DeliveredContract.GetDeliveredIntractor.OnFinishedListener{


    DeliveredContract deliveredContract;
    DeliveredIntract deliveredIntract;

    public DeliveredPresenter(DeliveredContract deliveredContract, DeliveredIntract deliveredIntract){
        this.deliveredContract = deliveredContract;
        this.deliveredIntract = deliveredIntract;
    }

    public void validateDetails(String order_id){
        deliveredIntract.directValidation(order_id,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        deliveredContract.delivered_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        deliveredContract.delivered_failure(error_msg);
    }

    @Override
    public void do_logout() {
        deliveredContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(deliveredContract != null) {
            deliveredIntract.deliveredAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        deliveredContract.delivered_failure(msg);
    }


}
