package com.carefree.coldwallet.commons.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.carefree.coldwallet.constants.Constants;


/*--------------------------------------------------------------------
  文 件 名：执久性文件存储类
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：主要是存储一些少量固定变量数据，例如登录账户和密码
---------------------------------------------------------------------*/
public class SharedPrefrencesHelper {
    private Context context;
    private SharedPreferences sharedPreferences;
    //构造方法
    public SharedPrefrencesHelper(Context context) {
        this.context = context;
        //preferences files名字
        //模式 Context.MODE_PRIVATE:私有化的 MODE_WORLD_READABLE,MODE_WORLD_WRITEABLE
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_NAME,
                Context.MODE_PRIVATE);
    }
    //保存一个字符串数据到sharedPreferences文件中
    public boolean saveString(String key, String data) {
        // 1. 获取Editor编辑器对象
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 2. 调用其中的方法来保存数据到sharedPreferences文件中
        editor.putString(key, data);

        // 3. 持久化数据
        return editor.commit();
    }
    //保存一个字符串数据到sharedPreferences文件中
    public boolean saveInt(String key, int data) {
        // 1. 获取Editor编辑器对象
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 2. 调用其中的方法来保存数据到sharedPreferences文件中
        editor.putInt(key, data);

        // 3. 持久化数据
        return editor.commit();
    }

    //保存一个布尔类型数据到sharedPreferences文件中
    public boolean saveBoolean(String key, boolean data) {
        // 1. 获取Editor编辑器对象
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 2. 调用其中的方法来保存数据到sharedPreferences文件中
        editor.putBoolean(key, data);

        // 3. 持久化数据
        return editor.commit();
    }
    //从sharedPreferences中获取一个字符串数据
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
    //从sharedPreferences中获取一个整形数据
    public int getInt(String key) {
        return sharedPreferences.getInt(key,-1);
    }

    //从sharedPreferences中获取boolean数据
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

}