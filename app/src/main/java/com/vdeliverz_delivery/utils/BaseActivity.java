package com.vdeliverz_delivery.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vdeliverz_delivery.login.LoginActivity;


public class BaseActivity extends AppCompatActivity {


    ProgressDialog progressDialog;
    String TAG= BaseActivity.class.getSimpleName();
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(BaseActivity.this);
    }


    public void showProgress(String title, String message) {
        showCustomdialog_progress(title, message);
    }

    public void dismissProgress(){
        Log.d(TAG, "dismissProgress: ");
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showCustomdialog_progress(String title, String message) {

        if (progressDialog != null) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
    }
    public void showPopup(String title, String exit){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this);
        alertDialogBuilder.setMessage(title);
        alertDialogBuilder.setPositiveButton(exit,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                        finishActivity(1);
                        dismissProgress();
                    }
                }).create();

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }





    public void showLoading()
    {
        Log.d(TAG, "showLoading: ");
        if ( (mProgressDialog == null || !mProgressDialog.isShowing()))
            mProgressDialog = HassleProgressDialog.showLoader(this, false);
    }


    public void hideLoading()
    {
        HassleProgressDialog.dismissLoader(mProgressDialog);
    }


    @Override
    protected void onDestroy()
    {
        if(mProgressDialog!=null)
            HassleProgressDialog.dismissLoader(mProgressDialog);
        super.onDestroy();
    }


    public void do_logout_and_login_redirect(){
        MnxPreferenceManager.clearAllPreferences();
        Intent myintent=new Intent(getApplicationContext(), LoginActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();
    }


}
