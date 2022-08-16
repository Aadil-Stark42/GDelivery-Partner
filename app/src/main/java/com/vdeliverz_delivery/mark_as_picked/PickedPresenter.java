package com.vdeliverz_delivery.mark_as_picked;


import com.vdeliverz_delivery.utils.GeneralResponse;

public class PickedPresenter implements PickedIntract.OnPickedListener, PickedContract.GetPickedIntractor.OnFinishedListener{


    PickedContract pickedContract;
    PickedIntract pickedIntract;

    public PickedPresenter(PickedContract pickedContract, PickedIntract pickedIntract){
        this.pickedContract = pickedContract;
        this.pickedIntract = pickedIntract;
    }

    public void validateDetails(String order_id){
        pickedIntract.directValidation(order_id,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        pickedContract.picked_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        pickedContract.picked_failure(error_msg);
    }

    @Override
    public void do_logout() {
        pickedContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(pickedContract != null) {
            pickedIntract.pickedAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        pickedContract.picked_failure(msg);
    }


}
