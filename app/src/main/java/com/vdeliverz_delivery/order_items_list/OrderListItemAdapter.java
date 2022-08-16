package com.vdeliverz_delivery.order_items_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.order_items_list.model.ProductDetail;

import java.util.List;


public class OrderListItemAdapter extends RecyclerView.Adapter<OrderListItemAdapter.ViewHolder> {

    Context context;
    List<ProductDetail> productDetailList;

    public OrderListItemAdapter(Context context,List<ProductDetail> productDetailList) {
        this.context = context;
        this.productDetailList = productDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.orders_items_list, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (productDetailList.get(position).getProductName()!=null)
            holder.tv_name.setText(productDetailList.get(position).getProductName());

        if (productDetailList.get(position).getToppings()!=null && productDetailList.get(position).getToppings().size()>0){
            OrderToppinglstitemAdapter orderListAdapter = new OrderToppinglstitemAdapter(context,productDetailList.get(position).getToppings());
            holder.rv_list.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_list.setAdapter(orderListAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        RecyclerView rv_list;
        TextView tv_qty,tv_name;

        public ViewHolder(View itemView) {

            super(itemView);
            rv_list=itemView.findViewById(R.id.rv_toppinglist);
            rv_list.setHasFixedSize(true);
            tv_qty=itemView.findViewById(R.id.tv_qty);
            tv_name=itemView.findViewById(R.id.tv_name);
            consitem=itemView.findViewById(R.id.consitem);

        }
    }
}