package com.vdeliverz_delivery.profile;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vdeliverz_delivery.R;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

public class TermsAndCondition extends AppCompatActivity {
    WebView webView;
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_terms_and_condition);
        iv_back=findViewById(R.id.iv_back);

        webView=findViewById(R.id.webview_tc);
        webView.loadUrl(MnxPreferenceManager.getString(MnxConstant.TERMS_CONDITION,""));
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
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
