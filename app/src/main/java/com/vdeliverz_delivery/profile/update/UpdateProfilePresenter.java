package com.vdeliverz_delivery.profile.update;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class UpdateProfilePresenter implements UpdateProfileIntract.OnUpdateProfieListener, UpdateProfileContract.UpdateProfileIntractor.OnFinishedListener{


    UpdateProfileContract updateProfileContract;
    UpdateProfileIntract updateProfileIntract;
    String TAG = UpdateProfilePresenter.class.getSimpleName();


    public UpdateProfilePresenter(UpdateProfileContract updateProfileContract, UpdateProfileIntract updateProfileIntract) {
        this.updateProfileContract = updateProfileContract;
        this.updateProfileIntract = updateProfileIntract;
    }

    public void validateDetails(String mobile,String email, String name){
        updateProfileIntract.directValidation(mobile,email,name,this);
    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        updateProfileContract.updateProfile_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        updateProfileContract.updateProfile_failure(error_msg);
    }

    @Override
    public void do_logout() {
        updateProfileContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(updateProfileContract != null) {
            updateProfileIntract.updateProfileAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        updateProfileContract.updateProfile_failure(msg);
    }


}
