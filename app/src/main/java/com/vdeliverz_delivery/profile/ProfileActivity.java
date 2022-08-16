package com.vdeliverz_delivery.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.logout.LogoutContract;
import com.vdeliverz_delivery.logout.LogoutIntract;
import com.vdeliverz_delivery.logout.LogoutPresenter;
import com.vdeliverz_delivery.splash.SplashActivity;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;


public class ProfileActivity extends BaseActivity implements LogoutContract {

    ImageView iv_back;
    ConstraintLayout con_edit,cons_about;
    TextView tv_login_value,tv_name,tv_number;
    LogoutPresenter logoutPresenter;
    String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        iv_back=findViewById(R.id.iv_back);
        tv_number=findViewById(R.id.tv_number);
        tv_name=findViewById(R.id.tv_name);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        con_edit=findViewById(R.id.cons_edit);
        cons_about=findViewById(R.id.cons_about);

        token=MnxPreferenceManager.getString(MnxConstant.TOKEN,null);

        if(MnxPreferenceManager.getString(MnxConstant.USER_NAME,null)!=null){
            tv_name.setText(MnxPreferenceManager.getString(MnxConstant.USER_NAME,null));
        }

        if(MnxPreferenceManager.getString(MnxConstant.USER_MOBILE,null)!=null){
            tv_number.setText(MnxPreferenceManager.getString(MnxConstant.USER_MOBILE,null));
        }

        con_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MnxPreferenceManager.getString(MnxConstant.TOKEN,null)!=null) {
                    Intent myintent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                    startActivity(myintent);
                }else{
                    Toast.makeText(ProfileActivity.this, "Token Issue", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cons_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent myintent = new Intent(ProfileActivity.this, ProfileAboutFAQ.class);
               startActivity(myintent);
            }
        });

        tv_login_value=findViewById(R.id.tv_login_value);

        tv_login_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Logout").
                        setMessage("Are you sure,want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                do_logout_apicall();
                                /*MnxPreferenceManager.clearAllPreferences();
                                Intent i = new Intent(ProfileActivity.this, SplashActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);*/

                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
    }


    public void do_logout_apicall(){
        if(token!=null) {
            showLoading();
            logoutPresenter = new LogoutPresenter(this, new LogoutIntract());
            logoutPresenter.validateDetails();
        }else{
            Toast.makeText(this, "Token issue", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void logout_success(GeneralResponse generalResponse) {
        hideLoading();
        if(generalResponse!=null){
            if(generalResponse.getStatus()){
                  MnxPreferenceManager.clearAllPreferences();
                  Intent i = new Intent(ProfileActivity.this, SplashActivity.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(i);
            }else{
                Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void logout_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
