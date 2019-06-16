package com.work;

import android.app.Application;

import com.work.utils.FrescoImageUtil;

/**
 * Application Class
 * Created by hmju on 2019-03-30.
 */
public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // init FrescoImage Loader
        FrescoImageUtil.getInstance().init(getApplicationContext());
    }
}
