package com.vdeliverz_delivery.mark_as_picked;


import com.vdeliverz_delivery.utils.GeneralResponse;

public interface PickedContract {

    void picked_success(GeneralResponse generalResponse);

    void picked_failure(String msg);

    void dashboard_logout();

    interface GetPickedIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void pickedAPICall(OnFinishedListener onFinishedListener);
    }

}
