package com.uclouddemo;

import android.app.Application;

import com.ucloud.ulive.UStreamingContext;

/**
 * Created by Flynn on 17/4/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UStreamingContext.init(getApplicationContext(), "publish3-key");
    }
}
