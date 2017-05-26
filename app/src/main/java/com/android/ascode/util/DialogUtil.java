package com.android.ascode.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.ascode.R;
import com.android.ascode.base.AppContext;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by yanfeng on 2017/5/22.
 */

public class DialogUtil {
    public static Dialog getDialg(Context context, View rsd){
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(rsd);
        return  dialog;
    }

    public static void setTPTYpe(TimePickerView.Builder builder, int types, String title){
        builder.setType(getType(types))//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(ResourceUtil.getColor(AppContext.context(),R.color.title_bg))//标题文字颜色
                .setSubmitColor(ResourceUtil.getColor(AppContext.context(),R.color.gray))//确定按钮文字颜色
                .setCancelColor(ResourceUtil.getColor(AppContext.context(),R.color.gray))//取消按钮文字颜色
                .setTitleBgColor(ResourceUtil.getColor(AppContext.context(),android.R.color.white))//标题背景颜色 Night mode
                .setBgColor(ResourceUtil.getColor(AppContext.context(),android.R.color.white))//滚轮背景颜色 Night mode
                .setRange(Calendar.getInstance().get(Calendar.YEAR) - 20, Calendar.getInstance().get(Calendar.YEAR) + 20)//默认是1900-2100年
                //.setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                // .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false);//是否显示为对话框样式
    }

    public static void setTPTYpe(OptionsPickerView.Builder builder, String title){
        builder//默认全部显示
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(ResourceUtil.getColor(AppContext.context(),R.color.title_bg))//标题文字颜色
                .setSubmitColor(ResourceUtil.getColor(AppContext.context(),R.color.gray))//确定按钮文字颜色
                .setCancelColor(ResourceUtil.getColor(AppContext.context(),R.color.gray))//取消按钮文字颜色
                .setTitleBgColor(ResourceUtil.getColor(AppContext.context(),android.R.color.white))//标题背景颜色 Night mode
                .setBgColor(ResourceUtil.getColor(AppContext.context(),android.R.color.white))//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLabels("", "", "")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false);//是否显示为对话框样式;
    }
    //获取年月日 0年月日 1 时分秒 ，2.时分，3 年月日，时分秒
    public static boolean [] getType(int type){
        boolean [] types = null ;
        switch (type) {
            case 0:
                types = new boolean[]{true,true,true,false,false,false};
                break;
            case 1:
                types = new boolean[]{false,false,false,true,true,true};
                break;
            case 2:
                types = new boolean[]{false,false,false,true,true,false};
                break;
            default:
                types = new boolean[]{true,true,true,true,true,true};
                break;
        }
        return types;
    }

    public static TimePickerView getTpv(final Context c, final TextView mTvTime, int type, String title) {
        TimePickerView.Builder pvTime = new TimePickerView.Builder(c, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (date.getTime() < Calendar.getInstance().getTimeInMillis()) {
                    ToastUtil.makeShortText(c,"不能选择过期时间");
                    return;
                }
                mTvTime.setText(CalendarUtil.getTime(date.getTime() + "", 1));
            }
        }) ;
        setTPTYpe(pvTime,type,title);
        return pvTime.build();
    }
    public static OptionsPickerView getOpv(final Context c, final TextView mTvOption, String title, final List<String> datas) {
        OptionsPickerView.Builder pvoptoin = new OptionsPickerView.Builder(c, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                mTvOption.setText(datas.get(options1));
            }
        }) ;
        setTPTYpe(pvoptoin,title);
        OptionsPickerView build = pvoptoin.build();
        build.setPicker(datas);
        return build;
    }
    public static OptionsPickerView getOpv(final Context c, final TextView mTvOption, String title, final List<String> datas, final List<List<String>> datass) {
        OptionsPickerView.Builder pvoptoin = new OptionsPickerView.Builder(c, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                mTvOption.setText(datas.get(options1)+" "+datass.get(options1).get(option2));
            }
        }) ;
        setTPTYpe(pvoptoin,title);
        OptionsPickerView build = pvoptoin.build();
        build.setPicker(datas,datass);
        return build;
    }

    public static void showDialog(Dialog dialog){
        if(dialog!=null&&!dialog.isShowing()){
            dialog.show();
        }
    }
    public static void dissDialog(Dialog dialog){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
    public static void showDialog(OptionsPickerView dialog){
        if(dialog!=null&&!dialog.isShowing()){
            dialog.show();
        }
    }
    public static void dissDialog(OptionsPickerView dialog){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
    public static void showDialog(TimePickerView dialog){
        if(dialog!=null&&!dialog.isShowing()){
            dialog.show();
        }
    }
    public static void dissDialog(TimePickerView dialog){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
