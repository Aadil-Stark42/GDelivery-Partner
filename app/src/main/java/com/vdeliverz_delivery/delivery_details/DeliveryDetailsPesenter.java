package com.vdeliverz_delivery.delivery_details;


import com.vdeliverz_delivery.delivery_details.model.DeliveryDetailResponse;

public class DeliveryDetailsPesenter implements DeliveryDetailsIntract.OnDeliveryDetailListener, DeliveryDetailsContract.GetdeliveryDetailIntractor.OnFinishedListener{


    DeliveryDetailsContract cancelContract;
    DeliveryDetailsIntract cancelIntract;

    public DeliveryDetailsPesenter(DeliveryDetailsContract cancelContract, DeliveryDetailsIntract cancelIntract){
        this.cancelContract = cancelContract;
        this.cancelIntract = cancelIntract;
    }

    public void validateDetails(String order_id){
        cancelIntract.directValidation(order_id,this);
    }

    @Override
    public void onFinished(DeliveryDetailResponse deliveryDetailResponse) {
        cancelContract.deliveryDetail_success(deliveryDetailResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        cancelContract.deliveryDetail_failure(error_msg);
    }

    @Override
    public void do_logout() {
        cancelContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(cancelContract != null) {
            cancelIntract.deliveryDetailAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        cancelContract.deliveryDetail_failure(msg);
    }


}
