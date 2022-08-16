package com.vdeliverz_delivery.delivered.canceledmvp;


import com.vdeliverz_delivery.delivered.model.DeliveredListResponse;

public class CancelledPresenter implements CancelledContract.GetCancelledIntractor.OnFinishedListener,CancelledIntract.OnCancelledListener {

    CancelledContract cancelledContract;
    CancelledIntract cancelledIntract;

    public CancelledPresenter(CancelledContract cancelledContract, CancelledIntract cancelledIntract){
        this.cancelledContract = cancelledContract;
        this.cancelledIntract = cancelledIntract;
    }

    @Override
    public void onFinished(DeliveredListResponse deliveredListResponse) {
        cancelledContract.cancelled_success(deliveredListResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        cancelledContract.cancelled_failure(error_msg);

    }

    @Override
    public void do_logout() {
        cancelledContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if(cancelledContract != null) {
            cancelledIntract.cancelled_APICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        cancelledContract.cancelled_failure(msg);
    }
    public void validateDetails(){

        cancelledIntract.directValidation(this);
    }
}
