package com.umeng.soexample;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by ntop on 15/7/8.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}
