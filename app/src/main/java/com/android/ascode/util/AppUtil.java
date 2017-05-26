package com.android.ascode.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * Created by yanfeng on 2017/5/22.
 */

public class AppUtil {
    public static boolean hasSDCard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    // 获取当前版本号码和名称
    public static PackageInfo getCurrentVersion(Context context) {

        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
    }

    /**
     * 获取app的存储目录
     * <p/>
     * 一般情况下是这样的/storage/emulated/0/Android/data/包名/
     *
     * @param context
     * @return
     */
    public static String getAppDir(Context context) {
        return (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) ? (Environment
                .getExternalStorageDirectory().getPath() + "/Android/data/")
                : (context.getCacheDir().getPath()))
                + context.getPackageName();
    }
}
