package com.vdeliverz_delivery.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.ongoing_orders.model.Order;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;


public class OngoingListAdapter extends RecyclerView.Adapter<OngoingListAdapter.ViewHolder> {

    Context context;
    View Listitem;
    Order order_obj;
    ImageView iv_call_shop;
    CheckBox checkbox1,checkbox2;
    TextView tv_shopname,tv_orderid,tv_price,tv_itemcount,tv_pay_method,pick_address,drop_address,
            tv_expected_deliverytime_val,tv_expected_deliverydate_val,tv_cancel,tv_delivered;

    int hoursToGo = 0;
    int minutesToGo = 0;
    int secondsToGo = 0;
    String TAG=OngoingListAdapter.class.getSimpleName();
    CountDownTimer countDownTimer;
    OngoingFragment ongoingFragment;
    String customer_phone;

    TextView tv_name,tv_view_details;


    public OngoingListAdapter(Context context,Order order, OngoingFragment ongoing_fragment) {
        this.context = context;
        this.order_obj = order;
        ongoingFragment = ongoing_fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
                Listitem = lf.inflate(R.layout.ongoing_listitem, parent, false);
            vh = new ViewHolder(Listitem);
            return vh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       if(order_obj!=null){
           tv_shopname.setText(order_obj.getShop_name());
           Log.d(TAG, "onBindViewHolder: start");
           tv_orderid.setText("Order id: "+order_obj.getOrderReferel());
           tv_name.setText(order_obj.getCus_name());
           tv_price.setText("₹ "+order_obj.getOrderAmount());
           tv_itemcount.setText(order_obj.getTotalItems()+" item");
           customer_phone=order_obj.getCustomer_mobile();
           if(order_obj.getPaymentType().equals("0")){
               tv_pay_method.setText("COD");
           }else{
               tv_pay_method.setText("Online Payment");
           }
           pick_address.setText(order_obj.getPickUp());
           drop_address.setText(order_obj.getDropOff());
           tv_expected_deliverytime_val.setText(order_obj.getExpectedTime());
           tv_expected_deliverydate_val.setText(order_obj.getExpectedDate());

           if(order_obj.getDeliveryStatus().equals("1")){
               tv_delivered.setText("Item Pickup");
               checkbox1.setChecked(false);
           }else if(order_obj.getDeliveryStatus().equals("2")){
               tv_delivered.setText("Delivered");
               checkbox1.setChecked(true);
           }

           if(order_obj.getCanCancel()){
               secondsToGo= Integer.parseInt(order_obj.getCancelTimeSec());
               minutesToGo= Integer.parseInt(order_obj.getCancelTimeMin());

               int millisToGo = secondsToGo*1000+minutesToGo*1000*60+hoursToGo*1000*60*60;

               countDownTimer =new CountDownTimer(millisToGo, 1000) {

                   @SuppressLint("SetTextI18n")
                   public void onTick(long millisUntilFinished) {

                       int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                       int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                       int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                       String text = String.format(" %02d : %02d ",minutes,seconds);

                       tv_cancel.setText("Cancel in "+""+text+"sec");
                   }

                   public void onFinish(){
                       /*tv_cancel.setText("Cancel");
                       tv_cancel.setTextColor(Color.parseColor("#808080"));
                       tv_cancel.setBackgroundResource(R.drawable.cancel_after_bg);*/
                       tv_cancel.setText("Contact Customer");
                       tv_cancel.setTextColor(Color.parseColor("#808080"));
                       tv_cancel.setBackgroundResource(R.drawable.cancel_after_bg);
                   }
               }.start();
           }else{
               tv_cancel.setText("Contact Customer");
               tv_cancel.setTextColor(Color.parseColor("#808080"));
               tv_cancel.setBackgroundResource(R.drawable.cancel_after_bg);
               /*tv_cancel.setText("Cancel");
               tv_cancel.setTextColor(Color.parseColor("#808080"));
               tv_cancel.setBackgroundResource(R.drawable.cancel_after_bg);*/
           }
       }

        pick_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(order_obj.getShop_lat()!=null && order_obj.getShop_lon()!=null) {

                    String lat = order_obj.getShop_lat();
                    String lang = order_obj.getShop_lon();
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    //String final_uri = "google.streetview:cbll=" + lat + "," + lang;
                    String final_uri = "google.navigation:q=" + lat + "," + lang;
                    Uri gmmIntentUri = Uri.parse(final_uri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);

                }else{
                    Toast.makeText(context, "Loading map issue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order_obj.getOrderId()!=null) {
                    ongoingFragment.do_order_details_api(order_obj.getOrderReferel(),order_obj.getOrderId());
                }
            }
        });

        iv_call_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=order_obj.getShop_mobile();
                if(number!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    context.startActivity(intent);
                }
            }
        });

        drop_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(order_obj.getCus_lat()!=null && order_obj.getCus_lon()!=null) {

                    String lat = order_obj.getCus_lat();
                    String lang = order_obj.getCus_lon();
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    //String final_uri = "google.streetview:cbll=" + lat + "," + lang;
                    String final_uri = "google.navigation:q=" + lat + "," + lang;
                    Uri gmmIntentUri = Uri.parse(final_uri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);

                }else{
                    Toast.makeText(context, "Loading map issue", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);


            checkbox1=itemView.findViewById(R.id.checkbox1);
            checkbox2=itemView.findViewById(R.id.checkbox2);

            checkbox1.setEnabled(false);
            checkbox2.setEnabled(false);

            tv_shopname=itemView.findViewById(R.id.tv_shopname);
            iv_call_shop=itemView.findViewById(R.id.iv_call_shop);
            tv_orderid=itemView.findViewById(R.id.tv_orderid);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_itemcount=itemView.findViewById(R.id.tv_itemcount);
            tv_pay_method=itemView.findViewById(R.id.tv_pay_method);
            pick_address=itemView.findViewById(R.id.pick_address);
            drop_address=itemView.findViewById(R.id.drop_address);
            tv_expected_deliverytime_val=itemView.findViewById(R.id.tv_expected_deliverytime_val);
            tv_expected_deliverydate_val=itemView.findViewById(R.id.tv_expected_deliverydate_val);
            tv_cancel=itemView.findViewById(R.id.tv_cancel);
            tv_delivered=itemView.findViewById(R.id.tv_delivered);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_view_details=itemView.findViewById(R.id.tv_view_details);

            String number=order_obj.getShop_mobile();
            if(number!=null){
                iv_call_shop.setVisibility(View.VISIBLE);
            }else{
                iv_call_shop.setVisibility(View.INVISIBLE);
            }


            tv_delivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String order_id=order_obj.getOrderId();
                    String cash=order_obj.getOrderAmount();
                    if(order_id!=null) {
                        MnxPreferenceManager.setString(MnxConstant.LATEST_ORDER_ID,order_id);
                        MnxPreferenceManager.setString(MnxConstant.CASH_AMOUNT,cash);
                        if(order_obj.getDeliveryStatus().equals("1")) {
                            ongoingFragment.order_pick_apicall(order_id);
                        }else if(order_obj.getDeliveryStatus().equals("2")){
                            if(order_obj.getPaymentType().equals("0")){
                                ongoingFragment.collect_cash_activity();
                            }else if(order_obj.getPaymentType().equals("1")){
                                ongoingFragment.order_delivered_apicall(order_id);
                            }
                        }
                    }else{
                        Toast.makeText(context, "Order ID Issue", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String tv_string = tv_cancel.getText().toString();
                    if (tv_string.equals("Contact Customer")) {
                        if(customer_phone!=null) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+customer_phone));
                            context.startActivity(intent);
                        }
                    }else{
                        String order_id = order_obj.getOrderId();
                        if (order_id != null) {
                            if (order_obj.getCanCancel()) {
                                ongoingFragment.order_cancel_apicall(order_id);
                            }
                        } else {
                            Toast.makeText(context, "Order ID Issue", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }
}

