package com.vdeliverz_delivery.collect_cash;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class CashPresenter implements CashIntract.OnCashListener, CashContract.GetCashIntractor.OnFinishedListener{


    CashContract cashContract;
    CashIntract cashIntract;

    public CashPresenter(CashContract cashContract, CashIntract cashIntract){
        this.cashContract = cashContract;
        this.cashIntract = cashIntract;
    }

    public void validateDetails(String order_id, String cash_note){
        cashIntract.directValidation(order_id,cash_note,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        cashContract.cash_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        cashContract.cash_failure(error_msg);
    }

    @Override
    public void do_logout() {
        cashContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(cashContract != null) {
            cashIntract.cashAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        cashContract.cash_failure(msg);
    }


}
