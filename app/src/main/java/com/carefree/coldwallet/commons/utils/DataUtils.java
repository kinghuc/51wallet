package com.carefree.coldwallet.commons.utils;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*--------------------------------------------------------------------
  文 件 名：DataUtils
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：时间工具类
---------------------------------------------------------------------*/
public class DataUtils {
    /**
     * 时间撮转化为2009-07-12格式
     */
    public static String transformTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }
    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param time
     * @return
     */
    public static long transformMillisecond(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        Date date;
        long millisecond = 0;
        String times = null;
        try {
            date = sdr.parse(time);
            millisecond = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return millisecond;
    }

    /**
     * 时间撮转化为07.12格式
     */
    public static String transformTime2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM.dd");
        return format.format(time);
    }
    /**
     * 时间撮转化为07-12格式
     */
    public static String transformTime3(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(time);
    }
    /**
     * 时间撮转化为2009-07-12 09:00:00格式
     */
    public static String transformTime4(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }
    /**
     * 时间撮转化为2009-07-12 09:00格式
     */
    public static String transformTime5(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(time);
    }
    /**
     * 时间撮转化为09:00格式
     */
    public static String transformTime6(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }

    /**
     * 时间撮转化为2017.07.12格式
     */
    public static String transformTime7(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(time);
    }
    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar calendar =Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear;
    }
    /**
     * 获取当前月份
     */
    public static int getCurrentMonth() {
        Calendar calendar =Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        return currentMonth;
    }
    /**
     * 获取今天日期
     */
    public static int getCurrentDay() {
        Calendar calendar =Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        return currentDay;
    }
    /**
     * 获取今天的毫秒
     */
    public static long getCurrentMillisecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取明天时间的毫秒
     */
    public static long getTomorrowdayMillisecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据日期获取星期
     */
    public static String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    /**
     * 根据出生日期获取年龄
     */
    public static int getAge(long birthDay) {
        Calendar cal = Calendar.getInstance();
        long now=cal.getTimeInMillis();
        if (birthDay>=now) {
            return 1;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(new Date(birthDay));
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth)
        {
            if (monthNow == monthBirth)
            {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            }
            else
            {
                age--;
            }
        }
        if(age==0){
            age=1;
        }
        return age;
    }
}
