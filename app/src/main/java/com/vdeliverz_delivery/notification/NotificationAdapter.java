package com.vdeliverz_delivery.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.notification.model.Datum;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context c;
    List<Datum> data;

    public NotificationAdapter(Context c,List<Datum> datas) {
        this.data=datas;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.notification_list_item, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tv_content.setText(data.get(position).getNotifyHead());
            holder.tv_desc.setText(data.get(position).getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_desc,tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_desc=itemView.findViewById(R.id.tv_desc);

        }
    }
}
