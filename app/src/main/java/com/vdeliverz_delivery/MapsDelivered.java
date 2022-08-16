package com.vdeliverz_delivery;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import com.vdeliverz_delivery.cash_collected.CashCollectedActivity;
import com.vdeliverz_delivery.route_map.DrawMarker;
import com.vdeliverz_delivery.route_map.DrawRouteMaps;

public class MapsDelivered  extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior bottomSheetBehavior;
    ConstraintLayout cons_delivered;

    ImageView cons_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_delivered);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        cons_back=findViewById(R.id.cons_back);

        cons_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cons_delivered=bottomSheet.findViewById(R.id.cons_delivered);

        cons_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });

    }

    private void callnextscreen(){
        try {
            Intent myintent=new Intent(MapsDelivered.this, CashCollectedActivity.class);
            startActivity(myintent);
//            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng origin = new LatLng(-7.788969, 110.338382);
        LatLng destination = new LatLng(-7.781200, 110.349709);
        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mMap);
        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.ic_start, "Origin Location");
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.ic_stop, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
    }
}
