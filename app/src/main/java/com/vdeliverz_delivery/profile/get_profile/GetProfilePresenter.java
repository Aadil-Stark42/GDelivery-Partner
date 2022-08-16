package com.vdeliverz_delivery.profile.get_profile;


import com.vdeliverz_delivery.profile.get_profile.model.GetProfileResponse;

public class GetProfilePresenter implements GetProfileIntract.OnGetProfieListener, GetProfileContract.GetProfileIntractor.OnFinishedListener{


    GetProfileContract getProfileContract;
    GetProfileIntract getProfileIntract;
    String TAG = GetProfilePresenter.class.getSimpleName();


    public GetProfilePresenter(GetProfileContract getProfileContract, GetProfileIntract getProfileIntract) {
        this.getProfileContract = getProfileContract;
        this.getProfileIntract = getProfileIntract;
    }

    public void validateDetails(){
        getProfileIntract.directValidation(this);
    }

    @Override
    public void onFinished(GetProfileResponse getProfileResponse) {
        getProfileContract.getProfile_success(getProfileResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        getProfileContract.getProfile_failure(error_msg);
    }

    @Override
    public void do_logout() {
        getProfileContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(getProfileContract != null) {
            getProfileIntract.getProfileAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        getProfileContract.getProfile_failure(msg);
    }


}
