package com.android.ascode.base;

import android.app.Application;
import android.os.Handler;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import static com.android.ascode.base.AppConfig.EXTERNAL_RELEASE;
import static com.android.ascode.base.AppConfig.YOUR_TAG;


/**
 * Created by yanfeng on 2017/5/22.
 */

public class AppContext extends Application {
    private static AppContext instance;
    public static Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger
                .init(YOUR_TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(EXTERNAL_RELEASE ? LogLevel.FULL : LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2).hideThreadInfo();               // default 0
        CrashHandler.getInstance(this);
    }

    public static AppContext context() {
        return instance;
    }

}
