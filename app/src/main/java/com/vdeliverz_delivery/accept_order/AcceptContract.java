package com.vdeliverz_delivery.accept_order;


import com.vdeliverz_delivery.utils.GeneralResponse;

public interface AcceptContract {

    void accept_success(GeneralResponse generalResponse);

    void accept_failure(String msg);

    void dashboard_logout();

    interface GetAcceptIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void acceptAPICall(OnFinishedListener onFinishedListener);
    }

}
