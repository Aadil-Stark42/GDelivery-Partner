package com.vdeliverz_delivery.login;


import com.vdeliverz_delivery.login.model.LoginResponse;

public class LoginPresenter implements LoginIntract.OnLoginListener, LoginContract.GetLoginIntractor.OnFinishedListener{


    LoginContract loginContract;
    LoginIntract loginIntract;

    public LoginPresenter(LoginContract loginContract, LoginIntract loginIntract){
        this.loginContract = loginContract;
        this.loginIntract = loginIntract;
    }

    public void validateDetails(String user_id,String pass,String fcm_token){
        loginIntract.directValidation(user_id,pass,fcm_token,this);

    }

    @Override
    public void onFinished(LoginResponse loginResponse) {
        loginContract.login_success(loginResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        loginContract.login_failure(error_msg);
    }

    @Override
    public void do_logout() {
        loginContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(loginContract != null) {
            loginIntract.loginAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        loginContract.login_failure(msg);
    }


}
