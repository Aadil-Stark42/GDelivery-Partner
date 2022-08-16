package com.vdeliverz_delivery.delivery_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.delivery_details.model.DeliveryDetailResponse;
import com.vdeliverz_delivery.home.HomeScreenActivity;
import com.vdeliverz_delivery.utils.BaseActivity;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

public class DeliveryDetailsActivity extends BaseActivity implements DeliveryDetailsContract {

    ImageView iv_back;
    DeliveryDetailsPesenter deliveryDetailsPesenter;
    DeliverydetailAdapter deliverydetailAdapter;
    RecyclerView item_rv;
    LinearLayoutManager linearLayoutManager;
    TextView tv_earned,tv_order_reff;
    TextView subtotal_value,coupon_value_tv,delivery_value_tv,grand_total_tv,tax_value_tv,grand_total_value_tv;
    TextView tv_order_numText,tv_shopname,tv_shopaddress,tv_drop_address_1,tv_cod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deliverydetail_new);

        iv_back=findViewById(R.id.iv_back);
        item_rv=findViewById(R.id.item_rv);
        tv_earned=findViewById(R.id.tv_earned);
        tv_order_reff=findViewById(R.id.tv_ordernum);
        subtotal_value=findViewById(R.id.subtotal_value);
        coupon_value_tv=findViewById(R.id.coupon_value_tv);
        grand_total_value_tv=findViewById(R.id.grand_total_value_tv);
        delivery_value_tv=findViewById(R.id.delivery_value_tv);
        tax_value_tv=findViewById(R.id.tax_value_tv);
        grand_total_tv=findViewById(R.id.grand_total_tv);
        tv_order_numText=findViewById(R.id.tv_order_numText);
        tv_shopname=findViewById(R.id.tv_shopname);
        tv_shopaddress=findViewById(R.id.tv_shopaddress);
        tv_drop_address_1=findViewById(R.id.tv_drop_address_1);
        tv_cod=findViewById(R.id.tv_cod);
        item_rv.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(DeliveryDetailsActivity.this,LinearLayoutManager.VERTICAL,false);
        item_rv.setLayoutManager(linearLayoutManager);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });

        if(MnxPreferenceManager.getString(MnxConstant.LATEST_ORDER_ID,null)!=null){
            String order_id=MnxPreferenceManager.getString(MnxConstant.LATEST_ORDER_ID,null);
            if(order_id!=null){
                do_apicall(order_id);
            }
        }

    }

    private  void callnextscreen(){
        Intent myintent=new Intent(DeliveryDetailsActivity.this, HomeScreenActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    public void do_apicall(String order_id){
        showLoading();
        deliveryDetailsPesenter=new DeliveryDetailsPesenter(DeliveryDetailsActivity.this,new DeliveryDetailsIntract());
        deliveryDetailsPesenter.validateDetails(order_id);
    }

    @Override
    public void deliveryDetail_success(DeliveryDetailResponse deliveryDetailResponse) {
        hideLoading();
        if(deliveryDetailResponse!=null){
            if(deliveryDetailResponse.getStatus()){
                if(deliveryDetailResponse.getOrderDetails()!=null){
                    if(deliveryDetailResponse.getOrderDetails().getProducts()!=null){
                        if(deliveryDetailResponse.getOrderDetails().getProducts().size()>0){
                            deliverydetailAdapter=new DeliverydetailAdapter(DeliveryDetailsActivity.this,
                                            deliveryDetailResponse.getOrderDetails().getProducts());
                            item_rv.setAdapter(deliverydetailAdapter);
                        }
                    }
                    subtotal_value.setText("₹ "+deliveryDetailResponse.getOrderDetails().getSubTotal());
                    tax_value_tv.setText("₹ "+deliveryDetailResponse.getOrderDetails().getTax());
                    delivery_value_tv.setText("₹ "+deliveryDetailResponse.getOrderDetails().getDeliveryCharge());
                    coupon_value_tv.setText("₹ "+deliveryDetailResponse.getOrderDetails().getCoupon());
                    grand_total_value_tv.setText("₹ "+deliveryDetailResponse.getOrderDetails().getTotalAmount());
                    tv_order_numText.setText(deliveryDetailResponse.getOrderDetails().getOrderReferel());
                    tv_order_reff.setText(deliveryDetailResponse.getOrderDetails().getOrderReferel());
                    tv_shopname.setText(deliveryDetailResponse.getOrderDetails().getShopName());
                    tv_shopaddress.setText(deliveryDetailResponse.getOrderDetails().getPickUp());
                    tv_drop_address_1.setText(deliveryDetailResponse.getOrderDetails().getDropOff());
                    if(deliveryDetailResponse.getOrderDetails().getPaymentType().equals("0")){
                        tv_cod.setText("COD");
                    }else if(deliveryDetailResponse.getOrderDetails().getPaymentType().equals("1")){
                        tv_cod.setText("Online Payment");
                    }
                    tv_earned.setText("Earned "+ "₹ "+deliveryDetailResponse.getOrderDetails().getEarned());
                }
            }else{
                Toast.makeText(this, deliveryDetailResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void deliveryDetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
