package com.vdeliverz_delivery.delivery_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.delivery_details.model.Product;


public class DeliverydetailAdapter extends RecyclerView.Adapter<DeliverydetailAdapter.PlacedValues> {

    Context context;
    List<Product> productList;
    TextView itemName_tv_order,item_qty_tv,cost_tv;
    ImageView item_type_iv;

    //varity......1==veg......0==Non veg

    public DeliverydetailAdapter(Context context, List<Product> items){
        this.context = context;
        this.productList = items;
    }

    @NonNull
    @Override
    public DeliverydetailAdapter.PlacedValues onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.delivery_detail_list, parent, false);
        return new DeliverydetailAdapter.PlacedValues(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliverydetailAdapter.PlacedValues holder, int position) {
        holder.setIsRecyclable(false);
        itemName_tv_order.setText(productList.get(position).getProductName());
        item_qty_tv.setText(productList.get(position).getQuantity());
        cost_tv.setText("₹ "+productList.get(position).getAmount());
        if(productList.get(position).getVariety().equals("1")){
            item_type_iv.setBackgroundResource(R.drawable.veg);
        }else{
            item_type_iv.setBackgroundResource(R.drawable.non_veg);
        }
    }

    @Override
    public int getItemCount(){
        return productList.size();
    }

    public class PlacedValues extends RecyclerView.ViewHolder{
        public PlacedValues(@NonNull View itemView) {
            super(itemView);
            itemName_tv_order=itemView.findViewById(R.id.itemName_tv_order);
            item_qty_tv=itemView.findViewById(R.id.item_qty_tv);
            cost_tv=itemView.findViewById(R.id.cost_tv);
            item_type_iv=itemView.findViewById(R.id.item_type_iv);
        }
    }
}
