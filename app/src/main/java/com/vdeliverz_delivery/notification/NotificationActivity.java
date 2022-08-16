package com.vdeliverz_delivery.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.home.HomeScreenActivity;
import com.vdeliverz_delivery.notification.model.Datum;
import com.vdeliverz_delivery.notification.model.NotificationListResponse;
import com.vdeliverz_delivery.notification.notificationmvp.NotificationContract;
import com.vdeliverz_delivery.notification.notificationmvp.NotificationIntract;
import com.vdeliverz_delivery.notification.notificationmvp.NotificationPresenter;
import com.vdeliverz_delivery.utils.BaseActivity;


public class NotificationActivity extends BaseActivity implements NotificationContract {

    RecyclerView recyclerView;
    RelativeLayout rl_nodatafound;

    ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_notification);

            recyclerView=findViewById(R.id.recyclerView);
            rl_nodatafound=findViewById(R.id.rl_nodatafound);

            iv_back=findViewById(R.id.iv_back);

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callnextscreen();
                }
            });
            callnotificationapi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void callnextscreen(){
        try {
            Intent myintent=new Intent(NotificationActivity.this, HomeScreenActivity.class);
            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myintent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    /**
     * Success Response of Notification List ApI
     * @param notificationListResponse
     */
    @Override
    public void notification_success(NotificationListResponse notificationListResponse) {
        try {
            hideLoading();
            if (notificationListResponse!=null){
                if (notificationListResponse.getStatus()){
                    if (notificationListResponse.getData()!=null) {
                        if (notificationListResponse.getData().size() > 0) {
                            rl_nodatafound.setVisibility(View.GONE);
                            loadlist(notificationListResponse.getData());
                        } else {
                            Toast.makeText(this, "No Notification found", Toast.LENGTH_LONG).show();
                            rl_nodatafound.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                        else
                        Toast.makeText(this, "No Notification found", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Response Data is empty",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Response Data is empty",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notification_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    /**
     * Load Notification List
     * @param data
     */
    private void loadlist(List<Datum> data){
        try {
            NotificationAdapter adapter = new NotificationAdapter(this,data);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method used to get notification list
     */

    private  void callnotificationapi(){
        try {
            showLoading();
            NotificationPresenter notificationPresenter=new NotificationPresenter(NotificationActivity.this,new NotificationIntract());
            notificationPresenter.validateDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
