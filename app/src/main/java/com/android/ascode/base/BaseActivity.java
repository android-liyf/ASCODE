package com.android.ascode.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.ascode.R;
import com.android.ascode.httputil.SubscriberListener;
import com.android.ascode.statusbar.StatusBarFontHelper;
import com.android.ascode.util.AppManager;
import com.android.ascode.util.DisplayUtil;
import com.android.ascode.util.StatusBarCompat;
import com.android.ascode.util.ViewFindUtils;

import butterknife.ButterKnife;
import io.reactivex.internal.disposables.ListCompositeDisposable;


/**
 * Created by yanfeng on 2017/5/23.
 */

public abstract class BaseActivity extends AppCompatActivity implements SubscriberListener {

    private ListCompositeDisposable listCompositeDisposable ;
    protected String TAG = this.getClass().getSimpleName();
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        afterSetContentView(savedInstanceState);
        StatusBarCompat.compat(this);
        StatusBarFontHelper.setStatusBarMode(this, true);
        initView();
        initData();
        initListener();
    }
    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    protected void beforeSetContentView(Bundle savedInstanceState) {
    }

    protected void afterSetContentView(Bundle savedInstanceState) {
    }

    @Override
    public void onNext(Object o, int httpcode) {

    }

    protected abstract int getLayoutId();

    public void setToolbar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //隐藏标题栏
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    @Override
    protected void onDestroy() {
        clear();
        AppManager.getAppManager().removeActivity(this);
        mContext = null;
        super.onDestroy();
    }

    public ListCompositeDisposable getComp() {
        if (this.listCompositeDisposable == null) {
            this.listCompositeDisposable = new ListCompositeDisposable();
        }
        return listCompositeDisposable;
    }
    protected void clear() {
        if (!getComp().isDisposed()) {
            getComp().clear();
        }
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * 设置不带Title的标题，注意父布局是LinearLayout
     * @param
     */
    public void setTitleNoBar(){
        View view = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        if(view instanceof LinearLayout) {
            View header = LayoutInflater.from(mContext).inflate(R.layout.statusheight_layout,null);
            ViewGroup.LayoutParams params = ViewFindUtils.find(header,R.id.view_title).getLayoutParams();
            params.height = DisplayUtil.getStatusHeight(mContext);
            header.setLayoutParams(params);
            ((LinearLayout) view).addView(header, 0);
        }
    }

}
