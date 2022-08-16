package com.vdeliverz_delivery.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.home.HomeScreenActivity;
import com.vdeliverz_delivery.login.model.LoginResponse;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

public class LoginActivity extends BaseActivity implements LoginContract {

    TextView tv_login;
    ImageView password_imageView;
    EditText tv_password_value,tv_userid_value;
    private boolean isShowPassword = false;
    LoginPresenter loginPresenter;
    String TAG=LoginActivity.class.getSimpleName();
    String fcm_token;
    ImageView imgLoginBg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        get_fcm_token();

        imgLoginBg=findViewById(R.id.imgLoginBg);
        Glide.with(this).load(R.drawable.login_background).into(imgLoginBg);
        tv_login=findViewById(R.id.tv_login_value);
        password_imageView=findViewById(R.id.password_imageView);
        tv_password_value=findViewById(R.id.tv_password_value);
        tv_userid_value=findViewById(R.id.tv_userid_value);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callnextscreen();
                String user_id=tv_userid_value.getText().toString().trim();
                String pass=tv_password_value.getText().toString().trim();

                if(user_id!=null) {
                    if(pass!=null){
                        if(fcm_token!=null) {
                            showLoading();
                            loginPresenter = new LoginPresenter(LoginActivity.this, new LoginIntract());
                            loginPresenter.validateDetails(user_id, pass, fcm_token);
                        }else{
                            Toast.makeText(LoginActivity.this, "FCM Token Issue", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Enter valid Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Enter valid User-ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        password_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowPassword) {
                    tv_password_value.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_closed));
                    isShowPassword = false;
                    tv_password_value.setSelection(tv_password_value.getText().length());
                } else {
                    tv_password_value.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));
                    isShowPassword = true;
                    tv_password_value.setSelection(tv_password_value.getText().length());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void callnextscreen(){
        try {
            Intent myintent=new Intent(LoginActivity.this, HomeScreenActivity.class);
            startActivity(myintent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login_success(LoginResponse loginResponse) {
        hideLoading();
        if(loginResponse!=null){
            if(loginResponse.getStatus()){
                MnxPreferenceManager.setString(MnxConstant.TOKEN,loginResponse.getToken());
                if(loginResponse.getUser()!=null){
                    MnxPreferenceManager.setString(MnxConstant.USER_MOBILE,loginResponse.getUser().getMobile());
                    MnxPreferenceManager.setString(MnxConstant.USER_EMAIL,loginResponse.getUser().getEmail());
                    MnxPreferenceManager.setString(MnxConstant.USER_NAME,loginResponse.getUser().getName());
                    Intent myintent=new Intent(LoginActivity.this, HomeScreenActivity.class);
                    startActivity(myintent);
                    finish();
                }
            }else{
                Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void login_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    public void get_fcm_token(){
        try{
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            // Get new FCM registration token
                            String token = task.getResult();
                            fcm_token=token;
                            // Log and toast
                            //Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                            MnxPreferenceManager.setString(MnxConstant.FCM_TOKEN,token);
                        }
                    });
        }catch(Exception e){

        }
    }
}
