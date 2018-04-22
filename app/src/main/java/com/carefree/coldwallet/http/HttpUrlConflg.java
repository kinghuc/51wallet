package com.carefree.coldwallet.http;

/*--------------------------------------------------------------------
  文 件 名：HttpUrlConflg
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：访问接口地址
---------------------------------------------------------------------*/
public class HttpUrlConflg {
    public static String host = "http://icwv.club:19000";

    /**
     * 用户查询账户信息
     */
    public static String URL_USER_ACCOUNTS_INFO = host + "/cwv/acc/pbqai.do";
    /**
     * 用于账户之间转账使用
     */
    public static String URL_TRANSFER_ACCOUNTS= host + "/cwv/trs/pbtxs.do";


}
