package com.android.ascode.util;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by yanfeng on 2017/5/23.
 */

public class DisplayUtil {
    // 状态栏高度
    private static int mStatusHeight = -1;
    // 屏幕像素点
    private static final Point screenSize = new Point();
    public static float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    public static int getDensityWdith(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getDensityHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    // 1.代码中设置setXXSize的都是px单位，都需要把布局中的dp，sp转成px才能使用

    /**
     * 根据手机分辨率从 px(像素) 单位 转成 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率从 dp 单位 转成 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getWidth(Context context, int liangbianjuli, int num,
                               int picjuli) {
        // 屏幕宽度-两边的距离-图片中间的距离 除以列数
        return (getDensityWdith(context)
                - DisplayUtil.dip2px(context, liangbianjuli) * 2 - dip2px(
                context, picjuli) * (num - 1))
                / num;
    }

    public static int getWidth(Context context, int num,
                               int jian) {
        // 屏幕宽度-两边的距离-图片中间的距离 除以列数
        return (getDensityWdith(context) - DisplayUtil.dip2px(context, jian))
                / num * 2;
    }

    public static int getWidth(Context context, int percent) {
        // 获取屏幕的高度
        return Integer.parseInt((getDensityWdith(context) * percent) / 100 + "");
    }

    public static int getHeight(Context context, int fenmu) {
        // 获取屏幕的高度
        return (getDensityHeight(context) / fenmu);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return mStatusHeight
     */
    public static int getStatusHeight(Context context) {
        if (mStatusHeight != -1) {
            return mStatusHeight;
        }
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mStatusHeight;
    }
    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        if (mStatusHeight <= 0) {
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            mStatusHeight = frame.top;
        }
        if (mStatusHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                mStatusHeight = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return mStatusHeight;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return bp
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, bmp.getWidth(), bmp.getHeight() - statusBarHeight);
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bp;
    }

    /**
     * 获取actionbar的像素高度，默认使用android官方兼容包做actionbar兼容
     *
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        if (context instanceof AppCompatActivity && ((AppCompatActivity) context).getSupportActionBar() != null) {
            Log.d("isAppCompatActivity", "==AppCompatActivity");
            actionBarHeight = ((AppCompatActivity) context).getSupportActionBar().getHeight();
        } else if (context instanceof Activity && ((Activity) context).getActionBar() != null) {
            Log.d("isActivity", "==Activity");
            actionBarHeight = ((Activity) context).getActionBar().getHeight();
        } else if (context instanceof ActivityGroup) {
            Log.d("ActivityGroup", "==ActivityGroup");
            if (((ActivityGroup) context).getCurrentActivity() instanceof AppCompatActivity && ((AppCompatActivity) ((ActivityGroup) context).getCurrentActivity()).getSupportActionBar() != null) {
                actionBarHeight = ((AppCompatActivity) ((ActivityGroup) context).getCurrentActivity()).getSupportActionBar().getHeight();
            } else if (((ActivityGroup) context).getCurrentActivity() instanceof Activity && ((Activity) ((ActivityGroup) context).getCurrentActivity()).getActionBar() != null) {
                actionBarHeight = ((Activity) ((ActivityGroup) context).getCurrentActivity()).getActionBar().getHeight();
            }
        }
        if (actionBarHeight != 0)
            return actionBarHeight;
        final TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)) {
            if (context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            if (context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        Log.d("actionBarHeight", "====" + actionBarHeight);
        return actionBarHeight;
    }


    /**
     * 设置view margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * @param context
     * @param bgAlpha
     * @param isDialog
     */
    public static void backgroundAlpha(Context context, float bgAlpha, boolean isDialog) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        if (isDialog)
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    //设置左边图片
    public static void setLeftImg(TextView tv, @DrawableRes int imgId) {
        if (tv != null)
            tv.setCompoundDrawablesWithIntrinsicBounds(imgId, 0, 0, 0);
    }
    public static void setRightImg(TextView tv, @DrawableRes int imgId) {
        if (tv != null)
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgId, 0);
    }
    public static void setDrawbleLeft(Context context, TextView view, int rsd, int demens) {
        Drawable drawable = ContextCompat.getDrawable(context, rsd);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable, null, null, null);
        view.setCompoundDrawablePadding(dip2px(context, demens));
    }

    public static void setDrawbleRight(Context context, TextView view, int rsd, int demens) {
        Drawable drawable = context.getResources().getDrawable(rsd);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
        view.setCompoundDrawablePadding(demens);
    }

    public static void setDrawbleTop(Context context, TextView view, int rsd, int demens) {
        Drawable drawable = context.getResources().getDrawable(rsd);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(null, drawable, null, null);
        view.setCompoundDrawablePadding(demens);
    }
    public static Point getScreenSize(Activity context) {
        if (context == null) {
            return screenSize;
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            Display diplay = wm.getDefaultDisplay();
            if (diplay != null) {
                diplay.getMetrics(mDisplayMetrics);
                int W = mDisplayMetrics.widthPixels;
                int H = mDisplayMetrics.heightPixels;
                if (W * H > 0 && (W > screenSize.x || H > screenSize.y)) {
                    screenSize.set(W, H);
                }
            }
        }
        return screenSize;
    }
}
