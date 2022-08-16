package com.vdeliverz_delivery.logout;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class LogoutPresenter implements LogoutIntract.OnDashistener,
        LogoutContract.GetLogoutIntractor.OnFinishedListener{

    LogoutContract logoutContract;
    LogoutIntract logoutIntract;

    public LogoutPresenter(LogoutContract logoutContract, LogoutIntract logoutIntract){
        this.logoutContract = logoutContract;
        this.logoutIntract = logoutIntract;
    }

    public void validateDetails(){
        logoutIntract.directValidation(this);
    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        logoutContract.logout_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        logoutContract.logout_failure(error_msg);
    }

    @Override
    public void do_logout() {
        logoutContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if(logoutContract != null) {
            logoutIntract.logoutAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        logoutContract.logout_failure(msg);
    }


}
