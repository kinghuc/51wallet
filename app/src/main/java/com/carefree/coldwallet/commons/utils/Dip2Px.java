
package com.carefree.coldwallet.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/*--------------------------------------------------------------------
  文 件 名：Dip2Px
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：根据手机的分辨率从 dp 的单位 转成为 px(像素)
---------------------------------------------------------------------*/
public class Dip2Px {
    public static int getDip2Px(Context context, float dpValue){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return (int) (dpValue * dm.density + 0.5f);
    }
    public static int getWith(Activity context){
        WindowManager wm = context.getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }
}
