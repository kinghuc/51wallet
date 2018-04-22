package com.carefree.coldwallet.commons.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.carefree.coldwallet.ui.activitys.BaseActivity;
import com.carefree.coldwallet.ui.activitys.LockFingerprintActivity;

/*--------------------------------------------------------------------
  文 件 名：FingerprintReceiver
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/30(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：监听手机屏幕灭时开启指纹解锁
---------------------------------------------------------------------*/

public class FingerprintReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
            Log.i("CarefreeWallet","----------亮屏");
            Intent fingerprintIntent = new Intent(context, LockFingerprintActivity.class);
            context.startActivity(fingerprintIntent);
        } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            Log.i("CarefreeWallet","----------灭屏");
            BaseActivity.removeBy(LockFingerprintActivity.class.getSimpleName());
        }
    }
}
