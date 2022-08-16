package com.vdeliverz_delivery.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vdeliverz_delivery.R;


public class ProfileFAQ  extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_aboutfaq);

        recyclerView=findViewById(R.id.recyclerview);
        /*FAQAdapter adapter = new FAQAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/

        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });
    }

    private  void callnextscreen(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

}
