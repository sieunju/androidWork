package com.work.activity;

import android.os.Bundle;

import com.work.R;
import com.work.utils.Logger;
import com.work.views.CustomWebView;

public class WebViewActivity extends BaseActivity {

    CustomWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = findViewById(R.id.custom_web_view);
        mWebView.setCookie();

        Logger.d("TEST:: LOGINKEY :: " + mWebView.getCookieLoginKey());
    }
}
