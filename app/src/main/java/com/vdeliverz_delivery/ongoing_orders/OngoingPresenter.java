package com.vdeliverz_delivery.ongoing_orders;


import com.vdeliverz_delivery.ongoing_orders.model.OngoingResponse;

public class OngoingPresenter implements OngoingIntract.OnOngoingListener, OngoingContract.GetOngoingIntractor.OnFinishedListener{


    OngoingContract ongoingContract;
    OngoingIntract ongoingIntract;



    public OngoingPresenter(OngoingContract ongoingContract, OngoingIntract ongoingIntract){
        this.ongoingContract = ongoingContract;
        this.ongoingIntract = ongoingIntract;
    }

    public void validateDetails(){
        ongoingIntract.directValidation(this);
    }

    @Override
    public void onFinished(OngoingResponse ongoingResponse) {
        ongoingContract.ongoing_success(ongoingResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        ongoingContract.ongoing_failure(error_msg);
    }

    @Override
    public void do_logout() {
        ongoingContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(ongoingContract != null) {
            ongoingIntract.ongoingAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        ongoingContract.ongoing_failure(msg);
    }


}
