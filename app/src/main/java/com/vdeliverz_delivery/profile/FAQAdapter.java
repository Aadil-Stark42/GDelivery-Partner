package com.vdeliverz_delivery.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;


public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
    Context context;

    public FAQAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FAQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FAQAdapter.ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.faq_list_item, parent, false);
            vh = new FAQAdapter.ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

}
