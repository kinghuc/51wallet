package com.carefree.coldwallet;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.carefree.coldwallet.commons.utils.SharedPrefrencesHelper;
import com.carefree.coldwallet.constants.Constants;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


/*--------------------------------------------------------------------
  文 件 名：application的子类
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：application对象的生命周期是整个程序中最长的，它的生命周期就等于这个程序的生命周期,
            通过Application来进行一些，数据传递，数据共享,数据缓存、初始化激活第三方框架等操作
---------------------------------------------------------------------*/
public class MyApplication extends Application {
    public String token;//访问服务的安全key
    private static MyApplication instance;
    private static SharedPrefrencesHelper sharedPrefrencesHelper;
    private List<Activity> mList = new LinkedList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static MyApplication getInstance() {
        return instance;
    }
    /**
     * 获取执久性文件存储对象
     */
    public static synchronized SharedPrefrencesHelper getSharedPrefrencesHelper() {
        if (sharedPrefrencesHelper == null) {
            sharedPrefrencesHelper = new SharedPrefrencesHelper(instance);
        }
        return sharedPrefrencesHelper;
    }
    
    @Override
    public void onTerminate() {
        //程序终止的时候执行
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        //低内存的时候执行
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        //程序在内存清理的时候执行
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void addActivity(Activity activity){
        mList.add(activity);
    }

    public void closeActivitys() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
            mList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
