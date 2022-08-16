package com.vdeliverz_delivery.profile.get_profile;


import com.vdeliverz_delivery.profile.get_profile.model.GetProfileResponse;

public interface GetProfileContract {

    void getProfile_success(GetProfileResponse getProfileResponse);

    void getProfile_failure(String msg);

    void dashboard_logout();

    interface GetProfileIntractor {

        interface OnFinishedListener {
            void onFinished(GetProfileResponse notifiationResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getProfileAPICall(OnFinishedListener onFinishedListener);
    }

}
