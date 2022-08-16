package com.vdeliverz_delivery.accept_order;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class AcceptPresenter implements AcceptIntract.OnAcceptListener, AcceptContract.GetAcceptIntractor.OnFinishedListener{


    AcceptContract acceptContract;
    AcceptIntract acceptIntract;

    public AcceptPresenter(AcceptContract acceptContract, AcceptIntract acceptIntract){
        this.acceptContract = acceptContract;
        this.acceptIntract = acceptIntract;
    }

    public void validateDetails(String order_id){
        acceptIntract.directValidation(order_id,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        acceptContract.accept_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        acceptContract.accept_failure(error_msg);
    }

    @Override
    public void do_logout() {
        acceptContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(acceptContract != null) {
            acceptIntract.acceptAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        acceptContract.accept_failure(msg);
    }


}
