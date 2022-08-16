package com.vdeliverz_delivery.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.notification.NotificationActivity;
import com.vdeliverz_delivery.profile.ProfileActivity;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

public class HomeScreenActivity extends BaseActivity {

    BottomNavigationView bottom_navigation;
    ImageView iv_notification,iv_profile,iv_refresh;
    TextView tv_header,notification_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        Log.i("TESTTOKEN","TESTTOKEN"+MnxPreferenceManager.getString(MnxConstant.TOKEN,""));

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        iv_notification=findViewById(R.id.iv_notification);
        notification_count=findViewById(R.id.notification_count);

        iv_profile=findViewById(R.id.iv_profile);
        iv_refresh=findViewById(R.id.iv_refresh);
        tv_header=findViewById(R.id.tv_header);

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen(1);
            }
        });

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*OrderFragment fragment = getFragmentManager().
                        findFragmentById(R.id.orders_act_parent_const);
                fragment.dash_apicall();*/

                OrderFragment fragment = (OrderFragment) getSupportFragmentManager().
                        findFragmentById(R.id.frameLayout);
                assert fragment != null;
                fragment.dash_apicall();
            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen(2);
            }
        });

            Fragment myordersFragment = new OrderFragment();
            iv_refresh.setVisibility(View.VISIBLE);
            loadMainFragment(myordersFragment, false, R.id.frameLayout);
            MnxPreferenceManager.setBoolean(MnxConstant.IS_DASHBOARD,true);
            bottom_navigation.getMenu().getItem(0).setIcon(R.drawable.ic_orders_active);
            tv_header.setText("New Orders");
            iv_refresh.setVisibility(View.VISIBLE);
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    bottom_navigation.getMenu().getItem(0).setIcon(R.drawable.ic_orders_active);
                    bottom_navigation.getMenu().getItem(1).setIcon(R.drawable.ic_ongoing);
                    bottom_navigation.getMenu().getItem(2).setIcon(R.drawable.ic_delivered_active);

                    switch (item.getItemId()) {
                        case R.id.navigation_order:
                            Fragment myordersFragment = new OrderFragment();
                            iv_refresh.setVisibility(View.VISIBLE);
                            MnxPreferenceManager.setBoolean(MnxConstant.IS_DASHBOARD,true);
                            loadMainFragment(myordersFragment, false, R.id.frameLayout);
                            item.setIcon(R.drawable.ic_orders_active);
                            tv_header.setText("New Orders");
                            return true;
                        case R.id.navigation_ongoing:
                            iv_refresh.setVisibility(View.INVISIBLE);
                            Fragment ongoingFragment = new OngoingFragment();
                            MnxPreferenceManager.setBoolean(MnxConstant.IS_DASHBOARD,false);
                            loadMainFragment(ongoingFragment, false, R.id.frameLayout);
                            item.setIcon(R.drawable.ic_ongoing);
                            tv_header.setText("Ongoing");
                            return true;
                        case R.id.navigation_delivered:
                            //Fragment profileFragment = new DeliveredFragment();
                            iv_refresh.setVisibility(View.INVISIBLE);
                            Fragment profileFragment = new DeliveredFragment();
                            MnxPreferenceManager.setBoolean(MnxConstant.IS_DASHBOARD,false);
                            loadMainFragment(profileFragment, false, R.id.frameLayout);
                            item.setIcon(R.drawable.ic_delivered_active);
                            tv_header.setText("Delivered");
                            return true;
                    }
                    return false;
                }
            };

    public boolean loadMainFragment(Fragment fragment, boolean addToBackStack, int container_viewid) {
        //switching fragment
        if (fragment != null) {
            if (addToBackStack) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(container_viewid, fragment)
                        .addToBackStack(null)
                        .commit();

            } else {
                Log.i("FragmentTesting", "Replacing Fragment");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(container_viewid, fragment)
                        .commit();
            }
            //for this function by default we are making it visible
            return true;
        }
        return false;
    }

    private void callnextscreen(int nVal){
        try {
            Intent myintent;
            if (nVal==1)
                myintent=new Intent(this, NotificationActivity.class);
            else {
                Log.i("TESTCALL","TESTCALL");
                myintent = new Intent(this, ProfileActivity.class);
            }
            startActivity(myintent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if(MnxPreferenceManager.getBoolean(MnxConstant.IS_DASHBOARD,false)){
            super.onBackPressed();
            this.finishAffinity();
        }else{
            Intent intent = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void update_notification_count(){
        if(MnxPreferenceManager.getString(MnxConstant.NOTIFICATION_COUNT,null)!=null) {
            if (MnxPreferenceManager.getString(MnxConstant.NOTIFICATION_COUNT, null).equals("0")) {
                notification_count.setVisibility(View.GONE);
            } else {
                notification_count.setVisibility(View.VISIBLE);
                notification_count.setText(MnxPreferenceManager.getString(MnxConstant.NOTIFICATION_COUNT, null));
            }
        }else{
            notification_count.setVisibility(View.GONE);
        }
    }

    public void do_move_to_ongoing_frag(){
        Fragment ongoingFragment = new OngoingFragment();
        loadMainFragment(ongoingFragment, false, R.id.frameLayout);
        MnxPreferenceManager.setBoolean(MnxConstant.IS_DASHBOARD,false);
        bottom_navigation.getMenu().findItem(R.id.navigation_ongoing).setChecked(true);
        tv_header.setText("Ongoing");
    }



}
