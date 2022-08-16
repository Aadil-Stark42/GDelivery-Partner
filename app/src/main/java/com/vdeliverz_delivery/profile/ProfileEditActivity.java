package com.vdeliverz_delivery.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.profile.get_profile.GetProfileContract;
import com.vdeliverz_delivery.profile.get_profile.GetProfileIntract;
import com.vdeliverz_delivery.profile.get_profile.GetProfilePresenter;
import com.vdeliverz_delivery.profile.get_profile.model.GetProfileResponse;
import com.vdeliverz_delivery.profile.update.UpdateProfileContract;
import com.vdeliverz_delivery.profile.update.UpdateProfileIntract;
import com.vdeliverz_delivery.profile.update.UpdateProfilePresenter;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

public class ProfileEditActivity extends BaseActivity implements GetProfileContract, UpdateProfileContract {

    ImageView  iv_back;
    EditText ed_name,ed_phonenumber,ed_mailid;
    String mobile,email,name;
    TextView tv_savechange;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.profile_edit);
            ed_phonenumber=findViewById(R.id.ed_phonenumber);
            ed_mailid=findViewById(R.id.ed_mailid);
            ed_name=findViewById(R.id.ed_name);
            tv_savechange=findViewById(R.id.tv_savechange);
            iv_back=findViewById(R.id.iv_back);

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tv_savechange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobile=ed_phonenumber.getText().toString().trim();
                    email=ed_mailid.getText().toString().trim();
                    name=ed_name.getText().toString().trim();
                    showLoading();
                    UpdateProfilePresenter updateProfilePresenter=new UpdateProfilePresenter(ProfileEditActivity.this,new UpdateProfileIntract());
                    updateProfilePresenter.validateDetails(mobile,email,name);
                }
            });

            do_getProfile_call();
            set_data_from_pref();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        set_data_from_pref();
    }

    private  void callnextscreen(){
        finish();
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            callnextscreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProfile_success(GetProfileResponse getProfileResponse) {
        try {
            hideLoading();
            if(getProfileResponse!=null){
                if(getProfileResponse.getStatus()){
                    if(getProfileResponse.getUser()!=null){
                        if(getProfileResponse.getUser().getName()!=null) {
                            ed_name.setText(getProfileResponse.getUser().getName());
                            MnxPreferenceManager.setString(MnxConstant.USER_NAME,getProfileResponse.getUser().getName());
                        }
                        else {
                            ed_name.setText("");
                        }
                        if(getProfileResponse.getUser().getEmail()!=null) {
                            ed_mailid.setText(getProfileResponse.getUser().getEmail());
                            MnxPreferenceManager.setString(MnxConstant.USER_EMAIL,getProfileResponse.getUser().getEmail());
                        }
                        else {
                            ed_mailid.setText("");
                        }
                        if(getProfileResponse.getUser().getMobile()!=null) {
                            ed_phonenumber.setText(getProfileResponse.getUser().getMobile());
                            MnxPreferenceManager.setString(MnxConstant.USER_MOBILE,getProfileResponse.getUser().getMobile());
                        }
                        else {
                            ed_phonenumber.setText("");
                        }
                    }else{
                        Toast.makeText(this, "User Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProfile_failure(String msg) {
        try {
            hideLoading();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile_success(GeneralResponse generalResponse) {
        try {
            hideLoading();
            MnxPreferenceManager.setString(MnxConstant.USER_NAME,name);
            MnxPreferenceManager.setString(MnxConstant.USER_MOBILE,mobile);
            Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile_failure(String msg) {
        try {
            hideLoading();
            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    private void set_data_from_pref() {
        try {
            if(MnxPreferenceManager.getString(MnxConstant.USER_NAME,null)!=null){
                ed_name.setText(MnxPreferenceManager.getString(MnxConstant.USER_NAME,null));
            }
            if(MnxPreferenceManager.getString(MnxConstant.USER_EMAIL,null)!=null){
                ed_mailid.setText(MnxPreferenceManager.getString(MnxConstant.USER_EMAIL,null));
            }
            if(MnxPreferenceManager.getString(MnxConstant.USER_MOBILE,null)!=null){
                ed_phonenumber.setText(MnxPreferenceManager.getString(MnxConstant.USER_MOBILE,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * used to get user details
     */

    private void do_getProfile_call() {
        try {
            String token= MnxPreferenceManager.getString(MnxConstant.TOKEN,null);
            if(token!=null){
                showLoading();
                GetProfilePresenter getProfilePresenter=new GetProfilePresenter(this,new GetProfileIntract());
                getProfilePresenter.validateDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
