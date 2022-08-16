package com.vdeliverz_delivery.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.vdeliverz_delivery.MapsActivity;
import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.home.model.Order;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    Context context;
    List<Order> orderList;
    TextView tv_shopname,tv_orderid,tv_price,tv_itemcount,tv_pay_method,pick_address,drop_address,
            tv_expected_deliverytime_val,tv_expected_deliverydate_val,tv_reject,tv_accept;
    OrderFragment orderFragment;

    public OrdersAdapter(Context context,List<Order> orders,OrderFragment order_frag){
        this.context = context;
        this.orderList = orders;
        orderFragment=order_frag;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrdersAdapter.ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.order_list_item, parent, false);
            vh = new OrdersAdapter.ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        tv_shopname.setText(orderList.get(position).getShop_name());
        tv_orderid.setText("Order id: "+orderList.get(position).getOrderReferel());
        tv_price.setText("₹ "+orderList.get(position).getOrderAmount());
        tv_itemcount.setText(orderList.get(position).getTotalItems()+" item");
        if(orderList.get(position).getPaymentType().equals("0")){
            tv_pay_method.setText("COD");
        }else{
            tv_pay_method.setText("Online Payment");
        }
        pick_address.setText(orderList.get(position).getPickUp());
        drop_address.setText(orderList.get(position).getDropOff());
        tv_expected_deliverytime_val.setText(orderList.get(position).getExpectedTime());
        tv_expected_deliverydate_val.setText(orderList.get(position).getExpectedDate());
    }

    private void callnextscreen(){
        try {
            Activity activity=(Activity)context;
            Intent myintent;
                myintent=new Intent(context, MapsActivity.class);
            context.startActivity(myintent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

            tv_shopname=itemView.findViewById(R.id.tv_shopname);
            tv_orderid=itemView.findViewById(R.id.tv_orderid);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_itemcount=itemView.findViewById(R.id.tv_itemcount);
            tv_pay_method=itemView.findViewById(R.id.tv_pay_method);
            pick_address=itemView.findViewById(R.id.pick_address);
            drop_address=itemView.findViewById(R.id.drop_address);
            tv_expected_deliverytime_val=itemView.findViewById(R.id.tv_expected_deliverytime_val);
            tv_expected_deliverydate_val=itemView.findViewById(R.id.tv_expected_deliverydate_val);
            tv_reject=itemView.findViewById(R.id.tv_reject);
            tv_accept=itemView.findViewById(R.id.tv_accept);


            tv_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String order_id=orderList.get(getAdapterPosition()).getOrderId();
                    if(order_id!=null) {
                        orderFragment.order_accept_apicall(order_id);
                    }
                }
            });

            tv_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String order_id=orderList.get(getAdapterPosition()).getOrderId();
                    if(order_id!=null) {
                        orderFragment.order_reject_apicall(order_id);
                    }
                }
            });
        }
    }
}
