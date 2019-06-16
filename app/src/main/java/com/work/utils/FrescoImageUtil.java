package com.work.utils;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Fresco Image Loader Util Class
 * Created by hmju on 2018-11-17.
 */
public class FrescoImageUtil {

    private static FrescoImageUtil instance = new FrescoImageUtil();

    Fresco fresco = null;

    public static FrescoImageUtil getInstance(){
        return instance;
    }

    /**
     * 앱을 사용하기 전에 무조건 실행해야 하는 함수.
     * @param context
     */
    public void init(Context context){
        Fresco.initialize(context);
    }
}
