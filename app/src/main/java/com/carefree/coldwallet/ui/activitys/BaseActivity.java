package com.carefree.coldwallet.ui.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.tu.loadingdialog.LoadingDialog;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.FingerprintReceiver;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/*--------------------------------------------------------------------
  文 件 名：Activity父类
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：是所有activity的父类，在此类执行整个activity都相关的操作以及控制管理所有activity
---------------------------------------------------------------------*/
public class BaseActivity extends AppCompatActivity {
    private LinearLayout base_main;
    private LinearLayout base_top;
    private LoadingDialog.Builder builder;
    private LoadingDialog loadingDialog;
    private FingerprintReceiver fingerprintReceiver;
    //添加activity的集合
    public static Map<String, SoftReference<Activity>> activitys = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_frame);
        addThis();
        initReceiver();//注册系统灭屏广播
    }
    /**
     * 添加activity
     */
    private void addThis() {
        if (activitys == null) {
            activitys = new HashMap<>();
        }
        String key = getClass().getSimpleName();
        if (!activitys.containsKey(key)) {
            activitys.put(key, new SoftReference<Activity>(this));
        }
    }
    /**
     * activity onDestroy 方法的时候异常自身对象
     */
    private void removeThis() {
        if (activitys == null) return;
        String key = getClass().getSimpleName();
        if (activitys.containsKey(key)) {
            if (activitys.get(key) != null) {
                activitys.get(key).get().finish();
            }
            activitys.remove(key);
        }
    }

    /**
     * 删除所有activity退出程序
     */
    public static void destroyAll() {
        if (activitys == null) return;
        Iterator<Map.Entry<String, SoftReference<Activity>>> it = activitys.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, SoftReference<Activity>> entry = it.next();
            if (entry.getValue() != null && entry.getValue() != null)
                entry.getValue().get().finish();
            it.remove();
            activitys.remove(entry.getKey());
        }
        activitys.clear();
        activitys = null;
    }
    /**
     * 删除对应的activity
     *
     * @param key
     */
    public static void removeBy(String key) {
        if (key == null || activitys == null || !activitys.containsKey(key)) {
            return;
        }
        if (activitys.get(key) != null && activitys.get(key).get() != null) {
            activitys.get(key).get().finish();
        }
        activitys.remove(key);
    }


    /**
     * 设置页面主体内容
     */
    protected void appendMainBody(Object object,int pResID) {
        View view = LayoutInflater.from(this).inflate(pResID, null);
//        x.view().inject(object,view);
        RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        base_main.addView(view,_LayoutParams);
    }


    /**
     * 设置页面头部内容
     */
    protected void appendTopBody(int pResID) {
        base_top.setVisibility(View.VISIBLE);
        View view = LayoutInflater.from(this).inflate(pResID,null);
        base_top.addView(view);
    }

    /**
     * 设置页面头部标题
     */
    protected void setTopBarTitle(String pText) {
        TextView tvTitle = (TextView) findViewById(R.id.top_center_text);
        if (tvTitle!=null) {
            tvTitle.setText(pText);
        }
    }

    /**
     *  默认监听
     */
    protected void setTopLeftDefultListener(){
        View leftView=findViewById(R.id.top_left);
        if(leftView!=null){
            leftView.setVisibility(View.VISIBLE);
            leftView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 加载菊花
     */
    protected void showLoadding(String message) {
        if (builder == null) {
            builder = new LoadingDialog.Builder(this)
                    .setCancelable(false);
        }
        builder.setMessage(message);
        if (loadingDialog == null) {
            loadingDialog = builder.create();
        }
        loadingDialog.show();
    }

    /**
     * 消失菊花
     */
    protected void dismissLoadding() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 注册系统灭屏广播
     */
    private void initReceiver(){
        fingerprintReceiver = new FingerprintReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(fingerprintReceiver,intentFilter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeThis();
        unregisterReceiver(fingerprintReceiver);
    }
}
