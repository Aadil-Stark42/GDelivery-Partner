package com.vdeliverz_delivery.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vdeliverz_delivery.R;


public class ProfileAboutFAQ extends AppCompatActivity {

    ConstraintLayout con_faq,cons_termsandcondition,cons_privacypolicy;
    ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_about_faq);

        con_faq=findViewById(R.id.con_faq);
        cons_termsandcondition=findViewById(R.id.con_termsandcon);
        cons_privacypolicy=findViewById(R.id.con_privacypolicy);

        con_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call(1);

            }
        });

        cons_termsandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(2);
            }
        });

        cons_privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(3);
            }
        });

        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }

    private void call(int nVal){
        Intent myintent;
        if (nVal==1)
         myintent=new Intent(this, ProfileFAQ.class);
        else if(nVal==2)
            myintent=new Intent(this,TermsAndCondition.class);
        else
            myintent=new Intent(this, PrivacyPolicy.class);
        startActivity(myintent);
    }


}
