package com.vdeliverz_delivery.delivered;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vdeliverz_delivery.R;

public class DeliveredActivity extends AppCompatActivity {

    TextView tv_canceled,tv_delivered;

    //test
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delivered_list);

        tv_canceled=findViewById(R.id.tv_cancelled);
        tv_delivered=findViewById(R.id.tv_delivered);

//       RecyclerView recyclerView=findViewById(R.id.recyclerview);
//        DeliveredAdapter adapter = new DeliveredAdapter(this);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
