package com.vdeliverz_delivery.mark_as_reject;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class RejectPresenter implements RejectIntract.OnRejectListener, RejectContract.GetRejectIntractor.OnFinishedListener{


    RejectContract rejectContract;
    RejectIntract rejectIntract;

    public RejectPresenter(RejectContract rejectContract, RejectIntract rejectIntract){
        this.rejectContract = rejectContract;
        this.rejectIntract = rejectIntract;
    }

    public void validateDetails(String order_id){
        rejectIntract.directValidation(order_id,this);
    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        rejectContract.reject_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        rejectContract.reject_failure(error_msg);
    }

    @Override
    public void do_logout() {
        rejectContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(rejectContract != null) {
            rejectIntract.rejectAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        rejectContract.reject_failure(msg);
    }


}
