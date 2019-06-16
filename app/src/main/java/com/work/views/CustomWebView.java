package com.work.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by hmju on 2018-11-22.
 */
public class CustomWebView extends WebView {

    private final String LOGIN_KEY = "x-test";

    private CookieManager mCookieManager;

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCookie() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            mCookieManager = CookieManager.getInstance();
            mCookieManager.setAcceptCookie(true);
            mCookieManager.setCookie(LOGIN_KEY, "TTEEF");
            mCookieManager.setAcceptThirdPartyCookies(this, true);
        }
    }

    public String getCookieLoginKey() {
        if (mCookieManager != null) {
            return mCookieManager.getCookie(LOGIN_KEY);
        } else {
            mCookieManager = CookieManager.getInstance();
            return mCookieManager.getCookie(LOGIN_KEY);
        }
    }
}
