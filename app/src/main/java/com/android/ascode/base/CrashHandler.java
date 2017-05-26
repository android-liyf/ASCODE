package com.android.ascode.base;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by yanfeng on 2017/5/23.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler instance;
    //程序的Context对象
    private Context mContext;
    private CrashHandler(Context context) {
        init(context);
    }
    /**
     * 获取CrashHandler实例
     */
    public static synchronized CrashHandler getInstance(Context context) {
        if (instance == null){
            instance = new CrashHandler(context) ;
        }
        return instance;
    }
    /**
     * 初始化
     */
    private void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread,ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
//                Log.e(TAG, "error : ", e);
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause() ;
        while (cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause() ;
        }
        printWriter.close();
        String result = writer.toString() ;
        Log.i("----cxkt----","Crash："+result) ;
        return true;
    }
}
