package com.vdeliverz_delivery.mark_as_canceled;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class CancelPresenter implements CancelIntract.OnDeliveredListener, CancelContract.GetCancelIntractor.OnFinishedListener{


    CancelContract cancelContract;
    CancelIntract cancelIntract;

    public CancelPresenter(CancelContract cancelContract, CancelIntract cancelIntract){
        this.cancelContract = cancelContract;
        this.cancelIntract = cancelIntract;
    }

    public void validateDetails(String order_id){
        cancelIntract.directValidation(order_id,this);
    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        cancelContract.cancel_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        cancelContract.cancel_failure(error_msg);
    }

    @Override
    public void do_logout() {
        cancelContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(cancelContract != null) {
            cancelIntract.cancelAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        cancelContract.cancel_failure(msg);
    }


}
