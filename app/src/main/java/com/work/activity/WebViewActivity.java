package com.work.activity;

import android.os.Bundle;

import com.work.R;
import com.work.utils.Logger;
import com.work.views.CustomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.custom_web_view)
    CustomWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        mWebView.setCookie();

        Logger.d("TEST:: LOGINKEY :: " + mWebView.getCookieLoginKey());
    }
}
