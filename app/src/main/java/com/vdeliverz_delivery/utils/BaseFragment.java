package com.vdeliverz_delivery.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vdeliverz_delivery.login.LoginActivity;

public class BaseFragment extends Fragment {

    private Dialog mProgressDialog;
    String TAG = BaseFragment.class.getSimpleName();
    static AlertDialog builderObj;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoading() {
        Log.d(TAG, "showLoading: ");
        if ((mProgressDialog == null || !mProgressDialog.isShowing()))
            mProgressDialog = HassleProgressDialog.showLoader(getActivity(), false);
    }



    public void hideLoading() {
        HassleProgressDialog.dismissLoader(mProgressDialog);
    }


    @Override
    public void onDestroy() {
        if(mProgressDialog!=null)
            HassleProgressDialog.dismissLoader(mProgressDialog);
        super.onDestroy();
    }


    public void do_logout_and_login_redirect(){
        MnxPreferenceManager.clearAllPreferences();
        Intent myintent=new Intent(context, LoginActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        getActivity().finish();
    }


}
