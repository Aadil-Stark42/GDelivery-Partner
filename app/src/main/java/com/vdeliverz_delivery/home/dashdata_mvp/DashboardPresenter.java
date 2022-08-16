package com.vdeliverz_delivery.home.dashdata_mvp;


import com.vdeliverz_delivery.home.model.DashboardResponse;

public class DashboardPresenter implements DashboardIntract.OnDashistener, DashboardContract.GetLoginIntractor.OnFinishedListener{


    DashboardContract dashboardContract;
    DashboardIntract dashboardIntract;



    public DashboardPresenter(DashboardContract dashboardContract, DashboardIntract dashboardIntract){
        this.dashboardContract = dashboardContract;
        this.dashboardIntract = dashboardIntract;
    }

    public void validateDetails(){
        dashboardIntract.directValidation(this);
    }

    @Override
    public void onFinished(DashboardResponse dashboardResponse) {
        dashboardContract.dash_success(dashboardResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        dashboardContract.dash_failure(error_msg);
    }

    @Override
    public void do_logout() {
        dashboardContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(dashboardContract != null) {
            dashboardIntract.dashAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        dashboardContract.dash_failure(msg);
    }


}
