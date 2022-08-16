package com.vdeliverz_delivery.mark_as_reject;


import com.vdeliverz_delivery.utils.GeneralResponse;

public interface RejectContract {

    void reject_success(GeneralResponse generalResponse);

    void reject_failure(String msg);

    void dashboard_logout();

    interface GetRejectIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void rejectAPICall(OnFinishedListener onFinishedListener);
    }

}
