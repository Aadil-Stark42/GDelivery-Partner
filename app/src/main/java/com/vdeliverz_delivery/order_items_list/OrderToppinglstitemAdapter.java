package com.vdeliverz_delivery.order_items_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.order_items_list.model.Topping;

import java.util.List;

public class OrderToppinglstitemAdapter extends RecyclerView.Adapter<OrderToppinglstitemAdapter.ViewHolder> {

    Context context;
    List<Topping> toppingList;

    public OrderToppinglstitemAdapter(Context context, List<Topping> toppings) {
        this.context = context;
        this.toppingList=toppings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.toppinglistitems, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if ((toppingList.get(position).getName()!=null) && (toppingList.get(position).getPrice()!=null))
            holder.tv_name.setText(toppingList.get(position).getName());
        else if(toppingList.get(position).getName()!=null)
            holder.tv_name.setText(toppingList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return toppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}