package com.vdeliverz_delivery.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.cash_collected.CashCollectedActivity;
import com.vdeliverz_delivery.delivery_details.DeliveryDetailsActivity;
import com.vdeliverz_delivery.mark_as_canceled.CancelContract;
import com.vdeliverz_delivery.mark_as_canceled.CancelIntract;
import com.vdeliverz_delivery.mark_as_canceled.CancelPresenter;
import com.vdeliverz_delivery.mark_as_delivered.DeliveredContract;
import com.vdeliverz_delivery.mark_as_delivered.DeliveredIntract;
import com.vdeliverz_delivery.mark_as_delivered.DeliveredPresenter;
import com.vdeliverz_delivery.mark_as_picked.PickedContract;
import com.vdeliverz_delivery.mark_as_picked.PickedIntract;
import com.vdeliverz_delivery.mark_as_picked.PickedPresenter;
import com.vdeliverz_delivery.ongoing_orders.OngoingContract;
import com.vdeliverz_delivery.ongoing_orders.OngoingIntract;
import com.vdeliverz_delivery.ongoing_orders.OngoingPresenter;
import com.vdeliverz_delivery.ongoing_orders.model.OngoingResponse;
import com.vdeliverz_delivery.order_items_list.OrderListItemAdapter;
import com.vdeliverz_delivery.order_items_list.model.OrderDetailsResponse;
import com.vdeliverz_delivery.order_items_list.model.ProductDetail;
import com.vdeliverz_delivery.order_items_list.mvp.OrderDetailsContract;
import com.vdeliverz_delivery.order_items_list.mvp.OrderDetailsIntract;
import com.vdeliverz_delivery.order_items_list.mvp.OrderDetailsPresenter;
import com.vdeliverz_delivery.utils.BaseFragment;
import com.vdeliverz_delivery.utils.GeneralResponse;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import java.util.List;


public class OngoingFragment extends BaseFragment implements OngoingContract,
        CancelContract,PickedContract, DeliveredContract, OrderDetailsContract {

    RecyclerView recyclerView;
    OngoingPresenter ongoingPresenter;
    OngoingListAdapter ongoingListAdapter;
    String TAG=OngoingFragment.class.getSimpleName();
    ConstraintLayout no_data_constraint;
    CancelPresenter cancelPresenter;
    PickedPresenter pickedPresenter;
    DeliveredPresenter deliveredPresenter;
    EditText search_et;

    OrderDetailsPresenter orderDetailsPresenter;
    String order_reff;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acivity_ongoing, container, false);

        recyclerView=view.findViewById(R.id.recyclerview);
        no_data_constraint=view.findViewById(R.id.no_data_constraint);
        search_et=view.findViewById(R.id.serach_et);

        search_et.setEnabled(false);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        do_apicall();
        return view;
    }

    public void do_apicall() {
        if(MnxPreferenceManager.getString(MnxConstant.TOKEN,null)!=null) {
            showLoading();
            ongoingPresenter = new OngoingPresenter(OngoingFragment.this,new OngoingIntract());
            ongoingPresenter.validateDetails();
        }else{
            Toast.makeText(getContext(), "User Not Logged-In", Toast.LENGTH_SHORT).show();
        }
    }

    public void order_cancel_apicall(String order_id) {
        if (MnxPreferenceManager.getString(MnxConstant.TOKEN, null) != null) {
            showLoading();
            cancelPresenter = new CancelPresenter(OngoingFragment.this, new CancelIntract());
            cancelPresenter.validateDetails(order_id);
        }else{
            Toast.makeText(getContext(), "User Not Logged-In", Toast.LENGTH_SHORT).show();
        }
    }

    public void order_pick_apicall(String order_id) {
        if (MnxPreferenceManager.getString(MnxConstant.TOKEN, null) != null) {
            showLoading();
            pickedPresenter = new PickedPresenter(OngoingFragment.this, new PickedIntract());
            pickedPresenter.validateDetails(order_id);
        }else{
            Toast.makeText(getContext(), "User Not Logged-In", Toast.LENGTH_SHORT).show();
        }
    }

    public void order_delivered_apicall(String order_id) {
        if (MnxPreferenceManager.getString(MnxConstant.TOKEN, null) != null) {
            showLoading();
            deliveredPresenter = new DeliveredPresenter(OngoingFragment.this, new DeliveredIntract());
            deliveredPresenter.validateDetails(order_id);
        }else{
            Toast.makeText(getContext(), "User Not Logged-In", Toast.LENGTH_SHORT).show();
        }
    }

    public void collect_cash_activity(){
        Intent intent= new Intent(getContext(), CashCollectedActivity.class);
        startActivity(intent);
    }

    @Override
    public void ongoing_success(OngoingResponse ongoingResponse) {
        hideLoading();
        if(ongoingResponse!=null){
            if(ongoingResponse.getStatus()){
                if(ongoingResponse.getOrder()!=null){
                    no_data_constraint.setVisibility(View.GONE);
                    Log.d(TAG, "ongoing_success: API call ongoing");
                    ongoingListAdapter= new OngoingListAdapter(getContext(),ongoingResponse.getOrder(), OngoingFragment.this);
                    recyclerView.setAdapter(ongoingListAdapter);
                }else{
                    no_data_constraint.setVisibility(View.VISIBLE);
                }
            }else{
                Toast.makeText(getContext(), ongoingResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void ongoing_failure(String msg){
        hideLoading();
        no_data_constraint.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancel_success(GeneralResponse generalResponse) {
        hideLoading();
        if(generalResponse!=null){
            if(generalResponse.getStatus()){
                Toast.makeText(getContext(), generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                do_apicall();
            }else{
                Toast.makeText(getContext(), generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void cancel_failure(String msg) {
        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void delivered_success(GeneralResponse generalResponse) {
        hideLoading();
        if(generalResponse!=null){
            if(generalResponse.getStatus()){
                Toast.makeText(getContext(), generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                do_apicall();
                call_delivery_detail_activity();
            }else{
                Toast.makeText(getContext(), generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void delivered_failure(String msg) {
        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void picked_success(GeneralResponse generalResponse) {
        hideLoading();
        if(generalResponse!=null){
            if(generalResponse.getStatus()){
                Toast.makeText(getContext(), generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                do_apicall();
            }else{
                Toast.makeText(getContext(), generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void picked_failure(String msg) {
        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    public void call_delivery_detail_activity(){
        Intent intent= new Intent(getContext(), DeliveryDetailsActivity.class);
        startActivity(intent);
    }

    public void do_order_details_api(String order_reff,String order_id){
        this.order_reff=order_reff;
        showLoading();
        orderDetailsPresenter=new OrderDetailsPresenter(this,
                new OrderDetailsIntract());
        orderDetailsPresenter.validateDetails(order_id);
    }


    @Override
    public void order_details_success(OrderDetailsResponse orderDetailsResponse) {
        hideLoading();
        if(orderDetailsResponse!=null){
            if(orderDetailsResponse.getStatus()){
                if(orderDetailsResponse.getOrderDetails()!=null){
                    if(orderDetailsResponse.getOrderDetails().size()>0){
                        if(orderDetailsResponse.getOrderDetails().get(0).getProductDetails()!=null){
                            if(orderDetailsResponse.getOrderDetails().get(0).getProductDetails().size()>0){
                                order_details_popup(order_reff,orderDetailsResponse.getOrderDetails().get(0).getProductDetails());
                            }else{
                                Toast.makeText(getContext(), "Details not found", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Details not found", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Details not found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Details not found", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), orderDetailsResponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void order_details_failure(String msg) {
        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    public void order_details_popup(String order_reff,List<ProductDetail> getProductDetails){

        RecyclerView rv_order_items;
        TextView tv_referalnum;

        View alertCustomdialog = LayoutInflater.from(getContext()).inflate(R.layout.order_detail_popup,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        tv_referalnum=alertCustomdialog.findViewById(R.id.tv_referalnum);
        tv_referalnum.setText(order_reff);

        rv_order_items=alertCustomdialog.findViewById(R.id.rv_list);
        rv_order_items.setHasFixedSize(true);
        rv_order_items.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderListItemAdapter orderListItemAdapter = new OrderListItemAdapter(getContext(),getProductDetails);
        rv_order_items.setAdapter(orderListItemAdapter);

        alert.setView(alertCustomdialog);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}
