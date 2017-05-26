package com.android.ascode.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by yanfeng on 2017/5/22.
 */

public class ResourceUtil {
    public static String getContent(Context c, int rid) {
        return c.getResources().getString(rid);
    }

    public static int getColor(Context c, int rid) {
        return c.getResources().getColor(rid);
    }

    public static float getDimension(Context c, int rid) {
        return c.getResources().getDimension(rid);
    }

    public static Drawable getDrawable(Context c, int rid) {
        return c.getResources().getDrawable(rid);
    }

    public static String getString(Context c, int rid) {
        return c.getResources().getString(rid);
    }

}
