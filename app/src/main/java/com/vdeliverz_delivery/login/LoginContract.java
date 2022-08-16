package com.vdeliverz_delivery.login;


import com.vdeliverz_delivery.login.model.LoginResponse;

public interface LoginContract {

    void login_success(LoginResponse loginResponse);

    void login_failure(String msg);

    void dashboard_logout();

    interface GetLoginIntractor {

        interface OnFinishedListener {
            void onFinished(LoginResponse loginResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void loginAPICall(OnFinishedListener onFinishedListener);
    }

}
