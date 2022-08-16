package com.vdeliverz_delivery.cash_collected;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.collect_cash.CashContract;
import com.vdeliverz_delivery.collect_cash.CashIntract;
import com.vdeliverz_delivery.collect_cash.CashPresenter;
import com.vdeliverz_delivery.delivery_details.DeliveryDetailsActivity;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;


public class CashCollectedActivity extends BaseActivity implements CashContract {

    TextView tv_cashcollected,cash_amount;
    CashPresenter cashPresenter;
    ImageView iv_back;
    String cash_note;
    EditText add_note_ed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cash_collected);
        iv_back=findViewById(R.id.iv_back);
        add_note_ed=findViewById(R.id.add_note_ed);
        cash_amount=findViewById(R.id.cash_amount);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(MnxPreferenceManager.getString(MnxConstant.CASH_AMOUNT,null)!=null){
            String amount=MnxPreferenceManager.getString(MnxConstant.CASH_AMOUNT,null);
            cash_amount.setText("₹"+amount);
        }


        tv_cashcollected=findViewById(R.id.tv_cashcollct);
        tv_cashcollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callnextscreen();
                cash_note=add_note_ed.getText().toString().trim();
                if(MnxPreferenceManager.getString(MnxConstant.LATEST_ORDER_ID,null)!=null){
                    String order_id=MnxPreferenceManager.getString(MnxConstant.LATEST_ORDER_ID,null);
                    if(order_id!=null){
                        do_apicall(order_id,cash_note);
                    }
                }
            }
        });

    }

    private void callnextscreen(){
        try {
            Intent myintent=new Intent(CashCollectedActivity.this, DeliveryDetailsActivity.class);
            startActivity(myintent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void do_apicall(String order_id,String cash_note){
        showLoading();
        cashPresenter=new CashPresenter(CashCollectedActivity.this,new CashIntract());
        cashPresenter.validateDetails(order_id,cash_note);
    }

    @Override
    public void cash_success(GeneralResponse generalResponse) {
        hideLoading();
        if(generalResponse!=null){
            if(generalResponse.getStatus()){
                Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                Intent myintent=new Intent(CashCollectedActivity.this,
                        DeliveryDetailsActivity.class);
                startActivity(myintent);
                finish();
            }else{
                Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void cash_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
