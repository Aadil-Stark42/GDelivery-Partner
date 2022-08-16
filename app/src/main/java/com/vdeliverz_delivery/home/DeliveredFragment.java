package com.vdeliverz_delivery.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.delivered.DeliveredAdapter;
import com.vdeliverz_delivery.delivered.canceledmvp.CancelledContract;
import com.vdeliverz_delivery.delivered.canceledmvp.CancelledIntract;
import com.vdeliverz_delivery.delivered.canceledmvp.CancelledPresenter;
import com.vdeliverz_delivery.delivered.deliveredmvp.DeliveredContract;
import com.vdeliverz_delivery.delivered.deliveredmvp.DeliveredIntract;
import com.vdeliverz_delivery.delivered.deliveredmvp.DeliveredPresenter;
import com.vdeliverz_delivery.delivered.model.DeliveredListResponse;
import com.vdeliverz_delivery.delivered.model.Order;
import com.vdeliverz_delivery.search_delivered.SearchDeliveredContract;
import com.vdeliverz_delivery.search_delivered.SearchDeliveredIntract;
import com.vdeliverz_delivery.search_delivered.SearchDeliveredPresenter;
import com.vdeliverz_delivery.search_delivered.model.DeliveredSearchResponse;
import com.vdeliverz_delivery.utils.BaseFragment;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;


public class DeliveredFragment  extends BaseFragment implements DeliveredContract,
        CancelledContract, SearchDeliveredContract {

    RecyclerView recyclerView;
    TextView tv_delivered,tv_cancelled,tv_bdcount;
    View view;
    RelativeLayout iv_baner,rl_nodata;
    int nCurrentTab=0;
    EditText search_et;
    String search_keyword;
    SearchDeliveredPresenter searchDeliveredPresenter;
    ConstraintLayout delivered_parent_const;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_delivered, container, false);
        tv_delivered=view.findViewById(R.id.tv_delivered);
        tv_cancelled=view.findViewById(R.id.tv_cancelled);
        iv_baner=view.findViewById(R.id.iv_baner);
        recyclerView=view.findViewById(R.id.recyclerview);
        rl_nodata=view.findViewById(R.id.rl_nodatafound);
        tv_bdcount=view.findViewById(R.id.tv_bdcount);

        search_et=view.findViewById(R.id.serach_et);
        delivered_parent_const=view.findViewById(R.id.delivered_parent_const);

        //search_et.setEnabled(false);

        calldeliveredapi();
        nCurrentTab=1;

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.segoe_ui_bold);
        tv_delivered.setTypeface(typeface);

        tv_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nCurrentTab!=1) {

                    nCurrentTab = 1;
                    calldeliveredapi();
                    tv_cancelled.setTextColor(getResources().getColor(R.color.background));
                    tv_delivered.setTextColor(getResources().getColor(R.color.green));
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.segoe_ui_bold);
                    tv_delivered.setTypeface(typeface);
                    Typeface typeface2 = ResourcesCompat.getFont(getContext(), R.font.seoge);
                    tv_cancelled.setTypeface(typeface2);
                }
            }
        });

        tv_cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (nCurrentTab!=2) {

                    callcancelledorderapi();
                    nCurrentTab = 2;
                    tv_cancelled.setTextColor(getResources().getColor(R.color.green));
                    tv_delivered.setTextColor(getResources().getColor(R.color.background));
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.segoe_ui_bold);
                    tv_cancelled.setTypeface(typeface);
                    Typeface typeface2 = ResourcesCompat.getFont(getContext(), R.font.seoge);
                    tv_delivered.setTypeface(typeface2);
                }

            }
        });


        search_et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search_keyword=search_et.getText().toString().trim();
                    do_search_apicall();
                    return true;
                }
                return false;
            }
        });


        return view;
    }



    private void do_search_apicall() {
        if(MnxPreferenceManager.getString(MnxConstant.TOKEN,null)!=null) {
            delivered_parent_const.setVisibility(View.INVISIBLE);
            showLoading();
            searchDeliveredPresenter = new SearchDeliveredPresenter(DeliveredFragment.this, new SearchDeliveredIntract());
            searchDeliveredPresenter.validateDetails(search_keyword);
        }else {
            Toast.makeText(getContext(), "Token Issue", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method used to load  list
     * @param order
     */
    private  void loadlist(List<Order> order){
        try {
            DeliveredAdapter adapter = new DeliveredAdapter(getActivity(),order);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method used to get delivered list
     */
    public  void calldeliveredapi(){
        try {
            delivered_parent_const.setVisibility(View.INVISIBLE);
            showLoading();
            DeliveredPresenter deliveredPresenter = new DeliveredPresenter(this, new DeliveredIntract());
            deliveredPresenter.validateDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this methos used to get  delivered list api response
     * @param deliveredListResponse
     */

    @Override
    public void delivered_success(DeliveredListResponse deliveredListResponse) {
        try {
            delivered_parent_const.setVisibility(View.VISIBLE);
            hideLoading();
            if (deliveredListResponse!=null){
                if (deliveredListResponse.getStatus()){
                    if (deliveredListResponse.getOrders()!=null){

                        if (deliveredListResponse.getOrders().size()>0){
                            recyclerView.setVisibility(View.VISIBLE);
                            if (deliveredListResponse.isDelivery_boy_type()) {
                                 tv_bdcount.setText(deliveredListResponse.getPoints()+" coins");
                            }
                            else


                            recyclerView.setVisibility(View.VISIBLE);
                            rl_nodata.setVisibility(View.GONE);
                            loadlist(deliveredListResponse.getOrders());
                        }
                        else {
                            recyclerView.setVisibility(View.GONE);
                            rl_nodata.setVisibility(View.VISIBLE);
                         }
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
                }
            }
            else {
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delivered_failure(String msg) {
        delivered_parent_const.setVisibility(View.VISIBLE);
        hideLoading();
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

    /**
     * This method used to call cancelled order list
     */

    private  void callcancelledorderapi(){
        try {
            delivered_parent_const.setVisibility(View.INVISIBLE);
            showLoading();
            CancelledPresenter cancelledPresenter = new CancelledPresenter(this, new CancelledIntract());
            cancelledPresenter.validateDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cancelled order list response
     * @param deliveredListResponse
     */

    @Override
    public void cancelled_success(DeliveredListResponse deliveredListResponse) {
        try {
            delivered_parent_const.setVisibility(View.VISIBLE);
            hideLoading();
            if (deliveredListResponse!=null){
                if (deliveredListResponse.getStatus()){
                    if (deliveredListResponse.getOrders()!=null){
                        if (deliveredListResponse.getOrders().size()>0){
                            recyclerView.setVisibility(View.VISIBLE);
                            rl_nodata.setVisibility(View.GONE);
                            loadlist(deliveredListResponse.getOrders());
                        }
                        else {
                            recyclerView.setVisibility(View.GONE);
                            rl_nodata.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
                }
            }
            else {
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void cancelled_failure(String msg) {
        delivered_parent_const.setVisibility(View.VISIBLE);
        hideLoading();
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
            hideLoading();
            do_logout_and_login_redirect();
    }

    @Override
    public void search_delivered_success(DeliveredSearchResponse searchResultResponse) {
        try {
            delivered_parent_const.setVisibility(View.VISIBLE);
            hideLoading();
            if (searchResultResponse!=null){
                if (searchResultResponse.getStatus()){
                    if (searchResultResponse.getOrders()!=null){

                        if (searchResultResponse.getOrders().size()>0){
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.removeAllViewsInLayout();
                            rl_nodata.setVisibility(View.GONE);
                            loadlist(searchResultResponse.getOrders());
                        }
                        else {
                            recyclerView.setVisibility(View.GONE);
                            rl_nodata.setVisibility(View.VISIBLE);

                        }
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
                }
            }
            else {
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Response Data is empty", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search_delivered_failure(String msg) {
        delivered_parent_const.setVisibility(View.VISIBLE);
        hideLoading();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
