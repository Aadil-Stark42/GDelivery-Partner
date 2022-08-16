package com.vdeliverz_delivery.delivered;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.delivered.model.Order;


public class DeliveredAdapter  extends RecyclerView.Adapter<DeliveredAdapter.ViewHolder> {
    Context c;
    View Listitem;
    List<Order> orders;


    public DeliveredAdapter(Context c,List<Order> orders) {
        this.c = c;
        this.orders=orders;

    }

    @NonNull
    @Override
    public DeliveredAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveredAdapter.ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());

            Listitem = lf.inflate(R.layout.ongoing_orderitem, parent, false);

            vh = new DeliveredAdapter.ViewHolder(Listitem);
            return vh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredAdapter.ViewHolder holder, int position) {
        try {
            holder.tv_ordernum.setText(orders.get(position).getOrderReferel());
            holder.tv_datetime.setText(orders.get(position).getTime()+" "+orders.get(position).getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {

        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ordernum,tv_datetime;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_ordernum=itemView.findViewById(R.id.tv_orderid_num);
            tv_datetime=itemView.findViewById(R.id.tv_dateandtime);

        }
    }
}