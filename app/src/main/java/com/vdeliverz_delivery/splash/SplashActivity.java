package com.vdeliverz_delivery.splash;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.firebase.messaging.FirebaseMessaging;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.home.HomeScreenActivity;
import com.vdeliverz_delivery.login.LoginActivity;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class SplashActivity extends BaseActivity {

    private static final int REQUEST_APP_UPDATE = 200;
    private static final int REQUEST_APP_UPDATEINPROGESS = 201;
    AppUpdateManager mAppUpdateManager;
    InstallStateUpdatedListener listener;
    ImageView imgSplash;
    String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        get_fcm_token();
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        imgSplash = findViewById(R.id.imgSplash);
        Glide.with(this).load(R.drawable.insuvai_splash).into(imgSplash);

        listener = state -> {
            Log.d(TAG, "onStateUpdate: " + state.toString());

            if (state.installStatus() == InstallStatus.INSTALLED) {
                Log.d(TAG, "onStateUpdate: after Install the update");
            }
        };

        mAppUpdateManager.registerListener(listener);

        mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            Log.d(TAG, "onCreate: on scucess listner");

                            if (appUpdateInfo.installStatus() == InstallStatus.INSTALLED) {

                                Log.d(TAG, "onCreate: app is install");
                            }
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                Log.d(TAG, "onCreate:  app is downloaded");
                            }

                            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {

                                Log.d(TAG, "onCreate: update is avaliable");
                                // Request the update.
                                try {
                                    mAppUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            REQUEST_APP_UPDATE);


                                } catch (IntentSender.SendIntentException e) {

                                    Log.e(TAG, "onCreate update error: " + e.getMessage());
                                    //e.printStackTrace();
                                }
                            } else {
                                Log.e(TAG, "onCreate:  is update is not  avaliable ");
                                splashTask();

                            }
                        }
                )
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: listener" + e.getMessage());

                    splashTask();
                });


        //throw new RuntimeException("Test Crash"); // Force a crash


    }

    private void splashTask() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MnxPreferenceManager.getString(MnxConstant.TOKEN, null) != null) {
                    Intent myintent = new Intent(SplashActivity.this, HomeScreenActivity.class);
                    startActivity(myintent);
                    finish();
                } else {
                    Intent myintent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(myintent);
                    finish();
                }
            }
        }, 2000);

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            mAppUpdateManager
                    .getAppUpdateInfo()
                    .addOnSuccessListener(
                            appUpdateInfo -> {
                                Log.d(TAG, "onResume: appa update");
                                if (appUpdateInfo.updateAvailability()

                                        == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                    // If an in-app update is already running, resume the update.
                                    try {
                                        mAppUpdateManager.startUpdateFlowForResult(
                                                appUpdateInfo,
                                                IMMEDIATE,
                                                this,
                                                REQUEST_APP_UPDATEINPROGESS);
                                    } catch (IntentSender.SendIntentException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, "Resume onFailure: listener" + e.getMessage());

                    splashTask();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "onResume: ");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            if (mAppUpdateManager != null) {
                mAppUpdateManager.unregisterListener(listener);
            }
        } catch (Exception e) {
            Log.d(TAG, "onDestroy: " + e.getMessage());
        }
    }


    public void get_fcm_token() {
        try {
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
                            Log.d(TAG, "onComplete:  fcmmmmm " + token);
                            // Log and toast
                            //Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                            MnxPreferenceManager.setString(MnxConstant.FCM_TOKEN, token);
                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "get_fcm_token: ");
        }
    }
}
